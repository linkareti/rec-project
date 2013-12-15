/*
 * SamplesSourceEvent.java
 *
 * Created on 8 de Janeiro de 2004, 16:47
 */

package com.linkare.rec.impl.data;

import com.linkare.rec.impl.utils.IntersectableEvent;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class SamplesSourceEvent extends java.util.EventObject implements IntersectableEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7869653039926379840L;
	private int sampleLargestIndex;

	/** Creates a new instance of SamplesSourceEvent */
	public SamplesSourceEvent(final SamplesSource source, final int sampleLargestIndex) {
		super(source);
		setSampleLargestIndex(sampleLargestIndex);
	}

	@Override
	public boolean intersectTo(final IntersectableEvent other) {
		if (!(other instanceof SamplesSourceEvent)) {
			return false;
		}

		final SamplesSourceEvent evt = (SamplesSourceEvent) other;
		setSampleLargestIndex(Math.max(getSampleLargestIndex(), evt.getSampleLargestIndex()));
		return true;
	}

	/**
	 * Getter for property sampleLargestIndex.
	 * 
	 * @return Value of property sampleLargestIndex.
	 * 
	 */
	public int getSampleLargestIndex() {
		return sampleLargestIndex;
	}

	/**
	 * Setter for property sampleLargestIndex.
	 * 
	 * @param sampleLargestIndex New value of property sampleLargestIndex.
	 * 
	 */
	private void setSampleLargestIndex(final int sampleLargestIndex) {
		this.sampleLargestIndex = sampleLargestIndex;
	}

}
