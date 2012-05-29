/*
 * RobotDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.vsemiconductor;

import java.util.logging.Logger;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.BaseDriver;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.net.protocols.Protocols;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author Andr�
 */

public class SDriver extends BaseDriver {
	/* Hardware and driver related variables */
	private static final String APPLICATION_IDENTIFIER = "E-Lab (Semiconductor)";
	private static final String DRIVER_UNIQUE_ID = "SCONDUCTOR_V1.0";
	private static final String HW_VERSION = "0.1";

	protected SDataProducer dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	protected String flowChartString = null;

	/** Creates a new instance of RobotDriver */
	public SDriver() {
	}

	public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException,
			WrongConfigurationException {
		fireIDriverStateListenerDriverConfiguring();
		info.validateConfig(config);
		extraValidateConfig(config, info);
		try {
			configure(config, info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WrongConfigurationException();
		}
	}

	public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {
		this.config = config;
		this.info = info;

		double d1 = Double.parseDouble(config.getSelectedHardwareParameterValue("N Dadores INI"));
		double d2 = Double.parseDouble(config.getSelectedHardwareParameterValue("N Dadores END"));
		double a1 = Double.parseDouble(config.getSelectedHardwareParameterValue("N Aceitadores INI"));
		double a2 = Double.parseDouble(config.getSelectedHardwareParameterValue("N Aceitadores END"));
		double t1 = Double.parseDouble(config.getSelectedHardwareParameterValue("Temp INI"));
		double t2 = Double.parseDouble(config.getSelectedHardwareParameterValue("Temp END"));
		double v1 = Double.parseDouble(config.getSelectedHardwareParameterValue("V ext INI"));
		double v2 = Double.parseDouble(config.getSelectedHardwareParameterValue("V ext END"));

		String material = config.getSelectedHardwareParameterValue("Material");
		String juncao = config.getSelectedHardwareParameterValue("Juncao");

		int tbs = (int) config.getSelectedFrequency().getFrequency();
		int nSamples = config.getTotalSamples();

		dataSource = new SDataProducer(this);

		dataSource.setJuncao(juncao);
		dataSource.setMaterial(material);
		dataSource.setNumAmostras(nSamples);
		dataSource.setTempoAmostras(tbs);
		dataSource.configureAceitadores((a1 != a2), a1, a2);
		dataSource.configureDadores((d1 != d2), d1, d2);
		dataSource.configurePotencial((v1 != v2), v1, v2);
		dataSource.configureTemperatura((t1 != t2), t1, t2);

		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
		}

		dataSource.setTotalSamples(nSamples);
		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}

	public void extraValidateConfig(HardwareAcquisitionConfig config, HardwareInfo info)
			throws WrongConfigurationException {
		/** not going to use */
	}

	public String getDriverUniqueID() {
		return DRIVER_UNIQUE_ID;
	}

	public void init(HardwareInfo info) {
		fireIDriverStateListenerDriverInited();
	}

	public void reset(HardwareInfo info) throws IncorrectStateException {
		/** Reset in not supported in webrobot */
	}

	public void shutdown() {
		if (dataSource != null) {
			dataSource.shutdown();
		}
		super.shutDownNow();
	}

	public IDataSource start(HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStarting();
		dataSource.startProduction();
		fireIDriverStateListenerDriverStarted();
		return dataSource;
	}

	public IDataSource startOutput(HardwareInfo info, IDataSource source) throws IncorrectStateException {
		/** Don't know what is startOutput... */
		return null;
	}

	public void stop(HardwareInfo info) throws IncorrectStateException {
		dataSource.stopNow();
	}

	public Object getHardwareInfo() {
		fireIDriverStateListenerDriverReseting();
		String baseHardwareInfoFile = "recresource://"+getClass().getPackage().getName().replaceAll("\\.","/")+"/HardwareInfo.xml";
		String prop = Defaults.defaultIfEmpty(System.getProperty("HardwareInfo"), baseHardwareInfoFile);

		if (prop.indexOf("://") == -1)
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;

		java.net.URL url = null;
		try {
			url = Protocols.getURL(prop);
		} catch (java.net.MalformedURLException e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e, Logger.getLogger("S.Logger"));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2, Logger
						.getLogger("S.Logger"));
			}
		}
		fireIDriverStateListenerDriverReseted();
		return url;
	}

	public void setStoping() {
		fireIDriverStateListenerDriverStoping();
	}

	public void setStoped() {
		fireIDriverStateListenerDriverStoped();
	}
}
