/*
 * SamplesSourceEventListener.java
 *
 * Created on 8 de Janeiro de 2004, 16:46
 */

package com.linkare.rec.impl.data;

/**
 *
 * @author  Administrator
 */
public interface SamplesPacketSourceEventListener extends java.util.EventListener
{
    public void newSamplesPackets(SamplesPacketSourceEvent evt);
}
