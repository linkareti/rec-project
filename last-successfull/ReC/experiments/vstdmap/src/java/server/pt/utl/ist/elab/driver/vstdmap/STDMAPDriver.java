package pt.utl.ist.elab.driver.vstdmap;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.IDataSource;

/**
 * 
 * @author Antonio Jose Rodrigues Figueiredo
 */

public class STDMAPDriver extends VirtualBaseDriver {

	// private static final Logger LOGGER =
	// Logger.getLogger(STDMAPDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "STANDARD_MAP_V1.0";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of CGDriver */
	public STDMAPDriver() {
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

}
