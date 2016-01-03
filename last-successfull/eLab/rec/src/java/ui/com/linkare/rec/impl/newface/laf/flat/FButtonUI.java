package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;

public class FButtonUI extends MetalButtonUI {

	public static ComponentUI createUI(final JComponent x) {
		return new FButtonUI();
	}

	@Override
	protected void paintFocus(final Graphics g, final AbstractButton b, final Rectangle viewRect,
			final Rectangle textRect, final Rectangle iconRect) {
		// Focus is rendered by FlatFocusRenderer
	}

}
