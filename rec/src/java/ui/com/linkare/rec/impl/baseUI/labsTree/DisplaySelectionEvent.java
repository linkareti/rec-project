/*
 * DisplaySelectionEvent.java
 *
 * Created on 19/07/04 4:42pm
 */

package com.linkare.rec.impl.baseUI.labsTree;

/**
 *
 * @author André Neto - LEFT - IST
 */

import com.linkare.rec.impl.baseUI.config.Apparatus;
import com.linkare.rec.impl.baseUI.config.Display;
import com.linkare.rec.impl.baseUI.config.Lab;

public class DisplaySelectionEvent extends ApparatusSelectionEvent {

	/** Holds value of property Display. */
	private Display dp;

	/** Creates a new instance of DisplaySelectionEvent */
	public DisplaySelectionEvent(Object source, Display dp) {
		this(source, dp, null, null);
	}

	/** Creates a new instance of DisplaySelectionEvent */
	public DisplaySelectionEvent(Object source, Display dp, Apparatus app) {
		this(source, dp, app, null);
	}

	/** Creates a new instance of DisplaySelectionEvent */
	public DisplaySelectionEvent(Object source, Display dp, Apparatus app, Lab lab) {
		super(source, app, lab);
		this.dp = dp;
	}

	/**
	 * Getter for property Display.
	 * 
	 * @return Value of property Display.
	 */
	public Display getDisplay() {
		return this.dp;
	}
}
