/*
 * RadioactividadeStampDriver.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric.genericexperiment;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.serialportgeneric.AbstractSerialPortDataSource;
import pt.utl.ist.elab.driver.serial.serialportgeneric.AbstractSerialPortDriver;
import pt.utl.ist.elab.driver.serial.serialportgeneric.DriverAction;
import pt.utl.ist.elab.driver.serial.serialportgeneric.DriverState;
import pt.utl.ist.elab.driver.serial.serialportgeneric.transproc.SerialPortCommand;
import pt.utl.ist.elab.driver.serial.serialportgeneric.transproc.SerialPortCommandList;

import com.linkare.rec.acquisition.HardwareState;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.IConditionDecisor;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.threading.WaitForConditionResult;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class GenericSerialPortDriver extends AbstractSerialPortDriver {

	private static final String BIN_BLOCK = "_BIN";
	private static final String DATA_BLOCK = "_DAT";
	private static final String ERR = "ERR";
	private SerialPortCommand stampConfig = null;
	private String hardwareConfigFile = null;
	private String rs232ConfigFile = null;
	private String className = null;
	private String packageName = null;

	private boolean initing = true;
	private boolean waitingStart = false;
	private boolean wroteStart = false;
	private boolean waitingStop = false;

	private boolean stoping = false;
	private boolean reseting = true;

	private boolean started = false;

	public static DriverState currentDriverState = DriverState.STOPPED;
	public static DriverAction currentDriverAction = DriverAction.IDLE;
	public static int currentBinaryLength = 0;
	private static BaseHardware baseHardware = null;

	/** Creates a new instance of GenericStampDriver */
	public GenericSerialPortDriver() {
		super();
		// TODO : define-se depois com no setBase
		setDriverUniqueID("");
		setApplicationNameLockPort("Generic Stamp Driver V1.0");
		className = this.getClass().getName().replaceFirst("StampDriver", "").replaceFirst(".class", "");
		hardwareConfigFile = className.concat("BaseHardwareInfo.xml");
		rs232ConfigFile = className.concat("Rs232Config.xml");
		packageName = getClass().getPackage().getName() + ".";
		setTimeOutPerPort(10000);
		// loadCommandHandlers();
	}

	public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {

		// TODO : configuração do stamp

		/*
		 * fireIDriverStateListenerDriverConfiguring();
		 * 
		 * stampConfig = new StampCommand(CONFIG_OUT_STRING); stampConfig.addCommandData(StampConfigTranslator.MODE_STR,
		 * Defaults.defaultIfEmpty(config .getSelectedHardwareParameterValue(StampConfigTranslator.MODE_STR), info
		 * .getHardwareParameterValue(StampConfigTranslator.MODE_STR)));
		 * stampConfig.addCommandData(StampConfigTranslator.HEIGHT_STR, Defaults.defaultIfEmpty(config
		 * .getSelectedHardwareParameterValue(StampConfigTranslator.HEIGHT_STR), info
		 * .getHardwareParameterValue(StampConfigTranslator.HEIGHT_STR)));
		 * stampConfig.addCommandData(StampConfigTranslator.MATERIAL_STR, Defaults.defaultIfEmpty(config
		 * .getSelectedHardwareParameterValue(StampConfigTranslator.MATERIAL_STR), info
		 * .getHardwareParameterValue(StampConfigTranslator.MATERIAL_STR)));
		 * 
		 * stampConfig.addCommandData(StampConfigTranslator.NUMSAMPLES_STR, new Integer(config.getTotalSamples()));
		 * 
		 * StampTranslator translator = StampTranslatorProcessorManager.getTranslator(stampConfig); if
		 * (!translator.translate(stampConfig)) throw new WrongConfigurationException("Cannot translate StampCommand!",
		 * -1);
		 * 
		 * config.getChannelsConfig(0).setTotalSamples(config.getTotalSamples());
		 * 
		 * this.config = config;
		 * 
		 * fireIDriverStateListenerDriverConfigured();
		 */
	}

	public HardwareAcquisitionConfig getAcquisitionHeader() {
		return config;
	}

	public Object getHardwareInfo() {
		// TODO : definir o url para o ficheiro de definições de hardware

		// TODO : change this url
		String packageBase = "recresource://pt/utl/ist/elab/driver/serial/stampgeneric/genericexperiment";
		String prop = Defaults.defaultIfEmpty(System.getProperty("eLab." + className + ".HardwareInfo"),
				hardwareConfigFile);

		if (prop.indexOf("://") == -1)
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;

		java.net.URL url = null;
		try {
			url = ReCProtocols.getURL(prop);
		} catch (java.net.MalformedURLException e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e, Logger.getLogger(STAMP_DRIVER_LOGGER));
			try {
				url = new java.net.URL(hardwareConfigFile);
			} catch (java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + hardwareConfigFile, e2, Logger
						.getLogger(STAMP_DRIVER_LOGGER));
			}
		}

		return url;

	}

	public AbstractSerialPortDataSource initDataSource() {
		GenericSerialPortDataSource dataSource = new GenericSerialPortDataSource();
		dataSource.setAcquisitionHeader(getAcquisitionHeader());
		return dataSource;
	}

	protected void loadExtraCommandHandlers() {
		/*
		 * SerialPortTranslatorProcessorManager.initStampProcessorsTranslators(new String[] { packageName +
		 * "processors.StampCounterProcessor", packageName + "processors.StampTimeProcessor", packageName +
		 * "translators.StampConfigTranslator", });
		 */
	}

	public void processCommand(SerialPortCommand cmd) {

		if (cmd == null || cmd.getCommandIdentifier() == null || baseHardware == null) {
			Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO, "Can not interpret command " + cmd);
			return;
		}

		if (SerialPortCommandList.IDS.toString().equalsIgnoreCase(cmd.getCommandIdentifier())) {
			// receives identification
			fireIDriverStateListenerDriverInited();

		} else if (SerialPortCommandList.CFG.toString().equalsIgnoreCase(cmd.getCommandIdentifier())) {
			// echo from configuration
			if (baseHardware.getHardwareState().getValue() != HardwareState._CONFIGURING
					&& currentDriverAction != DriverAction.SETTING_CONFIG) {
				Logger.getLogger(STAMP_DRIVER_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			if (isEqualToNewConfig(cmd, new Object())) {
				// currentConfig = newConfig;
				fireIDriverStateListenerDriverConfiguring();
			}
			else {
				// newConfig = currentConfig;
				return;
			}
		} else if (SerialPortCommandList.CFGOK.toString().equalsIgnoreCase(cmd.getCommandIdentifier())) {
			// configuration is now set
			if (currentDriverAction != DriverAction.SETTING_CONFIG && currentDriverState != DriverState.WAIT) {
				Logger.getLogger(STAMP_DRIVER_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.IDLE;
			currentDriverState = DriverState.STOPPED;
			fireIDriverStateListenerDriverConfigured();
		} else if (SerialPortCommandList.CUR.toString().equalsIgnoreCase(cmd.getCommandIdentifier())) {

			// receiving current configuration
			if (currentDriverAction != DriverAction.GETTING_CONFIG && currentDriverState != DriverState.STOPPED) {
				Logger.getLogger(STAMP_DRIVER_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.IDLE;
			currentDriverState = DriverState.WAIT;
		} else if (SerialPortCommandList.STR.toString().equalsIgnoreCase(cmd.getCommandIdentifier())) {
			// starting
			if (currentDriverAction != DriverAction.STARTING && currentDriverState != DriverState.STOPPED) {
				Logger.getLogger(STAMP_DRIVER_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.IDLE;
			currentDriverState = DriverState.STARTED;
			fireIDriverStateListenerDriverStarting();
			fireIDriverStateListenerDriverStarted();
		} else if (SerialPortCommandList.DAT.toString().equalsIgnoreCase(cmd.getCommandIdentifier())) {
			// will receive data
			if (currentDriverAction != DriverAction.IDLE && currentDriverState != DriverState.STARTED) {
				Logger.getLogger(STAMP_DRIVER_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.GETTING_DATA;
			currentDriverState = DriverState.READ;
		} else if (DATA_BLOCK.equalsIgnoreCase(cmd.getCommandIdentifier())) {
			// receiving data
			if (currentDriverAction != DriverAction.IDLE && currentDriverState != DriverState.STARTED) {
				Logger.getLogger(STAMP_DRIVER_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.GETTING_DATA;
			currentDriverState = DriverState.READ;
		} else if (SerialPortCommandList.END.toString().equalsIgnoreCase(cmd.getCommandIdentifier())) {
			// end of data
			if (currentDriverAction != DriverAction.GETTING_DATA && currentDriverState != DriverState.READ) {
				Logger.getLogger(STAMP_DRIVER_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.IDLE;
			currentDriverState = DriverState.STARTED;
		} else if (SerialPortCommandList.BIN.toString().equalsIgnoreCase(cmd.getCommandIdentifier())) {
			// will receive binary data
			if (currentDriverAction != DriverAction.IDLE && currentDriverState != DriverState.STARTED) {
				Logger.getLogger(STAMP_DRIVER_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.GETTING_BINARY;
			currentDriverState = DriverState.READ;
		}
		// internal use only
		else if (BIN_BLOCK.equalsIgnoreCase(cmd.getCommandIdentifier())) {
			// receiving binary data
			if (currentDriverAction != DriverAction.GETTING_BINARY && currentDriverState != DriverState.READ) {
				Logger.getLogger(STAMP_DRIVER_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.IDLE;
			currentDriverState = DriverState.STARTED;
		} else if (SerialPortCommandList.STP.toString().equalsIgnoreCase(cmd.getCommandIdentifier())) {
			if (currentDriverAction != DriverAction.STOPPING && currentDriverState != DriverState.STARTED) {
				Logger.getLogger(STAMP_DRIVER_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.STOPPING;
			currentDriverState = DriverState.WAIT;
			fireIDriverStateListenerDriverStoping();
		} else if (SerialPortCommandList.STPOK.toString().equalsIgnoreCase(cmd.getCommandIdentifier())) {
			if (currentDriverAction != DriverAction.STOPPING && currentDriverState != DriverState.WAIT) {
				Logger.getLogger(STAMP_DRIVER_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.IDLE;
			currentDriverState = DriverState.STOPPED;
			fireIDriverStateListenerDriverStoping();
		} else if (SerialPortCommandList.RST.toString().equalsIgnoreCase(cmd.getCommandIdentifier())) {
			if (currentDriverAction != DriverAction.RESETTING && currentDriverState != DriverState.STARTED) {
				Logger.getLogger(STAMP_DRIVER_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.IDLE;
			currentDriverState = DriverState.WAIT;
			fireIDriverStateListenerDriverReseting();
		} else if (SerialPortCommandList.RSTOK.toString().equalsIgnoreCase(cmd.getCommandIdentifier())) {
			if (currentDriverAction != DriverAction.RESETTING && currentDriverState != DriverState.WAIT) {
				Logger.getLogger(STAMP_DRIVER_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.IDLE;
			currentDriverState = DriverState.STOPPED;
			fireIDriverStateListenerDriverReseted();
		} else if (SerialPortCommandList.ERR.toString().equalsIgnoreCase(cmd.getCommandIdentifier())) {
			Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,
					"An error occured while in action" + currentDriverAction + " and state" + currentDriverState);
			currentDriverAction = DriverAction.IDLE;
			currentDriverState = DriverState.STOPPED;
			fireIDriverStateListenerDriverShutdown();
		} else {
			Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO, "Command " + cmd + " is unknown!");
			return;
		}

		/*
		 * if (cmd.getCommandIdentifier().equals(ID_STR)) { if (waitingStart) { waitingStart = false;
		 * writeMessage(stampConfig.getCommand()); wroteStart = false; fireIDriverStateListenerDriverStarting(); } else
		 * if (stoping) { stoping = false; fireIDriverStateListenerDriverStoping(); super.stopDataSource(); } else if
		 * (reseting) { reseting = false; fireIDriverStateListenerDriverReseted(); super.stopDataSource(); } else if
		 * (started) { started = false; fireIDriverStateListenerDriverStoping(); fireIDriverStateListenerDriverStoped();
		 * super.stopDataSource(); } else if (initing) { initing = false; fireIDriverStateListenerDriverReseting();
		 * fireIDriverStateListenerDriverReseted(); } } else if
		 * (cmd.getCommandIdentifier().equals(SerialPortStartProcessor.COMMAND_IDENTIFIER)) { started = true;
		 * config.getChannelsConfig(0).setTimeStart(new DateTime()); config.setTimeStart(new DateTime());
		 * fireIDriverStateListenerDriverStarted(); /* if(dataSource!=null) dataSource.setRunning(true);
		 */
		/*
		 * } else if (cmd.getCommandIdentifier().equals(SerialPortConfiguredProcessor.COMMAND_IDENTIFIER)) { // OK to
		 * go... - still gonna receive start...! }
		 * 
		 * else if (cmd.getCommandIdentifier().equals(SerialPortNotConfiguredProcessor.COMMAND_IDENTIFIER)) { if
		 * (waitingStart && wroteStart) { waitingStart = false; fireIDriverStateListenerDriverStoped();
		 * super.stopDataSource(); } else if (started) { started = false; fireIDriverStateListenerDriverReseting();
		 * fireIDriverStateListenerDriverReseted(); super.stopDataSource(); } }
		 */

	}

	private boolean isEqualToNewConfig(SerialPortCommand cmd, Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean compareWithCurrentConfig(SerialPortCommand cmd, Object object) {
		// TODO Auto-generated method stub
		return true;
	}

	public void startNow() throws TimedOutException {
		if (stampConfig == null)
			throw new TimedOutException("No configuration available yet!");

		currentDriverAction = DriverAction.STARTING;
		waitingStart = true;

		WaitForConditionResult.waitForConditionTrue(new AbstractConditionDecisor() {
			public int getConditionResult() {
				if (currentDriverAction != DriverAction.STARTING)
					return IConditionDecisor.CONDITION_MET_TRUE;

				return IConditionDecisor.CONDITION_NOT_MET;
			}
		}, 20000, 500);
	}

	public void setBase(BaseHardware baseHardware) {
		// TODO : pegar na informação do ficheiro de hardware
		baseHardware.getHardwareInfo();
	}

}
