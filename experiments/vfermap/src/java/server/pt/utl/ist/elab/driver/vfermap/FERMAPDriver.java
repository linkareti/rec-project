/*
 * FERMAPDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.vfermap;

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

public class FERMAPDriver extends VirtualBaseDriver {

	private static String FERMAP_DRIVER_LOGGER = "FERMAP.Logger";
	static {
		final Logger l = LogManager.getLogManager().getLogger(FERMAPDriver.FERMAP_DRIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(FERMAPDriver.FERMAP_DRIVER_LOGGER));
		}
	}

	/* Hardware and driver related variables */
	private static final String APPLICATION_IDENTIFIER = "E-Lab (Fermi-Map Driver)";
	private static final String DRIVER_UNIQUE_ID = "FERMI_MAP_V1.0";
	private static final String HW_VERSION = "0.1";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of CGDriver */
	public FERMAPDriver() {
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
			LoggerUtil.logThrowable("Error on config...", e, Logger.getLogger(FERMAPDriver.FERMAP_DRIVER_LOGGER));
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
			final int nSamples = Integer.parseInt(config.getSelectedHardwareParameterValue("nCol"));
			;

			final float x = Float.parseFloat(config.getSelectedHardwareParameterValue("x"));
			final float xDot = Float.parseFloat(config.getSelectedHardwareParameterValue("xDot"));
			final float psi = Float.parseFloat(config.getSelectedHardwareParameterValue("psi"));
			final float wFreq = Float.parseFloat(config.getSelectedHardwareParameterValue("psi"));
			final float wAmp = Float.parseFloat(config.getSelectedHardwareParameterValue("wAmp"));
			final float d = Float.parseFloat(config.getSelectedHardwareParameterValue("d"));

			dataSource = new FERMAPDataProducer(this, x, xDot, psi, wFreq, wAmp, d, tbs, nSamples);
		} else {
			final boolean staticImg = config.getSelectedHardwareParameterValue("staticImg").trim().equals("1") ? true
					: false;

			final int w = Integer.parseInt(config.getSelectedHardwareParameterValue("w"));
			final int h = Integer.parseInt(config.getSelectedHardwareParameterValue("h"));
			final byte pixSize = Byte.parseByte(config.getSelectedHardwareParameterValue("pixSize"));

			final int uMax = Integer.parseInt(config.getSelectedHardwareParameterValue("uMax"));

			if (simulType == 1) {
				final float m = Float.parseFloat(config.getSelectedHardwareParameterValue("m"));
				final float pcor = Float.parseFloat(config.getSelectedHardwareParameterValue("pcor"));
				final int iter = Integer.parseInt(config.getSelectedHardwareParameterValue("iter"));

				final float psi = Float.parseFloat(config.getSelectedHardwareParameterValue("psi"));
				final int nPsi = Integer.parseInt(config.getSelectedHardwareParameterValue("nPsi"));
				final float dPsi = Float.parseFloat(config.getSelectedHardwareParameterValue("dPsi"));

				final float uMapa = Float.parseFloat(config.getSelectedHardwareParameterValue("uMapa"));
				final int nUMapa = Integer.parseInt(config.getSelectedHardwareParameterValue("nUMapa"));
				final float dUMapa = Float.parseFloat(config.getSelectedHardwareParameterValue("dUMapa"));

				dataSource = new FERMAPDataProducer(this, m, psi, nPsi, dPsi, uMapa, nUMapa, dUMapa, iter, pcor, w, h,
						pixSize, uMax, staticImg);
			} else if (simulType == 3) {
				final float m = Float.parseFloat(config.getSelectedHardwareParameterValue("m"));
				final float pcor = Float.parseFloat(config.getSelectedHardwareParameterValue("pcor"));
				final int iter = Integer.parseInt(config.getSelectedHardwareParameterValue("iter"));

				final float psi = Float.parseFloat(config.getSelectedHardwareParameterValue("psi"));
				final float uMapa = Float.parseFloat(config.getSelectedHardwareParameterValue("uMapa"));

				dataSource = new FERMAPDataProducer(this, m, psi, uMapa, iter, pcor, w, h, pixSize, uMax, staticImg);
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
		return FERMAPDriver.DRIVER_UNIQUE_ID;
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
					Logger.getLogger(FERMAPDriver.FERMAP_DRIVER_LOGGER));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (final java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2,
						Logger.getLogger(FERMAPDriver.FERMAP_DRIVER_LOGGER));
			}
		}
		fireIDriverStateListenerDriverReseted();
		return url;
	}
}
