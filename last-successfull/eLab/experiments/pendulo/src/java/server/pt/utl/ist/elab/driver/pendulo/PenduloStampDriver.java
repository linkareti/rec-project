/*
 * RadioactividadeStampDriver.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.pendulo;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.pendulo.translators.StampConfigTranslator;
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
public class PenduloStampDriver extends AbstractStampDriver {
	private StampCommand stampConfig = null;

	/** Creates a new instance of RadioactividadeStampDriver */
	public PenduloStampDriver() {
		super();
		setDriverUniqueID("ELAB_PENDULO_STAMP_V02");
		setApplicationNameLockPort("Pendulo Stamp Driver V0.2");
		setTimeOutPerPort(10000);
		setPortBaudRate(38400);
		loadCommandHandlers();
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {

		fireIDriverStateListenerDriverConfiguring();

		stampConfig = new StampCommand(AbstractStampDriver.CONFIG_OUT_STRING);

		stampConfig.addCommandData(
				StampConfigTranslator.TETA_INIT_STR,
				new Integer(Defaults.defaultIfEmpty(
						config.getSelectedHardwareParameterValue(StampConfigTranslator.TETA_INIT_STR),
						info.getHardwareParameterValue(StampConfigTranslator.TETA_INIT_STR))));

		stampConfig.addCommandData(
				StampConfigTranslator.HEIGHT_INIT_STR,
				new Integer(Defaults.defaultIfEmpty(
						config.getSelectedHardwareParameterValue(StampConfigTranslator.HEIGHT_INIT_STR),
						info.getHardwareParameterValue(StampConfigTranslator.HEIGHT_INIT_STR))));

		stampConfig.addCommandData(StampConfigTranslator.FRICTION_STR, Defaults.defaultIfEmpty(
				config.getSelectedHardwareParameterValue(StampConfigTranslator.FRICTION_STR),
				info.getHardwareParameterValue(StampConfigTranslator.FRICTION_STR)));

		stampConfig.addCommandData(StampConfigTranslator.NUMSAMPLES_STR, new Integer(config.getTotalSamples()));

		stampConfig.addCommandData(StampConfigTranslator.FREQ_INTERBAL_STR, new Integer((int) config
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
	public Object getHardwareInfo() {
		final String baseHardwareInfoFile = "recresource://" + getClass().getPackage().getName().replaceAll("\\.", "/")
				+ "/HardwareInfo.xml";
		String prop = Defaults.defaultIfEmpty(System.getProperty("HardwareInfo"), baseHardwareInfoFile);

		if (prop.indexOf("://") == -1) {
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;
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

	@Override
	public AbstractStampDataSource initDataSource() {
		final PenduloStampDataSource dataSource = new PenduloStampDataSource();
		dataSource.setAcquisitionHeader(getAcquisitionHeader());
		return dataSource;
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
			Logger.getLogger(AbstractStampDriver.STAMP_DRIVER_LOGGER).log(Level.INFO,
					"Can not interpret command " + cmd);
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
	private final boolean waitingStop = false;
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
