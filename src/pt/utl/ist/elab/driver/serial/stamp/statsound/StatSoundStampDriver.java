/*
 * StatSoundStampDriver.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.serial.stamp.statsound;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDataSource;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.statsound.translators.StampConfigTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampTranslatorProcessorManager;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampConfiguredProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampNotConfiguredProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampStartProcessor;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.IConditionDecisor;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.threading.WaitForConditionResult;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author jp & Andr�
 */
public class StatSoundStampDriver extends AbstractStampDriver {
	private StampCommand stampConfig = null;
	private String typeOfExp = "";
	private int nSamples = 0;
	private int waveForm = 0;
	private int posIni = 0;
	private int posFin = 0;
	private int freqIni = 0;
	private int freqFin = 0;
	private int nPoints = 0;

	/** Creates a new instance of RadioactividadeStampDriver */
	public StatSoundStampDriver() {
		super();
		setDriverUniqueID("ELAB_STATSOUND_STAMP_V01");
		setApplicationNameLockPort("Stationary Sound Stamp Driver V0.1");
		setTimeOutPerPort(10000);
		setPortBaudRate(9600);
		loadCommandHandlers();
	}

	private double step = 0;

	public void configure(HardwareAcquisitionConfig config, HardwareInfo info)
			throws WrongConfigurationException {

		fireIDriverStateListenerDriverConfiguring();

		if (config == null) {
			System.out.println("Config is null!!");
		}

		if (info == null) {
			System.out.println("HardwareInfo is null!!");
		}

		nSamples = 0;

		typeOfExp = config
				.getSelectedHardwareParameterValue(StatSoundStampDataSource.TYPE_OF_EXP);

		String posIni = config
				.getSelectedHardwareParameterValue(StampConfigTranslator.POS_INIT_STR);
		String posFin = config
				.getSelectedHardwareParameterValue(StampConfigTranslator.POS_FIN_STR);

		freqIni = Integer
				.parseInt(config
						.getSelectedHardwareParameterValue(StatSoundStampDataSource.FREQ_INI));
		freqFin = Integer
				.parseInt(config
						.getSelectedHardwareParameterValue(StatSoundStampDataSource.FREQ_END));
		
		if (freqFin < freqIni) {
			int temp = freqFin;
			freqFin = freqIni;
			freqIni = temp;
		}

		typeOfExp = config
				.getSelectedHardwareParameterValue(StatSoundStampDataSource.TYPE_OF_EXP);
		if (typeOfExp.equalsIgnoreCase(StatSoundStampDataSource.EXP_1)) {
			/** This are the stamp n Samples... */
			nSamples = config.getTotalSamples();
		} else {
			nSamples = 1;
			posFin = posIni;
		}

		if (config.getTotalSamples() != 1) {
			step = Math.abs((double) (freqIni - freqFin))
					/ ((double) (config.getTotalSamples() - 1));
			System.out.println("Step=" + step);
		} else {
			/** Step needs to be bigger than 1 to get out of the for cycle */
			step = 1;
			freqIni = freqFin;
		}

		waveForm = Integer
				.parseInt(config
						.getSelectedHardwareParameterValue(StatSoundStampDataSource.WAVE_FORM));

		try {
			nPoints = Integer
					.parseInt(config
							.getSelectedHardwareParameterValue(StatSoundStampDataSource.N_POINTS));
		} catch (NumberFormatException e) {
			System.out.println("nPoint throw an exception");
			nPoints = 50;
		}

		stampConfig = new StampCommand(CONFIG_OUT_STRING);

		stampConfig.addCommandData(StampConfigTranslator.NUMSAMPLES_STR,
				new Integer(nSamples));

		this.posIni = Integer.parseInt(posIni);

		stampConfig.addCommandData(StampConfigTranslator.POS_INIT_STR,
				new Integer(posIni));

		stampConfig.addCommandData(StampConfigTranslator.POS_FIN_STR,
				new Integer(posFin));

		stampConfig
				.addCommandData(
						StampConfigTranslator.STATUS_STR,
						new String(
								config
										.getSelectedHardwareParameterValue(StampConfigTranslator.STATUS_STR)));

		stampConfig
				.addCommandData(
						StampConfigTranslator.RESET_STR,
						new Integer(
								config
										.getSelectedHardwareParameterValue(StampConfigTranslator.RESET_STR)));

		StampTranslator translator = StampTranslatorProcessorManager
				.getTranslator(stampConfig);
		if (!translator.translate(stampConfig))
			throw new WrongConfigurationException(
					"Cannot translate StampCommand!", -1);

		if (typeOfExp.equalsIgnoreCase(StatSoundStampDataSource.EXP_2)) {
			config.setTotalSamples(config.getTotalSamples() + 1);
		} else {
			config.setTotalSamples(config.getTotalSamples() + 2); // +2 for temp
																	// ini and
																	// temp
																	// final
		}

		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(
					config.getTotalSamples());
		}
		this.config = config;

		fireIDriverStateListenerDriverConfigured();
	}

	public HardwareAcquisitionConfig getAcquisitionHeader() {
		return config;
	}

	public Object getHardwareInfo() {
		String baseHardwareInfoFile = "recresource://pt/utl/ist/elab/driver/serial/stamp/statsound/StatSoundHardwareInfo.xml";
		String prop = Defaults.defaultIfEmpty(System
				.getProperty("eLab.StatSound.HardwareInfo"),
				baseHardwareInfoFile);

		if (prop.indexOf("://") == -1)
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;
		java.net.URL url = null;
		try {
			url = ReCProtocols.getURL(prop);
		} catch (java.net.MalformedURLException e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e,
					Logger.getLogger(STAMP_DRIVER_LOGGER));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: "
						+ baseHardwareInfoFile, e2, Logger
						.getLogger(STAMP_DRIVER_LOGGER));
			}
		}
		System.out.println("Returning url=" + url);
		System.out.println("File=" + url.getFile());
		return url;
	}

	private StatSoundStampDataSource dataSource;

	public AbstractStampDataSource initDataSource() {
		dataSource = new StatSoundStampDataSource();
		dataSource.setAcquisitionHeader(getAcquisitionHeader());
		return dataSource;
	}

	protected void loadExtraCommandHandlers() {
		String packageName = getClass().getPackage().getName() + ".";
		StampTranslatorProcessorManager
				.initStampProcessorsTranslators(new String[] {
						packageName + "processors.StampStatSoundProcessor",
						packageName + "processors.StampStatSoundTempProcessor",
						packageName + "translators.StampConfigTranslator", });
	}

	public void processCommand(StampCommand cmd) {
		// Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,"Passou por aqui com mensagem: "
		// + cmd.getCommand() + " " + cmd.getCommandIdentifier() +
		// " processado por " + cmd.getProcessor().toString());

		if (cmd == null || cmd.getCommandIdentifier() == null) {
			Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,
					"Can not interpret command " + cmd);
			return;
		}

		if (cmd.getCommandIdentifier().equals(ID_STR)) {
			if (dataSource != null
					&& !dataSource.isExpEnded()
					&& !config.getSelectedHardwareParameterValue(
							dataSource.TYPE_OF_EXP).equalsIgnoreCase(
							dataSource.EXP_1)) {
				System.out.println("Returning");
				return;
			}
			if (waitingStart) {
				System.out.println("Waiting start");
				waitingStart = false;
				writeMessage(stampConfig.getCommand());
				wroteStart = true;
				fireIDriverStateListenerDriverStarting();
			} else if (stoping) {
				System.out.println("Stoping");
				stoping = false;
				started = false;
				fireIDriverStateListenerDriverStoped();
			} else if (reseting) {
				System.out.println("Reseting");
				reseting = false;
				fireIDriverStateListenerDriverReseted();
				stopDataSource();
			} else if (started) {
				System.out.println("Started");
				started = false;
				fireIDriverStateListenerDriverStoping();
				fireIDriverStateListenerDriverStoped();
				stopDataSource();
			} else if (initing) {
				System.out.println("Initing");
				initing = false;
				fireIDriverStateListenerDriverReseting();
				fireIDriverStateListenerDriverReseted();
			}
		} else if (cmd.getCommandIdentifier().equals(
				StampStartProcessor.COMMAND_IDENTIFIER)) {
			started = true;

			for (int i = 0; i < config.getChannelsConfig().length; i++) {
				config.getChannelsConfig(i).setTimeStart(new DateTime());
			}

			config.setTimeStart(new DateTime());
			dataSource.setPosIni(posIni);
			dataSource.setPosFin(posFin);
			dataSource.setFreqIni(freqIni);
			dataSource.setFreqFin(freqFin);
			dataSource.setFreqStep(step);
			dataSource.setNPoints(nPoints);
			dataSource.setFirstTime(true);
			
			System.out.println("Debug Version 001");
			
			if (typeOfExp.equalsIgnoreCase(dataSource.EXP_1)) {
				dataSource.startAcquiring(false);
				dataSource.setExpEnded(false);
			} else if (typeOfExp.equalsIgnoreCase(dataSource.EXP_2)) {
				dataSource.startAcquiring(false);
				dataSource.setExpEnded(false);
			} else {
				//dataSource.setWaveForm(waveForm);
				System.out.println("Running waveform : " + waveForm);
				if (waveForm == 0)
					dataSource.playPinkNoise(freqIni, 15);
				else if (waveForm == 1) {
					dataSource.playPulseWave(freqIni, 15);
				}
				dataSource.startAcquiring(false);
				dataSource.setExpEnded(false);
			}
			fireIDriverStateListenerDriverStarted();
			if (dataSource != null) {
				// dataSource.setRunning(true);
			}
		} else if (cmd.getCommandIdentifier().equals(
				StampConfiguredProcessor.COMMAND_IDENTIFIER)) {
			// OK to go... - still gonna receive start...!
		}

		else if (cmd.getCommandIdentifier().equals(
				StampNotConfiguredProcessor.COMMAND_IDENTIFIER)) {
			if (waitingStart && wroteStart) {
				waitingStart = false;
				fireIDriverStateListenerDriverStoped();
				stopDataSource();
			} else if (started) {
				System.out.println("Started not configured!");
				started = false;
				fireIDriverStateListenerDriverReseting();
				fireIDriverStateListenerDriverReseted();
				stopDataSource();
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

	public void stopDataSource() {
		if (dataSource != null) {
			System.out.println("Stoping data source!");
			dataSource.stopPlaying();
			dataSource.stopAcquiring();
			// dataSource.setRunning(false);
			dataSource.setExpEnded(true);
			System.out.println("Finished stop data source!");
		}
	}

	public void stop(HardwareInfo info) throws IncorrectStateException {
		System.out.println("Stop called!");
		fireIDriverStateListenerDriverStoping();
		stopDataSource();
		stoping = true;
	}

	public void startNow() throws TimedOutException {
		wroteStart = false;

		if (stampConfig == null)
			throw new TimedOutException("No configuration available yet!");

		waitingStart = true;

		WaitForConditionResult.waitForConditionTrue(
				new AbstractConditionDecisor() {
					public int getConditionResult() {
						if (!waitingStart)
							return IConditionDecisor.CONDITION_MET_TRUE;

						return IConditionDecisor.CONDITION_NOT_MET;
					}
				}, 400000, 1000);
	}
}
