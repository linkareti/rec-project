/*
 * DataEndedEvent.java
 *
 * Created on 18 de Julho de 2003, 16:10
 */

package com.linkare.rec.impl.events;

import com.linkare.rec.acquisition.DataProducerState;
import com.linkare.rec.impl.threading.util.EnumPriority;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class DataProducerStateChangeEvent implements Prioritazible {
	DataProducerState dataProducerState = null;

	/** Creates a new instance of DataEndedEvent 
	 * @param dataProducerState */
	public DataProducerStateChangeEvent(final DataProducerState dataProducerState) {
		this.dataProducerState = dataProducerState;
	}

	/**
	 * Getter for property dataProducerState.
	 * 
	 * @return Value of property dataProducerState.
	 * 
	 */
	public DataProducerState getDataProducerState() {
		return dataProducerState;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return super.toString() + " - State = " + dataProducerState;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnumPriority getPriority() {
		return EnumPriority.MAXIMUM;
	}

}
