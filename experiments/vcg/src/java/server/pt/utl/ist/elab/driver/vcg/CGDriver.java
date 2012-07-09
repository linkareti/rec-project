package pt.utl.ist.elab.driver.vcg;

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

public class CGDriver extends VirtualBaseDriver {

	// private static final Logger LOGGER =
	// Logger.getLogger(CGDriver.class.getName());

	private static final String DRIVER_UNIQUE_ID = "CAVENDISH_G_V1.0";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of CGDriver */
	public CGDriver() {
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		this.config = config;
		this.info = info;

		// boolean expGType =
		// Boolean.getBoolean(config.getSelectedHardwareParameterValue("expGType"));
		final boolean expGType = config.getSelectedHardwareParameterValue("expGType").trim().equals("1") ? true : false;

		final int angInit = Integer.parseInt(config.getSelectedHardwareParameterValue("angInit"));
		final float s0 = Float.parseFloat(config.getSelectedHardwareParameterValue("s0"));
		final float d = Float.parseFloat(config.getSelectedHardwareParameterValue("d"));
		final float mm0 = Float.parseFloat(config.getSelectedHardwareParameterValue("mm0"));
		final float mm1 = Float.parseFloat(config.getSelectedHardwareParameterValue("mm1"));
		final float mM0 = Float.parseFloat(config.getSelectedHardwareParameterValue("mM0"));
		final float mM1 = Float.parseFloat(config.getSelectedHardwareParameterValue("mM1"));

		final float c = Float.parseFloat(config.getSelectedHardwareParameterValue("c"));
		final float k = Float.parseFloat(config.getSelectedHardwareParameterValue("k"));
		final float g = Float.parseFloat(config.getSelectedHardwareParameterValue("g"));

		final int tbs = (int) config.getSelectedFrequency().getFrequency();
		final int nSamples = config.getTotalSamples();

		dataSource = new CGDataProducer(this, expGType, angInit, s0, d, mm0, mm1, mM0, mM1, new double[] { c, k, g },
				tbs, nSamples);

		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
		}

		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}

	@Override
	public String getDriverUniqueID() {
		return CGDriver.DRIVER_UNIQUE_ID;
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
