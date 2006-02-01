/*
 * SamplesSourceEvent.java
 *
 * Created on 8 de Janeiro de 2004, 16:47
 */

package com.linkare.rec.impl.data;

import com.linkare.rec.impl.utils.IntersectableEvent;
/**
 *
 * @author  Administrator
 */
public class SamplesSourceEvent extends java.util.EventObject implements IntersectableEvent
{
    private int sampleLargestIndex;
    /** Creates a new instance of SamplesSourceEvent */
    public SamplesSourceEvent(SamplesSource source,int sampleLargestIndex)
    {
	super(source);
	setSampleLargestIndex(sampleLargestIndex);
    }
    
    public boolean intersectTo(IntersectableEvent other)
    {
	if(other==null || !(other instanceof SamplesSourceEvent))
	    return false;
	
	SamplesSourceEvent evt=(SamplesSourceEvent)other;
	setSampleLargestIndex(Math.max(getSampleLargestIndex(),evt.getSampleLargestIndex()));
	return true;
    }
    
    /** Getter for property sampleLargestIndex.
     * @return Value of property sampleLargestIndex.
     *
     */
    public int getSampleLargestIndex()
    {
	return sampleLargestIndex;
    }
    
    /** Setter for property sampleLargestIndex.
     * @param sampleLargestIndex New value of property sampleLargestIndex.
     *
     */
    private void setSampleLargestIndex(int sampleLargestIndex)
    {
	this.sampleLargestIndex = sampleLargestIndex;
    }
    
}
