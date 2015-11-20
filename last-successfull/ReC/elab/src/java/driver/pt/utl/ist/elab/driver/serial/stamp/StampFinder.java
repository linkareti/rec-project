package pt.utl.ist.elab.driver.serial.stamp;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommandListener;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class StampFinder {
	private static final Logger LOGGER = Logger.getLogger(StampFinder.class.getName());

	/** Holds value of property applicationNameLockPort. */
	private String applicationNameLockPort = "StampFinder App Lock";

	/** Holds value of property stampIdentifier. */
	private String stampIdentifier = "STAMP_ID";

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
	private StampFinderRunner runner = null;

	/** Holds value of property timeOutPerPort. */
	private long timeOutPerPort = 1000;

	/** Creates a new instance of StampFinder */
	public StampFinder() {

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
		return stampIdentifier;
	}

	/**
	 * Setter for property stampIdentifier.
	 * 
	 * @param stampIdentifier New value of property stampIdentifier.
	 */
	public void setStampIdentifier(final String stampIdentifier) {
		this.stampIdentifier = stampIdentifier;
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

	public void startSearch() {
		final Enumeration<?> commPortIdentifiers = gnu.io.CommPortIdentifier.getPortIdentifiers();

		final LinkedList<CommPortIdentifier> tempPorts = new LinkedList<CommPortIdentifier>();

		LOGGER.log(Level.INFO, "Are there COMM Ports on the System? " + commPortIdentifiers.hasMoreElements());

		while (commPortIdentifiers.hasMoreElements()) {
			final CommPortIdentifier identifier = (CommPortIdentifier) commPortIdentifiers.nextElement();
			if (identifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
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

		runner = new StampFinderRunner();
		runner.setPriority(Thread.NORM_PRIORITY + 2);
		runner.start();

	}

	/**
	 * Registers StampFinderListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	public synchronized void addStampFinderListener(
			final pt.utl.ist.elab.driver.serial.stamp.StampFinderListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(pt.utl.ist.elab.driver.serial.stamp.StampFinderListener.class, listener);
	}

	/**
	 * Removes StampFinderListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	public synchronized void removeStampFinderListener(
			final pt.utl.ist.elab.driver.serial.stamp.StampFinderListener listener) {
		listenerList.remove(pt.utl.ist.elab.driver.serial.stamp.StampFinderListener.class, listener);
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
			if (listeners[i] == pt.utl.ist.elab.driver.serial.stamp.StampFinderListener.class) {
				((pt.utl.ist.elab.driver.serial.stamp.StampFinderListener) listeners[i + 1]).stampFound(event);
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

	private class StampFinderRunner extends Thread {

		private boolean exit = false;
		private final Thread current = null;
		private int currentPort = 0;
		private BaseStampIO stampIO = null;
		private SerialPort currentPortOpen = null;
		// private BufferedReader currentReader=null;
		private final StampFinderRunnerPortListener stampFinderRunnerEventListener = new StampFinderRunnerPortListener();

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
		}

		public void cyclePort() {
			if (ports == null) {
				return;
			}

			if (currentPort >= ports.length) {
				currentPort = 0;
				LOGGER.log(Level.INFO, "Restarting from the first port!");
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
				currentPortOpen = (SerialPort) cpi.open(applicationNameLockPort, 100);
				stampIO = new BaseStampIO();
				stampIO.setApplicationNameLockPort(getApplicationNameLockPort());
				stampIO.setPortBaudRate(getPortBaudRate());
				stampIO.setPortDataBits(getPortDataBits());
				stampIO.setPortParity(getPortParity());
				stampIO.setPortStopBits(getPortStopBits());
				stampIO.setDTR(isDTR());
				stampIO.setRTS(isRTS());
				stampIO.setWaitForEcho(isWaitForEcho());
				stampIO.setStampCommandListener(stampFinderRunnerEventListener);
				stampIO.setPort(currentPortOpen);
				// currentReader=new BufferedReader(new
				// InputStreamReader(currentPortOpen.getInputStream(),"us-ascii"));
				// currentPortOpen.addEventListener(stampFinderRunnerEventListener);
				// currentPortOpen.notifyOnDataAvailable(true);

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
				synchronized (stampFinderRunnerEventListener) {
					LOGGER.log(Level.INFO, "NO id string detected on port " + cpi.getName());
					// currentPortOpen.removeEventListener();
					stampIO.shutdown();
					LOGGER.log(Level.INFO, "Shuted down stampIO!");
				}
			}

			currentPort++;
		}

		private boolean portFound = false;

		private class StampFinderRunnerPortListener implements StampCommandListener {

			@Override
			public void handleStampCommand(final StampCommand command) {
				LOGGER.log(Level.INFO, "Received a command " + command.getCommandIdentifier() + "!");
				if (command.getCommandIdentifier().equals(stampIdentifier)) {
					LOGGER.log(Level.INFO, "Identified STAMP on port " + currentPortOpen.getName() + "!");
					stampIO.shutdown();
					fireStampFinderListenerStampFound(currentPortOpen);
					portFound = true;
					exit = true;
				} else {
					LOGGER.log(Level.FINE, "Command ignored for stamp finder. Command [" + command + "]");
				}
			}

		}

	}

}