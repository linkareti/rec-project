/*
 * STDMAPDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.vstdmap;

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
 * @author Antonio Jose Rodrigues Figueiredo
 */

public class STDMAPDriver extends VirtualBaseDriver {

	private static String STDMAP_DRIVER_LOGGER = "STDMAP.Logger";
	static {
		final Logger l = LogManager.getLogManager().getLogger(STDMAPDriver.STDMAP_DRIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(STDMAPDriver.STDMAP_DRIVER_LOGGER));
		}
	}

	/* Hardware and driver related variables */
	private static final String APPLICATION_IDENTIFIER = "E-Lab (Standard-Map Driver)";
	private static final String DRIVER_UNIQUE_ID = "STANDARD_MAP_V1.0";
	private static final String HW_VERSION = "0.1";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of CGDriver */
	public STDMAPDriver() {
	}

	@Override
	public void config(final HardwareAcquisitionConfig config, final HardwareInfo info) throws IncorrectStateException,
			WrongConfigurationException {
		fireIDriverStateListenerDriverConfiguring();
		info.validateConfig(config);
		extraValidateConfig(config, info);
		try {
			configure(config, info);
		} catch (final Exception e) {
			LoggerUtil.logThrowable("Error on config...", e, Logger.getLogger(STDMAPDriver.STDMAP_DRIVER_LOGGER));
			throw new WrongConfigurationException();
		}
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		this.config = config;
		this.info = info;

		final byte simulType = Byte.parseByte(config.getSelectedHardwareParameterValue("simulType"));

		if (simulType == 2) { // anima
			final int tbs = (int) config.getSelectedFrequency().getFrequency();
			final int nSamples = config.getTotalSamples();

			final float theta = Float.parseFloat(config.getSelectedHardwareParameterValue("theta"));
			final float thetaDot = Float.parseFloat(config.getSelectedHardwareParameterValue("thetaDot"));
			final float mass = Float.parseFloat(config.getSelectedHardwareParameterValue("mass"));
			final float length = Float.parseFloat(config.getSelectedHardwareParameterValue("length"));
			final float force = Float.parseFloat(config.getSelectedHardwareParameterValue("force"));
			System.out.println("Configuring with " + nSamples + "nsamples");
			dataSource = new STDMAPDataProducer(this, theta, thetaDot, length, mass, force, tbs, nSamples);
		} else {
			final boolean staticImg = config.getSelectedHardwareParameterValue("staticImg").trim().equals("1") ? true
					: false;

			final int w = Integer.parseInt(config.getSelectedHardwareParameterValue("w"));
			final int h = Integer.parseInt(config.getSelectedHardwareParameterValue("h"));
			final byte pixSize = Byte.parseByte(config.getSelectedHardwareParameterValue("pixSize"));

			if (simulType == 1) {
				final float k = Float.parseFloat(config.getSelectedHardwareParameterValue("k"));
				final float pcor = Float.parseFloat(config.getSelectedHardwareParameterValue("pcor"));
				final int iter = Integer.parseInt(config.getSelectedHardwareParameterValue("iter"));

				final float theta = Float.parseFloat(config.getSelectedHardwareParameterValue("theta"));
				final int nTheta = Integer.parseInt(config.getSelectedHardwareParameterValue("nTheta"));
				final float dTheta = Float.parseFloat(config.getSelectedHardwareParameterValue("dTheta"));

				final float iMapa = Float.parseFloat(config.getSelectedHardwareParameterValue("iMapa"));
				final int nIMapa = Integer.parseInt(config.getSelectedHardwareParameterValue("nIMapa"));
				final float dIMapa = Float.parseFloat(config.getSelectedHardwareParameterValue("dIMapa"));

				dataSource = new STDMAPDataProducer(this, k, theta, nTheta, dTheta, iMapa, nIMapa, dIMapa, iter, pcor,
						w, h, pixSize, staticImg);
			} else if (simulType == 3) {
				final float k = Float.parseFloat(config.getSelectedHardwareParameterValue("k"));
				final float pcor = Float.parseFloat(config.getSelectedHardwareParameterValue("pcor"));
				final int iter = Integer.parseInt(config.getSelectedHardwareParameterValue("iter"));

				final float theta = Float.parseFloat(config.getSelectedHardwareParameterValue("theta"));
				final float iMapa = Float.parseFloat(config.getSelectedHardwareParameterValue("iMapa"));

				dataSource = new STDMAPDataProducer(this, k, theta, iMapa, iter, pcor, w, h, pixSize, staticImg);
			}
		}
		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
		}

		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}

	@Override
	public String getDriverUniqueID() {
		return STDMAPDriver.DRIVER_UNIQUE_ID;
	}

	@Override
	public void shutdown() {
		if (dataSource != null) {
			dataSource.stopNow();
		}
		super.shutDownNow();
	}

	@Override
	public IDataSource start(final HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStarting();
		dataSource.startProduction();
		fireIDriverStateListenerDriverStarted();
		return dataSource;
	}

	@Override
	public void stop(final HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStoping();
		dataSource.stopNow();
		fireIDriverStateListenerDriverStoped();
	}

	@Override
	public Object getHardwareInfo() {
		fireIDriverStateListenerDriverReseting();
		final String baseHardwareInfoFile = "recresource://" + getClass().getPackage().getName().replaceAll("\\.", "/")
				+ "/HardwareInfo.xml";
		String prop = Defaults.defaultIfEmpty(System.getProperty("HardwareInfo"), baseHardwareInfoFile);

		if (prop.indexOf("://") == -1) {
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;
		}

		java.net.URL url = null;
		try {
			url = ReCProtocols.getURL(prop);
		} catch (final java.net.MalformedURLException e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e,
					Logger.getLogger(STDMAPDriver.STDMAP_DRIVER_LOGGER));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (final java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2,
						Logger.getLogger(STDMAPDriver.STDMAP_DRIVER_LOGGER));
			}
		}
		fireIDriverStateListenerDriverReseted();
		return url;
	}
}
