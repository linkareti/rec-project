package com.linkare.rec.jmf.media.protocol.function;

public interface Functor {

    public void setTimeDelta(double seconds);

    public double getTimeDelta();

    public double getNextValue();

}
