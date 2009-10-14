/*
 * BaseStampIO.java
 *
 * Created on 15 de Maio de 2003, 14:30
 *
 * ->Changed by André on 26/07/04:
 *    Added suport to Basic Atom. Now we can control RTS, DTR and echo
 */
package pt.utl.ist.elab.driver.serial.stamp;

import gnu.io.*;
import java.io.*;
import java.util.logging.*;
import com.linkare.rec.impl.logging.*;
import pt.utl.ist.elab.driver.serial.stamp.transproc.*;

/**
 *
 * @author  jp
 */
public class BaseStampIO {

    private static String STAMP_IO_LOGGER = "BaseStampIO.Logger";
    

    static {
        Logger l = LogManager.getLogManager().getLogger(STAMP_IO_LOGGER);
        if (l == null) {
            LogManager.getLogManager().addLogger(Logger.getLogger(STAMP_IO_LOGGER));
        }
    }
    private SerialPort sPort = null;
    private SerialPortReader currentSerialPortReader = null;
    //private BufferedReader inReader=null;
    private Reader inReader = null;
    private OutputStream outStream = null;
    /** Utility field used by event firing mechanism. */
    private StampCommandListener listener = null;
    private String portName = "COM1";

    /** Creates a new instance of BaseStampIO */
    public BaseStampIO() {
    }

    public void setPort(SerialPort sPort) {
        try {
            synchronized (sPort) {
                if (currentSerialPortReader != null) {
                    currentSerialPortReader.exitNow();
                }
                portName = sPort.getName();
                sPort.close();
                CommPortIdentifier cpi = CommPortIdentifier.getPortIdentifier(portName);
                this.sPort = (SerialPort) cpi.open(applicationNameLockPort, 100);
                this.sPort.setSerialPortParams(portBaudRate, portDataBits, portStopBits, portParity);
                outStream = this.sPort.getOutputStream();
                inReader = new InputStreamReader(this.sPort.getInputStream(), "us-ascii");
                (currentSerialPortReader = new SerialPortReader()).start();
            //inReader=new BufferedReader(new InputStreamReader(this.sPort.getInputStream()),100);
            }
        } catch (Exception e) {
            LoggerUtil.logThrowable(null, e, Logger.getLogger(STAMP_IO_LOGGER));
        }
    }
    private String lastOutputMessage = null;

    public void writeMessage(String writeMessage) {
        synchronized (sPort) {
            try {
                Logger.getLogger(STAMP_IO_LOGGER).log(Level.INFO, "Write Line to STAMP: " + writeMessage);
                byte[] message = (writeMessage + "\r").getBytes("us-ascii");
                lastOutputMessage = writeMessage;

                if (sPort.getBaudRate() >= 9600) {
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                    }
                    for (int i = 0; i < message.length; i++) {
                        outStream.write(message, i, 1);
                        outStream.flush();
                        try {
                            Thread.sleep(50);
                        } catch (Exception e) {
                        }
                    }
                } else {

                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                    }
                    outStream.write(message);
                    outStream.flush();
                }

            } catch (IOException e) {
                LoggerUtil.logThrowable("Unable to write command to serial port...", e, Logger.getLogger(STAMP_IO_LOGGER));
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

        public void run() {
            currentThread = Thread.currentThread();
            String lineRead = null;
            StringBuffer lineReadTemp = null;

            while (!exit) {
                Thread.yield();

                try {
                    synchronized (sPort) {
                        char readChar = 0;
                        lineRead = null;
                        lineReadTemp = new StringBuffer(1024);
                        while (!exit) {
                            while (!inReader.ready()) {
                                sleep(0, 500);
                            }
                            readChar = (char) inReader.read();
                            if (readChar != '\r' && readChar != '\n') {
                                lineReadTemp.append(readChar);
                            } else {
                                break;
                            }
                        }

                        if (exit) {
                            currentThread = null;
                            return;
                        }

                        lineRead = lineReadTemp.toString().trim();
                        Logger.getLogger(STAMP_IO_LOGGER).log(Level.INFO, "Line read from STAMP: " + lineRead);

                        if (waitForEcho && lastOutputMessage != null) {
                            Logger.getLogger(STAMP_IO_LOGGER).log(Level.INFO, "Ignoring message...");
                            if (lastOutputMessage.startsWith(lineRead) || lineRead.startsWith(lastOutputMessage)) {
                                lastOutputMessage = null;
                            }
                            continue;
                        }
                    }


                    if (!lineRead.equals("")) {
                        Logger.getLogger(STAMP_IO_LOGGER).log(Level.INFO, "Processing message...");
                        processIncomingLine(lineRead);
                        Logger.getLogger(STAMP_IO_LOGGER).log(Level.INFO, "Processed message...");
                    }

                } catch (Exception e) {
                    LoggerUtil.logThrowable("Unable to read line from stamp serial port...", e, Logger.getLogger(STAMP_IO_LOGGER));
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
            } catch (Exception e) {
            }

            if (currentThread != null) {
                try {
                    currentThread.join(1000);
                } catch (Exception e) {
                }
            }
        }
    }

    private void processIncomingLine(String lineRead) {
        if (lineRead == null) {
            return;
        }
        int commandpos = lineRead.indexOf(" ");
        //sPort.removeEventListener();
        StampCommand inCommand;
        if (commandpos != -1) {
            inCommand = new StampCommand(lineRead.substring(0, commandpos));
            inCommand.setCommand(lineRead.substring(commandpos + 1));
        } else {
            //Couldn't split by space, then use a tab... just in case some student
            //tries to reinvent the wheel
            commandpos = lineRead.indexOf("\t");
            if (commandpos != -1) {
                inCommand = new StampCommand(lineRead.substring(0, commandpos));
                inCommand.setCommand(lineRead.substring(commandpos + 1));
            } else {
                inCommand = new StampCommand(lineRead);
            }
        }

        Logger.getLogger(STAMP_IO_LOGGER).log(Level.INFO, "Firing stamp command listener...");
        fireStampCommandListenerHandleStampCommand(inCommand);
    }

    /** Registers StampCommandListener to receive events.
     * @param listener The listener to register.
     */
    public synchronized void setStampCommandListener(StampCommandListener listener) {
        this.listener = listener;
    }

    /** Removes StampCommandListener from the list of listeners.
     * @param listener The listener to remove.
     */
    public synchronized void removeStampCommandListener() {
        this.listener = null;
    }

    /** Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireStampCommandListenerHandleStampCommand(StampCommand event) {
        if (listener != null) {
            listener.handleStampCommand(event);
        }
        Logger.getLogger(STAMP_IO_LOGGER).log(Level.INFO, "No listener for this event!!!!");

    }

    public void shutdown() {
        Logger.getLogger(STAMP_IO_LOGGER).log(Level.INFO, "Shutting down port...");
        //sPort.removeEventListener();
        if (currentSerialPortReader != null) {
            currentSerialPortReader.exitNow();
        /*try
        {
        System.out.println("Trying to close inReader...");
        inReader.close();
        System.out.println("closed inReader...");
        }catch(Exception e)
        {
        System.out.println("oopppsss... closing inReader");
        LoggerUtil.logThrowable(null,e,Logger.getLogger(STAMP_IO_LOGGER));
        }
        
        try
        {
        System.out.println("Trying to close outStream...");
        outStream.close();
        System.out.println("Closed outStream...");
        }catch(Exception e)
        {
        LoggerUtil.logThrowable(null,e,Logger.getLogger(STAMP_IO_LOGGER));
        }
         */
        }
        if (sPort != null) {
            Logger.getLogger(STAMP_IO_LOGGER).log(Level.INFO, "Closing sPort...");
            sPort.close();
            Logger.getLogger(STAMP_IO_LOGGER).log(Level.INFO, "Closed sPort...");
        }

        Logger.getLogger(STAMP_IO_LOGGER).log(Level.INFO, "Shutted down port...");
    }

    /** Getter for property portParity.
     * @return Value of property portParity.
     */
    public int getPortParity() {
        return this.portParity;
    }

    /** Setter for property portParity.
     * @param portParity New value of property portParity.
     */
    public void setPortParity(int portParity) {
        this.portParity = portParity;
    }

    /** Getter for property portBaudRate.
     * @return Value of property portBaudRate.
     */
    public int getPortBaudRate() {
        return this.portBaudRate;
    }

    /** Setter for property portBaudRate.
     * @param portBaudRate New value of property portBaudRate.
     */
    public void setPortBaudRate(int portBaudRate) {
        this.portBaudRate = portBaudRate;
    }

    /** Getter for property portDataBits.
     * @return Value of property portDataBits.
     */
    public int getPortDataBits() {
        return this.portDataBits;
    }

    /** Setter for property portDataBits.
     * @param portDataBits New value of property portDataBits.
     */
    public void setPortDataBits(int portDataBits) {
        this.portDataBits = portDataBits;
    }

    /** Getter for property portStopBits.
     * @return Value of property portStopBits.
     */
    public int getPortStopBits() {
        return this.portStopBits;
    }

    /** Setter for property portStopBits.
     * @param portStopBits New value of property portStopBits.
     */
    public void setPortStopBits(int portStopBits) {
        this.portStopBits = portStopBits;
    }

    /** Getter for property applicationNameLockPort.
     * @return Value of property applicationNameLockPort.
     */
    public String getApplicationNameLockPort() {
        return this.applicationNameLockPort;
    }

    /** Setter for property applicationNameLockPort.
     * @param applicationNameLockPort New value of property applicationNameLockPort.
     */
    public void setApplicationNameLockPort(String applicationNameLockPort) {
        this.applicationNameLockPort = applicationNameLockPort;
    }

    /** Getter for property DTR.
     * @return Value of property DTR.
     *
     */
    public boolean isDTR() {
        return this.DTR;
    }

    /** Setter for property DTR.
     * @param DTR New value of property DTR.
     *
     */
    public void setDTR(boolean DTR) {
        if (sPort != null) {
            sPort.setDTR(DTR);
        }
        this.DTR = DTR;
    }

    /** Getter for property RTS.
     * @return Value of property RTS.
     *
     */
    public boolean isRTS() {
        return this.RTS;
    }

    /** Setter for property RTS.
     * @param RTS New value of property RTS.
     *
     */
    public void setRTS(boolean RTS) {
        if (sPort != null) {
            sPort.setRTS(RTS);
        }
        this.RTS = RTS;
    }

    /** Getter for property waitForEcho.
     * @return Value of property waitForEcho.
     *
     */
    public boolean isWaitForEcho() {
        return this.waitForEcho;
    }

    /** Setter for property waitForEcho.
     * @param waitForEcho New value of property waitForEcho.
     *
     */
    public void setWaitForEcho(boolean waitForEcho) {
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
