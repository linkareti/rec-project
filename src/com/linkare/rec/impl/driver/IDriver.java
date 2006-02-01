/*
 * IDriver.java
 *
 * Created on 9 de Maio de 2003, 18:05
 */

package com.linkare.rec.impl.driver;

import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;

/**
 *
 * @author  jp
 */
public interface IDriver
{
	
	public Object getHardwareInfo(); //might return URL, String or HardwareInfo
	
	public String getDriverUniqueID();

	public void init(HardwareInfo info);
	
	public void config(HardwareAcquisitionConfig config,HardwareInfo info) throws IncorrectStateException,WrongConfigurationException;
	
	public IDataSource start(HardwareInfo info) throws IncorrectStateException;
	
	public IDataSource startOutput(HardwareInfo info,IDataSource source) throws IncorrectStateException;
	
	public void stop(HardwareInfo info) throws IncorrectStateException;
	
	public void reset(HardwareInfo info) throws IncorrectStateException;
	
	public void shutdown();
	
	/** Registers IDriverStateListener to receive events.
	 * @param listener The listener to register.
	 */
	public void addIDriverStateListener(IDriverStateListener listener) throws java.util.TooManyListenersException;

	/** Removes IDriverStateListener from the list of listeners.
	 * @param listener The listener to remove.
	 */
	public void removeIDriverStateListener(IDriverStateListener listener);

}
