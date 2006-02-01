package com.linkare.rec.impl.client.experiment;

/*
 * NewSamplesEvent.java
 *
 * Created on 7 de Maio de 2003, 12:02
 */

import com.linkare.rec.impl.utils.*;

/**
 *
 * @author  jp
 *
 * ->Changed by Andre on 16/07/04;
 *  intersectTo
 *
 */
public class NewExpDataEvent extends java.util.EventObject implements IntersectableEvent
{
	
	/** Holds value of property samplesStartRow. */
	private int samplesStartIndex;
	
	/** Holds value of property samplesEndIndex. */
	private int samplesEndIndex;
	
	/** Creates a new instance of NewSamplesEvent */
	public NewExpDataEvent(Object source, int samplesStartIndex, int samplesEndIndex)
	{
		super(source);
		this.samplesStartIndex=samplesStartIndex;
		this.samplesEndIndex=samplesEndIndex;
	}
	
	/** Getter for property samplesStartRow.
	 * @return Value of property samplesStartRow.
	 */
	public int getSamplesStartIndex()
	{
		return this.samplesStartIndex;
	}
	
	/** Getter for property samplesEndIndex.
	 * @return Value of property samplesEndIndex.
	 */
	public int getSamplesEndIndex()
	{
		return this.samplesEndIndex;
	}
	
        public boolean intersectTo(IntersectableEvent other) 
        {
            if(other==null || !(other instanceof NewExpDataEvent))
            {
                return false;
            }
	
            NewExpDataEvent evt = (NewExpDataEvent)other;
            samplesStartIndex = Math.min(samplesStartIndex, evt.getSamplesStartIndex());
            samplesEndIndex = Math.max(samplesEndIndex, evt.getSamplesEndIndex());

            return true;
        }        
}
