/*
 * RobotDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.webrobot;

import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.threading.*;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;
import com.linkare.rec.acquisition.*;
import com.linkare.rec.data.config.*;
import com.linkare.rec.data.metadata.*;
import com.linkare.rec.impl.logging.*;
import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.threading.*; 
import com.linkare.rec.impl.protocols.*;
import java.util.logging.*;
import pt.utl.ist.elab.driver.webrobot.serial.*;

/**
 *
 * @author  Andr�
 */

import pt.utl.ist.elab.driver.webrobot.debug.*;

public class RobotDriver extends BaseDriver
{    
    protected static String WR_DRIVER_LOGGER="WebRobotDriver.Logger";
    
    static
    {
	Logger l=LogManager.getLogManager().getLogger(WR_DRIVER_LOGGER);
	if(l==null)
	{
	    LogManager.getLogManager().addLogger(Logger.getLogger(WR_DRIVER_LOGGER));
	}
    }
    /* Hardware and driver related variables*/
    private static final String APPLICATION_IDENTIFIER = "E-Lab (Robot Driver)";
    private static final String DRIVER_UNIQUE_ID = "ELAB_WEB_ROBOT_V01";
    private static final String HW_VERSION = "0.1";
	    
    private static RobotDriver SingletonDriver = null;    
    
    protected RobotStateMachine dataSource = null;    
    protected HardwareAcquisitionConfig config=null;
    protected HardwareInfo info=null;
    
    protected String flowChartString=null;
        
    /**To comunicate with the robot*/
    private SerialComm serialComm;    
    
    /** Creates a new instance of RobotDriver */
    public RobotDriver() 
    {
    }
    
    
    public static RobotDriver Create()
    {
        if(SingletonDriver==null)
        {
            SingletonDriver = new RobotDriver();	
	}
        return SingletonDriver;
    }
    
    public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException, WrongConfigurationException
    {
        fireIDriverStateListenerDriverConfiguring();
        Logger.getLogger(WR_DRIVER_LOGGER).log(Level.INFO,"Starting config");
	info.validateConfig(config);
        extraValidateConfig(config,info);
	try
	{
            configure(config,info);
        }
        catch(Exception e)
	{
            e.printStackTrace();
            throw new WrongConfigurationException("Erro no config...", 20);
        }
    }    
            
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException 
    {
        this.config=config;
        this.info=info;
        ParameterConfig[] selectedParams=config.getSelectedHardwareParameters();
           
        if(selectedParams!=null)
        {
            /**Why these loops, if you know what you're looking for? Well...I'll 
             *understand this better if I need to look at this later...*/
            ParameterConfig flowParam=null;                
            for(int i=0;i<selectedParams.length;i++)
            {
                if(selectedParams[i].getParameterName().equalsIgnoreCase("FlowChart"))
                {
                    flowParam=selectedParams[i];  
                    flowChartString=flowParam.getParameterValue();
                }			
            }                          
        }	                
        /**I don't want null files!! Why are you sending them?*/
        if(flowChartString==null)
        {
            Logger.getLogger(WR_DRIVER_LOGGER).log(Level.INFO,"Flow chart String is NULL!!!");
            flowChartString="";
        }
        
        /**I really don't give a damn about the number of samples in this experiment,
         *but may be in the future this lines will help me to do other things...*/
        for(int i=0;i<config.getChannelsConfig().length;i++)
        {            
            config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
        }
        fireIDriverStateListenerDriverConfigured();
        Logger.getLogger(WR_DRIVER_LOGGER).log(Level.INFO,"Configured");
    }
    
    public void extraValidateConfig(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException 
    {
        /**not going to use*/
    }
    
    public String getDriverUniqueID() 
    {
        return DRIVER_UNIQUE_ID;
    }
    
    public void init(HardwareInfo info)     
    {        
        /**Open the serial connection*/
        serialComm=new pt.utl.ist.elab.driver.webrobot.serial.SerialComm();
        Logger.getLogger(WR_DRIVER_LOGGER).log(Level.INFO,"The serial port is opened!");
        dataSource=new RobotStateMachine(this);                        
        fireIDriverStateListenerDriverInited();        
    }
    
    public void reset(HardwareInfo info) throws IncorrectStateException 
    {
        /**Reset in not supported in webrobot*/
    }
    
    public void shutdown() 
    {
        if(dataSource!=null)
        {
            dataSource.shutdown();
        }
        super.shutDownNow();
    }
    
    public IDataSource start(HardwareInfo info) throws IncorrectStateException 
    {
        Logger.getLogger(WR_DRIVER_LOGGER).log(Level.INFO,"Starting!");
        fireIDriverStateListenerDriverStarting();    
        dataSource=new RobotStateMachine(this);                
        dataSource.setAtStartPosition();     
        while(!dataSource.isAtStartPosition())
        {
            try
            {
                Thread.currentThread().sleep(2000);
            }
            catch(InterruptedException ie)
            {
            }
        }
        Logger.getLogger(WR_DRIVER_LOGGER).log(Level.INFO,"The robot is at the start position");
        dataSource.setFlowString(flowChartString);                  
        dataSource.setAcquisitionHeader(config);		       
        dataSource.startProduction();  
        Logger.getLogger(WR_DRIVER_LOGGER).log(Level.INFO,"Ready to start");
        fireIDriverStateListenerDriverStarted();           
        Logger.getLogger(WR_DRIVER_LOGGER).log(Level.INFO,"Started!");
        //dataSource.setRunning(true);           
        return dataSource;
    }
    
    public IDataSource startOutput(HardwareInfo info, IDataSource source) throws IncorrectStateException 
    {
        /**Don't know what is startOutput...*/
        return null;
    }
    
    public void stop(HardwareInfo info) throws IncorrectStateException
    {
        dataSource.stopProduction();
    }    
    
    public Object getHardwareInfo() 
    {
        fireIDriverStateListenerDriverReseting();                
	
	
	String baseHardwareInfoFile="recresource://pt/utl/ist/elab/driver/webrobot/WebRobotBaseHardwareInfo.xml";
	String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.WebRobot.HardwareInfo"),baseHardwareInfoFile);
	
	if(prop.indexOf("://")==-1)
	    prop="file:///" + System.getProperty("user.dir") + "/" + prop;
	
	java.net.URL url=null;
	try
	{
	    url=ReCProtocols.getURL(prop);
            fireIDriverStateListenerDriverReseted();            
	}
        catch(java.net.MalformedURLException e)
	{
	    LoggerUtil.logThrowable("Unable to load resource: " + prop,e,Logger.getLogger("WebRobot"));
	    try
	    {
		url=new java.net.URL(baseHardwareInfoFile);
                fireIDriverStateListenerDriverReseted();            
	    }
            catch(java.net.MalformedURLException e2)
	    {
		LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile,e2,Logger.getLogger("WebRobot"));
	    }
	}		        
	return url;	
    }    
    
    public void setStoping()
    {
        Logger.getLogger(WR_DRIVER_LOGGER).log(Level.INFO,"Stoping");
        fireIDriverStateListenerDriverStoping();
        //dataSource.setRunning(false);                
    }
    
    public void setStoped()
    {
        Logger.getLogger(WR_DRIVER_LOGGER).log(Level.INFO,"Stoped");
        fireIDriverStateListenerDriverStoped(); 
    }    
        
    public SerialComm getSerialComm()
    {
        return serialComm;
    }
}
