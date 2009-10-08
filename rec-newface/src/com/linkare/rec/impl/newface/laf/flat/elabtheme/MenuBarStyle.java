/* 
 * MenuBarStyle.java created on Mar 27, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.util.Arrays;
import java.util.Map;

import javax.swing.plaf.ColorUIResource;

import com.linkare.rec.impl.newface.laf.flat.theme.Style;

/**
 * 
 * @author Henrique Fernandes
 */
@Style
public class MenuBarStyle extends DefaultStyle {

    //ENABLED COLORS
    private static final Color COLOR_MENU_BG = new Color(0x285357);

    public static final String ID = "MenuBar";

    /**
     * Creates the <code>MenuBarStyle</code>.
     */
    public MenuBarStyle() {
	super();
    }

    @Override
    protected String defineStyleId() {
	return ID;
    }

    @Override
    public void updatePropertyMap(Map<String, Object> map) {
	super.updatePropertyMap(map);
	map.put(BORDER, EMPTY_BORDER);
	map.put("gradient", Arrays.asList(new Object[] { new Float(1f), new Float(0f), new ColorUIResource(0x0c1011),
		new ColorUIResource(0x2a3436), new ColorUIResource(0x2a3436) }));
    }

    // -------------------------------------------------------------------------
    // TODO Getters (Used to marshal values to xml)

}
