package pt.utl.ist.elab.driver.rollpaper;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.BaseDriver;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author Bruno Catarino - Linkare TI
 */
public class RollPaperDriver extends BaseDriver {

    public static String ROLLPAPER_DRIVER_LOGGER = "Rollpaper.Logger";

    private static Logger log;

    static {
	final Logger l = LogManager.getLogManager().getLogger(ROLLPAPER_DRIVER_LOGGER);
	if (l == null) {
	    LogManager.getLogManager().addLogger(Logger.getLogger(ROLLPAPER_DRIVER_LOGGER));
	}
    }

    /* Hardware and driver related variables */
    private static final String APPLICATION_IDENTIFIER = "E-Lab (Rollpaper Driver)";
    private static final String DRIVER_UNIQUE_ID = "ROLLPAPER_V1.0";
    private static final String HW_VERSION = "0.1";

    protected RollPaperDataSource dataSource = null;
    protected HardwareAcquisitionConfig config = null;
    protected HardwareInfo info = null;

    /** Creates a new instance of RollPaperDriver */
    public RollPaperDriver() {
    }

    @Override
    public Object getHardwareInfo() {

	final String baseHardwareInfoFile = "recresource://" + getClass().getPackage().getName().replaceAll("\\.", "/") + "/HardwareInfo.xml";
	String prop = Defaults.defaultIfEmpty(System.getProperty("HardwareInfo"), baseHardwareInfoFile);

	if (prop.indexOf("://") == -1) {
	    prop = "file:///" + System.getProperty("user.dir") + "/" + prop;
	}

	java.net.URL url = null;
	try {
	    url = ReCProtocols.getURL(prop);
	} catch (final java.net.MalformedURLException e) {
	    LoggerUtil.logThrowable("Unable to load resource: " + prop, e, Logger.getLogger(ROLLPAPER_DRIVER_LOGGER));
	    try {
		url = new java.net.URL(baseHardwareInfoFile);
	    } catch (final java.net.MalformedURLException e2) {
		LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2, Logger.getLogger(ROLLPAPER_DRIVER_LOGGER));
	    }
	}

	return url;
    }

    @Override
    public String getDriverUniqueID() {
	return DRIVER_UNIQUE_ID;
    }

    @Override
    public void init(HardwareInfo info) {
	fireIDriverStateListenerDriverInited();
    }

    @Override
    public IDataSource start(HardwareInfo info) throws IncorrectStateException {
	fireIDriverStateListenerDriverStarting();
	dataSource.startProduction();
	fireIDriverStateListenerDriverStarted();
	return dataSource;
    }

    @Override
    public IDataSource startOutput(HardwareInfo info, IDataSource source) throws IncorrectStateException {
	return null;
    }

    @Override
    public void stop(HardwareInfo info) throws IncorrectStateException {
	fireIDriverStateListenerDriverStoping();
	dataSource.stopNow();
	fireIDriverStateListenerDriverStoped();
    }

    @Override
    public void reset(HardwareInfo info) throws IncorrectStateException {
    }

    @Override
    public void shutdown() {
	if (dataSource != null) {
	    dataSource.stopNow();
	}
	super.shutDownNow();
    }

    @Override
    public void extraValidateConfig(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {
    }

    @Override
    public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException, IncorrectStateException, TimedOutException {

	this.config = config;
	this.info = info;

	final int tbs = (int) config.getSelectedFrequency().getFrequency();
	final int nSamples = config.getTotalSamples();

	final float baseHeight1 = Float.parseFloat(config.getSelectedHardwareParameterValue("baseHeight1"));
	final float baseHeight2 = Float.parseFloat(config.getSelectedHardwareParameterValue("baseHeight2"));
	final float g = Float.parseFloat(config.getSelectedHardwareParameterValue("g"));
	final float outerRadius = Float.parseFloat(config.getSelectedHardwareParameterValue("outerRadius"));
	final float innerRadius = Float.parseFloat(config.getSelectedHardwareParameterValue("innerRadius"));

	dataSource = new RollPaperDataSource(this, baseHeight1, baseHeight2, g, outerRadius, innerRadius, tbs, nSamples);

	for (int i = 0; i < config.getChannelsConfig().length; i++) {
	    config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
	}

	dataSource.setAcquisitionHeader(config);

	fireIDriverStateListenerDriverConfigured();
    }

    public void stopVirtualHardware() {
	fireIDriverStateListenerDriverStoping();
	fireIDriverStateListenerDriverStoped();
    }
}