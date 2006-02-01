/*
 * SamplesSource.java
 *
 * Created on 8 de Janeiro de 2004, 16:44
 */

package com.linkare.rec.impl.data;

import com.linkare.rec.data.acquisition.*;
/**
 *
 * @author  Administrator
 */
public interface SamplesPacketSource
{
    public SamplesPacket[] getSamplesPackets(int packetStartIndex,int packetEndIndex) throws SamplesPacketReadException;
    public void addSamplesPacketSourceEventListener(SamplesPacketSourceEventListener l);
    public void removeSamplesPacketSourceEventListener(SamplesPacketSourceEventListener l);
    public int getLargestNumPacket();
}
