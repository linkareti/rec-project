/*
 * SamplesSourceEvent.java
 *
 * Created on 8 de Janeiro de 2004, 16:47
 */

package com.linkare.rec.impl.data;

import com.linkare.rec.impl.utils.IntersectableEvent;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class SamplesPacketSourceEvent extends java.util.EventObject implements IntersectableEvent
{
    private int packetLargestIndex;
    /** Creates a new instance of SamplesSourceEvent */
    public SamplesPacketSourceEvent(SamplesPacketSource source, int packetLargestIndex)
    {
	super(source);
	setPacketLargestIndex(packetLargestIndex);
    }
    
    public boolean intersectTo(IntersectableEvent other)
    {
	if(other==null || !(other instanceof SamplesPacketSourceEvent))
	    return false;
	
	SamplesPacketSourceEvent evt=(SamplesPacketSourceEvent)other;
	setPacketLargestIndex(Math.max(getPacketLargestIndex(),evt.getPacketLargestIndex()));
	return true;
    }
    
        
    /** Getter for property packetLargestIndex.
     * @return Value of property packetLargestIndex.
     *
     */
    public int getPacketLargestIndex()
    {
	return packetLargestIndex;
    }
    
    /** Setter for property packetLargestIndex.
     * @param packetLargestIndex New value of property packetLargestIndex.
     *
     */
    private void setPacketLargestIndex(int packetLargestIndex)
    {
	this.packetLargestIndex = packetLargestIndex;
    }
    
}
