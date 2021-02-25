/* 
 * ButtonStyle.java created on 2009/04/29
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;

/**
 * 
 * @author João Florindo
 */
public class ButtonStyle extends DefaultStyle {

	// Old Button
	// private static final FontUIResource FONT_BUTTON = new
	// FontUIResource(DEFAULT_FONT.getFontName(), Font.PLAIN, 12);
	// private static final List BUTTON_GRADIENT = Arrays.asList(new Object[] {
	// new ColorUIResource(0xF3F3F3), new ColorUIResource(0xFEFEFE) });
	// private static final Border COLOR_BORDER =
	// BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new
	// Color(0xC2C2C2)),
	// BorderFactory.createEmptyBorder(4, 16, 4, 16));
	// private static final Color COLOR_BUTTON_FG = new Color(0x848187);

	// New Button
	private static final FontUIResource FONT_BUTTON = new FontUIResource(DefaultStyle.DEFAULT_FONT.getFontName(),
			Font.PLAIN, 12);
	private static final List<Object> BUTTON_GRADIENT = Arrays.asList(new Object[] { Float.valueOf(.1f), Float.valueOf(0f),
			new ColorUIResource(0xD8E8F6), new ColorUIResource(0xEDF5FB), new ColorUIResource(0xD8F0FF) });
	private static final Border COLOR_BORDER = BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(new Color(0x636767)), BorderFactory.createEmptyBorder(4, 16, 4, 16));
	private static final Color COLOR_BUTTON_FG = new Color(0x395882);

	// PROPERTIES
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
		return ButtonStyle.ID;
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(ButtonStyle.PT_GRADIENT, ButtonStyle.BUTTON_GRADIENT);
		map.put(AbstractStyle.BORDER, ButtonStyle.COLOR_BORDER);
		map.put(AbstractStyle.FOREGROUND, ButtonStyle.COLOR_BUTTON_FG);
		map.put(ButtonStyle.ROLLOVER, false);

	}

}
