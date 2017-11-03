/* 
 * VideoCanvas.java created on 4 Apr 2013
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.component;

import java.awt.Canvas;
import java.awt.GraphicsConfiguration;

import com.linkare.rec.impl.newface.laf.flat.Focus;

/**
 * 
 * @author jpereira - Linkare TI
 */
@Focus(display=false)
public class VideoCanvas extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = -879547900152148149L;

	/**
	 * Creates the <code>VideoCanvas</code>.
	 */
	public VideoCanvas() {
	}

	/**
	 * Creates the <code>VideoCanvas</code>.
	 * @param config
	 */
	public VideoCanvas(GraphicsConfiguration config) {
		super(config);
	}

}
