/* 
 * FlatMenuUI.java created on Feb 26, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuUI;

import sun.swing.SwingUtilities2;

import com.linkare.rec.impl.newface.laf.flat.resources.FlatLAFResources;
import com.linkare.rec.impl.newface.laf.flat.resources.FlatLAFResources.FlatLAFResourcesEnum;

/**
 * @deprecated
 * @author Henrique Fernandes
 */
public class FlatMenuUI extends BasicMenuUI {

	private static final BufferedImage backgroundImg = FlatLAFResources
			.getImage(FlatLAFResourcesEnum.FLATMENUBAR_BACKGROUD_IMG.getName());

	public static ComponentUI createUI(JComponent x) {
		return new FlatMenuUI();
	}

	@Override
	protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
		// Simply draw the background image
		g.drawImage(backgroundImg, 0, 0, menuItem.getWidth(), menuItem.getHeight(), 0, 0, backgroundImg.getWidth(),
				backgroundImg.getHeight(), null);
	}

	@Override
	protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text) {
		ButtonModel model = menuItem.getModel();
		FontMetrics fm = SwingUtilities2.getFontMetrics(menuItem, g);
		int mnemIndex = menuItem.getDisplayedMnemonicIndex();

		if (!model.isEnabled()) {
			super.paintText(g, menuItem, textRect, text);
		} else {
			// *** paint the text normally
			g.setColor(UIManager.getColor("MenuItem.foreground"));

			if (model.isArmed() || (menuItem instanceof JMenu && model.isSelected())) {
				g.setColor(UIManager.getColor("MenuItem.selectionForeground")); // Uses
																				// protected
																				// field.
			}
			SwingUtilities2.drawStringUnderlineCharAt(menuItem, g, text, mnemIndex, textRect.x, textRect.y
					+ fm.getAscent());
		}
	}

}
