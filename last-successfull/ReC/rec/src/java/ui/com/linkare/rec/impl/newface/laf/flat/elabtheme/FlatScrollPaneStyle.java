package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;

public class FlatScrollPaneStyle extends DefaultStyle {
	public static final String ID = "FlatScrollPane";
	public static final Border THIN_BLUE_BORDER = BorderFactory.createCompoundBorder(
			javax.swing.BorderFactory.createLineBorder(new Color(0x517DA8)),
			javax.swing.BorderFactory.createEmptyBorder(2, 3, 2, 3));

	/**
	 * Creates the <code>FlatScrollPane</code>.
	 */
	public FlatScrollPaneStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return FlatScrollPaneStyle.ID;
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(AbstractStyle.BORDER, FlatScrollPaneStyle.THIN_BLUE_BORDER);
	}
}