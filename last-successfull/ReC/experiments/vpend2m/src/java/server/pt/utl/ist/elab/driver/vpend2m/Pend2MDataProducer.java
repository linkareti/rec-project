/*
 * Pend2MDataProducer.java
 *
 * Created on 27 de Fevereiro de 2005, 12:13 PM
 */

package pt.utl.ist.elab.driver.vpend2m;

/**
 *
 * @author  Antonio J. R. Figueiredo
 *          Last Review : 6/04/2005
 */

import org.opensourcephysics.numerics.ODE;
import org.opensourcephysics.numerics.ODESolver;
import org.opensourcephysics.numerics.RK4;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class Pend2MDataProducer extends VirtualBaseDataSource implements ODE {

	private final int NUM_CHANNELS = 7;

	private int tbs = 1000;
	private final int nSamples;

	private final double l1;
	private final double l2;
	private final double m1;
	private final double m2;
	private final double w;
	private final double fase;
	private final double a;

	private final double g;

	private final double[] state;
	private final ODESolver rk4;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;

	public Pend2MDataProducer(final VirtualBaseDriver driver, final float theta, final float phi, final float thetaDot,
			final float phiDot, final float l1, final float l2, final float m1, final float m2, final float w,
			final float fase, final float a, final float g, final int tbs, final int nSamples) {
		this.driver = driver;
		this.nSamples = nSamples;
		this.tbs = tbs;

		this.l1 = l1;
		this.l2 = l2;
		this.m1 = m1;
		this.m2 = m2;
		this.w = w;
		this.fase = fase;
		this.a = a;
		this.g = g;

		state = new double[] { theta, thetaDot, phi, phiDot, 0 };

		rk4 = new RK4(this);
		rk4.initialize(1e-3);
	}

	private class ProducerThread extends Thread {
		private int currentSample = 0;

		@Override
		public void run() {
			try {
				Thread.sleep(1000);

				PhysicsValue[] value;
				int counter = 0;

				while (currentSample < nSamples && !stopped) {

					rk4.step();
					if (counter % tbs == 0) {
						value = new PhysicsValue[NUM_CHANNELS];

						value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[0]),
								getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier());
						value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[2]),
								getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier());
						value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[1]),
								getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getMultiplier());
						value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[3]),
								getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getMultiplier());
						value[4] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[4]),
								getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getMultiplier());
						value[5] = new PhysicsValue(
								PhysicsValFactory.fromFloat((float) ((-a * m2 * w * w * Math.cos(state[2])
										* Math.cos(state[2] - state[0]) * Math.sin(fase + state[4] * w) + a * (m1 + m2)
										* w * w * Math.cos(state[0]) * Math.sin(fase + state[4] * w) + g * m2
										* Math.cos(state[2] - state[0]) * Math.sin(state[2]) - g * (m1 + m2)
										* Math.sin(state[0]) + m2
										* Math.sin(state[2] - state[0])
										* (l2 * state[3] * state[3] + l1 * Math.cos(state[2] - state[0]) * state[1]
												* state[1])) / (l1 * (m1 + m2 - m2
										* Math.pow(Math.cos(state[2] - state[0]), 2))))), getAcquisitionHeader()
										.getChannelsConfig(5).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getMultiplier());
						value[6] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (-(Math
								.sin(state[2] - state[0]) * (l2 * m2 * Math.cos(state[2] - state[0]) * state[3]
								* state[3] + (m1 + m2)
								* (g * Math.cos(state[0]) + a * w * w * Math.sin(fase + state[4] * w)
										* Math.sin(state[0]) + l1 * state[1] * state[1]))) / (l2 * (m1 + m2 - m2
								* Math.pow(Math.cos(state[2] - state[0]), 2))))), getAcquisitionHeader()
								.getChannelsConfig(6).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
								.getChannelsConfig(6).getSelectedScale().getMultiplier());

						addDataRow(value);

						Thread.sleep(tbs);
						currentSample++;
					}
					counter++;
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

	@Override
	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}

	@Override
	public void getRate(final double[] state, final double[] rate) {
		// theta
		rate[0] = state[1];
		rate[1] = (-a * m2 * w * w * Math.cos(state[2]) * Math.cos(state[2] - state[0]) * Math.sin(fase + state[4] * w)
				+ a * (m1 + m2) * w * w * Math.cos(state[0]) * Math.sin(fase + state[4] * w) + g * m2
				* Math.cos(state[2] - state[0]) * Math.sin(state[2]) - g * (m1 + m2) * Math.sin(state[0]) + m2
				* Math.sin(state[2] - state[0])
				* (l2 * state[3] * state[3] + l1 * Math.cos(state[2] - state[0]) * state[1] * state[1]))
				/ (l1 * (m1 + m2 - m2 * Math.pow(Math.cos(state[2] - state[0]), 2)));

		// phi
		rate[2] = state[3];
		rate[3] = -(Math.sin(state[2] - state[0]) * (l2 * m2 * Math.cos(state[2] - state[0]) * state[3] * state[3] + (m1 + m2)
				* (g * Math.cos(state[0]) + a * w * w * Math.sin(fase + state[4] * w) * Math.sin(state[0]) + l1
						* state[1] * state[1])))
				/ (l2 * (m1 + m2 - m2 * Math.pow(Math.cos(state[2] - state[0]), 2)));

		// t
		rate[4] = 1;
	}

	@Override
	public double[] getState() {
		return state;
	}

}
