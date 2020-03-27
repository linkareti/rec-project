package pt.utl.ist.elab.driver.radioactividade;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.radioactividade.translators.StampConfigTranslator;
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
public class RadioactividadeStampDriver extends AbstractStampDriver<RadioactividadeStampDataSource> {

	private static final Logger LOGGER = Logger.getLogger(RadioactividadeStampDriver.class.getName());

	public static final String[] ACQUISITION_MATERIALS = new String[] { "Madeira [10mm]", "Corticite [10mm]",
			"Tijolo [10mm]", "Cobre [0.2mm]", "Cobre [0.4mm]", "Cobre [0.8mm]", "Cobre [1.6mm]", "Cobre [3.2mm]",
			"Janela de controlo (Ar)", "Chumbo (isolante)" };
	public static final String[] ACQUISITION_MODES = new String[] { "Contagens", "Tempo entre Eventos" };

	private StampCommand stampConfig = null;

	/** Creates a new instance of RadioactividadeStampDriver */
	public RadioactividadeStampDriver() {
		super();
		setDriverUniqueID("ELAB_RAD_STAMP_V02");
		setApplicationNameLockPort("Radioactividade Stamp Driver V0.2");
		setTimeOutPerPort(10000);
		setPortBaudRate(4800);
		loadCommandHandlers();
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {

		fireIDriverStateListenerDriverConfiguring();

		stampConfig = new StampCommand(AbstractStampDriver.CONFIG_OUT_STRING);
		stampConfig.addCommandData(
				StampConfigTranslator.MODE_STR,
				Defaults.defaultIfEmpty(config.getSelectedHardwareParameterValue(StampConfigTranslator.MODE_STR),
						info.getHardwareParameterValue(StampConfigTranslator.MODE_STR)));
		stampConfig.addCommandData(StampConfigTranslator.HEIGHT_STR, Defaults.defaultIfEmpty(
				config.getSelectedHardwareParameterValue(StampConfigTranslator.HEIGHT_STR),
				info.getHardwareParameterValue(StampConfigTranslator.HEIGHT_STR)));
		stampConfig.addCommandData(StampConfigTranslator.MATERIAL_STR, Defaults.defaultIfEmpty(
				config.getSelectedHardwareParameterValue(StampConfigTranslator.MATERIAL_STR),
				info.getHardwareParameterValue(StampConfigTranslator.MATERIAL_STR)));

		stampConfig.addCommandData(StampConfigTranslator.NUMSAMPLES_STR, Integer.valueOf(config.getTotalSamples()));

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
				packageName + "processors.StampCounterProcessor", packageName + "processors.StampTimeProcessor",
				packageName + "translators.StampConfigTranslator", });
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
