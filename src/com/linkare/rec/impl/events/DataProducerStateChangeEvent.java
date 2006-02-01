/*
 * DataEndedEvent.java
 *
 * Created on 18 de Julho de 2003, 16:10
 */

package com.linkare.rec.impl.events;

import com.linkare.rec.acquisition.DataProducerState;

/**
 *
 * @author  jp
 */
public class DataProducerStateChangeEvent 
{
    DataProducerState dataProducerState=null;
    /** Creates a new instance of DataEndedEvent */
    public DataProducerStateChangeEvent(DataProducerState dataProducerState)
    {
	this.dataProducerState = dataProducerState;
    }
    
    /** Getter for property dataProducerState.
     * @return Value of property dataProducerState.
     *
     */
    public DataProducerState getDataProducerState()
    {
	return dataProducerState;
    }

}
