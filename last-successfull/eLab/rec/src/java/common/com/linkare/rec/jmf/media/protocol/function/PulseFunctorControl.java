package com.linkare.rec.jmf.media.protocol.function;

import java.awt.Component;

public class PulseFunctorControl implements FunctorControl {

	private final PulseFunctor functor;

	public PulseFunctorControl(final FunctorType functorType) {
		this.functor = (PulseFunctor) functorType.getFunctor();
	}

	@Override
	public Component getControlComponent() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFrequency(double frequency) {
		this.functor.setFrequency(frequency);
	}

}
