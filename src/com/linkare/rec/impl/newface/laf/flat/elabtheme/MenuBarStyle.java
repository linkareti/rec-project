/* 
 * MenuBarStyle.java created on Mar 27, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;

import com.linkare.rec.impl.newface.laf.flat.theme.Style;

/**
 * 
 * @author Henrique Fernandes
 */
@Style
public class MenuBarStyle extends DefaultStyle {

	//ENABLED COLORS
	private static final Color COLOR_MENU_BG = new Color(0x285357);
	
	public static final String ID = "MenuBar";
	/**
	 * Creates the <code>MenuBarStyle</code>.
	 */
	public MenuBarStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return  ID;
	}
	
	@Override
	public void updatePropertyMap(Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(BORDER, EMPTY_BORDER);
	}

	// -------------------------------------------------------------------------
	// TODO Getters (Used to marshal values to xml)
	

}
