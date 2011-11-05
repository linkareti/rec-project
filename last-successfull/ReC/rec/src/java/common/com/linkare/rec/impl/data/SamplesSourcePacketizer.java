package com.linkare.rec.impl.data;

import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.data.synch.Frequency;

public class SamplesSourcePacketizer implements SamplesPacketSource {

	private static final int NOT_AVAILABLE_TOTAL_SAMPLES_VALUE = -1;
	private SamplesSource samplesSource = null;
	private Frequency frequency = null;
	private int packetSize = 1;
	private ArrayList<int[]> packetsLocations = null;
	private int totalPackets = 1;
	private int totalSamples = SamplesSourcePacketizer.NOT_AVAILABLE_TOTAL_SAMPLES_VALUE;
	private int countSamplesCurrentPacket = 0;
	private int currentPacketSampleStartIndex = 0;
	private SamplesSourceAdapter adapter = null;

	/** Utility field used by event firing mechanism. */
	private EventListenerList listenerList = null;

	/** Creates a new instance of DiscardablePhysicsValueMatrixPacketizer */
	public SamplesSourcePacketizer(final Frequency freq) {
		this(freq, null, 1, 1, SamplesSourcePacketizer.NOT_AVAILABLE_TOTAL_SAMPLES_VALUE);
	}

	/** Creates a new instance of DiscardablePhysicsValueMatrixPacketizer */
	public SamplesSourcePacketizer(final Frequency freq, final int packetSize, final int totalPackets,
			final int totalSamples) {
		this(freq, null, packetSize, totalPackets, totalSamples);
	}

	/** Creates a new instance of DiscardablePhysicsValueMatrixPacketizer */
	public SamplesSourcePacketizer(final Frequency freq, final SamplesSource samplesSource, final int packetSize,
			final int totalPackets, final int totalSamples) {
		setSamplesSource(samplesSource);
		this.packetSize = packetSize;
		setTotalPackets(totalPackets);
		frequency = freq;
		this.totalSamples = totalSamples;
	}

	@Override
	public SamplesPacket[] getSamplesPackets(final int packetStartIndex, final int packetEndIndex)
			throws SamplesPacketReadException {
		if (packetsLocations == null || getSamplesSource() == null) {
			throw new SamplesPacketReadException("Error packetizing... probably source is not set yet",
					packetStartIndex);
		}

		if (packetStartIndex > packetEndIndex) {
			throw new SamplesPacketReadException("Start index is bigger than end index", packetStartIndex);
		}

		final SamplesPacket[] retVal = new SamplesPacket[packetEndIndex - packetStartIndex + 1];

		for (int i = 0; i < retVal.length; i++) {
			final int[] packetLocations = packetsLocations.get(packetStartIndex + i);
			if (packetLocations == null) {
				throw new SamplesPacketReadException("That packet index is not available", packetStartIndex + i);
			}

			PhysicsValue[][] data = null;
			try {
				data = samplesSource.getSamples(packetLocations[0], packetLocations[1]);
			} catch (final SamplesReadException e) {
				throw new SamplesPacketReadException("Error reading sample index " + e.getErrorSampleNumber()
						+ " for this packet", packetStartIndex + i);
			}

			retVal[i] = new SamplesPacket(packetStartIndex + i, getTotalPackets(), data);
		}

		return retVal;
	}

	public void finishLastPacket() {
		if (countSamplesCurrentPacket == 0) {
			return;
		}

		final int[] startAndEndLoc = new int[] { currentPacketSampleStartIndex,
				currentPacketSampleStartIndex + countSamplesCurrentPacket - 1 };

		packetsLocations.add(startAndEndLoc);

		fireNewSamplesPackets(packetsLocations.size() - 1);
	}

	private void refreshState(final int samplesLargestIndex) {
		final int newSamplesCount = samplesLargestIndex - (currentPacketSampleStartIndex + countSamplesCurrentPacket)
				+ 1;
		if (newSamplesCount < 0) {
			return;
		}

		if (countSamplesCurrentPacket + newSamplesCount < getPacketSize()) {
			countSamplesCurrentPacket += newSamplesCount;
			if (totalSamples != SamplesSourcePacketizer.NOT_AVAILABLE_TOTAL_SAMPLES_VALUE
					&& totalSamples == currentPacketSampleStartIndex + countSamplesCurrentPacket) {
				finishLastPacket();
			}
			return;
		}

		int notConsumedSamplesCount = newSamplesCount;
		do {
			final int[] startAndEndLoc = new int[] { currentPacketSampleStartIndex,
					currentPacketSampleStartIndex + getPacketSize() - 1 };
			packetsLocations.add(startAndEndLoc);
			currentPacketSampleStartIndex = startAndEndLoc[1] + 1;
			notConsumedSamplesCount -= (getPacketSize() - countSamplesCurrentPacket);
			countSamplesCurrentPacket = 0;

		} while (notConsumedSamplesCount >= getPacketSize());

		countSamplesCurrentPacket = notConsumedSamplesCount;
		if (countSamplesCurrentPacket > 0 && totalSamples != SamplesSourcePacketizer.NOT_AVAILABLE_TOTAL_SAMPLES_VALUE
				&& totalSamples == currentPacketSampleStartIndex) {
			finishLastPacket();
		} else {
			fireNewSamplesPackets(packetsLocations.size() - 1);
		}
	}

	/**
	 * Registers SamplesPacketSourceEventListener to receive events.
	 * 
	 * @param listener The listener to register.
	 * 
	 */
	@Override
	public synchronized void addSamplesPacketSourceEventListener(final SamplesPacketSourceEventListener listener) {
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
	@Override
	public synchronized void removeSamplesPacketSourceEventListener(final SamplesPacketSourceEventListener listener) {
		listenerList.remove(SamplesPacketSourceEventListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 * 
	 */
	private void fireNewSamplesPackets(final int packetLargestIndex) {
		System.out.println("--->>>>>>>>>Firing new samples Packet at Samples Source Packetizer refreshState "
				+ packetLargestIndex);

		final SamplesPacketSourceEvent event = new SamplesPacketSourceEvent(this, packetLargestIndex);
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
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
	public void setSamplesSource(final SamplesSource samplesSource) {
		if (this.samplesSource != null && this.samplesSource == samplesSource) {
			return;
		}

		if (this.samplesSource != null && adapter != null) {
			samplesSource.removeSamplesSourceEventListener(adapter);
		}

		this.samplesSource = samplesSource;

		packetsLocations = new ArrayList<int[]>(getTotalPackets());
		currentPacketSampleStartIndex = 0;
		countSamplesCurrentPacket = 0;

		if (this.samplesSource != null) {
			if (adapter == null) {
				adapter = new SamplesSourceAdapter();
			}

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
	public void setTotalPackets(final int totalPackets) {
		if (totalPackets <= 0) {
			return;
		}

		if (packetsLocations != null) {
			ArrayList<int[]> tempPacketsLocations = packetsLocations;
			packetsLocations = new ArrayList<int[]>(totalPackets);
			packetsLocations.addAll(tempPacketsLocations);
			tempPacketsLocations = null;
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

	public int size() {
		return packetsLocations.size();
	}

	@Override
	public int getLargestNumPacket() {
		return size() - 1;
	}

	private class SamplesSourceAdapter implements SamplesSourceEventListener {
		@Override
		public void newSamples(final SamplesSourceEvent evt) {
			if (evt != null) {
				refreshState(evt.getSampleLargestIndex());
			}
		}
	}
}
