/* 
 * MarkerFunctor.java created on 19 Aug 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.jmf.media.protocol.function;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 */
public class MarkerFunctor implements Functor {

	private double timeDelta;

	@Override
	public void setTimeDelta(double seconds) {
		this.timeDelta = seconds;
	}

	@Override
	public double getNextValue() {
		return 1.;
	}

	@Override
	public double getTimeDelta() {
		return timeDelta;
	}

}