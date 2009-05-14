/*
 * SamplesSource.java
 *
 * Created on 8 de Janeiro de 2004, 16:44
 */

package com.linkare.rec.impl.data;

import com.linkare.rec.data.acquisition.PhysicsValue;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public interface SamplesSource
{
    public PhysicsValue[][] getSamples(int sampleStartIndex,int sampleEndIndex) throws SamplesReadException;
    public void addSamplesSourceEventListener(SamplesSourceEventListener l);
    public void removeSamplesSourceEventListener(SamplesSourceEventListener l);
    public int getLastSampleNum();
}
