/*
 * RobotDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.vcolisao;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.virtual.VirtualBaseDriver;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.utils.Defaults;

/**
 *
 * @author  Emanuel Antunes
 */


public class ColisaoDriver extends VirtualBaseDriver {
    //O código desta classe é sempre igual!!! Alterar só os nomes para o vosso caso!
    private static String COLISAO_DRIVER_LOGGER="Colisao.Logger";
    static {
        Logger l=LogManager.getLogManager().getLogger(COLISAO_DRIVER_LOGGER);
        if(l==null) {
            LogManager.getLogManager().addLogger(Logger.getLogger(COLISAO_DRIVER_LOGGER));
        }
    }
    
    /* Hardware and driver related variables*/
    private static final String APPLICATION_IDENTIFIER = "E-Lab (Colisao Driver)";
    private static final String DRIVER_UNIQUE_ID = "COLISAO_V1.0";
    private static final String HW_VERSION = "0.1";
    
    protected VirtualBaseDataSource dataSource = null;
    protected HardwareAcquisitionConfig config=null;
    protected HardwareInfo info=null;
    
    /** Creates a new instance of RobotDriver */
    public ColisaoDriver() {
    }
    
    
    public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException, WrongConfigurationException {
        fireIDriverStateListenerDriverConfiguring();
        info.validateConfig(config);
        extraValidateConfig(config,info);
        try {
            configure(config,info);
        }
        catch(Exception e) {
            LoggerUtil.logThrowable("Error on config...",e,Logger.getLogger(COLISAO_DRIVER_LOGGER));
            throw new WrongConfigurationException();
        }
    }
    
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {
        //Recebemos ordem para configurar, no HardwareAcquisitionConfig estao todas as informacoes escolhidas pelo cliente...agora e' so' pedir
        this.config=config;
        this.info=info;
        
        float v1 = Float.parseFloat(config.getSelectedHardwareParameterValue("v1"));
        float m1 = Float.parseFloat(config.getSelectedHardwareParameterValue("m1"));
        float r1 = Float.parseFloat(config.getSelectedHardwareParameterValue("r1"));
        float v2 = Float.parseFloat(config.getSelectedHardwareParameterValue("v1"));
        float m2 = Float.parseFloat(config.getSelectedHardwareParameterValue("m1"));
        float r2 = Float.parseFloat(config.getSelectedHardwareParameterValue("r2"));
        int a = Integer.parseInt(config.getSelectedHardwareParameterValue("a"));
        int elasticCollision = Integer.parseInt(config.getSelectedHardwareParameterValue("elasticCollision"));
        int tbs = (int)config.getSelectedFrequency().getFrequency();
        int nSamples = config.getTotalSamples();
        
        //Vamos criar o nosso produtor de dados!
        dataSource = new ColisaoDataProducer(this, v1,v2,r1,r2,m1,m2,a,elasticCollision, tbs, nSamples);
        
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
        String baseHardwareInfoFile="recresource://pt/utl/ist/elab/driver/virtual/colisao/ColisaoBaseHardwareInfo.xml";
        String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.Colisao.HardwareInfo"),baseHardwareInfoFile);
        
        if(prop.indexOf("://")==-1)
            prop="file:///" + System.getProperty("user.dir") + "/" + prop;
        
        java.net.URL url=null;
        try {
            url=ReCProtocols.getURL(prop);
        }catch(java.net.MalformedURLException e) {
            LoggerUtil.logThrowable("Unable to load resource: " + prop,e,Logger.getLogger("Colisao"));
            try {
                url=new java.net.URL(baseHardwareInfoFile);
            }catch(java.net.MalformedURLException e2) {
                LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile,e2,Logger.getLogger("Colisao"));
            }
        }
        fireIDriverStateListenerDriverReseted();
        return url;
    }
}
