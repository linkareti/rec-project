/*
 * TiroDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.vtiro;

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
 * @author  nomead
 */


public class TiroDriver extends VirtualBaseDriver {
    
    private static String Tiro_DRIVER_LOGGER="Tiro.Logger";
    static {
        Logger l=LogManager.getLogManager().getLogger(Tiro_DRIVER_LOGGER);
        if(l==null) {
            LogManager.getLogManager().addLogger(Logger.getLogger(Tiro_DRIVER_LOGGER));
        }
    }
    
    /* Hardware and driver related variables*/
    private static final String APPLICATION_IDENTIFIER = "E-Lab (Cart Pole Driver)";
    private static final String DRIVER_UNIQUE_ID = "CART_POLE_V1.0";
    private static final String HW_VERSION = "0.1";
    
    protected VirtualBaseDataSource dataSource = null;
    protected HardwareAcquisitionConfig config=null;
    protected HardwareInfo info=null;
    
    /** Creates a new instance of TiroDriver */
    public TiroDriver() {
    }
    
    
    public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException, WrongConfigurationException {
        fireIDriverStateListenerDriverConfiguring();
        info.validateConfig(config);
        extraValidateConfig(config,info);
        try {
            configure(config,info);
        }
        catch(Exception e) {
            LoggerUtil.logThrowable("Error on config...",e,Logger.getLogger(Tiro_DRIVER_LOGGER));
            throw new WrongConfigurationException();
        }
    }
    
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {
        this.config=config;
        this.info=info;
        
        double g = Float.parseFloat(config.getSelectedHardwareParameterValue("g"));
        double w = Float.parseFloat(config.getSelectedHardwareParameterValue("w"));
        double h = Float.parseFloat(config.getSelectedHardwareParameterValue("h"));
        double v = Float.parseFloat(config.getSelectedHardwareParameterValue("v"));
        double theta = Float.parseFloat(config.getSelectedHardwareParameterValue("theta"));
        
        dataSource = new TiroDataProducer(this,w,h,v,theta,g);
        
        for(int i=0;i<config.getChannelsConfig().length;i++)
            config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
        
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
        String baseHardwareInfoFile="recresource://pt/utl/ist/elab/virtual/driver/tiro/TiroBaseHardwareInfo.xml";
        String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.Tiro.HardwareInfo"),baseHardwareInfoFile);
        
        if(prop.indexOf("://")==-1)
            prop="file:///" + System.getProperty("user.dir") + "/" + prop;
        
        java.net.URL url=null;
        try {
            url=ReCProtocols.getURL(prop);
        }catch(java.net.MalformedURLException e) {
            LoggerUtil.logThrowable("Unable to load resource: " + prop,e,Logger.getLogger(Tiro_DRIVER_LOGGER));
            try {
                url=new java.net.URL(baseHardwareInfoFile);
            }catch(java.net.MalformedURLException e2) {
                LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile,e2,Logger.getLogger(Tiro_DRIVER_LOGGER));
            }
        }
        fireIDriverStateListenerDriverReseted();
        return url;
    }
}
