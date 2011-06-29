/*
 * DefaultConfigSelectionEvent.java
 *
 * Created on 19/07/04 4:42pm
 */

package com.linkare.rec.impl.baseUI.labsTree;

/**
 *
 * @author André Neto - LEFT - IST
 */

import com.linkare.rec.impl.baseUI.config.Apparatus;
import com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig;
import com.linkare.rec.impl.baseUI.config.Lab;

public class DefaultConfigSelectionEvent extends ApparatusSelectionEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5123794113680057337L;
	/** Holds value of property DefaultConfig. */
	private final DefaultAcquisitionConfig cfg;

	/** Creates a new instance of DefaultConfigSelectionEvent */
	public DefaultConfigSelectionEvent(final Object source, final DefaultAcquisitionConfig cfg) {
		this(source, cfg, null, null);
	}

	/** Creates a new instance of DefaultConfigSelectionEvent */
	public DefaultConfigSelectionEvent(final Object source, final DefaultAcquisitionConfig cfg, final Apparatus app) {
		this(source, cfg, app, null);
	}

	/** Creates a new instance of DefaultConfigSelectionEvent */
	public DefaultConfigSelectionEvent(final Object source, final DefaultAcquisitionConfig cfg, final Apparatus app,
			final Lab lab) {
		super(source, app, lab);
		this.cfg = cfg;
	}

	/**
	 * Getter for property DefaultConfig.
	 * 
	 * @return Value of property DefaultConfig.
	 */
	public DefaultAcquisitionConfig getDefaultConfig() {
		return cfg;
	}
}
