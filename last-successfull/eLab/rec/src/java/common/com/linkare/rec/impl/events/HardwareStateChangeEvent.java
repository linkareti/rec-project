/*
 * HardwareStateChangeEvent.java
 *
 * Created on 6 de Novembro de 2002, 12:41
 */

package com.linkare.rec.impl.events;


/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class HardwareStateChangeEvent {

	/** Holds value of property newState. */
	private com.linkare.rec.acquisition.HardwareState newState;

	/** Creates a new instance of HardwareStateChangeEvent 
	 * @param newState */
	public HardwareStateChangeEvent(com.linkare.rec.acquisition.HardwareState newState) {
		this.newState = newState;
	}

	/**
	 * Getter for property newState.
	 * 
	 * @return Value of property newState.
	 */
	public com.linkare.rec.acquisition.HardwareState getNewState() {
		return this.newState;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return super.toString() + " - State = " + newState;
	}

}
