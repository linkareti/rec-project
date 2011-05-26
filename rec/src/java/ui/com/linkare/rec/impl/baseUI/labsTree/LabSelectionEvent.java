/*
 * LabSelectionEvent.java
 *
 * Created on 19/07/04 4:42pm
 */

package com.linkare.rec.impl.baseUI.labsTree;

/**
 *
 * @author  adnre
 */

import com.linkare.rec.impl.baseUI.config.Lab;

public class LabSelectionEvent extends java.util.EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3174550932608912559L;
	/** Holds value of property Lab. */
	private final Lab lab;

	/** Creates a new instance of LabSelectionEvent */
	public LabSelectionEvent(final Object source, final Lab lab) {
		super(source);
		this.lab = lab;
	}

	/**
	 * Getter for property Lab.
	 * 
	 * @return Value of property Lab.
	 */
	public Lab getLab() {
		return lab;
	}
}
