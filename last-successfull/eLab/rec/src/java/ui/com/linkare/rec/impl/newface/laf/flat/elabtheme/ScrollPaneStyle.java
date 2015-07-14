package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.util.Map;

import javax.swing.BorderFactory;

import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;

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
		return ScrollPaneStyle.ID;
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(AbstractStyle.BORDER, BorderFactory.createEmptyBorder());
	}
}
