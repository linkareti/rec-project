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
	 * @param largestNumPacket
	 */
	public NewPoisonSamplesEvent(int largestNumPacket) {
		super(largestNumPacket);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean intersectTo(IntersectableEvent other) {
		if (!(other instanceof NewPoisonSamplesEvent))
			return false;
		
		return super.intersectTo(other);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return super.toString();
	}

}
