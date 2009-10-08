package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class ScrollPaneStyle extends DefaultStyle {

	public static final String ID = "ScrollPane";
	
	/**
	 * Creates the <code>ScrollPaneStyle</code>.
	 */
	public ScrollPaneStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ID;
	}

	@Override
	public void updatePropertyMap(Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(BORDER, BorderFactory.createEmptyBorder());
	}
}
