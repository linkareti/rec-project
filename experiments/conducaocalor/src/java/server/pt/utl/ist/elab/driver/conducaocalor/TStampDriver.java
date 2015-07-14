/*
 * RadioactividadeStampDriver.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.conducaocalor;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.conducaocalor.translators.StampConfigTranslator;
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
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.threading.WaitForConditionResult;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class TStampDriver extends AbstractStampDriver<TStampDataSource> {
	private static final Logger LOGGER = Logger.getLogger(TStampDriver.class.getName());

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

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {

		fireIDriverStateListenerDriverConfiguring();

		stampConfig = new StampCommand(AbstractStampDriver.CONFIG_OUT_STRING);

		LOGGER.log(Level.INFO, "*******************************************");

		stampConfig.addCommandData(
				StampConfigTranslator.MODE_STR,
				new String(Defaults.defaultIfEmpty(
						config.getSelectedHardwareParameterValue(StampConfigTranslator.MODE_STR),
						info.getHardwareParameterValue(StampConfigTranslator.MODE_STR))));

		LOGGER.log(Level.INFO, info.getHardwareParameterValue(StampConfigTranslator.MODE_STR));

		stampConfig.addCommandData(
				StampConfigTranslator.HEAT_TIME_STR,
				new Integer(Defaults.defaultIfEmpty(
						config.getSelectedHardwareParameterValue(StampConfigTranslator.HEAT_TIME_STR),
						info.getHardwareParameterValue(StampConfigTranslator.HEAT_TIME_STR))));

		LOGGER.log(Level.INFO, info.getHardwareParameterValue(StampConfigTranslator.HEAT_TIME_STR));

		stampConfig.addCommandData(
				StampConfigTranslator.MAX_HEAT_STR,
				new Integer(Defaults.defaultIfEmpty(
						config.getSelectedHardwareParameterValue(StampConfigTranslator.MAX_HEAT_STR),
						info.getHardwareParameterValue(StampConfigTranslator.MAX_HEAT_STR))));

		LOGGER.log(Level.INFO, info.getHardwareParameterValue(StampConfigTranslator.MAX_HEAT_STR));

		stampConfig.addCommandData(StampConfigTranslator.TBS_STR, new Integer((int) config.getSelectedFrequency()
				.getFrequency()));

		LOGGER.log(Level.INFO, "" + (int) config.getSelectedFrequency().getFrequency());

		stampConfig.addCommandData(StampConfigTranslator.NUMSAMPLES_STR, new Integer(config.getTotalSamples()));

		LOGGER.log(Level.INFO, "" + config.getTotalSamples());

		final StampTranslator translator = StampTranslatorProcessorManager.getTranslator(stampConfig);
		if (!translator.translate(stampConfig)) {
			throw new WrongConfigurationException("Cannot translate StampCommand!", -1);
		}

		config.getChannelsConfig(0).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(1).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(2).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(3).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(4).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(5).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(6).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(7).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(8).setTotalSamples(config.getTotalSamples());

		LOGGER.log(Level.INFO, "*******************************************");
		this.config = config;

		fireIDriverStateListenerDriverConfigured();
	}

	@Override
	public HardwareAcquisitionConfig getAcquisitionHeader() {
		return config;
	}

	@Override
	protected void loadExtraCommandHandlers() {
		final String packageName = getClass().getPackage().getName() + ".";
		StampTranslatorProcessorManager.initStampProcessorsTranslators(new String[] {
				packageName + "processors.StampTProcessor", packageName + "translators.StampConfigTranslator", });
	}

	@Override
	public void processCommand(final StampCommand cmd) {
		if (cmd == null || cmd.getCommandIdentifier() == null) {
			LOGGER.log(Level.INFO, "Can not interpret command " + cmd);
			return;
		}

		if (cmd.getCommandIdentifier().equals(AbstractStampDriver.ID_STR)) {
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
			final DateTime dt = new DateTime();
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
	// private final boolean waitingStop = false;
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
