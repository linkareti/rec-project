package com.linkare.rec.impl.newface.component;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class FlatScrollPane extends JScrollPane {

	// private static final String uiClassID = "FlatScrollPaneUI";

	/**
	 * 
	 */
	private static final long serialVersionUID = -3293207949532846342L;

	public FlatScrollPane(final Component view, final int vertical_scrollbar_as_needed,
			final int horizontal_scrollbar_as_needed) {
		super(view, vertical_scrollbar_as_needed, horizontal_scrollbar_as_needed);
	}

	public FlatScrollPane(final Component view) {
		super(view, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}

	public FlatScrollPane() {
		super();
	}

	// @Override
	// public String getUIClassID() {
	// return uiClassID;
	// }
}
