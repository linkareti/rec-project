/*
 * SerialComm.java
 *
 * Created on 2 de Abril de 2003, 19:36
 */

package pt.utl.ist.elab.driver.webrobot.serial;

/**
 *
 * @author André Neto - LEFT - IST
 */

import pt.utl.ist.elab.driver.webrobot.RobotStateMachine;

public class SerialComm implements gnu.io.SerialPortEventListener {

	private static gnu.io.CommPortIdentifier commPortIdentifier;
	public static gnu.io.SerialPort serialPort;
	private static java.io.InputStream inputStream;
	private static java.io.OutputStream outputStream;
	private static String comName = "/dev/ttyS1";
	private static String arrivedString = null;
	private static String appName = "Elab-WebRobot";
	private static String encoding = "Cp437";
	private static int baud = 9600;
	private static int databits = gnu.io.SerialPort.DATABITS_8;
	private static int flowcontrol = gnu.io.SerialPort.FLOWCONTROL_NONE;
	private static int parity = gnu.io.SerialPort.PARITY_NONE;
	private static int stopbits = gnu.io.SerialPort.STOPBITS_1;

	/**
	 * The robot sends its sensors states in two packets... :(
	 */
	private static boolean waitingSecondPacket = false;
	private static boolean portOpened = false;

	/** Properties stuff... */
	java.io.InputStream is = null;
	java.io.File propFile = null;
	String resourceLocation = null;
	java.util.Properties props = null;

	/**
	 * If the robot doesn't respond at the end of n periods launch security
	 * procedures
	 */
	private int securityPeriods = 0;

	/**
	 * Debug communications...
	 */
	private static pt.utl.ist.elab.driver.webrobot.debug.ReadWrite readWrite;

	/**
	 * Stores the sensors states...
	 */
	private int[] sensorsState = new int[13];

	private pt.utl.ist.elab.driver.webrobot.RobotStateMachine robotStateMachine;

	/** Creates a new instance of SerialComm */
	public SerialComm() {
		if (isPortOpened()) {
			System.out.println("The port is already opened...");
			closePort();
		}
		readProps();
		configurePort();
		setWriteMode();
	}

	public SerialComm(pt.utl.ist.elab.driver.webrobot.debug.ReadWrite readWrite) {
		readProps();
		this.readWrite = readWrite;
		configurePort();
		setWriteMode();
	}

	private void readProps() {
		resourceLocation = "WebRobotProps.properties";
		propFile = new java.io.File(resourceLocation);
		try {
			is = new java.io.FileInputStream(propFile);
			props = new java.util.Properties();
			props.load(is);
			is.close();
		} catch (java.io.FileNotFoundException fnfe) {
			System.out.println("Couldn't found the file...\n" + fnfe);
			resourceLocation = getClass().getResource(
					"/pt/utl/ist/elab/driver/webrobot/configs/WebRobotProps.properties").getFile();
			try {
				propFile = new java.io.File(resourceLocation);
				is = new java.io.FileInputStream(propFile);
				props = new java.util.Properties();
				props.load(is);
				is.close();
			} catch (java.io.FileNotFoundException fnfe2) {
				System.out.println("Couldn't found the file...\n" + fnfe2);
			} catch (java.io.IOException ioe2) {
				System.out.println("Exception...\n" + ioe2);
			}
		} catch (java.io.IOException ioe) {
			System.out.println("Exception...\n" + ioe);
		}
		comName = props.getProperty("comName");
		baud = Integer.parseInt(props.getProperty("baud"), 10);
		databits = Integer.parseInt(props.getProperty("databits"), 10);
		flowcontrol = Integer.parseInt(props.getProperty("flowcontrol"), 10);
		parity = Integer.parseInt(props.getProperty("parity"), 10);
		stopbits = Integer.parseInt(props.getProperty("stopbits"), 10);
	}

	public synchronized void configurePort() {
		try {
			commPortIdentifier = gnu.io.CommPortIdentifier.getPortIdentifier(comName);
		} catch (gnu.io.NoSuchPortException nspe) {
			System.out.println("A porta:" + comName + " n�o foi encontrada\n" + nspe);
			return;
		}

		try {
			serialPort = (gnu.io.SerialPort) commPortIdentifier.open(appName, 2000);
		} catch (gnu.io.PortInUseException piue) {
			System.out.println("Esta porta est� a ser usada por outra aplica��o\n" + piue);
			return;
		}

		try {
			serialPort.setSerialPortParams(baud, databits, stopbits, parity);
		} catch (gnu.io.UnsupportedCommOperationException ucoe) {
			System.out.println("N�o consegui abrir a porta com os parametros selecionados!\n" + ucoe
					+ "\nA porta ser� fechada");
			System.out.println("baud=" + baud);
			System.out.println("databits=" + databits);
			System.out.println("databits_default=" + gnu.io.SerialPort.DATABITS_8);
			System.out.println("stopbits=" + stopbits);
			System.out.println("stopbits_default=" + gnu.io.SerialPort.STOPBITS_1);
			System.out.println("parity=" + parity);
			System.out.println("parity_none=" + gnu.io.SerialPort.PARITY_NONE);
			serialPort.close();
			return;
		}

		try {
			serialPort.setFlowControlMode(flowcontrol);
		} catch (gnu.io.UnsupportedCommOperationException ucoe) {
			System.out.println("O flow control escolhido n�o � suportado...\n" + ucoe + "\nA porta ser� fechada");
			serialPort.close();
			return;
		}

		try {
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (java.util.TooManyListenersException tmle) {
			System.out.println("Demasiados listeners...\n" + tmle);
		}

		try {
			inputStream = serialPort.getInputStream();
			outputStream = serialPort.getOutputStream();
		} catch (java.io.IOException ioe) {
			System.out.println("N�o consegui criar as streams...\n" + ioe);
		}
		setPortOpened(true);
	}

	public synchronized void write(int data) {
		/** The PIC has a problem with 128... */
		if (data == 128) {
			data = 127;
		} else if (data > 127) {
			data = data - 256;
		}
		if (!isPortOpened() || restarting) {
			return;
		}
		setWriteMode();
		try {
			outputStream.write((byte) data);
			outputStream.flush();
		} catch (java.io.IOException ioe) {
			System.out.println("N�o consegui escrever na porta s�rie\n" + ioe);
		}
		/**
		 * I'm asking the robot to send information
		 */
		if (data == 83) {
			securityPeriods++;
		}
		/**
		 * If securityPeriods==2 ...then something went wrong...Wrote 2 times
		 * without an answer from the robot ...Launching security procedure...
		 * Note that the securityPeriods goes to zero in the resolvedArrived
		 * function (meaning that the robot is comunicating...)
		 */
		if (securityPeriods == 2) {
			/**
			 * Try it 2...otherwise launch security procedure...
			 */
			securityPeriods = 0;
			restartPort();
			// System.out.println("Security procedure launched!!");
		}
	}

	/** WINDOWS VERSION ONLY WORKS WITH THIS METHOD!! */
	int firstNBytes = 0;

	/*
	 * public synchronized void serialEvent(gnu.io.SerialPortEvent
	 * serialPortEvent) { try {
	 * if(serialPortEvent.getEventType()==gnu.io.SerialPortEvent.DATA_AVAILABLE)
	 * { if(serialPort.isRTS()) { return; }
	 * Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	 * 
	 * byte[] readBuffer = new byte[20]; int nBytes=0; try { while
	 * (inputStream.available() > 0) { nBytes = inputStream.read(readBuffer); }
	 * System.out.println(nBytes); } catch (java.io.IOException ioe) {
	 * System.out.println("Erros ao ler da porta s�rie\n"+ioe); }
	 * 
	 * System.out.println("Reading="+new String(readBuffer));
	 * 
	 * if(nBytes>2&&readBuffer[1]==65&&readBuffer[2]==66) {
	 * waitingSecondPacket=true; firstNBytes=nBytes-2;
	 * if(nBytes>readBuffer.length) { nBytes=readBuffer.length; } for(int
	 * i=2;i<nBytes;i++) { sensorsState[i-2]=(int)readBuffer[i]; } } else
	 * if(waitingSecondPacket) { for(int
	 * i=0;i<(sensorsState.length-firstNBytes);i++) {
	 * sensorsState[i+firstNBytes]=(int)readBuffer[i]; }
	 * //sensorsState[sensorsState.length-1]=(int)readBuffer[0];
	 * waitingSecondPacket=false; setWriteMode(); securityPeriods=0;
	 * if(robotStateMachine!=null) { robotStateMachine.setNewData(true); }
	 * if(readWrite!=null) { readWrite.setValues(sensorsState); } } } }
	 * catch(Exception e) { System.out.println(e); } }
	 */

	/** LINUX VERSION...TO TEST IN WINDOWS */
	private boolean waitConf = true;
	private boolean fillArray = false;
	private boolean arrayFilled = false;

	private int readByte = 0;
	private int nBytes = 0;

	public synchronized void serialEvent(gnu.io.SerialPortEvent serialPortEvent) {
		try {
			if (serialPortEvent.getEventType() == gnu.io.SerialPortEvent.DATA_AVAILABLE) {
				if (serialPort.isRTS()) {
					return;
				}
				Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

				try {
					while (inputStream.available() > 0) {
						readByte = inputStream.read();

						/**
						 * If an 'A' (waitConf=false)was received first and it
						 * isn't fillig the array, start filling
						 */
						if (!waitConf && readByte == 66 && !fillArray) {
							fillArray = true;
							sensorsState = new int[13];
							continue;
						}

						/**
						 * If an 'A' is received and it isn't fillig the array
						 * set waitConf=true, and hope the next char is a 'B'
						 */
						waitConf = true;
						if (readByte == 65 && !fillArray) {
							waitConf = false;
						}

						/** Add the received char to the array */
						if (fillArray) {
							sensorsState[nBytes] = readByte;
							nBytes++;
						}

						/** Ok the array is full, send the array */
						if (nBytes == sensorsState.length) {
							arrayFilled = true;
							fillArray = false;
							nBytes = 0;
							break;
						}
					}
				} catch (java.io.IOException ioe) {
					System.out.println("Erros ao ler da porta s�rie\n" + ioe);
				}

				if (arrayFilled) {
					setWriteMode();
					securityPeriods = 0;
					if (robotStateMachine != null) {
						robotStateMachine.setNewData(true);
					}
					if (readWrite != null) {
						readWrite.setValues(sensorsState);
					}
					arrayFilled = false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public synchronized int[] getSensorsState() {
		synchronized (sensorsState) {
			return sensorsState;
		}
	}

	public void setWriteMode() {
		serialPort.setRTS(true);
	}

	public void setReadMode() {
		serialPort.setRTS(false);
	}

	private boolean restarting = false;

	public synchronized void restartPort() {
		restarting = true;
		setWriteMode();
		try {
			Thread.currentThread().sleep(100);
		} catch (InterruptedException ie) {
		}
		restarting = false;
	}

	public void registerStateMachine(RobotStateMachine robotStateMachine) {
		this.robotStateMachine = robotStateMachine;
	}

	public boolean isPortOpened() {
		return portOpened;
	}

	public void setPortOpened(boolean portOpened) {
		this.portOpened = portOpened;
	}

	public String getSerialData() {
		return arrivedString;
	}

	public void closePort() {
		if (!isPortOpened()) {
			return;
		}
		try {
			outputStream.close();
			inputStream.close();
		} catch (java.io.IOException ioe) {
			System.out.println("N�o consegui fechar as streams");
		}
		serialPort.close();
		setPortOpened(false);
	}
}