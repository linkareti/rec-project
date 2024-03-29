/* 
 * EditorPaneStyle.java created on 2009/04/08
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;

/**
 * 
 * @author João Florindo
 */
public class EditorPaneStyle extends DefaultStyle {

	public static final String ID = "EditorPane";

	public static final Border THIN_BLUE_BORDER = BorderFactory.createCompoundBorder(
			javax.swing.BorderFactory.createLineBorder(new Color(0x517DA8)),
			javax.swing.BorderFactory.createEmptyBorder(2, 3, 2, 3));

	// COLORS
	private static final Color COLOR_BORDER_SOLID_THIN_BLUE = new Color(0x8CABB3);
	private static final Color COLOR_EDITORPANE_FG = DefaultStyle.BLACK_DEFAULT_COLOR;
	private static final Color COLOR_EDITORPANE_BG = DefaultStyle.WHITE_DEFAULT_COLOR;

	/**
	 * Creates the <code>EditorPaneStyle</code>.
	 */
	public EditorPaneStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return EditorPaneStyle.ID;
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		// map.put(BORDER, THIN_BLUE_BORDER); //IF uncomment create double
		// border in chatbox
		map.put(AbstractStyle.FOREGROUND, EditorPaneStyle.COLOR_EDITORPANE_FG);
		map.put(AbstractStyle.BACKGROUND, EditorPaneStyle.COLOR_EDITORPANE_BG);
	}

}
