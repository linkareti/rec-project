/*
 * RadioactividadeStampDriver.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric.genericexperiment;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.serialportgeneric.AbstractSerialPortDataSource;
import pt.utl.ist.elab.driver.serial.serialportgeneric.AbstractSerialPortDriver;
import pt.utl.ist.elab.driver.serial.serialportgeneric.DriverState;
import pt.utl.ist.elab.driver.serial.serialportgeneric.SerialPortConfig;
import pt.utl.ist.elab.driver.serial.serialportgeneric.transproc.SerialPortCommand;
import pt.utl.ist.elab.driver.serial.serialportgeneric.transproc.SerialPortCommandList;

import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 * @author fdias
 * @version Oct 2009
 */
public class GenericSerialPortDriver extends AbstractSerialPortDriver {

	private SerialPortCommand stampConfig = null;
	private String hardwareConfigFile = null;
	private String rs232ConfigFile = null;
	private String className = null;
	private String packageName = null;
	
	HashMap<String, HashMap<String,processCommandMatrixElement>> processCommandMatrix = null;

	public static DriverState currentDriverState = DriverState.UNKNOWN;
	public static int currentBinaryLength = 0;
	public static int totalBinaryLength = 0;
	private static BaseHardware baseHardware = null;
	
	private class processCommandMatrixElement {
		private String nextState;
		private boolean expected;
		public String getNextState() {
			return nextState;
		}
		public void setNextState(String nextState) {
			this.nextState = nextState;
		}
		public boolean isExpected() {
			return expected;
		}
		public void setExpected(boolean expected) {
			this.expected = expected;
		}
	}
	
	private HashMap<String, HashMap<String,processCommandMatrixElement>> initProcessCommandMatrix() {
		
		HashMap<String, HashMap<String,processCommandMatrixElement>> processCommandMatrix = new HashMap<String, HashMap<String,processCommandMatrixElement>>();
		
		
		
		
		return null;
	}
	
	
	
	

	public GenericSerialPortDriver() {
		super();
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
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e, Logger.getLogger(SERIAL_PORT_LOGGER));
			try {
				url = new java.net.URL(hardwareConfigFile);
			} catch (java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + hardwareConfigFile, e2, Logger
						.getLogger(SERIAL_PORT_LOGGER));
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
		 * SerialPortTranslatorProcessorManager.initStampProcessorsTranslators(new
		 * String[] { packageName + "processors.StampCounterProcessor",
		 * packageName + "processors.StampTimeProcessor", packageName +
		 * "translators.StampConfigTranslator", });
		 */
	}

	public void processCommand(SerialPortCommand cmd) throws Exception {
		
		DriverState newDriverState = null;
		SerialPortCommandList thisCommand = null;

		// if the command is null, forget about it
		if (cmd == null || cmd.getCommandIdentifier() == null || baseHardware == null) {
			logMe("PROCESSCOMMAND : Cannot interpret command " + (cmd != null && cmd.getCommandIdentifier() != null ? cmd.getCommandIdentifier() : "\"null\""));
			return;
		}
		
		// if the hardware is sending data to the driver, OR
		// if the hardware speaks a unknown language, forget about it
		if (!SerialPortCommandList.exists(cmd.getCommandIdentifier())) {
			
			if (currentDriverState.equals(DriverState.RECEIVINGDATA)) {
				// TODO : process data information
				return;
			} else if (currentDriverState.equals(DriverState.RECEIVINGBIN)) {
				// TODO : process binary information
				return;
			}
			// the driver seems to speak Fortran 77, I cannot understand it
			logMe("PROCESSCOMMAND : Cannot interpret command " + cmd.getCommandIdentifier());
			return;
		}
		logMe("PROCESSCOMMAND : I'm gonna process the line : " + cmd.getCommand());
		
		// but if we understand it, let's listen to it			
		thisCommand = SerialPortCommandList.valueOf(cmd.getCommandIdentifier());
		
		// the next state according to the message of the driver
		newDriverState = currentDriverState.nextState(thisCommand);
		
		// process received data or states
		if (currentDriverState.processeMe(thisCommand)) {
			if (thisCommand.equals(SerialPortCommandList.IDS)) {
				if (cmd.getDataHashMap() == null || cmd.getDataHashMap().size() != 2) {
					// Houston we have a problem, IDS always comes with two parameters
					logMe("PROCESSCOMMAND : Error on command IDS, incorrect number of parameters: " + cmd.getDataHashMap() == null ? "null" : cmd.getDataHashMap().size() + " parameters instead of 2");
					currentDriverState = DriverState.UNKNOWN;
					// terminates this driver execution
					fireIDriverStateListenerDriverShutdown();
					return;
				}
				else {
					if (!SerialPortConfig.getInstance().getHardwareNode().getId().equals(cmd.getDataHashMap().get(1))) {
						logMe("PROCESSCOMMAND : Error on command IDS, wrong ID of hardware");
					}
					// TODO : verify if the hardware state is correct
					// TODO : set hardware state to the driver, using newDriverState
				}
			} else if (thisCommand.equals(SerialPortCommandList.CFG)) {
				// TODO : verify if the received echo have the same parameters that have been set to the hardware
			} else if (thisCommand.equals(SerialPortCommandList.CUR)) {
				// TODO : reads the parameters of the current hardware configuration
			} else if (thisCommand.equals(SerialPortCommandList.ERR)) {
				// TODO : reads the error received from the hardware
				fireIDriverStateListenerDriverShutdown();
			}
		}
		
		
			
			
		/*	
			
			
		} else if (SerialPortCommandList.IDS.toString().equals(cmd.getCommandIdentifier())) {
			processCommandIds(cmd)
			// receives identification

			fireIDriverStateListenerDriverInited();

		} else if (SerialPortCommandList.CFG.toString().equals(cmd.getCommandIdentifier())) {
			// echo from configuration
			if (currentDriverState != DriverState.CONFIGURING) {
				Logger.getLogger(SERIAL_PORT_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverState.toString() + " and state"
								+ currentDriverState);
				return;
			}
			if (isEqualToNewConfig(cmd, new Object())) {
				// currentConfig = newConfig;
				fireIDriverStateListenerDriverConfiguring();
			} else {
				// newConfig != currentConfig;
				return;
			}
			currentDriverState = DriverState.CONFIGUREWAIT;
		} else if (SerialPortCommandList.CFGOK.toString().equals(cmd.getCommandIdentifier())) {
			// configuration is now set
			if (currentDriverState != DriverState.CONFIGUREWAIT) {
				Logger.getLogger(SERIAL_PORT_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverState + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverState = DriverState.CONFIGURED;
			fireIDriverStateListenerDriverConfigured();
		} else if (SerialPortCommandList.CUR.toString().equals(cmd.getCommandIdentifier())) {

			// receiving current configuration
			if (currentDriverState != DriverState.CONFIGURING) {
				Logger.getLogger(SERIAL_PORT_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverState + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverState = DriverState.CONFIGURING;
		} else if (SerialPortCommandList.STR.toString().equals(cmd.getCommandIdentifier())) {
			// starting
			if (currentDriverState != DriverState.STARTING) {
				Logger.getLogger(SERIAL_PORT_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverState = DriverState.STARTWAIT;
			fireIDriverStateListenerDriverStarting();
		} else if (SerialPortCommandList.STROK.toString().equals(cmd.getCommandIdentifier())) {
			// starting
			if (currentDriverState != DriverState.STARTWAIT) {
				Logger.getLogger(SERIAL_PORT_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverState = DriverState.STARTED;
			fireIDriverStateListenerDriverStarted();
		} else if (SerialPortCommandList.DAT.toString().equals(cmd.getCommandIdentifier())) {
			// will receive data
			if (currentDriverState != DriverState.STARTED) {
				Logger.getLogger(SERIAL_PORT_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.GETDATA;
			currentDriverState = DriverState.READ;
		} else if (DATA_BLOCK.equals(cmd.getCommandIdentifier())) {
			// receiving data
			if (currentDriverAction != DriverAction.IDLE && currentDriverState != DriverState.STARTED) {
				Logger.getLogger(SERIAL_PORT_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.GETTING_DATA;
			currentDriverState = DriverState.READ;
		} else if (SerialPortCommandList.END.toString().equals(cmd.getCommandIdentifier())) {
			// end of data
			if (currentDriverAction != DriverAction.GETTING_DATA && currentDriverState != DriverState.READ) {
				Logger.getLogger(SERIAL_PORT_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.IDLE;
			currentDriverState = DriverState.STARTED;
		} else if (SerialPortCommandList.BIN.toString().equals(cmd.getCommandIdentifier())) {
			// will receive binary data
			if (currentDriverAction != DriverAction.IDLE && currentDriverState != DriverState.STARTED) {
				Logger.getLogger(SERIAL_PORT_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.GETTING_BINARY;
			currentDriverState = DriverState.READ;
		}
		// internal use only
		else if (BIN_BLOCK.equals(cmd.getCommandIdentifier())) {
			// receiving binary data
			if (currentDriverAction != DriverAction.GETTING_BINARY && currentDriverState != DriverState.READ) {
				Logger.getLogger(SERIAL_PORT_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.IDLE;
			currentDriverState = DriverState.STARTED;
		} else if (SerialPortCommandList.STP.toString().equals(cmd.getCommandIdentifier())) {
			if (currentDriverAction != DriverAction.STOPPING && currentDriverState != DriverState.STARTED) {
				Logger.getLogger(SERIAL_PORT_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.STOPPING;
			currentDriverState = DriverState.WAIT;
			fireIDriverStateListenerDriverStoping();
		} else if (SerialPortCommandList.STPOK.toString().equals(cmd.getCommandIdentifier())) {
			if (currentDriverAction != DriverAction.STOPPING && currentDriverState != DriverState.WAIT) {
				Logger.getLogger(SERIAL_PORT_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.IDLE;
			currentDriverState = DriverState.STOPPED;
			fireIDriverStateListenerDriverStoping();
		} else if (SerialPortCommandList.RST.toString().equals(cmd.getCommandIdentifier())) {
			if (currentDriverAction != DriverAction.RESETTING && currentDriverState != DriverState.STARTED) {
				Logger.getLogger(SERIAL_PORT_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.IDLE;
			currentDriverState = DriverState.WAIT;
			fireIDriverStateListenerDriverReseting();
		} else if (SerialPortCommandList.RSTOK.toString().equals(cmd.getCommandIdentifier())) {
			if (currentDriverAction != DriverAction.RESETTING && currentDriverState != DriverState.WAIT) {
				Logger.getLogger(SERIAL_PORT_LOGGER).log(
						Level.INFO,
						"Command " + cmd + " was not expected while in action" + currentDriverAction + " and state"
								+ currentDriverState);
				return;
			}
			currentDriverAction = DriverAction.IDLE;
			currentDriverState = DriverState.STOPPED;
			fireIDriverStateListenerDriverReseted();
		} else if (SerialPortCommandList.ERR.toString().equals(cmd.getCommandIdentifier())) {
			Logger.getLogger(SERIAL_PORT_LOGGER).log(Level.INFO,
					"An error occured while in action" + currentDriverAction + " and state" + currentDriverState);
			currentDriverAction = DriverAction.IDLE;
			currentDriverState = DriverState.STOPPED;
			fireIDriverStateListenerDriverShutdown();
		} else {
			Logger.getLogger(SERIAL_PORT_LOGGER).log(Level.INFO, "Command " + cmd + " is unknown!");
			return;
		}
		 */

		/*
		 * if (cmd.getCommandIdentifier().equals(ID_STR)) { if (waitingStart) {
		 * waitingStart = false; writeMessage(stampConfig.getCommand());
		 * wroteStart = false; fireIDriverStateListenerDriverStarting(); } else
		 * if (stoping) { stoping = false;
		 * fireIDriverStateListenerDriverStoping(); super.stopDataSource(); }
		 * else if (reseting) { reseting = false;
		 * fireIDriverStateListenerDriverReseted(); super.stopDataSource(); }
		 * else if (started) { started = false;
		 * fireIDriverStateListenerDriverStoping();
		 * fireIDriverStateListenerDriverStoped(); super.stopDataSource(); }
		 * else if (initing) { initing = false;
		 * fireIDriverStateListenerDriverReseting();
		 * fireIDriverStateListenerDriverReseted(); } } else if
		 * (cmd.getCommandIdentifier
		 * ().equals(SerialPortStartProcessor.COMMAND_IDENTIFIER)) { started =
		 * true; config.getChannelsConfig(0).setTimeStart(new DateTime());
		 * config.setTimeStart(new DateTime());
		 * fireIDriverStateListenerDriverStarted(); /* if(dataSource!=null)
		 * dataSource.setRunning(true);
		 */
		/*
		 * } else if
		 * (cmd.getCommandIdentifier().equals(SerialPortConfiguredProcessor
		 * .COMMAND_IDENTIFIER)) { // OK to go... - still gonna receive
		 * start...! }
		 * 
		 * else if
		 * (cmd.getCommandIdentifier().equals(SerialPortNotConfiguredProcessor
		 * .COMMAND_IDENTIFIER)) { if (waitingStart && wroteStart) {
		 * waitingStart = false; fireIDriverStateListenerDriverStoped();
		 * super.stopDataSource(); } else if (started) { started = false;
		 * fireIDriverStateListenerDriverReseting();
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

		/*
		currentDriverAction = DriverAction.STARTING;
		waitingStart = true;

		WaitForConditionResult.waitForConditionTrue(new AbstractConditionDecisor() {
			public ConditionResult getConditionResult() {
				if (currentDriverAction != DriverAction.STARTING)
					return ConditionResult.CONDITION_MET_TRUE;

				return ConditionResult.CONDITION_NOT_MET;
			}
		}, 20000, 500);
		*/
	}

	public void setBase(BaseHardware baseHardware) {
		// TODO : pegar na informação do ficheiro de hardware
		baseHardware.getHardwareInfo();
	}
	
	private void logMe(String message) {
		Logger.getLogger(SERIAL_PORT_LOGGER).log(Level.INFO, message);
	}
	

}
