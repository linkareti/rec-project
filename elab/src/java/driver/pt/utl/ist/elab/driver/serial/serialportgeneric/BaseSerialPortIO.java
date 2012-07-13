package pt.utl.ist.elab.driver.serial.serialportgeneric;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommand;
import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommandListener;

import com.linkare.rec.impl.threading.TimedOutException;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class BaseSerialPortIO {

	private static final Logger LOGGER = Logger.getLogger(BaseSerialPortIO.class.getName());

	private SerialPort sPort = null;
	private SerialPortReader currentSerialPortReader = null;
	// private BufferedReader inReader=null;
	private Reader inReader = null;
	private OutputStream outStream = null;
	/** Utility field used by event firing mechanism. */
	private SerialPortCommandListener listener = null;
	private String portName = "COM1";

	private final AbstractSerialPortDriver driver;

	/**
	 * Creates a new instance of BaseStampIO
	 * 
	 * @param driver
	 */
	public BaseSerialPortIO(final AbstractSerialPortDriver driver) {
		this.driver = driver;
	}

	public void setPort(final SerialPort sPort) {
		try {
			synchronized (sPort) {
				if (currentSerialPortReader != null) {
					currentSerialPortReader.exitNow();
				}
				portName = sPort.getName();
				sPort.close();
				final CommPortIdentifier cpi = CommPortIdentifier.getPortIdentifier(portName);
				this.sPort = (SerialPort) cpi.open(applicationNameLockPort, 100);
				this.sPort.setSerialPortParams(portBaudRate, portDataBits, portStopBits, portParity);
				outStream = this.sPort.getOutputStream();
				inReader = new InputStreamReader(this.sPort.getInputStream(), "us-ascii");
				(currentSerialPortReader = new SerialPortReader()).start();
			}
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, null, e);
		}
	}

	private String lastOutputMessage = null;

	public void resetLastOutputMessage() {
		synchronized (sPort) {
			lastOutputMessage = null;
		}
	}

	public void writeMessage(final String writeMessage) {
		synchronized (sPort) {
			try {
				LOGGER.log(Level.INFO, "Write Line to STAMP: " + writeMessage);
				final byte[] message = (writeMessage + "\r").getBytes("us-ascii");
				lastOutputMessage = writeMessage.trim();

				if (sPort.getBaudRate() >= 9600) {
					try {
						Thread.sleep(50);
					} catch (final Exception e) {
					}
					for (int i = 0; i < message.length; i++) {
						outStream.write(message, i, 1);
						outStream.flush();
						try {
							Thread.sleep(50);
						} catch (final Exception e) {
						}
					}
				} else {

					try {
						Thread.sleep(50);
					} catch (final Exception e) {
					}
					outStream.write(message);
					outStream.flush();
				}

			} catch (final IOException e) {
				LOGGER.log(Level.SEVERE, "Unable to write command to serial port...", e);
			}
		}

	}

	public class SerialPortReader extends Thread {

		private boolean exit = false;
		private Thread currentThread = null;

		public SerialPortReader() {
			super();
			setDaemon(true);
		}

		@Override
		public void run() {
			currentThread = Thread.currentThread();
			String lineRead = null;
			StringBuffer lineReadTemp = null;

			while (!exit) {
				Thread.yield();

				try {

					synchronized (sPort) {
						if (inReader != null) {
							char readChar = 0;
							lineRead = null;
							if (!driver.isDriverInState(DriverState.RECEIVINGBIN)) {
								lineReadTemp = new StringBuffer(1024);
							} else {
								lineReadTemp = new StringBuffer(AbstractSerialPortDriver.currentBinaryLength + 5 /*
																												 * length
																												 * of
																												 * _BIN
																												 */);
								lineReadTemp.append("_BIN\t");
							}
							while (!exit) {
								while (!inReader.ready()) {
									Thread.sleep(0, 500);
								}
								readChar = (char) inReader.read();
								if (!driver.isDriverInState(DriverState.RECEIVINGBIN)) {
									if (readChar != '\r' && readChar != '\n') {
										lineReadTemp.append(readChar);
									} else {
										break;
									}
								} else {
									if (lineReadTemp.length() <= AbstractSerialPortDriver.currentBinaryLength + 5) {
										/* 5 = length of _BIN header */
										lineReadTemp.append(readChar);
									} else {
										break;
									}
								}
							}

							if (exit) {
								currentThread = null;
								return;
							}

							lineRead = lineReadTemp.toString().trim();
							LOGGER.log(Level.INFO, "Line read from STAMP [" + lineRead + "]");

							if (waitForEcho && lastOutputMessage != null) {
								LOGGER.log(Level.FINER, "Line read [" + lineRead + "] waiting for echo [" + waitForEcho
										+ "] of output message [" + lastOutputMessage + "]");

								if (lastOutputMessage.equalsIgnoreCase(lineRead)) {
									lastOutputMessage = null;
									LOGGER.log(Level.INFO, "Received the echo message.");
								}
								continue;
							}
						}
					}

					if (lineRead != null && !lineRead.equals("")) {
						processIncomingLine(lineRead);
					}

				} catch (final Exception e) {
					LOGGER.log(Level.SEVERE, "Unable to read line from stamp serial port...", e);
					currentThread = null;
					return;
				}

				currentThread = null;
			}

		}

		public void exitNow() {
			exit = true;
			try {
				inReader = null;
			} catch (final Exception e) {
			}

			if (currentThread != null) {
				try {
					currentThread.join(1000);
				} catch (final Exception e) {
				}
			}
		}
	}

	private void processIncomingLine(final String lineRead) throws Exception {
		LOGGER.log(Level.FINE, "Processing message incoming line [" + lineRead + "]");

		SerialPortCommand inCommand = null;
		String[] commandArray = null;

		if (lineRead == null) {
			return;
		}

		if (driver.isDriverInState(DriverState.RECEIVINGBIN)) {
			if (lineRead.length() >= AbstractSerialPortDriver.totalBinaryLength) {

			} else {
				// TODO : binary buffer
			}
		}

		commandArray = lineRead.split("\\s");
		LOGGER.log(Level.FINEST, "Splited (whitepaces) line " + Arrays.deepToString(commandArray));
		if (commandArray.length > 0) {
			inCommand = new SerialPortCommand(commandArray[0]);
			if (commandArray.length > 1) {
				final StringBuilder commandTemp = new StringBuilder(commandArray[1]);
				for (int i = 2; i < commandArray.length; i++) {
					commandTemp.append("\t").append(commandArray[i]);
				}
				inCommand.setCommand(commandTemp.toString());
			}
		}

		LOGGER.log(Level.FINE, "Firing stamp command listener...");
		fireStampCommandListenerHandleStampCommand(inCommand);
		LOGGER.log(Level.FINEST, "Fired stamp command listener...");

	}

	/**
	 * Registers StampCommandListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	public synchronized void setStampCommandListener(final SerialPortCommandListener listener) {
		this.listener = listener;
	}

	/**
	 * Removes StampCommandListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	public synchronized void removeStampCommandListener() {
		listener = null;
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 * @throws Exception
	 */
	private void fireStampCommandListenerHandleStampCommand(final SerialPortCommand event) throws Exception {
		try {
			if (listener != null) {
				listener.handleStampCommand(event);
			}
		} catch (final TimedOutException e) {
			LOGGER.log(Level.INFO, "TIMEOUT: " + e.getMessage());
			shutdown();
		}

	}

	public void shutdown() {
		LOGGER.log(Level.INFO, "Shutting down port...");
		if (currentSerialPortReader != null) {
			currentSerialPortReader.exitNow();
		}
		if (sPort != null) {
			LOGGER.log(Level.INFO, "Closing sPort...");
			sPort.close();
			LOGGER.log(Level.INFO, "Closed sPort...");
		}
		LOGGER.log(Level.INFO, "Shutted down port...");
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
		if (sPort != null) {
			sPort.setDTR(DTR);
		}
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
		if (sPort != null) {
			sPort.setRTS(RTS);
		}
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

	/** Holds value of property applicationNameLockPort. */
	private String applicationNameLockPort = "BaseIO App Lock";
	/** Holds value of property portParity. */
	private int portParity = SerialPort.PARITY_NONE;
	/** Holds value of property portBaudRate. */
	private int portBaudRate = 9600;
	/** Holds value of property portDataBits. */
	private int portDataBits = SerialPort.DATABITS_8;
	/** Holds value of property portStopBits. */
	private int portStopBits = SerialPort.STOPBITS_2;
	/** Holds value of property DTR. */
	private boolean DTR = false;
	/** Holds value of property RTS. */
	private boolean RTS = false;
	/** Holds value of property waitForEcho. */
	private boolean waitForEcho = true;

	public void reopen() {
		synchronized (sPort) {
			setPort(sPort);
		}
	}
}
