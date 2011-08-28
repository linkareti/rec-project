/*
 * WebResourceSelectionEvent.java
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
import com.linkare.rec.impl.baseUI.config.WebResource;

public class WebResourceSelectionEvent extends ApparatusSelectionEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2946313989803255566L;
	/** Holds value of property WebResource. */
	private final WebResource wr;

	/** Creates a new instance of WebResourceSelectionEvent */
	public WebResourceSelectionEvent(final Object source, final WebResource wr) {
		this(source, wr, null, null);
	}

	/** Creates a new instance of WebResourceSelectionEvent 
	 * @param source 
	 * @param wr 
	 * @param app */
	public WebResourceSelectionEvent(final Object source, final WebResource wr, final Apparatus app) {
		this(source, wr, app, null);
	}

	/** Creates a new instance of WebResourceSelectionEvent 
	 * @param source 
	 * @param wr 
	 * @param app 
	 * @param lab */
	public WebResourceSelectionEvent(final Object source, final WebResource wr, final Apparatus app, final Lab lab) {
		super(source, app, lab);
		this.wr = wr;
	}

	/**
	 * Getter for property WebResource.
	 * 
	 * @return Value of property WebResource.
	 */
	public WebResource getWebResource() {
		return wr;
	}
}
