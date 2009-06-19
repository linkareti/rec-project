/*
 * BSDriver.java
 *
 * Created on 3 de Abril de 2005, 6:09
 */

package pt.utl.ist.elab.virtual.driver.bs;

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


public class BSDriver extends VirtualBaseDriver
{    
    //O codigo desta classe e' sempre igual!!! Alterar so' os nomes para o vosso caso!
    private static String BS_DRIVER_LOGGER="BS.Logger";
    static
    {
    	Logger l=LogManager.getLogManager().getLogger(BS_DRIVER_LOGGER);
    	if(l==null)
    	{
            LogManager.getLogManager().addLogger(Logger.getLogger(BS_DRIVER_LOGGER));
	}
    }
    
    /* Hardware and driver related variables*/
    private static final String APPLICATION_IDENTIFIER = "E-Lab (Biot-Savart Driver)";
    private static final String DRIVER_UNIQUE_ID = "BIOT_SAVART_V1.0";
    private static final String HW_VERSION = "0.1";
	        
    protected VirtualBaseDataSource dataSource = null;    
    protected HardwareAcquisitionConfig config=null;
    protected HardwareInfo info=null;
    
    /** Creates a new instance of BSDriver */
    public BSDriver() 
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
            LoggerUtil.logThrowable("Error on config...",e,Logger.getLogger(BS_DRIVER_LOGGER));
            throw new WrongConfigurationException();
        }
    }    
            
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException 
    {
        //Recebemos ordem para configurar, no HardwareAcquisitionConfig estao todas as informacoes escolhidas pelo cliente...agora e' so' pedir
        this.config=config;
        this.info=info;

        double i1_ini = Double.parseDouble(config.getSelectedHardwareParameterValue("i1_ini"));
        double i1_fin = Double.parseDouble(config.getSelectedHardwareParameterValue("i1_fin"));
        double i2_ini = Double.parseDouble(config.getSelectedHardwareParameterValue("i2_ini"));
        double i2_fin = Double.parseDouble(config.getSelectedHardwareParameterValue("i2_fin"));
        double dist = Double.parseDouble(config.getSelectedHardwareParameterValue("dist"));
        double xpto = Double.parseDouble(config.getSelectedHardwareParameterValue("xpto"));
        double ypto = Double.parseDouble(config.getSelectedHardwareParameterValue("ypto"));
        int tbs = (int)config.getSelectedFrequency().getFrequency();
        int nSamples = config.getTotalSamples();
        
        //Vamos criar o nosso produtor de dados!
        dataSource = new BSDataProducer(this, i1_ini, i1_fin, i2_ini, i2_fin, dist, xpto, ypto, tbs, nSamples);
        
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
	String baseHardwareInfoFile="recresource://pt/utl/ist/elab/driver/virtual/bs/BSBaseHardwareInfo.xml";
	String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.BS.HardwareInfo"),baseHardwareInfoFile);
	
	if(prop.indexOf("://")==-1)
	    prop="file:///" + System.getProperty("user.dir") + "/" + prop;
	
	java.net.URL url=null;
	try
	{
	    url=ReCProtocols.getURL(prop);
	}catch(java.net.MalformedURLException e)
	{
		// Nao sera' de alterar isto para BS, ou algo do genero?
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