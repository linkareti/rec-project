/*
 * CartPoleDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.vcartpole;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

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


public class CartPoleDriver extends VirtualBaseDriver {
    
    private static String CartPole_DRIVER_LOGGER="CartPole.Logger";
    static {
        Logger l=LogManager.getLogManager().getLogger(CartPole_DRIVER_LOGGER);
        if(l==null) {
            LogManager.getLogManager().addLogger(Logger.getLogger(CartPole_DRIVER_LOGGER));
        }
    }
    
    /* Hardware and driver related variables*/
    private static final String APPLICATION_IDENTIFIER = "E-Lab (Cart Pole Driver)";
    private static final String DRIVER_UNIQUE_ID = "CART_POLE_V1.0";
    private static final String HW_VERSION = "0.1";
    
    protected VirtualBaseDataSource dataSource = null;
    protected HardwareAcquisitionConfig config=null;
    protected HardwareInfo info=null;
    
    /** Creates a new instance of CartPoleDriver */
    public CartPoleDriver() {
    }
    
    
    public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException, WrongConfigurationException {
        fireIDriverStateListenerDriverConfiguring();
        info.validateConfig(config);
        extraValidateConfig(config,info);
        try {
            configure(config,info);
        }
        catch(Exception e) {
            LoggerUtil.logThrowable("Error on config...",e,Logger.getLogger(CartPole_DRIVER_LOGGER));
            throw new WrongConfigurationException();
        }
    }
    
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {
        this.config=config;
        this.info=info;
        
        int tbs = (int) config.getSelectedFrequency().getFrequency();
        int nSamples = config.getTotalSamples();
        
        double uCart = Float.parseFloat(config.getSelectedHardwareParameterValue("uCart"));
        double uPole = Float.parseFloat(config.getSelectedHardwareParameterValue("uPole"));
        
        double g = Float.parseFloat(config.getSelectedHardwareParameterValue("g"));
        
        int kp = Integer.parseInt(config.getSelectedHardwareParameterValue("kp"));
        int ki = Integer.parseInt(config.getSelectedHardwareParameterValue("ki"));
        int kd = Integer.parseInt(config.getSelectedHardwareParameterValue("kd"));
        
        boolean failActive = (config.getSelectedHardwareParameterValue("failActive").trim().equals("1")?true:false);
        int failNLaps = Integer.parseInt(config.getSelectedHardwareParameterValue("failNLaps"));
        int failN = Integer.parseInt(config.getSelectedHardwareParameterValue("failN"));
        int failTime = Integer.parseInt(config.getSelectedHardwareParameterValue("failTime"));
        
        boolean sucActive = (config.getSelectedHardwareParameterValue("sucActive").trim().equals("1")?true:false);
        int sucAngle = Integer.parseInt(config.getSelectedHardwareParameterValue("sucAngle"));
        int sucTime = Integer.parseInt(config.getSelectedHardwareParameterValue("sucTime"));
        double xMax = Float.parseFloat(config.getSelectedHardwareParameterValue("xMax"));
        
        
        double mCart = Float.parseFloat(config.getSelectedHardwareParameterValue("mCart"));
        double mPole = Float.parseFloat(config.getSelectedHardwareParameterValue("mPole"));
        
        double x = Float.parseFloat(config.getSelectedHardwareParameterValue("x"));
        double xdot = Float.parseFloat(config.getSelectedHardwareParameterValue("xdot"));
        double theta = Float.parseFloat(config.getSelectedHardwareParameterValue("theta"));
        double thetadot = Float.parseFloat(config.getSelectedHardwareParameterValue("thetadot"));
        double poleLength = Float.parseFloat(config.getSelectedHardwareParameterValue("poleLength"));
        double action = Float.parseFloat(config.getSelectedHardwareParameterValue("action"));
        
        dataSource = new CartPoleDataProducer(this, x,xdot,theta,thetadot,new double[]{uCart,uPole},new double[]{mCart,mPole},g,poleLength,action,tbs,nSamples);
        ((CartPoleDataProducer)dataSource).initializePID(kp,ki,kd);
        ((CartPoleDataProducer)dataSource).initializeFailure(failN,xMax,failTime,failNLaps);
        ((CartPoleDataProducer)dataSource).initializeSuccess(sucAngle,sucTime);
        
        
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
        String baseHardwareInfoFile="recresource://pt/utl/ist/elab/virtual/driver/cartpole/CartPoleBaseHardwareInfo.xml";
        String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.CartPole.HardwareInfo"),baseHardwareInfoFile);
        
        if(prop.indexOf("://")==-1)
            prop="file:///" + System.getProperty("user.dir") + "/" + prop;
        
        java.net.URL url=null;
        try {
            url=ReCProtocols.getURL(prop);
        }catch(java.net.MalformedURLException e) {
            LoggerUtil.logThrowable("Unable to load resource: " + prop,e,Logger.getLogger(CartPole_DRIVER_LOGGER));
            try {
                url=new java.net.URL(baseHardwareInfoFile);
            }catch(java.net.MalformedURLException e2) {
                LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile,e2,Logger.getLogger(CartPole_DRIVER_LOGGER));
            }
        }
        fireIDriverStateListenerDriverReseted();
        return url;
    }
}
