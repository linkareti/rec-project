package pt.utl.ist.elab.driver.serial.serialportgeneric;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommand;
import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommandList;
import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommandListener;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.HardwareNode;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class SerialPortFinder {

	private static final Logger LOGGER = Logger.getLogger(SerialPortFinder.class.getName());

	/** Holds value of property applicationNameLockPort. */
	private String applicationNameLockPort = "SerialPortFinder App Lock";

	/** Holds value of property stampIdentifier. */
	private String serialIdentifier = "STAMP_ID";

	/** Holds value of property portParity. */
	private int portParity = SerialPort.PARITY_NONE;

	/** Holds value of property portBaudRate. */
	private int portBaudRate = 9600;

	/** Holds value of property portDataBits. */
	private int portDataBits = SerialPort.DATABITS_8;

	/** Holds value of property portStopBits. */
	private int portStopBits = SerialPort.STOPBITS_2;

	/** Utility field used by event firing mechanism. */
	private javax.swing.event.EventListenerList listenerList = null;

	/** Holds value of property DTR. */
	private boolean DTR = false;

	/** Holds value of property RTS. */
	private boolean RTS = false;

	/** Holds value of property waitForEcho. */
	private boolean waitForEcho = true;

	private CommPortIdentifier[] ports = null;
	private SerialPortFinderRunner runner = null;

	/** Holds value of property timeOutPerPort. */
	private long timeOutPerPort = 1000;

	private final AbstractSerialPortDriver driver;

	/**
	 * Creates a new instance of StampFinder
	 * 
	 * @param driver
	 */
	public SerialPortFinder(final AbstractSerialPortDriver driver) {
		this.driver = driver;
	}

	/**
	 * Getter for property applicationNameLockPort.
	 * 
	 * @return Value of property applicationNameLockPort.
	 */
	public String getApplicationNameLockPort() {
		return applicationNameLockPort;
	}

	/**
	 * Setter for property applicationNameLockPort.
	 * 
	 * @param applicationNameLockPort New value of property
	 *            applicationNameLockPort.
	 */
	public void setApplicationNameLockPort(final String applicationNameLockPort) {
		this.applicationNameLockPort = applicationNameLockPort;
	}

	/**
	 * Getter for property stampIdentifier.
	 * 
	 * @return Value of property stampIdentifier.
	 */
	public String getStampIdentifier() {
		return serialIdentifier;
	}

	/**
	 * Setter for property stampIdentifier.
	 * 
	 * @param stampIdentifier New value of property stampIdentifier.
	 */
	public void setStampIdentifier(final String stampIdentifier) {
		serialIdentifier = stampIdentifier;
	}

	/**
	 * Getter for property portParity.
	 * 
	 * @return Value of property portParity.
	 */
	public int getPortParity() {
		return portParity;
	}

	/**
	 * Setter for property portParity.
	 * 
	 * @param portParity New value of property portParity.
	 */
	public void setPortParity(final int portParity) {
		this.portParity = portParity;
	}

	/**
	 * Getter for property portBaudRate.
	 * 
	 * @return Value of property portBaudRate.
	 */
	public int getPortBaudRate() {
		return portBaudRate;
	}

	/**
	 * Setter for property portBaudRate.
	 * 
	 * @param portBaudRate New value of property portBaudRate.
	 */
	public void setPortBaudRate(final int portBaudRate) {
		this.portBaudRate = portBaudRate;
	}

	/**
	 * Getter for property portDataBits.
	 * 
	 * @return Value of property portDataBits.
	 */
	public int getPortDataBits() {
		return portDataBits;
	}

	/**
	 * Setter for property portDataBits.
	 * 
	 * @param portDataBits New value of property portDataBits.
	 */
	public void setPortDataBits(final int portDataBits) {
		this.portDataBits = portDataBits;
	}

	/**
	 * Getter for property portStopBits.
	 * 
	 * @return Value of property portStopBits.
	 */
	public int getPortStopBits() {
		return portStopBits;
	}

	/**
	 * Setter for property portStopBits.
	 * 
	 * @param portStopBits New value of property portStopBits.
	 */
	public void setPortStopBits(final int portStopBits) {
		this.portStopBits = portStopBits;
	}

	/**
	 * Getter for property DTR.
	 * 
	 * @return Value of property DTR.
	 * 
	 */
	public boolean isDTR() {
		return DTR;
	}

	/**
	 * Setter for property DTR.
	 * 
	 * @param DTR New value of property DTR.
	 * 
	 */
	public void setDTR(final boolean DTR) {
		this.DTR = DTR;
	}

	/**
	 * Getter for property RTS.
	 * 
	 * @return Value of property RTS.
	 * 
	 */
	public boolean isRTS() {
		return RTS;
	}

	/**
	 * Setter for property RTS.
	 * 
	 * @param RTS New value of property RTS.
	 * 
	 */
	public void setRTS(final boolean RTS) {
		this.RTS = RTS;
	}

	/**
	 * Getter for property waitForEcho.
	 * 
	 * @return Value of property waitForEcho.
	 * 
	 */
	public boolean isWaitForEcho() {
		return waitForEcho;
	}

	/**
	 * Setter for property waitForEcho.
	 * 
	 * @param waitForEcho New value of property waitForEcho.
	 * 
	 */
	public void setWaitForEcho(final boolean waitForEcho) {
		this.waitForEcho = waitForEcho;
	}

	public void stopSearch() {
		if (runner != null) {
			runner.stopNow();
		}

		runner = null;
		ports = null;
	}

	@SuppressWarnings("unchecked")
	public void startSearch() {
		LOGGER.log(Level.INFO, "Starting the search for the hardware serial port.");

		final Enumeration<CommPortIdentifier> commPortIdentifiers = gnu.io.CommPortIdentifier.getPortIdentifiers();
		final LinkedList<CommPortIdentifier> tempPorts = new LinkedList<CommPortIdentifier>();
		final Set<String> configuredPorts = new HashSet<String>();

		LOGGER.log(Level.INFO, "Are there COMM Ports on the System? " + commPortIdentifiers.hasMoreElements());

		if (AbstractSerialPortDriver.rs232configs != null) {
			if (AbstractSerialPortDriver.rs232configs.getRs232().getPortsRestrict() != null
					&& AbstractSerialPortDriver.rs232configs.getRs232().getPortsRestrict().trim().length() != 0) {
				final String[] ports = AbstractSerialPortDriver.rs232configs.getRs232().getPortsRestrict().split(",");
				LOGGER.log(Level.FINE, "RS232 xml configured ports = " + Arrays.deepToString(ports));
				configuredPorts.addAll(Arrays.asList(ports));
			} else {
				while (commPortIdentifiers.hasMoreElements()) {
					CommPortIdentifier identifier = (CommPortIdentifier) commPortIdentifiers.nextElement();
					configuredPorts.add(identifier.getName());
				}
			}

		}

		// Lists all the ports and filters included on rs232 configuration file
		while (commPortIdentifiers.hasMoreElements()) {
			final CommPortIdentifier identifier = commPortIdentifiers.nextElement();
			LOGGER.log(
					Level.FINE,
					"CommPortIdentifier Name = " + identifier.getName() + " is it Serial = "
							+ String.valueOf(identifier.getPortType() == CommPortIdentifier.PORT_SERIAL));
			if (identifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (configuredPorts.contains(identifier.getName())) {
					tempPorts.add(identifier);
				}
			}
		}
		if(tempPorts.size()==0) {
			while (commPortIdentifiers.hasMoreElements()) {
				CommPortIdentifier identifier = (CommPortIdentifier) commPortIdentifiers.nextElement();
				tempPorts.add(identifier);
			}
		}
		

		ports = new CommPortIdentifier[tempPorts.size()];
		final Object[] portsObj = tempPorts.toArray();

		LOGGER.log(Level.INFO, "There are " + portsObj.length + " Serial COMM Ports in the system...");

		System.arraycopy(portsObj, 0, ports, 0, ports.length);

		LOGGER.log(Level.INFO, "Serial COMM Ports in the system are:");
		for (int i = 0; i < ports.length; i++) {
			LOGGER.log(Level.INFO, "Port[" + i + "]=" + ports[i].getName());
		}

		if (ports.length > 0) {
			runner = new SerialPortFinderRunner();
			runner.setPriority(Thread.NORM_PRIORITY + 2);
			runner.start();
		} else {
			LOGGER.log(Level.SEVERE, "There are none of the configured serial ports on the System!");
		}

	}

	/**
	 * Registers StampFinderListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	public synchronized void addStampFinderListener(final SerialPortFinderListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(SerialPortFinderListener.class, listener);
	}

	/**
	 * Removes StampFinderListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	public synchronized void removeStampFinderListener(final SerialPortFinderListener listener) {
		listenerList.remove(pt.utl.ist.elab.driver.serial.serialportgeneric.SerialPortFinderListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 */
	private void fireStampFinderListenerStampFound(final SerialPort event) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == pt.utl.ist.elab.driver.serial.serialportgeneric.SerialPortFinderListener.class) {
				((SerialPortFinderListener) listeners[i + 1]).stampFound(event);
			}
		}
	}

	/**
	 * Getter for property timeOutPerPort.
	 * 
	 * @return Value of property timeOutPerPort.
	 */
	public long getTimeOutPerPort() {
		return timeOutPerPort;
	}

	/**
	 * Setter for property timeOutPerPort.
	 * 
	 * @param timeOutPerPort New value of property timeOutPerPort.
	 */
	public void setTimeOutPerPort(final long timeOutPerPort) {
		this.timeOutPerPort = timeOutPerPort;
	}

	private class SerialPortFinderRunner extends Thread {

		private boolean exit = false;
		private final Thread current = null;
		private int currentPort = 0;
		private BaseSerialPortIO stampIO = null;
		private SerialPort currentPortOpen = null;
		// private BufferedReader currentReader=null;
		private final SerialPortFinderRunnerPortListener serialPortFinderRunnerEventListener = new SerialPortFinderRunnerPortListener();

		public void stopNow() {
			exit = true;
			if (current != null) {
				try {
					current.join();
				} catch (final InterruptedException e) {
					LOGGER.log(Level.SEVERE, null, e);
				}
			}
		}

		@Override
		public void run() {
			while (!exit) {
				LOGGER.log(Level.INFO, "Cycling port...");
				cyclePort();
			}
			LOGGER.log(Level.INFO, "Serial port finder thread ended.");
		}

		public void cyclePort() {
			if (ports == null || ports.length == 0) {
				LOGGER.log(Level.INFO, "There are no ports!");
				return;
			}

			if (currentPort >= ports.length) {
				currentPort = 0;
				LOGGER.log(Level.FINE, "Restarting from the first port!");
			}

			final CommPortIdentifier cpi = ports[currentPort];

			if (cpi.isCurrentlyOwned()) {
				LOGGER.log(Level.INFO, "Serial Port " + cpi.getName() + " is currently owned by another application - "
						+ cpi.getCurrentOwner());
				currentPort++;
				return;
			}

			LOGGER.log(Level.INFO, "Serial Port " + cpi.getName() + " is not Owned - Opening & Locking It! ");

			try {
				LOGGER.log(Level.FINE, "Port IO configurations = ApplicationNameLockPort ["
						+ getApplicationNameLockPort() + "] BaudRate [" + getPortBaudRate() + "] DataBits ["
						+ getPortDataBits() + "] Parity [" + getPortParity() + "] StopBits [" + getPortStopBits()
						+ "] DTR [" + isDTR() + "] RST [" + isRTS() + "] WaitForEcho [" + isWaitForEcho() + "]");

				currentPortOpen = (SerialPort) cpi.open(applicationNameLockPort, 100);
				stampIO = new BaseSerialPortIO(driver);
				stampIO.setApplicationNameLockPort(getApplicationNameLockPort());
				stampIO.setPortBaudRate(getPortBaudRate());
				stampIO.setPortDataBits(getPortDataBits());
				stampIO.setPortParity(getPortParity());
				stampIO.setPortStopBits(getPortStopBits());
				stampIO.setDTR(isDTR());
				stampIO.setRTS(isRTS());
				stampIO.setWaitForEcho(isWaitForEcho());
				stampIO.setStampCommandListener(serialPortFinderRunnerEventListener);
				stampIO.setPort(currentPortOpen);

			} catch (final gnu.io.PortInUseException e) {
				LOGGER.log(Level.SEVERE, "Serial port " + cpi.getName() + " is currently in use...", e);
				currentPort++;
				return;
			}

			try {
				LOGGER.log(Level.INFO, "Sleeping for " + (timeOutPerPort > 0 ? timeOutPerPort : 100) + " ms for port "
						+ cpi.getName() + " data events");
				synchronized (this) {
					wait(timeOutPerPort > 0 ? timeOutPerPort : 100);
					LOGGER.log(Level.INFO, "Ended Sleeping for " + (timeOutPerPort > 0 ? timeOutPerPort : 100)
							+ " ms for port " + cpi.getName() + " data events");
				}

			} catch (final InterruptedException e) {
				LOGGER.log(Level.SEVERE, "Error waiting for SerialPort identification!", e);
				return;
			}

			if (!portFound) {
				LOGGER.log(Level.INFO, "Port was not found!");
				synchronized (serialPortFinderRunnerEventListener) {
					LOGGER.log(Level.INFO, "NO id string detected on port " + cpi.getName());
					stampIO.shutdown();
					LOGGER.log(Level.INFO, "Shuted down stampIO!");
				}
			}

			currentPort++;
		}

		private boolean portFound = false;

		private class SerialPortFinderRunnerPortListener implements SerialPortCommandListener {

			@Override
			public void handleStampCommand(final SerialPortCommand command) {
				LOGGER.log(Level.INFO, "Received a command [" + command + "]");
				LOGGER.log(Level.FINE, "Current serial identifier [" + serialIdentifier + "] + port open ["
						+ currentPortOpen.getName() + "]");

				if (command != null && command.getCommandIdentifier() != null && command.getCommand() != null
						&& command.getCommandIdentifier().equals(SerialPortCommandList.IDS.toString())
						&& !command.getDataHashMap().isEmpty()
						&& command.getDataHashMap().get(0).equals(serialIdentifier)) {
					LOGGER.log(Level.INFO, "Identified STAMP on port " + currentPortOpen.getName() + "!");
					stampIO.shutdown();
					fireStampFinderListenerStampFound(currentPortOpen);
					portFound = true;
					exit = true;
				} else {
					LOGGER.log(Level.FINE, "Command ignored for serial port finder. Command [" + command + "]");
				}
			}

		}

	}

	/**
	 * @param rs232configs
	 */
	public void populateProperties(final HardwareNode rs232configs) {
		setStampIdentifier(rs232configs.getId());
		setPortBaudRate(rs232configs.getRs232().getBaud().intValue());
		setPortStopBits(rs232configs.getRs232().getStopbits().intValue());
		setPortParity(rs232configs.getRs232().getParitybits().intValue());
		setPortDataBits(rs232configs.getRs232().getNumbits().intValue());
		setTimeOutPerPort(rs232configs.getRs232().getTimeout().getPortListen().getTime().intValue());
	}

}
