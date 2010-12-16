/*
 * BSDataProducer.java
 *
 * Created on April 3, 2005, 10:13 PM
 */

package pt.utl.ist.elab.driver.vbs;

/**
 *
 * @author  Queiro'
 */

import org.opensourcephysics.display2d.GridPointData;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;
import pt.utl.ist.elab.driver.virtual.utils.ByteUtil;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class BSDataProducer extends VirtualBaseDataSource {
	// O numero de canais(de dados) que existem!
	private int NUM_CHANNELS = 7;

	private double i1_ini = 0.6;
	private double i1_fin = -0.2;
	private double i2_ini = -0.2;
	private double i2_fin = 0.6;
	private double dist = 0.06;
	private double xpto = 0;
	private double ypto = 0;

	private static final double u0 = 4 * Math.PI * Math.pow(10, -7);

	private double i1, i2, x1, x2, tempo = 0, bpto, b1, b2, r1, r2;

	private GridPointData grelha;
	private double[][][] data;

	private int tbs = 300;
	private int nSamples = 26;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;

	// Aproveitamos para inicializar todas as variaveis logo no construtor...
	public BSDataProducer(VirtualBaseDriver driver, double i1_ini, double i1_fin, double i2_ini, double i2_fin,
			double dist, double xpto, double ypto, int tbs, int nSamples) {
		this.driver = driver;
		this.i1_ini = i1_ini;
		this.i1_fin = i1_fin;
		this.i2_ini = i2_ini;
		this.i2_fin = i2_fin;
		this.dist = dist;
		this.xpto = xpto;
		this.ypto = ypto;
		this.tbs = tbs;
		this.nSamples = nSamples;

		i1 = i1_ini;
		i2 = i2_ini;
		x1 = 0 - (dist / 2.0);
		x2 = 0 + (dist / 2.0);

		r1 = calcDist(x1, 0, xpto, ypto);
		r2 = calcDist(x2, 0, xpto, ypto);

		b1 = ((u0 * i1) / 2 * Math.PI * r1);
		b2 = ((u0 * i2) / 2 * Math.PI * r2);
		bpto = b1 + b2;

		grelha = new GridPointData(10, 10, 3);
		grelha.setScale(-30, 30, -30, 30);

		data = grelha.getData();
	}

	public double calcDist(double _x1, double _y1, double _x2, double _y2) {
		return Math.sqrt(Math.pow((_x2 - _x1), 2) + Math.pow((_y2 - _y1), 2));
	}

	// Este e' o processo que nos vai simular e criar as amostras para enviar ao
	// cliente!
	private class ProducerThread extends Thread {
		private int currentSample = 0;

		public void run() {
			try {
				sleep(1000);

				// Enquanto a amostra actual for menor do que o numero de
				// amostras pedido pelo cliente E ninguem tiver parado a
				// exp...produz dados
				while (currentSample < nSamples && !stopped) {

					r1 = calcDist(x1, 0, xpto, ypto);
					r2 = calcDist(x2, 0, xpto, ypto);

					b1 = ((u0 * i1) / 2 * Math.PI * r1);
					b2 = ((u0 * i2) / 2 * Math.PI * r2);
					bpto = b1 + b2;

					for (int i = 0; i < data.length; i++) {
						for (int j = 0; j < data[0].length; j++) {
							double x = data[i][j][0] / 100.0; // the x location
							double y = data[i][j][1] / 100.0; // the y location

							r1 = calcDist(x1, 0, x, y); // distance to wire 1
							r2 = calcDist(x2, 0, x, y); // distance to wire 2

							b1 = (u0 * i1) / (2 * Math.PI * r1);
							b2 = (u0 * i2) / (2 * Math.PI * r2);

							double ra1 = Math.atan((y) / (x1 - x));
							double gamma1 = 0;
							if (i1 >= 0) {
								if (x < x1) {
									ra1 += Math.PI;
								}
								gamma1 = -((Math.PI / 2.0) + ra1);
							} else {
								if (x < x1) {
									ra1 += Math.PI;
								}
								gamma1 = (Math.PI / 2.0) - ra1;
							}

							double ra2 = Math.atan((y) / (x2 - x));
							double gamma2 = 0;
							if (i2 >= 0) {
								if (x < x2) {
									ra2 += Math.PI;
								}
								gamma2 = -((Math.PI / 2.0) + ra2);
							} else {
								if (x < x2) {
									ra2 += Math.PI;
								}
								gamma2 = (Math.PI / 2.0) - ra2;
							}

							data[i][j][2] = 5 * Math.pow(10, 5)
									* Math.abs(2 * Math.pow(10, -7) * ((i1 / r1) + (i2 / r2))); // vector
							// magnitude
							data[i][j][3] = (b1 * Math.cos(gamma1) + b2 * Math.cos(gamma2)) * 5 * Math.pow(10, 5);
							data[i][j][4] = (b1 * Math.sin(gamma1) + b2 * Math.sin(gamma2)) * 5 * Math.pow(10, 5);
						}
					}

					// envia as amostra calculadas!
					// 1- cria um array com o numero de canais existentes!
					PhysicsValue[] value = new PhysicsValue[NUM_CHANNELS];

					// envia no canal CORRESPONDENTE!!! o valor
					value[0] = new PhysicsValue(PhysicsValFactory.fromFloat((float) tempo), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getMultiplier());
					value[1] = new PhysicsValue(PhysicsValFactory.fromFloat((float) bpto), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getMultiplier());
					value[2] = new PhysicsValue(PhysicsValFactory.fromFloat((float) b1), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getMultiplier());
					value[3] = new PhysicsValue(PhysicsValFactory.fromFloat((float) b2), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getMultiplier());
					value[4] = new PhysicsValue(PhysicsValFactory.fromFloat((float) i1), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(0).getSelectedScale().getMultiplier());
					value[5] = new PhysicsValue(PhysicsValFactory.fromFloat((float) i2), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(), getAcquisitionHeader()
							.getChannelsConfig(1).getSelectedScale().getMultiplier());
					value[6] = new PhysicsValue(PhysicsValFactory.fromByteArray(ByteUtil.getObjectAsByteArray(data),
							"data/raw"), null, com.linkare.rec.data.Multiplier.none);

					addDataRow(value);

					// incrementa o tempo
					tempo += tbs / 1000F;
					i1 += (i1_fin - i1_ini) / nSamples;
					i2 += (i2_fin - i2_ini) / nSamples;

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
		/*
		 * DPendulumDataProducer dp = new DPendulumDataProducer(null,90, 90, 10,
		 * 0, 0.5f, 1.5f, 0.3f, 0.5f, 10, 10000); dp.startProduction();
		 */
	}

	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}
}