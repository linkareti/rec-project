package com.linkare.rec.jmf.media.protocol.function;

import java.awt.Component;

public class TriangularFunctorControl implements FunctorControl {

	private final TriangularFunctor functor;

	public TriangularFunctorControl(final FunctorType functorType) {
		this.functor = (TriangularFunctor) functorType.getFunctor();
	}

	@Override
	public Component getControlComponent() {
		return new TriangularFunctorControlComponent(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFrequency(double frequency) {
		this.functor.setFrequency(frequency);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getFrequency() {
		return this.functor.getFrequency();
	}

}
