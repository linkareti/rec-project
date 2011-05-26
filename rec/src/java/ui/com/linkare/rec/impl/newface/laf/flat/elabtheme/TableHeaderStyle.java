/* 
 * ListStyle.java created on 2009/04/30
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.util.Map;

import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;

/**
 * 
 * @author João FLorindo
 */
public class TableHeaderStyle extends DefaultStyle {

	// ENABLED COLORS
	private static final Color COLOR_HEADER_BG = new Color(0xCED7D5);
	private static final Color COLOR_HEADER_FG = new Color(0x41494D);

	public static final String ID = "TableHeader";

	/**
	 * Creates the <code>TableHeaderStyle</code>.
	 */
	public TableHeaderStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return TableHeaderStyle.ID;
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(AbstractStyle.FOREGROUND, TableHeaderStyle.COLOR_HEADER_FG);
		map.put(AbstractStyle.BACKGROUND, TableHeaderStyle.COLOR_HEADER_BG);
		map.put("cellBorder", DefaultStyle.EMPTY_BORDER_MARGIN_4);
		map.put(AbstractStyle.FONT, DefaultStyle.DEFAULT_FONT.deriveFont(java.awt.Font.BOLD));
	}
}
