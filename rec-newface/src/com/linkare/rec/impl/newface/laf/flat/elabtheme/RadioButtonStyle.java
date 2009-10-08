/* 
 * RadioButtonStyle.java created on 2009/04/08
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.util.Map;

/**
 * 
 * @author joao
 */
public class RadioButtonStyle extends DefaultStyle {

    public static final String ID = "RadioButton";

    //COLORS	
    private static final Color COLOR_RADIOBUTTON_FG = new Color(0x515151);
    private static final Color COLOR_RADIOBUTTON_BG = new Color(0xE4EEED);

    /**
     * Creates the <code>RadioButtonStyle</code>.
     */
    public RadioButtonStyle() {
	super();
    }

    @Override
    protected String defineStyleId() {
	return ID;
    }

    @Override
    public void updatePropertyMap(Map<String, Object> map) {
	super.updatePropertyMap(map);
	map.put(FOREGROUND, COLOR_RADIOBUTTON_FG);
	map.put(BACKGROUND, COLOR_RADIOBUTTON_BG);
    }

}
