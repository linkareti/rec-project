/*
 * BaseCypressIO.java
 *
 * Created on 15 de Maio de 2003, 14:30
 *
 * ->Changed by André on 26/07/04:
 *    Added suport to Basic Atom. Now we can control RTS, DTR and echo
 */


package pt.utl.ist.elab.driver.usb.cypress;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.usb.UsbDevice;
import javax.usb.UsbEndpoint;
import javax.usb.UsbInterface;
import javax.usb.UsbPipe;
import javax.usb.event.UsbDeviceListener;
import javax.usb.event.UsbPipeListener;
import javax.usb.util.StandardRequest;

import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressCommand;
import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressCommandListener;

import com.ibm.jusb.UsbConfigurationImp;
import com.linkare.rec.impl.logging.LoggerUtil;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class BaseCypressIO implements UsbDeviceListener, UsbPipeListener
{
    
    private static String CYPRESS_IO_LOGGER = "BaseCypressIO.Logger";
    
    static
    {
        Logger l=LogManager.getLogManager().getLogger(CYPRESS_IO_LOGGER);
        if(l==null)
        {
            LogManager.getLogManager().addLogger(Logger.getLogger(CYPRESS_IO_LOGGER));
        }
    }
    
    private UsbDevice device = null;
    private boolean exit = false;
    private String cypressIdentifier = "ELAB_CYPRESS_V0.0";
    private short vendorID = (short)0x0547;
    private short productID = (short)0x2131;
    private short interfaceNumber = (short)0;
    private short alternateSetting = (short)1;
    private byte inputChannelNumber = (byte)1;
    private byte outputChannelNumber = (byte)2;
    private UsbPipe inPipe = null;
    private UsbPipe outPipe = null;
    private byte[] inBuffer = null;
    private int readerSleep = 2000;
    
    /** Utility field used by event firing mechanism. */
    private CypressCommandListener listener =  null;
    
    /** Creates a new instance of BaseCypressIO */
    public BaseCypressIO()
    {
    }
    
    public byte getInputChannelNumber()
    {
        return inputChannelNumber;
    }
    
    public void setInputChannelNumber(byte inputChannelNumber)
    {
        this.inputChannelNumber = inputChannelNumber;
    }
    
    public byte getOutputChannelNumber()
    {
        return outputChannelNumber;
    }
    
    public void setOutputChannelNumber(byte outputChannelNumber)
    {
        this.outputChannelNumber = outputChannelNumber;
    }
    
    public short getVendorID()
    {
        return vendorID;
    }
    
    public void setVendorID(short vendorID)
    {
        this.vendorID = vendorID;
    }
    
    public short getProductID()
    {
        return productID;
    }
    
    public void setProductID(short productID)
    {
        this.productID = productID;
    }
    
    public short getInterfaceNumber()
    {
        return interfaceNumber;
    }
    
    public void setInterfaceNumber(short interfaceNumber)
    {
        this.interfaceNumber = interfaceNumber;
    }
    
    public short getAlternateSetting()
    {
        return alternateSetting;
    }
    
    public void setAlternateSetting(short alternateSetting)
    {
        this.alternateSetting = alternateSetting;
    }
    
    public void setDevice(UsbDevice device)
    {
        try
        {
            synchronized(device)
            {
                this.device = device;
                
                Logger.getLogger(CYPRESS_IO_LOGGER).log(Level.INFO, "Going to configure the device!");
                
                List ifaces = device.getActiveUsbConfiguration().getUsbInterfaces();
                
                UsbInterface usbInterface = null;
                
                Logger.getLogger(CYPRESS_IO_LOGGER).log(Level.INFO, "Configuration= " + StandardRequest.getConfiguration(device));
                
                Logger.getLogger(CYPRESS_IO_LOGGER).log(Level.INFO, "Setting interface!");
                
                List configs = device.getUsbConfigurations();
                for(int i = 0; i<configs.size(); i++)
                {
                    List inter = ((UsbConfigurationImp)configs.get(i)).getUsbInterfaces();
                    usbInterface = (UsbInterface)inter.get(0);
                    usbInterface.claim();
                }
                
                List settings = usbInterface.getSettings();
                
                UsbInterface settingOne = (UsbInterface)settings.get(1);
                StandardRequest.setInterface(device, interfaceNumber, alternateSetting);
                
                List endPoints = settingOne.getUsbEndpoints();
                
                UsbEndpoint endPointIn = (UsbEndpoint)endPoints.get(inputChannelNumber);
                UsbEndpoint endPointOut = (UsbEndpoint)endPoints.get(outputChannelNumber);
                
                inPipe = endPointIn.getUsbPipe();
                outPipe = endPointOut.getUsbPipe();
                
                if(inPipe.isOpen())
                    inPipe.close();
                
                if(outPipe.isOpen())
                    outPipe.close();
                
                inPipe.open();
                outPipe.open();
                
                inPipe.addUsbPipeListener(this);
                outPipe.addUsbPipeListener(this);
             
                new UsbPipeReader().start();
            }
        }
        catch(Exception e)
        {
            LoggerUtil.logThrowable(null,e,Logger.getLogger(CYPRESS_IO_LOGGER));
        }
    }
    
    private String lastOutputMessage=null;
    public void writeMessage(String writeMessage)
    {
        synchronized(device)
        {
            try
            {
                Logger.getLogger(CYPRESS_IO_LOGGER).log(Level.INFO, "Write Line to Cypress: " + writeMessage);
                byte[] message=(writeMessage).getBytes("us-ascii");
                lastOutputMessage=writeMessage;
                
                //TODO - ISTO PROVAVELMENTE VAI DAR ASNEIRA...SE CALHAR É PRECISO UM WAIT OU REPETIR DUAS VEZES O COMANDO
                outPipe.syncSubmit(message);
                //ISTO É MTO ESTUPIDO...MAS FUNCIONA...
                outPipe.syncSubmit(message);
            }
            catch(Exception e)
            {
                LoggerUtil.logThrowable("Unable to write command to cypress...",e,Logger.getLogger(CYPRESS_IO_LOGGER));
            }
        }
        
    }
    
    public class UsbPipeReader extends Thread
    {
        private boolean exit=false;
        private Thread currentThread=null;
        
        
        public UsbPipeReader()
        {
            super();
            setDaemon(true);
        }
        
        public void run()
        {
            while(!exit)
            {
                try
                {
                    inBuffer = new byte[inPipe.getUsbEndpoint().getUsbEndpointDescriptor().wMaxPacketSize()];
                    inPipe.syncSubmit(inBuffer);
                    sleep(readerSleep);
                    
                }
                catch(Exception e)
                {
                    LoggerUtil.logThrowable("Unable to read line from Cypress serial port...",e,Logger.getLogger(CYPRESS_IO_LOGGER));
                    currentThread=null;
                    return;
                }
                
                currentThread=null;
            }
            
        }
        
        public void exitNow()
        {
            exit=true;
            
            if(currentThread!=null)
            {
                try
                {
                    currentThread.join(1000);
                }catch(Exception e)
                {}
            }
        }
        
    }
    
    private void processIncomingLine(String lineRead)
    {
        if(lineRead==null) 
            return;
        
        lineRead = lineRead.trim();
        
        int commandpos=lineRead.indexOf(" ");
        CypressCommand inCommand;
        if(commandpos!=-1)
        {
            inCommand = new CypressCommand(lineRead.substring(0,commandpos));
            inCommand.setCommand(lineRead.substring(commandpos+1));            
        }
        else
        {
            inCommand = new CypressCommand(lineRead);
        }
        
        if(inCommand.getCommandIdentifier().equals(AbstractCypressDriver.START_STRING))
            readerSleep = 100;
        else if(inCommand.getCommandIdentifier().equals(AbstractCypressDriver.CONFIG_NOT_DONE_STRING))
            readerSleep = 2000;
        
        fireCypressCommandListenerHandleCypressCommand(inCommand);
    }
    
    /** Registers CypressCommandListener to receive events.
     * @param listener The listener to register.
     */
    public synchronized void setCypressCommandListener(CypressCommandListener listener)
    {
        this.listener=listener;
    }
    
    /** Removes CypressCommandListener from the list of listeners.
     * @param listener The listener to remove.
     */
    public synchronized void removeCypressCommandListener()
    {
        this.listener=null;
    }
    
    /** Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireCypressCommandListenerHandleCypressCommand(CypressCommand event)
    {
        if (listener!=null)
        {
            listener.handleCypressCommand(event);
        }
        
    }
    
    
    public void shutdown()
    {
        /*Logger.getLogger(Cypress_IO_LOGGER).log(Level.INFO, "Shutting down port...");
        //sPort.removeEventListener();
        exit=true;
        if(currentSerialPortReader!=null)
            currentSerialPortReader.exitNow();
         
        if(sPort!=null)
        {
            Logger.getLogger(Cypress_IO_LOGGER).log(Level.INFO, "Closing sPort...");
            sPort.close();
            Logger.getLogger(Cypress_IO_LOGGER).log(Level.INFO, "Closed sPort...");
        }
         
        Logger.getLogger(Cypress_IO_LOGGER).log(Level.INFO, "Shutted down port...");*/
    }
    
    /** Getter for property waitForEcho.
     * @return Value of property waitForEcho.
     *
     */
    public boolean isWaitForEcho()
    {
        return this.waitForEcho;
    }
    
    /** Setter for property waitForEcho.
     * @param waitForEcho New value of property waitForEcho.
     *
     */
    public void setWaitForEcho(boolean waitForEcho)
    {
        this.waitForEcho = waitForEcho;
    }
    
    
    /** Holds value of property waitForEcho. */
    private boolean waitForEcho = true;
    
    public void reopen()
    {
        /*synchronized(sPort)
        {
            setPort(sPort);
        }*/
    }
    
    public void dataEventOccurred(javax.usb.event.UsbDeviceDataEvent usbDeviceDataEvent)
    {
        Logger.getLogger(CYPRESS_IO_LOGGER).log(Level.INFO, "Received an usbDeviceDataEvent");
    }
    
    public void dataEventOccurred(javax.usb.event.UsbPipeDataEvent usbPipeDataEvent)
    {
        Logger.getLogger(CYPRESS_IO_LOGGER).log(Level.INFO, "Received an usbPipeDataEvent");
        
        String data = new String(usbPipeDataEvent.getData());
        if(data == null || data.equals(lastOutputMessage))
            return;        
        
        //TODO - possivel fonte de erro...        
        /*if(usbPipeDataEvent.getUsbPipe() != inPipe)
            return;*/
                
        Logger.getLogger(CYPRESS_IO_LOGGER).log(Level.INFO, data);
        
        processIncomingLine(new String(usbPipeDataEvent.getData()));
    }
    
    public void errorEventOccurred(javax.usb.event.UsbDeviceErrorEvent usbDeviceErrorEvent)
    {
        Logger.getLogger(CYPRESS_IO_LOGGER).log(Level.INFO, "Received an usbDeviceErrorEvent");
    }
    
    public void errorEventOccurred(javax.usb.event.UsbPipeErrorEvent usbPipeErrorEvent)
    {
        Logger.getLogger(CYPRESS_IO_LOGGER).log(Level.INFO, "Received an usbPipeErrorEvent");
    }
    
    public void usbDeviceDetached(javax.usb.event.UsbDeviceEvent usbDeviceEvent)
    {
        Logger.getLogger(CYPRESS_IO_LOGGER).log(Level.INFO, "Received an usbDeviceEvent");
    }
    
}
