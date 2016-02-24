package pt.utl.ist.elab.driver.pendulogravitico;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.pendulogravitico.translators.StampConfigTranslator;
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
 * @author André
 */
public class PenduloStampDriver extends AbstractStampDriver<PenduloStampDataSource> {

	private static final Logger LOGGER = Logger.getLogger(PenduloStampDriver.class.getName());

	private StampCommand stampConfig = null;

	/** Creates a new instance of RadioactividadeStampDriver */
	public PenduloStampDriver() {
		super();
		setDriverUniqueID("PENDULO_GRAV");
		setApplicationNameLockPort("Pendulo Gravitico Stamp Driver V0.2");
		setTimeOutPerPort(10000);
		// This driver is communicating with a pic which does not need
		// the wait for echo to work...
		setWaitForEcho(false);
		setPortBaudRate(115200);
		loadCommandHandlers();
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {

		fireIDriverStateListenerDriverConfiguring();

		stampConfig = new StampCommand(AbstractStampDriver.CONFIG_OUT_STRING);

		stampConfig.addCommandData(
				StampConfigTranslator.TETA_INIT_STR,
				new Float(Defaults.defaultIfEmpty(
						config.getSelectedHardwareParameterValue(StampConfigTranslator.TETA_INIT_STR),
						info.getHardwareParameterValue(StampConfigTranslator.TETA_INIT_STR))));

		stampConfig.addCommandData(
				StampConfigTranslator.HEIGHT_INIT_STR,
				new Float(Defaults.defaultIfEmpty(
						config.getSelectedHardwareParameterValue(StampConfigTranslator.HEIGHT_INIT_STR),
						info.getHardwareParameterValue(StampConfigTranslator.HEIGHT_INIT_STR))));

		stampConfig.addCommandData(StampConfigTranslator.NUMSAMPLES_STR, new Integer(config.getTotalSamples()));

		stampConfig.addCommandData(StampConfigTranslator.FREQ_INTERVAL_STR, new Integer((int) config
				.getSelectedFrequency().getFrequency()));

		final StampTranslator translator = StampTranslatorProcessorManager.getTranslator(stampConfig);
		if (!translator.translate(stampConfig)) {
			throw new WrongConfigurationException("Cannot translate StampCommand!", -1);
		}

		config.getChannelsConfig(0).setTotalSamples(config.getTotalSamples());

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
				packageName + "processors.StampPenduloProcessor", packageName + "translators.StampConfigTranslator", });
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
				wroteStart = true;
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
			config.getChannelsConfig(0).setTimeStart(new DateTime());
			config.setTimeStart(new DateTime());
			fireIDriverStateListenerDriverStarted();
			/*
			 * if(dataSource!=null) dataSource.setRunning(true);
			 */

		} else if (cmd.getCommandIdentifier().equals(StampConfiguredProcessor.COMMAND_IDENTIFIER)) {
			// OK to go... - still gonna receive start...!

		}

		else if (cmd.getCommandIdentifier().equals(StampNotConfiguredProcessor.COMMAND_IDENTIFIER)) {
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
		wroteStart = false;

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
