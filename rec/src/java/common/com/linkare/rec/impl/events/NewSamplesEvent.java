/*
 * NewSamplesEvent.java
 *
 * Created on 6 de Novembro de 2002, 12:46
 */

package com.linkare.rec.impl.events;

import com.linkare.rec.impl.utils.IntersectableEvent;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class NewSamplesEvent implements IntersectableEvent {

	private int largestNumPacket;

	/**
	 * Creates a new instance of NewSamplesEvent
	 * 
	 * @param largestNumPacket
	 */
	public NewSamplesEvent(int largestNumPacket) {
		this.largestNumPacket = largestNumPacket;
	}

	/**
	 * Getter for property numPacketEnd.
	 * 
	 * @return Value of property numPacketEnd.
	 * 
	 */
	public int getLargestNumPacket() {
		return largestNumPacket;
	}
	
	/**
	 * @param largestNumPacket the largestNumPacket to set
	 */
	public void setLargestNumPacket(int largestNumPacket) {
		this.largestNumPacket = largestNumPacket;
	}

	public boolean intersectTo(IntersectableEvent other) {
		if (!(other instanceof NewSamplesEvent))
			return false;

		NewSamplesEvent evt = (NewSamplesEvent) other;
		if (evt.isPoisoned()) {
			return false;
		}
		largestNumPacket = Math.max(largestNumPacket, evt.getLargestNumPacket());
		return true;
	}
	
	/**
	 * @return
	 */
	public boolean isPoisoned() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		builder.append(getClass().getSimpleName());
		builder.append(">largestNumPacket: ");
		builder.append(largestNumPacket);
		builder.append("]");
		return builder.toString();
	}

}
