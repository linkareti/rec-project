/* 
 * FunctorControl.java created on 28 Jul 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.jmf.media.protocol.function;

import java.awt.Component;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 */
public interface FunctorControl extends javax.media.Control {

	FunctorControl NULLOP_CONTROL = new FunctorControl() {
		@Override
		public void setFrequency(double frequency) {
		}

		@Override
		public Component getControlComponent() {
			return null;
		};
	};

	public void setFrequency(double frequency);
}
