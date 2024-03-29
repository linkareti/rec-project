/*
 * FERMAPDataProducer.java
 *
 * Created on February 13, 2004, 12:13 PM
 */

package pt.utl.ist.elab.driver.vfermap;

/**
 *
 * @author  Antonio Jose Rodrigues Figueiredo
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

import pt.utl.ist.elab.common.virtual.utils.ByteUtil;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class FERMAPDataProducer extends VirtualBaseDataSource implements Runnable {
	// O numero de canais(de dados) que existem!
	private final int NUM_CHANNELS = 6;

	private int tbs;
	private final int nSamples;

	private double m;
	private double psi, dPsi;
	private double uMapa, dUMapa;
	private double pcor;
	private int nPsi, nUMapa, iter;

	private byte pixSize = 1;
	private int w = 0, h = 0;
	private boolean staticImg;

	private double psiDot, length, force;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;

	private double uMax = 0;

	private double[] state;
	private double dt, tempDt;
	private double tol;
	private double wf, wAmp, d;
	private double bolaRadius;

	// ANIMA
	public FERMAPDataProducer(final VirtualBaseDriver driver, final double x, final double v, final double psi,
			final double wf, final double wAmp, final double d, final int tbs, final int nSamples) {
		this.driver = driver;
		this.nSamples = nSamples;
		this.tbs = tbs;

		state = new double[3];
		state[0] = x;
		state[1] = v;
		state[2] = psi - 3 * Math.PI / 2;

		this.wf = wf;
		this.wAmp = wAmp;
		this.d = d;
		bolaRadius = .5;

		dt = tempDt = 1e-3;
		tol = 1e-4;
	}

	// HIST + MAP
	public FERMAPDataProducer(final VirtualBaseDriver driver, final float m, final float psi, final float uMapa,
			final int iter, final float pcor, final int w, final int h, final byte pixSize, final double uMax,
			final boolean staticImg) {
		this.driver = driver;
		nSamples = 1;

		this.m = m;
		this.psi = psi;
		dPsi = 0;
		this.uMapa = uMapa;
		dUMapa = 0;
		this.pcor = pcor;
		nPsi = 1;
		nUMapa = 1;
		this.iter = iter;

		this.w = w;
		this.h = h;
		this.pixSize = pixSize;
		this.uMax = uMax;
		this.staticImg = staticImg;
	}

	// JUST MAP
	public FERMAPDataProducer(final VirtualBaseDriver driver, final float m, final float psi, final int nPsi,
			final float dPsi, final float uMapa, final int nUMapa, final float dUMapa, final int iter,
			final float pcor, final int w, final int h, final byte pixSize, final double uMax, final boolean staticImg) {
		this.driver = driver;
		nSamples = 1;

		this.m = m;
		this.psi = psi;
		this.dPsi = dPsi;
		this.uMapa = uMapa;
		this.dUMapa = dUMapa;
		this.pcor = pcor;
		this.nPsi = nPsi;
		this.nUMapa = nUMapa;
		this.iter = iter;

		this.w = w;
		this.h = h;
		this.pixSize = pixSize;
		this.uMax = uMax;
		this.staticImg = staticImg;
	}

	// TESTE
	public byte[] getMapaPixs() {
		if (uMax == 0) {
			setUMax();
		}

		final BufferedImage tempImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_INDEXED);
		final Graphics g = tempImg.getGraphics();

		for (int t = 0; t < nPsi; t++) {
			for (int i = 0; i < nUMapa; i++) {
				final float c1 = (float) Math.abs((uMapa + psi) % 1);
				final float c2 = (float) Math.abs((uMapa + pcor * Math.sin(psi)) % 1);
				final float c3 = Math.abs((c1 + c2) % 1);

				g.setColor(new java.awt.Color(c1, c2, c3));

				for (int j = 0; j < iter; j++) {

					uMapa = Math.abs(uMapa + Math.sin(psi));
					psi = (psi + m / uMapa) % (2 * Math.PI);

					if (psi < 0) {
						psi += 2 * Math.PI;
					}

					g.drawOval((int) Math.round(psi * w / (2 * Math.PI)), h - (int) Math.round(uMapa * h / uMax),
							pixSize, pixSize);

				}
				uMapa += dUMapa;
			}
			psi += dPsi;
		}

		g.dispose();

		final PixelGrabber pg = new PixelGrabber(tempImg, 0, 0, w, h, false);

		try {
			pg.grabPixels();
		} catch (final InterruptedException e) {
		}

		return (byte[]) pg.getPixels();
	}

	// TESTE
	public byte[] getIMapaData() {
		final float[] uMapaData = new float[iter];
		int dataCounter = 0;

		for (int j = 0; j < iter; j++) {

			uMapa = Math.abs(uMapa + Math.sin(psi));
			psi = (psi + m / uMapa) % (2 * Math.PI);

			if (psi < 0) {
				psi += 2 * Math.PI;
			}

			uMapaData[dataCounter++] = (float) uMapa;
		}
		return ByteUtil.floatArrayToByteArray(uMapaData);
	}

	// TESTE
	private Thread animaThread;

	// FIXME - driver should never depend on client side. should be fixed as
	// soon as possible
	// private pt.utl.ist.elab.client.vfermap.displays.Animation ferAn;
	//
	// // TESTE
	// public void startAnima(pt.utl.ist.elab.client.vfermap.displays.Animation
	// ferAn) {
	// this.ferAn = ferAn;
	// animaThread = new Thread(this);
	// animaThread.start();
	// }

	// TESTE
	// FIXME - driver should never depend on client side. should be fixed as
	// soon as possible
	// private pt.utl.ist.elab.client.vfermap.displays.FERHistogram ferHist;
	//
	// public void
	// startHist(pt.utl.ist.elab.client.vfermap.displays.FERHistogram ferHist) {
	// this.ferHist = ferHist;
	// animaThread = new Thread(this);
	// animaThread.start();
	// }

	// TESTE
	// FIXME - driver should never depend on client side. should be fixed as
	// soon as possible
	// private pt.utl.ist.elab.client.vfermap.displays.FERMAPImage ferIm;

	// public void startM(pt.utl.ist.elab.client.vfermap.displays.FERMAPImage
	// ferIm) {
	// this.ferIm = ferIm;
	// animaThread = new Thread(this);
	// animaThread.start();
	// }

	// TESTE
	public byte[] getMapaData() {
		int dataCounter = 0;
		final float[] mData = new float[nPsi * nUMapa * iter * 2];

		for (int t = 0; t < nPsi; t++) {
			for (int i = 0; i < nUMapa; i++) {
				for (int j = 0; j < iter; j++) {
					uMapa = Math.abs(uMapa + Math.sin(psi));
					psi = (psi + m / uMapa) % (2 * Math.PI);

					if (psi < 0) {
						psi += 2 * Math.PI;
					}

					mData[dataCounter++] = (float) psi;
					mData[dataCounter++] = (float) uMapa;

				}
				uMapa += dUMapa;
			}
			psi += dPsi;
		}

		return ByteUtil.floatArrayToByteArray(mData);
	}

	// TESTE
	public double getUMax() {
		return uMax;
	}

	// TESTE
	private final boolean stop = false;

	// FIXME - driver should never depend on client side. should be fixed as
	// soon as possible
	@Override
	public void run() {
		final int currentSample = 0;
		final int counter = 0;
		//
		// while (animaThread == Thread.currentThread() && currentSample <
		// nSamples) {
		//
		// if (tbs != 0) {
		//
		// if (state[0] + state[1] * dt >= d - wAmp || state[0] + state[1] *
		// tempDt >= d - wAmp) {
		//
		// while (state[0] + bolaRadius * 2 - d - wAmp * Math.sin(state[2]) <
		// -tol
		// && (state[0] + state[1] * dt >= d - wAmp || state[0] + state[1] *
		// tempDt >= d - wAmp)) {// (Math.abs(state[0]
		// // +
		// // state[1]*tempDt
		// // -
		// // wAmp*Math.sin(state[2])
		// // -
		// // wf*wAmp*Math.cos(state[2])*tempDt
		// // +
		// // wf*wf*wAmp*Math.sin(state[2])*tempDt*tempDt/2
		// // -
		// // d)
		// // >
		// // tol
		// // &&
		// // state[3]
		// // -
		// // (d-wAmp)/state[1]
		// // <
		// // 2*Math.PI/wf){
		//
		// state[0] += state[1] * tempDt;
		// state[2] += wf * tempDt;
		//
		// try {
		// animaThread.sleep(tbs);
		// } catch (InterruptedException e) {
		// }
		//
		// if (ferAn != null)
		// ferAn.move(state[0], d + wAmp * Math.sin(state[2]));
		// }
		//
		// if (state[0] + bolaRadius * 2 - d - wAmp * Math.sin(state[2]) >=
		// -tol) {
		// state[1] = 2 * wf * wAmp * Math.cos(state[2]) - state[1];
		//
		// if (ferHist != null) {
		// ferHist.append(state[1]);
		// ferHist.repaint();
		// } else if (ferIm != null) {
		// ferIm.setData((float) Math.abs((state[2] + 3 * Math.PI / 2d) % (2 *
		// Math.PI)), (float) Math
		// .abs(state[1]));
		// ferIm.drawImageNonStatic((float) Math.abs((state[2] + 3 * Math.PI /
		// 2d) % (2 * Math.PI)),
		// (float) Math.abs(state[1]));
		// ferIm.repaint();
		// } else
		// ferAn.setVel(state[1] / 100);
		//
		// }
		//
		// if (state[1] == 2 * wf * wAmp * Math.cos(state[2]) - state[1]) {//
		// duvida
		// state[0] += state[1] * tempDt;
		// state[2] += wf * tempDt;
		//
		// try {
		// animaThread.sleep(tbs);
		// } catch (InterruptedException e) {
		// }
		//
		// if (ferAn != null)
		// ferAn.move(state[0], d + wAmp * Math.sin(state[2]));
		// // state[3] += tempDt;
		// }
		//
		// state[0] += state[1] * tempDt;
		// state[2] += wf * tempDt;
		//
		// try {
		// animaThread.sleep(tbs);
		// } catch (InterruptedException e) {
		// }
		//
		// if (ferAn != null)
		// ferAn.move(state[0], d + wAmp * Math.sin(state[2]));
		// // state[3] += tempDt;
		// } else if (state[1] < 0 && state[0] + state[1] * dt < tol) {
		// // System.out.println(3);
		// while (state[0] > tol) {
		// state[0] += state[1] * tempDt;
		// state[2] += wf * tempDt;
		//
		// // if (state[3]%dt == 0){
		// try {
		// animaThread.sleep(tbs);
		// } catch (InterruptedException e) {
		// }
		// // }
		// if (ferAn != null)
		// ferAn.move(state[0], d + wAmp * Math.sin(state[2]));
		// // state[3] += tempDt;
		// }
		// state[1] *= -1;
		// if (ferAn != null)
		// ferAn.setVel(state[1] / 100);
		// // state[3] = 0;
		//
		// // ferAn.setVel(state[1]/10);
		// } else {
		// try {
		// animaThread.sleep(tbs);
		// } catch (InterruptedException e) {
		// }
		//
		// state[0] += state[1] * dt;
		// state[2] += wf * dt;
		// // state[3] += dt;
		//
		// if (ferAn != null)
		// ferAn.move(state[0], d + wAmp * Math.sin(state[2]));
		// }
		//
		// }
		// }
		// if (ferHist != null)
		// ferHist.repaint();
	}

	private void setUMax() {
		double tempUMapa = uMapa;
		double tempPsi = psi;

		for (int t = 0; t < nPsi; t++) {
			for (int i = 0; i < nUMapa; i++) {
				for (int j = 0; j < iter; j++) {

					tempUMapa = Math.abs(tempUMapa + Math.sin(tempPsi));
					tempPsi = (tempPsi + m / tempUMapa) % (2 * Math.PI);

					if (psi < 0) {
						psi += 2 * Math.PI;
					}

					uMax = Math.max(uMax, tempUMapa);
				}
				tempUMapa += dUMapa;
			}
			tempPsi += dPsi;
		}
	}

	private class ProducerThread extends Thread {
		private int currentSample = 0;

		@Override
		public void run() {
			try {
				Thread.sleep(1000);

				PhysicsValue[] value;

				if (uMax == -1) {
					setUMax();

					value = new PhysicsValue[NUM_CHANNELS];

					value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) uMax), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getMultiplier());

					addDataRow(value);
				}

				if (tbs != 0) { // Anima
					while (currentSample < nSamples && !stopped) {

						if (state[0] + state[1] * dt >= d - wAmp || state[0] + state[1] * tempDt >= d - wAmp) {

							while (state[0] + bolaRadius * 2 - d - wAmp * Math.sin(state[2]) < -tol
									&& (state[0] + state[1] * dt >= d - wAmp || state[0] + state[1] * tempDt >= d
											- wAmp)) {// (Math.abs(state[0] +
								// state[1]*tempDt -
								// wAmp*Math.sin(state[2])
								// -
								// wf*wAmp*Math.cos(state[2])*tempDt
								// +
								// wf*wf*wAmp*Math.sin(state[2])*tempDt*tempDt/2
								// - d) > tol &&
								// state[3] -
								// (d-wAmp)/state[1] <
								// 2*Math.PI/wf){

								state[0] += state[1] * tempDt;
								state[2] += wf * tempDt;

								value = new PhysicsValue[NUM_CHANNELS];

								value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[0]),
										getAcquisitionHeader().getChannelsConfig(0).getSelectedScale()
												.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(0)
												.getSelectedScale().getMultiplier());
								value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (d + wAmp
										* Math.sin(state[2]))), getAcquisitionHeader().getChannelsConfig(1)
										.getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
										.getChannelsConfig(1).getSelectedScale().getMultiplier());

								addDataRow(value);
							}

							if (state[0] + bolaRadius * 2 - d - wAmp * Math.sin(state[2]) >= -tol) {
								state[1] = 2 * wf * wAmp * Math.cos(state[2]) - state[1];

								value = new PhysicsValue[NUM_CHANNELS];

								value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[2]),
										getAcquisitionHeader().getChannelsConfig(0).getSelectedScale()
												.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(0)
												.getSelectedScale().getMultiplier());
								value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[1]),
										getAcquisitionHeader().getChannelsConfig(2).getSelectedScale()
												.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(2)
												.getSelectedScale().getMultiplier());

								addDataRow(value);
								currentSample++;
							}

							if (state[1] == 2 * wf * wAmp * Math.cos(state[2]) - state[1]) {// duvida
								state[0] += state[1] * tempDt;
								state[2] += wf * tempDt;

								Thread.sleep(tbs);

								value = new PhysicsValue[NUM_CHANNELS];

								value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[0]),
										getAcquisitionHeader().getChannelsConfig(0).getSelectedScale()
												.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(0)
												.getSelectedScale().getMultiplier());
								value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (d + wAmp
										* Math.sin(state[2]))), getAcquisitionHeader().getChannelsConfig(1)
										.getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
										.getChannelsConfig(1).getSelectedScale().getMultiplier());

								addDataRow(value);
							}

							state[0] += state[1] * tempDt;
							state[2] += wf * tempDt;

							Thread.sleep(tbs);

							value = new PhysicsValue[NUM_CHANNELS];

							value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[0]),
									getAcquisitionHeader().getChannelsConfig(0).getSelectedScale()
											.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(0)
											.getSelectedScale().getMultiplier());
							value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (d + wAmp
									* Math.sin(state[2]))), getAcquisitionHeader().getChannelsConfig(1)
									.getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
									.getChannelsConfig(1).getSelectedScale().getMultiplier());

							addDataRow(value);
						} else if (state[1] < 0 && state[0] + state[1] * dt < tol) {
							while (state[0] > tol) {
								state[0] += state[1] * tempDt;
								state[2] += wf * tempDt;

								Thread.sleep(tbs);

								value = new PhysicsValue[NUM_CHANNELS];

								value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[0]),
										getAcquisitionHeader().getChannelsConfig(0).getSelectedScale()
												.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(0)
												.getSelectedScale().getMultiplier());
								value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (d + wAmp
										* Math.sin(state[2]))), getAcquisitionHeader().getChannelsConfig(1)
										.getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
										.getChannelsConfig(1).getSelectedScale().getMultiplier());

								addDataRow(value);
							}
							state[1] *= -1;

							value = new PhysicsValue[NUM_CHANNELS];

							value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[1]),
									getAcquisitionHeader().getChannelsConfig(2).getSelectedScale()
											.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(2)
											.getSelectedScale().getMultiplier());

							addDataRow(value);
						} else {
							state[0] += state[1] * dt;
							state[2] += wf * dt;

							Thread.sleep(tbs);

							value = new PhysicsValue[NUM_CHANNELS];

							value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) state[0]),
									getAcquisitionHeader().getChannelsConfig(0).getSelectedScale()
											.getDefaultErrorValue(), getAcquisitionHeader().getChannelsConfig(0)
											.getSelectedScale().getMultiplier());
							value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) (d + wAmp
									* Math.sin(state[2]))), getAcquisitionHeader().getChannelsConfig(1)
									.getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
									.getChannelsConfig(1).getSelectedScale().getMultiplier());

							addDataRow(value);
						}
						System.out.println("1");
					}
				}
				/*
				 * else if (staticImg){
				 * 
				 * while(currentSample < nSamples && !stopped){
				 * 
				 * BufferedImage tempImg = new
				 * BufferedImage(w,h,BufferedImage.TYPE_BYTE_INDEXED); Graphics
				 * g = tempImg.getGraphics();
				 * 
				 * for (int t = 0; t < nPsi; t++){ for (int i = 0; i < nUMapa;
				 * i++){ float c1 = (float) Math.abs((psi+uMapa)%1); float c2 =
				 * (float) Math.abs((uMapa+pcor*Math.sin(psi))%1); float c3 =
				 * (float) Math.abs((c1+c2)%1);
				 * 
				 * g.setColor(new java.awt.Color(c1, c2, c3)); for (int j = 0; j
				 * < iter; j++){
				 * 
				 * uMapa = Math.abs(uMapa + Math.sin(psi)); psi = (psi +
				 * m/uMapa)%(2*Math.PI);
				 * 
				 * if (psi < 0) psi += 2*Math.PI;
				 * 
				 * if (nPsi*nUMapa == 1){ //histograma value = new
				 * PhysicsValue[NUM_CHANNELS];
				 * 
				 * value[1] = new
				 * PhysicsValue(PhysicsValFactory.fromFloat((float) uMapa),
				 * getAcquisitionHeader
				 * ().getChannelsConfig(1).getSelectedScale()
				 * .getDefaultErrorValue(),
				 * getAcquisitionHeader().getChannelsConfig
				 * (1).getSelectedScale().getMultiplier() ); } g.drawOval((int)
				 * Math.round(psi*w/(2*Math.PI)),
				 * h-(int)Math.round(uMapa*h/uMax), pixSize, pixSize); } uMapa
				 * += dUMapa; } psi += dPsi; }
				 * 
				 * g.dispose();
				 * 
				 * PixelGrabber pg = new PixelGrabber(tempImg, 0, 0, w, h,
				 * false);
				 * 
				 * try { pg.grabPixels(); } catch (InterruptedException e){}
				 * 
				 * value = new PhysicsValue[NUM_CHANNELS];
				 * 
				 * value[3] = new
				 * PhysicsValue(PhysicsValFactory.fromByteArray((byte[])
				 * pg.getPixels(), "data/raw"), null,
				 * com.linkare.rec.data.Multiplier.none);
				 * 
				 * addDataRow(value); currentSample++; } } else {
				 * while(currentSample < nSamples && !stopped){ for (int t = 0;
				 * t < nPsi; t++){ for (int i = 0; i < nUMapa; i++){ float c1 =
				 * (float) Math.abs((psi+uMapa)%1); float c2 = (float)
				 * Math.abs((uMapa+pcor*Math.sin(psi))%1); float c3 = (float)
				 * Math.abs((c1+c2)%1);
				 * 
				 * value = new PhysicsValue[NUM_CHANNELS];
				 * 
				 * value[0] = new PhysicsValue(PhysicsValFactory.fromFloat(c1),
				 * getAcquisitionHeader
				 * ().getChannelsConfig(0).getSelectedScale()
				 * .getDefaultErrorValue(),
				 * getAcquisitionHeader().getChannelsConfig
				 * (0).getSelectedScale().getMultiplier() ); value[1] = new
				 * PhysicsValue(PhysicsValFactory.fromFloat(c2),
				 * getAcquisitionHeader
				 * ().getChannelsConfig(1).getSelectedScale()
				 * .getDefaultErrorValue(),
				 * getAcquisitionHeader().getChannelsConfig
				 * (1).getSelectedScale().getMultiplier() ); value[2] = new
				 * PhysicsValue(PhysicsValFactory.fromFloat(c3),
				 * getAcquisitionHeader
				 * ().getChannelsConfig(2).getSelectedScale()
				 * .getDefaultErrorValue(),
				 * getAcquisitionHeader().getChannelsConfig
				 * (2).getSelectedScale().getMultiplier() );
				 * 
				 * addDataRow(value); currentSample++; for (int j = 0; j < iter;
				 * j++){
				 * 
				 * uMapa = Math.abs(uMapa + Math.sin(psi)); psi = (psi +
				 * m/uMapa)%(2*Math.PI);
				 * 
				 * if (psi < 0) psi += 2*Math.PI;
				 * 
				 * value = new PhysicsValue[NUM_CHANNELS];
				 * 
				 * value[0] = new
				 * PhysicsValue(PhysicsValFactory.fromFloat((float) psi),
				 * getAcquisitionHeader
				 * ().getChannelsConfig(0).getSelectedScale()
				 * .getDefaultErrorValue(),
				 * getAcquisitionHeader().getChannelsConfig
				 * (0).getSelectedScale().getMultiplier() ); value[1] = new
				 * PhysicsValue(PhysicsValFactory.fromFloat((float) uMapa),
				 * getAcquisitionHeader
				 * ().getChannelsConfig(1).getSelectedScale()
				 * .getDefaultErrorValue(),
				 * getAcquisitionHeader().getChannelsConfig
				 * (1).getSelectedScale().getMultiplier() );
				 * 
				 * addDataRow(value); currentSample++; } uMapa += dUMapa; } psi
				 * += dPsi; } }
				 * 
				 * }
				 */
				else if (staticImg) {

					if (nPsi * nUMapa == 1) { // histogram
						int dataCounter = 0;
						final float[] uMapaData = new float[iter];

						while (currentSample < nSamples && !stopped) {
							System.out.println("2");
							final BufferedImage tempImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_INDEXED);
							final Graphics g = tempImg.getGraphics();

							final float c1 = (float) Math.abs((psi + uMapa) % 1);
							final float c2 = (float) Math.abs((uMapa + pcor * Math.sin(psi)) % 1);
							final float c3 = Math.abs((c1 + c2) % 1);

							g.setColor(new java.awt.Color(c1, c2, c3));
							for (int j = 0; j < iter; j++) {

								uMapa = Math.abs(uMapa + Math.sin(psi));
								psi = (psi + m / uMapa) % (2 * Math.PI);

								if (psi < 0) {
									psi += 2 * Math.PI;
								}

								uMapaData[dataCounter++] = (float) uMapa;
								g.drawOval((int) Math.round(psi * w / (2 * Math.PI)),
										h - (int) Math.round(uMapa * h / (2 * Math.PI)), pixSize, pixSize);
							}

							g.dispose();

							final PixelGrabber pg = new PixelGrabber(tempImg, 0, 0, w, h, false);

							try {
								pg.grabPixels();
							} catch (final InterruptedException e) {
							}

							value = new PhysicsValue[NUM_CHANNELS];
							System.out.println("2");
							value[3] = new PhysicsValue(PhysicsValFactory.fromByteArray((byte[]) pg.getPixels(),
									"data/raw"), null, com.linkare.rec.data.Multiplier.none);
							value[4] = new PhysicsValue(PhysicsValFactory.fromByteArray(
									ByteUtil.floatArrayToByteArray(uMapaData), "data/raw"), null,
									com.linkare.rec.data.Multiplier.none);

							addDataRow(value);
							currentSample++;
						}
					} else {
						while (currentSample < nSamples && !stopped) {

							final BufferedImage tempImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_INDEXED);
							final Graphics g = tempImg.getGraphics();

							for (int t = 0; t < nPsi; t++) {
								for (int i = 0; i < nUMapa; i++) {
									final float c1 = (float) Math.abs((psi + uMapa) % 1);
									final float c2 = (float) Math.abs((uMapa + pcor * Math.sin(psi)) % 1);
									final float c3 = Math.abs((c1 + c2) % 1);

									g.setColor(new java.awt.Color(c1, c2, c3));
									for (int j = 0; j < iter; j++) {

										uMapa = Math.abs(uMapa + Math.sin(psi));
										psi = (psi + m / uMapa) % (2 * Math.PI);

										if (psi < 0) {
											psi += 2 * Math.PI;
										}

										g.drawOval((int) Math.round(psi * w / (2 * Math.PI)),
												h - (int) Math.round(uMapa * h / (2 * Math.PI)), pixSize, pixSize);
									}
									uMapa += dUMapa;
								}
								psi += dPsi;
							}

							g.dispose();

							final PixelGrabber pg = new PixelGrabber(tempImg, 0, 0, w, h, false);

							try {
								pg.grabPixels();
							} catch (final InterruptedException e) {
							}

							value = new PhysicsValue[NUM_CHANNELS];

							value[3] = new PhysicsValue(PhysicsValFactory.fromByteArray((byte[]) pg.getPixels(),
									"data/raw"), null, com.linkare.rec.data.Multiplier.none);
							System.out.println("3");
							addDataRow(value);
							currentSample++;
						}
					}
				} else {
					while (currentSample < nSamples && !stopped) {

						int dataCounter = 0;
						final float[] mData = new float[nPsi * nUMapa * iter * 2];

						for (int t = 0; t < nPsi; t++) {
							for (int i = 0; i < nUMapa; i++) {
								for (int j = 0; j < iter; j++) {

									uMapa = Math.abs(uMapa + Math.sin(psi));
									psi = (psi + m / uMapa) % (2 * Math.PI);

									if (psi < 0) {
										psi += 2 * Math.PI;
									}

									mData[dataCounter++] = (float) psi;
									mData[dataCounter++] = (float) uMapa;
								}
								uMapa += dUMapa;
							}
							psi += dPsi;
						}
						value = new PhysicsValue[NUM_CHANNELS];
						System.out.println("4");
						value[5] = new PhysicsValue(PhysicsValFactory.fromByteArray(
								ByteUtil.floatArrayToByteArray(mData), "data/raw"), null,
								com.linkare.rec.data.Multiplier.none);
						addDataRow(value);
						currentSample++;
					}
				}

				join(100);
				endProduction();

				driver.stopVirtualHardware();
			} catch (final InterruptedException ie) {
			} catch (final java.lang.OutOfMemoryError ome) {
				System.out.println("e");
				ome.printStackTrace();
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
