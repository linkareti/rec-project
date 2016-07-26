/*
 * BaseDriver.java
 *
 * Created on 9 de Maio de 2003, 18:14
 */

package com.linkare.rec.impl.driver;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.net.protocols.Protocols;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.threading.TimedOutException;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class BaseDriver implements com.linkare.rec.impl.driver.IDriver {

	/** Utility field holding the IDriverStateListener. */
	private transient com.linkare.rec.impl.driver.IDriverStateListener listenerIDriverStateListener = null;

	private static final Logger LOGGER = Logger.getLogger(BaseDriver.class.getName());

	/** Creates a new instance of BaseDriver */
	public BaseDriver() {
	}

	/**
	 * Registers IDriverStateListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	@Override
	public synchronized void addIDriverStateListener(final com.linkare.rec.impl.driver.IDriverStateListener listener)
			throws java.util.TooManyListenersException {
		if (listenerIDriverStateListener != null) {
			throw new java.util.TooManyListenersException();
		}
		listenerIDriverStateListener = listener;
	}

	/**
	 * Removes IDriverStateListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	@Override
	public synchronized void removeIDriverStateListener(final com.linkare.rec.impl.driver.IDriverStateListener listener) {
		listenerIDriverStateListener = null;
	}

	/**
	 * Notifies the registered listener about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverInited() {
		if (listenerIDriverStateListener == null) {
			return;
		}
		listenerIDriverStateListener.driverInited();
	}

	/**
	 * Notifies the registered listener about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverConfiguring() {
		if (listenerIDriverStateListener == null) {
			return;
		}
		listenerIDriverStateListener.driverConfiguring();
	}

	/**
	 * Notifies the registered listener about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverConfigured() {
		if (listenerIDriverStateListener == null) {
			return;
		}
		listenerIDriverStateListener.driverConfigured();
	}

	/**
	 * Notifies the registered listener about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverStarting() {
		if (listenerIDriverStateListener == null) {
			return;
		}
		listenerIDriverStateListener.driverStarting();
	}

	/**
	 * Notifies the registered listener about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverStarted() {
		if (listenerIDriverStateListener == null) {
			return;
		}
		listenerIDriverStateListener.driverStarted();
	}

	/**
	 * Notifies the registered listener about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverStoping() {
		if (listenerIDriverStateListener == null) {
			return;
		}
		listenerIDriverStateListener.driverStoping();
	}

	/**
	 * Notifies the registered listener about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverStoped() {
		if (listenerIDriverStateListener == null) {
			return;
		}
		listenerIDriverStateListener.driverStoped();
	}

	/**
	 * Notifies the registered listener about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverReseting() {
		if (listenerIDriverStateListener == null) {
			return;
		}
		listenerIDriverStateListener.driverReseting();
	}

	/**
	 * Notifies the registered listener about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverReseted() {
		if (listenerIDriverStateListener == null) {
			return;
		}
		listenerIDriverStateListener.driverReseted();
	}

	/**
	 * Notifies the registered listener about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverShutdown() {
		if (listenerIDriverStateListener == null) {
			return;
		}
		listenerIDriverStateListener.driverShutdown();
	}

	public void shutDownNow() {
		fireIDriverStateListenerDriverShutdown();
	}

	@Override
	public void config(final HardwareAcquisitionConfig config, final HardwareInfo info) throws IncorrectStateException,
			WrongConfigurationException {
		fireIDriverStateListenerDriverConfiguring();
		try {
			info.validateConfig(config);
			extraValidateConfig(config, info);
			configure(config, info);
		} catch (final WrongConfigurationException e) {
			fireIDriverStateListenerDriverStoped();
			throw e;
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE,e.getMessage(),e);
			throw new WrongConfigurationException(20);
		}
	}

	public abstract void extraValidateConfig(HardwareAcquisitionConfig config, HardwareInfo info)
			throws WrongConfigurationException;

	/**
	 * 
	 * @param config the configuration from the GUI. This is the DTO that
	 *            contains the selected parameters, chosen by the end user.
	 * @param info the configuration parameters for the hardware. It represents
	 *            the XML hardware configuration file specified for an
	 *            experiment.
	 * @throws WrongConfigurationException
	 * @throws IncorrectStateException
	 * @throws TimedOutException
	 */
	public abstract void configure(HardwareAcquisitionConfig config, HardwareInfo info)
			throws WrongConfigurationException, IncorrectStateException, TimedOutException;
	
	
	@Override
	public Object getHardwareInfo() {
		fireIDriverStateListenerDriverReseting();
		final String baseHardwareInfoFile = "recresource://" + getClass().getPackage().getName().replaceAll("\\.", "/")
				+ "/HardwareInfo.xml";
		String prop = ReCSystemProperty.HARDWARE_INFO_FILE.getValue(baseHardwareInfoFile);

		if (prop.indexOf("://") == -1) {
			prop = "file:///" + ReCSystemProperty.USER_DIR.getValue() + "/" + prop;
		}

		java.net.URL url = null;
		try {
			url = Protocols.getURL(prop);
		} catch (final java.net.MalformedURLException e) {

			LOGGER.log(Level.WARNING, "Unable to load resource: " + prop, e);
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (final java.net.MalformedURLException e2) {
				LOGGER.log(Level.SEVERE, "Unable to load resource: " + baseHardwareInfoFile, e);

			}
		}
		fireIDriverStateListenerDriverReseted();
		return url;
	}

}
