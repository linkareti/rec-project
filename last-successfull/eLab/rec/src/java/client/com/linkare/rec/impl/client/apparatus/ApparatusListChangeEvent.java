/*
 * ApparatusListChangeEvent.java
 *
 * Created on 9 de Julho de 2003, 18:50
 */

package com.linkare.rec.impl.client.apparatus;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ApparatusListChangeEvent extends java.util.EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7383529014751063949L;
	/** Holds value of property apparatus. */
	private Apparatus[] apparatus;

	/**
	 * Creates a new instance of ApparatusListChangeEvent
	 * 
	 * @param source
	 * @param apparatus
	 */
	public ApparatusListChangeEvent(final Object source, final Apparatus[] apparatus) {
		super(source);
		this.apparatus = apparatus;
	}

	/**
	 * Getter for property apparatus.
	 * 
	 * @return Value of property apparatus.
	 */
	public Apparatus[] getApparatus() {
		return apparatus;
	}

	/**
	 * Setter for property apparatus.
	 * 
	 * @param apparatus New value of property apparatus.
	 */
	public void setApparatus(final Apparatus[] apparatus) {
		this.apparatus = apparatus;
	}

}
