/*
 * RadioactividadeStampDriver.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.serial.stamp.momentoInercia;

import javax.comm.*;
import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.threading.*;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;
import com.linkare.rec.acquisition.*;
import com.linkare.rec.data.config.*;
import com.linkare.rec.data.*;
import com.linkare.rec.data.synch.*;
import com.linkare.rec.data.metadata.*;
import com.linkare.rec.impl.logging.*;
import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.threading.*;
import java.util.logging.*;
import pt.utl.ist.elab.driver.serial.stamp.*;
import pt.utl.ist.elab.driver.serial.stamp.transproc.*;
import pt.utl.ist.elab.driver.serial.stamp.transproc.processors.*;
import pt.utl.ist.elab.driver.serial.stamp.momentoInercia.processors.*;
import pt.utl.ist.elab.driver.serial.stamp.momentoInercia.translators.*;
import com.linkare.rec.impl.protocols.ReCProtocols;
/**
 *
 * @author  jp
 */
public class MomInerciaStampDriver extends AbstractStampDriver
{
    private StampCommand stampConfig=null;
        
    /** Creates a new instance of RadioactividadeStampDriver */
    public MomInerciaStampDriver()
    {	
	setDriverUniqueID("EXP_ANGULAR_STAMP_V1.0");
	setApplicationNameLockPort("Momento Inercia Stamp Driver V0.2");
	setTimeOutPerPort(10000);
	setPortBaudRate(38400);
	loadCommandHandlers();
    }
    
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException
    {
	
	fireIDriverStateListenerDriverConfiguring();
	
	stampConfig=new StampCommand(CONFIG_OUT_STRING);

	stampConfig.addCommandData(StampConfigTranslator.ITER_STR,
	new String(
	Defaults.defaultIfEmpty(
	config.getSelectedHardwareParameterValue(StampConfigTranslator.ITER_STR),
	info.getHardwareParameterValue(StampConfigTranslator.ITER_STR)
	)
	)
	);
                        
	stampConfig.addCommandData(StampConfigTranslator.LAUNCH_STR,
	new Integer(
	Defaults.defaultIfEmpty(
	config.getSelectedHardwareParameterValue(StampConfigTranslator.LAUNCH_STR),
	info.getHardwareParameterValue(StampConfigTranslator.LAUNCH_STR)
	)
	)
	);
	
	stampConfig.addCommandData(StampConfigTranslator.STOP_STR,
	new Integer(
	Defaults.defaultIfEmpty(
	config.getSelectedHardwareParameterValue(StampConfigTranslator.STOP_STR),
	info.getHardwareParameterValue(StampConfigTranslator.STOP_STR)
	)
	)
	);
			
	stampConfig.addCommandData(StampConfigTranslator.TBS_STR,
	    new Integer((int)config.getSelectedFrequency().getFrequency())
	);
	
	stampConfig.addCommandData(StampConfigTranslator.NUMSAMPLES_STR,
	    new Integer(config.getTotalSamples()));
	
	
	
	StampTranslator translator=StampTranslatorProcessorManager.getTranslator(stampConfig);
	if(!translator.translate(stampConfig))
	    throw new WrongConfigurationException("Cannot translate StampCommand!", -1);
	
	config.getChannelsConfig(0).setTotalSamples(config.getTotalSamples());
	config.getChannelsConfig(1).setTotalSamples(config.getTotalSamples());
	config.getChannelsConfig(2).setTotalSamples(config.getTotalSamples());
	
	this.config=config;
	
	fireIDriverStateListenerDriverConfigured();
    }
    
    public HardwareAcquisitionConfig getAcquisitionHeader()
    {
	return config;
    }
    
    public Object getHardwareInfo()
    {
	
	String baseHardwareInfoFile="recresource://pt/utl/ist/elab/driver/serial/stamp/momentoInercia/MomInerciaBaseHardwareInfo.xml";
	String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.MomInercia.HardwareInfo"),baseHardwareInfoFile);
	
	if(prop.indexOf("://")==-1)
	    prop="file:///" + System.getProperty("user.dir") + "/" + prop;
	
	java.net.URL url=null;
	try
	{
	    url=ReCProtocols.getURL(prop);
	}catch(java.net.MalformedURLException e)
	{
	    LoggerUtil.logThrowable("Unable to load resource: " + prop,e,Logger.getLogger(STAMP_DRIVER_LOGGER));
	    try
	    {
		url=new java.net.URL(baseHardwareInfoFile);
	    }catch(java.net.MalformedURLException e2)
	    {
		LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile,e2,Logger.getLogger(STAMP_DRIVER_LOGGER));
	    }
	}
	
	
	return url;
	
    }
    
    public AbstractStampDataSource initDataSource()
    {
	MomInerciaStampDataSource dataSource=new MomInerciaStampDataSource();
	dataSource.setAcquisitionHeader(getAcquisitionHeader());
	return dataSource;
    }
    
    protected void loadExtraCommandHandlers()
    {
	String packageName=getClass().getPackage().getName()+".";
	StampTranslatorProcessorManager.initStampProcessorsTranslators(
	new String[]
	{
	    packageName+"processors.StampMomInerciaProcessor",
	    packageName+"translators.StampConfigTranslator",
	}
	);
    }
    
    public void processCommand(StampCommand cmd)
    {
	if(cmd==null || cmd.getCommandIdentifier()==null)
	{
	    Logger.getLogger(STAMP_DRIVER_LOGGER).log(Level.INFO,"Can not interpret command " +cmd);
	    return;
	}
	
	if(cmd.getCommandIdentifier().equals(ID_STR))
	{
	    if(waitingStart)
	    {
		waitingStart=false;
		writeMessage(stampConfig.getCommand());
		wroteStart=false;
		fireIDriverStateListenerDriverStarting();
	    }
	    else if(stoping)
	    {
		stoping=false;
		fireIDriverStateListenerDriverStoping();
		super.stopDataSource();
	    }
	    else if(reseting)
	    {
		reseting=false;
		fireIDriverStateListenerDriverReseted();
		super.stopDataSource();
	    }
	    else if(started)
	    {
		started=false;
		fireIDriverStateListenerDriverStoping();
		fireIDriverStateListenerDriverStoped();
		super.stopDataSource();
	    }
	    else if(initing)
	    {
		initing=false;
		fireIDriverStateListenerDriverReseting();
		fireIDriverStateListenerDriverReseted();
	    }
	}
	else if(cmd.getCommandIdentifier().equals(StampStartProcessor.COMMAND_IDENTIFIER))
	{
	    started=true;
	    fireIDriverStateListenerDriverStarted();
	    DateTime dt=new DateTime();
	    config.getChannelsConfig(0).setTimeStart(dt);
	    config.getChannelsConfig(1).setTimeStart(dt);
	    config.getChannelsConfig(2).setTimeStart(dt);
	    config.setTimeStart(dt);
	    /*if(dataSource!=null)
		dataSource.setRunning(true);*/
	    
	}
	else if(cmd.getCommandIdentifier().equals(StampConfiguredProcessor.COMMAND_IDENTIFIER))
	{
	    //OK to go...  - still gonna receive start...!
	}
	else if(cmd.getCommandIdentifier().equals(StampNotConfiguredProcessor.COMMAND_IDENTIFIER))
	{
	    if(waitingStart  && wroteStart)
	    {
		waitingStart=false;
		fireIDriverStateListenerDriverStoped();
		super.stopDataSource();
	    }
	    else if(started)
	    {
		started=false;
		fireIDriverStateListenerDriverReseting();
		fireIDriverStateListenerDriverReseted();
		super.stopDataSource();
	    }
	}
	
    }
    
    private boolean initing=true;
    private boolean waitingStart=false;
    private boolean wroteStart=false;
    private boolean waitingStop=false;
    private boolean started=false;
    private boolean stoping=false;
    private boolean reseting=true;
    
    public void startNow() throws TimedOutException
    {
	if(stampConfig==null)
	    throw new TimedOutException("No configuration available yet!");
	
	waitingStart=true;
	
	WaitForConditionResult.waitForConditionTrue(
	new AbstractConditionDecisor()
	{
	    public int getConditionResult()
	    {
		if(!waitingStart)
		    return IConditionDecisor.CONDITION_MET_TRUE;
		
		return IConditionDecisor.CONDITION_NOT_MET;
	    }
	}
	,20000,500); 
    }        
}
