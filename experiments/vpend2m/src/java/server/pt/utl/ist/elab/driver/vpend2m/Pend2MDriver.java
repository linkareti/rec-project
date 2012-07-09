/*
 * Pend2MDriver.java
 *
 * Created on 27 de Fevereiro de 2005, 8:53
 */

package pt.utl.ist.elab.driver.vpend2m;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.impl.driver.IDataSource;

/**
 * 
 * @author Antonio J. R. Figueiredo Last Review : 6/04/2005
 */

public class Pend2MDriver extends VirtualBaseDriver {

	// private static final Logger LOGGER =
	// Logger.getLogger(CartPoleDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "PENDULO_DUPLO_MOTORIZADO_V1.0";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of Pend2MDriver */
	public Pend2MDriver() {
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		this.config = config;
		this.info = info;

		final int tbs = (int) config.getSelectedFrequency().getFrequency();
		final int nSamples = config.getTotalSamples();

		final float theta = Float.parseFloat(config.getSelectedHardwareParameterValue("theta"));
		final float phi = Float.parseFloat(config.getSelectedHardwareParameterValue("phi"));
		final float thetaDot = Float.parseFloat(config.getSelectedHardwareParameterValue("thetaDot"));
		final float phiDot = Float.parseFloat(config.getSelectedHardwareParameterValue("phiDot"));
		final float l1 = Float.parseFloat(config.getSelectedHardwareParameterValue("l1"));
		final float l2 = Float.parseFloat(config.getSelectedHardwareParameterValue("l2"));
		final float a = Float.parseFloat(config.getSelectedHardwareParameterValue("a"));
		final float fase = Float.parseFloat(config.getSelectedHardwareParameterValue("fase"));
		final float w = Float.parseFloat(config.getSelectedHardwareParameterValue("w"));
		final float m1 = Float.parseFloat(config.getSelectedHardwareParameterValue("m1"));
		final float m2 = Float.parseFloat(config.getSelectedHardwareParameterValue("m2"));
		final float g = Float.parseFloat(config.getSelectedHardwareParameterValue("g"));

		dataSource = new Pend2MDataProducer(this, theta, phi, thetaDot, phiDot, l1, l2, m1, m2, w, fase, a, g, tbs,
				nSamples);

		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
		}

		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}

	@Override
	public String getDriverUniqueID() {
		return Pend2MDriver.DRIVER_UNIQUE_ID;
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
		config.setTimeStart(new DateTime(System.currentTimeMillis()));
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
