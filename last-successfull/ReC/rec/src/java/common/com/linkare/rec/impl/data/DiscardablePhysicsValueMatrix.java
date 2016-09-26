package com.linkare.rec.impl.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.config.ReCSystemProperty;

/**
 * This class implements a sort of repository for data which hides the fact that
 * sometimes the experiments take too much memory and need to be flushed to disk
 * and read from disk
 * 
 * This class will know when to write the data samples to disk when the percent
 * free memory threshold is achieved in the JVM. Note that the System property
 * {@link #SYSPROP_FREE_THRESHOLD_NAME} may define a value (between 0 and 100)
 * for this data flushing mechanism in terms of percentage of free memory space
 * in the JVM
 * 
 * If there is no such property defined then the default value of
 * {@link #DEFAULT_FREE_THRESHOLD} is used
 * 
 * 
 * Note additionally that this type of data matrix is only temporary in nature,
 * because as the samples are obtained from it it is cleared from the internal
 * data structures, be it on disk or memory
 * 
 * @author José Pedro Pereira - Linkare TI
 * @version 2.0
 */
public class DiscardablePhysicsValueMatrix implements SamplesSource {

	private static final Logger LOGGER = Logger.getLogger(DiscardablePhysicsValueMatrix.class.getName());

	// The free mem should be defined by in the init scripts...
	/**
	 * Name of the system property that should be defined to override the
	 * default behaviour of 10% of free memory for serialization of data to
	 * occur in temporary files
	 */
	public static final String SYSPROP_FREE_THRESHOLD_NAME = "rec.percent.freememory.threshold.serialization";

	/**
	 * The free memory space after which the data dumping to disk occurs
	 */
	public static final double FREE_THRESHOLD_VALUE = Double.parseDouble(ReCSystemProperty.PERCENT_FREEMEMORY_SERIALIZATION.getValue());

	/**
	 * An indexed {@link HashMap} of rowNumber to {@link PhysicsValue}[] of data
	 */
	private ConcurrentMap<Integer, PhysicsValue[]> samplesRows = null;

	/**
	 * The last available sample count
	 */
	private int lastSampleCount = 0;

	/**
	 * The total number of samples
	 */
	private int totalSamples = 1;

	/**
	 * Actually this class uses this delegate to flush the samples to disk
	 */
	private DiscardablePhysicsValueMatrixIO ioDelegate;

	/**
	 * Tells whether this Matrix of data has been persisted to disk
	 */
	private boolean serialized = false;

	/** Utility field used by event firing mechanism. */
	private EventListenerList listenerList = null;

	/** Creates a new instance of PhysicsValueMatrix */
	public DiscardablePhysicsValueMatrix() {
		setTotalSamples(totalSamples);
	}

	/**
	 * Creates a new instance of PhysicsValueMatrix
	 * 
	 * @param totalSamples
	 */
	public DiscardablePhysicsValueMatrix(final int totalSamples) {
		setTotalSamples(totalSamples);
	}

	/**
	 * Adds new datarows to this matrix The first index of the data rows is the
	 * row and the second is the column
	 * 
	 * @param dataSamples The row/rows of samples to add to this matrix
	 */
	public void addDataRows(final PhysicsValue[]... dataSamples) {
		// Check if the samples are null, or have no values and return
		// immediatly
		if (dataSamples == null || dataSamples.length == 0) {
			return;
		}

		// Create a new map to hold all the new samples arrived
		final HashMap<Integer, PhysicsValue[]> tempSamples = new HashMap<Integer, PhysicsValue[]>(dataSamples.length);

		// Just add all dataRows and keep increasing the counter
		for (final PhysicsValue[] dataRow : dataSamples) {
			tempSamples.put(lastSampleCount++, dataRow);
		}

		// Now, one of two happens - either we are allready flushing to disk or
		// not... do it and keep consistency
		if (serialized) {
			try {
				ioDelegate.write(tempSamples);
			} catch (final IOException e) {
				LOGGER.log(Level.SEVERE, "Unable to write packets to file... defaulting to memory...", e);
				samplesRows.putAll(tempSamples);
			}
		} else {
			samplesRows.putAll(tempSamples);
		}

		if (shouldSerialize() && !serialized) {
			// if not allready serialized... but if we should.. then lazy create
			// the
			// delegate for io and asj it to write all the data
			try {
				ioDelegate = new DiscardablePhysicsValueMatrixIO();
				ioDelegate.write(samplesRows);
				// now that it is written to disk, set serialized to true to
				// keep flushing as needed
				serialized = true;
				// and clear our internal data
				samplesRows.clear();
				// Try to signal the garbage collector since we are near
				// exaustion
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		// Now say to all listeners that data is available
		fireNewSamples(lastSampleCount - 1);
	}

	/**
	 * Defines the total number of samples that are expected...
	 * 
	 * This method also tries to allocate/reallocate the {@link #samplesRows}
	 * variable accordingly to what is expected to be the total value of
	 * allocation required
	 * 
	 * @param totalSamples The total number of expected samples
	 */
	public void setTotalSamples(final int totalSamples) {
		// Saves the total samples number for later...
		this.totalSamples = totalSamples;

		// and creates or resizes the samplesRows variable
		if (samplesRows == null) {
			samplesRows = new ConcurrentHashMap<Integer, PhysicsValue[]>(totalSamples);
		} else {
			final Map<Integer, PhysicsValue[]> tempRows = samplesRows;
			samplesRows = new ConcurrentHashMap<Integer, PhysicsValue[]>(totalSamples);
			samplesRows.putAll(tempRows);
		}
	}

	/**
	 * Gets the total samples defined value
	 * 
	 * 
	 * @return the total number of samples currently defined. Note that not all
	 *         may be allready filled in the array
	 * 
	 * @see DiscardablePhysicsValueMatrix#setTotalSamples(int)
	 */
	public int getTotalSamples() {
		return totalSamples;
	}

	/**
	 * This method analizes the current free memory space vs the current max
	 * memory
	 * 
	 * @return true if the free memory is lower or equals to
	 *         {@link #FREE_THRESHOLD_VALUE}/100. * maxMemory
	 */
	private boolean shouldSerialize() {
		return (double) Runtime.getRuntime().freeMemory() <= (FREE_THRESHOLD_VALUE / 100.)
				* (double) Runtime.getRuntime().maxMemory();
	}

	/**
	 * This method enables one to remove a sample at index defined as the
	 * argument
	 * 
	 * 
	 * @param sampleIndex The index of the sample to remove
	 * @return The removed {@link PhysicsValue}[] data
	 * @throws SamplesReadException if it was not possible to find the sample at
	 *             the index specified
	 */
	private PhysicsValue[] removeSample(final int sampleIndex) throws SamplesReadException {
		if (serialized) {
			return ioDelegate.remove(sampleIndex, sampleIndex)[0];
		}

		if (samplesRows.containsKey(Integer.valueOf(sampleIndex))) {
			return samplesRows.remove(sampleIndex);
		} else {
			throw new SamplesReadException(new IOException("Error trying to read sample " + sampleIndex
					+ " from memory!"), sampleIndex);
		}
	}

	/**
	 * This method will return the values in the range sampleStartIndex to
	 * sampleEndIndex (inclusive on both ends)
	 * 
	 * and will then clear them from the underlying array as by the method
	 * {@link #removeSample(int)}
	 * 
	 * @param sampleStartIndex The index of the starting row (inclusive)
	 * @param sampleEndIndex The index of the ending row (inclusive)
	 * @throws SamplesReadException If some data is not available
	 */
	@Override
	public PhysicsValue[][] getSamples(final int sampleStartIndex, final int sampleEndIndex)
			throws SamplesReadException {

		// If it is on disk, then read it from disk
		if (serialized) {
			return ioDelegate.remove(sampleStartIndex, sampleEndIndex);
		}

		// Allocate the returning array with the correct size
		final PhysicsValue[][] retVal = new PhysicsValue[sampleEndIndex - sampleStartIndex + 1][];

		for (int i = sampleStartIndex; i <= sampleEndIndex; i++) {
			retVal[i - sampleStartIndex] = removeSample(i);
		}

		return retVal;
	}

	/**
	 * Registers SamplesSourceEventListener to receive events about newly added
	 * data to this {@link SamplesSource}
	 * 
	 * @param listener The listener to register.
	 * 
	 */
	@Override
	public synchronized void addSamplesSourceEventListener(final SamplesSourceEventListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(SamplesSourceEventListener.class, listener);
	}

	/**
	 * Removes SamplesSourceEventListener from the list of listeners to receive
	 * events about newly added data to this {@link SamplesSource}
	 * 
	 * @param listener The listener to remove.
	 * 
	 */
	@Override
	public synchronized void removeSamplesSourceEventListener(final SamplesSourceEventListener listener) {
		listenerList.remove(SamplesSourceEventListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the existence of new data, and
	 * sends them the largest sample index available from now on
	 * 
	 * @param sampleLargestIndex the current maximum sample index available on
	 *            new data
	 * 
	 */
	private void fireNewSamples(final int sampleLargestIndex) {
		final SamplesSourceEvent event = new SamplesSourceEvent(this, sampleLargestIndex);
		if (listenerList == null) {
			return;
		}

		// The way this code is implemented avoids the creation of new Listeners
		// arrays
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SamplesSourceEventListener.class) {
				((SamplesSourceEventListener) listeners[i + 1]).newSamples(event);
			}
		}
	}

	/**
	 * @return the largest sample index known at anytime
	 */
	@Override
	public int getLastSampleNum() {
		return lastSampleCount;
	}
}
