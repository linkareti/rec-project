/*
 * MovProjDataProducer.java
 *
 * Created on February 13, 2004, 12:13 PM
 */

package pt.utl.ist.elab.driver.vmovproj;

/**
 *
 * @author  nomead
 */

import org.opensourcephysics.numerics.Euler;
import org.opensourcephysics.numerics.ODE;
import org.opensourcephysics.numerics.ODESolver;
import org.opensourcephysics.numerics.RK4;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class MovProjDataProducer extends VirtualBaseDataSource implements ODE/*
																			 * ,
																			 * Runnable
																			 */
{
	// O numero de canais(de dados) que existem!
	private final int NUM_CHANNELS = 12;

	private int tbs = 100;
	private final int nSamples;

	private final double x, y, z;
	private final double[] omega;
	private final double[] velc;

	private final double s0;
	private final double raio, mass;
	private final double g;
	private double dt;

	private final double dragCoef1, dragCoef2, densidadeFluid;

	private final boolean gVariavel, atritoVariavel;
	private boolean rMod = false, vMod = false;

	private final double[] state;
	private ODESolver metodoNumerico;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;

	public MovProjDataProducer(final VirtualBaseDriver driver, final float x, final float y, final float z,
			final float vel, final float velTheta, final float velPhi, final float spin, final float spinTheta,
			final float spinPhi, final float radius, final float mass, final float dragCoef1, final float dragCoef2,
			final float densL, final float s0, final float g, final int dt, final boolean odeType, final boolean gType,
			final boolean dragType, final boolean rMod, final boolean vMod, final int tbs, final int nSamples) {
		System.out.println("Creating data producer...");
		this.driver = driver;
		this.nSamples = nSamples;
		this.tbs = tbs;

		this.x = x;
		this.y = y;
		this.z = z;

		raio = radius;
		this.mass = mass;

		System.out.println("A");

		velc = new double[] { vel * Math.cos(velTheta) * Math.sin(velPhi), vel * Math.sin(velTheta) * Math.sin(velPhi),
				vel * Math.cos(velPhi) };

		System.out.println("B");
		this.dragCoef1 = dragCoef1;
		this.dragCoef2 = dragCoef2;
		densidadeFluid = densL;

		this.s0 = s0;
		System.out.println("C");
		omega = new double[] { spin * Math.cos(spinTheta) * Math.sin(spinPhi),
				spin * Math.sin(spinTheta) * Math.sin(spinPhi), spin * Math.cos(spinPhi) };

		this.g = g;
		this.dt = dt;

		atritoVariavel = dragType;
		gVariavel = gType;

		this.rMod = rMod;
		this.vMod = vMod;

		System.out.println("D");
		state = new double[] { x, velc[0], y, velc[1], z, velc[2], 0 };

		if (!odeType) {
			metodoNumerico = new Euler(this);
		} else {
			metodoNumerico = new RK4(this);
		}

		metodoNumerico.initialize(dt / 1000d);
	}

	// TESTE
	/*
	 * public void
	 * start(pt.utl.ist.elab.virtual.client.movproj.displays.Animation an){
	 * this.an = an;
	 * 
	 * animaThread = new Thread(this); animaThread.start(); }
	 */

	// TESTE
	/*
	 * public void
	 * startChart(pt.utl.ist.elab.virtual.client.movproj.displays.MultipleChart
	 * ch){ this.ch = ch;
	 * 
	 * animaThread = new Thread(this); animaThread.start(); }
	 */

	// TESTE
	/*
	 * pt.utl.ist.elab.virtual.client.movproj.displays.MultipleChart ch;
	 * pt.utl.ist.elab.virtual.client.movproj.displays.Animation an; private
	 * Thread animaThread; public void run() { int currentSample = 0; double tmp
	 * = 0; boolean stop = false; while (animaThread == Thread.currentThread()
	 * && currentSample < nSamples && !stop){
	 * 
	 * if (state[2]+state[3]*(double)dt/1000d <= 0){ dt = (double)dt/10d;
	 * metodoNumerico.setStepSize((double)dt/1000d); }
	 * 
	 * metodoNumerico.step();
	 * 
	 * if (state[2] <= 0){ stop = true; System.out.println(state[2]); }
	 * 
	 * if ((int)Math.round((state[6]-tmp)*1000) >= tbs || stop){ if (ch == null)
	 * an
	 * .moves(state[0],state[2],state[4],state[1],state[3],state[5],drag(state[
	 * 1]) + magnus()*productoVectorial(state[1],state[3],state[5],
	 * omega[0],omega[1],omega[2])[0], drag(state[3]) - g() +
	 * magnus()*productoVectorial(state[1],state[3],state[5],
	 * omega[0],omega[1],omega[2])[1],drag(state[5]) +
	 * magnus()*productoVectorial(state[1],state[3],state[5],
	 * omega[0],omega[1],omega[2])[2],state[6]); else { ch.append(state); } tmp
	 * = state[6]; currentSample++; try{ animaThread.sleep(tbs); } catch
	 * (InterruptedException e){} } if (stop){ if (ch == null)
	 * an.dataModelEnded(); else ch.dataModelEnded(); } } }
	 */

	private class ProducerThread extends Thread {
		private int currentSample = 0;

		@Override
		public void run() {
			try {
				Thread.sleep(1000);

				PhysicsValue[] value;
				double tmp = 0;

				while (currentSample < nSamples && !stopped) {

					if (state[2] + state[3] * dt / 1000d <= 0) {
						dt = dt / 10d;
						metodoNumerico.setStepSize(dt / 1000d);
					}
					metodoNumerico.step();
					if (state[2] <= 0) {
						stopped = true;
					}
					if ((int) Math.round((state[6] - tmp) * 1000) >= tbs || stopped) {
						value = new PhysicsValue[NUM_CHANNELS];

						value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[0]),
								getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier());
						value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[2]),
								getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier());
						value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[4]),
								getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getMultiplier());
						value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[1]),
								getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getMultiplier());
						value[4] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[3]),
								getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getMultiplier());
						value[5] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[5]),
								getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getMultiplier());
						value[6] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[6]),
								getAcquisitionHeader().getChannelsConfig(6).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(6).getSelectedScale().getMultiplier());

						value[7] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (drag(state[1]) + magnus()
								* productoVectorial(state[1], state[3], state[5], omega[0], omega[1], omega[2])[0])),
								getAcquisitionHeader().getChannelsConfig(7).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(7).getSelectedScale().getMultiplier());
						value[8] = new PhysicsValue(
								PhysicsValFactory
										.fromFloat((float) (drag(state[3]) - g() + magnus()
												* productoVectorial(state[1], state[3], state[5], omega[0], omega[1],
														omega[2])[1])),
								getAcquisitionHeader().getChannelsConfig(8).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(8).getSelectedScale().getMultiplier());
						value[9] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (drag(state[5]) + magnus()
								* productoVectorial(state[1], state[3], state[5], omega[0], omega[1], omega[2])[2])),
								getAcquisitionHeader().getChannelsConfig(9).getSelectedScale().getDefaultErrorValue(),
								getAcquisitionHeader().getChannelsConfig(9).getSelectedScale().getMultiplier());

						if (rMod) {
							value[10] = new PhysicsValue(PhysicsValFactory.fromFloat((float) Math.sqrt(Math.pow(
									state[0], 2) + Math.pow(state[2], 2) + Math.pow(state[4], 2))),
									getAcquisitionHeader().getChannelsConfig(10).getSelectedScale()
											.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(10)
											.getSelectedScale().getMultiplier());
						}
						if (vMod) {
							value[11] = new PhysicsValue(PhysicsValFactory.fromFloat((float) Math.sqrt(Math.pow(
									state[1], 2) + Math.pow(state[3], 2) + Math.pow(state[5], 2))),
									getAcquisitionHeader().getChannelsConfig(11).getSelectedScale()
											.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(11)
											.getSelectedScale().getMultiplier());
						}

						addDataRow(value);
						tmp = state[6];
						Thread.sleep(tbs);
						currentSample++;
					}
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

	@Override
	public void getRate(final double[] state, final double[] rate) {
		rate[0] = state[1]; // x
		rate[1] = drag(state[1]) + magnus()
				* productoVectorial(state[1], state[3], state[5], omega[0], omega[1], omega[2])[0];

		rate[2] = state[3]; // y
		rate[3] = drag(state[3]) - g() + magnus()
				* productoVectorial(state[1], state[3], state[5], omega[0], omega[1], omega[2])[1];

		rate[4] = state[5]; // z
		rate[5] = drag(state[5]) + magnus()
				* productoVectorial(state[1], state[3], state[5], omega[0], omega[1], omega[2])[2];

		rate[6] = 1; // tempo
	}

	@Override
	public double[] getState() {
		return state;
	}

	// Aceleracao gravitica
	private double g() {
		if (gVariavel) {
			return 5.9742e24 * 6.672e-11 / Math.pow(6.378e6 + state[2], 2);
		} else {
			return g;
		}
	}

	// Factor constante da aceleracao de Magnus
	private double magnus() {
		return -s0 / (2 * Math.PI);
	}

	// Aceleracao de atrito (variavel ou invariavel [atritoInv])
	private double drag(final double _v) {
		double dragConst;

		if (moduloVectorial(state[1], state[3], state[5]) > 14 && atritoVariavel) {
			dragConst = dragCoef2 / moduloVectorial(state[1], state[3], state[5]);
		} else {
			dragConst = dragCoef1;
		}

		return -dragConst * Math.PI * raio * raio * densidadeFluid * moduloVectorial(state[1], state[3], state[5]) * _v
				/ (2 * mass);
	}

	// Modulo Vectorial generico (3)
	private double moduloVectorial(final double _x, final double _y, final double _z) {
		return Math.sqrt(_x * _x + _y * _y + _z * _z);
	}

	// Producto Vectorial generico (3)
	private double[] productoVectorial(final double _x1, final double _y1, final double _z1, final double _x2,
			final double _y2, final double _z2) {
		return new double[] { _y1 * _z2 - _z1 * _y2, -(_x1 * _z2 - _z1 * _x2), _x1 * _y2 - _y1 * _x2 };
	}

}
