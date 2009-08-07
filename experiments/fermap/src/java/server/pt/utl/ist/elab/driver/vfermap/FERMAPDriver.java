/*
 * FERMAPDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.vfermap;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.Defaults;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;
import pt.utl.ist.elab.virtual.driver.*;
import java.util.logging.*;

/**
 *
 * @author  Antonio Jose Rodrigues Figueiredo
 */


public class FERMAPDriver extends VirtualBaseDriver {
    
    private static String FERMAP_DRIVER_LOGGER="FERMAP.Logger";
    static {
        Logger l=LogManager.getLogManager().getLogger(FERMAP_DRIVER_LOGGER);
        if(l==null) {
            LogManager.getLogManager().addLogger(Logger.getLogger(FERMAP_DRIVER_LOGGER));
        }
    }
    
    /* Hardware and driver related variables*/
    private static final String APPLICATION_IDENTIFIER = "E-Lab (Fermi-Map Driver)";
    private static final String DRIVER_UNIQUE_ID = "FERMI_MAP_V1.0";
    private static final String HW_VERSION = "0.1";
    
    protected VirtualBaseDataSource dataSource = null;
    protected HardwareAcquisitionConfig config=null;
    protected HardwareInfo info=null;
    
    /** Creates a new instance of CGDriver */
    public FERMAPDriver() {
    }
    
    
    public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException, WrongConfigurationException {
        fireIDriverStateListenerDriverConfiguring();
        info.validateConfig(config);
        extraValidateConfig(config,info);
        try {
            configure(config,info);
        }
        catch(Exception e) {
            LoggerUtil.logThrowable("Error on config...",e,Logger.getLogger(FERMAP_DRIVER_LOGGER));
            throw new WrongConfigurationException();
        }
    }
    
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {
        this.config=config;
        this.info=info;
        
        byte simulType = Byte.parseByte(config.getSelectedHardwareParameterValue("simulType"));

        if (simulType == 2){ //anima
            int tbs = (int) config.getSelectedFrequency().getFrequency();
            int nSamples = Integer.parseInt(config.getSelectedHardwareParameterValue("nCol"));;
            
            float x = Float.parseFloat(config.getSelectedHardwareParameterValue("x"));
            float xDot = Float.parseFloat(config.getSelectedHardwareParameterValue("xDot"));
            float psi = Float.parseFloat(config.getSelectedHardwareParameterValue("psi"));
            float wFreq = Float.parseFloat(config.getSelectedHardwareParameterValue("psi"));
            float wAmp = Float.parseFloat(config.getSelectedHardwareParameterValue("wAmp"));
            float d = Float.parseFloat(config.getSelectedHardwareParameterValue("d"));
            
            dataSource = new FERMAPDataProducer(this, x, xDot, psi, wFreq, wAmp, d, tbs, nSamples);
        }
        else {
            boolean staticImg = config.getSelectedHardwareParameterValue("staticImg").trim().equals("1")?true:false;
            
            int w = Integer.parseInt(config.getSelectedHardwareParameterValue("w"));
            int h = Integer.parseInt(config.getSelectedHardwareParameterValue("h"));
            byte pixSize = Byte.parseByte(config.getSelectedHardwareParameterValue("pixSize"));
            
            int uMax = Integer.parseInt(config.getSelectedHardwareParameterValue("uMax"));
            
            if (simulType == 1){
                float m = Float.parseFloat(config.getSelectedHardwareParameterValue("m"));
                float pcor = Float.parseFloat(config.getSelectedHardwareParameterValue("pcor"));
                int iter = Integer.parseInt(config.getSelectedHardwareParameterValue("iter"));
                
                float psi = Float.parseFloat(config.getSelectedHardwareParameterValue("psi"));
                int nPsi = Integer.parseInt(config.getSelectedHardwareParameterValue("nPsi"));
                float dPsi = Float.parseFloat(config.getSelectedHardwareParameterValue("dPsi"));
                
                float uMapa = Float.parseFloat(config.getSelectedHardwareParameterValue("uMapa"));
                int nUMapa = Integer.parseInt(config.getSelectedHardwareParameterValue("nUMapa"));
                float dUMapa = Float.parseFloat(config.getSelectedHardwareParameterValue("dUMapa"));
                
                dataSource = new FERMAPDataProducer(this, m, psi, nPsi, dPsi, uMapa, nUMapa, dUMapa, iter, pcor, w, h, pixSize, uMax, staticImg);
            }
            else if (simulType == 3){
                float m = Float.parseFloat(config.getSelectedHardwareParameterValue("m"));
                float pcor = Float.parseFloat(config.getSelectedHardwareParameterValue("pcor"));
                int iter = Integer.parseInt(config.getSelectedHardwareParameterValue("iter"));
                
                float psi = Float.parseFloat(config.getSelectedHardwareParameterValue("psi"));
                float uMapa = Float.parseFloat(config.getSelectedHardwareParameterValue("uMapa"));
                
                dataSource = new FERMAPDataProducer(this, m, psi, uMapa, iter, pcor, w, h, pixSize, uMax, staticImg);
            }
        }
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
        String baseHardwareInfoFile="recresource://pt/utl/ist/elab/virtual/driver/fermap/FERMAPBaseHardwareInfo.xml";
        String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.FERMAP.HardwareInfo"),baseHardwareInfoFile);
        
        if(prop.indexOf("://")==-1)
            prop="file:///" + System.getProperty("user.dir") + "/" + prop;
        
        java.net.URL url=null;
        try {
            url=ReCProtocols.getURL(prop);
        }catch(java.net.MalformedURLException e) {
            LoggerUtil.logThrowable("Unable to load resource: " + prop,e,Logger.getLogger(FERMAP_DRIVER_LOGGER));
            try {
                url=new java.net.URL(baseHardwareInfoFile);
            }catch(java.net.MalformedURLException e2) {
                LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile,e2,Logger.getLogger(FERMAP_DRIVER_LOGGER));
            }
        }
        fireIDriverStateListenerDriverReseted();
        return url;
    }
}
