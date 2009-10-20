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

	public static ComponentUI createUI(JComponent x) {
		return new FlatOriginalButtonUI();
	}

	@Override
	protected String getPropertyPrefix() {
		return propertyPrefix;
	}

	// ********************************
	//          Paint
	// ********************************

	/*
	 * Para não ser desenhado o focus no Button
	 */
	@Override
	protected void paintButtonPressed(Graphics g, AbstractButton b) {
	}

	/*
	 * Para não ser desenhado o focus no Button
	 */
	@Override
	protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {

	}

}
