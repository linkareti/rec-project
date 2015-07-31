/*
 * M3DataProducer.java
 *
 * Created on 20 de Fevereiro de 2005, 19:10
 */

package pt.utl.ist.elab.driver.vm3;

import org.opensourcephysics.numerics.ODE;
import org.opensourcephysics.numerics.ODESolver;
import org.opensourcephysics.numerics.RK45;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

/**
 * 
 * @author n0dP2
 */
public class M3DataProducer extends VirtualBaseDataSource implements ODE {

	private final int NUM_CHANNELS = 5;
	private final double a = 10; // lado da caixa
	private final double l1 = 5, l2 = Math.sqrt(50), l3 = Math.sqrt(50); // comprimento
	// natural
	// das molas
	private final double[] state = new double[5];
	private final double k1;
	private final double k2;
	private final double k3;
	private final double massa;
	private final double x0;
	private final double y0;
	private final int tbs;
	private final int nSamples;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;
	private ODESolver odeSolver = null;

	@Override
	public double[] getState() {
		return state;
	}

	@Override
	public void getRate(final double[] state, final double[] rate) {
		rate[0] = state[1];
		rate[1] = (k1 * (a / 2 - state[0])
				* (-l1 * Math.pow((Math.pow((a - state[2]), 2) + Math.pow((a / 2 - state[0]), 2)), -0.5) + 1.0) + k2
				* state[0] * (l2 * Math.pow((Math.pow(state[0], 2) + Math.pow(state[2], 2)), -0.5) - 1.0) + k3
				* (a - state[0])
				* (-l3 * Math.pow((Math.pow((state[2]), 2) + Math.pow((a - state[0]), 2)), -0.5) + 1.0))
				/ massa;
		rate[2] = state[3];
		rate[3] = (k1 * (a - state[2])
				* (-l1 * Math.pow((Math.pow((a - state[2]), 2) + Math.pow((a / 2 - state[0]), 2)), -0.5) + 1.0) + k2
				* state[2] * (l2 * Math.pow((Math.pow(state[0], 2) + Math.pow(state[2], 2)), -0.5) - 1.0) + k3
				* (state[2]) * (l3 * Math.pow((Math.pow((state[2]), 2) + Math.pow((a - state[0]), 2)), -0.5) - 1.0))
				/ massa;
		rate[4] = 1;
	}

	// Aproveitamos para inicializar todas as vari�veis logo no construtor...
	public M3DataProducer(final VirtualBaseDriver driver, final float massa, final float k1, final float k2,
			final float k3, final float x0, final float y0, final int tbs, final int nSamples) {
		this.driver = driver;
		this.massa = massa;
		this.k1 = k1;
		this.k2 = k2;
		this.k3 = k3;
		this.x0 = x0;
		this.y0 = y0;
		this.tbs = tbs;
		this.nSamples = nSamples;

		state[0] = x0;
		state[1] = 0;
		state[2] = y0;
		state[3] = 0;
		state[4] = 0;

		odeSolver = new RK45(this);
		odeSolver.initialize(tbs / 1000D);
	}

	// Este � o processo que nos vai simular e criar as amostras para enviar ao
	// cliente!
	private class ProducerThread extends Thread {
		private int currentSample = 0;
		private float time = 0;

		@Override
		public void run() {
			try {
				Thread.sleep(1000);

				// Enquanto a amostra actual for menor do que o numero de
				// amostras pedido pelo cliente E ningu�m tiver parado a
				// exp...produz dados
				while (currentSample < nSamples && !stopped) {
					// envia as amostra calculadas!
					// 1- cria um array com o numero de canais existentes!
					final PhysicsValue[] value = new PhysicsValue[NUM_CHANNELS];

					// envia no canal CORRESPONDENTE!!! o valor
					// envie-se o tempo
					value[0] = new PhysicsValue(PhysicsValFactory.fromFloat(time), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getMultiplier());
					// envie-se a posicao
					value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) Math.sqrt(state[0] * state[0]
							+ state[2] * state[2])), getAcquisitionHeader().getChannelsConfig(1).getSelectedScale()
							.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(1).getSelectedScale()
							.getMultiplier());
					// envie-se a velocidade
					value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) Math.sqrt(state[1] * state[1]
							+ state[3] * state[3])), getAcquisitionHeader().getChannelsConfig(2).getSelectedScale()
							.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(2).getSelectedScale()
							.getMultiplier());
					// envie-se x
					value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[0]), getAcquisitionHeader()
							.getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(2).getSelectedScale().getMultiplier());
					// envie-se y
					value[4] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[2]), getAcquisitionHeader()
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

	}

	@Override
	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}
}
