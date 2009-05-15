/*
 * RobotDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.virtual.driver.dpendulum;

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

/**
 *
 * @author  Andr�
 */


public class DPendulumDriver extends BaseDriver
{    
    /* Hardware and driver related variables*/
    private static final String APPLICATION_IDENTIFIER = "E-Lab (P�ndulo Driver)";
    private static final String DRIVER_UNIQUE_ID = "DPENDULUM_V1.0";
    private static final String HW_VERSION = "0.1";
	        
    protected DPendulumDataProducer dataSource = null;    
    protected HardwareAcquisitionConfig config=null;
    protected HardwareInfo info=null;
    
    protected String flowChartString=null;
        
    /** Creates a new instance of RobotDriver */
    public DPendulumDriver() 
    {
    }
    
        
    public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException, WrongConfigurationException
    {
        fireIDriverStateListenerDriverConfiguring();
	info.validateConfig(config);
        extraValidateConfig(config,info);
	try
	{
            configure(config,info);
        }
        catch(Exception e)
	{
            e.printStackTrace();
            throw new WrongConfigurationException();
        }
    }    
            
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException 
    {
        this.config=config;
        this.info=info;

        float a1 = Float.parseFloat(config.getSelectedHardwareParameterValue("P1 ini angle"));
        float a2 = Float.parseFloat(config.getSelectedHardwareParameterValue("P2 ini angle"));
        float w1 = Float.parseFloat(config.getSelectedHardwareParameterValue("P1 vel ang ini"));
        float w2 = Float.parseFloat(config.getSelectedHardwareParameterValue("P2 vel ang ini"));
        float l1 = Float.parseFloat(config.getSelectedHardwareParameterValue("P1 length"));
        float l2 = Float.parseFloat(config.getSelectedHardwareParameterValue("P2 length"));
        float m1 = Float.parseFloat(config.getSelectedHardwareParameterValue("P1 Mass"));
        float m2 = Float.parseFloat(config.getSelectedHardwareParameterValue("P2 Mass"));
        int tbs = (int)config.getSelectedFrequency().getFrequency();
        int nSamples = config.getTotalSamples();
        
        dataSource = new DPendulumDataProducer(this, a1, a2, w1, w2, l1, l2, m1, m2, tbs, nSamples);
        
        for(int i=0;i<config.getChannelsConfig().length;i++)
        {            
            config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
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
        fireIDriverStateListenerDriverStarting();  
        dataSource.startProduction();
        fireIDriverStateListenerDriverStarted();  
        return dataSource;
    }
    
    public IDataSource startOutput(HardwareInfo info, IDataSource source) throws IncorrectStateException 
    {
        /**Don't know what is startOutput...*/
        return null;
    }
    
    public void stop(HardwareInfo info) throws IncorrectStateException
    {
        dataSource.stopNow();
    }    
    
    public Object getHardwareInfo() 
    {
        fireIDriverStateListenerDriverReseting();        
	String baseHardwareInfoFile="recresource://pt/utl/ist/elab/driver/virtual/dpendulum/DPendulumBaseHardwareInfo.xml";
	String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.DPendulum.HardwareInfo"),baseHardwareInfoFile);
	
	if(prop.indexOf("://")==-1)
	    prop="file:///" + System.getProperty("user.dir") + "/" + prop;
	
	java.net.URL url=null;
	try
	{
	    url=ReCProtocols.getURL(prop);
	}catch(java.net.MalformedURLException e)
	{
	    LoggerUtil.logThrowable("Unable to load resource: " + prop,e,Logger.getLogger("DPendulum"));
	    try
	    {
		url=new java.net.URL(baseHardwareInfoFile);
	    }catch(java.net.MalformedURLException e2)
	    {
		LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile,e2,Logger.getLogger("DPendulum"));
	    }
	}
        fireIDriverStateListenerDriverReseted(); 		
	return url;	
    }    
    
    public void setStoping()
    {
        fireIDriverStateListenerDriverStoping();
    }
    
    public void setStoped()
    {
        fireIDriverStateListenerDriverStoped(); 
    }    
}
