/*
 * QuantumDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.vquantum;

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


public class QuantumDriver extends VirtualBaseDriver {
    
    private static String Quantum_DRIVER_LOGGER="Quantum.Logger";
    static {
        Logger l=LogManager.getLogManager().getLogger(Quantum_DRIVER_LOGGER);
        if(l==null) {
            LogManager.getLogManager().addLogger(Logger.getLogger(Quantum_DRIVER_LOGGER));
        }
    }
    
    /* Hardware and driver related variables*/
    private static final String APPLICATION_IDENTIFIER = "E-Lab (Mecanica Quantica Driver)";
    private static final String DRIVER_UNIQUE_ID = "MECANICA_QUANTICA_V1.0";
    private static final String HW_VERSION = "0.1";
    
    protected VirtualBaseDataSource dataSource = null;
    protected HardwareAcquisitionConfig config=null;
    protected HardwareInfo info=null;
    
    /** Creates a new instance of CGDriver */
    public QuantumDriver() {
    }
    
    
    public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException, WrongConfigurationException {
        fireIDriverStateListenerDriverConfiguring();
        info.validateConfig(config);
        extraValidateConfig(config,info);
        try {
            configure(config,info);
        }
        catch(Exception e) {
            LoggerUtil.logThrowable("Error on config...",e,Logger.getLogger(Quantum_DRIVER_LOGGER));
            throw new WrongConfigurationException();
        }
    }
    
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {
        this.config=config;
        this.info=info;
        
        int nSamples = config.getTotalSamples();
        
        double x0 = Double.parseDouble(config.getSelectedHardwareParameterValue("x0"));
        short deltaX = Short.parseShort(config.getSelectedHardwareParameterValue("deltaX"));
        byte log2N = Byte.parseByte(config.getSelectedHardwareParameterValue("log2N"));
        short dX0 = Short.parseShort(config.getSelectedHardwareParameterValue("dX0"));
        
        String xDt = config.getSelectedHardwareParameterValue("xDt");
        String nDt = config.getSelectedHardwareParameterValue("nDt");
        double dt = Double.parseDouble(xDt+"e-"+nDt);
        
        String xE = config.getSelectedHardwareParameterValue("xEnergy");
        String nE = config.getSelectedHardwareParameterValue("nEnergy");
        double energy = Double.parseDouble(xE+"e"+nE);
        
        double tol = Double.parseDouble("1e-"+config.getSelectedHardwareParameterValue("logTol"));
        
        String xTbs = config.getSelectedHardwareParameterValue("xTbs");
        String nTbs = config.getSelectedHardwareParameterValue("nTbs");
        double tbs = Double.parseDouble(xTbs+"e-"+nTbs);
        
        boolean wraparoundKS = config.getSelectedHardwareParameterValue("wraparoundKS").trim().equals("1")?true:false;
        boolean wraparoundXS = config.getSelectedHardwareParameterValue("wraparoundXS").trim().equals("1")?true:false;
        boolean tunneling = config.getSelectedHardwareParameterValue("tunneling").trim().equals("1")?true:false;
        
        dataSource = new QuantumDataProducer(this,dX0,x0,energy,log2N,deltaX,tol,dt,tbs,nSamples,wraparoundKS,wraparoundXS,tunneling);
        ((QuantumDataProducer) dataSource).configPotentials(config.getSelectedHardwareParameterValue("potentials"));
        
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
        String baseHardwareInfoFile="recresource://pt/utl/ist/elab/virtual/driver/quantum/QuantumBaseHardwareInfo.xml";
        String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.Quantum.HardwareInfo"),baseHardwareInfoFile);
        
        if(prop.indexOf("://")==-1)
            prop="file:///" + System.getProperty("user.dir") + "/" + prop;
        
        java.net.URL url=null;
        try {
            url=ReCProtocols.getURL(prop);
        }catch(java.net.MalformedURLException e) {
            LoggerUtil.logThrowable("Unable to load resource: " + prop,e,Logger.getLogger(Quantum_DRIVER_LOGGER));
            try {
                url=new java.net.URL(baseHardwareInfoFile);
            }catch(java.net.MalformedURLException e2) {
                LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile,e2,Logger.getLogger(Quantum_DRIVER_LOGGER));
            }
        }
        fireIDriverStateListenerDriverReseted();
        return url;
    }
}
