/*
 * osciladorDataProducer.java
 *
 * Created on 31 de Mar�o de 2005, 12:47
 */

package pt.utl.ist.elab.driver.voscilador;

/**
 *
 * @author  RF
 */
import org.opensourcephysics.numerics.ODE;
import org.opensourcephysics.numerics.ODESolver;
import org.opensourcephysics.numerics.RK4;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class OsciladorDataProducer extends VirtualBaseDataSource implements ODE {

	private int NUM_CHANNELS = 16;

	private double[] state;

	private double raioMax;
	private double g = 9.8;
	private double a = 1.0;
	private double frequencia = 0.5;
	private double alturaInicial = 0.2;
	private int tbs = 100;
	private int nSamples = 100;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;
	private ODESolver odeSolver = null;

	public double[] getState() {
		return state;
	}

	public void getRate(double[] state, double[] rate) {
		rate[0] = state[1];
		rate[1] = (4 * Math.PI * Math.PI * frequencia * frequencia * state[0] - 2 * g * a * state[0] - 4 * a * a
				* state[0] * state[1] * state[1])
				/ (1 + 4 * a * a * state[0] * state[0]);
		rate[2] = 1;
	}

	/**
	 * Creates a new instance of osciladorDataProducer
	 * 
	 * @param driver
	 * @param g
	 * @param a
	 * @param frequencia
	 * @param alturaInicial
	 * @param tbs
	 * @param nSamples
	 */
	public OsciladorDataProducer(VirtualBaseDriver driver, float g, float a, float frequencia, float alturaInicial,
			int tbs, int nSamples) {
		this.driver = driver;
		this.g = g;
		this.a = a;
		this.frequencia = frequencia;
		this.alturaInicial = alturaInicial;
		this.tbs = tbs;
		this.nSamples = nSamples;
		state = new double[] { Math.sqrt(alturaInicial / a), 0, 0 };

		odeSolver = new RK4(this);
		odeSolver.initialize(tbs / 1000D);
	}

	private class ProducerThread extends Thread {
		private int currentSample = 0;
		private float time = 0;
		private float angulo = 0;

		public void run() {
			try {
				sleep(1000);

				// Enquanto a amostra actual for menor do que o numero de
				// amostras pedido pelo cliente E ninguém tiver parado a
				// exp...produz dados
				while (currentSample <= nSamples && !stopped) {
					// envia as amostra calculadas!
					// 1- cria um array com o numero de canais existentes!
					PhysicsValue[] value = new PhysicsValue[NUM_CHANNELS];

					// envia no canal CORRESPONDENTE!!! o valor
					// tempo
					value[0] = new PhysicsValue(PhysicsValFactory.fromFloat(time), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getMultiplier());
					// raio
					value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[0]), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getMultiplier());

					// x
					value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (state[0] * Math.cos(angulo))),
							getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getMultiplier());
					// y
					value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (state[0] * Math.sin(angulo))),
							getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getMultiplier());
					// z
					value[4] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (state[0] * state[0] * a)),
							getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getMultiplier());
					// vX
					value[5] = new PhysicsValue(PhysicsValFactory
							.fromFloat((float) (state[1] * Math.sin(angulo) + state[0] * 2 * Math.PI * frequencia
									* Math.cos(angulo))), getAcquisitionHeader().getChannelsConfig(5)
							.getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(5)
							.getSelectedScale().getMultiplier());

					// vY
					value[6] = new PhysicsValue(PhysicsValFactory
							.fromFloat((float) (state[1] * Math.cos(angulo) - state[0] * 2 * Math.PI * frequencia
									* Math.sin(angulo))), getAcquisitionHeader().getChannelsConfig(6)
							.getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(6)
							.getSelectedScale().getMultiplier());

					// vZ
					value[7] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (2 * a * state[0] * state[1])),
							getAcquisitionHeader().getChannelsConfig(7).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(7).getSelectedScale().getMultiplier());

					// v
					value[8] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (Math.sqrt(Math.pow((state[1]
							* Math.sin(angulo) + state[0] * 2 * Math.PI * frequencia * Math.cos(angulo)), 2)
							+ Math.pow((state[1] * Math.cos(angulo) - state[0] * 2 * Math.PI * frequencia
									* Math.sin(angulo)), 2) + Math.pow((2 * a * state[0] * state[1]), 2)))),
							getAcquisitionHeader().getChannelsConfig(8).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(8).getSelectedScale().getMultiplier());
					// vraio
					value[9] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (state[1])), getAcquisitionHeader()
							.getChannelsConfig(9).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(9).getSelectedScale().getMultiplier());
					// Energia Mec�nica
					value[10] = new PhysicsValue(PhysicsValFactory
							.fromFloat((float) (a * state[0] * state[0] * g + 0.5 * (Math.pow(state[1]
									* Math.sin(angulo) + state[0] * 2 * Math.PI * frequencia * Math.cos(angulo), 2)
									+ Math.pow(state[1] * Math.cos(angulo) - state[0] * 2 * Math.PI * frequencia
											* Math.sin(angulo), 2) + Math.pow(2 * a * state[0] * state[1], 2)))),
							getAcquisitionHeader().getChannelsConfig(10).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(10).getSelectedScale().getMultiplier());

					// Energia Cin�tica
					value[11] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (0.5 * (Math.pow(state[1]
							* Math.sin(angulo) + state[0] * 2 * Math.PI * frequencia * Math.cos(angulo), 2)
							+ Math.pow(state[1] * Math.cos(angulo) - state[0] * 2 * Math.PI * frequencia
									* Math.sin(angulo), 2) + Math.pow(2 * a * state[0] * state[1], 2)))),
							getAcquisitionHeader().getChannelsConfig(11).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(11).getSelectedScale().getMultiplier());

					// Energia Potencial
					value[12] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (a * state[0] * state[0] * g)),
							getAcquisitionHeader().getChannelsConfig(12).getSelectedScale().getDefaultErrorValue(),
							getAcquisitionHeader().getChannelsConfig(12).getSelectedScale().getMultiplier());

					// Angulo
					value[13] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (angulo)), getAcquisitionHeader()
							.getChannelsConfig(13).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(13).getSelectedScale().getMultiplier());

					// Abertura
					value[14] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (a)), getAcquisitionHeader()
							.getChannelsConfig(14).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(14).getSelectedScale().getMultiplier());

					// Raio M�ximo
					value[15] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (raioMax)), getAcquisitionHeader()
							.getChannelsConfig(15).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(15).getSelectedScale().getMultiplier());

					addDataRow(value);

					if (Math.abs(state[0]) > raioMax) {
						raioMax = Math.abs(state[0]);
					}

					angulo = angulo + 2 * (float) Math.PI * (float) frequencia * tbs / 1000;
					if (angulo >= 2 * Math.PI) {
						angulo = angulo - 2 * (float) Math.PI;
					}
					// incrementa o tempo
					time += tbs / 1000F;
					odeSolver.step();

					// dorme o tbs que o utilizador pediu...
					sleep(tbs);

					currentSample++;
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
		setDataSourceEnded();
	}

	public static void main(String args[]) {
		OsciladorDataProducer oscil = new OsciladorDataProducer(null, 9.8F, 1.0F, 0.5F, 0.2F, 100, 100);
		oscil.startProduction();

	}

	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}
}