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
 * @author José Pedro Pereira - Linkare TI
 */
public interface FunctorControl extends javax.media.Control {

	FunctorControl NULLOP_CONTROL = new FunctorControl() {
		private double frequency = Double.MAX_VALUE;

		@Override
		public void setFrequency(double frequency) {
			this.frequency = frequency;
		}

		@Override
		public Component getControlComponent() {
			return null;
		}

		@Override
		public double getFrequency() {
			return frequency;
		}
	};

	public void setFrequency(double frequency);

	public double getFrequency();
}
