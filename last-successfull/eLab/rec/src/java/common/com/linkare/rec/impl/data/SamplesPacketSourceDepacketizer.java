/*
 * DiscardablePhysicsValueMatrixPacketizer.java
 *
 * Created on 7 de Janeiro de 2004, 20:00
 */

package com.linkare.rec.impl.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

import com.linkare.rec.acquisition.MaxPacketNumUnknown;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.data.acquisition.TOTAL_PACKETS_UNDEFINED;
import com.linkare.rec.data.acquisition.TOTAL_SAMPLES_UNDEFINED;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class SamplesPacketSourceDepacketizer implements SamplesSource {
	private static final Logger LOGGER = Logger.getLogger(SamplesSourcePacketizer.class.getName());
	private SamplesPacketSource samplesPacketSource;
	private List<int[]> samplesLocations = null;
	private int lastSampleCount = TOTAL_SAMPLES_UNDEFINED.value;
	private int largestPacketGot = TOTAL_PACKETS_UNDEFINED.value;
	private SamplesPacketSourceAdapter adapter = null;
	/** Utility field used by event firing mechanism. */
	private EventListenerList listenerList = null;

	/** Creates a new instance of DiscardablePhysicsValueMatrixPacketizer */
	public SamplesPacketSourceDepacketizer() {
		this(null);
	}

	/**
	 * Creates a new instance of DiscardablePhysicsValueMatrixPacketizer
	 * 
	 * @param samplesPacketSource
	 */
	public SamplesPacketSourceDepacketizer(final SamplesPacketSource samplesPacketSource) {
		setSamplesPacketSource(samplesPacketSource);
	}

	@Override
	public PhysicsValue[][] getSamples(final int sampleStartIndex, final int sampleEndIndex)
			throws SamplesReadException {
		if (sampleStartIndex > sampleEndIndex) {
			throw new SamplesReadException("Start index is bigger than end index", sampleEndIndex);
		}

		final PhysicsValue[][] retVal = new PhysicsValue[sampleEndIndex - sampleStartIndex + 1][];

		for (int i = 0; i < retVal.length; i++) {
			retVal[i] = getSample(sampleStartIndex + i);
		}

		return retVal;
	}

	private PhysicsValue[] getSample(final int sampleIndex) throws SamplesReadException {

		if (samplesLocations == null || getSamplesPacketSource() == null) {
			throw new SamplesReadException("Error depacketizing... probably source is not set yet", sampleIndex);
		}

		try {
			final int[] sampleLocation = samplesLocations.get(sampleIndex);
			final SamplesPacket packet = samplesPacketSource.getSamplesPackets(sampleLocation[0], sampleLocation[0])[0];
			return packet.getData(sampleLocation[1]);

		} catch (final Exception e) {
			e.printStackTrace();
			throw new SamplesReadException("Error depacketizing... couldn't fetch sample", sampleIndex);
		}
	}

	private void refreshState(final int largestPacketIndex) {
		if (largestPacketGot >= largestPacketIndex) {
			return;
		}
		try {
			while (largestPacketGot < largestPacketIndex) {

				final SamplesPacket packet = samplesPacketSource.getSamplesPackets(largestPacketGot + 1,
						largestPacketGot + 1)[0];

				final PhysicsValue[][] rows = packet.getData();

				++largestPacketGot;

				if (rows == null) {
					continue;
				}

				for (int r = 0; r < rows.length; r++) {
					samplesLocations.add(new int[] { largestPacketGot, r });
				}

				if (lastSampleCount == MaxPacketNumUnknown.value) {
					lastSampleCount = rows.length - 1;
				} else {
					lastSampleCount += rows.length;
				}

			}
		} catch (final SamplesPacketReadException ignored) {
		}

		fireNewSamples(lastSampleCount);

	}

	/**
	 * Registers SamplesSourceEventListener to receive events.
	 * 
	 * @param listener The listener to register.
	 * 
	 */
	@Override
	public synchronized void addSamplesSourceEventListener(final SamplesSourceEventListener listener) {
		if (listenerList == null) {
			listenerList = new EventListenerList();
		}
		listenerList.add(SamplesSourceEventListener.class, listener);
	}

	/**
	 * Removes SamplesSourceEventListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 * 
	 */
	@Override
	public synchronized void removeSamplesSourceEventListener(final SamplesSourceEventListener listener) {
		listenerList.remove(SamplesSourceEventListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 * 
	 */
	private void fireNewSamples(final int sampleLargestIndex) {
		final SamplesSourceEvent event = new SamplesSourceEvent(this, sampleLargestIndex);
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		if (LOGGER.isLoggable(Level.FINEST)) {
			LOGGER.log(Level.FINEST, "Informing "+listeners.length+" listeners of new samples available up to "+sampleLargestIndex);
		}
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SamplesSourceEventListener.class) {
				if (LOGGER.isLoggable(Level.FINEST)) {
					LOGGER.log(Level.FINEST, "Informing '"+listeners[i + 1]+"' listener of new samples available up to "+sampleLargestIndex);
				}
				((SamplesSourceEventListener) listeners[i + 1]).newSamples(event);
			}
		}
	}

	/**
	 * Getter for property source.
	 * 
	 * @return Value of property samplesPacketSource.
	 * 
	 */
	public SamplesPacketSource getSamplesPacketSource() {
		return samplesPacketSource;
	}

	/**
	 * Setter for property source.
	 * 
	 * @param samplesPacketSource
	 * 
	 * @param source New value of property samplesPacketSource.
	 * 
	 */
	public void setSamplesPacketSource(final SamplesPacketSource samplesPacketSource) {
		if (this.samplesPacketSource != null && this.samplesPacketSource == samplesPacketSource) {
			return;
		}

		if (this.samplesPacketSource != null && adapter != null) {
			samplesPacketSource.removeSamplesPacketSourceEventListener(adapter);
		}

		this.samplesPacketSource = samplesPacketSource;

		samplesLocations = new ArrayList<int[]>(100);

		if (this.samplesPacketSource != null) {
			if (adapter == null) {
				adapter = new SamplesPacketSourceAdapter();
			}

			this.samplesPacketSource.addSamplesPacketSourceEventListener(adapter);
		}
	}

	@Override
	public int getLastSampleNum() {
		return lastSampleCount;
	}

	private class SamplesPacketSourceAdapter implements SamplesPacketSourceEventListener {

		@Override
		public void newSamplesPackets(final SamplesPacketSourceEvent evt) {
			// System.out.println("SamplesPacketSourceDepacktizer refreshing state... "+
			// evt.getPacketLargestIndex());
			if (LOGGER.isLoggable(Level.FINEST)) {
				LOGGER.log(Level.FINEST, "SamplesPacketSourceAdapter -> refresing state with largest packet index ="
						+ evt.getPacketLargestIndex());
			}
			if (evt != null) {
				refreshState(evt.getPacketLargestIndex());
			}
		}

	}

}
