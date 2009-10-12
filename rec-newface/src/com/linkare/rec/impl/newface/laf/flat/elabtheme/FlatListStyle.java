/* 
 * PanelStyle.java created on Mar 27, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.swing.plaf.FontUIResource;

import com.linkare.rec.impl.newface.laf.flat.theme.Style;

/**
 * @author Henrique Fernandes
 */
@Style
public class FlatListStyle extends DefaultStyle {

    public static final String ID = "List";

    public static final Color LIST_BACKGROUND = new Color(0xE4EEED);
    public static final Color LIST_FOREGROUND = new Color(0x231F20);

    /**
     * Creates the <code>ListStyle</code>.
     */
    public FlatListStyle() {
	super();
    }

    @Override
    protected String defineStyleId() {
	return ID;
    }

    @Override
    public void updatePropertyMap(Map<String, Object> map) {
	super.updatePropertyMap(map);
	map.put(BACKGROUND, LIST_BACKGROUND);
	map.put(FOREGROUND, LIST_FOREGROUND);
	map.put(FONT, new FontUIResource("Arial", Font.PLAIN, 20));
    }


}
