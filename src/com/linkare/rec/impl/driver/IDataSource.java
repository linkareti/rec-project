/*
 * IDataSource.java
 *
 * Created on 9 de Maio de 2003, 18:09
 */

package com.linkare.rec.impl.driver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.SamplesSource;

/**
 *
 * @author  jp
 */
public interface IDataSource extends SamplesSource
{
	public int getPacketSize();
	public int getTotalSamples();
	public String getName();
	
	public HardwareAcquisitionConfig getAcquisitionHeader();
	
	public void stopNow();
	/** Registers IDataSourceListener to receive events.
	 * @param listener The listener to register.
	 */
	public void addIDataSourceListener(IDataSourceListener listener);
	
	/** Removes IDataSourceListener from the list of listeners.
	 * @param listener The listener to remove.
	 */
	public void removeIDataSourceListener(IDataSourceListener listener);
	
}
