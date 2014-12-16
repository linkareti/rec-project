/* 
 * ToolBarStyle.java created on Mar 27, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.util.Map;

import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;
import com.linkare.rec.impl.newface.laf.flat.theme.Style;

/**
 * @author Henrique Fernandes
 */
@Style
public class ToolBarStyle extends DefaultStyle {

	public static final String ID = "ToolBar";

	/**
	 * Creates the <code>ToolBarStyle</code>.
	 */
	public ToolBarStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ToolBarStyle.ID;
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(AbstractStyle.BACKGROUND, DefaultStyle.BLACK_DEFAULT_COLOR);
		map.put(AbstractStyle.BORDER, DefaultStyle.EMPTY_BORDER);
	}

	// -------------------------------------------------------------------------
	// TODO Getters (Used to marshal values to xml)

}
