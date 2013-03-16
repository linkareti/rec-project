/*
 * PaschenDataProducer.java
 *
 * Created on 19 de Fevereiro de 2013
 */

package pt.utl.ist.elab.driver.paschen;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

/**
 * 
 * @author jloureiro
 */
public class PaschenDataProducer extends VirtualBaseDataSource implements Runnable {

	private final double volt_ini;
	private final double volt_fin;
	private final double volt_step;
	private final double press_set;
		
	private final double[] state; // voltage, current, pressure, index

	private final int NUM_CHANNELS = 4;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;

	public PaschenDataProducer(final VirtualBaseDriver driver, final double _volt_ini, 
	final double _volt_fin, final double _volt_step, final double _press_set) {
		this.driver = driver;

		volt_ini = _volt_ini;
		volt_fin = _volt_fin;
		volt_step = _volt_step;
		press_set = _press_set;

		state = new double[] { volt_ini, 0 , press_set, 0};
	}

	public void step() {
		state[0] += volt_step;
		state[1] = state[0] < press_set*10 ? 0 : 2000; 
//		state[1] += volt_step*volt_step/10000.;
		state[2] = 1000*(press_set/100 + (Math.random()-0.5)/100);
		state[3] += 1;

	}


	//private Thread animaThread;

	@Override
	public void run() {
		while (!stopped) {
			step();
			// an.move(state[0], state[2], state[1], state[3], state[5]);
			System.out.println(state[4]);
			try {
				Thread.sleep(100);
			} catch (final InterruptedException e) {
			}
		}
	}

	private class ProducerThread extends Thread {

		@Override
		public void run() {
			try {
				Thread.sleep(100);

				PhysicsValue[] value;

				while (!stopped && state[0] <= volt_fin) {

					step();
					value = new PhysicsValue[NUM_CHANNELS];

					value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[0]), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getMultiplier());
					value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[1]), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getMultiplier());
					value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[2]), getAcquisitionHeader()
							.getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(2).getSelectedScale().getMultiplier());
					value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[3]), getAcquisitionHeader()
							.getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(3).getSelectedScale().getMultiplier());

					addDataRow(value);
					Thread.sleep(100);

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
		setDataSourceStoped();
	}

	@Override
	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}

}