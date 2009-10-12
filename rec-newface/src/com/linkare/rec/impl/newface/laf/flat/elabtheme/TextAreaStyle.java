/* 
 * TextFieldStyle.java created on Mar 27, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.util.Map;

import javax.swing.border.Border;

import com.linkare.rec.impl.newface.laf.flat.theme.Style;

/**
 * @author João Florindo
 */
@Style
public class TextAreaStyle extends DefaultStyle {

    public static final String ID = "TextArea";

    //    public static final FontUIResource TEXTAREA_FONT = new FontUIResource(DEFAULT_FONT.getFontName(), Font.PLAIN, 12);
    public static final Border TEXTAREA_BORDER = javax.swing.BorderFactory.createEmptyBorder(2, 3, 2, 3);
    public static final Color TEXTAREA_BACKGROUND = new Color(0xE4EEED);

    /**
     * Creates the <code>TextAreaStyle</code>.
     */
    public TextAreaStyle() {
	super();
    }

    @Override
    protected String defineStyleId() {
	return ID;
    }

    @Override
    public void updatePropertyMap(Map<String, Object> map) {
	super.updatePropertyMap(map);
	map.put(BORDER, TEXTAREA_BORDER);
	map.put(BACKGROUND, TEXTAREA_BACKGROUND);

    }

    // -------------------------------------------------------------------------
    // TODO Getters (Used to marshal values to xml)

}
