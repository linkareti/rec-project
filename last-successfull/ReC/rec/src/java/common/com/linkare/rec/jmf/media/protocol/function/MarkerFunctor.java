/* 
 * MarkerFunctor.java created on 19 Aug 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.jmf.media.protocol.function;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class MarkerFunctor extends WhiteNoiseFunctor {

	private double currentTime = 0;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized double getNextValue() {
		currentTime += getTimeDelta();
		if (currentTime > 10) {
			currentTime -= 10;
		}
		return currentTime < 0.2 ? super.getNextValue() : 0.;
	}
}