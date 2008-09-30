/*
 * NewSamplesEvent.java
 *
 * Created on 6 de Novembro de 2002, 12:46
 */

package com.linkare.rec.impl.events;

import com.linkare.rec.impl.utils.IntersectableEvent;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class NewSamplesEvent implements IntersectableEvent
{
    
    
    private int largestNumPacket;
    
    /** Creates a new instance of NewSamplesEvent */
    public NewSamplesEvent(int largestNumPacket)
    {
	this.largestNumPacket=largestNumPacket;
    }
    
    /** Getter for property numPacketEnd.
     * @return Value of property numPacketEnd.
     *
     */
    public int getLargestNumPacket()
    {
	return largestNumPacket;
    }
    
    public boolean intersectTo(IntersectableEvent other)
    {
	if(!(other instanceof NewSamplesEvent)) return false;
	
	NewSamplesEvent evt=(NewSamplesEvent)other;
	largestNumPacket=Math.max(largestNumPacket,evt.getLargestNumPacket());
	return true;
    }
    
}
