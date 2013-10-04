package pt.utl.ist.elab.driver.vcartpole;

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

public class CartPoleDriver extends VirtualBaseDriver {

	// private static final Logger LOGGER =
	// Logger.getLogger(CartPoleDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "CART_POLE_V1.0";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of CartPoleDriver */
	public CartPoleDriver() {
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

		final int failNLaps = Integer.parseInt(config.getSelectedHardwareParameterValue("failNLaps"));
		final int failN = Integer.parseInt(config.getSelectedHardwareParameterValue("failN"));
		final int failTime = Integer.parseInt(config.getSelectedHardwareParameterValue("failTime"));

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
}
