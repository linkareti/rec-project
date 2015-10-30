/* 
 * RadioButtonStyle.java created on 2009/04/08
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.util.Map;

import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;

/**
 * 
 * @author joao
 */
public class RadioButtonStyle extends DefaultStyle {

	public static final String ID = "RadioButton";

	// COLORS
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
		return RadioButtonStyle.ID;
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(AbstractStyle.FOREGROUND, RadioButtonStyle.COLOR_RADIOBUTTON_FG);
		map.put(AbstractStyle.BACKGROUND, RadioButtonStyle.COLOR_RADIOBUTTON_BG);
	}

}
