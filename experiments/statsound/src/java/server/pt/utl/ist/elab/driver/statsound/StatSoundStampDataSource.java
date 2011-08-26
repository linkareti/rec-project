/*
 * StatSoundStampDataSource.java
 *
 * Created on 10 de Outubro de 2003, 19:38
 */

package pt.utl.ist.elab.driver.statsound;

import static pt.utl.ist.elab.driver.statsound.StatSoundStampDriver.EXPERIMENT_TYPE_PARAMETER;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.media.Control;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;
import pt.utl.ist.elab.driver.statsound.processors.StampStatSoundProcessor;
import pt.utl.ist.elab.driver.statsound.processors.StampStatSoundTempProcessor;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.synch.FrequencyDefType;
import com.linkare.rec.impl.data.PhysicsValueFactory;
import com.linkare.rec.jmf.media.datasink.capture.ChannelDataFrame;
import com.linkare.rec.jmf.media.datasink.capture.Handler;
import com.linkare.rec.jmf.media.protocol.function.FunctorControl;
import com.linkare.rec.jmf.media.protocol.function.FunctorType;
import com.linkare.rec.jmf.media.protocol.function.FunctorTypeControl;

/**
 * 
 * @author José Pedro Pereira - Linkare TI & André
 */
public class StatSoundStampDataSource extends AbstractStampDataSource {

	private final int POSITION_INDEX = 0;

	private final int VRMS1_INDEX = 1;

	private final int VRMS2_INDEX = 2;

	private final int WAVE1_INDEX = 3;

	private final int WAVE2_INDEX = 4;

	private final int FREQUENCY_INDEX = 5;

	private boolean expEnded = true;

	public static final String STATSOUND_I_VARY_PISTON = "Vary Piston";

	public static final String STATSOUND_II_VARY_FREQUENCY = "Vary Freq";

	public static final String SOUND_VELOCITY = "Sound Vel";

	private SoundWaveType waveForm = SoundWaveType.SIN;

	private int freqIni;

	private int nSamples;

	Integer temp = null;

	private double step = 1;

	private double stepInHardware = 1;

	private HardwareAcquisitionConfig config;

	PhysicsValue[] oldValues = new PhysicsValue[7];

	Object syncWait = new Object();

	private Handler soundCaptureDevice;

	// new stuff:
	private Control control;

	private int numberOfInvocationsToHardware;

	private int numberOfPosReceivedFromHardware;

	private boolean ignore = true;

	private int pistonStart;

	private static final Logger LOGGER = Logger.getLogger(StatSoundStampDriver.class.getName());

	private static final int VRMS_CAPTURE_SAMPLES = 1024;

	/** Creates a new instance of RadioactividadeStampDataSource */
	public StatSoundStampDataSource() {
	}

	@Override
	public void processDataCommand(final StampCommand cmd) {
		if (cmd == null || !cmd.isData() || cmd.getCommandIdentifier() == null) {
			LOGGER.log(Level.FINEST, "Return from process data...cmd isn't valid");
			return;
		}

		if (cmd.getCommandIdentifier().equals(StampStatSoundProcessor.COMMAND_IDENTIFIER)) {
			if (numberOfPosReceivedFromHardware >= numberOfInvocationsToHardware) {
				LOGGER.log(Level.FINEST, "Hardware is too friendly... sending me more data than I asked!!! Bye bye!");
				return;
			}

			ignore = !ignore;
			if (ignore) {
				LOGGER.fine("Ignoring the 2nd buggy result from hardware");
				return;
			}

			final String experimentTypeParameter = config.getSelectedHardwareParameterValue(EXPERIMENT_TYPE_PARAMETER);
			final TypeOfExperiment typeOfExperiment = TypeOfExperiment.from(experimentTypeParameter);
			// int position = (Integer)
			// cmd.getCommandData(StampStatSoundProcessor.COMMAND_IDENTIFIER);
			int nextPosition = getNextPosition();
			numberOfPosReceivedFromHardware++;
			final double frequencyInHz = getDesiredFrequencyFromConfig();
			switch (typeOfExperiment) {
			case STATSOUND_VARY_PISTON:
				handleProtocolVaryPiston(nextPosition, frequencyInHz);
				break;
			case STATSOUND_VARY_FREQUENCY:
				handleProtocolVaryFrequency(nextPosition, frequencyInHz);
				break;
			case SOUND_VELOCITY:
				handleProtocolSoundVelocity(nextPosition, frequencyInHz);
				break;
			}
			if (numberOfPosReceivedFromHardware >= numberOfInvocationsToHardware) {
				finishedMyJob();
			}
		} else if (cmd.getCommandIdentifier().equals(StampStatSoundTempProcessor.COMMAND_IDENTIFIER)) {
			try {
				// TODO: What?!!!!!
				temp = Integer.valueOf(cmd.getCommand().split(" ")[0]); // cmd.getCommandData(StampStatSoundTempProcessor.COMMAND_IDENTIFIER);
			} catch (final Exception e) {
				temp = null;
				LOGGER.log(Level.INFO, "Exception on temp if");
				e.printStackTrace();
				return;
			}
		}
	}

	private double getDesiredFrequencyFromConfig() {
		double frequencyInHz = config.getSelectedFrequency().getFrequency()
				* config.getSelectedFrequency().getMultiplier().getExpValue();
		if (config.getSelectedFrequency().getFrequencyDefType().getValue() == FrequencyDefType.SamplingIntervalType
				.getValue()) {
			frequencyInHz = 1. / frequencyInHz;
		}
		return frequencyInHz;
	}

	private void handleProtocolSoundVelocity(final int pos, final double frequencyInHz) {
		newWaveTypeOnFunctorControl().setFrequency(freqIni);
		waitBeforeCapture();

		ChannelDataFrame channelDataFrame = soundCaptureDevice.captureFrame(nSamples, frequencyInHz, true);

		for (int i = 0; i < nSamples; i++) {
			double wave1 = channelDataFrame.getChannelData(0)[i];
			double wave2 = channelDataFrame.getChannelData(1)[i];
			PhysicsValue[] values = fillInValues(pos, null, null, wave1, wave2, freqIni);
			super.addDataRow(values);
		}
	}

	private void handleProtocolVaryFrequency(final int pos, final double frequencyInHz) {
		final FunctorControl functorControl = newWaveTypeOnFunctorControl();
		double currentValueFreq = freqIni;
		// This is similar to the simpler iteration from freqInit till the end,
		// adding the step. However, iterating through the samples using an
		// integer guarantees precision (notice that the frequency is a double
		// type).
		for (int i = 0; i < nSamples; i++) {
			currentValueFreq = freqIni + (i * step);
			functorControl.setFrequency(currentValueFreq);

			waitBeforeCapture();
			ChannelDataFrame channelDataFrame = soundCaptureDevice.captureFrame(VRMS_CAPTURE_SAMPLES, frequencyInHz,
					false);
			double channelVRMS1 = channelDataFrame.getChannelVRMS(0);
			double channelVRMS2 = channelDataFrame.getChannelVRMS(1);

			PhysicsValue[] values = fillInValues(pos, channelVRMS1, channelVRMS2, null, null, currentValueFreq);
			super.addDataRow(values);
		}
	}

	private void handleProtocolVaryPiston(final int pos, final double frequencyInHz) {
		newWaveTypeOnFunctorControl().setFrequency(freqIni);
		waitBeforeCapture();
		ChannelDataFrame channelDataFrame = soundCaptureDevice.captureFrame(VRMS_CAPTURE_SAMPLES, frequencyInHz, false);
		double channelVRMS1 = channelDataFrame.getChannelVRMS(0);
		double channelVRMS2 = channelDataFrame.getChannelVRMS(1);
		PhysicsValue[] values = fillInValues(pos, channelVRMS1, channelVRMS2, null, null, freqIni);
		super.addDataRow(values);
	}

	/**
	 * This method changes the wave type associated to the functorControl which
	 * has been initiated in the driver with a silence wave type. This means
	 * that, by simply changing the wave type, the expected sound will come up.
	 */
	private FunctorControl newWaveTypeOnFunctorControl() {
		FunctorTypeControl functorTypeControl = (FunctorTypeControl) control;
		functorTypeControl.setFunctorType(waveForm.getFunctorType());
		return functorTypeControl.getFunctorControl();
	}

	private void waitBeforeCapture() {
		// FIXME - We should calculate how much time for the sound player buffer
		// to get empty...
		// this should be calculated in terms of SOUND_GENERATION_BUFFER_SIZE /
		// SOUND_GENERATION_FREQUENCY
		try {
			// Wait for buffer to empty and generate a new with the correct
			// frequency
			Thread.sleep(250);
			// Wait for wave to play for at least a while so that we don't catch
			// the train running
			Thread.sleep(soundCaptureDevice.getDeltaTime());
		} catch (InterruptedException e) {
			// FIXME - Handle this correctly
			LOGGER.warning("Unable to wait for sound stability...");
			this.stopNow();
		}
	}

	private PhysicsValue[] fillInValues(final int position, final Double channelVRMS1, final Double channelVRMS2,
			final Double wave1, final Double wave2, final double frequency) {
		PhysicsValue[] values = new PhysicsValue[config.getChannelsConfig().length];
		values[POSITION_INDEX] = PhysicsValueFactory.fromInt(position, config.getChannelsConfig(POSITION_INDEX)
				.getSelectedScale());
		values[VRMS1_INDEX] = channelVRMS1 == null ? null : PhysicsValueFactory.fromDouble(channelVRMS1, config
				.getChannelsConfig(VRMS1_INDEX).getSelectedScale().getMultiplier());
		values[VRMS2_INDEX] = channelVRMS1 == null ? null : PhysicsValueFactory.fromDouble(channelVRMS2, config
				.getChannelsConfig(VRMS2_INDEX).getSelectedScale().getMultiplier());
		values[WAVE1_INDEX] = wave1 == null ? null : PhysicsValueFactory.fromDouble(wave1,
				config.getChannelsConfig(WAVE1_INDEX).getSelectedScale());
		values[WAVE2_INDEX] = wave2 == null ? null : PhysicsValueFactory.fromDouble(wave2,
				config.getChannelsConfig(WAVE2_INDEX).getSelectedScale());
		values[FREQUENCY_INDEX] = PhysicsValueFactory.fromDouble(frequency, config.getChannelsConfig(FREQUENCY_INDEX)
				.getSelectedScale());
		return values;
	}

	@Override
	public void setAcquisitionHeader(final HardwareAcquisitionConfig config) {
		this.config = config;
		super.setAcquisitionHeader(config);
	}

	public boolean isExpEnded() {
		return expEnded;
	}

	public void setExpEnded(final boolean expEnded) {
		synchronized (syncWait) {
			this.expEnded = expEnded;
			syncWait.notifyAll();
		}
		LOGGER.log(Level.FINEST, "Data source set exp ended done! (" + expEnded + ")");
	}

	public void setWaveForm(final SoundWaveType waveForm) {
		this.waveForm = waveForm;
	}

	public void setFreqIni(final int freqIni) {
		this.freqIni = freqIni;
	}

	public void setFreqStep(final double step) {
		this.step = step;
	}

	/**
	 * @param nSamples the nSamples to set
	 */
	public void setNSamples(int nSamples) {
		this.nSamples = nSamples;
	}

	/**
	 * @return the control
	 */
	public Control getControl() {
		return control;
	}

	/**
	 * @param control the control to set
	 */
	public void setControl(Control control) {
		this.control = control;
	}

	@Override
	public void stopNow() {
		expEnded = true;
		setDataSourceStoped();
	}

	/**
	 * @param soundCaptureDevice
	 */
	public void setCaptureDevice(Handler soundCaptureDevice) {
		this.soundCaptureDevice = soundCaptureDevice;

	}

	/**
	 * The datasource should only state that it has finished its job. The
	 * driver, itself, is the only one that should really stop the experiment.
	 * This means that the driver should never invoke directly the method
	 * <code>setDataSourceEnded()</code>. However, the driver must know if the
	 * datasource has finished its job or not. Therefore, the datasource invokes
	 * this method after any handler method so that the internal variable
	 * <code>expEnded</code> may be set to true.
	 */
	private void finishedMyJob() {
		expEnded = true;
		playSilence();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDataSourceEnded() {
		super.setDataSourceEnded();
		expEnded = true;
	}

	private void playSilence() {
		FunctorTypeControl functorTypeControl = (FunctorTypeControl) control;
		if (control != null) {
			functorTypeControl.setFunctorType(FunctorType.SILENCE);
		}
	}

	/**
	 * @param numberOfInvocationsToHardware
	 */
	public void setNumberOfInvocationsToHardware(int numberOfInvocationsToHardware) {
		this.numberOfInvocationsToHardware = numberOfInvocationsToHardware;
	}

	/**
	 * @param stepInHardware
	 */
	public void setStepInHardware(double stepInHardware) {
		this.stepInHardware = stepInHardware;
	}

	/**
	 * @return the stepInHardware
	 */
	public double getStepInHardware() {
		return stepInHardware;
	}

	/**
	 * @return the numberOfInvocationsToHardware
	 */
	public int getNumberOfInvocationsToHardware() {
		return numberOfInvocationsToHardware;
	}

	/**
	 * @return the numberOfPosReceivedFromHardware
	 */
	public int getNumberOfPosReceivedFromHardware() {
		return numberOfPosReceivedFromHardware;
	}

	/**
	 * @param pistonStart
	 */
	public void setPistonStart(int pistonStart) {
		this.pistonStart = pistonStart;
	}

	public int getNextPosition() {
		double stepForInvocationNumber = stepInHardware * numberOfPosReceivedFromHardware;
		return (int) (pistonStart + stepForInvocationNumber);
	}
}