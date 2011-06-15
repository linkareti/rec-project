package com.linkare.jmf.media.protocol.function;

import java.awt.Component;

import javax.media.Control;

public class SineFunctorControl implements Control {

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

    public void setPhase(double phase) {
	this.functor.setFrequency(phase);
    }

    public double getPhase() {
	return this.functor.getPhase();
    }

}
