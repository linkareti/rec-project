/* 
 * FunctorDataControl.java cr eated on 25 Aug 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.jmf.media.protocol.function;

import java.awt.Component;

import javax.media.Control;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class FunctorDataControl implements Control {

	public static interface FunctorDataControlListener {
		public void newValue(long nanoTime, long value);
	}

	private FunctorDataControlListener listener = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component getControlComponent() {
		return null;
	}

	public void notifyNewValues(long value) {
		if (listener != null) {
			listener.newValue(System.nanoTime(), value);
		}
	}

	public void setListener(FunctorDataControlListener listener) {
		this.listener = listener;
	}

}
