package com.linkare.rec.jmf.media.protocol.function;

import java.awt.Component;

public class SineFunctorControl implements FunctorControl {

	private SineFunctor functor;

	public SineFunctorControl(FunctorType functorType) {
		this.functor = (SineFunctor) functorType.getFunctor();
	}

	@Override
	public Component getControlComponent() {
		return new SineFunctorControlComponent(this);
	}

	public void setFrequency(double frequency) {
		this.functor.setFrequency(frequency);
	}

	public double getFrequency() {
		return this.functor.getFrequency();
	}

}
