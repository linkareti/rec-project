package com.linkare.rec.jmf.media.protocol.function;

public class SineFunctor implements Functor {

	private static final double TWO_PI = 2. * Math.PI;

	private double currentTime = 0.;

	private double timeDelta = 1.;

	private double frequency = 1.;

	private double period = 1. / frequency;

	@Override
	public void setTimeDelta(double seconds) {
		this.timeDelta = seconds;
		System.out.println("time-delta=" + timeDelta);
	}

	@Override
	public synchronized double getNextValue() {
		currentTime += this.timeDelta;
		while (currentTime > period) {
			currentTime -= period;
		}

		return Math.sin(TWO_PI * frequency * currentTime);
	}

	public synchronized void setFrequency(double frequency) {
		this.frequency = frequency;
		this.period = 1. / frequency;
		currentTime = 0;
	}

	public double getTimeDelta() {
		return timeDelta;
	}

	public double getFrequency() {
		return frequency;
	}
}
