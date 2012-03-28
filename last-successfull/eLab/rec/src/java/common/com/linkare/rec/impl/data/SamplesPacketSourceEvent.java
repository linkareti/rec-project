/*
 * SamplesSourceEvent.java
 *
 * Created on 8 de Janeiro de 2004, 16:47
 */

package com.linkare.rec.impl.data;

import com.linkare.rec.impl.events.Prioritazible;
import com.linkare.rec.impl.threading.util.EnumPriority;
import com.linkare.rec.impl.utils.IntersectableEvent;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class SamplesPacketSourceEvent extends java.util.EventObject implements IntersectableEvent, Prioritazible {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3009716302549977110L;
	private int packetLargestIndex;

	/** Creates a new instance of SamplesSourceEvent */
	public SamplesPacketSourceEvent(final SamplesPacketSource source, final int packetLargestIndex) {
		super(source);
		setPacketLargestIndex(packetLargestIndex);
	}

	@Override
	public boolean intersectTo(final IntersectableEvent other) {
		if (!(other instanceof SamplesPacketSourceEvent)) {
			return false;
		}

		final SamplesPacketSourceEvent evt = (SamplesPacketSourceEvent) other;
		setPacketLargestIndex(Math.max(getPacketLargestIndex(), evt.getPacketLargestIndex()));
		return true;
	}

	/**
	 * Getter for property packetLargestIndex.
	 * 
	 * @return Value of property packetLargestIndex.
	 * 
	 */
	public int getPacketLargestIndex() {
		return packetLargestIndex;
	}

	/**
	 * Setter for property packetLargestIndex.
	 * 
	 * @param packetLargestIndex New value of property packetLargestIndex.
	 * 
	 */
	private void setPacketLargestIndex(final int packetLargestIndex) {
		this.packetLargestIndex = packetLargestIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnumPriority getPriority() {
		return EnumPriority.MEDIUM;
	}

}
