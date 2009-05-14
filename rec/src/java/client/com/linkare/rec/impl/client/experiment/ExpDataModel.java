/*
 * ExpDataModel.java
 *
 * Created on 7 de Maio de 2003, 12:00
 */

package com.linkare.rec.impl.client.experiment;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.data.config.ChannelAcquisitionConfig;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.wrappers.DataProducerWrapper;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public interface ExpDataModel
{
	/** Registers ExpDataModelListener to receive events.
	 * @param listener The listener to register.
	 */
	public void addExpDataModelListener(ExpDataModelListener listener);

	/** Removes ExpDataModelListener from the list of listeners.
	 * @param listener The listener to remove.
	 */
	public void removeExpDataModelListener(ExpDataModelListener listener);

	/** Getter for property channelCount.
	 * @return Value of property channelCount.
	 */
	public int getChannelCount();

	/** Getter for property totalSamples.
	 * @return Value of property totalSamples.
	 */
	public int getTotalSamples();

	public HardwareAcquisitionConfig getAcquisitionConfig();
	public ChannelAcquisitionConfig getChannelConfig(String channelName);
	public ChannelAcquisitionConfig getChannelConfig(int channelIndex);
	public String getChannelName(int channelIndex);
	public int getChannelIndex(String channelName);


	public com.linkare.rec.data.synch.DateTime getTimeStamp(int sampleIndex);

	public com.linkare.rec.data.acquisition.PhysicsValue getValueAt(int sampleIndex,int channelIndex);


	public String getApparatusName();

	
	public void pause();
	public void play();
	public void stopNow();

	public boolean isRunning();


	public void setDpwDataSource(DataProducerWrapper dpw) throws MaximumClientsReached;

	/** Getter for property dataAvailable.
	 * @return Value of property dataAvailable.
	 */
	public boolean isDataAvailable();

}
