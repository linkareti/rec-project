package pt.utl.ist.elab.driver.paschen;


import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.BaseDriver;
import com.linkare.rec.impl.driver.IDataSource;

/**
 * @author jloureiro
 */

public class PaschenDriver extends BaseDriver {

	//	private static final Logger LOGGER = Logger.getLogger(PaschenDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "PLASMALAB_PASCHEN_CURVE_V1.0";

	protected PaschenDataProducer dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of PaschenDriver */
	public PaschenDriver() {
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		this.config = config;
		this.info = info;
		
		final double volt_ini = Float.parseFloat(config.getSelectedHardwareParameterValue("volt_ini"));
		final double volt_fin = Float.parseFloat(config.getSelectedHardwareParameterValue("volt_fin"));
		final double volt_step = Float.parseFloat(config.getSelectedHardwareParameterValue("volt_step"));
		final double press_set = Float.parseFloat(config.getSelectedHardwareParameterValue("press_set"));
		
		dataSource = new PaschenDataProducer(this, volt_ini, volt_fin, volt_step, press_set);
		
		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
		}

		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}


	public String getDriverUniqueID() {
		return PaschenDriver.DRIVER_UNIQUE_ID;
	}


	public void shutdown() {
		if (dataSource != null) {
			dataSource.stopNow();
		}
		super.shutDownNow();
	}


	public IDataSource start(final HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStarting();
		dataSource.startProduction();
		fireIDriverStateListenerDriverStarted();
		return dataSource;
	}

	public void stop(final HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStoping();
		fireIDriverStateListenerDriverStoped();
	}
	
	public void stop() throws InterruptedException {
		fireIDriverStateListenerDriverStoping();
		fireIDriverStateListenerDriverStoped();
	}

	@Override
	public void init(HardwareInfo info) {
		fireIDriverStateListenerDriverInited();
	}

	@Override
	public IDataSource startOutput(HardwareInfo info, IDataSource source) throws IncorrectStateException {
		return null;
	}

	@Override
	public void reset(HardwareInfo info) throws IncorrectStateException {	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void extraValidateConfig(HardwareAcquisitionConfig config, HardwareInfo info)
			throws WrongConfigurationException {
		/** not going to use */
		
	}

}
