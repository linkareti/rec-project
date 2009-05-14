package pt.utl.ist.elab.driver.usb.cypress;



import java.util.logging.Level;
import java.util.logging.LogManager;
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
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.IConditionDecisor;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.threading.WaitForConditionResult;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;


public abstract class AbstractCypressDriver extends BaseDriver implements CypressFinderListener, CypressCommandListener
{
    protected static String CYPRESS_DRIVER_LOGGER="CypressDriver.Logger";
    
    static
    {
        Logger l=LogManager.getLogManager().getLogger(CYPRESS_DRIVER_LOGGER);
        if(l==null)
        {
            LogManager.getLogManager().addLogger(Logger.getLogger(CYPRESS_DRIVER_LOGGER));
        }
    }
        
    public static String START_STRING="STARTED";
    public static String CONFIG_OUT_STRING="cfg";
    public static String CONFIG_ACCEPTED_STRING="CONFIG_START_ACCEPTED";
    public static String CONFIG_NOT_DONE_STRING="CONFIG_START_NOT_DONE";
    public static String ID_STR="EXP_Cypress_V0.1";
    
    
    public static byte IN_2_BUF = 1;
    public static byte OUT_2_BUF = 2;
    public static byte IN_4_BUF = 3;
    public static byte OUT_4_BUF = 4;private short vendorID = (short)0x0547;
    private short productID = (short)0x2131;
    private short interfaceNumber = (short)0;
    private short alternateSetting = (short)1;
    private byte inputChannelNumber = IN_2_BUF;
    private byte outputChannelNumber = OUT_2_BUF;
    
    private BaseCypressIO cypressIO = null;
    private CypressFinder cypressFinder=new CypressFinder();
    private EventQueue cypressCommands;
    protected AbstractCypressDataSource dataSource=null;
    private int timeOurPerUSB = 1000;
    
    public AbstractCypressDriver()
    {
        cypressFinder = new CypressFinder();
        cypressFinder.addCypressFinderListener(this);
        cypressCommands=new EventQueue(new CommandDispatcher());
    }
    
    public byte getInputChannelNumber()
    {
        return cypressFinder.getInputChannelNumber();
    }
    
    public void setInputChannelNumber(byte inputChannelNumber)
    {
        cypressFinder.setInputChannelNumber(inputChannelNumber);
    }
    
    public byte getOutputChannelNumber()
    {
        return cypressFinder.getOutputChannelNumber();
    }
    
    public void setOutputChannelNumber(byte outputChannelNumber)
    {
        cypressFinder.setOutputChannelNumber(outputChannelNumber);
    }
    
    public short getVendorID()
    {
        return cypressFinder.getVendorID();
    }
    
    public void setVendorID(short vendorID)
    {
        cypressFinder.setVendorID(vendorID);
    }
    
    public short getProductID()
    {
        return cypressFinder.getProductID();
    }
    
    public void setProductID(short productID)
    {
        cypressFinder.setProductID(productID);
    }
    
    public short getInterfaceNumber()
    {
        return cypressFinder.getInterfaceNumber();
    }
    
    public void setInterfaceNumber(short interfaceNumber)
    {
        cypressFinder.setInterfaceNumber(interfaceNumber);
    }
    
    public short getAlternateSetting()
    {
        return cypressFinder.getAlternateSetting();
    }
    
    public void setAlternateSetting(short alternateSetting)
    {
        cypressFinder.setAlternateSetting(alternateSetting);
    }
    
    public String getCypressIdentifier()
    {
        return cypressFinder.getCypressIdentifier();
    }

    public void setCypressIdentifier(String CypressIdentifier)
    {
        cypressFinder.setCypressIdentifier(CypressIdentifier);
    }
    
    protected void loadCommandHandlers()
    {
        CypressTranslatorProcessorManager.initCypressProcessorTranslator("pt.utl.ist.elab.driver.usb.cypress.transproc.processors.CypressStartProcessor");
        CypressTranslatorProcessorManager.initCypressProcessorTranslator("pt.utl.ist.elab.driver.usb.cypress.transproc.processors.CypressConfiguredProcessor");
        CypressTranslatorProcessorManager.initCypressProcessorTranslator("pt.utl.ist.elab.driver.usb.cypress.transproc.processors.CypressNotConfiguredProcessor");
        CypressTranslatorProcessorManager.initCypressProcessorTranslator("pt.utl.ist.elab.driver.usb.cypress.transproc.processors.CypressIDProcessor");
        loadExtraCommandHandlers();
    }
    
    protected abstract void loadExtraCommandHandlers();
    
    /*** Base Driver impl ***/
    public void extraValidateConfig(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException
    {
        //silent noop - main validation is OK
    }
    
    public String getDriverUniqueID()
    {
        return ID_STR;
    }
    
    public void setDriverUniqueID(String IdStr)
    {
        ID_STR=IdStr;
        cypressFinder.setCypressIdentifier(IdStr);
    }
    
    public void init(HardwareInfo info)
    {
        
        if(cypressIO != null)
            cypressIO.shutdown();
        
        cypressIO = null;
        cypressFinder.startSearch();
        try
        {
            WaitForConditionResult.waitForConditionTrue(
            new AbstractConditionDecisor()
            {
                public int getConditionResult()
                {
                    synchronized(cypressFinder)
                    {
                        if(cypressIO != null)
                        {
                            return IConditionDecisor.CONDITION_MET_TRUE;
                        }
                    }
                    return IConditionDecisor.CONDITION_NOT_MET;
                }
            },120*1000, timeOurPerUSB);
        }catch(TimedOutException e)
        {
            LoggerUtil.logThrowable("Couldn't find port for Cypress in "+120+"s",e,Logger.getLogger(CYPRESS_DRIVER_LOGGER));
            
        }
        
        if(cypressIO!=null)
        {
            fireIDriverStateListenerDriverInited();
        }
        else
            fireIDriverStateListenerDriverShutdown();
    }
    
    protected HardwareAcquisitionConfig config=null;
    protected HardwareInfo info=null;
    
    public void reset(HardwareInfo info) throws IncorrectStateException
    {
        fireIDriverStateListenerDriverReseting();
        cypressIO.reopen();
        fireIDriverStateListenerDriverReseted();
    }
    
    public void shutdown()
    {
        if(cypressIO!=null)
            cypressIO.shutdown();
        
        super.shutDownNow();
    }
    
    public IDataSource start(HardwareInfo info) throws IncorrectStateException
    {
        dataSource=initDataSource();
        dataSource.setAcquisitionHeader(getAcquisitionHeader());
        
        if(dataSource!=null)
        {
            try
            {
                startNow();
            }catch(TimedOutException e)
            {
                LoggerUtil.logThrowable("Error on start... - rethrowing IncorrectStateException!",e,Logger.getLogger(CYPRESS_DRIVER_LOGGER));
                //I'll try to reopen the Cypress...this way it il not get stuck here...at least I hope so!
                fireIDriverStateListenerDriverReseting();
                cypressIO.reopen();
                fireIDriverStateListenerDriverReseted();
                throw new IncorrectStateException();
            }
            return dataSource;
        }
        else
            return null;
        
        
    }
    
    public abstract void startNow() throws TimedOutException;
    
    public IDataSource startOutput(HardwareInfo info, IDataSource source) throws IncorrectStateException
    {
        //big silent noop - does nothing -
        //current version does not support startOutput
        return null;
    }
    
    
    public void stop(HardwareInfo info) throws IncorrectStateException
    {
        fireIDriverStateListenerDriverStoping();
        cypressIO.reopen();
        fireIDriverStateListenerDriverStoped();
    }
    
    
    public void stopDataSource()
    {
        cypressCommands.addEvent(new StopEvent());
    }
    
    
    /** CypressFinderListener impl **/
    public void cypressFound(UsbDevice device)
    {
        synchronized(cypressFinder)
        {
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
    
    
    public void handleCypressCommand(CypressCommand command)
    {
        CypressProcessor processor=command.getProcessor();
        if(processor==null)
        {
            Logger.getLogger(CYPRESS_DRIVER_LOGGER).log(Level.INFO,"Didn't get a processor for command " + command.getCommandIdentifier());
            Logger.getLogger(CYPRESS_DRIVER_LOGGER).log(Level.INFO,"Droping the command, as it is not understood!");
            return;
        }
        
        if(processor.isData() && dataSource!=null)
        {
            if(!processor.process(command))
            {
                Logger.getLogger(CYPRESS_DRIVER_LOGGER).log(Level.INFO,"Couldn't process data command in CommandDispatcher... Strange...");
                return;
            }
            
            if(dataSource!=null)
                dataSource.processDataCommand(command);
            else
                Logger.getLogger(CYPRESS_DRIVER_LOGGER).log(Level.INFO,"No data source to process command...");
            
            //CypressCommands.addEvent(command);
        }
        else
        {
            if(!processor.process(command))
            {
                Logger.getLogger(CYPRESS_DRIVER_LOGGER).log(Level.INFO,"The processor didn't understand the message... Ooooppsss... Message was : "+command.getCommand()+" !");
                return;
            }
            processCommand(command);
        }
    }
    
    public abstract AbstractCypressDataSource initDataSource();
    public abstract HardwareAcquisitionConfig getAcquisitionHeader();
    public abstract void processCommand(CypressCommand cmd);
    
    public class CommandDispatcher implements EventQueueDispatcher
    {
        
        public void dispatchEvent(Object evt)
        {
            if(evt instanceof StopEvent)
            {
                if(dataSource!=null)
                    dataSource.stopNow();
            }
            else
            {
                Logger.getLogger(CYPRESS_DRIVER_LOGGER).log(Level.INFO,"CommandDispatcher doesn't know how to deal with other than CypressCommand's");
            }
        }
        
        public int getPriority()
        {
            return Thread.NORM_PRIORITY+2;
        }
        
    }
    
    /** Getter for property waitForEcho.
     * @return Value of property waitForEcho.
     *
     */
    public boolean isWaitForEcho()
    {
        return cypressFinder.isWaitForEcho();
    }
    
    /** Setter for property waitForEcho.
     * @param waitForEcho New value of property waitForEcho.
     *
     */
    public void setWaitForEcho(boolean waitForEcho)
    {
        cypressFinder.setWaitForEcho(waitForEcho);
    }
    
    protected void writeMessage(String message)
    {
        if(cypressIO!=null)
            cypressIO.writeMessage(message);
    }
}

