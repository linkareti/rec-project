package com.linkare.rec.jmf.media.protocol.function;

public class PulseFunctor implements Functor {

    private double currentTime = 0.;

    private double timeDelta = 1.;

    private double pulseLengthPercent = 0.;

    private double pulseLength = 0.;

    private double frequency = 1.;

    private double period = 1. / frequency;

    public void setPulseLengthPercent(double percent) {
	this.pulseLengthPercent = percent;
	this.pulseLength = period * percent;
    }

    public void setFrequency(double frequency) {
	this.frequency = frequency;
	this.period = 1. / frequency;
	this.pulseLength = period * pulseLengthPercent;
    }

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

	return (currentTime > (period - pulseLength) || currentTime < 0) ? 1. : 0.;
    }

    @Override
    public double getTimeDelta() {
	return timeDelta;
    }

    public double getPulseLengthPercent() {
	return pulseLengthPercent;
    }

    public double getFrequency() {
	return frequency;
    }

}
