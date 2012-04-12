package com.linkare.rec.jmf.media.protocol.function;

public class SilenceFunctor implements Functor {

    private double timeDelta;

    @Override
    public void setTimeDelta(double seconds) {
	this.timeDelta = seconds;
    }

    @Override
    public double getNextValue() {
	return 0.;
    }

    @Override
    public double getTimeDelta() {
	return timeDelta;
    }

}
