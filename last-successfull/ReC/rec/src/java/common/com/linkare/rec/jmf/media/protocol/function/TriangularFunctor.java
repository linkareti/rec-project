package com.linkare.rec.jmf.media.protocol.function;

public class TriangularFunctor implements Functor {

	private double currentTime = 0.;

	private double timeDelta = 1.;

	private double slope = 0.;

	private double frequency = 1.;

	private double period = 1. / frequency;

	private double deltaPeriod = period / 4.;

	private double currentCenter = 0;

	@Override
	public synchronized void setTimeDelta(double seconds) {
		this.timeDelta = seconds;
	}

	@Override
	public synchronized double getNextValue() {
		currentTime += this.timeDelta;
		while (currentTime > period) {
			currentTime -= period;
		}

		double currentSlope = 0.;
		if (currentTime > deltaPeriod && currentTime <= 3 * deltaPeriod) {
			currentSlope = -1. * this.slope;
			currentCenter = 2. * deltaPeriod;
		} else if (currentTime > 3. * deltaPeriod && currentTime <= period) {
			currentSlope = this.slope;
			currentCenter = period;
		} else {
			currentSlope = this.slope;
			currentCenter = 0;
		}

		return currentSlope * (currentTime - currentCenter);
	}

	public synchronized void setFrequency(double frequency) {
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
