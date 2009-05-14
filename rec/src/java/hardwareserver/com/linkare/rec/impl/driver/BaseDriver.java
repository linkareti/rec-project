/*
 * BaseDriver.java
 *
 * Created on 9 de Maio de 2003, 18:14
 */

package com.linkare.rec.impl.driver;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class BaseDriver implements com.linkare.rec.impl.driver.IDriver
{

	/** Utility field holding the IDriverStateListener. */
	private transient com.linkare.rec.impl.driver.IDriverStateListener listenerIDriverStateListener =  null;

	/** Creates a new instance of BaseDriver */
	public BaseDriver()
	{
	}

	/** Registers IDriverStateListener to receive events.
	 * @param listener The listener to register.
	 */
	public synchronized void addIDriverStateListener(com.linkare.rec.impl.driver.IDriverStateListener listener) throws java.util.TooManyListenersException
	{
		if (listenerIDriverStateListener != null)
		{
			throw new java.util.TooManyListenersException();
		}
		listenerIDriverStateListener = listener;
	}

	/** Removes IDriverStateListener from the list of listeners.
	 * @param listener The listener to remove.
	 */
	public synchronized void removeIDriverStateListener(com.linkare.rec.impl.driver.IDriverStateListener listener)
	{
		listenerIDriverStateListener = null;
	}

	/** Notifies the registered listener about the event.
	 *
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverInited()
	{
		if (listenerIDriverStateListener == null) return;
		listenerIDriverStateListener.driverInited();
	}

	/** Notifies the registered listener about the event.
	 *
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverConfiguring()
	{
		if (listenerIDriverStateListener == null) return;
		listenerIDriverStateListener.driverConfiguring();
	}

	/** Notifies the registered listener about the event.
	 *
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverConfigured()
	{
		if (listenerIDriverStateListener == null) return;
		listenerIDriverStateListener.driverConfigured();
	}

	/** Notifies the registered listener about the event.
	 *
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverStarting()
	{
		if (listenerIDriverStateListener == null) return;
		listenerIDriverStateListener.driverStarting();
	}

	/** Notifies the registered listener about the event.
	 *
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverStarted()
	{
		if (listenerIDriverStateListener == null) return;
		listenerIDriverStateListener.driverStarted();
	}

	/** Notifies the registered listener about the event.
	 *
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverStoping()
	{
		if (listenerIDriverStateListener == null) return;
		listenerIDriverStateListener.driverStoping();
	}

	/** Notifies the registered listener about the event.
	 *
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverStoped()
	{
		if (listenerIDriverStateListener == null) return;
		listenerIDriverStateListener.driverStoped();
	}

	/** Notifies the registered listener about the event.
	 *
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverReseting()
	{
		if (listenerIDriverStateListener == null) return;
		listenerIDriverStateListener.driverReseting();
	}

	/** Notifies the registered listener about the event.
	 *
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverReseted()
	{
		if (listenerIDriverStateListener == null) return;
		listenerIDriverStateListener.driverReseted();
	}

	/** Notifies the registered listener about the event.
	 *
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	protected void fireIDriverStateListenerDriverShutdown()
	{
		if (listenerIDriverStateListener == null) return;
		listenerIDriverStateListener.driverShutdown();
	}

	public void shutDownNow()
	{
		fireIDriverStateListenerDriverShutdown();
	}

	public void config(HardwareAcquisitionConfig config, HardwareInfo info) throws IncorrectStateException, WrongConfigurationException
	{
		fireIDriverStateListenerDriverConfiguring();
		info.validateConfig(config);
		extraValidateConfig(config,info);
		try
		{
		    configure(config,info);
		}catch(Exception e)
		{
		    e.printStackTrace();
		    throw new WrongConfigurationException(20);
		}
	}
	
	public abstract void extraValidateConfig(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException;
	public abstract void configure(HardwareAcquisitionConfig config, HardwareInfo info) throws WrongConfigurationException;
}
