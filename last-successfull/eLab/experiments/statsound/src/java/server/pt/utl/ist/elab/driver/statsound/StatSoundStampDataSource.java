/*
 * StatSoundStampDataSource.java
 *
 * Created on 10 de Outubro de 2003, 19:38
 */

package pt.utl.ist.elab.driver.statsound;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;
import pt.utl.ist.elab.driver.statsound.audio.DataSoundListener;
import pt.utl.ist.elab.driver.statsound.audio.NewDataBufferEvent;
import pt.utl.ist.elab.driver.statsound.audio.SoundRecorder;
import pt.utl.ist.elab.driver.statsound.audio.SoundThread;
import pt.utl.ist.elab.driver.statsound.processors.StampStatSoundProcessor;
import pt.utl.ist.elab.driver.statsound.processors.StampStatSoundTempProcessor;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValueFactory;

/**
 * 
 * @author José Pedro Pereira - Linkare TI & André
 */
public class StatSoundStampDataSource extends AbstractStampDataSource implements DataSoundListener {

	private int counter = 0;
	private int total_samples = 0;

	private boolean expEnded = true;

	public static final String TYPE_OF_EXP = "Type of experiment";
	public static final String STATSOUND_I_VARY_PISTON = "Vary Piston";
	public static final String STATSOUND_II_VARY_FREQUENCY = "Vary Freq";
	public static final String SOUND_VELOCITY = "Sound Vel";
	public static final String FREQ_INI = "Frequency start";
	public static final String FREQ_END = "Frequency end";
	public static final String POS_INI = "Piston start";
	public static final String WAVE_FORM = "Wave form";
	public static final String N_POINTS = "Number of points";

	private int waveForm = 0;
	private int posIni = 0;
	private int posFin = 0;
	private int freqIni = 0;
	private int freqFin = 0;
	private int nPoints = 0;

	Integer temp = null;

	private double step = 1;

	private HardwareAcquisitionConfig config;

	private boolean firstTime = true;

	private boolean fileExp = false;

	private boolean rmsAvailable = false;

	PhysicsValue[] oldValues = new PhysicsValue[7];

	private SoundRecorder sr = null;

	Object syncWait = new Object();

	/** Creates a new instance of RadioactividadeStampDataSource */
	public StatSoundStampDataSource() {
		sr = new SoundRecorder();
		sr.addDataSoundListener(this);
		soundPlaying = false;
	}

	public void processDataCommand(StampCommand cmd) {
		if (cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null) {
			Logger.getLogger("StampDriver.Logger").log(Level.FINEST, "Return from process data...cmd isn't valid");
			return;
		}

		PhysicsValue[] values = new PhysicsValue[7];

		if (cmd.getCommandIdentifier().equals(StampStatSoundProcessor.COMMAND_IDENTIFIER)) {
			if (config.getSelectedHardwareParameterValue(TYPE_OF_EXP).equalsIgnoreCase(STATSOUND_I_VARY_PISTON)) {

				final Object commandData = cmd.getCommandData(StampStatSoundProcessor.COMMAND_IDENTIFIER);
				Integer pos = null;
				try {
					pos = Integer.valueOf(String.valueOf(commandData));
				} catch (NumberFormatException e) {
					Logger.getLogger("StampDriver.Logger").log(Level.WARNING, "Error getting the position");
					e.printStackTrace();
					return;
				}

				if (!soundPlaying) {
					playSinWave(freqIni, freqFin, config.getTotalSamples(), 0);
					soundPlaying = true;
				}

				int posValor = pos.intValue();

				try {
					synchronized (syncWait) {
						Thread.sleep(200);

						while (!rmsAvailable && !expEnded) {
							syncWait.wait();
						}
						rmsAvailable = false;
					}
				} catch (InterruptedException ie) {
				}

				double rmsLeftValor = sr.getRMS(SoundRecorder.LEFT_CHANNEL);
				double rmsRightValor = sr.getRMS(SoundRecorder.RIGHT_CHANNEL);

				sr.resetRMS();

				values[0] = PhysicsValueFactory.fromInt(posValor, config.getChannelsConfig(0).getSelectedScale());
				values[1] = PhysicsValueFactory.fromDouble(freqIni, config.getChannelsConfig(1).getSelectedScale());
				values[3] = PhysicsValueFactory.fromDouble(rmsRightValor, config.getChannelsConfig(2)
						.getSelectedScale());
				values[4] = PhysicsValueFactory
						.fromDouble(rmsLeftValor, config.getChannelsConfig(3).getSelectedScale());

				if (Math.abs(values[3].getValue().getDoubleValue()) > 0.01d
						&& Math.abs(values[4].getValue().getDoubleValue()) > 0.01d) {
					super.addDataRow(values);
				} else {
					super.addDataRow(values);
				}

				counter++;
				if (counter == total_samples) {
					setDataSourceEnded();
				}
			} else if (config.getSelectedHardwareParameterValue(TYPE_OF_EXP).startsWith(STATSOUND_II_VARY_FREQUENCY)) {
				if (!soundPlaying) {
					playSinWave(freqIni, freqFin, (int) Math.round(config.getTotalSamples() / 4.0), 4);
					soundPlaying = true;
				}
				try {
					// initial time till acquisition
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (double f = freqIni; f <= freqFin; f += step) {
					Logger.getLogger("StampDriver.Logger").log(Level.INFO, "Exp2 for loop");
					if (expEnded) {
						soundPlaying = false;
						stopPlaying();
						stopAcquiring();
						setDataSourceEnded();
						break;
					}
					try {
						// waiting time between two acquisitions
						Thread.sleep(170);
						synchronized (syncWait) {
							Logger.getLogger("StampDriver.Logger").log(Level.INFO, "Entering syncronized");
							while (!rmsAvailable && !expEnded) {
								Logger.getLogger("StampDriver.Logger").log(Level.INFO,
										STATSOUND_II_VARY_FREQUENCY + " while loop");
								syncWait.wait();
							}
							rmsAvailable = false;
						}
						Logger.getLogger("StampDriver.Logger").log(Level.INFO, "Exiting syncronized");
					} catch (InterruptedException ie) {
					}

					Logger.getLogger("StampDriver.Logger").log(Level.FINEST, "Current frequency=" + f);
					double rmsLeftValor = sr.getRMS(SoundRecorder.LEFT_CHANNEL);
					double rmsRightValor = sr.getRMS(SoundRecorder.RIGHT_CHANNEL);
					sr.resetRMS();

					values[0] = PhysicsValueFactory.fromInt(posIni, config.getChannelsConfig(0).getSelectedScale());
					values[1] = PhysicsValueFactory.fromDouble(f, config.getChannelsConfig(1).getSelectedScale());
					values[3] = PhysicsValueFactory.fromDouble(rmsRightValor, config.getChannelsConfig(2)
							.getSelectedScale());
					values[4] = PhysicsValueFactory.fromDouble(rmsLeftValor, config.getChannelsConfig(3)
							.getSelectedScale());

					if (Math.abs(values[3].getValue().getDoubleValue()) > 0.01d
							&& Math.abs(values[4].getValue().getDoubleValue()) > 0.01d) {
						super.addDataRow(values);
					}
					setFrequency(f);
				}
				expEnded = true;
				setDataSourceEnded();
			} else if (!expEnded) {
				Logger.getLogger("StampDriver.Logger").log(Level.INFO, "Inside no expEnded");
				sr.startAcquiring(true);
				try {
					Thread.sleep(800);
					synchronized (syncWait) {
						Logger.getLogger("StampDriver.Logger").log(Level.INFO, "Entering not expEnded syncronized");
						while (!rmsAvailable && !expEnded) {
							Logger.getLogger("StampDriver.Logger").log(Level.INFO, "While not expEnded syncronized");
							syncWait.wait();
						}
						rmsAvailable = false;
					}
					Logger.getLogger("StampDriver.Logger").log(Level.INFO, "Exiting not expEnded syncronized");
				} catch (InterruptedException ie) {
				}
				sr.stopAcquiring();

				// fpulse
				nPoints = 1000;

				byte[] toSend = new byte[nPoints];
				byte[] acqByte = sr.getAcqBytes();

				int startPoint = acqByte.length / 2 - nPoints / 2;

				if (startPoint < 0) {
					startPoint = 0;
				}

				if ((startPoint + nPoints) > acqByte.length) {
					nPoints = acqByte.length / 2;
				}

				// 44100/100 amostras por milissegundo
				for (int i = 0; i < Math.round(nPoints); i++) {
					values = new PhysicsValue[7];

					String string = "";
					for (int j = 0; j < 4; j++) {
						string = string + " *" + i * 4 + j + "*:" + acqByte[i * 4 + j];
					}

					Logger.getLogger("StampDriver.Logger").log(Level.FINEST, "block " + i + " : " + string);
					double rmsLeftValor = sr.getRMS(SoundRecorder.LEFT_CHANNEL);
					double rmsRightValor = sr.getRMS(SoundRecorder.RIGHT_CHANNEL);
					sr.resetRMS();

					// Piston position
					values[0] = PhysicsValueFactory.fromInt(posIni, config.getChannelsConfig(0).getSelectedScale());
					// Temperature
					values[1] = PhysicsValueFactory.fromDouble(freqIni, config.getChannelsConfig(1).getSelectedScale());
					// RMS
					values[3] = PhysicsValueFactory.fromDouble(rmsRightValor, config.getChannelsConfig(2)
							.getSelectedScale());
					// Wave1
					values[4] = PhysicsValueFactory.fromDouble(
							((double) (acqByte[i * 4 + 1] << 8 | (255 & acqByte[i * 4]))), config.getChannelsConfig(3)
									.getSelectedScale());
					// Wave2
					values[5] = PhysicsValueFactory.fromDouble(
							((double) (acqByte[i * 4 + 3] << 8 | (255 & acqByte[i * 4 + 2]))), config
									.getChannelsConfig(4).getSelectedScale());
					super.addDataRow(values);
				}
				setDataSourceEnded();
				expEnded = true;
			}
		} else if (cmd.getCommandIdentifier().equals(StampStatSoundTempProcessor.COMMAND_IDENTIFIER)) {
			try {
				temp = Integer.valueOf(cmd.getCommand().split(" ")[0]); // cmd.getCommandData(StampStatSoundTempProcessor.COMMAND_IDENTIFIER);
			} catch (Exception e) {
				temp = null;
				Logger.getLogger("StampDriver.Logger").log(Level.INFO, "Exception on temp if");
				e.printStackTrace();
				return;
			}
		}
	}

	public void setAcquisitionHeader(HardwareAcquisitionConfig config) {
		this.config = config;
		super.setAcquisitionHeader(config);
		total_samples = config.getTotalSamples();
	}

	private void setFrequency(double freq) {
		try {
			synchronized (this) {
				// java.io.File file = new java.io.File("/tmp/freq");
				// java.io.FileWriter fw = new java.io.FileWriter(file);
				// fw.write(""+freq);
				// fw.close();
			}
		} catch (Exception ioe) {
		}
	}

	private SoundThread soundBoard = null;
	private boolean soundPlaying = false;

	public void playPulseWave(double freq, int time, int wait) {
		Logger.getLogger("StampDriver.Logger")
				.log(Level.FINEST, "Creating a Sound Thread for a pulse of freq: " + freq);
		soundBoard = new SoundThread(SoundThread.PULSE);
		soundBoard.newLine();
		// fpulse
		soundBoard.configure((float) freq, 0f, time, wait);
		soundBoard.newLine();
		new Thread(soundBoard).start();
	}

	public void playPinkNoise(double freq, int time, int wait) {
		Logger.getLogger("StampDriver.Logger").log(Level.FINEST,
				"Creating a Sound Thread for a pink noise of freq: " + freq);
		soundBoard = new SoundThread(SoundThread.PINK_NOISE);
		soundBoard.newLine();
		soundBoard.configure((float) freq, 0f, time, wait);
		soundBoard.newLine();
		new Thread(soundBoard).start();
	}

	public SoundThread playSinWave(double freqIni, double freqFin, int time, int wait) {
		if (freqFin == 0d)
			freqFin = freqIni;
		if (freqIni == 0d)
			freqIni = freqFin;

		soundPlaying = true;

		Logger.getLogger("StampDriver.Logger").log(Level.FINEST,
				"Creating a Sound Thread for a sounf of freq: " + freqIni + " to: " + freqFin);
		soundBoard = new SoundThread(SoundThread.WAVE);
		soundBoard.newLine();
		soundBoard.configure((float) freqIni, (float) freqFin, time, wait);
		soundBoard.newLine();
		new Thread(soundBoard).start();
		return soundBoard;
	}

	public void stopPlaying() {
		Logger.getLogger("StampDriver.Logger").log(Level.FINEST, "Data source stop playing done!");
	}

	public void startAcquiring(boolean fileExp) {
		this.fileExp = fileExp;
		sr.startAcquiring(fileExp);
	}

	public void stopAcquiring() {
		if (soundBoard != null) {
			soundBoard.stopWave();
		}
		soundPlaying = false;
		if (config.getSelectedHardwareParameterValue(TYPE_OF_EXP).equalsIgnoreCase(SOUND_VELOCITY)) {
			return;
		}
		sr.stopAcquiring();
		Logger.getLogger("StampDriver.Logger").log(Level.FINEST, "Data source stop acquiring done!");
	}

	public boolean isExpEnded() {
		return expEnded;
	}

	public void setExpEnded(boolean expEnded) {
		synchronized (syncWait) {
			this.expEnded = expEnded;
			syncWait.notifyAll();
		}
		Logger.getLogger("StampDriver.Logger").log(Level.FINEST, "Data source set exp ended done! (" + expEnded + ")");
	}

	public void setWaveForm(int waveForm) {
		this.waveForm = waveForm;
	}

	public void setPosIni(int posIni) {
		this.posIni = posIni;
	}

	public void setPosFin(int posFin) {
		this.posFin = posFin;
	}

	public void setFreqIni(int freqIni) {
		this.freqIni = freqIni;
	}

	public void setFreqFin(int freqFin) {
		this.freqFin = freqFin;
	}

	public void setFreqStep(double step) {
		this.step = step;
	}

	public void setNPoints(int nPoints) {
		this.nPoints = nPoints;
	}

	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

	public void bufferAvailable(NewDataBufferEvent evt) {
	}

	public void rmsAvailable() {
		synchronized (syncWait) {
			rmsAvailable = true;
			syncWait.notifyAll();
		}
	}

	public void stopNow() {
		soundPlaying = false;
		expEnded = true;
		setDataSourceStoped();
	}

}
