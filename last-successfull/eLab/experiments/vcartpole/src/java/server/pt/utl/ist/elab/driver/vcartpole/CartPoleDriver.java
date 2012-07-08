/*
 * CartPoleDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.vcartpole;

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
import com.linkare.net.protocols.Protocols;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author nomead
 */

public class CartPoleDriver extends VirtualBaseDriver {

	private static String CartPole_DRIVER_LOGGER = "CartPole.Logger";
	static {
		final Logger l = LogManager.getLogManager().getLogger(CartPoleDriver.CartPole_DRIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(CartPoleDriver.CartPole_DRIVER_LOGGER));
		}
	}

	/* Hardware and driver related variables */
	private static final String APPLICATION_IDENTIFIER = "E-Lab (Cart Pole Driver)";
	private static final String DRIVER_UNIQUE_ID = "CART_POLE_V1.0";
	private static final String HW_VERSION = "0.1";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of CartPoleDriver */
	public CartPoleDriver() {
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
			LoggerUtil.logThrowable("Error on config...", e, Logger.getLogger(CartPoleDriver.CartPole_DRIVER_LOGGER));
			throw new WrongConfigurationException();
		}
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		this.config = config;
		this.info = info;

		final int tbs = (int) config.getSelectedFrequency().getFrequency();
		final int nSamples = config.getTotalSamples();

		final double uCart = Float.parseFloat(config.getSelectedHardwareParameterValue("uCart"));
		final double uPole = Float.parseFloat(config.getSelectedHardwareParameterValue("uPole"));

		final double g = Float.parseFloat(config.getSelectedHardwareParameterValue("g"));

		final int kp = Integer.parseInt(config.getSelectedHardwareParameterValue("kp"));
		final int ki = Integer.parseInt(config.getSelectedHardwareParameterValue("ki"));
		final int kd = Integer.parseInt(config.getSelectedHardwareParameterValue("kd"));

		final boolean failActive = (config.getSelectedHardwareParameterValue("failActive").trim().equals("1") ? true
				: false);
		final int failNLaps = Integer.parseInt(config.getSelectedHardwareParameterValue("failNLaps"));
		final int failN = Integer.parseInt(config.getSelectedHardwareParameterValue("failN"));
		final int failTime = Integer.parseInt(config.getSelectedHardwareParameterValue("failTime"));

		final boolean sucActive = (config.getSelectedHardwareParameterValue("sucActive").trim().equals("1") ? true
				: false);
		final int sucAngle = Integer.parseInt(config.getSelectedHardwareParameterValue("sucAngle"));
		final int sucTime = Integer.parseInt(config.getSelectedHardwareParameterValue("sucTime"));
		final double xMax = Float.parseFloat(config.getSelectedHardwareParameterValue("xMax"));

		final double mCart = Float.parseFloat(config.getSelectedHardwareParameterValue("mCart"));
		final double mPole = Float.parseFloat(config.getSelectedHardwareParameterValue("mPole"));

		final double x = Float.parseFloat(config.getSelectedHardwareParameterValue("x"));
		final double xdot = Float.parseFloat(config.getSelectedHardwareParameterValue("xdot"));
		final double theta = Float.parseFloat(config.getSelectedHardwareParameterValue("theta"));
		final double thetadot = Float.parseFloat(config.getSelectedHardwareParameterValue("thetadot"));
		final double poleLength = Float.parseFloat(config.getSelectedHardwareParameterValue("poleLength"));
		final double action = Float.parseFloat(config.getSelectedHardwareParameterValue("action"));

		dataSource = new CartPoleDataProducer(this, x, xdot, theta, thetadot, new double[] { uCart, uPole },
				new double[] { mCart, mPole }, g, poleLength, action, tbs, nSamples);
		((CartPoleDataProducer) dataSource).initializePID(kp, ki, kd);
		((CartPoleDataProducer) dataSource).initializeFailure(failN, xMax, failTime, failNLaps);
		((CartPoleDataProducer) dataSource).initializeSuccess(sucAngle, sucTime);

		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
		}

		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}

	@Override
	public String getDriverUniqueID() {
		return CartPoleDriver.DRIVER_UNIQUE_ID;
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
			url = Protocols.getURL(prop);
		} catch (final java.net.MalformedURLException e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e,
					Logger.getLogger(CartPoleDriver.CartPole_DRIVER_LOGGER));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (final java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2,
						Logger.getLogger(CartPoleDriver.CartPole_DRIVER_LOGGER));
			}
		}
		fireIDriverStateListenerDriverReseted();
		return url;
	}
}
