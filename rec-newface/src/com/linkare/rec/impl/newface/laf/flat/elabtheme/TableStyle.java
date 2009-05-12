/* 
 * ListStyle.java created on 2009/04/30
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
 * @author João FLorindo
 */
public class TableStyle extends DefaultStyle{

	//ENABLED COLORS
	private static final Color COLOR_MENU_ITEM_BG = new Color(0x263537);
	private static final Color COLOR_MENU_ITEM_FG = new Color(0xE4EEED);
	private static final Color COLOR_MENU_BORDER = new Color(0xE4EEED);
	private static final Color COLOR_MENU_DISABLED_FG = new Color(0x22363A);
	
	//LABEL FONT
	public static final Font FONT_BUTTON = new FontUIResource("Arial", Font.BOLD, 10);
	
	public static final String ID = "Table";
	
	/**
	 * Creates the <code>TableStyle</code>.
	 */
	public TableStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ID;
	}

	@Override
	public void updatePropertyMap(Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(FOREGROUND, Color.black);
		map.put(BACKGROUND, Color.black);

		map.put("gridColor", Color.red); 
		map.put("scrollPaneBorder", Color.red); 
		map.put("dropLineShortColor", Color.yellow);	
		map.put("dropLineColor", Color.yellow);	
		
		map.put("dropCellBackground", Color.cyan);
		map.put("dropCellForeground", Color.red);
		map.put("focusCellBackground", Color.yellow);
		map.put("focusCellForeground", Color.blue);	
		
		map.put(FONT, FONT_BUTTON);
	}
}
