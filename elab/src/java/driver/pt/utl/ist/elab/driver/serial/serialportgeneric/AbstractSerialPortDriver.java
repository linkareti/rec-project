package pt.utl.ist.elab.driver.serial.serialportgeneric;

/**
 * 
 * ->Changed by André on 26/07/04:
 *    Added suport to Basic Atom. Now we can control RTS, DTR and echo
 * 
 * ->Changed by fdias on 19/10/2009
 *    Now everything is inside this abstract class. Every method and argument,
 *    every piece of code and business. Now... it's up to you to decide if you
 *    want a generic code like this, or want an evolution... extending your class
 *    from this class. 
 */

import gnu.io.SerialPort;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
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
import pt.utl.ist.elab.driver.serial.serialportgeneric.genericexperiment.GenericSerialPortDataSource;
import pt.utl.ist.elab.driver.serial.serialportgeneric.translator.SerialPortTranslator;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.ChannelParameter;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.metadata.ParameterType;
import com.linkare.rec.impl.driver.BaseDriver;
import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.threading.WaitForConditionResult;
import com.linkare.rec.impl.utils.Defaults;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;

public abstract class AbstractSerialPortDriver extends BaseDriver implements SerialPortFinderListener,
		SerialPortCommandListener {

	protected static String SERIAL_PORT_LOGGER = "SerialPortDriver.Logger";
	protected static final String hardwareConfigFile = "HardwareConfig.xml";
	protected static BaseHardware baseHardware = null;
	protected static int currentBinaryLength = 0;
	protected static int totalBinaryLength = 0;
	
	private static final String RS232_CONFIG_FILE_PATH = Defaults.defaultIfEmpty(System
			.getProperty("ReC.Driver.RS232_CONFIG_FILE_PATH"), "hardwareserver/etc/Rs232Config.xml");

	static {
		Logger l = LogManager.getLogManager().getLogger(SERIAL_PORT_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(SERIAL_PORT_LOGGER));
		}
	}

	protected String className = null;
	protected String packageName = null;
	protected String ID_STR = null;
	protected AbstractSerialPortDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	private BaseSerialPortIO serialIO = null;
	private SerialPortFinder serialFinder = null;
	private EventQueue serialCommands = null;
	private SerialPortCommand serialPortCommand = null;
	private String rememberLastWrittenMessage = null;

	public static HardwareNode rs232configs = null;

	public static DriverState currentDriverState = DriverState.UNKNOWN;

	/**
	 * 
	 * Once upon a time, there was a great explosion, and between electrons,
	 * protons, and lots of hydrogen atoms running to achieve an energetic
	 * stability, there, was born the constructor of this driver. Then,
	 * everything of right and wrong have started to happen between software and
	 * hardware...
	 * 
	 * @author fdias
	 */
	public AbstractSerialPortDriver() {
		
		Logger.getLogger(SERIAL_PORT_LOGGER).log(Level.FINE, "Instantiating the " + this.getClass().getSimpleName());

		try {
			rs232configs = loadRs232Configs(RS232_CONFIG_FILE_PATH);
			Logger.getLogger(SERIAL_PORT_LOGGER).log(Level.FINE, "Loaded the RS232 configuration.");
		} catch (IncorrectRs232ValuesException e) {
			logMe("SERIAL PORT DRIVER CONSTRUCTOR : Incorrect values on rs232 config file" + e.getMessage());
			return;
		} catch (IOException e) {
			logMe("SERIAL PORT DRIVER CONSTRUCTOR : Error reading rs232 config file" + e.getMessage());
			return;
		}

		className = this.getClass().getName().replaceFirst("StampDriver", "").replaceFirst(".class", "");
		packageName = getClass().getPackage().getName() + ".";

		ID_STR = rs232configs.getId();
		logMe("Driver Unique ID = " + ID_STR);

		loadCommandHandlers();

		Logger.getLogger(SERIAL_PORT_LOGGER).log(Level.FINE, "Creating the serial finder.");
		serialFinder = new SerialPortFinder();

		setApplicationNameLockPort(rs232configs.getId()); // TODO shoud be a new atribute in the xml node
		setDriverUniqueID(rs232configs.getId());
		setTimeOutPerPort(rs232configs.getRs232().getTimeout().getPortListen().getTimeInt());
		setPortBaudRate(rs232configs.getRs232().getBaud().intValue());
		setPortStopBits(rs232configs.getRs232().getStopbits().intValue());
		setPortDataBits(rs232configs.getRs232().getNumbits().intValue());
		setPortParity(rs232configs.getRs232().getParitybits().intValue());

		serialFinder.addStampFinderListener(this);
		
		Logger.getLogger(SERIAL_PORT_LOGGER).log(Level.FINE, "Creating the EventQueue for the serial commands.");
		serialCommands = new EventQueue(new CommandDispatcher(), this.getClass().getSimpleName());
	}

	/**
	 * 
	 * One day, men started to print lines on the clay to represent pounds of
	 * wheat. Accounting was born. Then they started to print figures as a
	 * representation of an idea. Logic writing was born. Then they put each
	 * vocal sound in a symbol and called them letters. Linguistics was born.
	 * Then they put two symbols surrounding each word, one < and one >. They
	 * said XML was born. I think they reused the same idea of the egyptian
	 * writing, but as they couldn't write capsules, they wrote these two
	 * symbols that nobody types. What if I could use this XML to represent
	 * structured ideas of relationships between objects? Then JAXB was born...
	 * 
	 * @return HardwareNode
	 * @throws IncorrectRs232ValuesException
	 * @author fdias
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private HardwareNode loadRs232Configs(String file) throws IncorrectRs232ValuesException, IOException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("pt.utl.ist.elab.driver.serial.serialportgeneric.config");
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<HardwareNode> jaxbElement = (JAXBElement<HardwareNode>) unmarshaller.unmarshal(new File(file));
			return (HardwareNode) jaxbElement.getValue();
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new IncorrectRs232ValuesException("Error \"" + e.getMessage()
					+ "\" on reading Rs232 definitions file: " + new File(file).getCanonicalPath());
		}
	}

	/**
	 * 
	 * Loads the information from HardwareInfo.xml
	 * 
	 * @author fdias
	 * @return {@link Object}
	 * 
	 */
	public Object getHardwareInfo() {
		// TODO : define the url to hardware info xml
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

	/**
	 * - Please, do this work for me. - Okay, I will ask a friend of mine to do
	 * it for me. - Ah, thanks, what is his name? - He is the
	 * loadExtraCommandHandlers. A cool and hard working guy. - And what are you
	 * going to do? - Give him the work. I think there's no problem, I return
	 * void anyway...
	 * 
	 * @author fdias
	 * 
	 */
	protected void loadCommandHandlers() {
		loadExtraCommandHandlers();
	}

	/**
	 * 
	 * Why the art tends to be so abstract? Well... is this class only one
	 * method is abstract. This is not abstract, nor impressionist, neither
	 * cubist. This is only a method that avoids this class to be the generic
	 * driver. - Why? This is not meant to be a generic driver? - Yes it is.
	 * But, everything is not so generic as you think it is...<br />
	 * <br />
	 * If you don't have code to put inside this method, just give a good name
	 * to your class and leave it empty.
	 * 
	 * @author fdias
	 * 
	 */
	protected abstract void loadExtraCommandHandlers();

	/**
	 * 
	 * Validates the configuration of the parameters using the HardwareInfo.xml
	 * data. Leave it blank if you don't need this.
	 * 
	 */
	public abstract void extraValidateConfig(HardwareAcquisitionConfig config, HardwareInfo info)
			throws WrongConfigurationException;

	/**
	 * 
	 * In front of the tree the ReC asks to the cat at the top:<br />
	 * - Who are you?<br />
	 * - Hello little girl. I am who I am, why you care about it?<br />
	 * - But you are a pink cat? What's your name?<br />
	 * - I'm the Cheshire cat.<br />
	 * - Impossible, I was expecting for a driver!<br />
	 * - What's the problem? You're neither a girl Alice! You're a framework!<br />
	 * - And this is not Wonderland?<br />
	 * - No... this is eLab.<br />
	 * 
	 * @author fdias
	 * 
	 */
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
	public void setDriverUniqueID(String IdStr) {
		ID_STR = IdStr;
		serialFinder.setStampIdentifier(IdStr);
	}

	/**
	 * 
	 * At the beginning there was nothing. So YHVH created the Earth and the
	 * skies around. The earth was without colour and void. So YHVH separated
	 * the light from the darkness, and called day to the light, and night to
	 * the darkness. And there was the beginning...
	 * 
	 * After that, the driver spoke through the serial port - let it be light.
	 * And there was light in all the LEDs of an experience set.
	 * 
	 * @param HardwareInfo
	 * @author fdias
	 * 
	 */
	public void init(HardwareInfo info) {
		Logger.getLogger(SERIAL_PORT_LOGGER).log(Level.FINE, "Initializing driver");
		this.info = info;
		
		if (serialIO != null) {
			serialIO.shutdown();
		}

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
			LoggerUtil.logThrowable("Couldn't find port for serial in 120 s", e, Logger.getLogger(SERIAL_PORT_LOGGER));
		}

		currentDriverState = DriverState.UNKNOWN;
		currentDriverState.startTimeoutClock();

		if (serialIO != null) {
			fireIDriverStateListenerDriverInited();
		} else {
			fireIDriverStateListenerDriverShutdown();
		}

		Logger.getLogger(SERIAL_PORT_LOGGER).log(Level.FINE, "Driver initialized");
	}

	/**
	 * 
	 * For 20 minutes the telescreen repeats over and over the same images and
	 * sounds and Winston Smith enters in a trance of delusion and hate. The Big
	 * Brother is configuring him for perpetual war and freedom. But, meanwhile,
	 * a thought of Julia appears on his mind.<br />
	 * And all that trance disappeared again. There is no more doublethink on
	 * his mind, but the eyes of Julia under that tree on the fields outside of
	 * London<br />
	 * A new configuration, a new way to this experience react. Beware the
	 * MiniTrue.
	 * 
	 * @throws IncorrectStateException
	 * @throws WrongConfigurationException
	 * @throws TimedOutException
	 * @author fdias
	 * 
	 */
	public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException,
			IncorrectStateException, TimedOutException {

		currentDriverState.explodeOnTimeout();

		// verifies if the driver can configure the hardware at this moment
		// through the current state
		if (currentDriverState != DriverState.CONFIGURED && currentDriverState != DriverState.STOPPED
				&& currentDriverState != DriverState.RESETED && currentDriverState != DriverState.UNKNOWN) {
			logMe("CONFIGURE : Cannot configure while on state " + currentDriverState.toString());
			throw new IncorrectStateException();
		}

		fireIDriverStateListenerDriverConfiguring();

		serialPortCommand = new SerialPortCommand(SerialPortCommandList.CFG.toString().toLowerCase());

		// loop through each parameter and add data to each one
		List<OneParameterNode> commandParameterNodes = new ArrayList<OneParameterNode>();
		for (int i = 1; i < rs232configs.getRs232().getParameters().getParameter().size(); i++) {
			// parameter from rs232
			OneParameterNode parameterNone = rs232configs.getRs232().getParameters().getParameterToOrder(i);
			// parameter from hardwareinfo
			ChannelParameter channelParameter = info.getHardwareParameters(i);
			// if is legible value...
			if (channelParameter.getParameterType() != ParameterType.BlackBoxValue) {
				String parameterName = channelParameter.getParameterName();
				String parameterValue = Defaults.defaultIfEmpty(
						config.getSelectedHardwareParameterValue(parameterName), info
								.getHardwareParameterValue(parameterName));
				// add to command
				serialPortCommand.addCommandData(parameterNone.getOrder().toString(), parameterValue);
				commandParameterNodes.add(parameterNone);
			} else {
				// TODO : send a blackbox value on cfg
			}
		}
		
		if (!SerialPortTranslator.translateConfig(serialPortCommand, commandParameterNodes)) {
			throw new WrongConfigurationException("Cannot translate config command!", -1);
		}
		
		writeMessage(serialPortCommand.getCommand());
		currentDriverState = DriverState.CONFIGURING;
		currentDriverState.startTimeoutClock();
		fireIDriverStateListenerDriverConfigured();
	}

	/**
	 * 
	 * Resets the driver
	 * 
	 * @throws {@link IncorrectStateException}
	 * @throws {@link TimedOutException}
	 * @author fdias
	 * 
	 */
	public void reset(HardwareInfo info) throws IncorrectStateException {

		currentDriverState.explodeOnTimeout();

		fireIDriverStateListenerDriverReseting();
		serialPortCommand = new SerialPortCommand(SerialPortCommandList.RST.toString().toLowerCase());
		SerialPortTranslator.translateReset(serialPortCommand);
		writeMessage(serialPortCommand.getCommand());
		currentDriverState = DriverState.RESETING;
		currentDriverState.startTimeoutClock();
		serialIO.reopen();
		fireIDriverStateListenerDriverReseted();
	}

	/**
	 * 
	 * Shuts down the driver
	 * 
	 * @author fdias
	 * 
	 */
	public void shutdown() {
		if (serialIO != null)
			serialIO.shutdown();
		currentDriverState = DriverState.UNKNOWN;
		currentDriverState.startTimeoutClock();
		fireIDriverStateListenerDriverShutdown();
		// super.shutDownNow();
	}

	/**
	 * 
	 * Starts the driver
	 * 
	 * @throws IncorrectStateException
	 * @author fdias
	 * 
	 */
	public IDataSource start(HardwareInfo info) throws IncorrectStateException {

		currentDriverState.explodeOnTimeout();

		// verifies if the driver can start the hardware at this moment through
		// the current state
		if (currentDriverState != DriverState.CONFIGURED && currentDriverState != DriverState.STOPPED) {
			logMe("START : Cannot start while on state " + currentDriverState.toString());
			throw new IncorrectStateException();
		}

		fireIDriverStateListenerDriverStarting();
		dataSource = initDataSource();
		dataSource.setRs232configs(rs232configs);
		dataSource.setAcquisitionHeader(getAcquisitionHeader());

		if (dataSource != null) {
			try {
				startNow();
			} catch (IncorrectStateException e) {
				LoggerUtil.logThrowable("Error on datasource start. Throwing IncorrectStateException!", e, Logger
						.getLogger(SERIAL_PORT_LOGGER));
				currentDriverState = DriverState.UNKNOWN;
				currentDriverState.startTimeoutClock();
				fireIDriverStateListenerDriverReseting();
				serialIO.reopen(); // why reopen? this is not a stop...
				fireIDriverStateListenerDriverReseted();
				throw new IncorrectStateException();
			}
			fireIDriverStateListenerDriverStarted();
			return dataSource;
		} else
			return null;
	}

	public void startNow() throws IncorrectStateException {

		currentDriverState.explodeOnTimeout();

		if (serialPortCommand == null) {
			logMe("No configuration available yet!");
			throw new IncorrectStateException();
		}
		serialPortCommand = new SerialPortCommand(SerialPortCommandList.STR.toString().toLowerCase());
		SerialPortTranslator.translateStart(serialPortCommand);
		writeMessage(serialPortCommand.getCommand());
		currentDriverState = DriverState.STARTING;
		currentDriverState.startTimeoutClock();
	}

	public IDataSource startOutput(HardwareInfo info, IDataSource source) throws IncorrectStateException {
		// NOP
		return null;
	}

	/**
	 * 
	 * Stops the driver
	 * 
	 * @throws IncorrectStateException
	 * @author fdias
	 * 
	 */
	public void stop(HardwareInfo info) throws IncorrectStateException {

		currentDriverState.explodeOnTimeout();

		// verifies if the driver can stop the hardware at this moment through
		// the current state
		if (currentDriverState != DriverState.STARTED && currentDriverState != DriverState.RECEIVINGDATA
				&& currentDriverState != DriverState.RECEIVINGBIN) {
			logMe("STOP : Cannot stop while on state " + currentDriverState.toString());
			throw new IncorrectStateException();
		}

		fireIDriverStateListenerDriverStoping();
		serialPortCommand = new SerialPortCommand(SerialPortCommandList.STP.toString().toLowerCase());
		SerialPortTranslator.translateStop(serialPortCommand);
		serialIO.reopen();
		fireIDriverStateListenerDriverStoped();
	}

	public void stopDataSource() {
		serialCommands.addEvent(new StopEvent());
	}

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

	/**
	 * 
	 * Only for maintaining compatibility with older versions of ReC.
	 * 
	 * @deprecated Use <code><b>processCommand</b></code> method instead
	 * @throws IncorrectStateException
	 * @throws TimedOutException
	 * @author fdias
	 * 
	 */
	@Deprecated
	public void handleStampCommand(SerialPortCommand command) throws IncorrectStateException, TimedOutException {
		logMe("Received a command " + command.getCommandIdentifier() + " !");
		processCommand(command);
	}

	public void handleSerialPortDataCommand(SerialPortCommand command) {
		if (dataSource != null)
			dataSource.processDataCommand(command);
	}

	/**
	 * 
	 * Initiates a GenericSerialPortDataSource as datasource for this driver. If
	 * you want to use an specific datasource, extend it from this class and
	 * override this method to create an instance of your datasource.
	 * 
	 * @return AbstractSerialPortDataSource
	 * @author fdias
	 * 
	 */
	public AbstractSerialPortDataSource initDataSource() {
		AbstractSerialPortDataSource dataSource = new GenericSerialPortDataSource();
		dataSource.setAcquisitionHeader(getAcquisitionHeader());
		return dataSource;
	}

	public HardwareAcquisitionConfig getAcquisitionHeader() {
		return config;
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
	 * @author fdias
	 * 
	 */
	public void processCommand(SerialPortCommand cmd) throws IncorrectStateException, TimedOutException {
		logMe("Going to process the command " + cmd);

		// is this time to
		currentDriverState.explodeOnTimeout();

		DriverState newDriverState = null;
		SerialPortCommandList thisCommand = null;

		// if the command is null, forget about it
		if (cmd == null || cmd.getCommandIdentifier() == null || baseHardware == null) {
			logMe("PROCESSCOMMAND : Cannot interpret command "
					+ (cmd != null && cmd.getCommandIdentifier() != null ? cmd.getCommandIdentifier() : "\"null\""));
			return;
		}
		
		logMe("Processing the serial port command with ID [" + cmd.getCommandIdentifier() + "] and command [" + cmd.getCommand() + "]");

		// if the hardware is sending data to the driver, OR
		// if the hardware speaks a unknown language, forget about it
		if (!SerialPortCommandList.exists(cmd.getCommandIdentifier())) {

			if (currentDriverState.equals(DriverState.RECEIVINGDATA)) {
				dataSource.processDataCommand(cmd);
				return;
			} else if (currentDriverState.equals(DriverState.RECEIVINGBIN)) {
				dataSource.processBinaryCommand(cmd);
				return;
			}
			// the driver seems to speak Fortran 77, I cannot understand it
			logMe("PROCESSCOMMAND : Cannot interpret command " + cmd.getCommandIdentifier());
			// terminates this driver execution
			currentDriverState = DriverState.UNKNOWN;
			currentDriverState.startTimeoutClock();
			fireIDriverStateListenerDriverShutdown();
			return;
		}
		logMe("PROCESSCOMMAND : I'm gonna process the line : " + cmd.getCommand());

		// but if we understand it, let's listen to it
		thisCommand = SerialPortCommandList.valueOf(cmd.getCommandIdentifier());

		// the next state according to the message of the driver
		newDriverState = currentDriverState.nextState(thisCommand);
		if (newDriverState != currentDriverState) {
			currentDriverState.startTimeoutClock();
		}

		// process received data or states
		if (currentDriverState.processeMe(thisCommand)) {
			if (thisCommand.equals(SerialPortCommandList.IDS)) {
				if (cmd.getDataHashMap() == null || cmd.getDataHashMap().size() != 2) {
					// Houston we have a problem, IDS always comes with two
					// parameters
					logMe("PROCESSCOMMAND : Error on command IDS, incorrect number of parameters: "
							+ cmd.getDataHashMap() == null ? "null" : cmd.getDataHashMap().size()
							+ " parameters instead of 2");
					currentDriverState = DriverState.UNKNOWN;
					currentDriverState.startTimeoutClock();
					// terminates this driver execution
					fireIDriverStateListenerDriverShutdown();
					return;
				} else {
					if (!rs232configs.getId().equals(cmd.getDataHashMap().get(1))) {
						logMe("PROCESSCOMMAND : Error on command IDS, wrong ID of hardware: "
								+ (cmd.getDataHashMap().size() > 1 ? cmd.getDataHashMap().get(1) : "null"));
						currentDriverState = DriverState.UNKNOWN;
						currentDriverState.startTimeoutClock();
						// terminates this driver execution
						fireIDriverStateListenerDriverShutdown();
						return;
					}
					if (!HardwareStatus.isValid(cmd.getDataHashMap().get(2))) {
						logMe("PROCESSCOMMAND : Error on command IDS, wrong status of hardware:"
								+ (cmd.getDataHashMap().size() > 2 ? cmd.getDataHashMap().get(2) : "null"));
						currentDriverState = DriverState.UNKNOWN;
						currentDriverState.startTimeoutClock();
						// terminates this driver execution
						fireIDriverStateListenerDriverShutdown();
						return;
					}
					if (!currentDriverState.acceptHardwareStatus(HardwareStatus.valueOf(cmd.getDataHashMap().get(2)))) {
						logMe("PROCESSCOMMAND : Current driver state: " + currentDriverState.toString()
								+ " does not matches hardware status:"
								+ (cmd.getDataHashMap().size() > 2 ? cmd.getDataHashMap().get(2) : "null")
								+ ". Shuting down driver.");
						currentDriverState = DriverState.UNKNOWN;
						currentDriverState.startTimeoutClock();
						// terminates this driver execution
						fireIDriverStateListenerDriverShutdown();
						return;
					}
				} // is an IDS
				// else ...
			} else if (thisCommand.equals(SerialPortCommandList.CFG)) {
				if (SerialPortCommand.isResponse(cmd.getCommand(), rememberLastWrittenMessage))
					currentDriverState = DriverState.CONFIGUREWAIT;
				currentDriverState.startTimeoutClock();
			} else {
				logMe("PROCESSCOMMAND : Configuration recieved from the hardware does not match: " + cmd.getCommand());
				currentDriverState = DriverState.UNKNOWN;
				currentDriverState.startTimeoutClock();
				fireIDriverStateListenerDriverShutdown();
				return;
			}
		} else if (thisCommand.equals(SerialPortCommandList.CUR)) {
			// TODO : what must I do with this?
		} else if (thisCommand.equals(SerialPortCommandList.ERR)) {
			logMe("PROCESSCOMMAND : Recieved error from the hardware: " + cmd.getCommand());
			fireIDriverStateListenerDriverShutdown();
			throw new IncorrectStateException();
		}
		// new state for the driver
		logMe("PROCESSCOMMAND : Switching between driver state " + currentDriverState.toString() + " and "
				+ newDriverState.toString());
		currentDriverState = newDriverState;
		currentDriverState.startTimeoutClock();
	}

	public class CommandDispatcher implements EventQueueDispatcher {

		public void dispatchEvent(Object evt) {
			if (evt instanceof StopEvent) {
				if (dataSource != null)
					// dataSource.setRunning(false);
					dataSource.stopNow();
			} else {
				logMe("CommandDispatcher only messes with StampCommand objects");
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
		if (serialIO != null) {
			rememberLastWrittenMessage = message;
			serialIO.writeMessage(message);
		}
	}

	protected void logMe(String message) {
		Logger.getLogger(SERIAL_PORT_LOGGER).log(Level.INFO, message);
	}

}
