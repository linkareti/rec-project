package pt.utl.ist.elab.driver.vtiro;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.IDataSource;

/**
 * @author nomead
 */

public class TiroDriver extends VirtualBaseDriver {

	//	private static final Logger LOGGER = Logger.getLogger(TiroDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "TIRO_V1.0";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of TiroDriver */
	public TiroDriver() {
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		this.config = config;
		this.info = info;

		final double g = Float.parseFloat(config.getSelectedHardwareParameterValue("g"));
		final double w = Float.parseFloat(config.getSelectedHardwareParameterValue("w"));
		final double h = Float.parseFloat(config.getSelectedHardwareParameterValue("h"));
		final double v = Float.parseFloat(config.getSelectedHardwareParameterValue("v"));
		final double theta = Float.parseFloat(config.getSelectedHardwareParameterValue("theta"));

		dataSource = new TiroDataProducer(this, w, h, v, theta, g);

		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
		}

		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}

	@Override
	public String getDriverUniqueID() {
		return TiroDriver.DRIVER_UNIQUE_ID;
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
