package pt.utl.ist.elab.driver.serial.serialportgeneric;

import gnu.io.SerialPort;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommand;
import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommandList;
import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommandListener;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.HardwareNode;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.OneParameterNode;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.OneParameterNode.TransferFunctionType;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.TimeoutNode;
import pt.utl.ist.elab.driver.serial.serialportgeneric.genericexperiment.GenericSerialPortDataSource;
import pt.utl.ist.elab.driver.serial.serialportgeneric.translator.SerialPortTranslator;

import com.linkare.net.protocols.Protocols;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.ChannelAcquisitionConfig;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.ChannelParameter;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.metadata.ParameterType;
import com.linkare.rec.impl.driver.BaseDriver;
import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.threading.WaitForConditionResult;
import com.linkare.rec.impl.utils.Defaults;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;

/**
 * 
 * - >Changed by André on 26/07/04: Added suport to Basic Atom. Now we can
 * control RTS, DTR and echo
 * 
 * ->Changed by fdias on 19/10/2009 Now everything is inside this abstract
 * class. Every method and argument, every piece of code and business. Now...
 * it's up to you to decide if you want a generic code like this, or want an
 * evolution... extending your class from this class.
 */
public abstract class AbstractSerialPortDriver extends BaseDriver implements SerialPortFinderListener,
		SerialPortCommandListener, ICommandTimeoutListener {

	protected static BaseHardware baseHardware = null;
	protected static int currentBinaryLength = 0;
	protected static int totalBinaryLength = 0;

	private static final String RS232_CONFIG_FILE_PATH = Defaults.defaultIfEmpty(
			System.getProperty("rec.driver.rs232_config_file_path"), "hardwareserver/etc/Rs232Config.xml");

	private static final Logger LOGGER = Logger.getLogger(AbstractSerialPortDriver.class.getName());

	protected String className = null;
	protected String packageName = null;
	protected String ID_STR = null;
	protected AbstractSerialPortDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	private BaseSerialPortIO serialIO = null;
	private SerialPortFinder serialFinder = null;
	private EventQueue serialCommands = null;
	private String rememberLastWrittenMessage = null;

	public static HardwareNode rs232configs = null;
	private CommandTimeoutChecker commandTimeoutChecker = null;
	private Object synch = null;

	private DriverState currentDriverState = DriverState.UNKNOWN;
	private boolean resetNoData = false;

	public AbstractSerialPortDriver() {

		LOGGER.log(Level.INFO, "Instantiating the " + this.getClass().getSimpleName());

		try {
			AbstractSerialPortDriver.rs232configs = loadRs232Configs(AbstractSerialPortDriver.RS232_CONFIG_FILE_PATH);
			LOGGER.log(Level.FINE, "Loaded the RS232 configuration.");
		} catch (final IncorrectRs232ValuesException e) {
			LOGGER.log(Level.SEVERE, "Incorrect values on rs232 config file" + e.getMessage());
			throw new RuntimeException(e);
		} catch (final IOException e) {
			LOGGER.log(Level.SEVERE, "Error reading rs232 config file" + e.getMessage());
			throw new RuntimeException(e);
		}

		className = this.getClass().getName().replaceFirst("StampDriver", "").replaceFirst(".class", "");
		packageName = getClass().getPackage().getName() + ".";

		ID_STR = AbstractSerialPortDriver.rs232configs.getId();
		LOGGER.log(Level.INFO, "Driver Unique ID = " + ID_STR);

		loadCommandHandlers();

		LOGGER.log(Level.FINE, "Creating the serial finder.");
		serialFinder = new SerialPortFinder(this);

		setApplicationNameLockPort(AbstractSerialPortDriver.rs232configs.getId()); // TODO
																					// shoud
																					// be
																					// a
																					// new
		// atribute in the
		// xml node
		setDriverUniqueID(AbstractSerialPortDriver.rs232configs.getId());
		setTimeOutPerPort(AbstractSerialPortDriver.rs232configs.getRs232().getTimeout().getPortListen().getTimeInt());
		setPortBaudRate(AbstractSerialPortDriver.rs232configs.getRs232().getBaud().intValue());
		setPortStopBits(AbstractSerialPortDriver.rs232configs.getRs232().getStopbits().intValue());
		setPortDataBits(AbstractSerialPortDriver.rs232configs.getRs232().getNumbits().intValue());
		setPortParity(AbstractSerialPortDriver.rs232configs.getRs232().getParitybits().intValue());

		serialFinder.addStampFinderListener(this);

		LOGGER.log(Level.FINE, "Creating the EventQueue for the serial commands.");
		serialCommands = new EventQueue(new CommandDispatcher(), this.getClass().getSimpleName());

		final TimeoutNode timeoutNode = AbstractSerialPortDriver.rs232configs.getRs232().getTimeout();
		commandTimeoutChecker = new CommandTimeoutChecker(this, timeoutNode);
		synch = commandTimeoutChecker;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void commandTimeout(final SerialPortCommand command) {
		LOGGER.log(Level.FINE, "Received notification for command timeout [" + command + "]");
		// timeout business logic
		if (command != null) {
			if (!command.getCommandIdentifier().equalsIgnoreCase(SerialPortCommandList.RST.toString())) {
				writeResetCommand();
			} else {
				// reset was attemped but failed!
				shutdown();
			}
		} else {
			LOGGER.log(Level.SEVERE, "Command is null. Something umpredicted has happened at timmeout checker!");
		}
	}

	private void writeResetCommand() {
		LOGGER.log(Level.INFO, "Going to send a reset command.");

		serialIO.resetLastOutputMessage();
		synchronized (synch) {
			currentDriverState = DriverState.RESETING;
			fireIDriverStateListenerDriverReseting();
		}
		final SerialPortCommand serialPortCommand = new SerialPortCommand(SerialPortCommandList.RST.toString()
				.toLowerCase());
		SerialPortTranslator.translate(serialPortCommand);
		writeMessage(serialPortCommand.getCommand());
		commandTimeoutChecker.checkCommand(serialPortCommand);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void commandTimeoutNoData() {
		LOGGER.log(Level.FINE, "Received notification for no data.");
		// timeout business logic
		writeResetCommand();
	}

	/**
	 * 
	 * @return HardwareNode
	 * @throws IncorrectRs232ValuesException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private HardwareNode loadRs232Configs(final String file) throws IncorrectRs232ValuesException, IOException {
		try {
			final JAXBContext jaxbContext = JAXBContext
					.newInstance("pt.utl.ist.elab.driver.serial.serialportgeneric.config");
			final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			final JAXBElement<HardwareNode> jaxbElement = (JAXBElement<HardwareNode>) unmarshaller.unmarshal(new File(
					file));
			return jaxbElement.getValue();
		} catch (final JAXBException e) {
			e.printStackTrace();
			throw new IncorrectRs232ValuesException("Error \"" + e.getMessage()
					+ "\" on reading Rs232 definitions file: " + new File(file).getCanonicalPath());
		}
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
			url = Protocols.getURL(prop);
		} catch (final java.net.MalformedURLException e) {
			LOGGER.log(Level.SEVERE, "Unable to load resource: " + prop, e);
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (final java.net.MalformedURLException e2) {
				LOGGER.log(Level.SEVERE, "Unable to load resource: " + baseHardwareInfoFile, e2);
			}
		}

		return url;
	}

	protected void loadCommandHandlers() {
		loadExtraCommandHandlers();
	}

	protected abstract void loadExtraCommandHandlers();

	/**
	 * 
	 * Validates the configuration of the parameters using the HardwareInfo.xml
	 * data. Leave it blank if you don't need this.
	 * 
	 */
	@Override
	public abstract void extraValidateConfig(HardwareAcquisitionConfig config, HardwareInfo info)
			throws WrongConfigurationException;

	@Override
	public String getDriverUniqueID() {
		return ID_STR;
	}

	/**
	 * 
	 * Sets the ID of the hardware to this driver.
	 * 
	 * @param IdStr
	 * 
	 */
	public void setDriverUniqueID(final String IdStr) {
		ID_STR = IdStr;
		serialFinder.setStampIdentifier(IdStr);
	}

	@Override
	public void init(final HardwareInfo info) {
		LOGGER.log(Level.INFO, "Initializing driver");

		if (serialIO != null) {
			serialIO.shutdown();
		}

		serialIO = null;
		serialFinder.startSearch();

		LOGGER.log(Level.FINE, "Waiting for serial IO to be instantiated.");
		try {
			WaitForConditionResult.waitForConditionTrue(new AbstractConditionDecisor() {
				@Override
				public ConditionResult getConditionResult() {
					synchronized (serialFinder) {
						if (serialIO != null) {
							return ConditionResult.CONDITION_MET_TRUE;
						}
					}
					return ConditionResult.CONDITION_NOT_MET;
				}
			}, 120 * 1000, serialFinder.getTimeOutPerPort());
		} catch (final TimedOutException e) {
			LOGGER.log(Level.SEVERE, "Couldn't find port for serial in 120 s", e);
		}
		LOGGER.log(Level.FINE, "The wait has ended with serial IO = " + serialIO);

		synchronized (synch) {
			currentDriverState = DriverState.UNKNOWN;
		}

		if (serialIO != null) {
			fireIDriverStateListenerDriverInited();
		} else {
			fireIDriverStateListenerDriverShutdown();
		}

		LOGGER.log(Level.FINE, "Driver initialized");
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException, IncorrectStateException, TimedOutException {
		LOGGER.log(Level.INFO, "Configuring driver...");

		// verifies if the driver can configure the hardware at this moment
		// through the current state
		synchronized (synch) {
			if (!isDriverInState(DriverState.CONFIGURED) && !isDriverInState(DriverState.STOPPED)
					&& !isDriverInState(DriverState.RESETED) && !isDriverInState(DriverState.UNKNOWN)) {
				LOGGER.log(Level.WARNING, "Cannot configure while on state " + currentDriverState.toString());
				throw new IncorrectStateException();
			}

			currentDriverState = DriverState.CONFIGURING;
			fireIDriverStateListenerDriverConfiguring();
		}

		LOGGER.log(Level.FINE, "Creating command.");

		final SerialPortCommand serialPortCommand = new SerialPortCommand(SerialPortCommandList.CFG.toString()
				.toLowerCase());

		// loop through each parameter and add data to each one
		final List<OneParameterNode> commandParameterNodes = new ArrayList<OneParameterNode>();
		for (int i = 0; i < AbstractSerialPortDriver.rs232configs.getRs232().getParameters().getParameter().size(); i++) {
			// parameter from rs232
			final OneParameterNode parameterNone = AbstractSerialPortDriver.rs232configs.getRs232().getParameters()
					.getParameterToOrder(i);
			// parameter from hardwareinfo
			final ChannelParameter channelParameter = info.getHardwareParameters(i);
			// if is legible value...
			if (channelParameter.getParameterType() != ParameterType.BlackBoxValue) {
				final String parameterName = channelParameter.getParameterName();
				final String parameterValueInput = Defaults.defaultIfEmpty(
						config.getSelectedHardwareParameterValue(parameterName),
						info.getHardwareParameterValue(parameterName));

				// transform the data from client to the hardware config format
				final Double value = parameterNone.calculate(Double.valueOf(parameterValueInput),
						TransferFunctionType.INPUT);
				final String parameterValue = parameterNone.formatInput(value);

				// add to command
				serialPortCommand.addCommandData(parameterNone.getOrder().toString(), parameterValue);
				commandParameterNodes.add(parameterNone);
			} else {
				// TODO : send a blackbox value on cfg
			}
		}

		if (!SerialPortTranslator.translateConfig(serialPortCommand, commandParameterNodes)) {
			LOGGER.log(Level.WARNING, "Error translating command.");
			throw new WrongConfigurationException("Cannot translate config command!", -1);
		}

		LOGGER.log(Level.INFO, "Created command [" + serialPortCommand.getCommand() + "]");

		final ChannelAcquisitionConfig[] channelsConfig = config.getChannelsConfig();
		if (channelsConfig != null) {
			LOGGER.log(Level.FINE, "Setting the channel acquisition total samples with [" + config.getTotalSamples()
					+ "]");
			for (final ChannelAcquisitionConfig channelAcquisitionConfig : channelsConfig) {
				channelAcquisitionConfig.setTotalSamples(config.getTotalSamples());
			}
		} else {
			LOGGER.log(Level.FINE, "There aren't defined channels config.");
		}

		this.config = config;

		writeMessage(serialPortCommand.getCommand());
		commandTimeoutChecker.checkCommand(serialPortCommand);
	}

	/**
	 * 
	 * Resets the driver
	 * 
	 * @throws {@link IncorrectStateException}
	 * @throws {@link TimedOutException}
	 * 
	 */
	@Override
	public void reset(final HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverReseting();
		final SerialPortCommand serialPortCommand = new SerialPortCommand(SerialPortCommandList.RST.toString()
				.toLowerCase());
		SerialPortTranslator.translate(serialPortCommand);
		writeMessage(serialPortCommand.getCommand());
		commandTimeoutChecker.checkCommand(serialPortCommand);
		synchronized (synch) {
			currentDriverState = DriverState.RESETING;
			fireIDriverStateListenerDriverReseted();
		}
		serialIO.reopen();
	}

	/**
	 * 
	 * Shuts down the driver
	 * 
	 */
	@Override
	public void shutdown() {
		LOGGER.log(Level.SEVERE, "Shutdown was invoked! Going to terminate...");

		if (serialIO != null) {
			serialIO.shutdown();
		}
		synchronized (synch) {
			currentDriverState = DriverState.UNKNOWN;
			fireIDriverStateListenerDriverShutdown();
		}
		// super.shutDownNow();
	}

	/**
	 * 
	 * Starts the driver
	 * 
	 * @throws IncorrectStateException
	 * 
	 */
	@Override
	public IDataSource start(final HardwareInfo info) throws IncorrectStateException {

		LOGGER.log(Level.INFO, "Starting with the hardware info [" + info + "]");

		// verifies if the driver can start the hardware at this moment through
		// the current state
		if (!isDriverInState(DriverState.CONFIGURED) && !isDriverInState(DriverState.STOPPED)) {
			LOGGER.log(Level.WARNING, "Cannot start while on state " + currentDriverState.toString());
			throw new IncorrectStateException();
		}

		dataSource = initDataSource();
		dataSource.setRs232configs(AbstractSerialPortDriver.rs232configs);
		dataSource.setAcquisitionHeader(getAcquisitionHeader());

		if (dataSource != null) {
			startNow();
			return dataSource;
		} else {
			return null;
		}
	}

	private void startNow() {

		synchronized (synch) {
			currentDriverState = DriverState.STARTING;
			fireIDriverStateListenerDriverStarting();
		}

		final SerialPortCommand serialPortCommand = new SerialPortCommand(SerialPortCommandList.STR.toString()
				.toLowerCase());
		SerialPortTranslator.translate(serialPortCommand);
		writeMessage(serialPortCommand.getCommand());
		commandTimeoutChecker.checkCommand(serialPortCommand);
	}

	@Override
	public IDataSource startOutput(final HardwareInfo info, final IDataSource source) throws IncorrectStateException {
		// NOP
		return null;
	}

	/**
	 * 
	 * Stops the driver
	 * 
	 * @throws IncorrectStateException
	 * 
	 */
	@Override
	public void stop(final HardwareInfo info) throws IncorrectStateException {
		LOGGER.log(Level.INFO, "Stopping hardware.");

		// verifies if the driver can stop the hardware at this moment through
		// the current state
		if (!isDriverInState(DriverState.STARTED) && !isDriverInState(DriverState.RECEIVINGDATA)
				&& !isDriverInState(DriverState.RECEIVINGBIN)) {
			LOGGER.log(Level.WARNING, "Cannot stop while on state " + currentDriverState.toString());
			throw new IncorrectStateException();
		}

		fireIDriverStateListenerDriverStoping();
		final SerialPortCommand serialPortCommand = new SerialPortCommand(SerialPortCommandList.STP.toString()
				.toLowerCase());
		SerialPortTranslator.translate(serialPortCommand);
		writeMessage(serialPortCommand.getCommand());
		commandTimeoutChecker.checkCommand(serialPortCommand);
		serialIO.reopen();
		fireIDriverStateListenerDriverStoped();
	}

	public void stopDataSource() {
		serialCommands.addEvent(new StopEvent());
	}

	@Override
	public void stampFound(final SerialPort sPort) {
		synchronized (serialFinder) {
			serialIO = new BaseSerialPortIO(this);
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

	/**
	 * 
	 * Only for maintaining compatibility with older versions of rec.
	 * 
	 * @deprecated Use <code><b>processCommand</b></code> method instead
	 * @throws IncorrectStateException
	 * @throws TimedOutException
	 * 
	 */
	@Override
	@Deprecated
	public void handleStampCommand(final SerialPortCommand command) throws IncorrectStateException, TimedOutException {
		processCommand(command);
	}

	public void handleSerialPortDataCommand(final SerialPortCommand command) {
		if (dataSource != null) {
			dataSource.processDataCommand(command);
		}
	}

	/**
	 * 
	 * Initiates a GenericSerialPortDataSource as datasource for this driver. If
	 * you want to use an specific datasource, extend it from this class and
	 * override this method to create an instance of your datasource.
	 * 
	 * @return AbstractSerialPortDataSource
	 */
	public AbstractSerialPortDataSource initDataSource() {
		final AbstractSerialPortDataSource dataSource = new GenericSerialPortDataSource();
		dataSource.setAcquisitionHeader(getAcquisitionHeader());
		return dataSource;
	}

	public HardwareAcquisitionConfig getAcquisitionHeader() {
		return config;
	}

	private static SerialPortCommand createTransformedDataCommand(final SerialPortCommand command) {
		if (command == null || command.getCommandIdentifier() == null || command.getCommand() == null) {
			return null;
		}
		final SerialPortCommand cmd = new SerialPortCommand(SerialPortCommandList.DAT.toString());
		cmd.setData(true);
		cmd.setCommand(command.getCommandIdentifier().concat("	").concat(command.getCommand()));
		return cmd;
	}

	/**
	 * 
	 * This method processes the commands received from the hardware, tries to
	 * understand them and fire the driver states. Any data command are relayed
	 * to processDataCommand.
	 * 
	 * @param cmd
	 * @throws TimedOutException
	 * @throws IncorrectStateException
	 * 
	 */
	public void processCommand(SerialPortCommand cmd) throws IncorrectStateException, TimedOutException {
		synchronized (synch) {
			LOGGER.log(Level.INFO, "Going to process the command " + cmd + " with the driver in state "
					+ currentDriverState);

			SerialPortCommandList thisCommand = null;

			if (cmd == null || cmd.getCommandIdentifier() == null) {
				LOGGER.log(Level.INFO, "PROCESSCOMMAND : Cannot interpret command " + cmd);
				return;
			}

			// if the hardware is sending data to the driver, OR
			// if the hardware speaks a unknown language, forget about it
			if (!SerialPortCommandList.exists(cmd.getCommandIdentifier())) {

				if (currentDriverState.equals(DriverState.RECEIVINGDATA)) {
					if (resetNoData) {
						resetNoData = false;
						// only do reset on changed state
						commandTimeoutChecker.reset();

						commandTimeoutChecker.checkCommand(new SerialPortCommand(SerialPortCommandList.END.toString()
								.toLowerCase()));
					}
					// FIXME hack! martelada! it shouln't be necessary to
					// transform but BaseSerialPort doesn't now...
					cmd = AbstractSerialPortDriver.createTransformedDataCommand(cmd);
					LOGGER.log(Level.FINEST, "Going to process the transformed data command [" + cmd + "]");
					dataSource.processDataCommand(cmd);
					return;
				} else if (currentDriverState.equals(DriverState.RECEIVINGBIN)) {
					// TODO BIN are not expected yet
					dataSource.processBinaryCommand(cmd);
					return;
				}
				// the driver seems to speak Fortran 77, I cannot understand it
				LOGGER.log(Level.INFO,
						"PROCESSCOMMAND : Cannot interpret command identifier " + cmd.getCommandIdentifier());
				// terminates this driver execution
				currentDriverState = DriverState.UNKNOWN;
				fireIDriverStateListenerDriverShutdown();
				return;
			}

			// but if we understand it, let's listen to it
			thisCommand = SerialPortCommandList.valueOf(cmd.getCommandIdentifier());
			SerialPortCommand writeStopCommand = null;

			// process received data or states
			if (currentDriverState.processeMe(thisCommand)) {
				if (thisCommand.equals(SerialPortCommandList.IDS)) {
					if (cmd.getDataHashMap() == null || cmd.getDataHashMap().size() != 2) {
						// Houston we have a problem, IDS always comes with two
						// parameters
						LOGGER.log(
								Level.WARNING,
								"Error on command IDS, incorrect number of parameters: " + cmd.getDataHashMap() == null ? "null"
										: cmd.getDataHashMap().size() + " parameters instead of 2");
						currentDriverState = DriverState.UNKNOWN;
						// terminates this driver execution
						fireIDriverStateListenerDriverShutdown();
						return;
					} else {
						if (!AbstractSerialPortDriver.rs232configs.getId().equals(cmd.getDataHashMap().get(0))) {
							LOGGER.log(Level.WARNING, "Error on command IDS, wrong ID of hardware: "
									+ (cmd.getDataHashMap().size() > 0 ? cmd.getDataHashMap().get(0) : "null"));
							currentDriverState = DriverState.UNKNOWN;
							// terminates this driver execution
							fireIDriverStateListenerDriverShutdown();
							return;
						}
						if (!HardwareStatus.isValid(cmd.getDataHashMap().get(1))) {
							LOGGER.log(Level.WARNING, "Error on command IDS, wrong status of hardware:"
									+ (cmd.getDataHashMap().size() > 1 ? cmd.getDataHashMap().get(1) : "null"));
							currentDriverState = DriverState.UNKNOWN;
							// terminates this driver execution
							fireIDriverStateListenerDriverShutdown();
							return;
						}
						if (!currentDriverState.acceptHardwareStatus(HardwareStatus
								.valueOf(cmd.getDataHashMap().get(1)))) {
							LOGGER.log(Level.WARNING, "Current driver state: " + currentDriverState.toString()
									+ " does not matches hardware status:"
									+ (cmd.getDataHashMap().size() == 2 ? cmd.getDataHashMap().get(1) : "null")
									+ ". Shuting down driver.");
							currentDriverState = DriverState.UNKNOWN;
							// terminates this driver execution
							fireIDriverStateListenerDriverShutdown();
							return;
						}
					} // is an IDS
						// else ...
				} else if (thisCommand.equals(SerialPortCommandList.CFG)) {
					// is this used???
					if (SerialPortCommand.isResponse(cmd.getCommand(), rememberLastWrittenMessage)) {
						currentDriverState = DriverState.CONFIGUREWAIT;
					}
				} else if (thisCommand.equals(SerialPortCommandList.CFGOK)
						|| thisCommand.equals(SerialPortCommandList.STROK)
						|| thisCommand.equals(SerialPortCommandList.STPOK)
						|| thisCommand.equals(SerialPortCommandList.RSTOK)) {
					// valid commands
					commandTimeoutChecker.reset();
				} else if (thisCommand.equals(SerialPortCommandList.DAT)
						|| thisCommand.equals(SerialPortCommandList.BIN)) {
					// valid commands
					commandTimeoutChecker.reset();
					// TODO confirm is there is an awake synch problem?
					commandTimeoutChecker.checkNoData();
					resetNoData = true;
				} else if (thisCommand.equals(SerialPortCommandList.END)) {
					// valid command
					commandTimeoutChecker.reset();

					// send stp command
					writeStopCommand = new SerialPortCommand(SerialPortCommandList.STP.toString().toLowerCase());
					SerialPortTranslator.translate(writeStopCommand);
				} else {
					LOGGER.log(Level.FINE,
							"Configuration recieved from the hardware does not match: " + cmd.getCommand());
					currentDriverState = DriverState.UNKNOWN;
					fireIDriverStateListenerDriverShutdown();
					return;
				}
			} else if (thisCommand.equals(SerialPortCommandList.CUR)) {
				// TODO : what must I do with this?
			} else if (thisCommand.equals(SerialPortCommandList.ERR)) {
				LOGGER.log(Level.INFO, "Recieved error from the hardware: " + cmd.getCommand());
				fireIDriverStateListenerDriverShutdown();
				throw new IncorrectStateException();
			}

			// the next state according to the message of the driver
			final DriverState newDriverState = currentDriverState.nextState(thisCommand, cmd);
			if (newDriverState != currentDriverState) {
				// new state for the driver
				LOGGER.log(Level.FINEST, "Switching between driver state " + currentDriverState + " and "
						+ newDriverState);
				currentDriverState = newDriverState;

				if (currentDriverState == DriverState.RESETED) {
					fireIDriverStateListenerDriverReseted();
				} else if (currentDriverState == DriverState.STOPPED) {
					fireIDriverStateListenerDriverStoped();
				} else if (currentDriverState == DriverState.CONFIGURED) {
					fireIDriverStateListenerDriverConfigured();
				} else if (currentDriverState == DriverState.STARTED) {
					fireIDriverStateListenerDriverStarted();
				}
			}

			// is it to send a stop command
			if (writeStopCommand != null && currentDriverState != DriverState.STOPPED
					&& currentDriverState != DriverState.STOPING) {
				writeMessage(writeStopCommand.getCommand());
				commandTimeoutChecker.checkCommand(writeStopCommand);
				currentDriverState = DriverState.STOPING;
				fireIDriverStateListenerDriverStoping();
			}
		}
	}

	public class CommandDispatcher implements EventQueueDispatcher {

		@Override
		public void dispatchEvent(final Object evt) {
			if (evt instanceof StopEvent) {
				if (dataSource != null) {
					// dataSource.setRunning(false);
					dataSource.stopNow();
				}
			} else {
				LOGGER.log(Level.FINE, "CommandDispatcher only messes with StampCommand objects");
			}
		}

		@Override
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
	public void setApplicationNameLockPort(final String applicationNameLockPort) {
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
	public void setPortParity(final int portParity) {
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
	public void setPortBaudRate(final int portBaudRate) {
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
	public void setPortDataBits(final int portDataBits) {
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
	public void setPortStopBits(final int portStopBits) {
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
	public void setTimeOutPerPort(final long timeOutPerPort) {
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
	public void setDTR(final boolean DTR) {
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
	public void setRTS(final boolean RTS) {
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
	public void setWaitForEcho(final boolean waitForEcho) {
		serialFinder.setWaitForEcho(waitForEcho);
	}

	protected void writeMessage(final String message) {
		if (serialIO != null) {
			rememberLastWrittenMessage = message;
			serialIO.writeMessage(message);
		}
	}

	public boolean isDriverInState(final DriverState state) {
		return currentDriverState == state;
	}


}
