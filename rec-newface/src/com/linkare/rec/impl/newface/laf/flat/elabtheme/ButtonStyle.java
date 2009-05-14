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
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.InsetsUIResource;

/**
 * 
 * @author João Florindo
 */
public class ButtonStyle extends DefaultStyle{
	
	
	private static final FontUIResource FONT_BUTTON = new FontUIResource(DEFAULT_FONT.getFontName(), Font.BOLD, 12);
	private static final List BUTTON_GRADIENT = Arrays.asList(new Object[] { new Float(.3f), new Float(0f),	new ColorUIResource(0xacd651), new ColorUIResource(0xffffff), new ColorUIResource(0x9fcb42) });
	private static final BorderUIResource COLOR_BORDER = new BorderUIResource(BorderFactory.createLineBorder(new Color(0x7da921)));
	private static final Color COLOR_BUTTON_BG = new Color(0xacd651);
	private static final Color COLOR_BUTTON_FG = new Color(0x2B7A3B);
	private static final InsetsUIResource MARGIN_BUTTON = new InsetsUIResource(2,8,2,8);
	
	//PROPERTIES
	private static final String PT_GRADIENT = "gradient";
	private static final String ROLLOVER = "rollover";	
	private static final String MARGIN = "margin";	
	
	
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
		map.put(FONT, FONT_BUTTON);
		map.put(PT_GRADIENT, BUTTON_GRADIENT);
		map.put(FOREGROUND, COLOR_BUTTON_FG);
//		map.put(MARGIN, MARGIN_BUTTON);
		map.put(ROLLOVER, false);
		
	}

}
