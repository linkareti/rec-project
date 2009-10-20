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
import javax.swing.plaf.metal.MetalButtonUI;

import sun.swing.SwingUtilities2;

import com.linkare.rec.impl.newface.component.FlatButton;

/**
 * 
 * @author João Florindo
 */
public class FlatButtonUI extends MetalButtonUI {

	private final static String propertyPrefix = "FlatButton" + ".";

	public static ComponentUI createUI(JComponent x) {
		return new FlatButtonUI();
	}

	@Override
	protected String getPropertyPrefix() {
		return propertyPrefix;
	}

	// ********************************
	//          Paint
	// ********************************

	@Override
	protected void paintButtonPressed(Graphics g, AbstractButton b) {
		//    	FlatButton fButton = (FlatButton)b;
		//    	    	
		//    	fButton.setForeground(fButton.getForegroundOn());
		//    	fButton.setBorder(BorderFactory.createLineBorder(fButton.getColorBorderOn()));
		//		FlatUtils.drawGradient(fButton, g, 0, 0, 0, b.getHeight(),
		//			new float[] {.0f, 1.0f},
		//			new Color[] {fButton.getGradientTopOn(), fButton.getGradientBottomOn()});
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		FlatButton fButton = (FlatButton) c;

		FlatUtils.drawGradient(fButton, g, 0, 0, 0, fButton.getHeight(), new float[] { .0f, .2f, .4f }, new Color[] {
				fButton.getGradientTop(), Color.white, fButton.getGradientBottom() });

		super.paint(g, c);
	}

	/*
	 * Para não ser desenhado o focus no FlatButton
	 */
	@Override
	protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {

	}

	@Override
	protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
		AbstractButton b = (AbstractButton) c;
		ButtonModel model = b.getModel();
		FontMetrics fm = SwingUtilities2.getFontMetrics(c, g);
		int mnemIndex = b.getDisplayedMnemonicIndex();

		/* Draw the Text */
		if (model.isEnabled()) {
			/*** paint the text normally */
			g.setColor(b.getForeground());
		} else {
			/*** paint the text disabled ***/
			g.setColor(getDisabledTextColor());
		}
		SwingUtilities2.drawStringUnderlineCharAt(c, g, text, mnemIndex, textRect.x, textRect.y + fm.getAscent());
	}

}
