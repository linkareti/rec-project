/* 
 * PaschenDriver.java created on 23 Aug 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.driver.paschen;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.rollpaper.RollPaperDataSource;
import pt.utl.ist.elab.driver.rollpaper.RollPaperDriver;

import com.linkare.net.protocols.Protocols;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.BaseDriver;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.impl.threading.TimedOutException;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author João Loureiro - ELAB
 */
public class PaschenDriver extends BaseDriver {

	private static final Logger LOGGER = Logger.getLogger(PaschenDriver.class.getName());

	/* Hardware and driver related variables */
	private static final String DRIVER_UNIQUE_ID = "PASCHEN_V1.0";

	protected PaschenDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of PaschenDriver */
	public PaschenDriver() {
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getHardwareInfo() {

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
			LOGGER.log(Level.SEVERE, "Unable to load resource: " + prop, e);
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (final java.net.MalformedURLException e2) {
				LOGGER.log(Level.SEVERE, "Unable to load resource: " + baseHardwareInfoFile, e2);
			}
		}

		return url;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDriverUniqueID() {
		return DRIVER_UNIQUE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(HardwareInfo info) {
		fireIDriverStateListenerDriverInited();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IDataSource start(HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStarting();
		dataSource.startProduction();
		fireIDriverStateListenerDriverStarted();
		return dataSource;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IDataSource startOutput(HardwareInfo info, IDataSource source) throws IncorrectStateException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop(HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStoping();
		dataSource.stopNow();
		fireIDriverStateListenerDriverStoped();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset(HardwareInfo info) throws IncorrectStateException {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void shutdown() {
		if (dataSource != null) {
			dataSource.stopNow();
		}
		super.shutDownNow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void extraValidateConfig(HardwareAcquisitionConfig config, HardwareInfo info)
			throws WrongConfigurationException {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException,
	IncorrectStateException, TimedOutException {

		this.config = config;
		this.info = info;
		
		final int tbs = (int) config.getSelectedFrequency().getFrequency();
		final int nSamples = config.getTotalSamples();
		
		final float userPressLow  = Float.parseFloat(config.getSelectedHardwareParameterValue("userPressLow"));
		final float userPressHigh = Float.parseFloat(config.getSelectedHardwareParameterValue("userPressHigh"));
		final float userVoltLow   = Float.parseFloat(config.getSelectedHardwareParameterValue("userVoltLow"));
		final float userVoltHigh  = Float.parseFloat(config.getSelectedHardwareParameterValue("userVoltHigh"));
		final float userPressStep   = Float.parseFloat(config.getSelectedHardwareParameterValue("userPressStep"));
		final float userVoltStep  = Float.parseFloat(config.getSelectedHardwareParameterValue("userVoltStep"));
		
		dataSource = new PaschenDataSource(this, userPressLow, userPressHigh, userVoltLow, userVoltHigh, userPressStep, userVoltStep, tbs, nSamples);
		
		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
		}
		
		dataSource.setAcquisitionHeader(config);
		
		fireIDriverStateListenerDriverConfigured();
	}

}
