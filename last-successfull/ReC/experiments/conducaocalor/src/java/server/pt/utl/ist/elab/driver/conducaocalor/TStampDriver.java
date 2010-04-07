/*
 * RadioactividadeStampDriver.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.conducaocalor;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.conducaocalor.translators.StampConfigTranslator;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampTranslatorProcessorManager;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampConfiguredProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampNotConfiguredProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampStartProcessor;

import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.threading.WaitForConditionResult;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class TStampDriver extends AbstractStampDriver {
	public static String HEAT_DRIVER_LOGGER = "HeatDriver.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(HEAT_DRIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(HEAT_DRIVER_LOGGER));
		}
	}

	private StampCommand stampConfig = null;

	/** Creates a new instance of RadioactividadeStampDriver */
	public TStampDriver() {
		super();
		setDriverUniqueID("ELAB_CONDCALOR_STAMP_V1.0");
		setApplicationNameLockPort("T Stamp Driver V0.2");
		setTimeOutPerPort(10000);
		setPortBaudRate(9600);
		loadCommandHandlers();
	}

	public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {

		fireIDriverStateListenerDriverConfiguring();

		stampConfig = new StampCommand(CONFIG_OUT_STRING);

		Logger.getLogger(HEAT_DRIVER_LOGGER).log(Level.INFO, "*******************************************");

		stampConfig.addCommandData(StampConfigTranslator.MODE_STR, new String(Defaults.defaultIfEmpty(config
				.getSelectedHardwareParameterValue(StampConfigTranslator.MODE_STR), info
				.getHardwareParameterValue(StampConfigTranslator.MODE_STR))));

		Logger.getLogger(HEAT_DRIVER_LOGGER).log(Level.INFO,
				info.getHardwareParameterValue(StampConfigTranslator.MODE_STR));

		stampConfig.addCommandData(StampConfigTranslator.HEAT_TIME_STR, new Integer(Defaults.defaultIfEmpty(config
				.getSelectedHardwareParameterValue(StampConfigTranslator.HEAT_TIME_STR), info
				.getHardwareParameterValue(StampConfigTranslator.HEAT_TIME_STR))));

		Logger.getLogger(HEAT_DRIVER_LOGGER).log(Level.INFO,
				info.getHardwareParameterValue(StampConfigTranslator.HEAT_TIME_STR));

		stampConfig.addCommandData(StampConfigTranslator.MAX_HEAT_STR, new Integer(Defaults.defaultIfEmpty(config
				.getSelectedHardwareParameterValue(StampConfigTranslator.MAX_HEAT_STR), info
				.getHardwareParameterValue(StampConfigTranslator.MAX_HEAT_STR))));

		Logger.getLogger(HEAT_DRIVER_LOGGER).log(Level.INFO,
				info.getHardwareParameterValue(StampConfigTranslator.MAX_HEAT_STR));

		stampConfig.addCommandData(StampConfigTranslator.TBS_STR, new Integer((int) config.getSelectedFrequency()
				.getFrequency()));

		Logger.getLogger(HEAT_DRIVER_LOGGER).log(Level.INFO, "" + (int) config.getSelectedFrequency().getFrequency());

		stampConfig.addCommandData(StampConfigTranslator.NUMSAMPLES_STR, new Integer(config.getTotalSamples()));

		Logger.getLogger(HEAT_DRIVER_LOGGER).log(Level.INFO, "" + (int) config.getTotalSamples());

		StampTranslator translator = StampTranslatorProcessorManager.getTranslator(stampConfig);
		if (!translator.translate(stampConfig))
			throw new WrongConfigurationException("Cannot translate StampCommand!", -1);

		config.getChannelsConfig(0).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(1).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(2).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(3).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(4).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(5).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(6).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(7).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(8).setTotalSamples(config.getTotalSamples());

		Logger.getLogger(HEAT_DRIVER_LOGGER).log(Level.INFO, "*******************************************");
		this.config = config;

		fireIDriverStateListenerDriverConfigured();
	}

	public HardwareAcquisitionConfig getAcquisitionHeader() {
		return config;
	}

	public Object getHardwareInfo() {

		String baseHardwareInfoFile = "recresource://pt/utl/ist/elab/driver/conducaocalor/TBaseHardwareInfo.xml";
		String prop = Defaults.defaultIfEmpty(System.getProperty("eLab.T.HardwareInfo"), baseHardwareInfoFile);

		if (prop.indexOf("://") == -1)
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;

		java.net.URL url = null;
		try {
			url = ReCProtocols.getURL(prop);
		} catch (java.net.MalformedURLException e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e, Logger.getLogger(STAMP_DRIVER_LOGGER));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2, Logger
						.getLogger(STAMP_DRIVER_LOGGER));
			}
		}

		return url;

	}

	public AbstractStampDataSource initDataSource() {
		Logger.getLogger(HEAT_DRIVER_LOGGER).log(Level.INFO, "************************************");
		Logger.getLogger(HEAT_DRIVER_LOGGER).log(Level.INFO, "INITING DATA SOURCE");
		Logger.getLogger(HEAT_DRIVER_LOGGER).log(Level.INFO, "************************************");
		TStampDataSource dataSource = new TStampDataSource();
		dataSource.setAcquisitionHeader(getAcquisitionHeader());
		Logger.getLogger(HEAT_DRIVER_LOGGER).log(Level.INFO, "************************************");
		Logger.getLogger(HEAT_DRIVER_LOGGER).log(Level.INFO, "INITED DATA SOURCE");
		Logger.getLogger(HEAT_DRIVER_LOGGER).log(Level.INFO, "************************************");

		return dataSource;
	}

	protected void loadExtraCommandHandlers() {
		String packageName = getClass().getPackage().getName() + ".";
		StampTranslatorProcessorManager.initStampProcessorsTranslators(new String[] {
				packageName + "processors.StampTProcessor", packageName + "translators.StampConfigTranslator", });
	}

	public void processCommand(StampCommand cmd) {
		if (cmd == null || cmd.getCommandIdentifier() == null) {
			Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO, "Can not interpret command " + cmd);
			return;
		}

		if (cmd.getCommandIdentifier().equals(ID_STR)) {
			if (waitingStart) {
				waitingStart = false;
				writeMessage(stampConfig.getCommand());
				wroteStart = false;
				fireIDriverStateListenerDriverStarting();
			} else if (stoping) {
				stoping = false;
				fireIDriverStateListenerDriverStoping();
				super.stopDataSource();
			} else if (reseting) {
				reseting = false;
				fireIDriverStateListenerDriverReseted();
				super.stopDataSource();
			} else if (started) {
				started = false;
				fireIDriverStateListenerDriverStoping();
				fireIDriverStateListenerDriverStoped();
				super.stopDataSource();
			} else if (initing) {
				initing = false;
				fireIDriverStateListenerDriverReseting();
				fireIDriverStateListenerDriverReseted();
			}
		} else if (cmd.getCommandIdentifier().equals(StampStartProcessor.COMMAND_IDENTIFIER)) {
			started = true;
			fireIDriverStateListenerDriverStarted();
			DateTime dt = new DateTime();
			config.getChannelsConfig(0).setTimeStart(dt);
			config.getChannelsConfig(1).setTimeStart(dt);
			config.getChannelsConfig(2).setTimeStart(dt);
			config.getChannelsConfig(3).setTimeStart(dt);
			config.getChannelsConfig(4).setTimeStart(dt);
			config.getChannelsConfig(5).setTimeStart(dt);
			config.getChannelsConfig(6).setTimeStart(dt);
			config.getChannelsConfig(7).setTimeStart(dt);
			config.getChannelsConfig(8).setTimeStart(dt);
			config.setTimeStart(dt);
			/*
			 * if(dataSource!=null) dataSource.setRunning(true);
			 */

		} else if (cmd.getCommandIdentifier().equals(StampConfiguredProcessor.COMMAND_IDENTIFIER)) {
			// OK to go... - still gonna receive start...!
		} else if (cmd.getCommandIdentifier().equals(StampNotConfiguredProcessor.COMMAND_IDENTIFIER)) {
			if (waitingStart && wroteStart) {
				waitingStart = false;
				fireIDriverStateListenerDriverStoped();
				super.stopDataSource();
			} else if (started) {
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
	private boolean waitingStop = false;
	private boolean started = false;
	private boolean stoping = false;
	private boolean reseting = true;

	public void startNow() throws TimedOutException {
		if (stampConfig == null)
			throw new TimedOutException("No configuration available yet!");

		waitingStart = true;

		WaitForConditionResult.waitForConditionTrue(new AbstractConditionDecisor() {
			public ConditionResult getConditionResult() {
				if (!waitingStart)
					return ConditionResult.CONDITION_MET_TRUE;

				return ConditionResult.CONDITION_NOT_MET;
			}
		}, 20000, 500);
	}
}
