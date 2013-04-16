package pt.utl.ist.elab.driver.vquantum;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.IDataSource;

/**
 * 
 * @author nomead
 */

public class QuantumDriver extends VirtualBaseDriver {

	// private static final Logger LOGGER =
	// Logger.getLogger(QuantumDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "MECANICA_QUANTICA_V1.0";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of CGDriver */
	public QuantumDriver() {
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
}
