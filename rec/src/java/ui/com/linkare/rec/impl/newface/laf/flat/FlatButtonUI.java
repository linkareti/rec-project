/*
 * FlatButtonUI.java created on 2009/04/29
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.metal.MetalButtonUI;

import com.linkare.rec.impl.newface.component.FlatButton;

/**
 * 
 * @author João Florindo
 */
public class FlatButtonUI extends MetalButtonUI {

	private final static String propertyPrefix = "FlatButton" + ".";

	public static ComponentUI createUI(final JComponent x) {
		return new FlatButtonUI();
	}

	@Override
	protected String getPropertyPrefix() {
		return FlatButtonUI.propertyPrefix;
	}

	// ********************************
	// Paint
	// ********************************

	@Override
	protected void paintButtonPressed(final Graphics g, final AbstractButton b) {
		// FlatButton fButton = (FlatButton) b;

		// fButton.setForeground(fButton.getForeground());
		// fButton.setBorder(BorderFactory.createLineBorder(fButton.getBorderColor()));
		// FlatUtils.drawGradient(fButton, g, 0, 0, 0, b.getHeight(), new
		// float[] { .0f, 1.0f }, new Color[] { fButton.getGradientTop(),
		// fButton.getGradientBottom() });
	}

	@Override
	public void paint(final Graphics g, final JComponent c) {
		final FlatButton fButton = (FlatButton) c;

		// FlatUtils.drawGradient(fButton, g, 0, 0, 0, fButton.getHeight(), new
		// float[] { .0f, .2f, .4f }, new Color[] {
		// fButton.getGradientTop(), Color.white, fButton.getGradientBottom()
		// });

		FlatUtils.drawGradient(fButton, g, 0, 0, 0, fButton.getHeight(), new float[] { .0f, .4f }, new Color[] {
				fButton.getGradientTop(), fButton.getGradientBottom() });

		super.paint(g, c);
	}

	@Override
	protected void paintFocus(final Graphics g, final AbstractButton b, final Rectangle viewRect,
			final Rectangle textRect, final Rectangle iconRect) {
		// Para não ser desenhado o focus no FlatButton
	}

	@Override
	protected void paintText(final Graphics g, final JComponent c, final Rectangle textRect, final String text) {
		final AbstractButton b = (AbstractButton) c;
		final ButtonModel model = b.getModel();
		final FontMetrics fm = g.getFontMetrics(c.getFont());
		final int mnemIndex = b.getDisplayedMnemonicIndex();

		/* Draw the Text */
		if (model.isEnabled()) {
			/*** paint the text normally */
			g.setColor(b.getForeground());
		} else {
			/*** paint the text disabled ***/
			g.setColor(getDisabledTextColor());
		}
		BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, mnemIndex, textRect.x, textRect.y + fm.getAscent());
	}

}
