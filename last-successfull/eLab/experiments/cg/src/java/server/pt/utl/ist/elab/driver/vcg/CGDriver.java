/*
 * CGDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.vcg;

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
 * @author nomead
 */

public class CGDriver extends VirtualBaseDriver {

	private static String CG_DRIVER_LOGGER = "CG.Logger";
	static {
		Logger l = LogManager.getLogManager().getLogger(CG_DRIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(CG_DRIVER_LOGGER));
		}
	}

	/* Hardware and driver related variables */
	private static final String APPLICATION_IDENTIFIER = "E-Lab (Cavendish-G Driver)";
	private static final String DRIVER_UNIQUE_ID = "CAVENDISH_G_V1.0";
	private static final String HW_VERSION = "0.1";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of CGDriver */
	public CGDriver() {
	}

	public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException,
			WrongConfigurationException {
		fireIDriverStateListenerDriverConfiguring();
		info.validateConfig(config);
		extraValidateConfig(config, info);
		try {
			configure(config, info);
		} catch (Exception e) {
			LoggerUtil.logThrowable("Error on config...", e, Logger.getLogger(CG_DRIVER_LOGGER));
			throw new WrongConfigurationException();
		}
	}

	public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {
		this.config = config;
		this.info = info;

		// boolean expGType =
		// Boolean.getBoolean(config.getSelectedHardwareParameterValue("expGType"));
		boolean expGType = config.getSelectedHardwareParameterValue("expGType").trim().equals("1") ? true : false;

		int angInit = Integer.parseInt(config.getSelectedHardwareParameterValue("angInit"));
		float s0 = Float.parseFloat(config.getSelectedHardwareParameterValue("s0"));
		float d = Float.parseFloat(config.getSelectedHardwareParameterValue("d"));
		float mm0 = Float.parseFloat(config.getSelectedHardwareParameterValue("mm0"));
		float mm1 = Float.parseFloat(config.getSelectedHardwareParameterValue("mm1"));
		float mM0 = Float.parseFloat(config.getSelectedHardwareParameterValue("mM0"));
		float mM1 = Float.parseFloat(config.getSelectedHardwareParameterValue("mM1"));

		float c = Float.parseFloat(config.getSelectedHardwareParameterValue("c"));
		float k = Float.parseFloat(config.getSelectedHardwareParameterValue("k"));
		float g = Float.parseFloat(config.getSelectedHardwareParameterValue("g"));

		int tbs = (int) config.getSelectedFrequency().getFrequency();
		int nSamples = config.getTotalSamples();

		dataSource = new CGDataProducer(this, expGType, angInit, s0, d, mm0, mm1, mM0, mM1, new double[] { c, k, g },
				tbs, nSamples);

		for (int i = 0; i < config.getChannelsConfig().length; i++)
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());

		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}

	public String getDriverUniqueID() {
		return DRIVER_UNIQUE_ID;
	}

	public void shutdown() {
		if (dataSource != null) {
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
		String baseHardwareInfoFile = "recresource://pt/utl/ist/elab/virtual/driver/cg/CGBaseHardwareInfo.xml";
		String prop = Defaults.defaultIfEmpty(System.getProperty("eLab.CG.HardwareInfo"), baseHardwareInfoFile);

		if (prop.indexOf("://") == -1)
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;

		java.net.URL url = null;
		try {
			url = ReCProtocols.getURL(prop);
		} catch (java.net.MalformedURLException e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e, Logger.getLogger(CG_DRIVER_LOGGER));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2, Logger
						.getLogger(CG_DRIVER_LOGGER));
			}
		}
		fireIDriverStateListenerDriverReseted();
		return url;
	}
}
