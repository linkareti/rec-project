package pt.utl.ist.elab.driver.condensadorcilindrico;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.condensadorcilindrico.translators.StampConfigTranslator;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampTranslatorProcessorManager;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampConfiguredProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampNotConfiguredProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampStartProcessor;

import com.linkare.net.protocols.Protocols;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.threading.WaitForConditionResult;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class CCStampDriver extends AbstractStampDriver {

	private StampCommand stampConfig = null;

	private static final Logger LOGGER = Logger.getLogger(CCStampDriver.class.getName());

	public CCStampDriver() {
		super();
		setDriverUniqueID("ELAB_CC_STAMP_V02");
		setApplicationNameLockPort("CondCilindrico Stamp Driver V0.2");
		setTimeOutPerPort(10000);
		setPortBaudRate(115200);
		setWaitForEcho(false);
		loadCommandHandlers();
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {

		final String initPosInfo = info.getHardwareParameterValue(StampConfigTranslator.START_POS_STR);
		final String initPosConfig = config.getSelectedHardwareParameterValue(StampConfigTranslator.START_POS_STR);
		final String endPosInfo = info.getHardwareParameterValue(StampConfigTranslator.END_POS_STR);
		final String endPosConfig = config.getSelectedHardwareParameterValue(StampConfigTranslator.END_POS_STR);
		final int totalSamples = config.getTotalSamples();
		LOGGER.finest("initPosInfo: " + initPosInfo + " initPosConfig: " + initPosConfig);
		LOGGER.finest("endPosInfo: " + endPosInfo + " endPosConfig: " + endPosConfig);
		LOGGER.finest("totalSamples: " + totalSamples);

		fireIDriverStateListenerDriverConfiguring();

		stampConfig = new StampCommand(AbstractStampDriver.CONFIG_OUT_STRING);

		stampConfig.addCommandData(StampConfigTranslator.START_POS_STR,
				new Integer(Defaults.defaultIfEmpty(initPosConfig, initPosInfo)));

		stampConfig.addCommandData(StampConfigTranslator.END_POS_STR,
				new Integer(Defaults.defaultIfEmpty(endPosConfig, endPosInfo)));

		stampConfig.addCommandData(StampConfigTranslator.NUMSAMPLES_STR, new Integer(totalSamples));

		final StampTranslator translator = StampTranslatorProcessorManager.getTranslator(stampConfig);
		if (!translator.translate(stampConfig)) {
			throw new WrongConfigurationException("Cannot translate StampCommand!", -1);
		}

		config.getChannelsConfig(0).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(1).setTotalSamples(config.getTotalSamples());

		this.config = config;

		fireIDriverStateListenerDriverConfigured();
	}

	@Override
	public HardwareAcquisitionConfig getAcquisitionHeader() {
		return config;
	}

	@Override
	public Object getHardwareInfo() {

		final String baseHardwareInfoFile = "recresource://" + getClass().getPackage().getName().replaceAll("\\.", "/")
				+ "/HardwareInfo.xml";
		String prop = Defaults.defaultIfEmpty(System.getProperty("HardwareInfo"), baseHardwareInfoFile);

		if (prop.indexOf("://") == -1) {
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;
		}

		java.net.URL url = null;
		try {
			url = Protocols.getURL(prop);
		} catch (final java.net.MalformedURLException e) {
			LOGGER.log(Level.SEVERE, "Unable to load resource: " + prop, e);
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (final java.net.MalformedURLException e2) {
				LOGGER.log(Level.SEVERE, "Unable to load resource: " + baseHardwareInfoFile, e2);
			}
		}

		return url;
	}

	@Override
	public AbstractStampDataSource initDataSource() {
		return new CCStampDataSource();
	}

	@Override
	protected void loadExtraCommandHandlers() {
		final String packageName = getClass().getPackage().getName() + ".";
		StampTranslatorProcessorManager.initStampProcessorsTranslators(new String[] {
				packageName + "processors.StampCCProcessor", packageName + "translators.StampConfigTranslator", });
	}

	@Override
	public void processCommand(final StampCommand cmd) {
		if (cmd == null || cmd.getCommandIdentifier() == null) {
			LOGGER.info("Can not interpret command " + cmd);
			return;
		}

		if (cmd.getCommandIdentifier().equals(AbstractStampDriver.ID_STR)) {
			if (waitingStart) {
				LOGGER.fine("Waiting start");
				waitingStart = false;
				writeMessage(stampConfig.getCommand());
				wroteStart = false;
				fireIDriverStateListenerDriverStarting();
			} else if (stoping) {
				LOGGER.fine("stopping");
				stoping = false;
				fireIDriverStateListenerDriverStoping();
				super.stopDataSource();
			} else if (reseting) {
				LOGGER.fine("reseting");
				reseting = false;
				fireIDriverStateListenerDriverReseted();
				super.stopDataSource();
			} else if (started) {
				LOGGER.fine("started");
				started = false;
				fireIDriverStateListenerDriverStoping();
				fireIDriverStateListenerDriverStoped();
				super.stopDataSource();
			} else if (initing) {
				LOGGER.fine("initing");
				initing = false;
				fireIDriverStateListenerDriverReseting();
				fireIDriverStateListenerDriverReseted();
			}
		} else if (cmd.getCommandIdentifier().equals(StampStartProcessor.COMMAND_IDENTIFIER)) {
			LOGGER.fine("starting");
			started = true;
			fireIDriverStateListenerDriverStarted();
			config.getChannelsConfig(0).setTimeStart(new DateTime());
			config.getChannelsConfig(1).setTimeStart(new DateTime());
			config.setTimeStart(new DateTime());

		} else if (cmd.getCommandIdentifier().equals(StampConfiguredProcessor.COMMAND_IDENTIFIER)) {
			LOGGER.fine("configured");
			// OK to go... - still gonna receive start...!
		} else if (cmd.getCommandIdentifier().equals(StampNotConfiguredProcessor.COMMAND_IDENTIFIER)) {
			LOGGER.fine("not configured");
			if (waitingStart && wroteStart) {
				LOGGER.fine("waiting start but alreadt wrote start");
				waitingStart = false;
				fireIDriverStateListenerDriverStoped();
				super.stopDataSource();
			} else if (started) {
				LOGGER.fine("started2");
				started = false;
				fireIDriverStateListenerDriverReseting();
				fireIDriverStateListenerDriverReseted();
				super.stopDataSource();
			}
		}
	}

	private boolean initing = true;
	private boolean waitingStart = false;
	private boolean wroteStart = false;
	private boolean started = false;
	private boolean stoping = false;
	private boolean reseting = true;

	@Override
	public void startNow() throws TimedOutException {
		if (stampConfig == null) {
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
		}, 20000, 500);
	}
}
