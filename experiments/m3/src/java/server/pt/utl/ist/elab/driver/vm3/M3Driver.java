/*
 * M3Driver.java
 *
 * Created on 17 de Fevereiro de 2005, 19:19
 */

package pt.utl.ist.elab.driver.vm3;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.Defaults;

import pt.utl.ist.elab.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.virtual.VirtualBaseDriver;
import pt.utl.ist.elab.virtual.driver.*;
import java.util.logging.*;

/**
 *
 * @author n0dP2
 */
public class M3Driver extends VirtualBaseDriver{
    
    /** Creates a new instance of M3Driver */
    public M3Driver() {
    }
    
    private static String M3_DRIVER_LOGGER="M3.Logger";
    static
    {
    	Logger l=LogManager.getLogManager().getLogger(M3_DRIVER_LOGGER);
    	if(l==null)
    	{
            LogManager.getLogManager().addLogger(Logger.getLogger(M3_DRIVER_LOGGER));
	}
    }
    
    /* Hardware and driver related variables*/
    private static final String APPLICATION_IDENTIFIER = "E-Lab (Molas 3 Driver)";
    private static final String DRIVER_UNIQUE_ID = "MOLAS3_V1.0";
    private static final String HW_VERSION = "0.1";
	        
    protected VirtualBaseDataSource dataSource = null;    
    protected HardwareAcquisitionConfig config=null;
    protected HardwareInfo info=null;
    
        
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
            LoggerUtil.logThrowable("Error on config...",e,Logger.getLogger(M3_DRIVER_LOGGER));
            throw new WrongConfigurationException();
        }
    }    
            
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException 
    {
        this.config=config;
        this.info=info;

        float massa = Float.parseFloat(config.getSelectedHardwareParameterValue("massa"));
        float k1 = Float.parseFloat(config.getSelectedHardwareParameterValue("k1"));
        float k2 = Float.parseFloat(config.getSelectedHardwareParameterValue("k2"));
        float k3 = Float.parseFloat(config.getSelectedHardwareParameterValue("k3"));
        float x0 = Float.parseFloat(config.getSelectedHardwareParameterValue("x0"));
        float y0 = Float.parseFloat(config.getSelectedHardwareParameterValue("y0"));
        int tbs = (int)config.getSelectedFrequency().getFrequency();
        int nSamples = config.getTotalSamples();
        
        //Vamos criar o nosso produtor de dados!
        dataSource = new M3DataProducer(this, massa, k1, k2, k3, x0, y0, tbs, nSamples);
        
        //NECESSARIO!! Colocar o numero de amostra para todos os canais!
        for(int i=0;i<config.getChannelsConfig().length;i++)
        {            
            config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
        }
        
        //NAO ESQUECER ESTA LINHA!
        dataSource.setAcquisitionHeader(config);
        
        fireIDriverStateListenerDriverConfigured();
    }
    
    public String getDriverUniqueID() 
    {
        return DRIVER_UNIQUE_ID;
    }
    
    public void shutdown() 
    {
        if(dataSource!=null)
        {
            dataSource.stopNow();
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
    
    public void stop(HardwareInfo info) throws IncorrectStateException
    {
        fireIDriverStateListenerDriverStoping();
        dataSource.stopNow();
        fireIDriverStateListenerDriverStoped();
    }    
    
    public Object getHardwareInfo() 
    {
        fireIDriverStateListenerDriverReseting(); 
        //MUITO IMPORTANTE!!! ALTERAR AS PROXIMAS DUAS LINHAS!!!
	String baseHardwareInfoFile="recresource://pt/utl/ist/elab/driver/virtual/m3/M3BaseHardwareInfo.xml";
	String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.M3.HardwareInfo"),baseHardwareInfoFile);
	
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
    
}
