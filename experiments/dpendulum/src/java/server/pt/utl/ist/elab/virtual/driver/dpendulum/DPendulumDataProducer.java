/*
 * DataProducer.java
 *
 * Created on February 13, 2004, 12:13 PM
 */

package pt.utl.ist.elab.virtual.driver.dpendulum;

/**
 *
 * @author André Neto - LEFT - IST
 */

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;
import com.linkare.rec.impl.driver.BaseDataSource;

public class DPendulumDataProducer extends BaseDataSource {

	private static final float G = 9.8f;
	private static final int N = 4; /* number of equations to be integrated */
	private float ini_theta1_deg = 0;
	private float ini_theta2_deg = 0;
	private float ini_omega1 = 0;
	private float ini_omega2 = 0;
	private float length1 = 0;
	private float length2 = 0;
	private float mass1 = 0;
	private float mass2 = 0;
	private int tbs = 100;
	private int nSamples = 10;

	private DPendulumDriver dpd;

	/** Creates a new instance of DataProducer */
	public DPendulumDataProducer(DPendulumDriver dpd, float ini_theta1_deg, float ini_theta2_deg, float ini_omega1,
			float ini_omega2, float length1, float length2, float mass1, float mass2, int tbs, int nSamples) {
		this.dpd = dpd;
		this.ini_theta1_deg = ini_theta1_deg;
		this.ini_theta2_deg = ini_theta2_deg;
		this.ini_omega1 = ini_omega1;
		this.ini_omega2 = ini_omega2;
		this.length1 = length1;
		this.length2 = length2;
		this.mass1 = mass1;
		this.mass2 = mass2;
		this.tbs = tbs;
		this.nSamples = nSamples;
	}

	private class ProducerThread extends Thread {
		private int cSample = 0;

		private float theta1 = (float) (ini_theta1_deg * Math.PI / 180f);
		private float omega1 = ini_omega1;
		private float theta2 = (float) (ini_theta2_deg * Math.PI / 180f);
		private float omega2 = ini_omega2;
		private float x = 0;
		private float[] yin = null;
		private float[] yout = null;
		float h = 0;

		public ProducerThread() {
			yin = new float[N];
			h = tbs / 1000f; /* stepsize for integration */
		}

		public void run() {
			try {
				sleep(1000);
			} catch (InterruptedException ie) {
			}
			while (cSample < nSamples && running) {
				float xin = x;
				yin[0] = theta1;
				yin[1] = omega1;
				yin[2] = theta2;
				yin[3] = omega2;/* number of equations to be integrated */
				yout = runge_kutta(xin, yin, N, h);
				theta1 = yout[0];
				omega1 = yout[1];
				theta2 = yout[2];
				omega2 = yout[3];

				x += tbs / 1000f;

				PhysicsValue[] value = new PhysicsValue[5];
				value[0] = new PhysicsValue(PhysicsValFactory.fromFloat(x), getAcquisitionHeader().getChannelsConfig(0)
						.getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(0)
						.getSelectedScale().getMultiplier());
				value[1] = new PhysicsValue(PhysicsValFactory.fromFloat(theta1), getAcquisitionHeader()
						.getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
						.getChannelsConfig(1).getSelectedScale().getMultiplier());
				value[2] = new PhysicsValue(PhysicsValFactory.fromFloat(theta2), getAcquisitionHeader()
						.getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
						.getChannelsConfig(2).getSelectedScale().getMultiplier());
				value[3] = new PhysicsValue(PhysicsValFactory.fromFloat(omega1), getAcquisitionHeader()
						.getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
						.getChannelsConfig(3).getSelectedScale().getMultiplier());
				value[4] = new PhysicsValue(PhysicsValFactory.fromFloat(omega2), getAcquisitionHeader()
						.getChannelsConfig(4).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
						.getChannelsConfig(4).getSelectedScale().getMultiplier());
				addDataRow(value);

				try {
					sleep(tbs);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}

				cSample++;
			}

			try {
				join(100);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			dpd.setStoping();
			dpd.setStoped();
			endProduction();
		}
	}

	private float[] runge_kutta(float x, float[] y, int n, float h) {
		int i;
		float[] yout = new float[n];
		float[] dydx = null;
		float[] dydxt = null;
		float[] vt = new float[n];
		float[] yt = new float[n];
		float[] k1 = new float[n];
		float[] k2 = new float[n];
		float[] k3 = new float[n];
		float[] k4 = new float[n];
		float hh;
		float xh;

		hh = 0.5f * h;
		xh = x + hh;

		dydx = derivs(x, y, n); /* first step */
		for (i = 0; i < n; i++) {
			k1[i] = h * dydx[i];/* number of equations to be integrated */
			yt[i] = y[i] + 0.5f * k1[i];
		}

		dydxt = derivs(xh, yt, n); /* second step */
		for (i = 0; i < n; i++) {
			k2[i] = h * dydxt[i];
			yt[i] = y[i] + 0.5f * k2[i];
		}

		dydxt = derivs(xh, yt, n); /* third step */
		for (i = 0; i < n; i++) {
			k3[i] = h * dydxt[i];
			yt[i] = y[i] + k3[i];
		}

		dydxt = derivs(x + h, yt, n); /* fourth step */
		for (i = 0; i < n; i++) {
			k4[i] = h * dydxt[i];
			yout[i] = y[i] + k1[i] / 6f + k2[i] / 3f + k3[i] / 3f + k4[i] / 6f;
		}

		/* number of equations to be integrated */return yout;
	}

	/* Function derivs(x,y,dydx) fills array of derivatives dydx at x */
	private float[] derivs(float x, float[] y, int n) {
		float[] dydx = new float[n];
		float den1 = 0f;
		float den2 = 0f;
		float del = 0f;
		float a = 0.5f;

		dydx[0] = y[1];

		del = y[2] - y[0];
		den1 = (float) ((mass1 + mass2) * length1 - mass2 * length1 * Math.cos(del) * Math.cos(del));
		dydx[1] = (float) ((mass2 * length1 * y[1] * y[1] * Math.sin(del) * Math.cos(del) + mass2 * G * Math.sin(y[2])
				* Math.cos(del) + mass2 * length2 * y[3] * y[3] * Math.sin(del) - (mass1 + mass2) * G * Math.sin(y[0])) / den1);

		dydx[2] = y[3];

		den2 = (float) ((length2 / length1) * den1);
		dydx[3] = (float) ((-mass2 * length2 * y[3] * y[3] * Math.sin(del) * Math.cos(del) + (mass1 + mass2) * G
				* Math.sin(y[0]) * Math.cos(del) - (mass1 + mass2) * length1 * y[1] * y[1] * Math.sin(del) - (mass1 + mass2)
				* G * Math.sin(y[2])) / den2);

		return dydx;
	}

	private boolean running = false;

	public void startProduction() {
		running = true;
		new ProducerThread().start();
	}

	public void shutdown() {
		running = false;
	}

	public void endProduction() {
		running = false;
		setDataSourceEnded();
	}

	public static void main(String args[]) {
		DPendulumDataProducer dp = new DPendulumDataProducer(null, 90, 90, 10, 0, 0.5f, 1.5f, 0.3f, 0.5f, 10, 10000);
		dp.startProduction();
	}

	public void stopNow() {
		running = false;
		setDataSourceStoped();
	}

}
