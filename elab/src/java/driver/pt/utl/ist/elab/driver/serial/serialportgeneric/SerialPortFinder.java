/*
 * StampFinder.java
 *
 * Created on 15 de Maio de 2003, 11:51
 *
 *
 * ->Changed by André on 26/07/04:
 *    Added suport to Basic Atom. Now we can control RTS, DTR and echo
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.serialportgeneric.transproc.SerialPortCommand;
import pt.utl.ist.elab.driver.serial.serialportgeneric.transproc.SerialPortCommandListener;

import com.linkare.rec.impl.logging.LoggerUtil;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class SerialPortFinder {
	private static String STAMP_FINDER_LOGGER = "StampFinder.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(STAMP_FINDER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(STAMP_FINDER_LOGGER));
		}
	}
	/** Holds value of property applicationNameLockPort. */
	private String applicationNameLockPort = "SerialFinder App Lock";

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

	/** Creates a new instance of StampFinder */
	public SerialPortFinder() {

	}

	/**
	 * Getter for property applicationNameLockPort.
	 * 
	 * @return Value of property applicationNameLockPort.
	 */
	public String getApplicationNameLockPort() {
		return this.applicationNameLockPort;
	}

	/**
	 * Setter for property applicationNameLockPort.
	 * 
	 * @param applicationNameLockPort
	 *            New value of property applicationNameLockPort.
	 */
	public void setApplicationNameLockPort(String applicationNameLockPort) {
		this.applicationNameLockPort = applicationNameLockPort;
	}

	/**
	 * Getter for property stampIdentifier.
	 * 
	 * @return Value of property stampIdentifier.
	 */
	public String getStampIdentifier() {
		return this.serialIdentifier;
	}

	/**
	 * Setter for property stampIdentifier.
	 * 
	 * @param stampIdentifier
	 *            New value of property stampIdentifier.
	 */
	public void setStampIdentifier(String stampIdentifier) {
		this.serialIdentifier = stampIdentifier;
	}

	/**
	 * Getter for property portParity.
	 * 
	 * @return Value of property portParity.
	 */
	public int getPortParity() {
		return this.portParity;
	}

	/**
	 * Setter for property portParity.
	 * 
	 * @param portParity
	 *            New value of property portParity.
	 */
	public void setPortParity(int portParity) {
		this.portParity = portParity;
	}

	/**
	 * Getter for property portBaudRate.
	 * 
	 * @return Value of property portBaudRate.
	 */
	public int getPortBaudRate() {
		return this.portBaudRate;
	}

	/**
	 * Setter for property portBaudRate.
	 * 
	 * @param portBaudRate
	 *            New value of property portBaudRate.
	 */
	public void setPortBaudRate(int portBaudRate) {
		this.portBaudRate = portBaudRate;
	}

	/**
	 * Getter for property portDataBits.
	 * 
	 * @return Value of property portDataBits.
	 */
	public int getPortDataBits() {
		return this.portDataBits;
	}

	/**
	 * Setter for property portDataBits.
	 * 
	 * @param portDataBits
	 *            New value of property portDataBits.
	 */
	public void setPortDataBits(int portDataBits) {
		this.portDataBits = portDataBits;
	}

	/**
	 * Getter for property portStopBits.
	 * 
	 * @return Value of property portStopBits.
	 */
	public int getPortStopBits() {
		return this.portStopBits;
	}

	/**
	 * Setter for property portStopBits.
	 * 
	 * @param portStopBits
	 *            New value of property portStopBits.
	 */
	public void setPortStopBits(int portStopBits) {
		this.portStopBits = portStopBits;
	}

	/**
	 * Getter for property DTR.
	 * 
	 * @return Value of property DTR.
	 * 
	 */
	public boolean isDTR() {
		return this.DTR;
	}

	/**
	 * Setter for property DTR.
	 * 
	 * @param DTR
	 *            New value of property DTR.
	 * 
	 */
	public void setDTR(boolean DTR) {
		this.DTR = DTR;
	}

	/**
	 * Getter for property RTS.
	 * 
	 * @return Value of property RTS.
	 * 
	 */
	public boolean isRTS() {
		return this.RTS;
	}

	/**
	 * Setter for property RTS.
	 * 
	 * @param RTS
	 *            New value of property RTS.
	 * 
	 */
	public void setRTS(boolean RTS) {
		this.RTS = RTS;
	}

	/**
	 * Getter for property waitForEcho.
	 * 
	 * @return Value of property waitForEcho.
	 * 
	 */
	public boolean isWaitForEcho() {
		return this.waitForEcho;
	}

	/**
	 * Setter for property waitForEcho.
	 * 
	 * @param waitForEcho
	 *            New value of property waitForEcho.
	 * 
	 */
	public void setWaitForEcho(boolean waitForEcho) {
		this.waitForEcho = waitForEcho;
	}

	public void stopSearch() {
		if (runner != null)
			runner.stopNow();

		runner = null;
		ports = null;
	}

	public void startSearch() {
		Enumeration<CommPortIdentifier> commPortIdentifiers = gnu.io.CommPortIdentifier.getPortIdentifiers();

		LinkedList<CommPortIdentifier> tempPorts = new LinkedList<CommPortIdentifier>();

		Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO,
				"Are there COMM Ports on the System? " + commPortIdentifiers.hasMoreElements());

		while (commPortIdentifiers.hasMoreElements()) {
			CommPortIdentifier identifier = commPortIdentifiers.nextElement();
			if (identifier.getPortType() == CommPortIdentifier.PORT_SERIAL)
				tempPorts.add(identifier);
		}

		ports = new CommPortIdentifier[tempPorts.size()];
		Object[] portsObj = tempPorts.toArray();

		Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO,
				"There are " + portsObj.length + " Serial COMM Ports in the system...");

		System.arraycopy(portsObj, 0, ports, 0, ports.length);

		Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO, "Serial COMM Ports in the system are:");
		for (int i = 0; i < ports.length; i++) {
			Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO, "Port[" + i + "]=" + ports[i].getName());
		}

		runner = new SerialPortFinderRunner();
		runner.setPriority(Thread.NORM_PRIORITY + 2);
		runner.start();

	}

	/**
	 * Registers StampFinderListener to receive events.
	 * 
	 * @param listener
	 *            The listener to register.
	 */
	public synchronized void addStampFinderListener(SerialPortFinderListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(SerialPortFinderListener.class, listener);
	}

	/**
	 * Removes StampFinderListener from the list of listeners.
	 * 
	 * @param listener
	 *            The listener to remove.
	 */
	public synchronized void removeStampFinderListener(pt.utl.ist.elab.driver.serial.stamp.StampFinderListener listener) {
		listenerList.remove(pt.utl.ist.elab.driver.serial.stamp.StampFinderListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event
	 *            The event to be fired
	 */
	private void fireStampFinderListenerStampFound(SerialPort event) {
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
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
		return this.timeOutPerPort;
	}

	/**
	 * Setter for property timeOutPerPort.
	 * 
	 * @param timeOutPerPort
	 *            New value of property timeOutPerPort.
	 */
	public void setTimeOutPerPort(long timeOutPerPort) {
		this.timeOutPerPort = timeOutPerPort;
	}

	private class SerialPortFinderRunner extends Thread {

		private boolean exit = false;
		private Thread current = null;
		private int currentPort = 0;
		private BaseSerialPortIO stampIO = null;
		private SerialPort currentPortOpen = null;
		// private BufferedReader currentReader=null;
		private SerialPortFinderRunnerPortListener serialPortFinderRunnerEventListener = new SerialPortFinderRunnerPortListener();

		public void stopNow() {
			exit = true;
			if (current != null) {
				try {
					current.join();
				} catch (InterruptedException e) {
					LoggerUtil.logThrowable(null, e, Logger.getLogger(STAMP_FINDER_LOGGER));
				}
			}
		}

		public void run() {
			while (!exit) {
				Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO, "Cycling port...");
				cyclePort();
			}
		}

		public void cyclePort() {
			if (ports == null)
				return;

			if (currentPort >= ports.length) {
				currentPort = 0;
				Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO, "Restarting from the first port!");
			}

			CommPortIdentifier cpi = ports[currentPort];

			if (cpi.isCurrentlyOwned()) {
				Logger.getLogger(STAMP_FINDER_LOGGER).log(
						Level.INFO,
						"Serial Port " + cpi.getName() + " is currently owned by another application - "
								+ cpi.getCurrentOwner());
				currentPort++;
				return;
			}

			Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO,
					"Serial Port " + cpi.getName() + " is not Owned - Opening & Locking It! ");

			try {
				currentPortOpen = (SerialPort) cpi.open(applicationNameLockPort, 100);
				stampIO = new BaseSerialPortIO();
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
				// currentReader=new BufferedReader(new InputStreamReader(currentPortOpen.getInputStream(),"us-ascii"));
				// currentPortOpen.addEventListener(stampFinderRunnerEventListener);
				// currentPortOpen.notifyOnDataAvailable(true);

			} catch (gnu.io.PortInUseException e) {
				LoggerUtil.logThrowable("Serial port " + cpi.getName() + " is currently in use...", e, Logger
						.getLogger(STAMP_FINDER_LOGGER));
				currentPort++;
				return;
			}

			try {
				Logger.getLogger(STAMP_FINDER_LOGGER).log(
						Level.INFO,
						"Sleeping for " + (timeOutPerPort > 0 ? timeOutPerPort : 100) + " ms for port " + cpi.getName()
								+ " data events");
				synchronized (this) {
					wait(timeOutPerPort > 0 ? timeOutPerPort : 100);
					Logger.getLogger(STAMP_FINDER_LOGGER).log(
							Level.INFO,
							"Ended Sleeping for " + (timeOutPerPort > 0 ? timeOutPerPort : 100) + " ms for port "
									+ cpi.getName() + " data events");
				}

			} catch (InterruptedException e) {
				LoggerUtil.logThrowable("Error waiting for SerialPort identification!", e, Logger
						.getLogger(STAMP_FINDER_LOGGER));
				return;
			}

			if (!portFound) {
				Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO, "Port was not found!");
				synchronized (serialPortFinderRunnerEventListener) {
					Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO,
							"NO id string detected on port " + cpi.getName());
					// currentPortOpen.removeEventListener();
					stampIO.shutdown();
					Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO, "Shuted down stampIO!");
				}
				/*
				 * try { Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO,"Closing port "+cpi.getName()+" IO
				 * Stream!"); currentReader.close(); }catch(Exception e) { LoggerUtil.logThrowable("Error closing port
				 * "+cpi.getName() +" IO Stream!",e,Logger.getLogger(STAMP_FINDER_LOGGER)); } try {
				 * Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO,"Closing port "+cpi.getName()+"!");
				 * currentPortOpen.close(); }catch(Exception e) { LoggerUtil.logThrowable("Error closing port
				 * "+cpi.getName()+"!",e,Logger.getLogger(STAMP_FINDER_LOGGER)); }
				 */
			}

			currentPort++;
		}

		private boolean portFound = false;

		private class SerialPortFinderRunnerPortListener implements SerialPortCommandListener {

			/*
			 * public synchronized void serialEvent(gnu.io.SerialPortEvent serialPortEvent) {
			 * Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO,"Received port event "+currentPortOpen.getName()+"
			 * whith type="+serialPortEvent.getEventType()+" - DataAvailableEventType="+SerialPortEvent.DATA_AVAILABLE);
			 * 
			 * if(serialPortEvent.getEventType()==SerialPortEvent.DATA_AVAILABLE) { String line="";
			 * 
			 * try { Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO,"Reading line of data from port
			 * "+currentPortOpen.getName()+"!"); line=currentReader.readLine();
			 * Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO,"Data Line is : '"+line+"'!"); }catch(IOException e) {
			 * LoggerUtil.logThrowable("IO Error reading line from port
			 * "+currentPortOpen.getName()+"!",e,Logger.getLogger(STAMP_FINDER_LOGGER)); }
			 * if(line.equals(stampIdentifier)) { Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO,"Identified STAMP
			 * on port "+currentPortOpen.getName()+"!"); //currentPortOpen.removeEventListener();
			 * fireStampFinderListenerStampFound(currentPortOpen); portFound=true; exit=true; } } }
			 */

			public void handleStampCommand(SerialPortCommand command) {
				Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO,
						"Received a command " + command.getCommandIdentifier() + "!");
				if (command.getCommandIdentifier().equals(serialIdentifier)) {
					Logger.getLogger(STAMP_FINDER_LOGGER).log(Level.INFO,
							"Identified STAMP on port " + currentPortOpen.getName() + "!");
					stampIO.shutdown();
					fireStampFinderListenerStampFound(currentPortOpen);
					portFound = true;
					exit = true;
				}
			}

		}

	}

}
