/*
 * MeteoDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.telescopio;

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
import java.util.logging.*;

/**
 *
 * @author  Andr�
 */


public class TelescopioDriver extends BaseDriver
{    
    /* Hardware and driver related variables*/
    private static final String APPLICATION_IDENTIFIER = "E-Lab Telescopio Driver";
    private static final String DRIVER_UNIQUE_ID = "ELAB_TELESCOPIO_V01";
    private static final String HW_VERSION = "0.1";	    
    
    protected TelescopioDataProducer dataSource = null;    
    protected HardwareAcquisitionConfig config=null;
    protected HardwareInfo info=null;
                
    /** Creates a new instance of RobotDriver */
    public TelescopioDriver() 
    {
    }
    
            
    public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException, WrongConfigurationException
    {
        fireIDriverStateListenerDriverConfiguring();
	info.validateConfig(config);
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
            
    private int expo = 0;
    private String command = "";
    
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException 
    {
        if(dataSource != null)
        {
            dataSource = null;
        }
        
        dataSource = new TelescopioDataProducer(this);        
        
        this.config=config;
        this.info=info;
        
        expo = Integer.parseInt(config.getSelectedHardwareParameter("TempoExp").getParameterValue().trim());
        
        command = config.getSelectedHardwareParameter("Comando").getParameterValue().trim();        
                
        int nSamples = 1;
        
        config.setTotalSamples(nSamples);
        
        for(int i=0; i<config.getChannelsConfig().length; i++)
        {
            config.getChannelsConfig(i).setTotalSamples(nSamples);
        }                        
        dataSource.setAcquisitionHeader(config);
        
        fireIDriverStateListenerDriverConfigured();
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
        /*SQLConnector sqlc = new SQLConnector(SQLConnector.MYSQL);
        conn = sqlc.connect("192.168.0.111/meteo", "root", "");                */                
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
        if(dataSource!=null)
        {
            fireIDriverStateListenerDriverStarting();    
            dataSource.start(command, "" + expo);            
            fireIDriverStateListenerDriverStarted();     
            return dataSource;
	}
	else
        {
            return null;
        }        
    }
    
    public IDataSource startOutput(HardwareInfo info, IDataSource source) throws IncorrectStateException 
    {
        /**Don't know what is startOutput...*/
        return null;
    }
    
    public void stop(HardwareInfo info) throws IncorrectStateException
    {
        
    }    
    
    public Object getHardwareInfo() 
    {
        fireIDriverStateListenerDriverReseting();                
	
	String baseHardwareInfoFile="recresource://pt/utl/ist/elab/driver/telescopio/TelescopioBaseHardwareInfo.xml";
	String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.Telescopio.HardwareInfo"), baseHardwareInfoFile);
	
	if(prop.indexOf("://")==-1)
	    prop="file:///" + System.getProperty("user.dir") + "/" + prop;
	
	java.net.URL url=null;
	try
	{
	    url=new java.net.URL(prop);
            fireIDriverStateListenerDriverReseted();            
	}
        catch(java.net.MalformedURLException e)
	{
	    LoggerUtil.logThrowable("Unable to load resource: " + prop,e,Logger.getLogger("Telescopio"));
	    try
	    {
		url=new java.net.URL(baseHardwareInfoFile);
                fireIDriverStateListenerDriverReseted();            
	    }
            catch(java.net.MalformedURLException e2)
	    {
		LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile,e2,Logger.getLogger("Telescopio"));
	    }
	}	
        
        System.out.println("Loading url = " + url);
        
	return url;	
    }     
    

    protected void stop()
    {        
        fireIDriverStateListenerDriverStoping();        
        dataSource.stopProduction();
        fireIDriverStateListenerDriverStoped();   

    }
    
}
