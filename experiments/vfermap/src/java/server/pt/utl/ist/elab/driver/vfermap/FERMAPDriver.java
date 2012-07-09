package pt.utl.ist.elab.driver.vfermap;

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

public class FERMAPDriver extends VirtualBaseDriver {

	// private static final Logger LOGGER =
	// Logger.getLogger(FERMAPDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "FERMI_MAP_V1.0";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of CGDriver */
	public FERMAPDriver() {
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

}
