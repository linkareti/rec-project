package com.linkare.jmf.media.protocol.function;
public class TriangularFunctor implements Functor {

	private double currentTime = 0.;

	private double timeDelta = 1.;

	private double slope = 0.;

	private double frequency = 1.;

	

	private double period = 1. / frequency;

	private double deltaPeriod = period / 4.;

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

		if (currentTime > deltaPeriod && currentTime < 3. * deltaPeriod) {
			this.slope = -1. / deltaPeriod;
		} else {
			this.slope = 1. / deltaPeriod;
		}

		return this.slope * currentTime;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
		this.period = 1. / frequency;
		this.deltaPeriod = this.period / 4.;
		this.slope = 1. / deltaPeriod;
	}

	public double getTimeDelta() {
	    return timeDelta;
	}

	public double getFrequency() {
	    return frequency;
	}
}
