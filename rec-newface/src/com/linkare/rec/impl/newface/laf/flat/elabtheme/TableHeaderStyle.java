/* 
 * ListStyle.java created on 2009/04/30
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

/**
 * 
 * @author João FLorindo
 */
public class TableHeaderStyle extends DefaultStyle {

    //ENABLED COLORS
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
	return ID;
    }

    @Override
    public void updatePropertyMap(Map<String, Object> map) {
	super.updatePropertyMap(map);
	map.put(FOREGROUND, COLOR_HEADER_FG);
	map.put(BACKGROUND, COLOR_HEADER_BG);
	map.put("cellBorder", EMPTY_BORDER_MARGIN_4);
	map.put(FONT, DEFAULT_FONT.deriveFont(java.awt.Font.BOLD));
    }
}
