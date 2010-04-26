/*
 * RadioactividadeStampDriver.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.thomson;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampTranslatorProcessorManager;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampConfiguredProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampNotConfiguredProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampStartProcessor;
import pt.utl.ist.elab.driver.thomson.translators.StampConfigTranslator;

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
public class ThomsonStampDriver extends AbstractStampDriver {
	private static String THOMSON_HARDWARE_DRIVER_LOGGER = "ThomsonHardwareDriver.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(THOMSON_HARDWARE_DRIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(THOMSON_HARDWARE_DRIVER_LOGGER));
			Logger.getLogger(THOMSON_HARDWARE_DRIVER_LOGGER).log(Level.INFO, "Creating logger!");
		}
	}

	private StampCommand stampConfig = null;
	private GravadorVideo video = null;

	/** Creates a new instance of RadioactividadeStampDriver */
	public ThomsonStampDriver() {
		super();
		setDriverUniqueID("ELAB_THOMSON_STAMP_V01");
		setApplicationNameLockPort("Thomson Stamp Driver V0.2");
		setTimeOutPerPort(10000);
		setPortBaudRate(9600);
		loadCommandHandlers();
		video = new GravadorVideo();
		video.iniciarWebcam();
		System.out.println("OK! all created!");
	}

	public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {

		fireIDriverStateListenerDriverConfiguring();

		stampConfig = new StampCommand(CONFIG_OUT_STRING);

		stampConfig.addCommandData(StampConfigTranslator.V_ACELERACAO_STR, new Integer(Defaults.defaultIfEmpty(config
				.getSelectedHardwareParameterValue(StampConfigTranslator.V_ACELERACAO_STR), info
				.getHardwareParameterValue(StampConfigTranslator.V_ACELERACAO_STR))));

		stampConfig.addCommandData(StampConfigTranslator.I_BOBINES_STR, new Integer(Defaults.defaultIfEmpty(config
				.getSelectedHardwareParameterValue(StampConfigTranslator.I_BOBINES_STR), info
				.getHardwareParameterValue(StampConfigTranslator.I_BOBINES_STR))));

		stampConfig.addCommandData(StampConfigTranslator.MODO_STR, new String(Defaults.defaultIfEmpty(config
				.getSelectedHardwareParameterValue(StampConfigTranslator.MODO_STR), info
				.getHardwareParameterValue(StampConfigTranslator.MODO_STR))));

		stampConfig.addCommandData(StampConfigTranslator.NUMSAMPLES_STR, new Integer(config.getTotalSamples()));

		StampTranslator translator = StampTranslatorProcessorManager.getTranslator(stampConfig);
		if (!translator.translate(stampConfig))
			throw new WrongConfigurationException("Cannot translate StampCommand!", -1);

		config.getChannelsConfig(0).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(1).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(2).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(3).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(4).setTotalSamples(config.getTotalSamples());

		this.config = config;

		fireIDriverStateListenerDriverConfigured();
	}

	public HardwareAcquisitionConfig getAcquisitionHeader() {
		return config;
	}

	public Object getHardwareInfo() {

		String baseHardwareInfoFile = "recresource://"+getClass().getPackage().getName().replaceAll("\\.","/")+"/HardwareInfo.xml";
		String prop = Defaults.defaultIfEmpty(System.getProperty("HardwareInfo"), baseHardwareInfoFile);

		if (prop.indexOf("://") == -1)
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;

		java.net.URL url = null;
		try {
			url = ReCProtocols.getURL(prop);
		} catch (Exception e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e, Logger.getLogger(STAMP_DRIVER_LOGGER));
			e.printStackTrace();
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (Exception e2) {
				e2.printStackTrace();
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2, Logger
						.getLogger(STAMP_DRIVER_LOGGER));
			}
		}

		System.out.println("Returning url = " + url);
		java.io.File f = new java.io.File(url.getFile());
		System.out.println("File = " + url.getFile());
		if (f.exists())
			System.out.println("File exists! COOL!");
		else
			System.out.println("SHIT");
		Logger.getLogger(THOMSON_HARDWARE_DRIVER_LOGGER).log(Level.INFO, "Returning url = " + url);

		return url;

	}

	private ThomsonStampDataSource dataSource = null;

	public AbstractStampDataSource initDataSource() {
		dataSource = new ThomsonStampDataSource();
		dataSource.setAcquisitionHeader(getAcquisitionHeader());
		return dataSource;
	}

	protected void loadExtraCommandHandlers() {
		System.out.println("Loading command handlers...");
		String packageName = getClass().getPackage().getName() + ".";
		StampTranslatorProcessorManager.initStampProcessorsTranslators(new String[] {
				packageName + "processors.StampThomsonProcessor", packageName + "translators.StampConfigTranslator", });
		System.out.println("Loaded command handlers...");
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
				video.comecarVideo();
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
			config.getChannelsConfig(0).setTimeStart(new DateTime());
			config.getChannelsConfig(1).setTimeStart(new DateTime());
			config.getChannelsConfig(2).setTimeStart(new DateTime());
			config.getChannelsConfig(3).setTimeStart(new DateTime());
			config.getChannelsConfig(4).setTimeStart(new DateTime());
			config.setTimeStart(new DateTime());
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
				// TODO THIS SHOULDN'T BE HERE!!
				dataSource.sendImages(video.getVideo());
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
