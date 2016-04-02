/*
 * DataProducer.java
 *
 * Created on February 13, 2004, 12:13 PM
 */

package pt.utl.ist.elab.driver.vmm;

/**
 *
 * @author André Neto - LEFT - IST
 */

import org.opensourcephysics.numerics.ODE;
import org.opensourcephysics.numerics.ODESolver;
import org.opensourcephysics.numerics.RK45;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class MMDataProducer extends VirtualBaseDataSource implements ODE {
	// O numero de canais(de dados) que existem!
	private final int NUM_CHANNELS = 3;

	private final double[] state = new double[] { 0.05, 0.0, 0.0 };// x,v,t
	private final double amp = 1.0;
	private double kmola = 1;
	private double massa = 1.0;
	private double atrito = 0;
	private double xini = 0;
	private int tbs = 100;
	private int nSamples = 10;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;
	private ODESolver odeSolver = null;

	@Override
	public double[] getState() {
		return state;
	}

	@Override
	public void getRate(final double[] state, final double[] rate) {
		rate[0] = state[1];// dx/dt
		rate[1] = -kmola / massa * state[0] - atrito * state[1];// dv/dt=dx2/dt2
		rate[2] = 1;// dt/dt
	}

	// Aproveitamos para inicializar todas as variáveis logo no construtor...
	public MMDataProducer(final VirtualBaseDriver driver, final float kmola, final float massa, final float atrito,
			final float xini, final int tbs, final int nSamples) {
		this.driver = driver;
		this.kmola = kmola;
		this.massa = massa;
		this.atrito = atrito;
		this.xini = xini;
		this.tbs = tbs;
		this.nSamples = nSamples;

		state[0] = xini;

		odeSolver = new RK45(this);
		odeSolver.initialize(tbs / 1000D);
	}

	// Este é o processo que nos vai simular e criar as amostras para enviar ao
	// cliente!
	private class ProducerThread extends Thread {
		private int currentSample = 0;
		private float time = 0;

		@Override
		public void run() {
			try {
				Thread.sleep(1000);

				// Enquanto a amostra actual for menor do que o numero de
				// amostras pedido pelo cliente E ninguém tiver parado a
				// exp...produz dados
				while (currentSample < nSamples && !stopped) {
					// envia as amostra calculadas!
					// 1- cria um array com o numero de canais existentes!
					final PhysicsValue[] value = new PhysicsValue[NUM_CHANNELS];

					// envia no canal CORRESPONDENTE!!! o valor
					value[0] = new PhysicsValue(PhysicsValFactory.fromFloat(time), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getMultiplier());
					value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[0]), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getMultiplier());
					value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[1]), getAcquisitionHeader()
							.getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(2).getSelectedScale().getMultiplier());

					addDataRow(value);

					// incrementa o tempo
					time += tbs / 1000F;
					odeSolver.step();

					// dorme o tbs que o utilizador pediu...
					Thread.sleep(tbs);

					currentSample++;
				}

				join(100);
				endProduction();

				driver.stopVirtualHardware();

			} catch (final InterruptedException ie) {
			}

		}
	}

	@Override
	public void startProduction() {
		stopped = false;
		new ProducerThread().start();
	}

	public void endProduction() {
		stopped = true;
		setDataSourceEnded();
	}

	public static void main(final String args[]) {
		/*
		 * DPendulumDataProducer dp = new DPendulumDataProducer(null,90, 90, 10,
		 * 0, 0.5f, 1.5f, 0.3f, 0.5f, 10, 10000); dp.startProduction();
		 */
	}

	@Override
	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}
}
