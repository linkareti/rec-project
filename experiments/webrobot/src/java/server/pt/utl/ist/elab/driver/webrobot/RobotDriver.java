package pt.utl.ist.elab.driver.webrobot;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.webrobot.serial.SerialComm;

import com.linkare.net.protocols.Protocols;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.BaseDriver;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.impl.utils.Defaults;

public class RobotDriver extends BaseDriver {

	private static final Logger LOGGER = Logger.getLogger(RobotDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "ELAB_WEB_ROBOT_V01";

	private static RobotDriver SingletonDriver = null;

	protected RobotStateMachine dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	protected String flowChartString = null;

	/** To comunicate with the robot */
	private SerialComm serialComm;

	/** Creates a new instance of RobotDriver */
	public RobotDriver() {
	}

	public static RobotDriver Create() {
		if (RobotDriver.SingletonDriver == null) {
			RobotDriver.SingletonDriver = new RobotDriver();
		}
		return RobotDriver.SingletonDriver;
	}

	@Override
	public void config(final HardwareAcquisitionConfig config, final HardwareInfo info) throws IncorrectStateException,
			WrongConfigurationException {
		fireIDriverStateListenerDriverConfiguring();
		LOGGER.log(Level.INFO, "Starting config");
		info.validateConfig(config);
		extraValidateConfig(config, info);
		try {
			configure(config, info);
		} catch (final Exception e) {
			e.printStackTrace();
			throw new WrongConfigurationException("Erro no config...", 20);
		}
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		this.config = config;
		this.info = info;
		final ParameterConfig[] selectedParams = config.getSelectedHardwareParameters();

		if (selectedParams != null) {
			/**
			 * Why these loops, if you know what you're looking for? Well...I'll
			 * understand this better if I need to look at this later...
			 */
			ParameterConfig flowParam = null;
			for (final ParameterConfig selectedParam : selectedParams) {
				if (selectedParam.getParameterName().equalsIgnoreCase("FlowChart")) {
					flowParam = selectedParam;
					flowChartString = flowParam.getParameterValue();
				}
			}
		}
		/** I don't want null files!! Why are you sending them? */
		if (flowChartString == null) {
			LOGGER.log(Level.INFO, "Flow chart String is NULL!!!");
			flowChartString = "";
		}

		/**
		 * I really don't give a damn about the number of samples in this
		 * experiment, but may be in the future this lines will help me to do
		 * other things...
		 */
		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
		}
		fireIDriverStateListenerDriverConfigured();
		LOGGER.log(Level.INFO, "Configured");
	}

	@Override
	public void extraValidateConfig(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		/** not going to use */
	}

	@Override
	public String getDriverUniqueID() {
		return RobotDriver.DRIVER_UNIQUE_ID;
	}

	@Override
	public void init(final HardwareInfo info) {
		/** Open the serial connection */
		serialComm = new pt.utl.ist.elab.driver.webrobot.serial.SerialComm();
		LOGGER.log(Level.INFO, "The serial port is opened!");
		dataSource = new RobotStateMachine(this);
		fireIDriverStateListenerDriverInited();
	}

	@Override
	public void reset(final HardwareInfo info) throws IncorrectStateException {
		/** Reset in not supported in webrobot */
	}

	@Override
	public void shutdown() {
		if (dataSource != null) {
			dataSource.shutdown();
		}
		super.shutDownNow();
	}

	@Override
	public IDataSource start(final HardwareInfo info) throws IncorrectStateException {
		LOGGER.log(Level.INFO, "Starting!");
		fireIDriverStateListenerDriverStarting();
		dataSource = new RobotStateMachine(this);
		dataSource.setAtStartPosition();
		while (!dataSource.isAtStartPosition()) {
			try {
				Thread.currentThread();
				Thread.sleep(2000);
			} catch (final InterruptedException ie) {
			}
		}
		LOGGER.log(Level.INFO, "The robot is at the start position");
		dataSource.setFlowString(flowChartString);
		dataSource.setAcquisitionHeader(config);
		dataSource.startProduction();
		LOGGER.log(Level.INFO, "Ready to start");
		fireIDriverStateListenerDriverStarted();
		LOGGER.log(Level.INFO, "Started!");
		// dataSource.setRunning(true);
		return dataSource;
	}

	@Override
	public IDataSource startOutput(final HardwareInfo info, final IDataSource source) throws IncorrectStateException {
		/** Don't know what is startOutput... */
		return null;
	}

	@Override
	public void stop(final HardwareInfo info) throws IncorrectStateException {
		dataSource.stopProduction();
	}

	@Override
	public Object getHardwareInfo() {
		fireIDriverStateListenerDriverReseting();

		final String baseHardwareInfoFile = "recresource://" + getClass().getPackage().getName().replaceAll("\\.", "/")
				+ "/HardwareInfo.xml";
		String prop = Defaults.defaultIfEmpty(System.getProperty("HardwareInfo"), baseHardwareInfoFile);

		if (prop.indexOf("://") == -1) {
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;
		}

		java.net.URL url = null;
		try {
			url = Protocols.getURL(prop);
			fireIDriverStateListenerDriverReseted();
		} catch (final java.net.MalformedURLException e) {
			LOGGER.log(Level.SEVERE, "Unable to load resource: " + prop, e);
			try {
				url = new java.net.URL(baseHardwareInfoFile);
				fireIDriverStateListenerDriverReseted();
			} catch (final java.net.MalformedURLException e2) {
				LOGGER.log(Level.SEVERE, "Unable to load resource: " + baseHardwareInfoFile, e2);
			}
		}
		return url;
	}

	public void setStoping() {
		LOGGER.log(Level.INFO, "Stoping");
		fireIDriverStateListenerDriverStoping();
		// dataSource.setRunning(false);
	}

	public void setStoped() {
		LOGGER.log(Level.INFO, "Stoped");
		fireIDriverStateListenerDriverStoped();
	}

	public SerialComm getSerialComm() {
		return serialComm;
	}
}
