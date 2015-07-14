package com.linkare.rec.impl.client.experiment;

import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.MultiCastHardware;
import com.linkare.rec.data.Multiplier;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.data.config.ChannelAcquisitionConfig;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.impl.data.PhysicsValueUtil;
import com.linkare.rec.impl.events.NewSamplesEvent;

/**
 * Client side abstraction of the MVC pattern model for an experiment's data
 * 
 * The implementation actually already caches data, so it is not sensible to
 * implement caches on higher levels, as this will certainly duplicate
 * information
 * 
 * This interface presents a close resemblance with the {@link DataProducer}
 * interface, and its state machine follows the previous one
 * 
 * 
 * @see ExpDataModelListener
 * @see NewSamplesEvent
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public interface ExpDataModel {
	/**
	 * Registers ExpDataModelListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	void addExpDataModelListener(ExpDataModelListener listener);

	/**
	 * Removes ExpDataModelListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	void removeExpDataModelListener(ExpDataModelListener listener);

	/**
	 * Getter for property channelCount.
	 * 
	 * @return Value of property channelCount.
	 */
	int getChannelCount();

	/**
	 * Getter for property totalSamples.
	 * 
	 * @return Value of property totalSamples.
	 */
	int getTotalSamples();

	/**
	 * A way to access the configuration being used for this experiment
	 * 
	 * @return The {@link HardwareAcquisitionConfig} describing this
	 *         experiment's parameters
	 */
	HardwareAcquisitionConfig getAcquisitionConfig();

	/**
	 * A simpler way to access the configuration of a single channel
	 * 
	 * @param channelName The name of the channel. This should be the i18n
	 *            independent name
	 * @return The {@link ChannelAcquisitionConfig} describing the channel with
	 *         the referred name, or null if not found
	 */
	ChannelAcquisitionConfig getChannelConfig(String channelName);

	/**
	 * A way to obtain the configuration of a single channel by its index
	 * 
	 * @param channelIndex the index (zero based) of the channel, according to
	 *            the {@link HardwareInfo} order of described channels
	 * @return The {@link ChannelAcquisitionConfig} describing the channel with
	 *         the referred name
	 */
	ChannelAcquisitionConfig getChannelConfig(int channelIndex);

	/**
	 * A way to obtain the i18n independent channel name according to the
	 * {@link HardwareInfo} order of described channels
	 * 
	 * @param channelIndex the index (zero based) of the channel, according to
	 *            the {@link HardwareInfo} order of described channels
	 * @return The i18n independent name of the channel
	 */
	String getChannelName(int channelIndex);

	/**
	 * A way to obtain the index (according to {@link HardwareInfo} described
	 * order) of a channel by its i18n independent name
	 * 
	 * @param channelName The i18n independent channel name
	 * @return The index of the channel according to {@link HardwareInfo}
	 *         described order
	 */
	int getChannelIndex(String channelName);

	/**
	 * This method calculates the timestamp of a sample, based on its index, the
	 * configured acquisition frequency and the
	 * {@link SamplesPacket#getTimeStart()}
	 * 
	 * @param sampleIndex The index of the sample
	 * @return The {@link DateTime} of the sample at the referenced index
	 */
	com.linkare.rec.data.synch.DateTime getTimeStamp(int sampleIndex);

	/**
	 * This method provides a way to fetch the data value and error value for a
	 * sample at the specific sample index and channel index
	 * 
	 * @param sampleIndex The index of the sample
	 * @param channelIndex The index of the channel
	 * @return The {@link PhysicsValue} at the sample index and channel index.
	 *         It contains both the value, it's error and the applied
	 *         {@link Multiplier}
	 * 
	 * @see PhysicsValueUtil
	 */
	com.linkare.rec.data.acquisition.PhysicsValue getValueAt(int sampleIndex, int channelIndex);

	/**
	 * Access to the underlying hardware familiar name
	 * 
	 * @return the i18n independent familiar name of the apparatus (the hardware
	 *         familiar name, according to the {@link HardwareInfo} structure)
	 */
	String getApparatusName();

	/**
	 * Method to "pause" the acquisition... This does not actually pause the
	 * acquisition at the remote end, but rather stops receiving data and
	 * sending events
	 */
	void pause();

	/**
	 * Method to "resume" the acquisition... This does not actually resume the
	 * acquisition at the remote end, but rather reenables the data receiving
	 * and event communication to the {@link ExpDataModelListener}'s registered
	 */
	void play();

	/**
	 * Method to "stop" the acquisition... This method does not actually stop
	 * the acquisition at the remote end (see
	 * {@link MultiCastHardware#stop(com.linkare.rec.acquisition.UserInfo)} for
	 * that kind of functionality) but rather stops receiving data and
	 * deregisters from the remote endpoint
	 */
	void stopNow();

	/**
	 * Method to determine if remote experiment is still running.
	 * 
	 * @return true if the remote experiment is still acquiring data, or false
	 *         if it is stopped already, because it reached the end of data,
	 *         someone stopped the hardware or there was a remote end error
	 */
	boolean isRunning();

	/**
	 * Getter for property dataAvailable.
	 * 
	 * @return true if there is already data to show at the interface. From the
	 *         moment this method returns true, events are expected to arrive at
	 *         {@link ExpDataModelListener#newSamples(NewExpDataEvent)}. False
	 *         if the experiment does not have data acquired yet
	 */
	boolean isDataAvailable();

	/**
	 * The ID of the experiment, as assigned by the multicast controller
	 * 
	 * @return Returns the object ID of the remote dataproducer, so it is a
	 *         unique ID for the experiment
	 */
	String getDataProducerName();

	/**
	 * @param remoteDataProducer
	 * @throws MaximumClientsReached
	 */
	void setDpwDataSource(DataProducer remoteDataProducer) throws MaximumClientsReached;

}
