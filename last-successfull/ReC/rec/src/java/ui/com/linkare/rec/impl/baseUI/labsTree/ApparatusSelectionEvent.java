/*
 * ApparatusSelectionEvent.java
 *
 * Created on 19/07/04 4:42pm
 */

package com.linkare.rec.impl.baseUI.labsTree;

/**
 *
 * @author André Neto - LEFT - IST
 */

import com.linkare.rec.impl.baseUI.config.Apparatus;
import com.linkare.rec.impl.baseUI.config.Lab;

public class ApparatusSelectionEvent extends LabSelectionEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2546597189502256910L;
	/** Holds value of property Apparatus. */
	private final Apparatus app;

	/** Creates a new instance of ApparatusSelectionEvent 
	 * @param source 
	 * @param app */
	public ApparatusSelectionEvent(final Object source, final Apparatus app) {
		this(source, app, null);
	}

	/** Creates a new instance of ApparatusSelectionEvent 
	 * @param source 
	 * @param app 
	 * @param lab */
	public ApparatusSelectionEvent(final Object source, final Apparatus app, final Lab lab) {
		super(source, lab);
		this.app = app;
	}

	/**
	 * Getter for property Apparatus.
	 * 
	 * @return Value of property Apparatus.
	 */
	public Apparatus getApparatus() {
		return app;
	}
}
