/*
 * StatSoundStampDriver.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.statsound;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Control;
import javax.media.ControllerAdapter;
import javax.media.DataSink;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.PackageManager;
import javax.media.Player;
import javax.media.PrefetchCompleteEvent;
import javax.media.RealizeCompleteEvent;
import javax.media.StartEvent;
import javax.media.format.AudioFormat;
import javax.media.protocol.DataSource;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

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
import com.linkare.rec.jmf.media.datasink.capture.Handler;
import com.linkare.rec.jmf.media.protocol.function.FunctorControl;
import com.linkare.rec.jmf.media.protocol.function.FunctorType;
import com.linkare.rec.jmf.media.protocol.function.FunctorTypeControl;

/**
 * 
 * @author José Pedro Pereira - Linkare TI & André
 */
public class StatSoundStampDriver extends AbstractStampDriver {

	/**
	 * 
	 */
	private static final String CAPTURE_LOCATOR = "capture://44100/16/2";
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

	private static final String CAPTURE_DEVICE_URL = "capture.device.url";

	private static final String HARDWARE_INFO_XML = "/HardwareInfo.xml";

	private static final String USER_DIR = "user.dir";

	private static final String HARDWARE_INFO = "HardwareInfo";

	private static final String JMF_PACKAGE = "com.linkare.rec.jmf";

	private static final String SILENCE_FUNCTION = "function://44100/16/silence";

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

	private StatSoundStampDataSource dataSource;

	// new stuff:
	private static final int NUM_CHANNELS_DEFAULT = 2;

	private static final int BITS_PER_CHANNEL_DEFAULT = 16;

	private int numChannels = NUM_CHANNELS_DEFAULT;

	private int bitsPerChannel = BITS_PER_CHANNEL_DEFAULT;

	private int endian = javax.media.format.AudioFormat.LITTLE_ENDIAN;

	private double sampleRate = 44100;

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
		sendCommandToHardware();

		// notify that my work is finished
		fireIDriverStateListenerDriverConfigured();
	}

	private void initPlayerWithSilence() {
		final String locatorString = SILENCE_FUNCTION;
		LOGGER.info("Creating player...");
		try {
			player = Manager.createPlayer(new MediaLocator(locatorString));
		} catch (NoPlayerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// TODO: The following code should use a Java barrier
		final Object prefetchWait = new int[0];
		player.addControllerListener(new ControllerAdapter() {

			@Override
			public void realizeComplete(RealizeCompleteEvent e) {
				LOGGER.fine("Prefetching player...");
				player.prefetch();
			}

			@Override
			public void prefetchComplete(PrefetchCompleteEvent e) {
				synchronized (prefetchWait) {
					prefetchWait.notifyAll();
				}
				LOGGER.fine("Starting player...");
				player.start();
			}

			@Override
			public void start(StartEvent e) {
				LOGGER.fine("Started player...");
			}
		});

		LOGGER.fine("Realizing player...");
		player.realize();

		synchronized (prefetchWait) {
			try {
				prefetchWait.wait(10000);
			} catch (InterruptedException e) {
				// cleanup the player
				player.stop();
				player.deallocate();
				player.close();
				e.printStackTrace();
				throw new RuntimeException("The sound driver is facing problems " + e.getMessage(), e);
			}
		}
	}

	private void sendCommandToHardware() throws WrongConfigurationException {
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
		stampConfigCommand.addCommandData(NUMSAMPLES_COMMAND_PART, numberOfInvocationsToHardware);
		stampConfigCommand.addCommandData(PISTON_START_COMMAND_PART, this.pistonStart);
		stampConfigCommand.addCommandData(PISTON_END_COMMAND_PART, this.pistonEnd);
		stampConfigCommand.addCommandData(STATUS_COMMAND_PART,
				config.getSelectedHardwareParameterValue(STATUS_COMMAND_PART));
		stampConfigCommand.addCommandData(CALIBRATION_COMMAND_PART,
				Integer.valueOf(config.getSelectedHardwareParameterValue(CALIBRATION_COMMAND_PART)));
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
		switch (typeOfExperiment) {
		case SOUND_VELOCITY:
		case STATSOUND_VARY_FREQUENCY:
			numberOfInvocationsToHardware = 1;
			break;
		case STATSOUND_VARY_PISTON:
			numberOfInvocationsToHardware = config.getTotalSamples();
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
			freqIni = freqFin;
		}
		LOGGER.fine("Step = " + step);
		waveForm = SoundWaveType.from(config.getSelectedHardwareParameterValue(WAVE_FORM_PARAMETER));
		pistonStart = Integer.valueOf(config.getSelectedHardwareParameterValue(PISTON_START_PARAMETER));
		pistonEnd = Integer.valueOf(config.getSelectedHardwareParameterValue(PISTON_END_PARAMETER));
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

		// initialize the JMF classes
		@SuppressWarnings("unchecked")
		Vector<String> protocolPrefixList = PackageManager.getProtocolPrefixList();

		if (!protocolPrefixList.contains(JMF_PACKAGE)) {
			protocolPrefixList.add(JMF_PACKAGE);
			PackageManager.setProtocolPrefixList(protocolPrefixList);
			PackageManager.commitProtocolPrefixList();
		}

		@SuppressWarnings("unchecked")
		Vector<String> contentPrefixList = PackageManager.getContentPrefixList();
		if (!contentPrefixList.contains(JMF_PACKAGE)) {
			contentPrefixList.add(JMF_PACKAGE);
			PackageManager.setContentPrefixList(protocolPrefixList);
			PackageManager.commitContentPrefixList();
		}

		try {

			// It's there, start to register JavaSound with CaptureDeviceManager
			Vector devices = (Vector) CaptureDeviceManager.getDeviceList(null).clone();

			// detect if javasound capturers are already defined!
			boolean foundJavaSoundDevice = false;
			String name;
			Enumeration enumDevices = devices.elements();
			while (enumDevices.hasMoreElements()) {
				CaptureDeviceInfo cdi = (CaptureDeviceInfo) enumDevices.nextElement();
				name = cdi.getName();
				if (name.startsWith("JavaSound")) {
					foundJavaSoundDevice = true;
					break;
				}
			}

			if (!foundJavaSoundDevice) {
				DataLine.Info lineInfo = new DataLine.Info(TargetDataLine.class, null, AudioSystem.NOT_SPECIFIED);

				if (!AudioSystem.isLineSupported(lineInfo)) {
					throw new Exception("AudioSystem.isLineSupported said 'false'");
				}

				// collect javasound capture device info from
				// JavaSoundSourceStream
				// and register them with CaptureDeviceManager
				CaptureDeviceInfo[] cdi = com.sun.media.protocol.javasound.JavaSoundSourceStream
						.listCaptureDeviceInfo();
				if (cdi != null) {
					for (int i = 0; i < cdi.length; i++) {
						CaptureDeviceManager.addDevice(cdi[i]);
					}
					CaptureDeviceManager.commit();
					LOGGER.fine("JavaSoundAuto: Committed ok");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Unable to detect java sound capture device... No sound board???", e);
		}

		String deviceLocation = System.getProperty(CAPTURE_DEVICE_URL);

		AudioFormat audioFormat = new AudioFormat(AudioFormat.LINEAR, sampleRate, bitsPerChannel, numChannels,
				this.endian, this.signed);

		if (deviceLocation == null) {
			@SuppressWarnings("unchecked")
			Vector<CaptureDeviceInfo> deviceList = CaptureDeviceManager.getDeviceList(audioFormat);
			if (deviceList != null && deviceList.size() > 0) {
				for (CaptureDeviceInfo captureDeviceInfo : deviceList) {
					LOGGER.info("Found device at " + captureDeviceInfo.getLocator().toExternalForm());
				}
				if (deviceList.size() > 1) {
					LOGGER.severe("Please specify the correct device by setting the system property "
							+ CAPTURE_DEVICE_URL);
				} else {
					deviceLocation = deviceList.get(0).getLocator().toExternalForm();
				}
			} else {
				LOGGER.severe("Did not find any device matching the caracteristics...");
				LOGGER.severe("Please use the auto-detect funcions of JMFRegistry (will be opened now if possible) and commit the changes...");
				return;
			}
		}
		initPlayerWithSilence();
		initCaptureDevice(deviceLocation);

//		FunctorTypeControl functorTypeControl = (FunctorTypeControl) player.getControl(FunctorTypeControl.class
//				.getName());
//
//		long startMarkerTimeStamp = System.currentTimeMillis();
//		functorTypeControl.setFunctorType(FunctorType.WHITE_NOISE);
//		try {
//			soundCaptureDevice.calibrateWithMarker(startMarkerTimeStamp);
//			LOGGER.info("Calibration of sound capture lazyness was calculated at "+soundCaptureDevice.getDeltaTime()+" ms");
//		} catch (InterruptedException e) {
//			throw new RuntimeException(e);
//		}
//
//		functorTypeControl.setFunctorType(FunctorType.SILENCE);
	}

	private void initCaptureDevice(String deviceLocation) {
		if (deviceLocation != null) {
			MediaLocator locator = new MediaLocator(deviceLocation);
			LOGGER.fine("Capturing from " + locator);
			try {
				DataSource dataSource = Manager.createDataSource(locator);
				String destinationLocator = CAPTURE_LOCATOR;
				DataSink sink = Manager.createDataSink(dataSource, new MediaLocator(destinationLocator));
				sink.open();
				dataSource.connect();
				soundCaptureDevice = (Handler) sink;
				sink.start();
				dataSource.start();
			} catch (Exception e) {
				throw new RuntimeException("The sound driver is facing problems " + e.getMessage(), e);
			}
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
				LOGGER.fine("Returning");
				return;
			}
			processAbstractStampDriverIdCommand();
		} else if (cmd.getCommandIdentifier().equals(StampStartProcessor.COMMAND_IDENTIFIER)) {
			// actually process the command, preparing the data source.
			processStampStartCommand();
		} else if (cmd.getCommandIdentifier().equals(StampConfiguredProcessor.COMMAND_IDENTIFIER)) {
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
		dataSource.setControl(player.getControl(FunctorTypeControl.class.getName()));
		dataSource.setWaveForm(waveForm);
		dataSource.setNSamples(nSamples);
		dataSource.setExpEnded(false);
		dataSource.setCaptureDevice(soundCaptureDevice);
		dataSource.setNumberOfInvocationsToHardware(numberOfInvocationsToHardware);
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