package pt.utl.ist.elab.driver.serial.serialportgeneric;

/**
 * 
 * ->Changed by André on 26/07/04:
 *    Added suport to Basic Atom. Now we can control RTS, DTR and echo
 */

import gnu.io.SerialPort;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.serialportgeneric.transproc.SerialPortCommand;
import pt.utl.ist.elab.driver.serial.serialportgeneric.transproc.SerialPortCommandListener;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.BaseDriver;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.threading.WaitForConditionResult;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;

public abstract class AbstractSerialPortDriver extends BaseDriver implements SerialPortFinderListener,
		SerialPortCommandListener {
	protected static String STAMP_DRIVER_LOGGER = "StampDriver.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(STAMP_DRIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(STAMP_DRIVER_LOGGER));
		}
	}

	public static String START_STRING = "STARTED";
	public static String CONFIG_OUT_STRING = "cfg";
	public static String CONFIG_ACCEPTED_STRING = "CONFIG_START_ACCEPTED";
	public static String CONFIG_NOT_DONE_STRING = "CONFIG_START_NOT_DONE";
	public static String ID_STR = "GENERIC_EXPERIENCE";

	private BaseSerialPortIO serialIO = null;
	private SerialPortFinder serialFinder = new SerialPortFinder();
	private EventQueue serialCommands;
	protected AbstractSerialPortDataSource dataSource = null;

	public AbstractSerialPortDriver() {
		serialFinder = new SerialPortFinder();
		serialFinder.addStampFinderListener(this);
		serialCommands = new EventQueue(new CommandDispatcher());
	}

	protected void loadCommandHandlers() {
		/*
		 * SerialPortTranslatorProcessorManager.initStampProcessorTranslator(
		 * "pt.utl.ist.elab.driver.serial.serialportgeneric.transproc.processors.SerialPortStartProcessor"
		 * ); SerialPortTranslatorProcessorManager
		 * .initStampProcessorTranslator(
		 * "pt.utl.ist.elab.driver.serial.serialportgeneric.transproc.processors.SerialPortConfiguredProcessor"
		 * ); SerialPortTranslatorProcessorManager
		 * .initStampProcessorTranslator(
		 * "pt.utl.ist.elab.driver.serial.serialportgeneric.transproc.processors.SerialPortNotConfiguredProcessor"
		 * ); SerialPortTranslatorProcessorManager
		 * .initStampProcessorTranslator(
		 * "pt.utl.ist.elab.driver.serial.serialportgeneric.transproc.processors.SerialPortIdProcessor"
		 * );
		 */
		loadExtraCommandHandlers();
	}

	protected abstract void loadExtraCommandHandlers();

	/** * Base Driver impl ** */
	public void extraValidateConfig(HardwareAcquisitionConfig config, HardwareInfo info)
			throws WrongConfigurationException {
		// silent noop - main validation is OK
	}

	public String getDriverUniqueID() {
		return ID_STR;
	}

	public void setDriverUniqueID(String IdStr) {
		ID_STR = IdStr;
		serialFinder.setStampIdentifier(IdStr);
	}

	public void init(HardwareInfo info) {

		this.info = info;

		if (serialIO != null)
			serialIO.shutdown();

		serialIO = null;
		serialFinder.startSearch();
		try {
			WaitForConditionResult.waitForConditionTrue(new AbstractConditionDecisor() {
				public ConditionResult getConditionResult() {
					synchronized (serialFinder) {
						if (serialIO != null) {
							return ConditionResult.CONDITION_MET_TRUE;
						}
					}
					return ConditionResult.CONDITION_NOT_MET;
				}
			}, 120 * 1000, serialFinder.getTimeOutPerPort());
		} catch (TimedOutException e) {
			LoggerUtil.logThrowable("Couldn't find port for STAMP in " + 120 + "s", e, Logger
					.getLogger(STAMP_DRIVER_LOGGER));

		}

		if (serialIO != null) {
			fireIDriverStateListenerDriverInited();
		} else
			fireIDriverStateListenerDriverShutdown();
	}

	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	public void reset(HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverReseting();
		serialIO.reopen();
		fireIDriverStateListenerDriverReseted();
	}

	public void shutdown() {
		if (serialIO != null)
			serialIO.shutdown();

		super.shutDownNow();
	}

	public IDataSource start(HardwareInfo info) throws IncorrectStateException {
		dataSource = initDataSource();
		dataSource.setAcquisitionHeader(getAcquisitionHeader());

		if (dataSource != null) {
			try {
				startNow();
			} catch (TimedOutException e) {
				LoggerUtil.logThrowable("Error on start... - rethrowing IncorrectStateException!", e, Logger
						.getLogger(STAMP_DRIVER_LOGGER));
				// I'll try to reopen the stamp...this way it il not get stuck
				// here...at least I hope so!
				fireIDriverStateListenerDriverReseting();
				serialIO.reopen();
				fireIDriverStateListenerDriverReseted();
				throw new IncorrectStateException();
			}
			return dataSource;
		} else
			return null;

	}

	public abstract void startNow() throws TimedOutException;

	public IDataSource startOutput(HardwareInfo info, IDataSource source) throws IncorrectStateException {
		// big silent noop - does nothing -
		// current version does not support startOutput
		return null;
	}

	public void stop(HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStoping();
		serialIO.reopen();
		fireIDriverStateListenerDriverStoped();
	}

	public void stopDataSource() {
		serialCommands.addEvent(new StopEvent());
	}

	/** StampFinderListener impl * */
	public void stampFound(SerialPort sPort) {
		synchronized (serialFinder) {
			serialIO = new BaseSerialPortIO();
			serialIO.setApplicationNameLockPort(serialFinder.getApplicationNameLockPort());
			serialIO.setPortBaudRate(serialFinder.getPortBaudRate());
			serialIO.setPortDataBits(serialFinder.getPortDataBits());
			serialIO.setPortParity(serialFinder.getPortParity());
			serialIO.setPortStopBits(serialFinder.getPortStopBits());
			serialIO.setDTR(serialFinder.isDTR());
			serialIO.setRTS(serialFinder.isRTS());
			serialIO.setWaitForEcho(serialFinder.isWaitForEcho());
			serialIO.setStampCommandListener(this);
			serialIO.setPort(sPort);
		}
	}

	public void handleStampCommand(SerialPortCommand command) {
		// SerialPortProcessor processor = command.getProcessor();
		/*
		 * if (processor == null) {
		 * Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO, "Didn't get a
		 * processor for command
		 * " + command.getCommandIdentifier()); Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO, "
		 * Droping the command, as it is not understood!"); return; }
		 * 
		 * if (processor.isData() && dataSource != null) { if
		 * (!processor.process(command)) {
		 * Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO, "Couldn't
		 * process data command in CommandDispatcher... Strange..."); return; }
		 */
		/*
		 * if (dataSource != null) dataSource.processDataCommand(command); else
		 * Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,
		 * "No data source to process command...");
		 */
		// stampCommands.addEvent(command);
		/*
		 * } else { if (!processor.process(command)) {
		 * Logger.getLogger(STAMP_DRIVER_LOGGER).log( Level.INFO, "The processor
		 * didn't understand the message... Ooooppsss... Message was :
		 * " + command.getCommand() + " !"); return; }
		 */

		processCommand(command);
		// }
	}

	public void handleSerialPortDataCommand(SerialPortCommand command) {
		if (dataSource != null)
			dataSource.processDataCommand(command);
	}

	public abstract AbstractSerialPortDataSource initDataSource();

	public abstract HardwareAcquisitionConfig getAcquisitionHeader();

	public abstract void processCommand(SerialPortCommand cmd);

	public class CommandDispatcher implements EventQueueDispatcher {

		public void dispatchEvent(Object evt) {
			/*
			 * if(evt instanceof StampCommand) { StampCommand
			 * cmd=(StampCommand)evt; StampProcessor
			 * processor=cmd.getProcessor(); if(processor==null ||
			 * !processor.process(cmd)) {
			 * Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,"Couldn't
			 * process data command in CommandDispatcher... Strange...");
			 * return; }
			 * 
			 * if(cmd.isData() && dataSource!=null)
			 * dataSource.processDataCommand(cmd); else
			 * Logger.getLogger(STAMP_DRIVER_LOGGER
			 * ).log(Level.INFO,"CommandDispatcher doesn't know how to deal with
			 * other than Data StampCommand's"); } else
			 */
			if (evt instanceof StopEvent) {
				if (dataSource != null)
					// dataSource.setRunning(false);
					dataSource.stopNow();
			} else {
				Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,
						"CommandDispatcher doesn't know how to deal with other than StampCommand's");
			}
		}

		public int getPriority() {
			return Thread.NORM_PRIORITY + 2;
		}

	}

	/** * Proxy for StampFinder Props ** */

	/**
	 * Getter for property applicationNameLockPort.
	 * 
	 * @return Value of property applicationNameLockPort.
	 */
	public String getApplicationNameLockPort() {
		return serialFinder.getApplicationNameLockPort();
	}

	/**
	 * Setter for property applicationNameLockPort.
	 * 
	 * @param applicationNameLockPort New value of property
	 *            applicationNameLockPort.
	 */
	public void setApplicationNameLockPort(String applicationNameLockPort) {
		serialFinder.setApplicationNameLockPort(applicationNameLockPort);
	}

	/**
	 * Getter for property portParity.
	 * 
	 * @return Value of property portParity.
	 */
	public int getPortParity() {
		return serialFinder.getPortParity();
	}

	/**
	 * Setter for property portParity.
	 * 
	 * @param portParity New value of property portParity.
	 */
	public void setPortParity(int portParity) {
		serialFinder.setPortParity(portParity);
	}

	/**
	 * Getter for property portBaudRate.
	 * 
	 * @return Value of property portBaudRate.
	 */
	public int getPortBaudRate() {
		return serialFinder.getPortBaudRate();
	}

	/**
	 * Setter for property portBaudRate.
	 * 
	 * @param portBaudRate New value of property portBaudRate.
	 */
	public void setPortBaudRate(int portBaudRate) {
		serialFinder.setPortBaudRate(portBaudRate);
	}

	/**
	 * Getter for property portDataBits.
	 * 
	 * @return Value of property portDataBits.
	 */
	public int getPortDataBits() {
		return serialFinder.getPortDataBits();
	}

	/**
	 * Setter for property portDataBits.
	 * 
	 * @param portDataBits New value of property portDataBits.
	 */
	public void setPortDataBits(int portDataBits) {
		serialFinder.setPortDataBits(portDataBits);
	}

	/**
	 * Getter for property portStopBits.
	 * 
	 * @return Value of property portStopBits.
	 */
	public int getPortStopBits() {
		return serialFinder.getPortStopBits();
	}

	/**
	 * Setter for property portStopBits.
	 * 
	 * @param portStopBits New value of property portStopBits.
	 */
	public void setPortStopBits(int portStopBits) {
		serialFinder.setPortStopBits(portStopBits);
	}

	/**
	 * Getter for property timeOutPerPort.
	 * 
	 * @return Value of property timeOutPerPort.
	 */
	public long getTimeOutPerPort() {
		return serialFinder.getTimeOutPerPort();
	}

	/**
	 * Setter for property timeOutPerPort.
	 * 
	 * @param timeOutPerPort New value of property timeOutPerPort.
	 */
	public void setTimeOutPerPort(long timeOutPerPort) {
		serialFinder.setTimeOutPerPort(timeOutPerPort);
	}

	/**
	 * Getter for property DTR.
	 * 
	 * @return Value of property DTR.
	 * 
	 */
	public boolean isDTR() {
		return serialFinder.isDTR();
	}

	/**
	 * Setter for property DTR.
	 * 
	 * @param DTR New value of property DTR.
	 * 
	 */
	public void setDTR(boolean DTR) {
		serialFinder.setDTR(DTR);
	}

	/**
	 * Getter for property RTS.
	 * 
	 * @return Value of property RTS.
	 * 
	 */
	public boolean isRTS() {
		return serialFinder.isRTS();
	}

	/**
	 * Setter for property RTS.
	 * 
	 * @param RTS New value of property RTS.
	 * 
	 */
	public void setRTS(boolean RTS) {
		serialFinder.setRTS(RTS);
	}

	/**
	 * Getter for property waitForEcho.
	 * 
	 * @return Value of property waitForEcho.
	 * 
	 */
	public boolean isWaitForEcho() {
		return serialFinder.isWaitForEcho();
	}

	/**
	 * Setter for property waitForEcho.
	 * 
	 * @param waitForEcho New value of property waitForEcho.
	 * 
	 */
	public void setWaitForEcho(boolean waitForEcho) {
		serialFinder.setWaitForEcho(waitForEcho);
	}

	protected void writeMessage(String message) {
		if (serialIO != null)
			serialIO.writeMessage(message);
	}

}
