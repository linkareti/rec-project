package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Font;
import java.util.Map;

import javax.swing.plaf.FontUIResource;

import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;

public class TabbedPaneStyle extends DefaultStyle {

	public static final String ID = "TabbedPane";

	// LABEL FONT
	public static final Font FONT_TABBEDPANE = new FontUIResource("Arial", Font.BOLD, 11);

	/**
	 * Creates the <code>ComboBoxStyle</code>.
	 */
	public TabbedPaneStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return TabbedPaneStyle.ID;
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(AbstractStyle.FONT, TabbedPaneStyle.FONT_TABBEDPANE);
	}
}
