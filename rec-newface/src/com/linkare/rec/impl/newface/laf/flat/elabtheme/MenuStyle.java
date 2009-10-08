/* 
 * MenuStyle.java created on Mar 27, 2009
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

import com.linkare.rec.impl.newface.laf.flat.theme.Style;

/**
 * @author Henrique Fernandes
 */
@Style
public class MenuStyle extends DefaultStyle {

    //ENABLED COLORS
    private static final Color COLOR_MENU_BG = new Color(0x285357);
    private static final Color COLOR_MENU_FG = new Color(0xE4EEED);
    private static final Color COLOR_MENU_ITEM_HOVER = new Color(0x22363A);
    private static final Color COLOR_MENU_DISABLED_FG = new Color(0x22363A);
    //PROPERTIES
    private static final String PT_MENU_SELECTION_FG = "selectionForeground";
    private static final String PT_MENU_SELECTION_BG = "selectionBackground";
    private static final String PT_MENU_DISABLED_FG = "disabledForeground";

    //LABEL FONT
    public static final Font FONT_MENU = new FontUIResource("Arial", Font.PLAIN, 12);

    public static final String ID = "Menu";

    /**
     * Creates the <code>MenuStyle</code>.
     */
    public MenuStyle() {
	super();
    }

    @Override
    protected String defineStyleId() {
	return ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePropertyMap(Map<String, Object> map) {
	super.updatePropertyMap(map);
	map.put(FOREGROUND, WHITE_DEFAULT_COLOR);
	map.put(PT_MENU_SELECTION_FG, COLOR_MENU_ITEM_HOVER);
	map.put(PT_MENU_SELECTION_BG, COLOR_MENU_FG);

	map.put(BORDER, EMPTY_BORDER);
	map.put(FONT, FONT_MENU);

    }

    // -------------------------------------------------------------------------
    // TODO Getters (Used to marshal values to xml)

}
