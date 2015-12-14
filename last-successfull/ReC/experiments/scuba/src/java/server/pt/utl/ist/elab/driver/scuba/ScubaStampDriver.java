package pt.utl.ist.elab.driver.scuba;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.scuba.translators.StampConfigTranslator;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampTranslatorProcessorManager;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampConfiguredProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampNotConfiguredProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampStartProcessor;

import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.Multiplier;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.data.synch.FrequencyDefType;
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.threading.WaitForConditionResult;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ScubaStampDriver extends AbstractStampDriver<ScubaStampDataSource> {

	private static final Logger LOGGER = Logger.getLogger(ScubaStampDriver.class.getName());

	private StampCommand stampConfig = null;

	/** Creates a new instance of RadioactividadeStampDriver */
	public ScubaStampDriver() {
		super();
		setDriverUniqueID("ELAB_SCUBA_STAMP_V02");
		setApplicationNameLockPort("Scuba Stamp Driver V0.2");
		setTimeOutPerPort(10000);
		loadCommandHandlers();
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {

		fireIDriverStateListenerDriverConfiguring();

		stampConfig = new StampCommand(AbstractStampDriver.CONFIG_OUT_STRING);

		stampConfig.addCommandData(
				StampConfigTranslator.XINI_STR,
				new Integer(Defaults.defaultIfEmpty(
						config.getSelectedHardwareParameterValue(StampConfigTranslator.XINI_STR),
						info.getHardwareParameterValue(StampConfigTranslator.XINI_STR))));

		stampConfig.addCommandData(
				StampConfigTranslator.XFIN_STR,
				new Integer(Defaults.defaultIfEmpty(
						config.getSelectedHardwareParameterValue(StampConfigTranslator.XFIN_STR),
						info.getHardwareParameterValue(StampConfigTranslator.XFIN_STR))));

		stampConfig.addCommandData(
				StampConfigTranslator.CALIB_STR,
				new Integer(Defaults.defaultIfEmpty(
						config.getSelectedHardwareParameterValue(StampConfigTranslator.CALIB_STR),
						info.getHardwareParameterValue(StampConfigTranslator.CALIB_STR))));

		stampConfig.addCommandData(StampConfigTranslator.NUMSAMPLES_STR, new Integer(config.getTotalSamples()));

		final StampTranslator translator = StampTranslatorProcessorManager.getTranslator(stampConfig);
		if (!translator.translate(stampConfig)) {
			throw new WrongConfigurationException("Cannot translate StampCommand!", -1);
		}

		config.getChannelsConfig(0).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(1).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(2).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(3).setTotalSamples(config.getTotalSamples());
		config.getChannelsConfig(4).setTotalSamples(config.getTotalSamples());

		final double xfin = Double.parseDouble(config.getSelectedHardwareParameterValue("xfin"));
		final double xini = Double.parseDouble(config.getSelectedHardwareParameterValue("xini"));
		final double dx = 45. * Math.abs(xfin - xini) / config.getTotalSamples() / 10.;
		final double tbs_ms = 25. * dx + 400.;

		config.setSelectedFrequency(new Frequency(tbs_ms, Multiplier.mili, FrequencyDefType.SamplingIntervalType));

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
				packageName + "processors.StampScubaProcessor", packageName + "translators.StampConfigTranslator", });
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
			config.getChannelsConfig(0).setTimeStart(new DateTime());
			config.getChannelsConfig(1).setTimeStart(new DateTime());
			config.getChannelsConfig(2).setTimeStart(new DateTime());
			config.getChannelsConfig(3).setTimeStart(new DateTime());
			config.getChannelsConfig(4).setTimeStart(new DateTime());
			config.setTimeStart(new DateTime());
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
