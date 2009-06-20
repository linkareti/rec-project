/*
 * DIDriver.java
 *
 * Created on 3 de Abril de 2005, 6:09
 */

package pt.utl.ist.elab.virtual.driver.di;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.Defaults;

import pt.utl.ist.elab.virtual.driver.*;
import java.util.logging.*;

/**
 *
 * @author  Pedro Queiro'
 */


public class DIDriver extends VirtualBaseDriver {    
    //O codigo desta classe e' sempre igual!!! Alterar so' os nomes para o vosso caso!
    private static String DI_DRIVER_LOGGER="DI.Logger";
    static
    {
    	Logger l=LogManager.getLogManager().getLogger(DI_DRIVER_LOGGER);
    	if(l==null)
    	{
            LogManager.getLogManager().addLogger(Logger.getLogger(DI_DRIVER_LOGGER));
	}
    }
    
    /* Hardware and driver related variables*/
    private static final String APPLICATION_IDENTIFIER = "E-Lab (Discos de Inercia Driver)";
    private static final String DRIVER_UNIQUE_ID = "DISCOS_INERCIA_V1.0";
    private static final String HW_VERSION = "0.1";
	        
    protected VirtualBaseDataSource dataSource = null;    
    protected HardwareAcquisitionConfig config=null;
    protected HardwareInfo info=null;
    
    /** Creates a new instance of DIDriver */
    public DIDriver() 
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
            LoggerUtil.logThrowable("Error on config...",e,Logger.getLogger(DI_DRIVER_LOGGER));
            throw new WrongConfigurationException();
        }
    }    
            
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException 
    {
        //Recebemos ordem para configurar, no HardwareAcquisitionConfig estao todas as informacoes escolhidas pelo cliente...agora e' so' pedir
        this.config=config;
        this.info=info;

        double r1i = Double.parseDouble(config.getSelectedHardwareParameterValue("r1i"));
        double r1e = Double.parseDouble(config.getSelectedHardwareParameterValue("r1e"));
        double r2i = Double.parseDouble(config.getSelectedHardwareParameterValue("r2i"));
        double r2e = Double.parseDouble(config.getSelectedHardwareParameterValue("r2e"));
        double m1 = Double.parseDouble(config.getSelectedHardwareParameterValue("m1"));
        double m2 = Double.parseDouble(config.getSelectedHardwareParameterValue("m2"));
        double inc = Double.parseDouble(config.getSelectedHardwareParameterValue("inc"));
        
        int tbs = (int)config.getSelectedFrequency().getFrequency();
        int nSamples = config.getTotalSamples();
        
        //Vamos criar o nosso produtor de dados!
        dataSource = new DIDataProducer(this, r1i, r1e, r2i, r2e, m1, m2, inc, tbs, nSamples);
        
        //NECESSARIO!! Colocar o numero de amostras para todos os canais!
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
	String baseHardwareInfoFile="recresource://pt/utl/ist/elab/driver/virtual/di/DIBaseHardwareInfo.xml";
	String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.DI.HardwareInfo"),baseHardwareInfoFile);
	
	if(prop.indexOf("://")==-1)
	    prop="file:///" + System.getProperty("user.dir") + "/" + prop;
	
	java.net.URL url=null;
	try
	{
	    url=ReCProtocols.getURL(prop);
	}catch(java.net.MalformedURLException e)
	{
		// Nao sera' de alterar isto para DI, ou algo do genero?
	    LoggerUtil.logThrowable("Unable to load resource: " + prop,e,Logger.getLogger("DPendulum"));
	    try
	    {
		url=new java.net.URL(baseHardwareInfoFile);
	    }catch(java.net.MalformedURLException e2)
	    {
	    // E isto?
		LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile,e2,Logger.getLogger("DPendulum"));
	    }
	}
        fireIDriverStateListenerDriverReseted(); 		
	return url;	
    }     
}