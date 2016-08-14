/*
 * CGDataProducer.java
 *
 * Created on February 13, 2004, 12:13 PM
 */

package pt.utl.ist.elab.driver.vcg;

/**
 *
 * @author  nomead
 */

import org.opensourcephysics.numerics.ODE;
import org.opensourcephysics.numerics.ODESolver;
import org.opensourcephysics.numerics.RK45;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class CGDataProducer extends VirtualBaseDataSource implements ODE {
	// O numero de canais(de dados) que existem!
	private final int NUM_CHANNELS = 3;

	// random
	private final double c;
	private final double k; // depende de c, isto e: K*K/(4*I*I) < C/I, I
							// momento de
	// inercia
	private final double g;

	private final double d;// = 5e-2;
	private final double s0;// = 4.65e-2;
	private final double i;// = 7.625e-5;

	private final boolean expGType;
	private final double angInit;
	private final double[] mm;
	private final double[] mM;

	private int tbs = 100;
	private int nSamples = 10;

	private final double[] state; // ang, dang/dt, t
	private final ODESolver rk45;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;

	public CGDataProducer(final VirtualBaseDriver driver, final boolean expGType, final int angInit, final double s0,
			final double d, final double mm0, final double mm1, final double mM0, final double mM1,
			final double[] consts, final int tbs, final int nSamples) {

		this.driver = driver;
		this.expGType = expGType;
		this.angInit = angInit * Math.PI / 180;
		this.s0 = s0;
		this.d = d;
		mm = new double[] { mm0, mm1 };
		mM = new double[] { mM0, mM1 };

		i = (mm[0] + mm[1]) * d * d + 2 * Math.pow(d - 6.6e-3, 3) * Math.pow(1.25e-3, 2) * Math.PI * 2700 / 3 + 2
				* Math.pow(6.6e-3, 2) * (mm[0] + mm[1]) / 5;

		this.tbs = tbs;
		this.nSamples = nSamples;

		c = consts[0];
		k = consts[1];
		g = consts[2];

		state = new double[] { this.angInit, 0, 0 };

		rk45 = new RK45(this);
		rk45.initialize(tbs / 10);
	}

	@Override
	public void getRate(final double[] state, final double[] rate) {
		final double ljj = Math.pow(d - d * Math.cos(state[0]), 2) + Math.pow(s0 - d * Math.sin(state[0]), 2);
		final double lji = Math.pow(d + d * Math.cos(state[0]), 2) + Math.pow(s0 + d * Math.sin(state[0]), 2);
		final double aux = (s0 - d * Math.tan(state[0])) * Math.cos(state[0]);

		rate[0] = state[1];

		if (expGType) {
			rate[1] = (d * g * mm[0] * mM[0] * aux / (ljj * Math.sqrt(ljj)) + d * g * mm[1] * mM[1] * aux
					/ (ljj * Math.sqrt(ljj)) + d * g * mm[0] * mM[1] * aux / (lji * Math.sqrt(lji)) + d * g * mm[1]
					* mM[0] * aux / (lji * Math.sqrt(lji)) - c * state[0] - k * state[1])
					/ i;
		} else {
			rate[1] = (-c * state[0] - k * state[1]) / i;
		}

		rate[2] = 1;
	}

	@Override
	public double[] getState() {
		return state;
	}

	// o processo que nos vai simular e criar as amostras para enviar ao
	// cliente!
	private class ProducerThread extends Thread {
		private int currentSample = 0;

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
					value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[2]), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getMultiplier());
					value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[0]), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getMultiplier());
					value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[1]), getAcquisitionHeader()
							.getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(2).getSelectedScale().getMultiplier());

					addDataRow(value);

					rk45.setStepSize(tbs / 10);
					rk45.step();
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

	@Override
	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}
}
