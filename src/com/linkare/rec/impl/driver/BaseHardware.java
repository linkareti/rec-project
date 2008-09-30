/*
 * SimulationHardwareImpl.java
 *
 * Created on 24 de Junho de 2002, 19:52
 */

package com.linkare.rec.impl.driver;

import java.io.InputStream;
import java.net.URL;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.acquisition.DataClient;
import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.DataReceiver;
import com.linkare.rec.acquisition.Hardware;
import com.linkare.rec.acquisition.HardwareOperations;
import com.linkare.rec.acquisition.HardwarePOATie;
import com.linkare.rec.acquisition.HardwareState;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.events.HardwareStateChangeEvent;
import com.linkare.rec.impl.exceptions.IncorrectStateExceptionConstants;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;
import com.linkare.rec.impl.utils.HardwareBinder;
import com.linkare.rec.impl.utils.HardwareInfoXMLReader;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.wrappers.DataClientWrapper;
import com.linkare.rec.impl.wrappers.DataProducerWrapper;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class BaseHardware implements HardwareOperations,BaseDataProducerListener
{
    private static String BASE_HARDWARE_LOGGER="BaseHardware.Logger";
    
    static
    {
	Logger l=LogManager.getLogManager().getLogger(BASE_HARDWARE_LOGGER);
	if(l==null)
	{
	    LogManager.getLogManager().addLogger(Logger.getLogger(BASE_HARDWARE_LOGGER));
	}
    }
    
    private HardwareBinder refBinder=new HardwareBinder();
    
    private IDriver driver=null;
    
    private HardwareInfo info=null;
    
    private HardwareAcquisitionConfig config=null;
    
    private BaseDataProducer dataProducerInEffect= null;
    private Vector<BaseDataProducer> oldDataProducers=new Vector<BaseDataProducer>();
    
    protected DataClientWrapper dataClient=null;
    
    private Hardware _this=null;
    private Hardware _this()
    {
	if(_this!=null) return _this;
	
	try
	{
	    HardwarePOATie thisRemoteObject=(HardwarePOATie)ORBBean.getORBBean().registerAutoIdRootPOAServant(com.linkare.rec.acquisition.Hardware.class,this,null);
	    
	    _this=thisRemoteObject._this();
	    
	    Logger.getLogger(BASE_HARDWARE_LOGGER).log(Level.INFO,"Registering this hardware with name "+"com/linkare/rec/hardwares/"+driver.getDriverUniqueID());
            
	    refBinder.setHardware(_this);
	    
	    return _this;
	}
	catch(Exception e)
	{
	    LoggerUtil.logThrowable("Couldn't register BaseHardware with ORB...",e,Logger.getLogger(BASE_HARDWARE_LOGGER));
	    try
	    {driver.shutdown();}catch(Exception ignored)
	    {}
	    driver=null;
	    System.gc();
	    System.exit(-1);
	    return null;
	}
	
    }
    
    
    private class StateMachine implements IDriverStateListener
    {
	public void driverInited()
	{startup();}
	public void driverConfiguring()
	{setHardwareState(HardwareState.CONFIGURING);}
	public void driverConfigured()
	{setHardwareState(HardwareState.CONFIGURED);}
	public void driverStarting()
	{setHardwareState(HardwareState.STARTING);}
	public void driverStarted()
	{setHardwareState(HardwareState.STARTED);}
	public void driverStoping()
	{setHardwareState(HardwareState.STOPING);}
	public void driverStoped()
	{setHardwareState(HardwareState.STOPED);}
	public void driverReseting()
	{setHardwareState(HardwareState.RESETING);}
	public void driverReseted()
	{setHardwareState(HardwareState.RESETED);}
	public void driverShutdown()
	{shutdown();}
    }
    
    
    EventQueue eventQueue=null;
    public BaseHardware()
    {
	eventQueue=new EventQueue(new BaseHardwareDataClientDispatcher());
    }
    
    
    /** Creates a new instance of SimulationHardwareImpl */
    public BaseHardware(IDriver driver)
    {
	this();
	this.setDriver(driver);
    }
    
    /** Getter for property driver.
     * @return Value of property driver.
     */
    public IDriver getDriver()
    {
	return this.driver;
    }
    
    StateMachine stateMachine=new StateMachine();
    
    /** Setter for property driver.
     * @param driver New value of property driver.
     */
    public void setDriver(IDriver driver)
    {
	
	if(this.driver!=null)
	{
	    this.driver.removeIDriverStateListener(stateMachine);
	}
	this.driver = driver;
	if(driver==null) shutdown();
	else
	{
	    try
	    {
		driver.addIDriverStateListener(stateMachine);
	    }catch(java.util.TooManyListenersException e)
	    {
		LoggerUtil.logThrowable("Couldn't register IDriverStateListener with Driver...",e,Logger.getLogger(BASE_HARDWARE_LOGGER));
	    }
	    readInHardwareInfo(driver.getHardwareInfo());
	    driver.init(getHardwareInfo());
	}
	
    }
    
    
    private void shutdown()
    {
	//do nothing for now... should unregister client, or somethin'
    }
    
    private void startup()
    {
	Logger.getLogger(BASE_HARDWARE_LOGGER).log(Level.INFO,"Driver started up... Register with ORB...");
	_this();
    }
    
    
    
    
    private HardwareState state=HardwareState.UNKNOWN;
    public HardwareState getHardwareState()
    {
	synchronized(state)
	{
	    return state;
	}
    }
    
    public void setHardwareState(HardwareState state)
    {
	synchronized(state)
	{
	    if(!this.state.equals(state))
	    {
		this.state=state;
		eventQueue.addEvent(new HardwareStateChangeEvent(state));
	    }
	}
    }
    
    
    private void readInHardwareInfo(Object o)
    {
	if(o==null) return;
	try
	{
	    if(o instanceof HardwareInfo)
	    {
		setHardwareInfo((HardwareInfo) o);
	    }
	    else if(o instanceof String)
	    {
		setHardwareInfo(HardwareInfoXMLReader.readHardwareInfo((String)o));
	    }
	    else if(o instanceof URL)
	    {
		setHardwareInfo(HardwareInfoXMLReader.readHardwareInfo((URL)o));
	    }
	    else if(o instanceof InputStream)
	    {
		setHardwareInfo(HardwareInfoXMLReader.readHardwareInfo((InputStream)o));
	    }
	    else
		throw new RuntimeException("Not possible to read HardwareInfo");
	}catch(Exception e)
	{
	    LoggerUtil.logThrowable("Couldn't read HardwareInfo from Driver",e,Logger.getLogger(BASE_HARDWARE_LOGGER));
	}
    }
    
    
    public void setHardwareInfo(HardwareInfo info)
    {
	this.info=info;
    }
    
    public HardwareInfo getHardwareInfo()
    {
	return info;
    }
    
    
    public DataProducer getDataProducer()
    throws IncorrectStateException, NotAvailableException
    {
	if(dataProducerInEffect==null)
	    throw new com.linkare.rec.acquisition.NotAvailableException();
	
	return dataProducerInEffect._this();
	
    }
    
    public void registerDataClient(DataClient data_client)
    {
	this.dataClient=new DataClientWrapper(data_client);
    }
    
    public DataClient getDataClient()
    {
	return dataClient.getDelegate();
    }
    
    public void configure(HardwareAcquisitionConfig config) throws IncorrectStateException, WrongConfigurationException
    {
	
	if(driver==null)
	    throw new IncorrectStateException(IncorrectStateExceptionConstants.UNKNOWN_ERROR,state,HardwareState.CONFIGURING);
	
	if(!state.equals(HardwareState.CONFIGURED) && !state.equals(HardwareState.STOPED) && !state.equals(HardwareState.RESETED))
	    throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,state,HardwareState.CONFIGURING);
	
	try
	{
	driver.config(config,getHardwareInfo());
	}catch(Exception e)
	{
	    e.printStackTrace();
	}
	
    }
    
    public DataProducer start(DataReceiver receiver)
    throws IncorrectStateException
    {        
	if(driver==null)
	    throw new IncorrectStateException(IncorrectStateExceptionConstants.UNKNOWN_ERROR,state,HardwareState.STARTING);
	
	if(!state.equals(HardwareState.STOPED) && !state.equals(HardwareState.CONFIGURED))
	    throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,state,HardwareState.STARTING);
	
	try
	{
	    IDataSource ds=driver.start(getHardwareInfo());
	    dataProducerInEffect=new BaseDataProducer(ds,receiver);
	    dataProducerInEffect.addBaseDataProducerListener(this);
	    
	    //TODO CHECK WITH JP
	    //dataProducerInEffect.dataSourceStateStarted();
	    
	    oldDataProducers.add(dataProducerInEffect);
	    return dataProducerInEffect._this();
	}catch(IncorrectStateException e)
	{
	    throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,getHardwareState(),HardwareState.STARTING);
	}
	
    }
    
    public DataProducer startOutput(DataReceiver receiver, DataProducer data_source)
    throws IncorrectStateException
    {
	if(driver==null)
	    throw new IncorrectStateException(IncorrectStateExceptionConstants.UNKNOWN_ERROR,state,HardwareState.STARTING);
	
	if(!state.equals(HardwareState.STOPED) && !state.equals(HardwareState.CONFIGURED))
	    throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,state,HardwareState.STARTING);
	
	try
	{
	    IDataSource ds=driver.startOutput(getHardwareInfo(),new DataProducer2IDataSourceAdapter(new DataProducerWrapper(data_source)));
	    dataProducerInEffect=new BaseDataProducer(ds,receiver);
	    oldDataProducers.add(dataProducerInEffect);
	    dataProducerInEffect.addBaseDataProducerListener(this);
	    return dataProducerInEffect._this();
	}catch(IncorrectStateException e)
	{
	    throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,getHardwareState(),HardwareState.STARTING);
	}
    }
    
    
    
    public void stop()
    throws IncorrectStateException
    {
	if(driver==null)
	    throw new IncorrectStateException(IncorrectStateExceptionConstants.UNKNOWN_ERROR,state,HardwareState.STOPING);
	
	if(!state.equals(HardwareState.STARTED))
	    throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,state,HardwareState.STOPING);
	
	Logger.getLogger(BASE_HARDWARE_LOGGER).log(Level.INFO,"Reseting Driver...");
	try
	{
	    if(this.dataProducerInEffect!=null)
	    {
		dataProducerInEffect.stopNow();		
		
		//TODO CHECK WITH JP
		dataProducerInEffect.dataSourceStateStoped();
	    }
	    
	    driver.stop(getHardwareInfo());
	}catch(IncorrectStateException e)
	{
	    throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,getHardwareState(),HardwareState.STOPING);
	}
    }
    
    
    
    public void reset()
    throws IncorrectStateException
    {
	if(driver==null)
	    throw new IncorrectStateException(IncorrectStateExceptionConstants.UNKNOWN_ERROR,state,HardwareState.RESETING);
	
	if(!state.equals(HardwareState.STARTED) && !state.equals(HardwareState.CONFIGURED) && !state.equals(HardwareState.UNKNOWN))
	    throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,state,HardwareState.RESETING);
	
	Logger.getLogger(BASE_HARDWARE_LOGGER).log(Level.INFO,"Reseting Driver...");
	try
	{
	    driver.reset(getHardwareInfo());
	}catch(IncorrectStateException e)
	{
	    throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,getHardwareState(),HardwareState.RESETING);
	}
    }
    
    public void baseDataProducerIsEmpty(BaseDataProducer producer)
    {
	if(producer!=null)
	    oldDataProducers.remove(producer);
	
	if(producer==dataProducerInEffect)
	    dataProducerInEffect=null;
    }
    
    
    
    private class BaseHardwareDataClientDispatcher implements EventQueueDispatcher
    {
	public void dispatchEvent(Object o)
	{
	    try
	    {
		if(o instanceof HardwareStateChangeEvent)
		{
		    if(dataClient!=null)
			dataClient.hardwareStateChange(((HardwareStateChangeEvent)o).getNewState());
		}
	    }
	    catch(Exception e)
	    {
		LoggerUtil.logThrowable("Error comunicating HardwareStateChange to dataClient",e,Logger.getLogger(BASE_HARDWARE_LOGGER));
	    }
	}
	
	public int getPriority()
	{
	    return Thread.NORM_PRIORITY;
	}
	
    }
    
}
