/*
 * RadioactividadeStampDriver.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.usb.cypress.gamma;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.usb.cypress.AbstractCypressDataSource;
import pt.utl.ist.elab.driver.usb.cypress.AbstractCypressDriver;
import pt.utl.ist.elab.driver.usb.cypress.gamma.translators.CypressConfigTranslator;
import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressCommand;
import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressTranslator;
import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressTranslatorProcessorManager;
import pt.utl.ist.elab.driver.usb.cypress.transproc.processors.CypressConfiguredProcessor;
import pt.utl.ist.elab.driver.usb.cypress.transproc.processors.CypressNotConfiguredProcessor;
import pt.utl.ist.elab.driver.usb.cypress.transproc.processors.CypressStartProcessor;

import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.IConditionDecisor;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.threading.WaitForConditionResult;
import com.linkare.rec.impl.utils.Defaults;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class GammaCypressDriver extends AbstractCypressDriver
{
    private CypressCommand cypressConfig=null;
    
    /** Creates a new instance of RadioactividadeStampDriver */
    public GammaCypressDriver()
    {
	super();
	setDriverUniqueID("ELAB_GAMMA_V1.0");
        setAlternateSetting((short)1);
        setVendorID((short)0x0547);
        setProductID((short)0x2132);
        setInterfaceNumber((short)0);
        setInputChannelNumber((byte)IN_2_BUF);
        setOutputChannelNumber((byte)OUT_2_BUF);
	loadCommandHandlers();
    }
    
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException
    {
	
	fireIDriverStateListenerDriverConfiguring();
	
	cypressConfig=new CypressCommand(CONFIG_OUT_STRING);
	
	cypressConfig.addCommandData(CypressConfigTranslator.VOLUME_STR,
	new Integer(
	Defaults.defaultIfEmpty(
	config.getSelectedHardwareParameterValue(CypressConfigTranslator.VOLUME_STR),
	info.getHardwareParameterValue(CypressConfigTranslator.VOLUME_STR)
	)
	)
	);
        
        cypressConfig.addCommandData(CypressConfigTranslator.FREQ_STR,
	    new Integer((int)config.getSelectedFrequency().getFrequency()));
	
	cypressConfig.addCommandData(CypressConfigTranslator.NUMSAMPLES_STR,
	    new Integer(config.getTotalSamples()));
	
	CypressTranslator translator = CypressTranslatorProcessorManager.getTranslator(cypressConfig);
	if(!translator.translate(cypressConfig))
	    throw new WrongConfigurationException("Cannot translate StampCommand!",-1);
	
	config.getChannelsConfig(0).setTotalSamples(config.getTotalSamples());
	config.getChannelsConfig(1).setTotalSamples(config.getTotalSamples());
        config.getChannelsConfig(2).setTotalSamples(config.getTotalSamples());
        config.getChannelsConfig(3).setTotalSamples(config.getTotalSamples());
        
	
	this.config=config;
	
	fireIDriverStateListenerDriverConfigured();
    }
    
    public HardwareAcquisitionConfig getAcquisitionHeader()
    {
	return config;
    }
    
    public Object getHardwareInfo()
    {
	
	String baseHardwareInfoFile="recresource://pt/utl/ist/elab/driver/usb/cypress/gamma/GammaHardwareInfo.xml";
	String prop=Defaults.defaultIfEmpty(System.getProperty("eLab.Gamma.HardwareInfo"),baseHardwareInfoFile);
	
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
	GammaCypressDataSource dataSource = new GammaCypressDataSource();
	dataSource.setAcquisitionHeader(getAcquisitionHeader());
	return dataSource;
    }
    
    protected void loadExtraCommandHandlers()
    {
	String packageName=getClass().getPackage().getName()+".";
	CypressTranslatorProcessorManager.initCypressProcessorsTranslators(
	new String[]
	{
	    packageName+"processors.CypressGammaProcessor1",
            packageName+"processors.CypressGammaProcessor2",            
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
