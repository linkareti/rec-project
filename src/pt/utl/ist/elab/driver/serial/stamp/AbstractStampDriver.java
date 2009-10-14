package pt.utl.ist.elab.driver.serial.stamp;

/**
 * 
 * ->Changed by André on 26/07/04:
 *    Added suport to Basic Atom. Now we can control RTS, DTR and echo
 */

import gnu.io.*;
import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.threading.*;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;
import com.linkare.rec.acquisition.*;
import com.linkare.rec.data.config.*;
import com.linkare.rec.data.metadata.*;
import com.linkare.rec.impl.logging.*;
import java.util.logging.*;
import pt.utl.ist.elab.driver.serial.stamp.transproc.*;


public abstract class AbstractStampDriver extends BaseDriver implements StampFinderListener, StampCommandListener
{
    protected static String STAMP_DRIVER_LOGGER="StampDriver.Logger";
    
    static
    {
	Logger l=LogManager.getLogManager().getLogger(STAMP_DRIVER_LOGGER);
	if(l==null)
	{
	    LogManager.getLogManager().addLogger(Logger.getLogger(STAMP_DRIVER_LOGGER));
	}
    }
    
    public static String START_STRING="STARTED";
    public static String CONFIG_OUT_STRING="cfg";
    public static String CONFIG_ACCEPTED_STRING="CONFIG_START_ACCEPTED";
    public static String CONFIG_NOT_DONE_STRING="CONFIG_START_NOT_DONE";
    public static String ID_STR="EXP_STAMP_V0.1";
    
    private BaseStampIO stampIO = null;
    private StampFinder stampFinder=new StampFinder();
    private EventQueue stampCommands;
    protected AbstractStampDataSource dataSource=null;
    
    public AbstractStampDriver()
    {
	stampFinder = new StampFinder();
	stampFinder.addStampFinderListener(this);
	stampCommands=new EventQueue(new CommandDispatcher());
    }
    
    protected void loadCommandHandlers()
    {
	StampTranslatorProcessorManager.initStampProcessorTranslator("pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampStartProcessor");
	StampTranslatorProcessorManager.initStampProcessorTranslator("pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampConfiguredProcessor");
	StampTranslatorProcessorManager.initStampProcessorTranslator("pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampNotConfiguredProcessor");
	StampTranslatorProcessorManager.initStampProcessorTranslator("pt.utl.ist.elab.driver.serial.stamp.transproc.processors.StampIdProcessor");
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
	stampFinder.setStampIdentifier(IdStr);
    }
    
    public void init(HardwareInfo info)
    {
	
	if(stampIO!=null)
	    stampIO.shutdown();
	
	stampIO=null;
	stampFinder.startSearch();
	try
	{
	    WaitForConditionResult.waitForConditionTrue(
	    new AbstractConditionDecisor()
	    {
		public int getConditionResult()
		{
		    synchronized(stampFinder)
		    {
			if(stampIO!=null)
			{
			    return IConditionDecisor.CONDITION_MET_TRUE;
			}
		    }
		    return IConditionDecisor.CONDITION_NOT_MET;
		}
	    },120*1000,stampFinder.getTimeOutPerPort());
	}catch(TimedOutException e)
	{
	    LoggerUtil.logThrowable("Couldn't find port for STAMP in "+120+"s",e,Logger.getLogger(STAMP_DRIVER_LOGGER));
	    
	}
	
	if(stampIO!=null)
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
	stampIO.reopen();
	fireIDriverStateListenerDriverReseted();
    }
    
    public void shutdown()
    {
	if(stampIO!=null)
	    stampIO.shutdown();
	
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
		LoggerUtil.logThrowable("Error on start... - rethrowing IncorrectStateException!",e,Logger.getLogger(STAMP_DRIVER_LOGGER));
                //I'll try to reopen the stamp...this way it il not get stuck here...at least I hope so!
                fireIDriverStateListenerDriverReseting();
                stampIO.reopen();
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
	stampIO.reopen();
	fireIDriverStateListenerDriverStoped();
    }
    
    
    public void stopDataSource()
    {
	stampCommands.addEvent(new StopEvent());
    }
    
    
    /** StampFinderListener impl **/
    public void stampFound(SerialPort sPort)
    {
	synchronized(stampFinder)
	{
	    stampIO=new BaseStampIO();
	    stampIO.setApplicationNameLockPort(stampFinder.getApplicationNameLockPort());
	    stampIO.setPortBaudRate(stampFinder.getPortBaudRate());
	    stampIO.setPortDataBits(stampFinder.getPortDataBits());
	    stampIO.setPortParity(stampFinder.getPortParity());
	    stampIO.setPortStopBits(stampFinder.getPortStopBits());
            stampIO.setDTR(stampFinder.isDTR());
            stampIO.setRTS(stampFinder.isRTS());
            stampIO.setWaitForEcho(stampFinder.isWaitForEcho());
	    stampIO.setStampCommandListener(this);
	    stampIO.setPort(sPort);
	}
    }
    
    
    public void handleStampCommand(StampCommand command)
    {
    Logger.getLogger("handleStampCommand: command=" + command.getCommandIdentifier() + " processor=" + command.getProcessor() == null ? "null" : command.getProcessor().getClass().getName());
	StampProcessor processor=command.getProcessor();
	if(processor==null)
	{
	    Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,"Didn't get a processor for command " + command.getCommandIdentifier());
	    Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,"Droping the command, as it is not understood!");
	    return;
	}
	
	//a little temporary hack to ignore TEMP lines
	if (command.getCommandIdentifier().equalsIgnoreCase("TEMP"))
		return;
	
	if(processor.isData() && dataSource!=null)
	{
	    if(!processor.process(command))
	    {
	        Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,"Couldn't process data command in CommandDispatcher... Strange...");
	        return;
	    }
		
	    if(dataSource!=null)
		dataSource.processDataCommand(command);
	    else
	        Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,"No data source to process command...");
		
	    //stampCommands.addEvent(command);
	}
	else
	{
	    if(!processor.process(command))
	    {
		Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,"The processor didn't understand the message... Ooooppsss... Message was : "+command.getCommand()+" !");
		return;
	    }
	    processCommand(command);
	}
    }
    
    public abstract AbstractStampDataSource initDataSource();
    public abstract HardwareAcquisitionConfig getAcquisitionHeader();
    public abstract void processCommand(StampCommand cmd);
    
    public class CommandDispatcher implements EventQueueDispatcher
    {
	
	public void dispatchEvent(Object evt)
	{
	    /*if(evt instanceof StampCommand)
	    {
		StampCommand cmd=(StampCommand)evt;
		StampProcessor processor=cmd.getProcessor();
		if(processor==null || !processor.process(cmd))
		{
		    Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,"Couldn't process data command in CommandDispatcher... Strange...");
		    return;
		}
		
		if(cmd.isData() && dataSource!=null)
		    dataSource.processDataCommand(cmd);
		else
		    Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,"CommandDispatcher doesn't know how to deal with other than Data StampCommand's");
	    }
	    else*/ 
	    if(evt instanceof StopEvent)
	    {
		if(dataSource!=null)
		    //dataSource.setRunning(false);
		    dataSource.stopNow();
	    }
	    else
	    {
		Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,"CommandDispatcher doesn't know how to deal with other than StampCommand's");
	    }
	}
	
	public int getPriority()
	{
	    return Thread.NORM_PRIORITY+2;
	}
	
    }
    
    
    /*** Proxy for StampFinder Props ***/
    
    /** Getter for property applicationNameLockPort.
     * @return Value of property applicationNameLockPort.
     */
    public String getApplicationNameLockPort()
    {
	return stampFinder.getApplicationNameLockPort();
    }
    
    /** Setter for property applicationNameLockPort.
     * @param applicationNameLockPort New value of property applicationNameLockPort.
     */
    public void setApplicationNameLockPort(String applicationNameLockPort)
    {
	stampFinder.setApplicationNameLockPort(applicationNameLockPort);
    }
    
    
    /** Getter for property portParity.
     * @return Value of property portParity.
     */
    public int getPortParity()
    {
	return stampFinder.getPortParity();
    }
    
    /** Setter for property portParity.
     * @param portParity New value of property portParity.
     */
    public void setPortParity(int portParity)
    {
	stampFinder.setPortParity(portParity);
    }
    
    /** Getter for property portBaudRate.
     * @return Value of property portBaudRate.
     */
    public int getPortBaudRate()
    {
	return stampFinder.getPortBaudRate();
    }
    
    /** Setter for property portBaudRate.
     * @param portBaudRate New value of property portBaudRate.
     */
    public void setPortBaudRate(int portBaudRate)
    {
	stampFinder.setPortBaudRate(portBaudRate);
    }
    
    /** Getter for property portDataBits.
     * @return Value of property portDataBits.
     */
    public int getPortDataBits()
    {
	return stampFinder.getPortDataBits();
    }
    
    /** Setter for property portDataBits.
     * @param portDataBits New value of property portDataBits.
     */
    public void setPortDataBits(int portDataBits)
    {
	stampFinder.setPortDataBits(portDataBits);
    }
    
    /** Getter for property portStopBits.
     * @return Value of property portStopBits.
     */
    public int getPortStopBits()
    {
	return stampFinder.getPortStopBits();
    }
    
    /** Setter for property portStopBits.
     * @param portStopBits New value of property portStopBits.
     */
    public void setPortStopBits(int portStopBits)
    {
	stampFinder.setPortStopBits(portStopBits);
    }
    
    /** Getter for property timeOutPerPort.
     * @return Value of property timeOutPerPort.
     */
    public long getTimeOutPerPort()
    {
	return stampFinder.getTimeOutPerPort();
    }
    
    /** Setter for property timeOutPerPort.
     * @param timeOutPerPort New value of property timeOutPerPort.
     */
    public void setTimeOutPerPort(long timeOutPerPort)
    {
	stampFinder.setTimeOutPerPort(timeOutPerPort);
    }

    
    /** Getter for property DTR.
     * @return Value of property DTR.
     *
     */
    public boolean isDTR() {
        return stampFinder.isDTR();
    }
    
    /** Setter for property DTR.
     * @param DTR New value of property DTR.
     *
     */
    public void setDTR(boolean DTR) 
    {
        stampFinder.setDTR(DTR);
    }
    
    /** Getter for property RTS.
     * @return Value of property RTS.
     *
     */
    public boolean isRTS() 
    {
        return stampFinder.isRTS();
    }
    
    /** Setter for property RTS.
     * @param RTS New value of property RTS.
     *
     */
    public void setRTS(boolean RTS) {
        stampFinder.setRTS(RTS);
    }    
    
    
    /** Getter for property waitForEcho.
     * @return Value of property waitForEcho.
     *
     */
    public boolean isWaitForEcho() {
        return stampFinder.isWaitForEcho();
    }
    
    /** Setter for property waitForEcho.
     * @param waitForEcho New value of property waitForEcho.
     *
     */
    public void setWaitForEcho(boolean waitForEcho) {
        stampFinder.setWaitForEcho(waitForEcho);
    }            
    
    protected void writeMessage(String message)
    {
	if(stampIO!=null)
	    stampIO.writeMessage(message);
    }
    
    
}

