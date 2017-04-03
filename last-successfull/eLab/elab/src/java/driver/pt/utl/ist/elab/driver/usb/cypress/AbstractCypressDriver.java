package pt.utl.ist.elab.driver.usb.cypress;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.usb.UsbDevice;

import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressCommand;
import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressCommandListener;
import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressProcessor;
import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressTranslatorProcessorManager;

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

public abstract class AbstractCypressDriver extends BaseDriver implements CypressFinderListener, CypressCommandListener {
	private static final Logger LOGGER = Logger.getLogger(BaseCypressIO.class.getName());

	public static String START_STRING = "STARTED";
	public static String CONFIG_OUT_STRING = "cfg";
	public static String CONFIG_ACCEPTED_STRING = "CONFIG_START_ACCEPTED";
	public static String CONFIG_NOT_DONE_STRING = "CONFIG_START_NOT_DONE";
	public static String ID_STR = "EXP_Cypress_V0.1";

	public static byte IN_2_BUF = 1;
	public static byte OUT_2_BUF = 2;
	public static byte IN_4_BUF = 3;
	public static byte OUT_4_BUF = 4;
	// private final short vendorID = (short) 0x0547;
	// private final short productID = (short) 0x2131;
	// private final short interfaceNumber = (short) 0;
	// private final short alternateSetting = (short) 1;
	// private final byte inputChannelNumber = AbstractCypressDriver.IN_2_BUF;
	// private final byte outputChannelNumber = AbstractCypressDriver.OUT_2_BUF;

	private BaseCypressIO cypressIO = null;
	private CypressFinder cypressFinder = new CypressFinder();
	private final EventQueue cypressCommands;
	protected AbstractCypressDataSource dataSource = null;
	private final int timeOurPerUSB = 1000;

	public AbstractCypressDriver() {
		cypressFinder = new CypressFinder();
		cypressFinder.addCypressFinderListener(this);
		cypressCommands = new EventQueue(new CommandDispatcher(), this.getClass().getSimpleName());
	}

	public byte getInputChannelNumber() {
		return cypressFinder.getInputChannelNumber();
	}

	public void setInputChannelNumber(final byte inputChannelNumber) {
		cypressFinder.setInputChannelNumber(inputChannelNumber);
	}

	public byte getOutputChannelNumber() {
		return cypressFinder.getOutputChannelNumber();
	}

	public void setOutputChannelNumber(final byte outputChannelNumber) {
		cypressFinder.setOutputChannelNumber(outputChannelNumber);
	}

	public short getVendorID() {
		return cypressFinder.getVendorID();
	}

	public void setVendorID(final short vendorID) {
		cypressFinder.setVendorID(vendorID);
	}

	public short getProductID() {
		return cypressFinder.getProductID();
	}

	public void setProductID(final short productID) {
		cypressFinder.setProductID(productID);
	}

	public short getInterfaceNumber() {
		return cypressFinder.getInterfaceNumber();
	}

	public void setInterfaceNumber(final short interfaceNumber) {
		cypressFinder.setInterfaceNumber(interfaceNumber);
	}

	public short getAlternateSetting() {
		return cypressFinder.getAlternateSetting();
	}

	public void setAlternateSetting(final short alternateSetting) {
		cypressFinder.setAlternateSetting(alternateSetting);
	}

	public String getCypressIdentifier() {
		return cypressFinder.getCypressIdentifier();
	}

	public void setCypressIdentifier(final String CypressIdentifier) {
		cypressFinder.setCypressIdentifier(CypressIdentifier);
	}

	protected void loadCommandHandlers() {
		CypressTranslatorProcessorManager
				.initCypressProcessorTranslator("pt.utl.ist.elab.driver.usb.cypress.transproc.processors.CypressStartProcessor");
		CypressTranslatorProcessorManager
				.initCypressProcessorTranslator("pt.utl.ist.elab.driver.usb.cypress.transproc.processors.CypressConfiguredProcessor");
		CypressTranslatorProcessorManager
				.initCypressProcessorTranslator("pt.utl.ist.elab.driver.usb.cypress.transproc.processors.CypressNotConfiguredProcessor");
		CypressTranslatorProcessorManager
				.initCypressProcessorTranslator("pt.utl.ist.elab.driver.usb.cypress.transproc.processors.CypressIDProcessor");
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
		return AbstractCypressDriver.ID_STR;
	}

	public void setDriverUniqueID(final String IdStr) {
		AbstractCypressDriver.ID_STR = IdStr;
		cypressFinder.setCypressIdentifier(IdStr);
	}

	@Override
	public void init(final HardwareInfo info) {

		if (cypressIO != null) {
			cypressIO.shutdown();
		}

		cypressIO = null;
		cypressFinder.startSearch();
		try {
			WaitForConditionResult.waitForConditionTrue(new AbstractConditionDecisor() {
				@Override
				public ConditionResult getConditionResult() {
					synchronized (cypressFinder) {
						if (cypressIO != null) {
							return ConditionResult.CONDITION_MET_TRUE;
						}
					}
					return ConditionResult.CONDITION_NOT_MET;
				}
			}, 120 * 1000, timeOurPerUSB);
		} catch (final TimedOutException e) {
			LOGGER.log(Level.SEVERE, "Couldn't find port for Cypress in " + 120 + "s", e);

		}

		if (cypressIO != null) {
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
		cypressIO.reopen();
		fireIDriverStateListenerDriverReseted();
	}

	@Override
	public void shutdown() {
		if (cypressIO != null) {
			cypressIO.shutdown();
		}

		super.shutDownNow();
	}

	@Override
	public IDataSource start(final HardwareInfo info) throws IncorrectStateException {
		dataSource = initDataSource();
		dataSource.setAcquisitionHeader(getAcquisitionHeader());

		if (dataSource != null) {
			try {
				startNow();
			} catch (final TimedOutException e) {
				LOGGER.log(Level.SEVERE, "Error on start... - rethrowing IncorrectStateException!", e);
				// I'll try to reopen the Cypress...this way it il not get stuck
				// here...at least I hope so!
				fireIDriverStateListenerDriverReseting();
				cypressIO.reopen();
				fireIDriverStateListenerDriverReseted();
				throw new IncorrectStateException();
			}
			return dataSource;
		} else {
			return null;
		}

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
		cypressIO.reopen();
		fireIDriverStateListenerDriverStoped();
	}

	public void stopDataSource() {
		cypressCommands.addEvent(new StopEvent());
	}

	/** CypressFinderListener impl **/
	@Override
	public void cypressFound(final UsbDevice device) {
		synchronized (cypressFinder) {
			cypressIO = new BaseCypressIO();
			cypressIO.setInterfaceNumber(cypressFinder.getInterfaceNumber());
			cypressIO.setAlternateSetting(cypressFinder.getAlternateSetting());
			cypressIO.setInputChannelNumber(cypressFinder.getInputChannelNumber());
			cypressIO.setOutputChannelNumber(cypressFinder.getOutputChannelNumber());
			cypressIO.setProductID(cypressFinder.getProductID());
			cypressIO.setVendorID(cypressFinder.getVendorID());
			cypressIO.setCypressCommandListener(this);
			cypressIO.setDevice(device);
		}
	}

	@Override
	public void handleCypressCommand(final CypressCommand command) {
		final CypressProcessor processor = command.getProcessor();
		if (processor == null) {
			LOGGER.log(Level.INFO, "Didn't get a processor for command " + command.getCommandIdentifier());
			LOGGER.log(Level.INFO, "Droping the command, as it is not understood!");
			return;
		}

		if (processor.isData() && dataSource != null) {
			if (!processor.process(command)) {
				LOGGER.log(Level.INFO, "Couldn't process data command in CommandDispatcher... Strange...");
				return;
			}

			if (dataSource != null) {
				dataSource.processDataCommand(command);
			} else {
				LOGGER.log(Level.INFO, "No data source to process command...");
			}

			// CypressCommands.addEvent(command);
		} else {
			if (!processor.process(command)) {
				LOGGER.log(Level.INFO, "The processor didn't understand the message... Ooooppsss... Message was : "
						+ command.getCommand() + " !");
				return;
			}
			processCommand(command);
		}
	}

	public abstract AbstractCypressDataSource initDataSource();

	public abstract HardwareAcquisitionConfig getAcquisitionHeader();

	public abstract void processCommand(CypressCommand cmd);

	public class CommandDispatcher implements EventQueueDispatcher {

		@Override
		public void dispatchEvent(final Object evt) {
			if (evt instanceof StopEvent) {
				if (dataSource != null) {
					dataSource.stopNow();
				}
			} else {
				LOGGER.log(Level.INFO, "CommandDispatcher doesn't know how to deal with other than CypressCommand's");
			}
		}

		@Override
		public int getPriority() {
			return Thread.NORM_PRIORITY + 2;
		}

	}

	/**
	 * Getter for property waitForEcho.
	 * 
	 * @return Value of property waitForEcho.
	 * 
	 */
	public boolean isWaitForEcho() {
		return cypressFinder.isWaitForEcho();
	}

	/**
	 * Setter for property waitForEcho.
	 * 
	 * @param waitForEcho New value of property waitForEcho.
	 * 
	 */
	public void setWaitForEcho(final boolean waitForEcho) {
		cypressFinder.setWaitForEcho(waitForEcho);
	}

	protected void writeMessage(final String message) {
		if (cypressIO != null) {
			cypressIO.writeMessage(message);
		}
	}
}
