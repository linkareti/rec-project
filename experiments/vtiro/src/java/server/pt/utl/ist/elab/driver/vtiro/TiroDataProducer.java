/*
 * TiroDataProducer.java
 *
 * Created on 16 de Fevereiro de 2005, 23:36
 */

package pt.utl.ist.elab.driver.vtiro;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

/**
 * 
 * @author nomead
 */
public class TiroDataProducer extends VirtualBaseDataSource implements Runnable {

	private final double g;
	private final double w, h;

	private final double[] state; // theta, dtheta/dt, x, dx/dt, t

	private final int NUM_CHANNELS = 7;

	private double dt = .1;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;

	public TiroDataProducer(final VirtualBaseDriver driver, final double _w, final double _h, final double v,
			final double theta, final double _g) {
		this.driver = driver;

		g = _g;
		w = _w;
		h = _h;

		state = new double[] { 0, v * Math.cos(theta), 0, v * Math.sin(theta), 0, h, 0 };
	}

	public void step() {
		state[4] += dt;
		state[0] += state[1] * dt;

		state[2] += state[3] * dt - g * dt * dt / 2;
		state[3] -= g * dt;

		state[5] += state[6] * dt - g * dt * dt / 2;
		state[6] -= g * dt;
	}

	// // TESTE
	// public void start(pt.utl.ist.elab.client.vtiro.displays.Animation an) {
	// this.an = an;
	// animaThread = new Thread(this);
	// animaThread.start();
	// }
	//
	// // TESTE
	// pt.utl.ist.elab.client.vtiro.displays.Animation an;
	private Thread animaThread;

	@Override
	public void run() {
		while (animaThread == Thread.currentThread() && !stopped && state[0] < w) {
			step();
			// an.move(state[0], state[2], state[1], state[3], state[5]);
			System.out.println(state[4]);
			try {
				Thread.sleep(Math.round(dt * 1000));
			} catch (final InterruptedException e) {
			}
		}
	}

	private class ProducerThread extends Thread {
		private int currentSample = 0;

		@Override
		public void run() {
			try {
				Thread.sleep(1000);

				PhysicsValue[] value;

				while (!stopped && state[0] < w && currentSample < 3000) {

					if (state[0] + state[1] * dt > w) {
						dt /= 10;
					}
					step();
					value = new PhysicsValue[NUM_CHANNELS];

					value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[0]), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getMultiplier());
					value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[2]), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getMultiplier());
					value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[1]), getAcquisitionHeader()
							.getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(2).getSelectedScale().getMultiplier());
					value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[3]), getAcquisitionHeader()
							.getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(3).getSelectedScale().getMultiplier());
					value[4] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[5]), getAcquisitionHeader()
							.getChannelsConfig(4).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(4).getSelectedScale().getMultiplier());
					value[5] = new PhysicsValue(PhysicsValFactory.fromFloat((float) Math.sqrt(Math.pow(state[0] - w, 2)
							+ Math.pow(state[2] - state[5], 2))), getAcquisitionHeader().getChannelsConfig(5)
							.getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(5)
							.getSelectedScale().getMultiplier());
					value[6] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[4]), getAcquisitionHeader()
							.getChannelsConfig(6).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(6).getSelectedScale().getMultiplier());

					System.out.println("**** ADDING ROW "+currentSample);
					
					addDataRow(value);
					System.out.println("**** SLEEPING FOR "+Math.round(dt * 1000));
					Thread.sleep(Math.round(dt * 1000));
					
					currentSample++;

				}

				System.out.println("***** ENDING PRODUCTION OF DATA ");
				endProduction();

				System.out.println("***** STOPING HARDWARE ");
				driver.stopVirtualHardware();
			} catch (final Throwable t) {
				t.printStackTrace(System.out);
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
		setDataSourceStoped();
	}

	@Override
	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}

}