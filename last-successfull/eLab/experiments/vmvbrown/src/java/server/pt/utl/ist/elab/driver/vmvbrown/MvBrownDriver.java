/*
 * MvBrownDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.vmvbrown;

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

public class MvBrownDriver extends VirtualBaseDriver {

	// private static final Logger LOGGER =
	// Logger.getLogger(CartPoleDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "MOVIMENTO_BROWNIANO_V1.0";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of CGDriver */
	public MvBrownDriver() {
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		this.config = config;
		this.info = info;

		final int tbs = (int) config.getSelectedFrequency().getFrequency();
		final int nSamples = config.getTotalSamples();

		final byte dim = Byte.parseByte(config.getSelectedHardwareParameterValue("dim"));
		final int nPart = Integer.parseInt(config.getSelectedHardwareParameterValue("nPart"));
		final int w = Integer.parseInt(config.getSelectedHardwareParameterValue("w"));
		final int h = Integer.parseInt(config.getSelectedHardwareParameterValue("h"));

		final boolean conPts = config.getSelectedHardwareParameterValue("conPts").trim().equals("1") ? true : false;
		final boolean anima = config.getSelectedHardwareParameterValue("animaAct").trim().equals("1") ? true : false;

		dataSource = new MvBrownDataProducer(this, nPart, dim, w, h, conPts, anima, tbs, nSamples);

		((MvBrownDataProducer) dataSource).initializeGraphs(
				new String[] { config.getSelectedHardwareParameterValue("graph1"),
						config.getSelectedHardwareParameterValue("graph2"),
						config.getSelectedHardwareParameterValue("graph3"),
						config.getSelectedHardwareParameterValue("graph4") }, new boolean[] {
						config.getSelectedHardwareParameterValue("graph1Med").trim().equals("1") ? true : false,
						config.getSelectedHardwareParameterValue("graph2Med").trim().equals("1") ? true : false,
						config.getSelectedHardwareParameterValue("graph3Med").trim().equals("1") ? true : false,
						config.getSelectedHardwareParameterValue("graph4Med").trim().equals("1") ? true : false });

		((MvBrownDataProducer) dataSource).initializeGenPosGraph(config.getSelectedHardwareParameterValue("x").trim()
				.equals("1") ? true : false, config.getSelectedHardwareParameterValue("y").trim().equals("1") ? true
				: false, config.getSelectedHardwareParameterValue("z").trim().equals("1") ? true : false, config
				.getSelectedHardwareParameterValue("posModulus").trim().equals("1") ? true : false, true, config
				.getSelectedHardwareParameterValue("posQuad").trim().equals("1") ? true : false, false);

		((MvBrownDataProducer) dataSource).initializeGenVelGraph(config.getSelectedHardwareParameterValue("vx").trim()
				.equals("1") ? true : false, config.getSelectedHardwareParameterValue("vy").trim().equals("1") ? true
				: false, config.getSelectedHardwareParameterValue("vz").trim().equals("1") ? true : false, config
				.getSelectedHardwareParameterValue("velModulus").trim().equals("1") ? true : false, true, config
				.getSelectedHardwareParameterValue("velQuad").trim().equals("1") ? true : false, false);

		if (config.getSelectedHardwareParameterValue("langevin").trim().equals("1") ? true : false) {

			double[] vel = new double[3];
			if (dim == 2) {
				final double velMod = Float.parseFloat(config.getSelectedHardwareParameterValue("velMod"));
				final double velTheta = Float.parseFloat(config.getSelectedHardwareParameterValue("velTheta"));
				vel = new double[] { velMod * Math.cos(velTheta), velMod * Math.sin(velTheta), 0 };
			} else if (dim == 3) {
				final double velMod = Float.parseFloat(config.getSelectedHardwareParameterValue("velMod"));
				final double velTheta = Float.parseFloat(config.getSelectedHardwareParameterValue("velTheta"));
				final double velPhi = Float.parseFloat(config.getSelectedHardwareParameterValue("velPhi"));
				vel = new double[] { velMod * Math.cos(velTheta) * Math.sin(velPhi),
						velMod * Math.sin(velTheta) * Math.sin(velPhi), velMod * Math.cos(velPhi) };
			} else {
				vel = new double[] { Float.parseFloat(config.getSelectedHardwareParameterValue("velMod")), 0, 0 };
			}

			((MvBrownDataProducer) dataSource).initializeLangevin(
					Float.parseFloat(config.getSelectedHardwareParameterValue("mass")),
					new double[] { Float.parseFloat(config.getSelectedHardwareParameterValue("radius")),
							Float.parseFloat(config.getSelectedHardwareParameterValue("vis")) },
					new double[] { Float.parseFloat(config.getSelectedHardwareParameterValue("dP")),
							Float.parseFloat(config.getSelectedHardwareParameterValue("freq")) }, vel, false);

		} else {
			((MvBrownDataProducer) dataSource).initializeRandomSimul(
					Float.parseFloat(config.getSelectedHardwareParameterValue("d")), false);
		}

		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
		}

		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}

	@Override
	public String getDriverUniqueID() {
		return MvBrownDriver.DRIVER_UNIQUE_ID;
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
