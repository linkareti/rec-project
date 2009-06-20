/*
 * YoungInterfDriver.java
 *
 * Created on 27 de Dezembro de 2004, 17:24
 */

package pt.utl.ist.elab.virtual.driver.younginterf;

/**
 *
 * @author  Emanuel Antunes
 */

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

public class YoungInterfDriver  extends VirtualBaseDriver{
    
    //O codigo desta classe e sempre igual!!! Alterar so os nomes para o vosso caso!
    private static String YOUNGINTERF_DRIVER_LOGGER="YoungInterf.Logger";
    static {
        Logger l=LogManager.getLogManager().getLogger(YOUNGINTERF_DRIVER_LOGGER);
        if(l==null) {
            LogManager.getLogManager().addLogger(Logger.getLogger(YOUNGINTERF_DRIVER_LOGGER));
        }
    }
    
    /* Hardware and driver related variables*/
    private static final String APPLICATION_IDENTIFIER = "E-Lab (Interferencia-Young Driver)";
    private static final String DRIVER_UNIQUE_ID = "INTERFERENCIA_YOUNG_V1.0";
    private static final String HW_VERSION = "0.1";
    
    protected VirtualBaseDataSource dataSource = null;
    protected HardwareAcquisitionConfig config=null;
    protected HardwareInfo info=null;
    
    
    /** Creates a new instance of YoungInterfDriver */
    public YoungInterfDriver() {  }
    
    
    public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException, WrongConfigurationException {
        fireIDriverStateListenerDriverConfiguring();
        info.validateConfig(config);
        extraValidateConfig(config,info);
        try {
            configure(config,info);
        }
        catch(Exception e) {
            LoggerUtil.logThrowable("Error on config...",e,Logger.getLogger(YOUNGINTERF_DRIVER_LOGGER));
            throw new WrongConfigurationException();
        }
    }
    
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {
        //Recebemos ordem para configurar, no HardwareAcquisitionConfig estao todas as informacoes escolhidas pelo cliente...agora e' so' pedir
        this.config=config;
        this.info=info;
        float lambda = Float.parseFloat(config.getSelectedHardwareParameterValue("lambda"));
        float dfendas = Float.parseFloat(config.getSelectedHardwareParameterValue("dfendas"));
        float dplanos = Float.parseFloat(config.getSelectedHardwareParameterValue("dplanos"));
        float lpadrao = Float.parseFloat(config.getSelectedHardwareParameterValue("lpadrao"));
        
        int tbs = (int)config.getSelectedFrequency().getFrequency();
        int nSamples = config.getTotalSamples();
        
        
        //Vamos criar o nosso produtor de dados!
        dataSource = new YoungInterfDataProducer(this, dfendas , dplanos , lambda , lpadrao , tbs, nSamples);
        
        //NECESSARIO!! Colocar o numero de amostra para todos os canais!
        for(int i=0;i<config.getChannelsConfig().length;i++) {
            config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
        }
        
        //NAO ESQUECER ESTA LINHA!
        dataSource.setAcquisitionHeader(config);
        
        fireIDriverStateListenerDriverConfigured();
    }
    
    public String getDriverUniqueID() {
        return DRIVER_UNIQUE_ID;
    }
    
    public void shutdown() {
        if(dataSource!=null) {
            dataSource.stopNow();
        }
        super.shutDownNow();
    }
    
    public IDataSource start(HardwareInfo info) throws IncorrectStateException {
        fireIDriverStateListenerDriverStarting();
        dataSource.startProduction();
        fireIDriverStateListenerDriverStarted();
        return dataSource;
    }
    
    public void stop(HardwareInfo info) throws IncorrectStateException {
        fireIDriverStateListenerDriverStoping();
        dataSource.stopNow();
        fireIDriverStateListenerDriverStoped();
    }
    
    public Object getHardwareInfo() {
        fireIDriverStateListenerDriverReseting();
        //MUITO IMPORTANTE!!! ALTERAR AS PROXIMAS DUAS LINHAS!!!
        String baseHardwareInfoFile="recresource://pt/utl/ist/elab/driver/virtual//YoungInterfBaseHardwareInfo.xml";
        String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.YoungInterf.HardwareInfo"),baseHardwareInfoFile);
        
        if(prop.indexOf("://")==-1)
            prop="file:///" + System.getProperty("user.dir") + "/" + prop;
        
        java.net.URL url=null;
        try {
            url=ReCProtocols.getURL(prop);
        }catch(java.net.MalformedURLException e) {
            LoggerUtil.logThrowable("Unable to load resource: " + prop,e,Logger.getLogger("YoungInterf"));
            try {
                url=new java.net.URL(baseHardwareInfoFile);
            }catch(java.net.MalformedURLException e2) {
                LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile,e2,Logger.getLogger("YoungInterf"));
            }
        }
        fireIDriverStateListenerDriverReseted();
        return url;
    }
}
