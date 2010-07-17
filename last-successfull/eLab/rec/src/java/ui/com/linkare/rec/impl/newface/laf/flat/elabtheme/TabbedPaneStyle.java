package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Font;
import java.util.Map;

import javax.swing.plaf.FontUIResource;

public class TabbedPaneStyle extends DefaultStyle {

	public static final String ID = "TabbedPane";

	//LABEL FONT
	public static final Font FONT_TABBEDPANE = new FontUIResource("Arial", Font.BOLD, 11);

	/**
	 * Creates the <code>ComboBoxStyle</code>.
	 */
	public TabbedPaneStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ID;
	}

	@Override
	public void updatePropertyMap(Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(FONT, FONT_TABBEDPANE);
	}
}
