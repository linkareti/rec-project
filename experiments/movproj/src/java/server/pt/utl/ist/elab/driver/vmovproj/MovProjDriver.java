/*
 * MovProjDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.vmovproj;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;
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
 * @author  nomead
 */


public class MovProjDriver extends VirtualBaseDriver {
    
    private static String MovProj_DRIVER_LOGGER="MovProj.Logger";
    static {
        Logger l=LogManager.getLogManager().getLogger(MovProj_DRIVER_LOGGER);
        if(l==null) {
            LogManager.getLogManager().addLogger(Logger.getLogger(MovProj_DRIVER_LOGGER));
        }
    }
    
    /* Hardware and driver related variables*/
    private static final String APPLICATION_IDENTIFIER = "E-Lab (Movimento de Projecteis Driver)";
    private static final String DRIVER_UNIQUE_ID = "MOVIMENTO_DE_PROJECTEIS_V1.0";
    private static final String HW_VERSION = "0.1";
    
    protected VirtualBaseDataSource dataSource = null;
    protected HardwareAcquisitionConfig config=null;
    protected HardwareInfo info=null;
    
    /** Creates a new instance of CGDriver */
    public MovProjDriver() {
    }
    
    
    public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException, WrongConfigurationException {
        fireIDriverStateListenerDriverConfiguring();
        info.validateConfig(config);
        extraValidateConfig(config,info);
        try {
            configure(config,info);
        }
        catch(Exception e) {
            e.printStackTrace();
            LoggerUtil.logThrowable("Error on config...",e,Logger.getLogger(MovProj_DRIVER_LOGGER));
            throw new WrongConfigurationException();
        }
    }
    
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {
        this.config=config;
        this.info=info;
        
        int tbs = (int) config.getSelectedFrequency().getFrequency();
        int nSamples = config.getTotalSamples();
        
        boolean odeType = config.getSelectedHardwareParameterValue("odeType").trim().equals("1")?true:false;
        boolean gType = config.getSelectedHardwareParameterValue("gType").trim().equals("1")?true:false;
        boolean dragType = config.getSelectedHardwareParameterValue("dragType").trim().equals("1")?true:false;
        
        float x = Float.parseFloat(config.getSelectedHardwareParameterValue("x"));
        float y = Float.parseFloat(config.getSelectedHardwareParameterValue("y"));
        float z = Float.parseFloat(config.getSelectedHardwareParameterValue("z"));
        
        float velMod = Float.parseFloat(config.getSelectedHardwareParameterValue("velMod"));
        float velTheta = Float.parseFloat(config.getSelectedHardwareParameterValue("velTheta"));
        float velPhi = Float.parseFloat(config.getSelectedHardwareParameterValue("velPhi"));
        
        float spinMod = Float.parseFloat(config.getSelectedHardwareParameterValue("spinMod"));
        float spinTheta = Float.parseFloat(config.getSelectedHardwareParameterValue("spinTheta"));
        float spinPhi = Float.parseFloat(config.getSelectedHardwareParameterValue("spinPhi"));
        
        float mass = Float.parseFloat(config.getSelectedHardwareParameterValue("mass"));
        float radius = Float.parseFloat(config.getSelectedHardwareParameterValue("radius"));
        
        float dragCoef1 = Float.parseFloat(config.getSelectedHardwareParameterValue("dragCoef1"));
        float dragCoef2 = Float.parseFloat(config.getSelectedHardwareParameterValue("dragCoef2"));
        float densL = Float.parseFloat(config.getSelectedHardwareParameterValue("densL"));
        
        float s0 = Float.parseFloat(config.getSelectedHardwareParameterValue("s0"));
        float g = Float.parseFloat(config.getSelectedHardwareParameterValue("g"));
        int dt = Integer.parseInt(config.getSelectedHardwareParameterValue("dt"));
        
        boolean rMod = config.getSelectedHardwareParameterValue("posModulus").trim().equals("1")?true:false;
        boolean vMod = config.getSelectedHardwareParameterValue("velModulus").trim().equals("1")?true:false;
        
        ParameterConfig[] selectedParams=config.getSelectedHardwareParameters();
        
        if(selectedParams!=null) {
            ParameterConfig flowParam=null;
            for(int i=0;i<selectedParams.length;i++) {
                System.out.println(selectedParams[i].getParameterName() + " = " + selectedParams[i].getParameterValue());
            }
        }
        
        System.out.println("1");
        if (!rMod || !vMod){
            String [] graph1 = splitArroundPoint(config.getSelectedHardwareParameterValue("graph1"));
            for (int i = 0; i < 2; i++)
                if (graph1[i].equalsIgnoreCase("| r |"))
                    rMod = true;
                else if (graph1[i].equalsIgnoreCase("| v |"))
                    vMod = true;
            if (!rMod || !vMod) {
                String [] graph2 = splitArroundPoint(config.getSelectedHardwareParameterValue("graph2"));
                for (int i = 0; i < 2; i++)
                    if (graph2[i].equalsIgnoreCase("| r |"))
                        rMod = true;
                    else if (graph2[i].equalsIgnoreCase("| v |"))
                        vMod = true;
                if (!rMod || !vMod) {
                    String [] graph3 = splitArroundPoint(config.getSelectedHardwareParameterValue("graph3"));
                    for (int i = 0; i < 2; i++)
                        if (graph3[i].equalsIgnoreCase("| r |"))
                            rMod = true;
                        else if (graph3[i].equalsIgnoreCase("| v |"))
                            vMod = true;
                    if (!rMod || !vMod) {
                        String [] graph4 = splitArroundPoint(config.getSelectedHardwareParameterValue("graph4"));
                        for (int i = 0; i < 2; i++)
                            if (graph4[i].equalsIgnoreCase("| r |"))
                                rMod = true;
                            else if (graph4[i].equalsIgnoreCase("| v |"))
                                vMod = true;
                    }
                }
            }
        }
        System.out.println("2");
        try {
            dataSource = new MovProjDataProducer(this,x,y,z,velMod,velTheta,velPhi,spinMod,spinTheta,spinPhi,radius,mass,dragCoef1,dragCoef2,densL,s0,g,dt,odeType,gType,dragType,rMod,vMod,tbs,nSamples);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        
        
        System.out.println("3");
        for(int i=0;i<config.getChannelsConfig().length;i++)
            config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
        
        dataSource.setAcquisitionHeader(config);
        
        fireIDriverStateListenerDriverConfigured();
    }
    
    private String[] splitArroundPoint(String tosplit) {
        java.util.Vector v = new java.util.Vector();
        java.util.StringTokenizer token = new java.util.StringTokenizer(tosplit, ".");
        while(token.hasMoreTokens())
            v.add(token.nextToken());
        return (String[])v.toArray(new String[0]);
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
        String baseHardwareInfoFile="recresource://pt/utl/ist/elab/virtual/driver/movproj/MovProjBaseHardwareInfo.xml";
        String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.MovProj.HardwareInfo"),baseHardwareInfoFile);
        
        if(prop.indexOf("://")==-1)
            prop="file:///" + System.getProperty("user.dir") + "/" + prop;
        
        java.net.URL url=null;
        try {
            url=ReCProtocols.getURL(prop);
        }catch(java.net.MalformedURLException e) {
            LoggerUtil.logThrowable("Unable to load resource: " + prop,e,Logger.getLogger(MovProj_DRIVER_LOGGER));
            try {
                url=new java.net.URL(baseHardwareInfoFile);
            }catch(java.net.MalformedURLException e2) {
                LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile,e2,Logger.getLogger(MovProj_DRIVER_LOGGER));
            }
        }
        fireIDriverStateListenerDriverReseted();
        return url;
    }
}
