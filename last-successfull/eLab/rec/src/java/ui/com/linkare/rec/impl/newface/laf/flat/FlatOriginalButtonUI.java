/*
 * FlatButtonUI.java created on 2009/04/29
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;

/**
 * 
 * @author João Florindo
 */
public class FlatOriginalButtonUI extends MetalButtonUI {

	private final static String propertyPrefix = "Button" + ".";

	public static ComponentUI createUI(final JComponent x) {
		return new FlatOriginalButtonUI();
	}

	@Override
	protected String getPropertyPrefix() {
		return FlatOriginalButtonUI.propertyPrefix;
	}

	// ********************************
	// Paint
	// ********************************

	@Override
	protected void paintButtonPressed(final Graphics g, final AbstractButton b) {
		// FIXME João: Podes confirmar se este noop é está mesmo relacionado com
		// o focus? Não poderá influenciar o comportamento do button pressed?
	}

	@Override
	protected void paintFocus(final Graphics g, final AbstractButton b, final Rectangle viewRect,
			final Rectangle textRect, final Rectangle iconRect) {
		// Para não ser desenhado o focus no Button
	}

}
