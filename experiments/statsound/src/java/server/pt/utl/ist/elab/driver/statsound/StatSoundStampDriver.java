/*
 * StatSoundStampDriver.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.statsound;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.format.AudioFormat;
import javax.media.protocol.DataSource;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampTranslatorProcessorManager;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampConfiguredProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampNotConfiguredProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampStartProcessor;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.threading.WaitForConditionResult;
import com.linkare.rec.impl.utils.Defaults;
import com.linkare.rec.jmf.ReCJMFUtils;
import com.linkare.rec.jmf.media.datasink.capture.Handler;
import com.linkare.rec.jmf.media.protocol.function.FunctorTypeControl;

/**
 * 
 * @author José Pedro Pereira - Linkare TI & André
 */
public class StatSoundStampDriver extends AbstractStampDriver {

	// parameters
	public static final String PISTON_START_PARAMETER = "piston.start";
	public static final String PISTON_END_PARAMETER = "piston.end";
	public static final String FREQUENCY_START_PARAMETER = "frequency.start";
	public static final String FREQUENCY_END_PARAMETER = "frequency.end";
	public static final String WAVE_FORM_PARAMETER = "wave.form";
	public static final String EXPERIMENT_TYPE_PARAMETER = "experiment.type";
	public static final String CALIBRATION_PARAMETER = "calibration";
	public static final String STATUS_PARAMETER = "status";

	// command parameters
	public static final String NUMSAMPLES_COMMAND_PART = "number of samples";
	public static final String PISTON_START_COMMAND_PART = PISTON_START_PARAMETER;
	public static final String PISTON_END_COMMAND_PART = PISTON_END_PARAMETER;
	public static final String STATUS_COMMAND_PART = STATUS_PARAMETER;
	public static final String CALIBRATION_COMMAND_PART = CALIBRATION_PARAMETER;

	// other stuff
	private boolean initing = true;

	private boolean waitingStart = false;

	private boolean wroteStart = false;

	private boolean started = false;

	private boolean stoping = false;

	private boolean reseting = true;

	private static final String HARDWARE_INFO_XML = "/HardwareInfo.xml";

	private static final String USER_DIR = "user.dir";

	private static final String HARDWARE_INFO = "HardwareInfo";

	private static final int SAMPLE_RATE = 11025;

	private static final String SILENCE_FUNCTION = "function://" + SAMPLE_RATE + "/16/silence";

	private static final String APPLICATION_NAME_LOCK_PORT = "Stationary Sound Stamp Driver V0.1";

	private static final String DRIVER_UNIQUE_ID = "ELAB_STATSOUND_STAMP_V01";

	private static final int TIME_OUT_PER_PORT = 10000;

	private StampCommand stampConfigCommand = null;

	private int nSamples = 0;

	private int numberOfInvocationsToHardware = 0;

	private SoundWaveType waveForm = SoundWaveType.SIN;

	private int pistonStart = 0;

	private int pistonEnd;

	private int freqIni;

	private int freqFin = 0;

	private double step = 0;

	private double stepInHardware = 0;

	private StatSoundStampDataSource dataSource;

	// new stuff:
	private static final int NUM_CHANNELS_DEFAULT = 2;

	private static final int BITS_PER_CHANNEL_DEFAULT = 16;

	private int numChannels = NUM_CHANNELS_DEFAULT;

	private int bitsPerChannel = BITS_PER_CHANNEL_DEFAULT;

	private int endian = javax.media.format.AudioFormat.LITTLE_ENDIAN;

	private int signed = AudioFormat.SIGNED;

	private Player player;

	private Handler soundCaptureDevice;

	private static final Logger LOGGER = Logger.getLogger(StatSoundStampDriver.class.getName());

	/** Creates a new instance of RadioactividadeStampDriver */
	public StatSoundStampDriver() {
		super();
		setDriverUniqueID(DRIVER_UNIQUE_ID);
		setApplicationNameLockPort(APPLICATION_NAME_LOCK_PORT);
		setTimeOutPerPort(TIME_OUT_PER_PORT);
		loadCommandHandlers();
	}

	private void checkConfigureParameters(final HardwareAcquisitionConfig config, final HardwareInfo info) {
		if (config == null) {
			throw new IllegalArgumentException("Config is null!!");
		}
		if (info == null) {
			throw new IllegalAccessError("HardwareInfo is null!!");
		}
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		// notify that I am configuring now...
		fireIDriverStateListenerDriverConfiguring();
		checkConfigureParameters(config, info);

		initInternalParameters(config);
		prepareCommand(config);
		translateCommandFromParameters();

		// notify that my work is finished
		fireIDriverStateListenerDriverConfigured();
	}

	private void initPlayerWithSilence() {
		try {
			this.player = ReCJMFUtils.createAndStartPlayer(SILENCE_FUNCTION);
		} catch (NoPlayerException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This method actually creates the command string from the parameters
	 * passed in to the stampConfigCommand.
	 * 
	 * @throws WrongConfigurationException
	 */
	void translateCommandFromParameters() throws WrongConfigurationException {
		// The translator is, basically, the entity that sends the command from
		// the software to the hardware
		final StampTranslator translator = StampTranslatorProcessorManager.getTranslator(stampConfigCommand);
		// send the command, if valid, to the hardware
		if (!translator.translate(stampConfigCommand)) {
			throw new WrongConfigurationException("Cannot translate StampCommand!", -1);
		}
	}

	private void prepareCommand(final HardwareAcquisitionConfig config) {
		stampConfigCommand = new StampCommand(AbstractStampDriver.CONFIG_OUT_STRING);
		// hack: in order to be able to always control the hardware, I always
		// tell the stamp to be invoked only once
		stampConfigCommand.addCommandData(NUMSAMPLES_COMMAND_PART, 1);
		// stampConfigCommand.addCommandData(NUMSAMPLES_COMMAND_PART,
		// numberOfInvocationsToHardware);
		stampConfigCommand.addCommandData(PISTON_START_COMMAND_PART, this.pistonStart);
		// hack: in order to be able to always control the hardware, I can't
		// tell stamp to move by itself. Instead, I'm the one that has to tell
		// him where to move next...
		stampConfigCommand.addCommandData(PISTON_END_COMMAND_PART, this.pistonStart);
		// stampConfigCommand.addCommandData(PISTON_END_COMMAND_PART,
		// this.pistonEnd);
		stampConfigCommand.addCommandData(STATUS_COMMAND_PART,
				config.getSelectedHardwareParameterValue(STATUS_COMMAND_PART));
		stampConfigCommand.addCommandData(CALIBRATION_COMMAND_PART,
				Integer.valueOf(config.getSelectedHardwareParameterValue(CALIBRATION_COMMAND_PART)));
	}

	void prepareCommandForNextStatement(final int pos) {
		stampConfigCommand = new StampCommand(AbstractStampDriver.CONFIG_OUT_STRING);
		stampConfigCommand.addCommandData(NUMSAMPLES_COMMAND_PART, 1);
		stampConfigCommand.addCommandData(PISTON_START_COMMAND_PART, pos);
		stampConfigCommand.addCommandData(PISTON_END_COMMAND_PART, pos);
		stampConfigCommand.addCommandData(STATUS_COMMAND_PART,
				config.getSelectedHardwareParameterValue(STATUS_COMMAND_PART));
		// never calibrate on the non first invocations to the hardware!
		stampConfigCommand.addCommandData(CALIBRATION_COMMAND_PART, 0);
	}

	private void initInternalParameters(final HardwareAcquisitionConfig config) {
		LOGGER.fine("Parameters follow:");
		for (final ParameterConfig pconfig : config.getSelectedHardwareParameters()) {
			LOGGER.fine(pconfig.getParameterName() + "=" + pconfig.getParameterValue());
		}
		// start configuration...
		this.config = config;
		freqIni = Integer.parseInt(config.getSelectedHardwareParameterValue(FREQUENCY_START_PARAMETER));
		freqFin = Integer.parseInt(config.getSelectedHardwareParameterValue(FREQUENCY_END_PARAMETER));
		// switch them, if the final frequency is before the initial frequency
		if (freqFin < freqIni) {
			final int temp = freqFin;
			freqFin = freqIni;
			freqIni = temp;
		}
		nSamples = config.getTotalSamples();
		/*
		 * The numberOfInvocationsToHardware must be calculated according to the
		 * type of experiment:
		 * 
		 * - For the sound velocity and vary frequency, it should be 1
		 * 
		 * - For the sound piston, it should be the number of chosen samples.
		 * 
		 * This is understandable because the nSamples controls the number of
		 * executions I'll ask the serial port hardware to run.
		 */
		final String experimentTypeParameter = config.getSelectedHardwareParameterValue(EXPERIMENT_TYPE_PARAMETER);
		final TypeOfExperiment typeOfExperiment = TypeOfExperiment.from(experimentTypeParameter);
		String waveFormString = config.getSelectedHardwareParameterValue(WAVE_FORM_PARAMETER);
		switch (typeOfExperiment) {
		case SOUND_VELOCITY:
			numberOfInvocationsToHardware = 1;
			waveForm = SoundWaveType.from(waveFormString);
			break;
		case STATSOUND_VARY_FREQUENCY:
			numberOfInvocationsToHardware = 1;
			// The Sin is the only wave that makes sense for this protocol
			waveForm = SoundWaveType.SIN;
			break;
		case STATSOUND_VARY_PISTON:
			numberOfInvocationsToHardware = config.getTotalSamples();
			// The Sin is the only wave that makes sense for this protocol
			waveForm = SoundWaveType.SIN;
			break;
		default:
			break;
		}
		// samples
		if (nSamples > 1) {
			step = Math.abs((double) (freqIni - freqFin)) / ((nSamples - 1));
		} else {
			/** Step needs to be bigger than 1 to get out of the for cycle */
			step = 1;
			freqFin = freqIni;
		}
		LOGGER.fine("Step = " + step);
		pistonStart = Integer.valueOf(config.getSelectedHardwareParameterValue(PISTON_START_PARAMETER));
		pistonEnd = Integer.valueOf(config.getSelectedHardwareParameterValue(PISTON_END_PARAMETER));
		if (numberOfInvocationsToHardware > 1) {
			stepInHardware = (pistonEnd - pistonStart) / (numberOfInvocationsToHardware - 1);
		} else {
			stepInHardware = 1;
			pistonEnd = pistonStart;
		}
		LOGGER.fine("Step in hardware = " + stepInHardware);
	}

	@Override
	public HardwareAcquisitionConfig getAcquisitionHeader() {
		return config;
	}

	@Override
	public Object getHardwareInfo() {

		final String baseHardwareInfoFile = "recresource://" + getClass().getPackage().getName().replaceAll("\\.", "/")
				+ HARDWARE_INFO_XML;
		String prop = Defaults.defaultIfEmpty(System.getProperty(HARDWARE_INFO), baseHardwareInfoFile);

		if (prop.indexOf("://") == -1) {
			prop = "file:///" + System.getProperty(USER_DIR) + "/" + prop;
		}

		java.net.URL url = null;
		try {
			url = ReCProtocols.getURL(prop);
		} catch (final java.net.MalformedURLException e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e,
					Logger.getLogger(AbstractStampDriver.STAMP_DRIVER_LOGGER));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (final java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2,
						Logger.getLogger(AbstractStampDriver.STAMP_DRIVER_LOGGER));
			}
		}

		return url;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(HardwareInfo info) {
		super.init(info);

		ReCJMFUtils.initReCJMFPackages();
		final String deviceLocation = ReCJMFUtils.locateCaptureDeviceForParameters(SAMPLE_RATE, bitsPerChannel,
				numChannels, endian, signed);

		initCaptureDevice(deviceLocation);
		initPlayerWithSilence();
	}

	private void initCaptureDevice(final String deviceLocation) {
		ReCJMFUtils.autoDetectJavaSoundDevices();
		try {
			DataSource dataSource = ReCJMFUtils.locateDataSource(deviceLocation, 16L);
			soundCaptureDevice = ReCJMFUtils.createCaptureDeviceFor(dataSource, SAMPLE_RATE, bitsPerChannel,
					numChannels);
			ReCJMFUtils.startCapturing(dataSource, soundCaptureDevice);
		} catch (NoDataSourceException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (NoDataSinkException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public AbstractStampDataSource initDataSource() {
		dataSource = new StatSoundStampDataSource();
		dataSource.setAcquisitionHeader(getAcquisitionHeader());
		return dataSource;
	}

	@Override
	protected void loadExtraCommandHandlers() {
		final String packageName = getClass().getPackage().getName() + ".";
		StampTranslatorProcessorManager.initStampProcessorsTranslators(new String[] {
				packageName + "processors.StampStatSoundProcessor",
				packageName + "processors.StampStatSoundTempProcessor",
				packageName + "translators.StampConfigTranslator", });
	}

	@Override
	public void processCommand(final StampCommand cmd) {
		if (cmd == null || cmd.getCommandIdentifier() == null) {
			Logger.getLogger(AbstractStampDriver.STAMP_DRIVER_LOGGER).log(Level.INFO,
					"Can not interpret command " + cmd);
			return;
		}

		if (cmd.getCommandIdentifier().equals(getDriverUniqueID())) {
			// the driver is controlling the state machine so, while the
			// data source is not ended, simply ignore the hardware messages
			if (dataSource != null && !dataSource.isExpEnded()) {
				if (dataSource.getNumberOfPosReceivedFromHardware() < dataSource.getNumberOfInvocationsToHardware()) {
					int nextPosition = dataSource.getNextPosition();
					LOGGER.fine(dataSource.getNumberOfPosReceivedFromHardware() + " < "
							+ dataSource.getNumberOfInvocationsToHardware() + ": Sending new cfg for position "
							+ nextPosition);
					prepareCommandForNextStatement(nextPosition);
					try {
						translateCommandFromParameters();
					} catch (WrongConfigurationException e) {
						// TODO: pzenida - Ask JP if we should do something
						// about the states of the driver and the datasource
						// here...
						LOGGER.log(Level.SEVERE,
								"Oops! Something wrong when sending the sequence commands to hardware", e);
					}
					writeMessage(stampConfigCommand.getCommand());
				}
				return;
			}
			processAbstractStampDriverIdCommand();
		} else if (cmd.getCommandIdentifier().equals(StampStartProcessor.COMMAND_IDENTIFIER)) {
			if (dataSource != null && !dataSource.isExpEnded()) {
				LOGGER.fine("Returning");
				return;
			}
			// actually process the command, preparing the data source.
			processStampStartCommand();
		} else if (cmd.getCommandIdentifier().equals(StampConfiguredProcessor.COMMAND_IDENTIFIER)) {
			if (dataSource != null && !dataSource.isExpEnded()) {
				LOGGER.fine("Returning");
				return;
			}
			processStampConfiguredCommand();
		} else if (cmd.getCommandIdentifier().equals(StampNotConfiguredProcessor.COMMAND_IDENTIFIER)) {
			// the driver is controlling the state machine so, while the
			// data source is not ended, simply ignore the hardware messages
			if (dataSource != null && !dataSource.isExpEnded()) {
				LOGGER.fine("Returning");
				return;
			}
			processStampNotConfiguredCommand();
		}
	}

	private void processStampConfiguredCommand() {
		LOGGER.fine("OK to go... - still gonna receive start...!");
	}

	private void processStampNotConfiguredCommand() {
		LOGGER.info("Processing command " + StampNotConfiguredProcessor.COMMAND_IDENTIFIER);
		if (waitingStart && wroteStart) {
			waitingStart = false;
			fireIDriverStateListenerDriverStoped();
			stopDataSource();
			LOGGER.info("stopped datasource because waitingStart and wroteStart are true");
		} else if (started) {
			LOGGER.fine("Started not configured!");
			started = false;
			fireIDriverStateListenerDriverReseting();
			fireIDriverStateListenerDriverReseted();
			stopDataSource();
			LOGGER.info("stopped datasource because started is true");
		}
	}

	private void processStampStartCommand() {
		started = true;

		config.setTimeStart(new DateTime());
		dataSource.setFreqIni(freqIni);
		dataSource.setFreqStep(step);
		dataSource.setPistonStart(pistonStart);
		dataSource.setControl(player.getControl(FunctorTypeControl.class.getName()));
		dataSource.setWaveForm(waveForm);
		dataSource.setNSamples(nSamples);
		dataSource.setExpEnded(false);
		dataSource.setCaptureDevice(soundCaptureDevice);
		dataSource.setNumberOfInvocationsToHardware(numberOfInvocationsToHardware);
		dataSource.setStepInHardware(stepInHardware);
		fireIDriverStateListenerDriverStarted();
	}

	private void processAbstractStampDriverIdCommand() {
		if (waitingStart) {
			LOGGER.fine("Waiting start");
			waitingStart = false;
			writeMessage(stampConfigCommand.getCommand());
			wroteStart = true;
			fireIDriverStateListenerDriverStarting();
		} else if (stoping) {
			LOGGER.fine("Stoping");
			stoping = false;
			started = false;
			fireIDriverStateListenerDriverStoped();
		} else if (reseting) {
			LOGGER.fine("Reseting");
			reseting = false;
			fireIDriverStateListenerDriverReseted();
			stopDataSource();
		} else if (started) {
			LOGGER.fine("Started");
			started = false;
			fireIDriverStateListenerDriverStoping();
			fireIDriverStateListenerDriverStoped();
			stopDataSource();
		} else if (initing) {
			LOGGER.fine("Initing");
			initing = false;
			fireIDriverStateListenerDriverReseting();
			fireIDriverStateListenerDriverReseted();
		}
	}

	@Override
	public void stopDataSource() {
		super.stopDataSource();
		if (dataSource != null) {
			LOGGER.fine("Stoping data source!");
			dataSource.setDataSourceEnded();
			LOGGER.fine("Finished stop data source!");
		}
	}

	@Override
	public void stop(final HardwareInfo info) throws IncorrectStateException {
		LOGGER.fine("Stop called!");
		fireIDriverStateListenerDriverStoping();
		stopDataSource();
		stoping = true;
	}

	@Override
	public void startNow() throws TimedOutException {
		wroteStart = false;

		if (stampConfigCommand == null) {
			throw new TimedOutException("No configuration available yet!");
		}

		waitingStart = true;

		WaitForConditionResult.waitForConditionTrue(new AbstractConditionDecisor() {
			@Override
			public ConditionResult getConditionResult() {
				if (!waitingStart) {
					return ConditionResult.CONDITION_MET_TRUE;
				}

				return ConditionResult.CONDITION_NOT_MET;
			}
		}, 400000, 1000);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void shutdown() {
		try {
			if (player != null) {
				player.stop();
				player.deallocate();
				player.close();
				player = null;
			}
		} catch (Exception ignored) {
		}

		super.shutdown();

	}

}
