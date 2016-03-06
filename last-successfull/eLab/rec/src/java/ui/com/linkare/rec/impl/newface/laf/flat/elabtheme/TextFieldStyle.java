/* 
 * TextFieldStyle.java created on Mar 27, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;

import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;
import com.linkare.rec.impl.newface.laf.flat.theme.Style;

/**
 * @author Henrique Fernandes
 */
@Style
public class TextFieldStyle extends DefaultStyle {

	public static final String ID = "TextField";

	public static final FontUIResource TEXTFIELD_FONT = new FontUIResource(DefaultStyle.DEFAULT_FONT.getFontName(),
			Font.PLAIN, 12);
	public static final Border THIN_BLUE_BORDER = BorderFactory.createCompoundBorder(
			javax.swing.BorderFactory.createLineBorder(new Color(0x517DA8)),
			javax.swing.BorderFactory.createEmptyBorder(2, 3, 2, 3));

	/**
	 * Creates the <code>TextFieldStyle</code>.
	 */
	public TextFieldStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return TextFieldStyle.ID;
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(AbstractStyle.BORDER, TextFieldStyle.THIN_BLUE_BORDER);
		map.put(AbstractStyle.BACKGROUND, Color.white);

	}

	// -------------------------------------------------------------------------
	// TODO Getters (Used to marshal values to xml)

}