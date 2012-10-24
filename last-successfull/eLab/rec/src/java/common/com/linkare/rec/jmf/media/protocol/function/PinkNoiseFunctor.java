package com.linkare.rec.jmf.media.protocol.function;

import java.util.Random;

public class PinkNoiseFunctor implements Functor {

	private static final int POLES = 5;
	private static final double ALPHA = 1.;

	private final double[] multipliers;

	private final double[] values;
	private double timeDelta;
	private static final Random rnd = new Random();

	public PinkNoiseFunctor() {
		this.multipliers = new double[POLES];
		this.values = new double[POLES];

		double a = 1;
		for (int i = 0; i < POLES; i++) {
			a = (i - ALPHA / 2.) * a / (i + 1.);
			multipliers[i] = a;
		}

		// Fill the history with random values
		for (int i = 0; i < 5 * POLES; i++) {
			this.getNextValue();
        }
	}

	@Override
	public void setTimeDelta(double seconds) {
		this.timeDelta = seconds;
	}

	@Override
	public double getTimeDelta() {
		return timeDelta;
	}

	@Override
	public double getNextValue() {
		double x = rnd.nextDouble();

		for (int i = 0; i < POLES; i++) {
			x -= multipliers[i] * values[i];
		}
		System.arraycopy(values, 0, values, 1, values.length - 1);
		values[0] = x;

		return x / POLES;
	}
}
