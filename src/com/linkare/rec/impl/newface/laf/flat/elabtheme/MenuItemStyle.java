/* 
 * MenuBarStyle.java created on Mar 27, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.util.Map;

import com.linkare.rec.impl.newface.laf.flat.theme.Style;

/**
 * @author Henrique Fernandes
 */
@Style
public class MenuItemStyle extends DefaultStyle {

	public static final String ID = "MenuItem";
	
	public static final Object MENUITEM_DISABLEDFOREGROUND_COLOR = new Color(0x677478);
	
	public static final Color MENUITEM_FOREGROUND_COLOR = new Color(0xE4EEED);

	/**
	 * Creates the <code>MenuItemStyle</code>.
	 */
	public MenuItemStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ID;
	}

	@Override
	public void updatePropertyMap(Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(BORDER, EMPTY_BORDER_MARGIN_4);
		map.put(DISABLED_FOREGROUND, MENUITEM_DISABLEDFOREGROUND_COLOR);
		//map.put(FOREGROUND, MENUITEM_FOREGROUND_COLOR);
		//map.put(SELECTION_FOREGROUND, WHITE_DEFAULT_COLOR);
	}

	// -------------------------------------------------------------------------
	// TODO Getters (Used to marshal values to xml)
	
}
