package pt.utl.ist.elab.driver.serial.stamp;

import gnu.io.SerialPort;

import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommandListener;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampTranslatorProcessorManager;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.BaseDriver;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.threading.WaitForConditionResult;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;

public abstract class AbstractStampDriver<T extends AbstractStampDataSource> extends BaseDriver implements
		StampFinderListener, StampCommandListener {

	private static final Logger LOGGER = Logger.getLogger(AbstractStampDriver.class.getName());

	public static String START_STRING = "STARTED";
	public static String CONFIG_OUT_STRING = "cfg";
	public static String CONFIG_ACCEPTED_STRING = "CONFIG_START_ACCEPTED";
	public static String CONFIG_NOT_DONE_STRING = "CONFIG_START_NOT_DONE";
	public static String ID_STR = "EXP_STAMP_V0.1";
	private BaseStampIO stampIO = null;
	private StampFinder stampFinder = new StampFinder();
	private final EventQueue stampCommands;
	protected AbstractStampDataSource dataSource = null;

	public AbstractStampDriver() {
		stampFinder = new StampFinder();
		stampFinder.addStampFinderListener(this);
		stampCommands = new EventQueue(new CommandDispatcher(), this.getClass().getSimpleName());
	}

	protected void loadCommandHandlers() {
		StampTranslatorProcessorManager
				.initStampProcessorTranslator(pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampStartProcessor.class
						.getName());
		StampTranslatorProcessorManager
				.initStampProcessorTranslator(pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampConfiguredProcessor.class
						.getName());
		StampTranslatorProcessorManager
				.initStampProcessorTranslator(pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampNotConfiguredProcessor.class
						.getName());
		StampTranslatorProcessorManager
				.initStampProcessorTranslator(pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampIdProcessor.class
						.getName());
		loadExtraCommandHandlers();
	}

	protected abstract void loadExtraCommandHandlers();

	/*** Base Driver impl ***/
	@Override
	public void extraValidateConfig(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		// silent noop - main validation is OK
	}

	@Override
	public String getDriverUniqueID() {
		return AbstractStampDriver.ID_STR;
	}

	public void setDriverUniqueID(final String IdStr) {
		AbstractStampDriver.ID_STR = IdStr;
		stampFinder.setStampIdentifier(IdStr);
	}

	@Override
	public void init(final HardwareInfo info) {

		if (stampIO != null) {
			stampIO.shutdown();
		}

		stampIO = null;
		stampFinder.startSearch();
		try {
			WaitForConditionResult.waitForConditionTrue(new AbstractConditionDecisor() {

				@Override
				public ConditionResult getConditionResult() {
					synchronized (stampFinder) {
						if (stampIO != null) {
							return ConditionResult.CONDITION_MET_TRUE;
						}
					}
					return ConditionResult.CONDITION_NOT_MET;
				}
			}, 120 * 1000, stampFinder.getTimeOutPerPort());
		} catch (final TimedOutException e) {
			LOGGER.log(Level.SEVERE, "Couldn't find port for STAMP in " + 120 + "s", e);

		}

		if (stampIO != null) {
			fireIDriverStateListenerDriverInited();
		} else {
			fireIDriverStateListenerDriverShutdown();
		}
	}

	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	@Override
	public void reset(final HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverReseting();
		stampIO.reopen();
		fireIDriverStateListenerDriverReseted();
	}

	@Override
	public void shutdown() {
		if (stampIO != null) {
			stampIO.shutdown();
		}

		super.shutDownNow();
	}

	@Override
	public IDataSource start(final HardwareInfo info) throws IncorrectStateException {
		try {
			dataSource = initDataSource();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to instantiate DataSource class" + e.getMessage(), e);
			throw new RuntimeException(e);
		}
		dataSource.setAcquisitionHeader(getAcquisitionHeader());

		try {
			startNow();
		} catch (final TimedOutException e) {
			LOGGER.log(Level.SEVERE, "Error on start... - rethrowing IncorrectStateException!", e);
			// I'll try to reopen the stamp...this way it il not get stuck
			// here...at least I hope so!
			fireIDriverStateListenerDriverReseting();
			stampIO.reopen();
			fireIDriverStateListenerDriverReseted();
			throw new IncorrectStateException();
		}
		return dataSource;

	}

	public abstract void startNow() throws TimedOutException;

	@Override
	public IDataSource startOutput(final HardwareInfo info, final IDataSource source) throws IncorrectStateException {
		// big silent noop - does nothing -
		// current version does not support startOutput
		return null;
	}

	@Override
	public void stop(final HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStoping();
		stampIO.reopen();
		fireIDriverStateListenerDriverStoped();
	}

	public void stopDataSource() {
		stampCommands.addEvent(new StopEvent());
	}

	/** StampFinderListener impl **/
	@Override
	public void stampFound(final SerialPort sPort) {
		synchronized (stampFinder) {
			stampIO = new BaseStampIO();
			stampIO.setApplicationNameLockPort(stampFinder.getApplicationNameLockPort());
			stampIO.setPortBaudRate(stampFinder.getPortBaudRate());
			stampIO.setPortDataBits(stampFinder.getPortDataBits());
			stampIO.setPortParity(stampFinder.getPortParity());
			stampIO.setPortStopBits(stampFinder.getPortStopBits());
			stampIO.setDTR(stampFinder.isDTR());
			stampIO.setRTS(stampFinder.isRTS());
			stampIO.setWaitForEcho(stampFinder.isWaitForEcho());
			stampIO.setStampCommandListener(this);
			stampIO.setPort(sPort);
		}
	}

	@Override
	public void handleStampCommand(final StampCommand command) {
		final StampProcessor processor = command.getProcessor();
		if (processor == null) {
			LOGGER.log(Level.INFO, "Didn't get a processor for command " + command.getCommandIdentifier());
			LOGGER.log(Level.INFO, "Droping the command, as it is not understood!");
			return;
		}

		if (processor.process(command)) {
			if (processor.isData() || command.isData()) {// já a prever a
															// remoção do método
															// "isData" do
															// processor!

				if (dataSource != null) {
					dataSource.processDataCommand(command);
				} else {
					LOGGER.log(Level.INFO, "No data source to process command...");
				}

			} else {
				processCommand(command);
			}
		} else {
			LOGGER.log(Level.INFO, "The processor didn't understand the message... Ooooppsss... Message was : "
					+ command.getCommand() + " !");
		}

	}

	public T initDataSource() throws InstantiationException, IllegalAccessException {

		Class<?> superClass = this.getClass();
		ParameterizedType genericSuperType = null;

		while (superClass != Object.class && genericSuperType == null) {
			if (superClass.getGenericSuperclass() instanceof ParameterizedType) {
				genericSuperType = (ParameterizedType) superClass.getGenericSuperclass();
			} else {
				superClass = superClass.getSuperclass();
			}
		}

		while (superClass != Object.class && genericSuperType != null) {
			if (genericSuperType.getActualTypeArguments() != null
					&& genericSuperType.getActualTypeArguments().length > 0) {
				@SuppressWarnings("unchecked")
				Class<T> parameterClass = (Class<T>) genericSuperType.getActualTypeArguments()[0];
				return parameterClass.newInstance();
			} else {
				genericSuperType = null;
				while (superClass != Object.class && genericSuperType == null) {
					if (superClass.getGenericSuperclass() instanceof ParameterizedType) {
						genericSuperType = (ParameterizedType) superClass.getGenericSuperclass();
					} else {
						superClass = superClass.getSuperclass();
					}
				}
			}
		}

		throw new InstantiationException("Unable to find generic super class of datasource for driver "
				+ getClass().getName());
	}

	public abstract HardwareAcquisitionConfig getAcquisitionHeader();

	public abstract void processCommand(StampCommand cmd);

	public class CommandDispatcher implements EventQueueDispatcher {

		@Override
		public void dispatchEvent(final Object evt) {
			/*
			 * if(evt instanceof StampCommand) { StampCommand
			 * cmd=(StampCommand)evt; StampProcessor
			 * processor=cmd.getProcessor(); if(processor==null ||
			 * !processor.process(cmd)) {
			 * Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,
			 * "Couldn't process data command in CommandDispatcher... Strange..."
			 * ); return; }
			 * 
			 * if(cmd.isData() && dataSource!=null)
			 * dataSource.processDataCommand(cmd); else
			 * Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,
			 * "CommandDispatcher doesn't know how to deal with other than Data StampCommand's"
			 * ); } else
			 */
			if (evt instanceof StopEvent) {
				if (dataSource != null) {
					// dataSource.setRunning(false);
					dataSource.stopNow();
				}
			} else {
				LOGGER.log(Level.INFO, "CommandDispatcher doesn't know how to deal with other than StampCommand's");
			}
		}

		@Override
		public int getPriority() {
			return Thread.NORM_PRIORITY + 2;
		}
	}

	/*** Proxy for StampFinder Props ***/
	/**
	 * Getter for property applicationNameLockPort.
	 * 
	 * @return Value of property applicationNameLockPort.
	 */
	public String getApplicationNameLockPort() {
		return stampFinder.getApplicationNameLockPort();
	}

	/**
	 * Setter for property applicationNameLockPort.
	 * 
	 * @param applicationNameLockPort New value of property
	 *            applicationNameLockPort.
	 */
	public void setApplicationNameLockPort(final String applicationNameLockPort) {
		stampFinder.setApplicationNameLockPort(applicationNameLockPort);
	}

	/**
	 * Getter for property portParity.
	 * 
	 * @return Value of property portParity.
	 */
	public int getPortParity() {
		return stampFinder.getPortParity();
	}

	/**
	 * Setter for property portParity.
	 * 
	 * @param portParity New value of property portParity.
	 */
	public void setPortParity(final int portParity) {
		stampFinder.setPortParity(portParity);
	}

	/**
	 * Getter for property portBaudRate.
	 * 
	 * @return Value of property portBaudRate.
	 */
	public int getPortBaudRate() {
		return stampFinder.getPortBaudRate();
	}

	/**
	 * Setter for property portBaudRate.
	 * 
	 * @param portBaudRate New value of property portBaudRate.
	 */
	public void setPortBaudRate(final int portBaudRate) {
		stampFinder.setPortBaudRate(portBaudRate);
	}

	/**
	 * Getter for property portDataBits.
	 * 
	 * @return Value of property portDataBits.
	 */
	public int getPortDataBits() {
		return stampFinder.getPortDataBits();
	}

	/**
	 * Setter for property portDataBits.
	 * 
	 * @param portDataBits New value of property portDataBits.
	 */
	public void setPortDataBits(final int portDataBits) {
		stampFinder.setPortDataBits(portDataBits);
	}

	/**
	 * Getter for property portStopBits.
	 * 
	 * @return Value of property portStopBits.
	 */
	public int getPortStopBits() {
		return stampFinder.getPortStopBits();
	}

	/**
	 * Setter for property portStopBits.
	 * 
	 * @param portStopBits New value of property portStopBits.
	 */
	public void setPortStopBits(final int portStopBits) {
		stampFinder.setPortStopBits(portStopBits);
	}

	/**
	 * Getter for property timeOutPerPort.
	 * 
	 * @return Value of property timeOutPerPort.
	 */
	public long getTimeOutPerPort() {
		return stampFinder.getTimeOutPerPort();
	}

	/**
	 * Setter for property timeOutPerPort.
	 * 
	 * @param timeOutPerPort New value of property timeOutPerPort.
	 */
	public void setTimeOutPerPort(final long timeOutPerPort) {
		stampFinder.setTimeOutPerPort(timeOutPerPort);
	}

	/**
	 * Getter for property DTR.
	 * 
	 * @return Value of property DTR.
	 * 
	 */
	public boolean isDTR() {
		return stampFinder.isDTR();
	}

	/**
	 * Setter for property DTR.
	 * 
	 * @param DTR New value of property DTR.
	 * 
	 */
	public void setDTR(final boolean DTR) {
		stampFinder.setDTR(DTR);
	}

	/**
	 * Getter for property RTS.
	 * 
	 * @return Value of property RTS.
	 * 
	 */
	public boolean isRTS() {
		return stampFinder.isRTS();
	}

	/**
	 * Setter for property RTS.
	 * 
	 * @param RTS New value of property RTS.
	 * 
	 */
	public void setRTS(final boolean RTS) {
		stampFinder.setRTS(RTS);
	}

	/**
	 * Getter for property waitForEcho.
	 * 
	 * @return Value of property waitForEcho.
	 * 
	 */
	public boolean isWaitForEcho() {
		return stampFinder.isWaitForEcho();
	}

	/**
	 * Setter for property waitForEcho.
	 * 
	 * @param waitForEcho New value of property waitForEcho.
	 * 
	 */
	public void setWaitForEcho(final boolean waitForEcho) {
		stampFinder.setWaitForEcho(waitForEcho);
	}

	protected void writeMessage(final String message) {
		if (stampIO != null) {
			stampIO.writeMessage(message);
		}
	}

}