/* 
 * PanelStyle.java created on Mar 27, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.util.Map;

import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;
import com.linkare.rec.impl.newface.laf.flat.theme.Style;

/**
 * @author Henrique Fernandes
 */
@Style
public class FlatListStyle extends DefaultStyle {

	public static final String ID = "List";

	public static final Color LIST_BACKGROUND = DefaultStyle.WHITE_DEFAULT_COLOR;
	public static final Color LIST_FOREGROUND = new Color(0x231F20);

	/**
	 * Creates the <code>ListStyle</code>.
	 */
	public FlatListStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return FlatListStyle.ID;
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(AbstractStyle.BACKGROUND, FlatListStyle.LIST_BACKGROUND);
		map.put(AbstractStyle.FOREGROUND, FlatListStyle.LIST_FOREGROUND);
		map.put(AbstractStyle.FONT, DefaultStyle.DEFAULT_FONT);
	}

}
