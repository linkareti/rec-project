/* 
 * EditorPaneStyle.java created on 2009/04/08
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.FontUIResource;

import com.linkare.rec.impl.newface.laf.flat.FlatComboBoxUI;

/**
 * 
 * @author João Florindo
 */
public class EditorPaneStyle extends DefaultStyle{
	
	public static final String ID = "EditorPane";
	
	//LABEL FONT
	public static final Font FONT_EDITORPANE = new FontUIResource("Arial", Font.BOLD, 12);	
	
	//PROPERTIES
	private static final String PT_EDITORPANE_BG = "background";
	private static final String PT_EDITORPANE_FG = "foreground";
	
	//COLORS	
	private static final Color COLOR_BORDER_SOLID_THIN_BLUE = new Color(0x8CABB3);	
	private static final Color COLOR_EDITORPANE_FG = BLACK_DEFAULT_COLOR;
	private static final Color COLOR_EDITORPANE_BG = WHITE_DEFAULT_COLOR;
	private static final Color COLOR_EDITORPANE_FONT = new Color(0x64797F);
	
	//BORDER
	private static final Border BORDER_COMBOBOX = BorderFactory.createLineBorder(COLOR_BORDER_SOLID_THIN_BLUE);

	
	/**
	 * Creates the <code>ComboBoxStyle</code>.
	 */
	public EditorPaneStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ID;
	}

	@Override
	public void updatePropertyMap(Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(BORDER, BORDER_COMBOBOX);
		map.put(PT_EDITORPANE_FG, COLOR_EDITORPANE_FG);
		map.put(PT_EDITORPANE_BG, COLOR_EDITORPANE_BG);
		map.put(FONT, FONT_EDITORPANE);
	}

}
