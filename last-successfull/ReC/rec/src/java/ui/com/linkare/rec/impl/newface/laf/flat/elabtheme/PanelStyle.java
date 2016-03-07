/* 
 * PanelStyle.java created on Mar 27, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.swing.plaf.FontUIResource;

import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;
import com.linkare.rec.impl.newface.laf.flat.theme.Style;

/**
 * @author Henrique Fernandes
 */
@Style
public class PanelStyle extends DefaultStyle {

	public static final String ID = "Panel";

	public static final Color PANEL_BACKGROUND = new Color(0xE4EEED);
	public static final Color PANEL_FOREGROUND = new Color(0x231F20);

	/**
	 * Creates the <code>PanelStyle</code>.
	 */
	public PanelStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return PanelStyle.ID;
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(AbstractStyle.BACKGROUND, PanelStyle.PANEL_BACKGROUND);
		map.put(AbstractStyle.FOREGROUND, PanelStyle.PANEL_FOREGROUND);
		map.put(AbstractStyle.FONT, new FontUIResource("Arial", Font.PLAIN, 20));
	}

	// -------------------------------------------------------------------------
	// TODO Getters (Used to marshal values to xml)

}
