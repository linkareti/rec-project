package com.linkare.jmf.media.protocol.function;

public class SineFunctor implements Functor {

    private static final double TWO_PI = 2. * Math.PI;

    private double currentTime = 0.;

    private double timeDelta = 1.;

    private double phase = 0.;

    private double frequency = 1.;

    private double period = 1. / frequency;

    @Override
    public void setTimeDelta(double seconds) {
	this.timeDelta = seconds;
    }

    @Override
    public double getNextValue() {
	currentTime += this.timeDelta;
	if (currentTime > period) {
	    currentTime -= period;
	}

	return Math.sin(phase + TWO_PI * (frequency * currentTime));
    }

    public void setPhase(double phase) {
	this.phase = phase;
    }

    public void setFrequency(double frequency) {
	this.frequency = frequency;
	this.period = 1. / frequency;
    }

    public double getTimeDelta() {
	return timeDelta;
    }

    public double getPhase() {
	return phase;
    }

    public double getFrequency() {
	return frequency;
    }
}
