/*
 * MeteoDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.meteo;

import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.threading.*;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;
import com.linkare.rec.acquisition.*;
import com.linkare.rec.data.config.*;
import com.linkare.rec.data.metadata.*;
import com.linkare.rec.impl.logging.*;
import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.threading.*;
import java.util.logging.*;
import pt.utl.ist.cfn.math.MathUtils;
import pt.utl.ist.cfn.serial.*;
import java.sql.*;
import pt.utl.ist.cfn.sql.*;

/**
 *
 * @author  Andr�
 */


public class MeteoDriver extends BaseDriver
{    
    /* Hardware and driver related variables*/
    private static final String APPLICATION_IDENTIFIER = "E-Lab (Meteo Driver)";
    private static final String DRIVER_UNIQUE_ID = "ELAB_METEO_V01";
    private static final String HW_VERSION = "0.1";	    
    
    protected MeteoDataProducer dataSource = null;    
    protected HardwareAcquisitionConfig config=null;
    protected HardwareInfo info=null;
    
    protected String flowChartString=null;
        
    private SerialComm sc = null;
    private Connection conn = null;
    
    /** Creates a new instance of RobotDriver */
    public MeteoDriver() 
    {
    }
    
    public SerialComm getSerialComm()
    {
        return sc;
    }
    
    public Connection getSQLConnection()
    {
        return conn;
    }
            
    public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException, WrongConfigurationException
    {
        fireIDriverStateListenerDriverConfiguring();
	info.validateConfig(config);
	try
	{
            configure(config,info);
        }
        catch(Exception e)
	{
            e.printStackTrace();
            throw new WrongConfigurationException(20);
        }
    }    
            
    private boolean[] selected = null;
    private String startDate = "";
    private String endDate = "";
    
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException 
    {
        if(dataSource != null)
        {
            dataSource = null;
        }
        
        dataSource = new MeteoDataProducer(this);        
        
        this.config=config;
        this.info=info;
        
        selected = MathUtils.integerToBinaryBoolean(Integer.parseInt(config.getSelectedHardwareParameter("Sensor").getParameterValue().trim()), 8);
        
        String startHour = config.getSelectedHardwareParameter("StartHour").getParameterValue().trim();
        String startDay = config.getSelectedHardwareParameter("StartDay").getParameterValue().trim();
        String startMonth = config.getSelectedHardwareParameter("StartMonth").getParameterValue().trim();
        String startYear = config.getSelectedHardwareParameter("StartYear").getParameterValue().trim();
        String endHour = config.getSelectedHardwareParameter("EndHour").getParameterValue().trim();
        String endDay = config.getSelectedHardwareParameter("EndDay").getParameterValue().trim();
        String endMonth = config.getSelectedHardwareParameter("EndMonth").getParameterValue().trim();
        String endYear = config.getSelectedHardwareParameter("EndYear").getParameterValue().trim();
        
        startDate = startYear + "-" + startMonth + "-" + startDay + " " + startHour + ":0:0";
        endDate = endYear + "-" + endMonth + "-" + endDay + " " + endHour + ":59:59";
        
        String res = config.getSelectedHardwareParameter("Resolution").getParameterValue();
        int resolution = 0;
        if(res.trim().equalsIgnoreCase("Daily"))
        {
            resolution = dataSource.DAILY;
        }
        else if(res.trim().equalsIgnoreCase("Hourly"))
        {
            resolution = dataSource.HOURLY;
        }
        else if(res.trim().equalsIgnoreCase("Monthly"))
        {
            resolution = dataSource.MONTHLY;
        }        
        else
        {
            resolution = dataSource.YEARLY;
        }                
        
        int nSamples = dataSource.checkNSamples(startDate, endDate, resolution);                
                
        config.setTotalSamples(nSamples);
        for(int i=0; i<config.getChannelsConfig().length; i++)
        {
            config.getChannelsConfig(i).setTotalSamples(nSamples);
        }                        
        dataSource.setAcquisitionHeader(config);
        
        fireIDriverStateListenerDriverConfigured();
    }
    
    public void extraValidateConfig(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException 
    {
        /**not going to use*/
    }
    
    public String getDriverUniqueID() 
    {
        return DRIVER_UNIQUE_ID;
    }
    
    private MeteoDataSource source = null;
    
    public void init(HardwareInfo info)     
    {                        
        SQLConnector sqlc = new SQLConnector(SQLConnector.MYSQL);
        conn = sqlc.connect("192.168.0.111/meteo", "root", "");                
                
        source = new MeteoDataSource(conn);
        
        System.out.println("INITED!");
        fireIDriverStateListenerDriverInited();        
    }
    
    public Float[] getRainArray()
    {
        return source.getRainArray();
    }
    
    public void reset(HardwareInfo info) throws IncorrectStateException 
    {
        /**Reset in not supported in webrobot*/
    }
    
    public void shutdown() 
    {
        if(dataSource!=null)
        {
            dataSource.shutdown();
        }
        super.shutDownNow();
    }
    
    public IDataSource start(HardwareInfo info) throws IncorrectStateException 
    {
        if(dataSource!=null)
        {
            fireIDriverStateListenerDriverStarting();    
            //dataSource.setRunning(true);
            dataSource.startProduction(selected);
            fireIDriverStateListenerDriverStarted();     
            return dataSource;
	}
	else
        {
            return null;
        }        
    }
    
    public IDataSource startOutput(HardwareInfo info, IDataSource source) throws IncorrectStateException 
    {
        /**Don't know what is startOutput...*/
        return null;
    }
    
    public void stop(HardwareInfo info) throws IncorrectStateException
    {
        fireIDriverStateListenerDriverStoping();        
        dataSource.stopProduction();
        fireIDriverStateListenerDriverStoped();        
    }    
    
    public Object getHardwareInfo() 
    {
        fireIDriverStateListenerDriverReseting();                
	
	String baseHardwareInfoFile="recresource://pt/utl/ist/elab/driver/meteo/MeteoHardwareInfo.xml";
	String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.Meteo.HardwareInfo"), baseHardwareInfoFile);
	
	if(prop.indexOf("://")==-1)
	    prop="file:///" + System.getProperty("user.dir") + "/" + prop;
	
	java.net.URL url=null;
	try
	{
	    url=new java.net.URL(prop);
            fireIDriverStateListenerDriverReseted();            
	}
        catch(java.net.MalformedURLException e)
	{
	    LoggerUtil.logThrowable("Unable to load resource: " + prop,e,Logger.getLogger("Meteo"));
	    try
	    {
		url=new java.net.URL(baseHardwareInfoFile);
                fireIDriverStateListenerDriverReseted();            
	    }
            catch(java.net.MalformedURLException e2)
	    {
		LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile,e2,Logger.getLogger("WebRobot"));
	    }
	}	
        
        System.out.println("Loading url = " + url);
        
	return url;	
    }        
}
