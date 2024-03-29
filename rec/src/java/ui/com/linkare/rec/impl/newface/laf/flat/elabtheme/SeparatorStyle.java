/* 
 * SeparatorStyle.java created on 2009/04/27
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
 * @author João Florindo
 */
public class SeparatorStyle extends DefaultStyle {

	// ENABLED COLORS
	// private static final Color COLOR_SEPARATOR_BG = new Color(0x263537);
	private static final Color COLOR_SEPARATOR_BG = new Color(0xE4EEED);
	private static final Color COLOR_SEPARATOR_FG = new Color(0xE4EEED);
	private static final Color COLOR_SEPARATOR_SHADOW = new Color(0xE4EEED);
	private static final Color COLOR_SEPARATOR_HIGHTLIGHT = new Color(0xE4EEED);
	// PROPERTIES
	private static final String PT_SEPARATOR_SHADOW = "shadow";
	private static final String PT_SEPARATOR_HIGHTLIGHT = "hightlight";

	public static final String ID = "Separator";

	/**
	 * Creates the <code>Separator</code>.
	 */
	public SeparatorStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return SeparatorStyle.ID;
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(AbstractStyle.FOREGROUND, SeparatorStyle.COLOR_SEPARATOR_FG);
		map.put(AbstractStyle.BACKGROUND, SeparatorStyle.COLOR_SEPARATOR_BG);
		// map.put(PT_SEPARATOR_SHADOW, COLOR_SEPARATOR_SHADOW);
		// map.put(PT_SEPARATOR_HIGHTLIGHT, COLOR_SEPARATOR_HIGHTLIGHT);

	}
}
