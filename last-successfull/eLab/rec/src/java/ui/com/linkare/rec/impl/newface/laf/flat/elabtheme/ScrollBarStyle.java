/* 
 * ScrollBarStyle.java created on 2009/04/14
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.util.Map;

/**
 * 
 * @author João FLorindo
 */
public class ScrollBarStyle extends DefaultStyle {

	public static final String ID = "ScrollBar";

	// PROPERTIES
	public static final String PT_WIDTH = "width";

	/**
	 * Creates the <code>ScrollBarStyle</code>.
	 */
	public ScrollBarStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ScrollBarStyle.ID;
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(ScrollBarStyle.PT_WIDTH, 18);
	}

	// -------------------------------------------------------------------------
	// TODO Getters (Used to marshal values to xml)

}
