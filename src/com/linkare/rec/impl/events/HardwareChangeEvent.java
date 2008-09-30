/*
 * HardwareChangeEvent.java
 *
 * Created on 6 de Novembro de 2002, 12:38
 */

package com.linkare.rec.impl.events;

import com.linkare.rec.impl.utils.IntersectableEvent;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class HardwareChangeEvent implements IntersectableEvent
{
	
	/** Creates a new instance of HardwareChangeEvent */
	public HardwareChangeEvent()
	{
	}
	
	public boolean intersectTo(IntersectableEvent other)
	{
	    return (other instanceof HardwareChangeEvent);
	}
	
}
