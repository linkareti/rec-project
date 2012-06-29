/*
 * Cargas3DDataProducer.java
 *
 * Created on 22 de Mar�o de 2005, 14:37
 */

package pt.utl.ist.elab.driver.vcargas3d;

/**
 *
 * @author  n0dP2
 */

import java.util.ArrayList;
import java.util.List;

import org.opensourcephysics.displayejs.InteractiveCharge;

import pt.utl.ist.elab.common.virtual.utils.ByteUtil;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class Cargas3DDataProducer extends VirtualBaseDataSource {
	// public DrawingPanel3D panel = new DrawingPanel3D();

	private final float k = 8.9875E9f;
	// O numero de canais(de dados) que existem!
	private final int NUM_CHANNELS = 2;

	private VirtualBaseDriver driver = null;
	private final List<InteractiveCharge> sistema;

	// Aproveitamos para inicializar todas as vari�veis logo no construtor...
	public Cargas3DDataProducer(final VirtualBaseDriver driver, final List<InteractiveCharge> sistema) {
		this.driver = driver;
		this.sistema = sistema;
	}

	public Cargas3DDataProducer(final List<InteractiveCharge> sistema) {
		// panel.setPreferredMinMax(0,10,0,10, 0,10);
		this.sistema = sistema;
	}

	// Este é o processo que nos vai simular e criar as amostras para enviar ao
	// cliente!
	private class ProducerThread extends Thread {

		private final ArrayList<ArrayList<Object>> linhas = new ArrayList<ArrayList<Object>>();
		private final List<List<List<Float>>> superficies = new ArrayList<List<List<Float>>>(3);

		private float x, y, z;
		private float rx, ry, rz, r;
		private float Ex = 0, Ey = 0, Ez = 0;
		private float EF;
		private float ExF = 0, EyF = 0, EzF = 0, E = 0;

		public ArrayList<ArrayList<Object>> calculaLinhas() {
			for (int i = 0; i < sistema.size(); i++) {
				pontos(3, i);
			}
			return linhas;
		}

		public List<List<List<Float>>> calculaSuperficies() {
			calculaSuperficiesMaxMin();
			calculaSuperficiesFinal();
			return superficies;
		}

		// porque 20?
		// do espaco dividido em 50, apenas sao mais relevantes
		// para estudo os primeiros 20
		@SuppressWarnings("unchecked")
		List<List<Float>> listax = new ArrayList<List<Float>>(20);
		@SuppressWarnings("unchecked")
		List<List<Float>> listay = new ArrayList<List<Float>>(20);
		@SuppressWarnings("unchecked")
		List<List<Float>> listaz = new ArrayList<List<Float>>(20);
		double EFmin, EFmax;
		// EFmin e EFmax arredondados
		int EFFmin, EFFmax;

		private void calculaSuperficiesMaxMin() {
			// campo minimo,seja numero muito grande
			// para obter-mos o minimo real
			EFmin = 1E100;
			EFmax = 0;

			for (x = 0f; x < 10; x = x + 0.1f) {
				for (y = 0f; y < 10; y = y + 0.1f) {
					for (z = 0f; z < 10; z = z + 0.1f) {

						calculaCampo();
						// /final int EFF =
						// pt.utl.ist.elab.client.virtual.guipack.QMethods.arredondarInt(EF);

						// considere-se apenas o campo dentro de certos
						// limites na caixa
						boolean minmax = true;
						for (int i = 0; i < sistema.size() && minmax; i++) { // se
							// for
							// uma
							// fez
							// falso,
							// seja
							// para
							// sempre
							final InteractiveCharge carga = sistema.get(i);
							if ((x < carga.getX() + 0.5 && x > carga.getX() - 0.5)
									|| (y < carga.getY() + 0.5 && y > carga.getY() - 0.5)
									|| (z < carga.getZ() + 0.5 && z > carga.getZ() - 0.5) || z > 8 || z < 3 || y > 8
									|| y < 3 || x > 8 || x < 3) {
								minmax = false;
							} else {
								minmax = true;
							}
						}
						if (minmax == true) {
							EFmax = Math.max(EF, EFmax);
							EFmin = Math.min(EF, EFmin);
						}
					}
				}
			}
			EFFmin = pt.utl.ist.elab.common.virtual.utils.QMethods.arredondarInt(EFmin);
			EFFmax = pt.utl.ist.elab.common.virtual.utils.QMethods.arredondarInt(EFmax);
			// calculou-se o minimo e maximo
			// volte-se a calcular para selecionar os valores
			// tendo em consideracao o max e o min
		}

		private void calculaSuperficiesFinal() {
			// considere-se o espaco dividido em 50

			// divisao do espaco a considerar para
			// o modulo do campo
			final int razao = pt.utl.ist.elab.common.virtual.utils.QMethods.arredondarInt((EFmax - EFmin) / 50);

			// inicializem-se as listas
			for (int i = 0; i < 20; i++) {
				listax.set(i, new ArrayList<Float>());
				listay.set(i, new ArrayList<Float>());
				listaz.set(i, new ArrayList<Float>());
			}

			for (x = 0f; x < 10; x = x + 0.1f) {
				for (y = 0f; y < 10; y = y + 0.1f) {
					for (z = 0f; z < 10; z = z + 0.1f) {

						calculaCampo();
						final int EFF = pt.utl.ist.elab.common.virtual.utils.QMethods.arredondarInt(EF);
						// o meio e' a tolerancia em guardar os pontos
						final int meio = (int) (EFF * 0.02);
						for (int i = 1; i <= 20; i++) {

							final int modulo = i * razao + EFFmin;
							// adicione-se a lista
							if (modulo > EFF - meio && modulo < EFF + meio && EFF < EFFmax) {
								listax.get(i - 1).add(new Float(x));
								listay.get(i - 1).add(new Float(y));
								listaz.get(i - 1).add(new Float(z));
							}
						}
					}
				}
			}
			superficies.set(0, listax);
			superficies.set(1, listay);
			superficies.set(2, listaz);
			// setArray();
		}

		private void calculaCampo() {
			Ex = 0;
			Ey = 0;
			Ez = 0;
			ExF = 0;
			EyF = 0;
			EzF = 0;
			E = 0;

			for (int i = 0; i < sistema.size(); i++) {
				final InteractiveCharge carga = sistema.get(i);
				rx = x - (float) (carga).getX();
				ry = y - (float) (carga).getY();
				rz = z - (float) (carga).getZ();
				r = (float) Math.sqrt(rx * rx + ry * ry + rz * rz);
				final float q = carga.getCharge();
				E = (float) (k * q * 1E-6 / (r * r));

				Ex = E * rx / r;
				Ey = E * ry / r;
				Ez = E * rz / r;

				ExF = ExF + Ex;
				EyF = EyF + Ey;
				EzF = EzF + Ez;
			}

			EF = (float) Math.sqrt(ExF * ExF + EzF * EzF + EyF * EyF);
		}

		private void pontos(final int densidade, final int nparticula) {
			final InteractiveCharge carga = sistema.get(nparticula);
			final double r = 0.15;
			double x = 0, y = 0, z = 0;
			double x1, y1, z1;

			x1 = -r / 2;
			y1 = -r / 2;
			z1 = -r / 2;

			for (double k = 0; k <= r; k += r / densidade) {
				for (double j = 0; j <= r; j += r / densidade) {
					for (double i = 0; i <= r; i += r / densidade) {

						final double norma = Math.sqrt((i + x1) * (i + x1) + (j + y1) * (j + y1) + (k + z1) * (k + z1));
						x = (x1 + i) / (norma) * r;
						y = (y1 + j) / (norma) * r;
						z = (z1 + k) / (norma) * r;
						// calcula para cada ponto da superficies esferica, para
						// cada esfera
						linhas.add(calculaLinha(carga.getX() + x, carga.getY() + y, carga.getZ() + z, carga.getCharge()));
					}
				}
			}
		}

		private ArrayList<Object> calculaLinha(final double x0, final double y0, final double z0, final double Q) {
			final ArrayList<Object> linha = new ArrayList<Object>();
			if (Q < 0) {
				linha.add("neg");
			}
			if (Q > 0) {
				linha.add("pos");
			}
			if (Q == 0) {
				linha.add("nul");
			}
			final float passo = 0.05f;
			x = (float) x0;
			y = (float) y0;
			z = (float) z0;

			boolean controla = true;
			int controlador = 0;
			while (x < 10 && x > 0 && y < 10 && y > 0 && z < 10 && z > 0 && controla) {
				calculaCampo();
				// inverta-se o sentido das linhas de campo
				// caso a carga seja negativa
				if (Q < 0) {
					ExF = -ExF;
					EyF = -EyF;
					EzF = -EzF;
				}

				x = x + (ExF / EF) * passo;
				y = y + (EyF / EF) * passo;
				z = z + (EzF / EF) * passo;

				controlador++;
				// seguranca apenas
				// previne OutOfMemoryError
				if (controlador > 1500) {
					controla = false;
				}

				// adicione-se ponto ao traco
				final Float[] linhaXYZ = new Float[3];
				linhaXYZ[0] = new Float(x);
				linhaXYZ[1] = new Float(y);
				linhaXYZ[2] = new Float(z);
				linha.add(linhaXYZ);

				// se a linha for de encontro a uma carga, pare-se o calculo
				for (int i = 0; i < sistema.size(); i++) {
					final InteractiveCharge carga = sistema.get(i);
					if ((x < carga.getX() + 0.15 && x > carga.getX() - 0.15)
							&& (y < carga.getY() + 0.15 && y > carga.getY() - 0.15)
							&& (z < carga.getZ() + 0.15 && z > carga.getZ() - 0.15)) {
						controla = false;
					}
				}
			}

			return linha;
		}

		@Override
		public void run() {
			// ArrayList sup20=new ArrayList();
			// toPanelSuperficies(calculaSuperficies(),sup20);
			// panel.addDrawable((org.opensourcephysics.displayejs.InteractivePoints)(sup20.get(10)));
			// panel.repaint();
			final PhysicsValue[] value = new PhysicsValue[NUM_CHANNELS];

			// envia no canal CORRESPONDENTE!!! o valor
			PhysicsVal val = PhysicsValFactory
					.fromByteArray(ByteUtil.getObjectAsByteArray(calculaLinhas()), "data/raw");
			value[0] = new PhysicsValue(val, null, com.linkare.rec.data.Multiplier.none);
			val = PhysicsValFactory.fromByteArray(ByteUtil.getObjectAsByteArray(calculaSuperficies()), "data/raw");
			value[1] = new PhysicsValue(val, null, com.linkare.rec.data.Multiplier.none);

			addDataRow(value);
			try {
				join(100);
			} catch (final InterruptedException ie) {
			}
			endProduction();

			driver.stopVirtualHardware();

		}
	}

	// public static void main(String args[]){
	// pt.utl.ist.elab.virtual.client.cargas3d.Sistema.novaCarga(5f, 8f, 5f,
	// -25f);
	// pt.utl.ist.elab.virtual.client.cargas3d.Sistema.novaCarga(5f, 2f, 5f,
	// 25f);
	// pt.utl.ist.elab.virtual.client.cargas3d.Sistema.novaCarga(1f, 2f, 3f,
	// 10f);
	// Cargas3DDataProducer hips = new Cargas3DDataProducer(
	// pt.utl.ist.elab.virtual.client.cargas3d.Sistema.sistema);
	// hips.startProduction();
	// javax.swing.JFrame frame = new javax.swing.JFrame();
	// frame.getContentPane().add(hips.panel);
	// frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	// frame.pack();
	// frame.show();
	// }

	@Override
	public void startProduction() {
		new ProducerThread().start();
	}

	public void endProduction() {
		setDataSourceEnded();
	}

	@Override
	public void stopNow() {
		setDataSourceStoped();
	}

}