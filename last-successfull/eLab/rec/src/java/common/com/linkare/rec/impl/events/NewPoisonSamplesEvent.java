/* 
 * NewSamplesPoisonEvent.java created on 23 Jul 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.events;

import com.linkare.rec.impl.utils.IntersectableEvent;

/**
 * 
 * @author npadriano
 */
public class NewPoisonSamplesEvent extends NewSamplesEvent {

	/**
	 * Creates the <code>NewSamplesPoisonEvent</code>.
	 * 
	 * @param largestNumPacket
	 */
	public NewPoisonSamplesEvent(final int largestNumPacket) {
		super(largestNumPacket);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean intersectTo(final IntersectableEvent other) {
		if (!(other instanceof NewPoisonSamplesEvent)) {
			return false;
		}

		final NewPoisonSamplesEvent evt = (NewPoisonSamplesEvent) other;
		setLargestNumPacket(Math.max(getLargestNumPacket(), evt.getLargestNumPacket()));
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPoisoned() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return super.toString();
	}

}