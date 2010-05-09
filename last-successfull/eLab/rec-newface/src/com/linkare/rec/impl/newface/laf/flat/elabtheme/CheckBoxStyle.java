/* 
 * RadioButtonStyle.java created on 2009/04/08
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.swing.plaf.FontUIResource;

/**
 * 
 * @author joao
 */
public class CheckBoxStyle extends DefaultStyle {

	//LABEL FONT
	public static final Font FONT_CHECKBOX = new FontUIResource("Arial", Font.BOLD, 12);

	private static final Color COLOR_CHECKBOX_FG = BLACK_DEFAULT_COLOR;
	private static final Color COLOR_CHECKBOX_BG = WHITE_DEFAULT_COLOR;

	public static final String ID = "CheckBox";

	/**
	 * Creates the <code>CheckBox</code>.
	 */
	public CheckBoxStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ID;
	}

	@Override
	public void updatePropertyMap(Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(FOREGROUND, COLOR_CHECKBOX_FG);
		map.put(BACKGROUND, COLOR_CHECKBOX_BG);

		map.put(FONT, FONT_CHECKBOX);
	}
}
