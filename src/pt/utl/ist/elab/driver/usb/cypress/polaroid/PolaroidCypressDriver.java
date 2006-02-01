/*
 * RadioactividadeStampDriver.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.usb.cypress.polaroid;

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
import pt.utl.ist.elab.driver.usb.cypress.*;
import pt.utl.ist.elab.driver.usb.cypress.transproc.*;
import pt.utl.ist.elab.driver.usb.cypress.transproc.processors.*;
import pt.utl.ist.elab.driver.usb.cypress.polaroid.processors.*;
import pt.utl.ist.elab.driver.usb.cypress.polaroid.translators.*;
import com.linkare.rec.impl.protocols.ReCProtocols;
/**
 *
 * @author  jp
 */
public class PolaroidCypressDriver extends AbstractCypressDriver
{
    private CypressCommand cypressConfig=null;
    
    /** Creates a new instance of RadioactividadeStampDriver */
    public PolaroidCypressDriver()
    {
	super();
	setDriverUniqueID("ELAB_POLAROID_V1.3");
        setAlternateSetting((short)1);
        setVendorID((short)0x0547);
        setProductID((short)0x2131);
        setInterfaceNumber((short)0);
        setInputChannelNumber((byte)IN_2_BUF);
        setOutputChannelNumber((byte)OUT_2_BUF);
	loadCommandHandlers();
    }
    
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException
    {
	
	fireIDriverStateListenerDriverConfiguring();
	
	cypressConfig=new CypressCommand(CONFIG_OUT_STRING);
	
	cypressConfig.addCommandData(CypressConfigTranslator.POS_INI_POL_STR,
	new Integer(
	Defaults.defaultIfEmpty(
	config.getSelectedHardwareParameterValue(CypressConfigTranslator.POS_INI_POL_STR),
	info.getHardwareParameterValue(CypressConfigTranslator.POS_INI_POL_STR)
	)
	)
	);
        
        cypressConfig.addCommandData(CypressConfigTranslator.POS_END_POL_STR,
	new Integer(
	Defaults.defaultIfEmpty(
	config.getSelectedHardwareParameterValue(CypressConfigTranslator.POS_END_POL_STR),
	info.getHardwareParameterValue(CypressConfigTranslator.POS_END_POL_STR)
	)
	)
	);
        
        cypressConfig.addCommandData(CypressConfigTranslator.POS_FIXO_STR,
	new Integer(
	Defaults.defaultIfEmpty(
	config.getSelectedHardwareParameterValue(CypressConfigTranslator.POS_FIXO_STR),
	info.getHardwareParameterValue(CypressConfigTranslator.POS_FIXO_STR)
	)
	)
	);
        
        cypressConfig.addCommandData(CypressConfigTranslator.LUZ_POL_STR,
	new Integer(
	Defaults.defaultIfEmpty(
	config.getSelectedHardwareParameterValue(CypressConfigTranslator.LUZ_POL_STR),
	info.getHardwareParameterValue(CypressConfigTranslator.LUZ_POL_STR)
	)
	)
	);
        
        cypressConfig.addCommandData(CypressConfigTranslator.CALIB_STR,
	new Integer(
	Defaults.defaultIfEmpty(
	config.getSelectedHardwareParameterValue(CypressConfigTranslator.CALIB_STR),
	info.getHardwareParameterValue(CypressConfigTranslator.CALIB_STR)
	)
	)
	);
	
	cypressConfig.addCommandData(CypressConfigTranslator.NUMSAMPLES_STR,
	    new Integer(config.getTotalSamples()));
	
	CypressTranslator translator = CypressTranslatorProcessorManager.getTranslator(cypressConfig);
	if(!translator.translate(cypressConfig))
	    throw new WrongConfigurationException("Cannot translate StampCommand!",-1);
	
	config.getChannelsConfig(0).setTotalSamples(config.getTotalSamples());
	config.getChannelsConfig(1).setTotalSamples(config.getTotalSamples());
	
	this.config=config;
	
	fireIDriverStateListenerDriverConfigured();
    }
    
    public HardwareAcquisitionConfig getAcquisitionHeader()
    {
	return config;
    }
    
    public Object getHardwareInfo()
    {
	
	String baseHardwareInfoFile="recresource://pt/utl/ist/elab/driver/usb/cypress/polaroid/PolaroidHardwareInfo.xml";
	String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.Polaroid.HardwareInfo"),baseHardwareInfoFile);
	
	if(prop.indexOf("://")==-1)
	    prop="file:///" + System.getProperty("user.dir") + "/" + prop;
	
	java.net.URL url=null;
	try
	{
	    url=ReCProtocols.getURL(prop);
	}catch(java.net.MalformedURLException e)
	{
	    LoggerUtil.logThrowable("Unable to load resource: " + prop,e,Logger.getLogger(CYPRESS_DRIVER_LOGGER));
	    try
	    {
		url=new java.net.URL(baseHardwareInfoFile);
	    }catch(java.net.MalformedURLException e2)
	    {
		LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile,e2,Logger.getLogger(CYPRESS_DRIVER_LOGGER));
	    }
	}
	
	
	return url;
	
    }
    
    public AbstractCypressDataSource initDataSource()
    {
	PolaroidCypressDataSource dataSource = new PolaroidCypressDataSource();
	dataSource.setAcquisitionHeader(getAcquisitionHeader());
	return dataSource;
    }
    
    protected void loadExtraCommandHandlers()
    {
	String packageName=getClass().getPackage().getName()+".";
	CypressTranslatorProcessorManager.initCypressProcessorsTranslators(
	new String[]
	{
	    packageName+"processors.CypressPolaroidProcessor",
	    packageName+"translators.CypressConfigTranslator",
	}
	);
    }
    
    public void processCommand(CypressCommand cmd)
    {
	if(cmd==null || cmd.getCommandIdentifier()==null)
	{
	    Logger.getLogger(CYPRESS_DRIVER_LOGGER).log(Level.INFO,"Can not interpret command " +cmd);
	    return;
	}
	
	if(cmd.getCommandIdentifier().equals(ID_STR))
	{
	    if(waitingStart)
	    {
		waitingStart=false;
		writeMessage(cypressConfig.getCommand());
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
	else if(cmd.getCommandIdentifier().equals(CypressStartProcessor.COMMAND_IDENTIFIER))
	{
	    started=true;
	    fireIDriverStateListenerDriverStarted();
	    config.getChannelsConfig(0).setTimeStart(new DateTime());
	    config.getChannelsConfig(1).setTimeStart(new DateTime());
	    config.setTimeStart(new DateTime());
	    //if(dataSource!=null)
		//dataSource.setRunning(true);
	    
	}
	else if(cmd.getCommandIdentifier().equals(CypressConfiguredProcessor.COMMAND_IDENTIFIER))
	{
	    //OK to go...  - still gonna receive start...!
	}
	else if(cmd.getCommandIdentifier().equals(CypressNotConfiguredProcessor.COMMAND_IDENTIFIER))
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
	if(cypressConfig==null)
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
