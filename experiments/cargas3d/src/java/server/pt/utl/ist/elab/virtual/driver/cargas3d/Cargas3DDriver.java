/*
 * Cargas3DDriver.java
 *
 * Created on 22 de Mar�o de 2005, 14:38
 */

package pt.utl.ist.elab.virtual.driver.cargas3d;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.Defaults;
import pt.utl.ist.elab.virtual.client.cargas3d.*;

import pt.utl.ist.elab.virtual.driver.*;
import java.util.logging.*;
/**
 *
 * @author n0dP2
 */
public class Cargas3DDriver extends VirtualBaseDriver{
    
    //O código desta classe é sempre igual!!! Alterar só os nomes para o vosso caso!
    private static String Cargas3D_DRIVER_LOGGER="Cargas3D.Logger";
    static
    {
    	Logger l=LogManager.getLogManager().getLogger(Cargas3D_DRIVER_LOGGER);
    	if(l==null)
    	{
            LogManager.getLogManager().addLogger(Logger.getLogger(Cargas3D_DRIVER_LOGGER));
	}
    }
    
    /* Hardware and driver related variables*/
    private static final String APPLICATION_IDENTIFIER = "E-Lab (Cargas3D Driver)";
    private static final String DRIVER_UNIQUE_ID = "CARGAS3D_V1.0";
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
            e.printStackTrace();
            LoggerUtil.logThrowable("Error on config...",e,Logger.getLogger(Cargas3D_DRIVER_LOGGER));
            throw new WrongConfigurationException();
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
    }    
            
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException 
    {
        //Recebemos ordem para configurar, no HardwareAcquisitionConfig estao todas as informacoes escolhidas pelo cliente...agora e' so' pedir
        this.config=config;
        this.info=info;
     
        String sist = config.getSelectedHardwareParameterValue("Sistema");
        
        System.out.println("Received = " + sist);
        
        //Vamos criar o nosso produtor de dados!
        dataSource = new Cargas3DDataProducer(this, Sistema.stringToSistema(sist));
        
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
	String baseHardwareInfoFile="recresource://pt/utl/ist/elab/driver/virtual/cargas3d/Cargas3DBaseHardwareInfo.xml";
	String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.Cargas3D.HardwareInfo"),baseHardwareInfoFile);
	
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
