/* 
 * ButtonStyle.java created on 2009/04/29
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.InsetsUIResource;

/**
 * 
 * @author João Florindo
 */
public class ButtonStyle extends DefaultStyle{
	
	
	private static final FontUIResource FONT_BUTTON = new FontUIResource(DEFAULT_FONT.getFontName(), Font.PLAIN, 12);
	private static final List BUTTON_GRADIENT = Arrays.asList(new Object[] { new Float(.1f), new Float(0f),	new ColorUIResource(0xDBDBDB), new ColorUIResource(0xDBDBDB), new ColorUIResource(0xE0E3FF) });
	private static final Border COLOR_BORDER = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0xC8CCC5)), BorderFactory.createEmptyBorder(4, 16, 4, 16));
	private static final Color COLOR_BUTTON_FG = new Color(0x848187);
	
	//PROPERTIES
	private static final String PT_GRADIENT = "gradient";
	private static final String ROLLOVER = "rollover";	
	
	
	public static final String ID = "Button";

	/**
	 * Creates the <code>ButtonStyle</code>.
	 */
	public ButtonStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ID;
	}

	@Override
	public void updatePropertyMap(Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(PT_GRADIENT, BUTTON_GRADIENT);
		map.put(BORDER, COLOR_BORDER);
		map.put(FOREGROUND, COLOR_BUTTON_FG);
		map.put(ROLLOVER, false);
		
	}

}
