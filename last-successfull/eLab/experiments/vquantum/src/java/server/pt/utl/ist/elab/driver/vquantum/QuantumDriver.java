/*
 * QuantumDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.vquantum;

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

public class QuantumDriver extends VirtualBaseDriver {

	private static String Quantum_DRIVER_LOGGER = "Quantum.Logger";
	static {
		final Logger l = LogManager.getLogManager().getLogger(QuantumDriver.Quantum_DRIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(QuantumDriver.Quantum_DRIVER_LOGGER));
		}
	}

	/* Hardware and driver related variables */
	private static final String APPLICATION_IDENTIFIER = "E-Lab (Mecanica Quantica Driver)";
	private static final String DRIVER_UNIQUE_ID = "MECANICA_QUANTICA_V1.0";
	private static final String HW_VERSION = "0.1";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of CGDriver */
	public QuantumDriver() {
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
			LoggerUtil.logThrowable("Error on config...", e, Logger.getLogger(QuantumDriver.Quantum_DRIVER_LOGGER));
			throw new WrongConfigurationException();
		}
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		this.config = config;
		this.info = info;

		final int nSamples = config.getTotalSamples();

		final double x0 = Double.parseDouble(config.getSelectedHardwareParameterValue("x0"));
		final short deltaX = Short.parseShort(config.getSelectedHardwareParameterValue("deltaX"));
		final byte log2N = Byte.parseByte(config.getSelectedHardwareParameterValue("log2N"));
		final short dX0 = Short.parseShort(config.getSelectedHardwareParameterValue("dX0"));

		final String xDt = config.getSelectedHardwareParameterValue("xDt");
		final String nDt = config.getSelectedHardwareParameterValue("nDt");
		final double dt = Double.parseDouble(xDt + "e-" + nDt);

		final String xE = config.getSelectedHardwareParameterValue("xEnergy");
		final String nE = config.getSelectedHardwareParameterValue("nEnergy");
		final double energy = Double.parseDouble(xE + "e" + nE);

		final double tol = Double.parseDouble("1e-" + config.getSelectedHardwareParameterValue("logTol"));

		final String xTbs = config.getSelectedHardwareParameterValue("xTbs");
		final String nTbs = config.getSelectedHardwareParameterValue("nTbs");
		final double tbs = Double.parseDouble(xTbs + "e-" + nTbs);

		final boolean wraparoundKS = config.getSelectedHardwareParameterValue("wraparoundKS").trim().equals("1") ? true
				: false;
		final boolean wraparoundXS = config.getSelectedHardwareParameterValue("wraparoundXS").trim().equals("1") ? true
				: false;
		final boolean tunneling = config.getSelectedHardwareParameterValue("tunneling").trim().equals("1") ? true
				: false;

		dataSource = new QuantumDataProducer(this, dX0, x0, energy, log2N, deltaX, tol, dt, tbs, nSamples,
				wraparoundKS, wraparoundXS, tunneling);
		((QuantumDataProducer) dataSource).configPotentials(config.getSelectedHardwareParameterValue("potentials"));

		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
		}

		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}

	@Override
	public String getDriverUniqueID() {
		return QuantumDriver.DRIVER_UNIQUE_ID;
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
					Logger.getLogger(QuantumDriver.Quantum_DRIVER_LOGGER));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (final java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2,
						Logger.getLogger(QuantumDriver.Quantum_DRIVER_LOGGER));
			}
		}
		fireIDriverStateListenerDriverReseted();
		return url;
	}
}
