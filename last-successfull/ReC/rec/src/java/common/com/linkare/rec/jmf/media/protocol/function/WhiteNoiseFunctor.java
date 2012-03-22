package com.linkare.rec.jmf.media.protocol.function;

import java.util.Random;

public class WhiteNoiseFunctor implements Functor {

	private static final double RANGE_SHIFT = 1.;
	private static final double RANGE_LENGTH = 2.;

	private Random random = new Random();
	private double timeDelta;

	@Override
	public void setTimeDelta(double seconds) {
		this.timeDelta = seconds;
	}

	@Override
	public double getNextValue() {
		return random.nextDouble() * RANGE_LENGTH - RANGE_SHIFT;
	}

	@Override
	public double getTimeDelta() {
		return timeDelta;
	}

}
