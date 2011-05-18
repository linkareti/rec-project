/* 
 * FlatButtonStyle.java created on 2009/04/29
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * 
 * @author João Florindo
 */
public class FlatButtonStyle extends DefaultStyle {

	private static final Border COLOR_BORDER = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0x7da921)),
			BorderFactory.createEmptyBorder(4, 16, 4, 16));
	private static final Color COLOR_BUTTON_FG = new Color(0x2B7A3B);

	public static final String ID = "FlatButton";

	/**
	 * Creates the <code>FlatButtonStyle</code>.
	 */
	public FlatButtonStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ID;
	}

	@Override
	public void updatePropertyMap(Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(BORDER, COLOR_BORDER);
		map.put(FOREGROUND, COLOR_BUTTON_FG);
	}
}
