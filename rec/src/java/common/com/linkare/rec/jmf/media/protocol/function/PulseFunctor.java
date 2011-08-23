package com.linkare.rec.jmf.media.protocol.function;

public class PulseFunctor implements Functor {

	private double currentTime = 0.;

	private double timeDelta = 1.;

	private double pulseLengthPercent = 0.1;

	private double frequency = 1.;

	private double pulseLength = 1. / frequency;

	private double period = pulseLength / pulseLengthPercent;

	public void setPulseLengthPercent(double percent) {
		this.pulseLengthPercent = percent;
		this.pulseLength = 1. / frequency;
		this.period = pulseLength / pulseLengthPercent;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
		this.pulseLength = 1. / frequency;
		this.period = pulseLength / pulseLengthPercent;
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

		return (currentTime < pulseLength) ? 1. : 0.;
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
