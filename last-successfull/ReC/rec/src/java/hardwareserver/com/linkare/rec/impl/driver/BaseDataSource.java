/*
 * BaseDataSource.java
 *
 * Created on 9 de Maio de 2003, 18:15
 */

package com.linkare.rec.impl.driver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.DiscardablePhysicsValueMatrix;
import com.linkare.rec.impl.data.SamplesReadException;
import com.linkare.rec.impl.data.SamplesSourceEventListener;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class BaseDataSource implements IDataSource {
	private DiscardablePhysicsValueMatrix samples = null;

	/** Holds value of property name. */
	private String name = "";

	/** Holds value of property packetSize. */
	private int packetSize = 1;

	/** Utility field holding the IDataSourceListener. */
	private transient IDataSourceListener listenerIDataSourceListener = null;

	/** Creates a new instance of BaseDataSource */
	public BaseDataSource() {
		samples = new DiscardablePhysicsValueMatrix();
	}

	/**
	 * Getter for property name.
	 * 
	 * @return Value of property name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setter for property name.
	 * 
	 * @param name New value of property name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for property packetSize.
	 * 
	 * @return Value of property packetSize.
	 */
	public int getPacketSize() {
		return this.packetSize;
	}

	/**
	 * Setter for property packetSize.
	 * 
	 * @param packetSize New value of property packetSize.
	 */
	public void setPacketSize(int packetSize) {
		this.packetSize = packetSize;
	}

	public PhysicsValue[][] getSamples(int sampleIndexStart, int sampleIndexEnd) throws SamplesReadException {
		return samples.getSamples(sampleIndexStart, sampleIndexEnd);
	}

	public void addDataRows(PhysicsValue[][] dataSamples) {
		samples.addDataRows(dataSamples);
	}

	public void addDataRow(PhysicsValue[] dataSample) {
		samples.addDataRows(dataSample);
	}

	private HardwareAcquisitionConfig config = null;

	/** Utility field used by event firing mechanism. */
	private javax.swing.event.EventListenerList listenerList = null;

	public HardwareAcquisitionConfig getAcquisitionHeader() {
		return config;
	}

	public void setAcquisitionHeader(HardwareAcquisitionConfig config) {
		this.config = config;
		if (config != null)
			setTotalSamples(config.getTotalSamples());
	}

	public int getTotalSamples() {
		return samples.getTotalSamples();
	}

	public void setTotalSamples(int totalSamples) {
		samples.setTotalSamples(totalSamples);
	}

	/**
	 * Registers IDataSourceListener to receive events.
	 * 
	 * @param listener The listener to register.
	 * 
	 */
	public void addIDataSourceListener(IDataSourceListener listener) {
		listenerIDataSourceListener = listener;
	}

	/**
	 * Removes IDataSourceListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 * 
	 */
	public void removeIDataSourceListener(IDataSourceListener listener) {
		listenerIDataSourceListener = null;
	}

	/**
	 * Notifies the registered listener about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 * 
	 */
	private void fireDataSourceWaiting() {
		if (listenerIDataSourceListener == null)
			return;
		listenerIDataSourceListener.dataSourceWaiting();
	}

	/**
	 * Notifies the registered listener about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 * 
	 */
	private void fireDataSourceStarted() {
		if (listenerIDataSourceListener == null)
			return;
		listenerIDataSourceListener.dataSourceStarted();
	}

	/**
	 * Notifies the registered listener about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 * 
	 */
	private void fireDataSourceEnded() {
		if (listenerIDataSourceListener == null)
			return;
		listenerIDataSourceListener.dataSourceEnded();
	}

	/**
	 * Notifies the registered listener about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 * 
	 */
	private void fireDataSourceStoped() {
		if (listenerIDataSourceListener == null)
			return;
		listenerIDataSourceListener.dataSourceStoped();
	}

	/**
	 * Notifies the registered listener about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 * 
	 */
	private void fireDataSourceError() {
		if (listenerIDataSourceListener == null)
			return;
		listenerIDataSourceListener.dataSourceError();
	}

	/**
	 * Registers SamplesSourceEventListener to receive events.
	 * 
	 * @param listener The listener to register.
	 * 
	 */
	public void addSamplesSourceEventListener(SamplesSourceEventListener listener) {
		samples.addSamplesSourceEventListener(listener);
	}

	/**
	 * Removes SamplesSourceEventListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 * 
	 */
	public void removeSamplesSourceEventListener(SamplesSourceEventListener listener) {
		samples.removeSamplesSourceEventListener(listener);
	}

	public int getLastSampleNum() {
		return samples.getLastSampleNum();
	}

	public void setDataSourceStarted() {
		fireDataSourceStarted();
	}

	public void setDataSourceStoped() {
		fireDataSourceStoped();
	}

	public void setDataSourceEnded() {
		fireDataSourceEnded();
	}
}
