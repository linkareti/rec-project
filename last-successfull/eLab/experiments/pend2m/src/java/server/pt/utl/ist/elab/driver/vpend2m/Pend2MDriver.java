/*
 * Pend2MDriver.java
 *
 * Created on 27 de Fevereiro de 2005, 8:53
 */

package pt.utl.ist.elab.driver.vpend2m;

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
 * @author Antonio J. R. Figueiredo Last Review : 6/04/2005
 */

public class Pend2MDriver extends VirtualBaseDriver {

	private static String Pend2M_DRIVER_LOGGER = "Pend2M.Logger";
	static {
		Logger l = LogManager.getLogManager().getLogger(Pend2M_DRIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(Pend2M_DRIVER_LOGGER));
		}
	}

	/* Hardware and driver related variables */
	private static final String APPLICATION_IDENTIFIER = "E-Lab (Pendulo-Duplo Motorizado Driver)";
	private static final String DRIVER_UNIQUE_ID = "PENDULO_DUPLO_MOTORIZADO_V1.0";
	private static final String HW_VERSION = "0.1";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of Pend2MDriver */
	public Pend2MDriver() {
	}

	public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException,
			WrongConfigurationException {
		fireIDriverStateListenerDriverConfiguring();
		info.validateConfig(config);
		extraValidateConfig(config, info);
		try {
			configure(config, info);
		} catch (Exception e) {
			LoggerUtil.logThrowable("Error on config...", e, Logger.getLogger(Pend2M_DRIVER_LOGGER));
			throw new WrongConfigurationException();
		}
	}

	public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {
		this.config = config;
		this.info = info;

		int tbs = (int) config.getSelectedFrequency().getFrequency();
		int nSamples = config.getTotalSamples();

		float theta = Float.parseFloat(config.getSelectedHardwareParameterValue("theta"));
		float phi = Float.parseFloat(config.getSelectedHardwareParameterValue("phi"));
		float thetaDot = Float.parseFloat(config.getSelectedHardwareParameterValue("thetaDot"));
		float phiDot = Float.parseFloat(config.getSelectedHardwareParameterValue("phiDot"));
		float l1 = Float.parseFloat(config.getSelectedHardwareParameterValue("l1"));
		float l2 = Float.parseFloat(config.getSelectedHardwareParameterValue("l2"));
		float a = Float.parseFloat(config.getSelectedHardwareParameterValue("a"));
		float fase = Float.parseFloat(config.getSelectedHardwareParameterValue("fase"));
		float w = Float.parseFloat(config.getSelectedHardwareParameterValue("w"));
		float m1 = Float.parseFloat(config.getSelectedHardwareParameterValue("m1"));
		float m2 = Float.parseFloat(config.getSelectedHardwareParameterValue("m2"));
		float g = Float.parseFloat(config.getSelectedHardwareParameterValue("g"));

		dataSource = new Pend2MDataProducer(this, theta, phi, thetaDot, phiDot, l1, l2, m1, m2, w, fase, a, g, tbs,
				nSamples);

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
		String baseHardwareInfoFile = "recresource://pt/utl/ist/elab/virtual/driver/pend2m/Pend2MBaseHardwareInfo.xml";
		String prop = Defaults.defaultIfEmpty(System.getProperty("eLab.Pend2M.HardwareInfo"), baseHardwareInfoFile);

		if (prop.indexOf("://") == -1)
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;

		java.net.URL url = null;
		try {
			url = ReCProtocols.getURL(prop);
		} catch (java.net.MalformedURLException e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e, Logger.getLogger(Pend2M_DRIVER_LOGGER));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2, Logger
						.getLogger(Pend2M_DRIVER_LOGGER));
			}
		}
		fireIDriverStateListenerDriverReseted();
		return url;
	}
}
