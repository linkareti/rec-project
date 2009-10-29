/*
 * StatSoundStampDataSource.java
 *
 * Created on 10 de Outubro de 2003, 19:38
 */

package pt.utl.ist.elab.driver.serial.stamp.statsound;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.statsound.audio.DataSoundListener;
import pt.utl.ist.elab.driver.serial.stamp.statsound.audio.NewDataBufferEvent;
import pt.utl.ist.elab.driver.serial.stamp.statsound.audio.SoundRecorder;
import pt.utl.ist.elab.driver.serial.stamp.statsound.audio.SoundThread;
import pt.utl.ist.elab.driver.serial.stamp.statsound.processors.StampStatSoundProcessor;
import pt.utl.ist.elab.driver.serial.stamp.statsound.processors.StampStatSoundTempProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.PhysicsValFactory;
import com.linkare.rec.impl.data.PhysicsValueFactory;

/**
 * 
 * @author jp & Andr�
 */
public class StatSoundStampDataSource extends AbstractStampDataSource implements DataSoundListener {

	private int counter = 0;
	private int total_samples = 0;

	private boolean expEnded = true;

	public static final String TYPE_OF_EXP = "Type of experiment";
	public static final String EXP_1 = "Vary Piston";
	public static final String EXP_2 = "Vary Freq";
	public static final String EXP_3 = "Sound Vel";
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

	private double step = 1;

	private boolean firstTime = true;
	private boolean rmsAvailable = false;
	PhysicsValue[] oldValues = new PhysicsValue[7];

	private SoundRecorder sr = null;

	Object syncWait = new Object();

	/** Creates a new instance of RadioactividadeStampDataSource */
	public StatSoundStampDataSource() {
		sr = new SoundRecorder();
		sr.addDataSoundListener(this);
	}

	public void processDataCommand(StampCommand cmd) {
		Logger.getLogger("StampDriver.Logger").log(Level.INFO, "Entering processDataCommand on StatSoundStampDataSource");
		System.out.println("Entering processDataCommand on StatSoundStampDataSource");
		System.out.println("EXP:= " + config.getSelectedHardwareParameterValue(TYPE_OF_EXP));

		if (cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null) {
			System.out.println("Return from process data...cmd isn't valid");
			return;
		}

		if (freqFin < freqIni) {
			int temp = freqFin;
			freqFin = freqIni;
			freqIni = temp;
		}

		PhysicsValue[] values = new PhysicsValue[7];

		Logger.getLogger("StampDriver.Logger").log(Level.INFO, "Inside processDataCommand using " + cmd.getCommandIdentifier());

		if (cmd.getCommandIdentifier().equals(StampStatSoundProcessor.COMMAND_IDENTIFIER)) {
			/** PISTON EXPERIMENT */
			if (config.getSelectedHardwareParameterValue(TYPE_OF_EXP).equalsIgnoreCase(EXP_1)) {
				Integer pos;
				try {
					pos = (Integer) cmd.getCommandData(StampStatSoundProcessor.COMMAND_IDENTIFIER);
				} catch (ClassCastException e) {
					System.out.println("Error getting the position");
					e.printStackTrace();
					return;
				}

				int posValor = pos.intValue();

				try {
					Logger.getLogger("StampDriver.Logger").log(Level.INFO, "pos1");
					synchronized (syncWait) {
						Logger.getLogger("StampDriver.Logger").log(Level.INFO, "pos2");
						long time1 = System.currentTimeMillis();
						Thread.currentThread().sleep(200);

						while (!rmsAvailable && !expEnded) {
							Logger.getLogger("StampDriver.Logger").log(Level.INFO, "pos3");
							syncWait.wait();
						}
						long time2 = System.currentTimeMillis();

						System.out.println("Waited:" + (time2 - time1));
						rmsAvailable = false;
					}
					Logger.getLogger("StampDriver.Logger").log(Level.INFO, "pos4");
				} catch (InterruptedException ie) {
				}

				double rmsLeftValor = sr.getRMS(sr.LEFT_CHANNEL);
				double rmsRightValor = sr.getRMS(sr.RIGHT_CHANNEL);
				sr.resetRMS();

				values[0] = PhysicsValueFactory.fromInt(posValor, config.getChannelsConfig(0).getSelectedScale());
				values[1] = PhysicsValueFactory.fromDouble(freqIni, config.getChannelsConfig(1).getSelectedScale());
				values[2] = PhysicsValueFactory.fromDouble(rmsRightValor, config.getChannelsConfig(2).getSelectedScale());
				values[3] = PhysicsValueFactory.fromDouble(rmsLeftValor, config.getChannelsConfig(3).getSelectedScale());

				if (values[0].getValue().getFloatValue() != 0f && values[1].getValue().getFloatValue() != 0f && values[2].getValue().getFloatValue() != 0f
						&& values[3].getValue().getFloatValue() != 0f) {
					super.addDataRow(values);
				}
				// super.addDataRow(values);

				counter++;
				if (counter == total_samples)
					setDataSourceEnded();
			}
			/** FREQ EXPERIMENT */
			else if (config.getSelectedHardwareParameterValue(TYPE_OF_EXP).startsWith(EXP_2)) {
				try {
					Thread.currentThread().sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (double f = freqIni; f <= freqFin; f += step) {
					System.out.println("freqIni :" + freqIni + " freqFin :" + freqFin + " step :" + step);
					Logger.getLogger("StampDriver.Logger").log(Level.INFO, "Exp2 for loop");
					if (expEnded) {
						stopPlaying();
						stopAcquiring();
						setDataSourceEnded();
						break;
					}
					try {
						Thread.currentThread().sleep(150);
						synchronized (syncWait) {
							Logger.getLogger("StampDriver.Logger").log(Level.INFO, "Entering syncronized");
							while (!rmsAvailable && !expEnded) {
								Logger.getLogger("StampDriver.Logger").log(Level.INFO, "Exp2 while loop");
								syncWait.wait();
							}
							rmsAvailable = false;
						}
						Logger.getLogger("StampDriver.Logger").log(Level.INFO, "Exiting syncronized");
					}

					catch (InterruptedException ie) {
					}

					System.out.println("Current frequency=" + f);
					double rmsLeftValor = sr.getRMS(sr.LEFT_CHANNEL);
					double rmsRightValor = sr.getRMS(sr.RIGHT_CHANNEL);
					sr.resetRMS();

					values[0] = PhysicsValueFactory.fromInt(posIni, config.getChannelsConfig(0).getSelectedScale());
					values[1] = PhysicsValueFactory.fromDouble(f, config.getChannelsConfig(1).getSelectedScale());
					values[2] = PhysicsValueFactory.fromDouble(rmsRightValor, config.getChannelsConfig(2).getSelectedScale());
					values[3] = PhysicsValueFactory.fromDouble(rmsLeftValor, config.getChannelsConfig(3).getSelectedScale());

					if (Math.abs(values[2].getValue().getDoubleValue()) > 0.01d && Math.abs(values[3].getValue().getDoubleValue()) > 0.01d) {
						System.out.println("Add row : " + values[2].getValue().getFloatValue() + " , " + values[3].getValue().getFloatValue());
						super.addDataRow(values);
					}
					System.out.println("Passed through..." + f);

					setFrequency(f);
				}
				expEnded = true;
				setDataSourceEnded();
			} else if (!expEnded) {
				Logger.getLogger("StampDriver.Logger").log(Level.INFO, "Inside no expEnded");
				sr.startAcquiring(true);
				try {
					Thread.currentThread().sleep(800);
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

				byte[] toSend = new byte[nPoints];
				byte[] acqByte = sr.getAcqBytes();

				int startPoint = acqByte.length / 2 - nPoints / 2;

				if (startPoint < 0) {
					startPoint = 0;
				}

				if ((startPoint + nPoints) > acqByte.length) {
					nPoints = acqByte.length / 2;
				}

				System.arraycopy(acqByte, startPoint, toSend, 0, nPoints);

				values[5] = new PhysicsValue(PhysicsValFactory.fromByteArray(toSend, "sound/wav"), null, com.linkare.rec.data.Multiplier.none);

				super.addDataRow(values);
				setDataSourceEnded();
				expEnded = true;
			}
		} else if (cmd.getCommandIdentifier().equals(StampStatSoundTempProcessor.COMMAND_IDENTIFIER)) {
			Integer temp;
			try {
				temp = Integer.valueOf(cmd.getCommand().split(" ")[0]); // cmd.getCommandData(StampStatSoundTempProcessor.COMMAND_IDENTIFIER);
			} catch (Exception e) {

				Logger.getLogger("StampDriver.Logger").log(Level.INFO, "Exception on temp if");
				e.printStackTrace();
				return;
			}
			int tempValor = temp.intValue();

			values[4] = PhysicsValueFactory.fromInt(tempValor, config.getChannelsConfig(4).getSelectedScale());

			//super.addDataRow(values);
			counter++;
			if (counter == total_samples)
				setDataSourceEnded();
		}
	}

	private HardwareAcquisitionConfig config;

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

	public void playPulseWave(double freq, int time) {
		System.out.println("Creating a Sound Thread for a pink noise of freq: " + freqIni);
		soundBoard = new SoundThread(SoundThread.PULSE);
		soundBoard.newLine();
		soundBoard.configure((float) freqIni, 0f, time);
		soundBoard.newLine();
		new Thread(soundBoard).start();
	}

	public void playPinkNoise(double freq, int time) {
		System.out.println("Creating a Sound Thread for a pink noise of freq: " + freqIni);
		soundBoard = new SoundThread(SoundThread.PINK_NOISE);
		soundBoard.newLine();
		soundBoard.configure((float) freqIni, 0f, time);
		soundBoard.newLine();
		new Thread(soundBoard).start();
	}

	public void playSinWave(double freqIni, double freqFin, int time) {
		if (freqFin == 0d)
			freqFin = freqIni;
		if (freqIni == 0d)
			freqIni = freqFin;

		System.out.println("Creating a Sound Thread for a sounf of freq: " + freqIni + " to: " + freqFin);
		soundBoard = new SoundThread(SoundThread.WAVE);
		soundBoard.newLine();
		soundBoard.configure((float) freqIni, (float) freqFin, time);
		soundBoard.newLine();
		new Thread(soundBoard).start();
	}

	public void stopPlaying() {
		System.out.println("Data source stop playing done!");
	}

	private boolean fileExp = false;

	public void startAcquiring(boolean fileExp) {
		this.fileExp = fileExp;
		sr.startAcquiring(fileExp);
	}

	public void stopAcquiring() {
		if (soundBoard != null) {
			soundBoard.stopWave();
		}
		System.out.println("Trying to stop acquiring!");
		if (config.getSelectedHardwareParameterValue(TYPE_OF_EXP).equalsIgnoreCase(EXP_3)) {
			return;
		}
		System.out.println("Trying to stop acquiring @ 2!");
		sr.stopAcquiring();
		System.out.println("Data source stop acquiring done!");
	}

	public boolean isExpEnded() {
		return expEnded;
	}

	public void setExpEnded(boolean expEnded) {
		synchronized (syncWait) {
			this.expEnded = expEnded;
			syncWait.notifyAll();
		}
		System.out.println("Data source set exp ended done! (" + (expEnded ? "true" : "false") + ")");
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
		expEnded = true;
		setDataSourceStoped();
	}

}
