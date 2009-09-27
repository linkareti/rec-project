package com.linkare.rec.impl.data;

import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.data.synch.Frequency;

public class SamplesSourcePacketizer implements SamplesPacketSource {
	private SamplesSource samplesSource = null;
	private Frequency frequency = null;
	private int packetSize = 1;
	private ArrayList<int[]> packetsLocations = null;
	private int totalPackets = 1;
	private int countSamplesCurrentPacket = 0;
	private int currentPacketSampleStartIndex = 0;
	private SamplesSourceAdapter adapter = null;

	/** Utility field used by event firing mechanism. */
	private EventListenerList listenerList = null;

	/** Creates a new instance of DiscardablePhysicsValueMatrixPacketizer */
	public SamplesSourcePacketizer(Frequency freq) {
		this(freq, null, 1, 1);
	}

	/** Creates a new instance of DiscardablePhysicsValueMatrixPacketizer */
	public SamplesSourcePacketizer(Frequency freq, int packetSize, int totalPackets) {
		this(freq, null, packetSize, totalPackets);
	}

	/** Creates a new instance of DiscardablePhysicsValueMatrixPacketizer */
	public SamplesSourcePacketizer(Frequency freq, SamplesSource samplesSource, int packetSize, int totalPackets) {
		setSamplesSource(samplesSource);
		setPacketSize(packetSize);
		setTotalPackets(totalPackets);
		setFrequency(freq);
	}

	public SamplesPacket[] getSamplesPackets(int packetStartIndex, int packetEndIndex)
			throws SamplesPacketReadException {
		if (packetsLocations == null || getSamplesSource() == null)
			throw new SamplesPacketReadException("Error packetizing... probably source is not set yet",
					packetStartIndex);

		if (packetStartIndex > packetEndIndex)
			throw new SamplesPacketReadException("Start index is bigger than end index", packetStartIndex);

		SamplesPacket[] retVal = new SamplesPacket[packetEndIndex - packetStartIndex + 1];

		for (int i = 0; i < retVal.length; i++) {
			int[] packetLocations = (int[]) packetsLocations.get(packetStartIndex + i);
			if (packetLocations == null)
				throw new SamplesPacketReadException("That packet index is not available", packetStartIndex + i);

			PhysicsValue[][] data = null;
			try {
				data = samplesSource.getSamples(packetLocations[0], packetLocations[1]);
			} catch (SamplesReadException e) {
				throw new SamplesPacketReadException("Error reading sample index " + e.getErrorSampleNumber()
						+ " for this packet", packetStartIndex + i);
			}

			retVal[i] = new SamplesPacket(packetStartIndex + i, getTotalPackets(), data);
		}

		return retVal;
	}

	public void finishLastPacket() {
		if (countSamplesCurrentPacket == 0)
			return;

		int[] startAndEndLoc = new int[] { currentPacketSampleStartIndex,
				currentPacketSampleStartIndex + countSamplesCurrentPacket - 1 };

		packetsLocations.add(startAndEndLoc);

		fireNewSamplesPackets(packetsLocations.size() - 1);
	}

	private void refreshState(int samplesLargestIndex) {
		int newSamplesCount = samplesLargestIndex - (currentPacketSampleStartIndex + countSamplesCurrentPacket) + 1;
		if (newSamplesCount < 0)
			return;

		if (countSamplesCurrentPacket + newSamplesCount < getPacketSize()) {
			countSamplesCurrentPacket += newSamplesCount;
			return;
		}

		int notConsumedSamplesCount = newSamplesCount;
		do {
			int[] startAndEndLoc = new int[] { currentPacketSampleStartIndex,
					currentPacketSampleStartIndex + getPacketSize() - 1 };
			packetsLocations.add(startAndEndLoc);
			currentPacketSampleStartIndex = startAndEndLoc[1] + 1;
			notConsumedSamplesCount -= (getPacketSize() - countSamplesCurrentPacket);
			countSamplesCurrentPacket = 0;

		} while (notConsumedSamplesCount >= getPacketSize());

		countSamplesCurrentPacket = notConsumedSamplesCount;

		fireNewSamplesPackets(packetsLocations.size() - 1);
	}

	/**
	 * Registers SamplesPacketSourceEventListener to receive events.
	 * 
	 * @param listener The listener to register.
	 * 
	 */
	public synchronized void addSamplesPacketSourceEventListener(SamplesPacketSourceEventListener listener) {
		if (listenerList == null) {
			listenerList = new EventListenerList();
		}
		listenerList.add(SamplesPacketSourceEventListener.class, listener);
	}

	/**
	 * Removes SamplesPacketSourceEventListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 * 
	 */
	public synchronized void removeSamplesPacketSourceEventListener(SamplesPacketSourceEventListener listener) {
		listenerList.remove(SamplesPacketSourceEventListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 * 
	 */
	private void fireNewSamplesPackets(int packetLargestIndex) {
		System.out.println("--->>>>>>>>>Firing new samples Packet at Samples Source Packetizer refreshState "
				+ packetLargestIndex);

		SamplesPacketSourceEvent event = new SamplesPacketSourceEvent(this, packetLargestIndex);
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SamplesPacketSourceEventListener.class) {
				((SamplesPacketSourceEventListener) listeners[i + 1]).newSamplesPackets(event);
			}
		}
	}

	/**
	 * Getter for property samplesSource.
	 * 
	 * @return Value of property samplesSource.
	 * 
	 */
	public SamplesSource getSamplesSource() {
		return samplesSource;
	}

	/**
	 * Setter for property samplesSource.
	 * 
	 * @param samplesSource New value of property samplesSource.
	 * 
	 */
	public void setSamplesSource(SamplesSource samplesSource) {
		if (this.samplesSource != null && this.samplesSource == samplesSource) {
			return;
		}

		if (this.samplesSource != null && adapter != null) {
			samplesSource.removeSamplesSourceEventListener(adapter);
		}

		this.samplesSource = samplesSource;

		this.packetsLocations = new ArrayList<int[]>(getTotalPackets());
		this.currentPacketSampleStartIndex = 0;
		this.countSamplesCurrentPacket = 0;

		if (this.samplesSource != null) {
			if (adapter == null)
				adapter = new SamplesSourceAdapter();

			this.samplesSource.addSamplesSourceEventListener(adapter);
		}

	}

	/**
	 * Getter for property packetSize.
	 * 
	 * @return Value of property packetSize.
	 * 
	 */
	public int getPacketSize() {
		return packetSize;
	}

	/**
	 * Setter for property packetSize.
	 * 
	 * @param packetSize New value of property packetSize.
	 * 
	 */
	public void setPacketSize(int packetSize) {
		if (packetSize <= 0)
			return;
		this.packetSize = packetSize;
		refreshState(currentPacketSampleStartIndex + countSamplesCurrentPacket);
	}

	/**
	 * Getter for property totalPackets.
	 * 
	 * @return Value of property totalPackets.
	 * 
	 */
	public int getTotalPackets() {
		return totalPackets;
	}

	/**
	 * Setter for property totalPackets.
	 * 
	 * @param totalPackets New value of property totalPackets.
	 * 
	 */
	public void setTotalPackets(int totalPackets) {
		if (totalPackets <= 0)
			return;

		if (packetsLocations != null) {
			ArrayList<int[]> tempPacketsLocations = packetsLocations;
			packetsLocations = new ArrayList<int[]>(totalPackets);
			packetsLocations.addAll(tempPacketsLocations);
			tempPacketsLocations = null;
			System.gc();
		}

		this.totalPackets = totalPackets;
	}

	/**
	 * Getter for property frequency.
	 * 
	 * @return Value of property frequency.
	 * 
	 */
	public Frequency getFrequency() {
		return frequency;
	}

	/**
	 * Setter for property frequency.
	 * 
	 * @param frequency New value of property frequency.
	 * 
	 */
	private void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public int size() {
		return packetsLocations.size();
	}

	public int getLargestNumPacket() {
		return size() - 1;
	}

	private class SamplesSourceAdapter implements SamplesSourceEventListener {
		public void newSamples(SamplesSourceEvent evt) {
			if (evt != null)
				refreshState(evt.getSampleLargestIndex());
		}
	}
}
