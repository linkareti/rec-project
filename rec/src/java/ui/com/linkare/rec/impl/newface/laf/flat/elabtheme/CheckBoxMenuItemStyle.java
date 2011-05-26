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

import javax.swing.plaf.FontUIResource;

import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;
import com.linkare.rec.impl.newface.laf.flat.theme.Style;

/**
 * @author João Florindo
 */
@Style
public class CheckBoxMenuItemStyle extends DefaultStyle {

	// ENABLED COLORS
	private static final Color COLOR_MENU_ITEM_BG = new Color(0x263537);
	private static final Color COLOR_MENU_ITEM_FG = new Color(0xE4EEED);
	private static final Color COLOR_MENU_BORDER = new Color(0xE4EEED);
	private static final Color COLOR_MENU_DISABLED_FG = new Color(0x22363A);
	private static final Color COLOR_MENU_ACCELERATOR_FG = new Color(0xE4EEED);
	// PROPERTIES
	private static final String PT_MENU_SELECTION_BG = "selectionBackground";
	private static final String PT_MENU_SELECTION_FG = "selectionForeground";
	private static final String PT_MENU_DISABLED_FG = "disabledForeground";
	private static final String PT_MENU_ACCELERATOR_FG = "acceleratorForeground";
	private static final String PT_MENU_MARGIN = "margin";
	private static final String PT_FONT_ACCELERATOR = "acceleratorFont";

	// LABEL FONT
	public static final Font FONT_MENU = new FontUIResource("Arial", Font.BOLD, 10);

	public static final String ID = "CheckBoxMenuItem";

	/**
	 * Creates the <code>RadioButtonMenuItem</code>.
	 */
	public CheckBoxMenuItemStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return CheckBoxMenuItemStyle.ID;
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(AbstractStyle.BORDER, DefaultStyle.EMPTY_BORDER_MARGIN_2);
		map.put(AbstractStyle.FOREGROUND, CheckBoxMenuItemStyle.COLOR_MENU_ITEM_FG);
		map.put(AbstractStyle.BACKGROUND, CheckBoxMenuItemStyle.COLOR_MENU_ITEM_BG);
		map.put(CheckBoxMenuItemStyle.PT_MENU_SELECTION_FG, CheckBoxMenuItemStyle.COLOR_MENU_ITEM_BG);
		map.put(CheckBoxMenuItemStyle.PT_MENU_SELECTION_BG, CheckBoxMenuItemStyle.PT_MENU_SELECTION_FG);
		map.put(AbstractStyle.FONT, CheckBoxMenuItemStyle.FONT_MENU);
		map.put(CheckBoxMenuItemStyle.PT_FONT_ACCELERATOR, CheckBoxMenuItemStyle.FONT_MENU);
		map.put(CheckBoxMenuItemStyle.PT_MENU_ACCELERATOR_FG, CheckBoxMenuItemStyle.COLOR_MENU_ACCELERATOR_FG);
	}

	// -------------------------------------------------------------------------
	// TODO Getters (Used to marshal values to xml)

}
