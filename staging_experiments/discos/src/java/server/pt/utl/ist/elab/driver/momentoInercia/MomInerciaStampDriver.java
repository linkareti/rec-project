package pt.utl.ist.elab.driver.momentoInercia;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.momentoInercia.translators.StampConfigTranslator;
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
import com.linkare.net.protocols.Protocols;
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.threading.WaitForConditionResult;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author jp
 */
public class MomInerciaStampDriver extends AbstractStampDriver {

	private static final Logger LOGGER = Logger.getLogger(MomInerciaStampDriver.class.getName());

	private StampCommand stampConfig = null;

	/** Creates a new instance of RadioactividadeStampDriver */
	public MomInerciaStampDriver() {
		setDriverUniqueID("EXP_ANGULAR_STAMP_V1.0");
		setApplicationNameLockPort("Momento Inercia Stamp Driver V0.2");
		setTimeOutPerPort(10000);
		setPortBaudRate(38400);
		loadCommandHandlers();
	}

	public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {

		fireIDriverStateListenerDriverConfiguring();

		stampConfig = new StampCommand(CONFIG_OUT_STRING);

		stampConfig.addCommandData(
				StampConfigTranslator.ITER_STR,
				new String(Defaults.defaultIfEmpty(
						config.getSelectedHardwareParameterValue(StampConfigTranslator.ITER_STR),
						info.getHardwareParameterValue(StampConfigTranslator.ITER_STR))));

		stampConfig.addCommandData(
				StampConfigTranslator.LAUNCH_STR,
				new Integer(Defaults.defaultIfEmpty(
						config.getSelectedHardwareParameterValue(StampConfigTranslator.LAUNCH_STR),
						info.getHardwareParameterValue(StampConfigTranslator.LAUNCH_STR))));

		stampConfig.addCommandData(
				StampConfigTranslator.STOP_STR,
				new Integer(Defaults.defaultIfEmpty(
						config.getSelectedHardwareParameterValue(StampConfigTranslator.STOP_STR),
						info.getHardwareParameterValue(StampConfigTranslator.STOP_STR))));

		stampConfig.addCommandData(StampConfigTranslator.TBS_STR, new Integer((int) config.getSelectedFrequency()
				.getFrequency()));

		stampConfig.addCommandData(StampConfigTranslator.NUMSAMPLES_STR, new Integer(config.getTotalSamples()));

		StampTranslator translator = StampTranslatorProcessorManager.getTranslator(stampConfig);
		if (!translator.translate(stampConfig))
			throw new WrongConfigurationException("Cannot translate StampCommand!", -1);

		config.getChannelsConfig(0).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(1).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(2).setTotalSamples(config.getTotalSamples());

		this.config = config;

		fireIDriverStateListenerDriverConfigured();
	}

	public HardwareAcquisitionConfig getAcquisitionHeader() {
		return config;
	}

	

	public AbstractStampDataSource initDataSource() {
		MomInerciaStampDataSource dataSource = super.initDataSource();
		dataSource.setAcquisitionHeader(getAcquisitionHeader());
		return dataSource;
	}

	protected void loadExtraCommandHandlers() {
		String packageName = getClass().getPackage().getName() + ".";
		StampTranslatorProcessorManager
				.initStampProcessorsTranslators(new String[] { packageName + "processors.StampMomInerciaProcessor",
						packageName + "translators.StampConfigTranslator", });
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
			config.setTimeStart(dt);
			if (dataSource != null)
				dataSource.setDataSourceStarted();

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
