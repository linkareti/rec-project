/*
 * QuantumDataProducer.java
 *
 * Created on February 13, 2004, 12:13 PM
 */

package pt.utl.ist.elab.driver.vquantum;

/**
 *
 * @author  nomead
 */

import org.opensourcephysics.numerics.ParsedFunction;
import org.opensourcephysics.numerics.ParserException;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;
import pt.utl.ist.elab.driver.virtual.utils.ByteUtil;
import pt.utl.ist.elab.driver.virtual.utils.Complex;
import pt.utl.ist.elab.driver.virtual.utils.FFT;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class QuantumDataProducer extends VirtualBaseDataSource implements Runnable {
	// O numero de canais(de dados) que existem!
	private int NUM_CHANNELS = 10;

	private int tbs = 1;
	private int nSamples;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;

	// funcao de onda
	private Complex[] psi;
	private Complex[] ExpT, ExpV;

	// constante planck radiana ao quadrado (me.eV.A*A)
	private static final double h = 7.627549007;
	private static final double hbar = 6.58195389e-16; // constante planck
	// radiana (eV)

	// //////////////////////////////////////////////
	// //////////////////////////////////////////////

	/***********************************************
	 * POTENTIAL
	 **********************************************/
	double[][] potentials; // [n Potentials][x,width]
	// //////////////////////////////////////////////
	// //////////////////////////////////////////////

	private double time = 0; // s
	private double dt; // s
	private double dtFrame; // s
	private double dx; // Angstroms
	private int N;

	// Gaussiana
	private double x0; // Angstroms
	private double k0; // 1/Angstroms
	private double deltaX; // Angstroms

	/*
	 * Coeficientes de Transmissao Coeficientes de Reflexao
	 */
	private double[] coefR;
	private double[] coefT;
	private boolean modalRC = false, modalTC = false;
	private boolean principioCoef = false;

	/*
	 * Tunneling Time Tunneling Speed
	 */
	private double[][] tunneling;
	private int potentialLookUp = -1;
	private boolean principioTunneling = true;
	private double psiLookedUp = 0;
	private double potentialInitTime = 0;

	private double tolerancia = 1e-5;

	/**
	 * Ferramentas de Diagnostico
	 */
	private boolean wraparoundKSCheckup = true;
	private boolean wraparoundXSCheckup;
	private boolean tunnelingSettings;

	/**
	 * Creates a new instance of Particle
	 * 
	 * (min, max) -> Angstroms potential[0] -> inicio da primeira barreira de
	 * potencial (Angstroms) potential[1] -> fim da primeira barreira de
	 * potencial (Angstroms) potential[2] -> espaco entre potenciais (Angstroms)
	 * potential[3] -> valor do potencial (eV) _nPotentials -> numero de
	 * potenciais _energy -> energia da particula (eV)
	 */
	public QuantumDataProducer(VirtualBaseDriver driver, double _dX0, double _x0, double _energy, int log2N,
			double _deltaX, double tol, double _dt, double _tbs, int _nSamples, boolean _wraparoundKS,
			boolean _wraparoundXS, boolean _tunneling) {
		this.driver = driver;
		nSamples = _nSamples;
		N = (int) Math.round(Math.pow(2, log2N));
		dx = _dX0 / (N - 1);
		x0 = _x0;
		deltaX = _deltaX;
		k0 = Math.sqrt(2 * _energy / h);
		tolerancia = tol;
		dt = _dt;
		dtFrame = _tbs;
		tunnelingSettings = _tunneling;
		wraparoundKSCheckup = _wraparoundKS;
		wraparoundXSCheckup = _wraparoundXS;
		psi = new Complex[N];
		ExpV = new Complex[N];
		for (int i = 0; i < N; i++) {
			psi[i] = getInitialPsi(i * dx + x0 - _dX0 / 2);
			ExpV[i] = Complex.expI(new Complex(0, 0));
		}
		initializeExpT();
	}

	/*
	 * Caracteristicas da Particula e sistema a ela associada
	 */
	public String toString() {
		return "";// "Energia : "+(k0*k0*h/2)+" eV\nN : "+N+"\nk0 : "+k0+" Angstroms-1\n--------------------------------\nPotenciais : "+nPotentials+"\nV0 : "+vFunction.toString()+" eV\nComprimento : "+((xFPotential-xIPotential)*dx)+" Angstroms\nEspa\u00e7amento : "+(potentialGap*dx)+" Angstroms\n--------------------------------\ndx : "+dx+" Angstroms\nX : "+(dx*N)+" Angstroms\ndk : "+(2*Math.PI/((N-1)*dx))+" Angstroms\nK : "+(2*Math.PI/dx)+" Angstroms\ndeltaX : "+deltaX+" Angstroms\nFrequ\u00eancia de Samplagem : "+(1/dx)+" Hz\n--------------------------------\n--------------------------------";
	}

	/*
	 * Inicializa o ExpT
	 */
	public void initializeExpT() {

		ExpT = new Complex[N];

		double k, KE, dk = 2 * Math.PI / ((N - 1) * dx);

		for (int i = 1; i <= N / 2; i++) {
			k = (i - 1) * dk;
			KE = k * k * h / 2;
			ExpT[i - 1] = Complex.expI(new Complex(0, -KE * dt / hbar));
			k = -i * dk;
			KE = k * k * h / 2;
			ExpT[N - i] = Complex.expI(new Complex(0, -KE * dt / hbar));
		}
	}

	/*
	 * Determina se a funcao e' constante
	 */
	public static boolean isConstant(ParsedFunction _func) {
		String f = _func.toString();
		if (f.indexOf("x", f.indexOf("=") + 1) == -1 && f.indexOf("X", f.indexOf("=") + 1) == -1)
			return true;
		return false;
	}

	public void configPotentials(String strPotentials) {
		String[] pots = strPotentials.split("#");
		double[][] tmpPotentials = new double[pots.length][2];

		java.util.ArrayList tmpList = new java.util.ArrayList();

		for (int i = 0; i < pots.length; i++) {
			String[] potsSet = pots[i].split(":");
			tmpPotentials[i][0] = Double.parseDouble(potsSet[0]);
			tmpPotentials[i][1] = Double.parseDouble(potsSet[1]);
			String vF = potsSet[2].substring(potsSet[2].indexOf("=") + 1, potsSet[2].length());
			boolean isMedio = potsSet[3].equalsIgnoreCase("true");
			try {
				if (initializeExpV(tmpPotentials[i][0], tmpPotentials[i][1], vF, isMedio))
					tmpList.add(tmpPotentials[i]);
			} catch (ParserException pe) {
			}
		}
		java.util.Collections.sort(tmpList, new SortPotentials());
		potentials = new double[tmpList.size()][2];
		for (int i = 0; i < tmpList.size(); i++) {
			potentials[i] = (double[]) tmpList.get(i);
			potentials[i][0] -= (x0 - (N - 1) * dx / 2);
		}

		coefR = new double[potentials.length];
		coefT = new double[potentials.length];
		tunneling = new double[potentials.length][3];
	}

	// Initicialize o ExpV dum potencial (x,width,vF,isMedio)
	// devolve true se o potencial esta no espaco da simulacao
	public boolean initializeExpV(double x, double width, String vF, boolean isMedio) throws ParserException {
		if (x - x0 > (N - 1) * dx / 2 || x + width - x0 < -(N - 1) * dx / 2)
			return false;

		ParsedFunction vFunction = new ParsedFunction(vF);
		if (isMedio && !isConstant(vFunction))
			isMedio = false;

		if (isMedio) {
			double V0 = vFunction.evaluate(0);
			double h2 = 6.626068e-34 / 2;
			double left = x - (x0 - (N - 1) * dx / 2);
			double right = x + width - (x0 - (N - 1) * dx / 2);
			for (int i = 0; i < N; i++) {
				if (i * dx >= left - h2 && i * dx < left + h2)
					ExpV[i] = Complex.expI(new Complex(0, -(V0 * (i * dx - (left - h2)) / h) * dt / (hbar * 2)));
				else if (i * dx >= left + h2 && i * dx <= right - h2)
					ExpV[i] = Complex.expI(new Complex(0, -V0 * dt / (hbar * 2)));
				else if (i * dx > right - h2 && i * dx <= right + h2)
					ExpV[i] = Complex.expI(new Complex(0, -(V0 + (right + h2 - i * dx)) * dt / (hbar * 2)));
			}
		} else
			for (int i = 0; i < N; i++)
				if (i * dx >= x - (x0 - (N - 1) * dx / 2) && i * dx <= x + width - (x0 - (N - 1) * dx / 2)) {
					ExpV[i] = Complex.expI(new Complex(0, -(vFunction.evaluate(i * dx - x + (x0 - (N - 1) * dx / 2)))
							* dt / (hbar * 2)));
				} else if (i * dx > x + width - (x0 - (N - 1) * dx / 2))
					return true;
		return true;
	}

	/*
	 * Devolve o y para em funcao do x, da gaussiana inicial que representa a
	 * particula
	 */
	public Complex getInitialPsi(double x) {
		Complex _psi = Complex.expI(new Complex(-Math.pow((x - x0), 2) / (4 * deltaX * deltaX), k0 * x));
		_psi.times((1 / Math.pow((2 * Math.PI * deltaX * deltaX), 1 / 4)));

		return _psi;
	}

	/*
	 * Limpa as caracteristicas dos coeficientes, do tempo e do tunneling
	 */
	public void reset() {
		for (int i = 0; i < coefR.length; i++) {
			coefR[i] = 0;
			coefT[i] = 0;
		}

		for (int j = 0; j < tunneling.length; j++)
			for (int i = 0; i < 3; i++)
				tunneling[j][i] = 0;
		time = 0;
	}

	/*
	 * Avanca numericamente a particula, ate a soma dos passos dados prefazerem
	 * o dtFrame. dtFrame < dt => dtFrame = dt
	 * 
	 * Este avanco contempla a funcao de onda, as caracteristicas de tunneling e
	 * os coeficientes
	 * 
	 * Inicia o calculo dos coeficientes entre o fim dum tempo de tunel e o
	 * inicio da contagem de outro
	 */
	public double[] step() {
		double timeInicial = time;
		byte wraparoundXS = 0;
		byte wraparoundKS = 0;
		Complex[] phi = new Complex[N];

		while (time - timeInicial < dtFrame) {
			time += dt;

			for (int i = 0; i < N; i++)
				phi[i] = Complex.times(ExpV[i], psi[i]);

			phi = FFT.calculateFFT(phi, (int) (Math.log(N) / Math.log(2)), 1);

			for (int i = 0; i < N; i++)
				phi[i] = Complex.times(ExpT[i], phi[i]);

			phi = FFT.calculateFFT(phi, (int) (Math.log(N) / Math.log(2)), -1);

			for (int i = 0; i < N; i++)
				psi[i] = Complex.times(ExpV[i], phi[i]);

			// //////////////////////////////////
			// //////////////////////////////////

			if (wraparoundKSCheckup && wraparoundCheckUpKSpace()) {
				wraparoundKS = 1;
				// se chegar aos limites e nao tiver coeficiente e ja tiver
				// iniciado, fica com o actual
				if (principioCoef && tunnelingSettings) {
					modalRC = true;
					modalTC = true;
					principioCoef = false;
				}
			}

			if (wraparoundXSCheckup && wraparoundCheckUpCoordinateSpace()) {
				wraparoundXS = 1;
				// se chegar aos limites e nao tiver coeficiente e ja tiver
				// iniciado, fica com o actual
				if (principioCoef && tunnelingSettings) {
					modalRC = true;
					modalTC = true;
					principioCoef = false;
				}
			}

			if (tunnelingSettings) {
				if (tunnelingTimeSpeed() != null) {
					principioCoef = true;
					modalRC = false;
					modalTC = false;
				}
				// verifica se inicio da contagem do tunneling time e se sim e
				// no caso de ainda nao ter obtido coefs, finaliza com os
				// valores actuais
				else if (potentialInitTime == time && principioCoef && (!modalRC || !modalTC)) {
					modalRC = true;
					modalTC = true;
					principioCoef = false;
				} else if (modalRC && modalTC)
					principioCoef = false;

				if (modalRC && modalTC) {
					modalRC = false;
					modalTC = false;

					double auxCoefR = coefR[potentialLookUp];

					coefR[potentialLookUp] /= (coefR[potentialLookUp] + coefT[potentialLookUp]);
					coefT[potentialLookUp] /= (auxCoefR + coefT[potentialLookUp]);
				}

				if (principioCoef)
					coefs();
			}
		}
		return new double[] { time, wraparoundXS, wraparoundKS };
	}

	/*
	 * Calculo numerico dos coeficientes
	 * 
	 * Avalia o integral dum lado e doutro do potencial actual, esperando obter
	 * um maximo local, que equivalera ao coeficiente
	 * 
	 * No caso em que o comprimento do potencial ultrapasse a grelha, e
	 * considerado como transmissao o calculo entre a primeira face do potencial
	 * e o fim da grelha.
	 */
	private void coefs() {
		int ni = (int) Math.round(potentials[potentialLookUp][0] / dx);
		int nf = (int) Math.round((potentials[potentialLookUp][0] + potentials[potentialLookUp][1]) / dx);
		int n2i;
		int n2f;

		if (potentialLookUp == 0)
			n2i = 0;
		else
			n2i = (int) Math.round(potentials[potentialLookUp - 1][0] / dx);

		if (potentialLookUp == potentials.length - 1)
			n2f = N - 1;
		else
			n2f = (int) Math.round((potentials[potentialLookUp + 1][0] + potentials[potentialLookUp + 1][1]) / dx);

		if (n2f >= psi.length)
			n2f = psi.length - 1;

		if (nf >= psi.length)
			nf = psi.length - 1;

		double left = integralPsi(n2i, ni);
		double right;
		if (nf == n2f)
			right = integralPsi(ni, nf);
		else
			right = integralPsi(nf, n2f);

		coefR[potentialLookUp] = Math.max(left, coefR[potentialLookUp]);
		coefT[potentialLookUp] = Math.max(right, coefT[potentialLookUp]);

		if (coefR[potentialLookUp] > left)
			modalRC = true;
		if (coefT[potentialLookUp] > right)
			modalTC = true;
		if (nf == n2f) {
			modalRC = true;
			modalTC = true;
		}
	}

	/*
	 * Faz o calculo do tempo que a funcao de onda demora a atravessar o
	 * potencial. No caso de nao atravessar nao faz qualquer definicao
	 * 
	 * Inicio da contagem quando se obtem um maximo local na primeira face po
	 * potencial actual e fim da contagem quando o mesmo acontece para o a
	 * segunda face do mesmo potencial. Este maximo local tem de ser maior que
	 * um valor de tolerancia.
	 * 
	 * [t,v,w] -> ]s,m/s,A]
	 */
	private double[] tunnelingTimeSpeed() {
		if (potentialLookUp + 2 <= potentials.length) {
			int ini;

			if (principioTunneling)
				ini = (int) Math.round(potentials[potentialLookUp + 1][0] / dx);
			else {
				ini = (int) Math.round((potentials[potentialLookUp + 1][0] + potentials[potentialLookUp + 1][1]) / dx);
				if (ini >= psi.length) // final para la da grelha = final no
					// ultimo ponto da grelha
					ini = psi.length - 1;
			}

			if (ini < psi.length) {
				double[] modPsiSq = new double[psi.length];

				for (int i = 0; i < psi.length; i++)
					modPsiSq[i] = Math.pow(psi[i].abs(), 2);

				psiLookedUp = Math.max(modPsiSq[ini], psiLookedUp);

				if (psiLookedUp > modPsiSq[ini] && psiLookedUp > tolerancia) {
					principioTunneling = !principioTunneling;

					if (principioTunneling)
						potentialLookUp++;

					psiLookedUp = 0;

					if (!principioTunneling)
						potentialInitTime = time;
					else {
						tunneling[potentialLookUp][0] = time - potentialInitTime;
						tunneling[potentialLookUp][1] = potentials[potentialLookUp][1] * 1e-10
								/ (time - potentialInitTime);// (ini-xIPotential-(potentialLookUp-1)*(xFPotential
						// - xIPotential
						// +
						// potentialGap))*dx*1e-10/(time-potentialInitTime);
						tunneling[potentialLookUp][2] = potentials[potentialLookUp][1];// (ini-xIPotential-(potentialLookUp-1)*(xFPotential
						// -
						// xIPotential
						// +
						// potentialGap))*dx*1e-10;
						return new double[] { tunneling[potentialLookUp][0], tunneling[potentialLookUp][1],
								tunneling[potentialLookUp][2] };
					}
				}
			}
		}
		return null;
	}

	/*
	 * Calcula a FFT da funcao de onda e re-ordena os pontos segundo o limite de
	 * nyquist
	 */
	private Complex[] psiKS() {
		Complex[] psiKS = FFT.calculateFFT(psi, (int) (Math.log(N) / Math.log(2)), 1);
		Complex[] psiKSArranged = new Complex[psiKS.length];

		for (int i = psiKS.length / 2; i < psiKS.length; i++) {
			psiKSArranged[i - psiKS.length / 2] = psiKS[i];
			psiKSArranged[i] = psiKS[i - psiKS.length / 2];
		}

		return psiKSArranged;
	}

	/*
	 * Faz o integral entre os extremos (min, max) da FFT da funcao de onda
	 */
	private double integralPsiKS(int min, int max) {
		double dk = 2 * Math.PI / ((N - 1) * dx);
		Complex[] psiKSArranged = psiKS();
		double integral = 0;

		for (int i = min; i < max - 1; i++) {
			double modPsiKS = Math.pow(psiKSArranged[i].abs(), 2);
			integral += modPsiKS * dk + dk * (Math.pow(psiKSArranged[i + 1].abs(), 2) - modPsiKS) / 2;
		}

		return integral;
	}

	/*
	 * Faz o integral entre os extremos (min, max) da funcao de onda
	 */
	public double integralPsi(int min, int max) {
		double integral = 0;

		for (int i = min; i < max - 1; i++) {
			double modPsi = Math.pow(this.psi[i].abs(), 2);
			integral += modPsi * this.dx + this.dx * (Math.pow(this.psi[i + 1].abs(), 2) - modPsi) / 2;
		}
		return integral;
	}

	/*
	 * Verifica se pelo menos 0.5% da funcao de onda esta nos ultimos 5% dos
	 * extremos do espaco coordenado
	 */
	private boolean wraparoundCheckUpCoordinateSpace() {

		double total = integralPsi(0, N);

		double left = integralPsi(0, (int) (N * .05)) / total;
		double right = integralPsi((int) (N - N * 0.05), N) / total;

		if (right >= .005 || left >= .005)
			return true;
		return false;
	}

	/*
	 * Verifica se pelo menos 0.5% da FFT da funcao de onda esta nos ultimos 5%
	 * dos extremos do espaco dos k's
	 */
	private boolean wraparoundCheckUpKSpace() {

		double total = integralPsiKS(0, N);

		double left = integralPsiKS(0, ((int) (N * 0.05))) / total;
		double right = integralPsiKS(N - ((int) (N * 0.05)), N) / total;

		if (right >= .005 || left >= .005)
			return true;
		return false;
	}

	public double[][] getTunneling() {
		return tunneling;
	}

	public double getTC(int _n) {
		if (principioCoef && potentialLookUp == _n)
			return -1;
		return coefT[_n];
	}

	public double getTime() {
		return time;
	}

	public double getRC(int _n) {
		if (principioCoef && potentialLookUp == _n)
			return -1;
		return coefR[_n];
	}

	// TESTE
	public void start(pt.utl.ist.elab.virtual.client.quantum.displays.Animation an) {
		this.an = an;
		animaThread = new Thread(this);
		animaThread.start();
	}

	// TESTE
	pt.utl.ist.elab.virtual.client.quantum.displays.Animation an;
	private Thread animaThread;

	public void run() {
		int currentSample = 0;
		int counter = 0;

		while (animaThread == Thread.currentThread() && currentSample < nSamples) {

			step();
			// an.moves(psi);
			an.moves(ByteUtil.getObjectAsByteArray(psi));
			currentSample++;
			try {
				animaThread.sleep(tbs);
			} catch (InterruptedException e) {
			}

			counter++;
		}
	}

	private class ProducerThread extends Thread {
		private int currentSample = 0;

		public void run() {
			try {
				sleep(1000);

				PhysicsValue[] value;

				while (currentSample < nSamples && !stopped) {
					double timeInicial = time;
					byte wraparoundXS = 0;
					byte wraparoundKS = 0;
					Complex[] phi = new Complex[N];

					while (time - timeInicial < dtFrame) {
						time += dt;

						for (int i = 0; i < N; i++)
							phi[i] = Complex.times(ExpV[i], psi[i]);

						phi = FFT.calculateFFT(phi, (int) (Math.log(N) / Math.log(2)), 1);

						for (int i = 0; i < N; i++)
							phi[i] = Complex.times(ExpT[i], phi[i]);

						phi = FFT.calculateFFT(phi, (int) (Math.log(N) / Math.log(2)), -1);

						for (int i = 0; i < N; i++)
							psi[i] = Complex.times(ExpV[i], phi[i]);

						// //////////////////////////////////
						// //////////////////////////////////
						value = new PhysicsValue[NUM_CHANNELS];
						if (wraparoundKS == 0 && wraparoundKSCheckup && wraparoundCheckUpKSpace()) {
							wraparoundKS = 1;
							value[7] = new PhysicsValue(PhysicsValFactory.fromBoolean(true), getAcquisitionHeader()
									.getChannelsConfig(7).getSelectedScale().getDefaultErrorValue(),
									getAcquisitionHeader().getChannelsConfig(7).getSelectedScale().getMultiplier());
							addDataRow(value);
							currentSample++;
							// se chegar aos limites e nao tiver coeficiente e
							// ja tiver iniciado, fica com o actual
							if (principioCoef && tunnelingSettings) {
								modalRC = true;
								modalTC = true;
								principioCoef = false;
							}
						}

						if (wraparoundXS == 0 && wraparoundXSCheckup && wraparoundCheckUpCoordinateSpace()) {
							wraparoundXS = 1;
							value[8] = new PhysicsValue(PhysicsValFactory.fromBoolean(true), getAcquisitionHeader()
									.getChannelsConfig(8).getSelectedScale().getDefaultErrorValue(),
									getAcquisitionHeader().getChannelsConfig(8).getSelectedScale().getMultiplier());
							addDataRow(value);
							currentSample++;
							// se chegar aos limites e nao tiver coeficiente e
							// ja tiver iniciado, fica com o actual
							if (principioCoef && tunnelingSettings) {
								modalRC = true;
								modalTC = true;
								principioCoef = false;
							}
						}

						if (tunnelingSettings) {
							double[] tunnelingTimeSpeed = tunnelingTimeSpeed(); // t,v,w
							if (tunnelingTimeSpeed != null && potentialLookUp < potentials.length) {
								principioCoef = true;
								modalRC = false;
								modalTC = false;

								value = new PhysicsValue[NUM_CHANNELS];
								value[1] = new PhysicsValue(PhysicsValFactory.fromInt(potentialLookUp),
										getAcquisitionHeader().getChannelsConfig(1).getSelectedScale()
												.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(1)
												.getSelectedScale().getMultiplier());
								value[4] = new PhysicsValue(PhysicsValFactory.fromFloat((float) tunnelingTimeSpeed[1]),
										getAcquisitionHeader().getChannelsConfig(4).getSelectedScale()
												.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(4)
												.getSelectedScale().getMultiplier());
								value[5] = new PhysicsValue(PhysicsValFactory.fromFloat((float) tunnelingTimeSpeed[2]),
										getAcquisitionHeader().getChannelsConfig(5).getSelectedScale()
												.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(5)
												.getSelectedScale().getMultiplier());
								value[6] = new PhysicsValue(PhysicsValFactory
										.fromFloat((float) (tunnelingTimeSpeed[0] * 1e21)), getAcquisitionHeader()
										.getChannelsConfig(6).getSelectedScale().getDefaultErrorValue(),
										getAcquisitionHeader().getChannelsConfig(6).getSelectedScale().getMultiplier());
								addDataRow(value);
								currentSample++;
							}
							// verifica se inicio da contagem do tunneling time
							// e se sim e no caso de ainda nao ter obtido coefs,
							// finaliza com os valores actuais
							else if (potentialInitTime == time && principioCoef && (!modalRC || !modalTC)) {
								modalRC = true;
								modalTC = true;
								principioCoef = false;
							} else if (modalRC && modalTC)
								principioCoef = false;

							if ((modalRC && modalTC || currentSample == nSamples - 2)
									&& potentialLookUp < potentials.length) {
								modalRC = false;
								modalTC = false;

								double auxCoefR = coefR[potentialLookUp];

								coefR[potentialLookUp] /= (coefR[potentialLookUp] + coefT[potentialLookUp]);
								coefT[potentialLookUp] /= (auxCoefR + coefT[potentialLookUp]);

								value = new PhysicsValue[NUM_CHANNELS];
								value[9] = new PhysicsValue(PhysicsValFactory.fromInt(potentialLookUp),
										getAcquisitionHeader().getChannelsConfig(9).getSelectedScale()
												.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(9)
												.getSelectedScale().getMultiplier());
								value[2] = new PhysicsValue(
										PhysicsValFactory.fromFloat((float) coefR[potentialLookUp]),
										getAcquisitionHeader().getChannelsConfig(2).getSelectedScale()
												.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(2)
												.getSelectedScale().getMultiplier());
								value[3] = new PhysicsValue(
										PhysicsValFactory.fromFloat((float) coefT[potentialLookUp]),
										getAcquisitionHeader().getChannelsConfig(3).getSelectedScale()
												.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(3)
												.getSelectedScale().getMultiplier());
								addDataRow(value);
								currentSample++;
							}

							if (principioCoef)
								coefs();
						}
					}
					value = new PhysicsValue[NUM_CHANNELS];
					value[0] = new PhysicsValue(PhysicsValFactory.fromByteArray(ByteUtil.getObjectAsByteArray(psi),
							"data/raw"), null, com.linkare.rec.data.Multiplier.none);
					addDataRow(value);
					currentSample++;
					sleep(tbs);
				}

				join(100);
				endProduction();

				driver.stopVirtualHardware();
			} catch (InterruptedException ie) {
			}
		}
	}

	public void startProduction() {
		stopped = false;
		new ProducerThread().start();
	}

	public void endProduction() {
		stopped = true;
		setDataSourceStoped();
	}

	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}

}
