/*
 * HardwareStateChangeEvent.java
 *
 * Created on 6 de Novembro de 2002, 12:41
 */

package com.linkare.rec.impl.events;

import com.linkare.rec.impl.utils.IntersectableEvent;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class HardwareStateChangeEvent implements IntersectableEvent
{

	/** Holds value of property newState. */
	private com.linkare.rec.acquisition.HardwareState newState;
	private long systemTime=0;
	/** Creates a new instance of HardwareStateChangeEvent */
	public HardwareStateChangeEvent(com.linkare.rec.acquisition.HardwareState newState)
	{
		this.newState=newState;
		systemTime=System.currentTimeMillis();
	}

	/** Getter for property newState.
	 * @return Value of property newState.
	 */
	public com.linkare.rec.acquisition.HardwareState getNewState()
	{
		return this.newState;
	}

	public boolean intersectTo(IntersectableEvent other)
	{
	    if(other instanceof HardwareStateChangeEvent)
	    {
		HardwareStateChangeEvent evt=((HardwareStateChangeEvent)other);
		if(evt.systemTime<systemTime)
		    return false;
		else
		    this.newState=evt.getNewState();
		
		return true;
	    }
	    return false;
	}
	
}
