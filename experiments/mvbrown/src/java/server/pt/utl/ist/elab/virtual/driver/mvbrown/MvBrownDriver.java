/*
 * MvBrownDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.virtual.driver.mvbrown;

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

public class MvBrownDriver extends VirtualBaseDriver {

	private static String MvBrown_DRIVER_LOGGER = "MvBrown.Logger";
	static {
		Logger l = LogManager.getLogManager().getLogger(MvBrown_DRIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(MvBrown_DRIVER_LOGGER));
		}
	}

	/* Hardware and driver related variables */
	private static final String APPLICATION_IDENTIFIER = "E-Lab (Movimento Browniano Driver)";
	private static final String DRIVER_UNIQUE_ID = "MOVIMENTO_BROWNIANO_V1.0";
	private static final String HW_VERSION = "0.1";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of CGDriver */
	public MvBrownDriver() {
	}

	public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException,
			WrongConfigurationException {
		fireIDriverStateListenerDriverConfiguring();
		info.validateConfig(config);
		extraValidateConfig(config, info);
		try {
			configure(config, info);
		} catch (Exception e) {
			LoggerUtil.logThrowable("Error on config...", e, Logger.getLogger(MvBrown_DRIVER_LOGGER));
			throw new WrongConfigurationException();
		}
	}

	public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException {
		this.config = config;
		this.info = info;

		int tbs = (int) config.getSelectedFrequency().getFrequency();
		int nSamples = config.getTotalSamples();

		byte dim = Byte.parseByte(config.getSelectedHardwareParameterValue("dim"));
		int nPart = Integer.parseInt(config.getSelectedHardwareParameterValue("nPart"));
		int w = Integer.parseInt(config.getSelectedHardwareParameterValue("w"));
		int h = Integer.parseInt(config.getSelectedHardwareParameterValue("h"));

		boolean conPts = config.getSelectedHardwareParameterValue("conPts").trim().equals("1") ? true : false;
		boolean anima = config.getSelectedHardwareParameterValue("animaAct").trim().equals("1") ? true : false;

		dataSource = new MvBrownDataProducer(this, nPart, dim, w, h, conPts, anima, tbs, nSamples);

		((MvBrownDataProducer) dataSource).initializeGraphs(
				new String[] { config.getSelectedHardwareParameterValue("graph1"),
						config.getSelectedHardwareParameterValue("graph2"),
						config.getSelectedHardwareParameterValue("graph3"),
						config.getSelectedHardwareParameterValue("graph4") }, new boolean[] {
						config.getSelectedHardwareParameterValue("graph1Med").trim().equals("1") ? true : false,
						config.getSelectedHardwareParameterValue("graph2Med").trim().equals("1") ? true : false,
						config.getSelectedHardwareParameterValue("graph3Med").trim().equals("1") ? true : false,
						config.getSelectedHardwareParameterValue("graph4Med").trim().equals("1") ? true : false });

		((MvBrownDataProducer) dataSource).initializeGenPosGraph(config.getSelectedHardwareParameterValue("x").trim()
				.equals("1") ? true : false, config.getSelectedHardwareParameterValue("y").trim().equals("1") ? true
				: false, config.getSelectedHardwareParameterValue("z").trim().equals("1") ? true : false, config
				.getSelectedHardwareParameterValue("posModulus").trim().equals("1") ? true : false, true, config
				.getSelectedHardwareParameterValue("posQuad").trim().equals("1") ? true : false, false);

		((MvBrownDataProducer) dataSource).initializeGenVelGraph(config.getSelectedHardwareParameterValue("vx").trim()
				.equals("1") ? true : false, config.getSelectedHardwareParameterValue("vy").trim().equals("1") ? true
				: false, config.getSelectedHardwareParameterValue("vz").trim().equals("1") ? true : false, config
				.getSelectedHardwareParameterValue("velModulus").trim().equals("1") ? true : false, true, config
				.getSelectedHardwareParameterValue("velQuad").trim().equals("1") ? true : false, false);

		if (config.getSelectedHardwareParameterValue("langevin").trim().equals("1") ? true : false) {

			double[] vel = new double[3];
			if (dim == 2) {
				double velMod = Float.parseFloat(config.getSelectedHardwareParameterValue("velMod"));
				double velTheta = Float.parseFloat(config.getSelectedHardwareParameterValue("velTheta"));
				vel = new double[] { velMod * Math.cos(velTheta), velMod * Math.sin(velTheta), 0 };
			} else if (dim == 3) {
				double velMod = Float.parseFloat(config.getSelectedHardwareParameterValue("velMod"));
				double velTheta = Float.parseFloat(config.getSelectedHardwareParameterValue("velTheta"));
				double velPhi = Float.parseFloat(config.getSelectedHardwareParameterValue("velPhi"));
				vel = new double[] { velMod * Math.cos(velTheta) * Math.sin(velPhi),
						velMod * Math.sin(velTheta) * Math.sin(velPhi), velMod * Math.cos(velPhi) };
			} else {
				vel = new double[] { Float.parseFloat(config.getSelectedHardwareParameterValue("velMod")), 0, 0 };
			}

			((MvBrownDataProducer) dataSource).initializeLangevin(Float.parseFloat(config
					.getSelectedHardwareParameterValue("mass")), new double[] {
					Float.parseFloat(config.getSelectedHardwareParameterValue("radius")),
					Float.parseFloat(config.getSelectedHardwareParameterValue("vis")) }, new double[] {
					Float.parseFloat(config.getSelectedHardwareParameterValue("dP")),
					Float.parseFloat(config.getSelectedHardwareParameterValue("freq")) }, vel, false);

		} else {
			((MvBrownDataProducer) dataSource).initializeRandomSimul(Float.parseFloat(config
					.getSelectedHardwareParameterValue("d")), false);
		}

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
		String baseHardwareInfoFile = "recresource://pt/utl/ist/elab/virtual/driver/mvbrown/MvBrownBaseHardwareInfo.xml";
		String prop = Defaults.defaultIfEmpty(System.getProperty("eLab.MvBrown.HardwareInfo"), baseHardwareInfoFile);

		if (prop.indexOf("://") == -1)
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;

		java.net.URL url = null;
		try {
			url = ReCProtocols.getURL(prop);
		} catch (java.net.MalformedURLException e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e, Logger.getLogger(MvBrown_DRIVER_LOGGER));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2, Logger
						.getLogger(MvBrown_DRIVER_LOGGER));
			}
		}
		fireIDriverStateListenerDriverReseted();
		return url;
	}
}
