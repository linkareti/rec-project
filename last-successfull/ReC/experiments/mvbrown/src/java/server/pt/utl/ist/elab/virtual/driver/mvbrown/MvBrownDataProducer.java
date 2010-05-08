/*
 * MvBrownDataProducer.java
 *
 * Created on February 13, 2004, 12:13 PM
 */

package pt.utl.ist.elab.virtual.driver.mvbrown;

/**
 *
 * @author  nomead
 */

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.data.PhysicsValFactory;

public class MvBrownDataProducer extends VirtualBaseDataSource {// implements
	// Runnable {

	private int NUM_CHANNELS = 24;

	private int tbs = 100;
	private int nSamples;

	private boolean stopped = false;
	private VirtualBaseDriver driver = null;

	/**************************************
	 * Langevin
	 **************************************/

	// Direcao Forca Aleatoria 3D
	private javax.media.j3d.Transform3D eulerTransf;
	private javax.vecmath.Vector3d vect3D;

	// Forca Aleatoria
	private double dPRandForce;
	private double freqColisao;

	// Atrito
	private double a;
	private double visco;

	private double massa;
	private boolean langevin;

	/**************************************
	 * Simulacao Solo Random
	 **************************************/
	private double d;

	/**************************************
	 * TRONCO NUMERICO
	 **************************************/
	private double[][] state;
	private byte execDim;
	private int numPart;
	private long seed = Math.round(Math.PI * Integer.MAX_VALUE);
	private java.util.Random rand;

	/**************************************
	 * GRAFISMO
	 **************************************/

	private XYSeries[] datasets;
	private IntervalXYDataset datasetGenPos;
	private IntervalXYDataset datasetGenVel;

	private int w, h;
	private boolean conPts;
	private boolean anima;
	private boolean[] graphMed;

	public MvBrownDataProducer(VirtualBaseDriver driver, int _numPart, byte _dim, int _w, int _h, boolean _conPts,
			boolean _anima, int _tbs, int _nSamples) {
		this.driver = driver;
		nSamples = _nSamples;
		tbs = _tbs;
		numPart = _numPart;

		rand = new java.util.Random();

		execDim = _dim;

		w = _w;
		h = _h;
		conPts = _conPts;
		anima = _anima;

		state = new double[numPart][6];
	}

	/*
	 * Inicializa um grafico do tipo (x,y), com x e y quaisquer que e'
	 * renderizado num JFreeChart e cuja imagem e' enviada como um array de
	 * bytes.
	 */
	public void initializeGraphs(String[] _graph, boolean[] _graphMed) {

		graphMed = _graphMed;
		int k = 0;
		int p = 0;
		for (int i = 0; i < _graph.length; i++)
			if (!_graph[i].equalsIgnoreCase("")) {
				if (!_graphMed[i])
					p++;
				k++;
			}

		axis[0] = new byte[k][3];
		datasets = new XYSeries[p];

		for (int i = 0; i < _graph.length; i++) {
			if (!_graph[i].equalsIgnoreCase("")) {
				if (!_graphMed[i]) {
					datasets[--p] = new XYSeries(_graph[i]);
				} else {
					String str[] = _graph[i].split(" vs ");
					if (!str[0].equalsIgnoreCase("t"))
						str[0] = "<" + str[0] + ">";
					if (!str[1].equalsIgnoreCase("t"))
						str[1] = "<" + str[1] + ">";

					_graph[i] = str[0].concat(" vs " + str[1]);
					serieRelateAxis(_graph[i], --k, (byte) 0);
				}
			}
		}
		if (datasets.length > 0)
			relateDataset(); // correspondencia serie->eixos
		else
			datasets = null;
	}

	/*
	 * Inicializa um grafico do tipo (t,vx,vy,vz,|v|) que e' renderizado num
	 * JFreeChart e cuja imagem e' enviada como um array de bytes.
	 */
	public void initializeGenVelGraph(boolean _vx, boolean _vy, boolean _vz, boolean _vMod, boolean _vMed,
			boolean _vQuad, boolean _vAbs) {

		if (!_vMed && !_vQuad && !_vAbs) {
			datasetGenVel = new XYSeriesCollection();
			if (_vx)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs Vx", false, true));
			if (_vy)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs Vy", false, true));
			if (_vz)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs Vz", false, true));
			if (_vMod)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs | v |", false, true));
		} else if (!_vMed && !_vAbs) {
			datasetGenVel = new XYSeriesCollection();
			if (_vx)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs Vx^2", false, true));
			if (_vy)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs Vy^2", false, true));
			if (_vz)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs Vz^2", false, true));
			if (_vMod)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs | v |^2", false, true));
		} else if (!_vMed && !_vQuad) {
			datasetGenVel = new XYSeriesCollection();
			if (_vx)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs | Vx |", false, true));
			if (_vy)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs | Vy |", false, true));
			if (_vz)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs | Vz |", false, true));
			if (_vMod)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs | v |", false, true));
		} else if (!_vAbs && !_vQuad) {
			int k = 0;
			if (_vx)
				k++;
			if (_vy)
				k++;
			if (_vz)
				k++;
			if (_vMod)
				k++;
			axis[2] = new byte[k][3];
			int i = 0;
			if (_vx)
				serieRelateAxis("t vs <Vx>", i++, (byte) 2);
			if (_vy)
				serieRelateAxis("t vs <Vy>", i++, (byte) 2);
			if (_vz)
				serieRelateAxis("t vs <Vz>", i++, (byte) 2);
			if (_vMod)
				serieRelateAxis("t vs <| v |>", i++, (byte) 2);
		} else if (!_vAbs) {
			int k = 0;
			if (_vx)
				k++;
			if (_vy)
				k++;
			if (_vz)
				k++;
			if (_vMod)
				k++;
			axis[2] = new byte[k][3];
			int i = 0;
			if (_vx)
				serieRelateAxis("t vs <Vx^2>", i++, (byte) 2);
			if (_vy)
				serieRelateAxis("t vs <Vy^2>", i++, (byte) 2);
			if (_vz)
				serieRelateAxis("t vs <Vz^2>", i++, (byte) 2);
			if (_vMod)
				serieRelateAxis("t vs <| v |^2>", i++, (byte) 2);
		} else if (!_vMed) {
			datasetGenVel = new XYSeriesCollection();
			if (_vx)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs Vx^2", false, true));
			if (_vy)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs Vy^2", false, true));
			if (_vz)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs Vz^2", false, true));
			if (_vMod)
				((XYSeriesCollection) datasetGenVel).addSeries(new XYSeries("t vs | v |^2", false, true));
		} else if (!_vQuad) {
			int k = 0;
			if (_vx)
				k++;
			if (_vy)
				k++;
			if (_vz)
				k++;
			if (_vMod)
				k++;
			axis[2] = new byte[k][3];
			int i = 0;
			if (_vx)
				serieRelateAxis("t vs <| Vx |>", i++, (byte) 2);
			if (_vy)
				serieRelateAxis("t vs <| Vy |>", i++, (byte) 2);
			if (_vz)
				serieRelateAxis("t vs <| Vz |>", i++, (byte) 2);
			if (_vMod)
				serieRelateAxis("t vs <| v |>", i++, (byte) 2);
		}

		if (datasetGenVel != null)
			relateVelDataset();
	}

	/*
	 * Initializa um grafico do tipo (t,x,y,z,|r|) que e' renderizado num
	 * JFreeChart e cuja imagem e' enviada como um array de bytes.
	 */
	public void initializeGenPosGraph(boolean _x, boolean _y, boolean _z, boolean _rMod, boolean _rMed, boolean _rQuad,
			boolean _rAbs) {

		if (!_rMed && !_rQuad && !_rAbs) {
			datasetGenPos = new XYSeriesCollection();
			if (_x)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs X", false, true));
			if (_y)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs Y", false, true));
			if (_z)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs Z", false, true));
			if (_rMod)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs | r |", false, true));
		} else if (!_rMed && !_rAbs) {
			datasetGenPos = new XYSeriesCollection();
			if (_x)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs X^2", false, true));
			if (_y)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs Y^2", false, true));
			if (_z)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs Z^2", false, true));
			if (_rMod)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs | r |^2", false, true));
		} else if (!_rMed && !_rQuad) {
			datasetGenPos = new XYSeriesCollection();
			if (_x)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs | X |", false, true));
			if (_y)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs | Y |", false, true));
			if (_z)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs | Z |", false, true));
			if (_rMod)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs | r |", false, true));
		} else if (!_rAbs && !_rQuad) {
			int k = 0;
			if (_x)
				k++;
			if (_y)
				k++;
			if (_z)
				k++;
			if (_rMod)
				k++;
			axis[1] = new byte[k][3];
			int i = 0;
			if (_x)
				serieRelateAxis("t vs <X>", i++, (byte) 1);
			if (_y)
				serieRelateAxis("t vs <Y>", i++, (byte) 1);
			if (_z)
				serieRelateAxis("t vs <Z>", i++, (byte) 1);
			if (_rMod)
				serieRelateAxis("t vs <| r |>", i++, (byte) 1);
		} else if (!_rAbs) {
			int k = 0;
			if (_x)
				k++;
			if (_y)
				k++;
			if (_z)
				k++;
			if (_rMod)
				k++;
			axis[1] = new byte[k][3];
			int i = 0;
			if (_x)
				serieRelateAxis("t vs <X^2>", i++, (byte) 1);
			if (_y)
				serieRelateAxis("t vs <Y^2>", i++, (byte) 1);
			if (_z)
				serieRelateAxis("t vs <Z^2>", i++, (byte) 1);
			if (_rMod)
				serieRelateAxis("t vs <| r |^2>", i++, (byte) 1);
		} else if (!_rMed) {
			datasetGenPos = new XYSeriesCollection();
			if (_x)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs X^2", false, true));
			if (_y)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs Y^2", false, true));
			if (_z)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs Z^2", false, true));
			if (_rMod)
				((XYSeriesCollection) datasetGenPos).addSeries(new XYSeries("t vs | r |^2", false, true));
		} else if (!_rQuad) {
			int k = 0;
			if (_x)
				k++;
			if (_y)
				k++;
			if (_z)
				k++;
			if (_rMod)
				k++;
			axis[1] = new byte[k][3];
			int i = 0;
			if (_x)
				serieRelateAxis("t vs <| X |>", i++, (byte) 1);
			if (_y)
				serieRelateAxis("t vs <| Y |>", i++, (byte) 1);
			if (_z)
				serieRelateAxis("t vs <| Z |>", i++, (byte) 1);
			if (_rMod)
				serieRelateAxis("t vs <| r |>", i++, (byte) 1);
		}

		if (datasetGenPos != null)
			relatePosDataset();
	}

	/*
	 * //TESTE public void
	 * start(pt.utl.ist.elab.virtual.client.mvbrown.displays.Animation an){
	 * this.an = an;
	 * 
	 * animaThread = new Thread(this); animaThread.start(); }
	 * 
	 * //TESTE pt.utl.ist.elab.virtual.client.mvbrown.displays.StaticChart ch;
	 * public void
	 * startChart(pt.utl.ist.elab.virtual.client.mvbrown.displays.StaticChart
	 * ch){ this.ch = ch;
	 * 
	 * animaThread = new Thread(this); animaThread.start(); }
	 * 
	 * pt.utl.ist.elab.virtual.client.mvbrown.displays.Animation an; private
	 * Thread animaThread; public void run() {
	 * 
	 * int currentSample = 0;
	 * 
	 * //[ Variavel: x,y,z,... ][ isMedia ] [ valor(es) ] //caso geral, media e
	 * ~media //float [][][] data = new float[19][2][];
	 * 
	 * //[ Variavel: x,y,z,... ][ 0 ] [ valor(es) ] //usado so quando nao e'
	 * media float [][] data = new float[19][];
	 * 
	 * while(currentSample < nSamples){
	 * 
	 * 
	 * for (int i = 0; i < axis.length; i++) if (axis[i] != null) for (int j =
	 * 0; j < axis[i].length; j++){ if (axis[i][j][0] > 0){ if (axis[i][j][2] ==
	 * 0) //x ~media data[axis[i][j][0]] = getValue(axis[i][j], currentSample,
	 * 0); else ;//ADDROW CANAL : (axis[i][j][0]-1), getValue(axis[i][j],
	 * currentSample, 0) } if (axis[i][j][1] > 0){ if (axis[i][j][2] == 0) //y
	 * ~media data[axis[i][j][1]] = getValue(axis[i][j], currentSample, 1); else
	 * ;//ADDROW CANAL : (axis[i][j][1]-1), getValue(axis[i][j], currentSample,
	 * 1); } }
	 * 
	 * 
	 * //~MEDIA - preencher serie if (datasets != null) for (int i = 0; i <
	 * datasets.length; i++)
	 * appendValueToSerie(datasets[i],data[Math.abs(axis[0]
	 * [i][0])],data[Math.abs(axis[0][i][1])]); //~MEDIA - preencher serie if
	 * (datasetGenPos != null) for (int i = 0; i <
	 * datasetGenPos.getSeriesCount(); i++)
	 * appendValueToSerie(((XYSeriesCollection
	 * )datasetGenPos).getSeries(i),data[Math
	 * .abs(axis[1][i][0])],data[Math.abs(axis[1][i][1])]); //~MEDIA - preencher
	 * serie if (datasetGenVel != null) for (int i = 0; i <
	 * datasetGenVel.getSeriesCount(); i++)
	 * appendValueToSerie(((XYSeriesCollection
	 * )datasetGenVel).getSeries(i),data[Math
	 * .abs(axis[2][i][0])],data[Math.abs(axis[2][i][1])]);
	 * 
	 * 
	 * 
	 * if (anima){
	 * 
	 * if (execDim == 2){ float [] f = new float[numPart*2];
	 * 
	 * for (int i = 0; i < numPart; i++){ f[i] = (float) state[i][0]; f[i+1] =
	 * (float) state[i][2]; }
	 * 
	 * byte [] b =
	 * pt.utl.ist.elab.virtual.utils.ByteUtil.floatArrayToByteArray(f); //ADDROW
	 * CANAL 0 an.moves(b); } else if (execDim == 3){ float [] f = new
	 * float[numPart*3];
	 * 
	 * for (int i = 0; i < numPart; i++){ f[i] = (float) state[i][0]; f[i+1] =
	 * (float) state[i][2]; f[i+2] = (float) state[i][4]; }
	 * 
	 * byte [] b =
	 * pt.utl.ist.elab.virtual.utils.ByteUtil.floatArrayToByteArray(f); //ADDROW
	 * CANAL 0 an.moves(b); } else { float [] f = new float[numPart];
	 * 
	 * for (int i = 0; i < numPart; i++) f[i] = (float) state[i][0];
	 * 
	 * byte [] b =
	 * pt.utl.ist.elab.virtual.utils.ByteUtil.floatArrayToByteArray(f); //ADDROW
	 * CANAL 0 an.moves(b); }
	 * 
	 * try { animaThread.sleep(tbs); } catch (InterruptedException e){}
	 * 
	 * } stepNumerico(); currentSample++; }
	 * 
	 * //ENVIAR NO FIM GRAFICOS QUE NAO TOMAM MEDIAS //GRAFICOS GERALMENTE
	 * PESADOS
	 * 
	 * //ENVIAR Graficos 2 vars [~Media] if (datasets != null){ int o =
	 * datasets.length; for (int i = 0; i < graphMed.length; i++) if
	 * (!graphMed[i]) ch.makeImage(graphImageBytes(datasets[--o])); //ADDROW
	 * CANAL : (18+i) graphImageBytes(datasets[i]); } //ENVIAR Grafico da
	 * posicao [~Media] if (datasetGenPos != null) //ADDROW CANAL : 22
	 * graphImageBytes(datasetGenPos);
	 * ch.makeImage(graphImageBytes(datasetGenPos));
	 * 
	 * //ENVIAR Grafico da velocidade [~Media] if (datasetGenVel != null)
	 * //ADDROW CANAL : 23 graphImageBytes(datasetGenVel)
	 * ch.makeImage(graphImageBytes(datasetGenVel));
	 * 
	 * System.out.println("FIM"); }
	 */

	private class ProducerThread extends Thread {
		private int currentSample = 0;

		public void run() {
			try {
				sleep(1000);

				PhysicsValue[] value;

				// [ Variavel: x,y,z,... ][ isMedia ] [ valor(es) ]
				// caso geral, media e ~media
				// float [][][] data = new float[19][2][];

				// [ Variavel: x,y,z,... ][ 0 ] [ valor(es) ]
				// usado so quando nao e' media
				float[][] data = new float[19][];

				while (currentSample < nSamples && !stopped) {
					value = new PhysicsValue[NUM_CHANNELS];

					for (int i = 0; i < axis.length; i++)
						if (axis[i] != null)
							for (int j = 0; j < axis[i].length; j++) {
								if (axis[i][j][0] > 0) {
									if (axis[i][j][2] == 0) // x ~media
										data[axis[i][j][0]] = getValue(axis[i][j], currentSample, 0);
									else
										value[axis[i][j][0] - 1] = new PhysicsValue(PhysicsValFactory
												.fromFloat((float) getValue(axis[i][j], currentSample, 0)[0]),
												getAcquisitionHeader().getChannelsConfig(axis[i][j][0] - 1)
														.getSelectedScale().getDefaultErrorValue(),
												getAcquisitionHeader().getChannelsConfig(axis[i][j][0] - 1)
														.getSelectedScale().getMultiplier());
								}
								if (axis[i][j][1] > 0) {
									if (axis[i][j][2] == 0) // y ~media
										data[axis[i][j][1]] = getValue(axis[i][j], currentSample, 1);
									else
										value[axis[i][j][1] - 1] = new PhysicsValue(PhysicsValFactory
												.fromFloat((float) getValue(axis[i][j], currentSample, 1)[0]),
												getAcquisitionHeader().getChannelsConfig(axis[i][j][1] - 1)
														.getSelectedScale().getDefaultErrorValue(),
												getAcquisitionHeader().getChannelsConfig(axis[i][j][1] - 1)
														.getSelectedScale().getMultiplier());
								}
							}

					// ~MEDIA - preencher serie
					if (datasets != null)
						for (int i = 0; i < datasets.length; i++)
							appendValueToSerie(datasets[i], data[Math.abs(axis[0][i][0])],
									data[Math.abs(axis[0][i][1])]);
					// ~MEDIA - preencher serie
					if (datasetGenPos != null)
						for (int i = 0; i < datasetGenPos.getSeriesCount(); i++)
							appendValueToSerie(((XYSeriesCollection) datasetGenPos).getSeries(i), data[Math
									.abs(axis[1][i][0])], data[Math.abs(axis[1][i][1])]);
					// ~MEDIA - preencher serie
					if (datasetGenVel != null)
						for (int i = 0; i < datasetGenVel.getSeriesCount(); i++)
							appendValueToSerie(((XYSeriesCollection) datasetGenVel).getSeries(i), data[Math
									.abs(axis[2][i][0])], data[Math.abs(axis[2][i][1])]);

					if (anima) {

						if (execDim == 2) {
							float[] f = new float[numPart * 2];

							for (int i = 0; i < numPart; i++) {
								f[i] = (float) state[i][0];
								f[i + 1] = (float) state[i][2];
							}

							byte[] b = pt.utl.ist.elab.driver.virtual.utils.ByteUtil.floatArrayToByteArray(f);

							value[0] = new PhysicsValue(PhysicsValFactory.fromByteArray(b, "data/raw"), null,
									com.linkare.rec.data.Multiplier.none);
						} else if (execDim == 3) {
							float[] f = new float[numPart * 3];

							for (int i = 0; i < numPart; i++) {
								f[i] = (float) state[i][0];
								f[i + 1] = (float) state[i][2];
								f[i + 2] = (float) state[i][4];
							}

							byte[] b = pt.utl.ist.elab.driver.virtual.utils.ByteUtil.floatArrayToByteArray(f);
							value[0] = new PhysicsValue(PhysicsValFactory.fromByteArray(b, "data/raw"), null,
									com.linkare.rec.data.Multiplier.none);
						} else {
							float[] f = new float[numPart];

							for (int i = 0; i < numPart; i++)
								f[i] = (float) state[i][0];

							byte[] b = pt.utl.ist.elab.driver.virtual.utils.ByteUtil.floatArrayToByteArray(f);
							value[0] = new PhysicsValue(PhysicsValFactory.fromByteArray(b, "data/raw"), null,
									com.linkare.rec.data.Multiplier.none);
						}
						sleep(tbs);
					}
					addDataRow(value);
					currentSample++;
					stepNumerico();
				}

				value = new PhysicsValue[NUM_CHANNELS];

				// ENVIAR NO FIM GRAFICOS QUE NAO TOMAM MEDIAS
				// GRAFICOS GERALMENTE PESADOS

				// ENVIAR Graficos 2 vars [~Media]
				if (datasets != null) {
					int o = datasets.length;
					for (int i = 0; i < graphMed.length; i++)
						if (!graphMed[i])
							value[18 + i] = new PhysicsValue(PhysicsValFactory.fromByteArray(
									graphImageBytes(datasets[--o]), "data/raw"), null,
									com.linkare.rec.data.Multiplier.none);
				}
				// ENVIAR Grafico da posicao [~Media]
				if (datasetGenPos != null)
					value[22] = new PhysicsValue(PhysicsValFactory.fromByteArray(graphImageBytes(datasetGenPos),
							"data/raw"), null, com.linkare.rec.data.Multiplier.none);

				// ENVIAR Grafico da velocidade [~Media]
				if (datasetGenVel != null)
					value[23] = new PhysicsValue(PhysicsValFactory.fromByteArray(graphImageBytes(datasetGenVel),
							"data/raw"), null, com.linkare.rec.data.Multiplier.none);

				addDataRow(value);

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

	public void stopNow() {
		stopped = true;
		setDataSourceStoped();
	}

	/*
	 * Avanca todas as particulas numericamente
	 */
	public void stepNumerico() {
		if (!langevin)
			stepRandomSimul();
		else
			stepLangevin();
	}

	/*
	 * Devolve um aleatorio no intervalo [0,2Pi[ Alterando, ou nao, a seed na
	 * inicializacao, permite obter (alterando) numeros aleatorios sem
	 * correlacao, ou (nao alterado) os mesmos numeros aleatorios por cada
	 * inicializacao do sistema
	 */
	private double randomize() {
		return rand.nextDouble() * 2 * Math.PI;
	}

	/*************************************************************
	 *------------------------------------------------------------ MODELO
	 * ALEATORIO ------------------------------------------------------------
	 *************************************************************/

	/*
	 * Inicializa numericamente o sistema para correr com o Modelo Aleatorio,
	 * com as definicoes dos argumentos: _d = modulo do incremento posicional ;
	 * _dim = dimensoes ; _seed = mesmo conjunto de aleatorios.
	 */
	public void initializeRandomSimul(double _d, boolean _seed) {
		// resetState();
		d = _d;
		langevin = false;

		if (_seed) // Modo Aleatorio com correlacao
			rand.setSeed(seed);
		else
			// Modo aleatorio sem correlacao
			rand.setSeed((long) (Math.random() * Long.MAX_VALUE));

		if (execDim == 3) {
			eulerTransf = new javax.media.j3d.Transform3D();
			vect3D = new javax.vecmath.Vector3d(d / Math.sqrt(3), d / Math.sqrt(3), d / Math.sqrt(3));
		}
	}

	/*
	 * Avanca numericamente todas as particulas com o Modelo Aleatorio
	 */
	private void stepRandomSimul() {

		for (int i = 0; i < numPart; i++) {

			if (execDim == 1 || execDim == 0) {
				double angTheta = randomize();
				state[i][0] += d * Math.cos(angTheta) / Math.abs(Math.cos(angTheta));
			} else if (execDim == 2) {
				double angTheta = randomize();
				state[i][0] += d * Math.cos(angTheta);
				state[i][2] += d * Math.sin(angTheta);
			} else {
				eulerTransf.setEuler(new javax.vecmath.Vector3d(randomize(), randomize(), randomize()));
				eulerTransf.transform(vect3D);

				state[i][0] += vect3D.x;
				state[i][2] += vect3D.y;
				state[i][4] += vect3D.z;
			}
		}

	}

	/*************************************************************
	 *------------------------------------------------------------ MODELO DE
	 * LANGEVIN ------------------------------------------------------------
	 *************************************************************/

	/*
	 * Inicializa numericamente o sistema para correr com o modelo de Langevin,
	 * com as definicoes dos argumentos: _massa = massa de cada particula ; []
	 * _atrito: _atrito[0] = raio da particula _atrito[1] = viscosidade do
	 * fluido ; [] _randForce : _randForce[0] = variacao do momento linear
	 * _randForce[1] = frequencia de colisao ; [] _velcInit = velocidade inicial
	 * X , Y, Z ; _dim = dimensoes ; _seed = mesmo conjunto de aleatorios.
	 */
	public void initializeLangevin(double _massa, double[] _atrito, double[] _randForce, double[] _velcInit,
			boolean _seed) {
		// resetState();
		massa = _massa;
		a = _atrito[0];
		visco = _atrito[1];
		dPRandForce = _randForce[0];
		freqColisao = _randForce[1];
		langevin = true;

		if (_seed) // Modo Aleatorio com correlacao
			rand.setSeed(seed);
		else
			// Modo aleatorio sem correlacao
			rand.setSeed((long) (Math.random() * Long.MAX_VALUE));

		if (execDim == 3) {
			eulerTransf = new javax.media.j3d.Transform3D();
			vect3D = new javax.vecmath.Vector3d(dPRandForce / (Math.sqrt(3) * massa), dPRandForce
					/ (Math.sqrt(3) * massa), dPRandForce / (Math.sqrt(3) * massa));
		}

		for (int i = 0; i < numPart; i++) {
			state[i][1] = _velcInit[0];
			state[i][3] = _velcInit[1];
			state[i][5] = _velcInit[2];
		}
	}

	/*
	 * Avanca numericamente todas as particulas com o Modelo de Langevin
	 */
	private void stepLangevin() {
		atrito(1d / freqColisao);
		randomForce();
	}

	/*
	 * Aplica a Forca Aleatoria, traduzida num incremento instantaneo de
	 * velocidade numa direccao aleatoria
	 */
	private void randomForce() {

		for (int i = 0; i < numPart; i++) {

			if (execDim == 1 || execDim == 0) {
				double angTheta = randomize();
				state[i][1] += dPRandForce * Math.cos(angTheta) / (Math.abs(Math.cos(angTheta)) * massa);
			} else if (execDim == 2) {
				double angTheta = randomize();
				state[i][1] += dPRandForce * Math.cos(angTheta) / massa;
				state[i][3] += dPRandForce * Math.sin(angTheta) / massa;
			} else {
				eulerTransf.setEuler(new javax.vecmath.Vector3d(randomize(), randomize(), randomize()));

				eulerTransf.transform(vect3D);

				state[i][1] += vect3D.x;
				state[i][3] += vect3D.y;
				state[i][5] += vect3D.z;
			}
		}
	}

	/*
	 * Actualiza o estado do sistema apos a aplicao duma forca de atrito durante
	 * um intervalo de tempo _dt
	 */
	private void atrito(double _dt) {
		double alpha = 6 * Math.PI * a * visco / massa;

		for (int i = 0; i < numPart; i++) {

			if (execDim == 1 || execDim == 0) {
				state[i][0] += -state[i][1] * Math.exp(-alpha * _dt) / alpha + state[i][1] / alpha;
				state[i][1] = state[i][1] * Math.exp(-alpha * _dt);
			} else if (execDim == 2) {
				state[i][0] += -state[i][1] * Math.exp(-alpha * _dt) / alpha + state[i][1] / alpha;
				state[i][1] = state[i][1] * Math.exp(-alpha * _dt);
				state[i][2] += -state[i][3] * Math.exp(-alpha * _dt) / alpha + state[i][3] / alpha;
				state[i][3] = state[i][3] * Math.exp(-alpha * _dt);
			} else {
				state[i][0] += -state[i][1] * Math.exp(-alpha * _dt) / alpha + state[i][1] / alpha;
				state[i][1] = state[i][1] * Math.exp(-alpha * _dt);
				state[i][2] += -state[i][3] * Math.exp(-alpha * _dt) / alpha + state[i][3] / alpha;
				state[i][3] = state[i][3] * Math.exp(-alpha * _dt);
				state[i][4] += -state[i][5] * Math.exp(-alpha * _dt) / alpha + state[i][5] / alpha;
				state[i][5] = state[i][5] * Math.exp(-alpha * _dt);
			}

		}
	}

	/*************************************************************
	 *------------------------------------------------------------ GRAFICOS
	 * ------------------------------------------------------------
	 *************************************************************/
	// Graficos de 2 vars
	private byte[] graphImageBytes(XYSeries serie) {

		String labels[] = serie.getDescription().split(" vs ");

		JFreeChart tmpChart;
		if (conPts)
			tmpChart = ChartFactory.createXYLineChart(serie.getDescription(), labels[0], labels[1],
					new XYSeriesCollection(serie), PlotOrientation.VERTICAL, true, true, true);
		else
			tmpChart = ChartFactory.createScatterPlot(serie.getDescription(), labels[0], labels[1],
					new XYSeriesCollection(serie), PlotOrientation.VERTICAL, true, true, true);

		BufferedImage tempImg = ((JFreeChart) tmpChart)
				.createBufferedImage(w, h, BufferedImage.TYPE_BYTE_INDEXED, null);

		PixelGrabber pg = new PixelGrabber(tempImg, 0, 0, tempImg.getWidth(), tempImg.getHeight(), false);

		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
		}

		return (byte[]) pg.getPixels();
	}

	// Graficos de varias vars
	private byte[] graphImageBytes(IntervalXYDataset serCol) {

		String label = "t";

		JFreeChart tmpChart;
		if (conPts)
			tmpChart = ChartFactory.createXYLineChart("General Chart", label, "", serCol, PlotOrientation.VERTICAL,
					true, true, true);
		else
			tmpChart = ChartFactory.createScatterPlot("General Chart", label, "", serCol, PlotOrientation.VERTICAL,
					true, true, true);

		BufferedImage tempImg = ((JFreeChart) tmpChart)
				.createBufferedImage(w, h, BufferedImage.TYPE_BYTE_INDEXED, null);

		PixelGrabber pg = new PixelGrabber(tempImg, 0, 0, tempImg.getWidth(), tempImg.getHeight(), false);

		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
		}

		return (byte[]) pg.getPixels();
	}

	private void relateDataset() {
		for (int i = 0; i < datasets.length; i++)
			serieRelateAxis(datasets[i].getDescription(), i, (byte) 0);
	}

	private void relatePosDataset() {
		axis[1] = new byte[datasetGenPos.getSeriesCount()][3];
		for (int i = 0; i < datasetGenPos.getSeriesCount(); i++)
			serieRelateAxis(((XYSeriesCollection) datasetGenPos).getSeries(i).getDescription(), i, (byte) 1);
	}

	private void relateVelDataset() {
		axis[2] = new byte[datasetGenVel.getSeriesCount()][3];
		for (int i = 0; i < datasetGenVel.getSeriesCount(); i++)
			serieRelateAxis(((XYSeriesCollection) datasetGenVel).getSeries(i).getDescription(), i, (byte) 2);
	}

	// 3-> 3 tipos de graficos, 0 : graficos 2 vars, 1 : grafico da posicao, 2 :
	// grafico da velocidade
	private byte[][][] axis = new byte[3][][];

	/*
	 * byte[ind][k][3], 3 -> (x,y,isMedia)
	 * 
	 * @param ind tipo de grafico, 0 : graficos 2 vars, 1 : grafico da posicao,
	 * 2 : grafico da velocidade
	 * 
	 * @param k indice da localizacao do grafico
	 * 
	 * @param name nome do grafico do tipo: x vs y
	 */
	private void serieRelateAxis(String name, int k, byte ind) {
		String[] str = name.split(" vs ");
		axis[ind][k][2] = 0;
		if (str[0].indexOf("<") != -1) {
			axis[ind][k][2] = 1;
			str[0] = str[0].substring(str[0].indexOf("<") + 1, str[0].indexOf(">"));
		}
		if (str[1].indexOf("<") != -1) {
			axis[ind][k][2] = 1;
			str[1] = str[1].substring(str[1].indexOf("<") + 1, str[1].indexOf(">"));
		}

		for (int i = 0; i < 2; i++) {
			if (str[i].equalsIgnoreCase("X"))
				axis[ind][k][i] = 2;// 0;
			if (str[i].equalsIgnoreCase("Y"))
				axis[ind][k][i] = 4;// 2;
			if (str[i].equalsIgnoreCase("Z"))
				axis[ind][k][i] = 6;// 4;
			else if (str[i].equalsIgnoreCase("X^2"))
				axis[ind][k][i] = 8;// 6;
			else if (str[i].equalsIgnoreCase("Y^2"))
				axis[ind][k][i] = 10;// 8;
			else if (str[i].equalsIgnoreCase("Z^2"))
				axis[ind][k][i] = 12;// 10;
			else if (str[i].equalsIgnoreCase("| r |^2"))
				axis[ind][k][i] = 14;// 12;
			else if (str[i].equalsIgnoreCase("Vx"))
				axis[ind][k][i] = 3;// 1;
			else if (str[i].equalsIgnoreCase("Vy"))
				axis[ind][k][i] = 5;// 3;
			else if (str[i].equalsIgnoreCase("Vz"))
				axis[ind][k][i] = 7;// 5;
			else if (str[i].equalsIgnoreCase("Vx^2"))
				axis[ind][k][i] = 9;// 7;
			else if (str[i].equalsIgnoreCase("Vy^2"))
				axis[ind][k][i] = 11;// 9;
			else if (str[i].equalsIgnoreCase("Vz^2"))
				axis[ind][k][i] = 13;// 11;
			else if (str[i].equalsIgnoreCase("| v |^2"))
				axis[ind][k][i] = 15;// 13;
			else if (str[i].equalsIgnoreCase("t"))
				axis[ind][k][i] = 18;// 16;
			else if (str[i].equalsIgnoreCase("| r |"))
				axis[ind][k][i] = 16;// 14;
			else if (str[i].equalsIgnoreCase("| v |"))
				axis[ind][k][i] = 17;// 15;

			detectRepetition(axis, ind, k, i);
		}
	}

	private static void detectRepetition(byte[][][] axis, byte ind, int k, int i) {
		for (int j = 0; j <= ind; j++)
			for (int l = 0; l < axis[j].length; l++)
				for (int q = 0; q < 2; q++)
					if (axis[j][l][2] == axis[ind][k][2] && axis[j][l][q] == axis[ind][k][i]
							&& (j != ind || l != k || q != i)) {
						axis[ind][k][i] *= -1; // identifica repeticoes
						return;
					}
	}

	public static void appendValueToSerie(XYSeries serie, float[] fx, float[] fy) {
		for (int i = 0; i < fx.length; i++)
			serie.add(fx[i], fy[i]);
	}

	/*
	 * //NAO USADO public void appendValueToSerie(XYSeries serie, byte [] _axis,
	 * int _currentSample){ double x = 0; double y = 0; for (int i = 0; i <
	 * numPart; i++){ if (_axis[0] < 8){ if (_axis[2] == 1) x +=
	 * state[i][_axis[0]-2]; else x = state[i][_axis[0]-2]; } else if (_axis[0]
	 * < 14){ if (_axis[2] == 1) x += Math.pow(state[i][_axis[0]-8],2); else x =
	 * Math.pow(state[i][_axis[0]-8],2); } else { switch (_axis[0]){ case 14 :
	 * if (_axis[2] == 1) x +=
	 * Math.pow(state[i][0],2)+Math.pow(state[i][2],2)+Math.pow(state[i][4],2);
	 * else x =
	 * Math.pow(state[i][0],2)+Math.pow(state[i][2],2)+Math.pow(state[i][4],2);
	 * break; case 16 : if (_axis[2] == 1) x +=
	 * Math.sqrt(Math.pow(state[i][0],2)
	 * +Math.pow(state[i][2],2)+Math.pow(state[i][4],2)); else x =
	 * Math.sqrt(Math
	 * .pow(state[i][0],2)+Math.pow(state[i][2],2)+Math.pow(state[i][4],2));
	 * break; case 18 : if (_axis[2] == 1) x += _currentSample+1; else x =
	 * _currentSample+1; break; case 15 : if (_axis[2] == 1) x +=
	 * Math.pow(state[i][1],2)+Math.pow(state[i][3],2)+Math.pow(state[i][5],2);
	 * else x =
	 * Math.pow(state[i][1],2)+Math.pow(state[i][3],2)+Math.pow(state[i][5],2);
	 * break; case 17 : if (_axis[2] == 1) x +=
	 * Math.sqrt(Math.pow(state[i][1],2)
	 * +Math.pow(state[i][3],2)+Math.pow(state[i][5],2)); else x =
	 * Math.sqrt(Math
	 * .pow(state[i][1],2)+Math.pow(state[i][3],2)+Math.pow(state[i][5],2)); } }
	 * 
	 * if (_axis[1] < 8){ if (_axis[2] == 1) y += state[i][_axis[1]-2]; else y =
	 * state[i][_axis[1]-2]; } else if (_axis[1] < 14){ if (_axis[2] == 1) y +=
	 * Math.pow(state[i][_axis[1]-8],2); else y =
	 * Math.pow(state[i][_axis[1]-8],2); } else { switch (_axis[1]){ case 14 :
	 * if (_axis[2] == 1) y +=
	 * Math.pow(state[i][0],2)+Math.pow(state[i][2],2)+Math.pow(state[i][4],2);
	 * else y =
	 * Math.pow(state[i][0],2)+Math.pow(state[i][2],2)+Math.pow(state[i][4],2);
	 * break; case 16 : if (_axis[2] == 1) y +=
	 * Math.sqrt(Math.pow(state[i][0],2)
	 * +Math.pow(state[i][2],2)+Math.pow(state[i][4],2)); else y =
	 * Math.sqrt(Math
	 * .pow(state[i][0],2)+Math.pow(state[i][2],2)+Math.pow(state[i][4],2));
	 * break; case 18 : if (_axis[2] == 1) y += _currentSample+1; else y =
	 * _currentSample+1; break; case 15 : if (_axis[2] == 1) y +=
	 * Math.pow(state[i][1],2)+Math.pow(state[i][3],2)+Math.pow(state[i][5],2);
	 * else y =
	 * Math.pow(state[i][1],2)+Math.pow(state[i][3],2)+Math.pow(state[i][5],2);
	 * break; case 17 : if (_axis[2] == 1) y +=
	 * Math.sqrt(Math.pow(state[i][1],2)
	 * +Math.pow(state[i][3],2)+Math.pow(state[i][5],2)); else y =
	 * Math.sqrt(Math
	 * .pow(state[i][1],2)+Math.pow(state[i][3],2)+Math.pow(state[i][5],2)); } }
	 * if (_axis[2] != 1) serie.add(x,y);
	 * 
	 * } if (_axis[2] == 1) serie.add(x/numPart,y/numPart);
	 * 
	 * }
	 * 
	 * //NAO USADO private float [][] getValues(byte [] _axis, int
	 * _currentSample){ if (_axis[2] == 1){ double x = 0; double y = 0; for (int
	 * i = 0; i < numPart; i++){ if (_axis[0] < 8){ x += state[i][_axis[0]-2]; }
	 * else if (_axis[0] < 14){ x += Math.pow(state[i][_axis[0]-8],2); } else {
	 * switch (_axis[0]){ case 14 : x +=
	 * Math.pow(state[i][0],2)+Math.pow(state[i][2],2)+Math.pow(state[i][4],2);
	 * break; case 16 : x +=
	 * Math.sqrt(Math.pow(state[i][0],2)+Math.pow(state[i][
	 * 2],2)+Math.pow(state[i][4],2)); break; case 18 : x += _currentSample+1;
	 * break; case 15 : x +=
	 * Math.pow(state[i][1],2)+Math.pow(state[i][3],2)+Math.pow(state[i][5],2);
	 * break; case 17 : x +=
	 * Math.sqrt(Math.pow(state[i][1],2)+Math.pow(state[i][
	 * 3],2)+Math.pow(state[i][5],2)); } }
	 * 
	 * if (_axis[1] < 8){ y += state[i][_axis[1]-2]; } else if (_axis[1] < 14){
	 * y += Math.pow(state[i][_axis[1]-8],2); } else { switch (_axis[1]){ case
	 * 14 : y +=
	 * Math.pow(state[i][0],2)+Math.pow(state[i][2],2)+Math.pow(state[i][4],2);
	 * break; case 16 : y +=
	 * Math.sqrt(Math.pow(state[i][0],2)+Math.pow(state[i][
	 * 2],2)+Math.pow(state[i][4],2)); break; case 18 : y += _currentSample+1;
	 * break; case 15 : y +=
	 * Math.pow(state[i][1],2)+Math.pow(state[i][3],2)+Math.pow(state[i][5],2);
	 * break; case 17 : y +=
	 * Math.sqrt(Math.pow(state[i][1],2)+Math.pow(state[i][
	 * 3],2)+Math.pow(state[i][5],2)); } } } return new
	 * float[][]{{(float)(x/numPart),(float)(y/numPart)}}; } else { float [][] f
	 * = new float[numPart][2]; double x = 0; double y = 0; for (int i = 0; i <
	 * numPart; i++){ if (_axis[0] < 8){ if (_axis[2] == 1) x +=
	 * state[i][_axis[0]-2]; else x = state[i][_axis[0]-2]; } else if (_axis[0]
	 * < 14){ if (_axis[2] == 1) x += Math.pow(state[i][_axis[0]-8],2); else x =
	 * Math.pow(state[i][_axis[0]-8],2); } else { switch (_axis[0]){ case 14 :
	 * if (_axis[2] == 1) x +=
	 * Math.pow(state[i][0],2)+Math.pow(state[i][2],2)+Math.pow(state[i][4],2);
	 * else x =
	 * Math.pow(state[i][0],2)+Math.pow(state[i][2],2)+Math.pow(state[i][4],2);
	 * break; case 16 : if (_axis[2] == 1) x +=
	 * Math.sqrt(Math.pow(state[i][0],2)
	 * +Math.pow(state[i][2],2)+Math.pow(state[i][4],2)); else x =
	 * Math.sqrt(Math
	 * .pow(state[i][0],2)+Math.pow(state[i][2],2)+Math.pow(state[i][4],2));
	 * break; case 18 : if (_axis[2] == 1) x += _currentSample+1; else x =
	 * _currentSample+1; break; case 15 : if (_axis[2] == 1) x +=
	 * Math.pow(state[i][1],2)+Math.pow(state[i][3],2)+Math.pow(state[i][5],2);
	 * else x =
	 * Math.pow(state[i][1],2)+Math.pow(state[i][3],2)+Math.pow(state[i][5],2);
	 * break; case 17 : if (_axis[2] == 1) x +=
	 * Math.sqrt(Math.pow(state[i][1],2)
	 * +Math.pow(state[i][3],2)+Math.pow(state[i][5],2)); else x =
	 * Math.sqrt(Math
	 * .pow(state[i][1],2)+Math.pow(state[i][3],2)+Math.pow(state[i][5],2)); } }
	 * 
	 * if (_axis[1] < 8){ if (_axis[2] == 1) y += state[i][_axis[1]-2]; else y =
	 * state[i][_axis[1]-2]; } else if (_axis[1] < 14){ if (_axis[2] == 1) y +=
	 * Math.pow(state[i][_axis[1]-8],2); else y =
	 * Math.pow(state[i][_axis[1]-8],2); } else { switch (_axis[1]){ case 14 :
	 * if (_axis[2] == 1) y +=
	 * Math.pow(state[i][0],2)+Math.pow(state[i][2],2)+Math.pow(state[i][4],2);
	 * else y =
	 * Math.pow(state[i][0],2)+Math.pow(state[i][2],2)+Math.pow(state[i][4],2);
	 * break; case 16 : if (_axis[2] == 1) y +=
	 * Math.sqrt(Math.pow(state[i][0],2)
	 * +Math.pow(state[i][2],2)+Math.pow(state[i][4],2)); else y =
	 * Math.sqrt(Math
	 * .pow(state[i][0],2)+Math.pow(state[i][2],2)+Math.pow(state[i][4],2));
	 * break; case 18 : if (_axis[2] == 1) y += _currentSample+1; else y =
	 * _currentSample+1; break; case 15 : if (_axis[2] == 1) y +=
	 * Math.pow(state[i][1],2)+Math.pow(state[i][3],2)+Math.pow(state[i][5],2);
	 * else y =
	 * Math.pow(state[i][1],2)+Math.pow(state[i][3],2)+Math.pow(state[i][5],2);
	 * break; case 17 : if (_axis[2] == 1) y +=
	 * Math.sqrt(Math.pow(state[i][1],2)
	 * +Math.pow(state[i][3],2)+Math.pow(state[i][5],2)); else y =
	 * Math.sqrt(Math
	 * .pow(state[i][1],2)+Math.pow(state[i][3],2)+Math.pow(state[i][5],2)); } }
	 * f[i][0] = (float)x; f[i][1] = (float)y; } return f; } }
	 */
	private float[] getValue(byte[] _axis, int _currentSample, int ind) {
		if (_axis[2] == 1) {
			double x = 0;
			double y = 0;
			for (int i = 0; i < numPart; i++) {
				if (_axis[ind] < 8) {
					x += state[i][_axis[ind] - 2];
				} else if (_axis[ind] < 14) {
					x += Math.pow(state[i][_axis[ind] - 8], 2);
				} else {
					switch (_axis[ind]) {
					case 14:
						x += Math.pow(state[i][0], 2) + Math.pow(state[i][2], 2) + Math.pow(state[i][4], 2);
						break;
					case 16:
						x += Math.sqrt(Math.pow(state[i][0], 2) + Math.pow(state[i][2], 2) + Math.pow(state[i][4], 2));
						break;
					case 18:
						x += _currentSample + 1;
						break;
					case 15:
						x += Math.pow(state[i][1], 2) + Math.pow(state[i][3], 2) + Math.pow(state[i][5], 2);
						break;
					case 17:
						x += Math.sqrt(Math.pow(state[i][1], 2) + Math.pow(state[i][3], 2) + Math.pow(state[i][5], 2));
					}
				}
			}
			return new float[] { (float) (x / numPart) };
		} else {
			float[] f = new float[numPart];
			double x = 0;
			double y = 0;
			for (int i = 0; i < numPart; i++) {
				if (_axis[ind] < 8) {
					if (_axis[2] == 1)
						x += state[i][_axis[ind] - 2];
					else
						x = state[i][_axis[ind] - 2];
				} else if (_axis[ind] < 14) {
					if (_axis[2] == 1)
						x += Math.pow(state[i][_axis[ind] - 8], 2);
					else
						x = Math.pow(state[i][_axis[ind] - 8], 2);
				} else {
					switch (_axis[ind]) {
					case 14:
						if (_axis[2] == 1)
							x += Math.pow(state[i][0], 2) + Math.pow(state[i][2], 2) + Math.pow(state[i][4], 2);
						else
							x = Math.pow(state[i][0], 2) + Math.pow(state[i][2], 2) + Math.pow(state[i][4], 2);
						break;
					case 16:
						if (_axis[2] == 1)
							x += Math.sqrt(Math.pow(state[i][0], 2) + Math.pow(state[i][2], 2)
									+ Math.pow(state[i][4], 2));
						else
							x = Math.sqrt(Math.pow(state[i][0], 2) + Math.pow(state[i][2], 2)
									+ Math.pow(state[i][4], 2));
						break;
					case 18:
						if (_axis[2] == 1)
							x += _currentSample + 1;
						else
							x = _currentSample + 1;
						break;
					case 15:
						if (_axis[2] == 1)
							x += Math.pow(state[i][1], 2) + Math.pow(state[i][3], 2) + Math.pow(state[i][5], 2);
						else
							x = Math.pow(state[i][1], 2) + Math.pow(state[i][3], 2) + Math.pow(state[i][5], 2);
						break;
					case 17:
						if (_axis[2] == 1)
							x += Math.sqrt(Math.pow(state[i][1], 2) + Math.pow(state[i][3], 2)
									+ Math.pow(state[i][5], 2));
						else
							x = Math.sqrt(Math.pow(state[i][1], 2) + Math.pow(state[i][3], 2)
									+ Math.pow(state[i][5], 2));
					}
				}

				f[i] = (float) x;
			}
			return f;
		}
	}

}
