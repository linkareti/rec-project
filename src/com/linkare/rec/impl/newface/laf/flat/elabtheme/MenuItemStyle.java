/* 
 * MenuBarStyle.java created on Mar 27, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.InsetsUIResource;

import com.linkare.rec.impl.newface.laf.flat.theme.Style;

/**
 * @author Henrique Fernandes
 */
@Style
public class MenuItemStyle extends DefaultStyle {

	//ENABLED COLORS
	private static final Color COLOR_MENU_ITEM_BG = new Color(0x285357);
	private static final Color COLOR_MENU_ITEM_FG = new Color(0xE4EEED);
	private static final Color COLOR_MENU_BORDER = new Color(0xE4EEED);
	private static final Color COLOR_MENU_DISABLED_FG = new Color(0x22363A);
	//PROPERTIES
	private static final String PT_MENU_SELECTION_BG = "selectionBackground";
	private static final String PT_MENU_SELECTION_FG = "selectionForeground"; 
	private static final String PT_MENU_DISABLED_FG = "disabledForeground"; 
	private static final String PT_MENU_MARGIN = "margin";
	 
	//BORDER
	public static final Border SOLID_THIN_BORDER = BorderFactory.createLineBorder(COLOR_MENU_BORDER);
		
	//LABEL FONT
	public static final Font FONT_MENU = new FontUIResource("Arial", Font.BOLD, 10);
	
	public static final String ID = "MenuItem";
	
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
		map.put(BORDER, EMPTY_BORDER_MARGIN_2);
		map.put(FOREGROUND, COLOR_MENU_ITEM_FG);
		map.put(BACKGROUND, COLOR_MENU_ITEM_BG);
		map.put(PT_MENU_SELECTION_FG, COLOR_MENU_ITEM_BG);
		map.put(FONT, FONT_MENU);
	}

	// -------------------------------------------------------------------------
	// TODO Getters (Used to marshal values to xml)
	
}
