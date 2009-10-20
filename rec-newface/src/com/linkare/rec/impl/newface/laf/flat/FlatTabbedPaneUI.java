/* 
 * FlatScrollBarUI.java created on 2009/04/16
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import javax.swing.text.View;

import sun.swing.SwingUtilities2;

/**
 * 
 * @author João Florindo
 */
public class FlatTabbedPaneUI extends MetalTabbedPaneUI {

	//FIXME: Corrigir esta forma de obter as cores. Tem de ser por intermédio de propriedades
	//ENABLED COLORS
	private static final Color COLOR_SELECTED_TAB_BG = new Color(0xE4EEED);
	private static final Color COLOR_SELECTED_TAB_FG = new Color(0x22363A);

	private static final Color COLOR_UNSELECTED_TAB_FG = new Color(0xE4EEED);
	private static final Color COLOR_UNSELECTED_TAB_BG = new Color(0x424A4E);
	//OTHER COLORS
	public static final Color COLOR_DISABLE_TAB_FG = new Color(0x677478);

	public static ComponentUI createUI(JComponent c) {
		return new FlatTabbedPaneUI();
	}

	@Override
	protected LayoutManager createLayoutManager() {
		if (tabPane.getTabLayoutPolicy() == JTabbedPane.SCROLL_TAB_LAYOUT) {
			return super.createLayoutManager();
		}
		return new TabbedPaneLayout();
	}

	@Override
	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {

		if (isSelected) {
			g.setColor(COLOR_SELECTED_TAB_BG);
		} else {
			g.setColor(COLOR_UNSELECTED_TAB_BG);
		}
		switch (tabPlacement) {
		case LEFT:
			g.fillRect(x + 5, y + 1, w - 5, h - 1);
			g.fillRect(x + 2, y + 4, 3, h - 4);
			break;
		case BOTTOM:
			g.fillRect(x, y, w - 5, h - 1);
			g.fillRect(x + (w - 1) - 4, y, 4, h - 5);
			g.fillRect(x + (w - 1) - 4, y + (h - 1) - 4, 2, 2);
			break;
		case RIGHT:
			g.fillRect(x + 1, y + 1, w - 5, h - 1);
			g.fillRect(x + (w - 1) - 3, y + 5, 3, h - 5);
			break;
		case TOP:
		default:

			g.fillRect(x - 2, y - 2, w - 2, h + 4);
		}
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		int selectedIndex = tabPane.getSelectedIndex();
		int tabPlacement = tabPane.getTabPlacement();

		ensureCurrentLayout();
		paintTabArea(g, tabPlacement, selectedIndex);
		g.setColor(COLOR_SELECTED_TAB_BG);
		g.fillRect(0, 28, tabPane.getWidth(), tabPane.getHeight() - 28);
	}

	private void ensureCurrentLayout() {
		if (!tabPane.isValid()) {
			tabPane.validate();
		}
		/*
		 * If tabPane doesn't have a peer yet, the validate() call will silently fail. We handle that by forcing a
		 * layout if tabPane is still invalid. See bug 4237677.
		 */
		if (!tabPane.isValid()) {
			TabbedPaneLayout layout = (TabbedPaneLayout) tabPane.getLayout();
			layout.calculateLayoutInfo();
		}
	}

	@Override
	protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect,
			Rectangle textRect, boolean isSelected) {

	}

	@Override
	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {

	}

	@Override
	protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {

	}

	@Override
	protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect,
			boolean isSelected) {

		g.setFont(font);

		View v = getTextViewForTab(tabIndex);
		if (v != null) {
			// html
			v.paint(g, textRect);
		} else {

			// plain text
			int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);
			//FIXME Substituir estas constantes por propriedades
			if (tabPane.isEnabled() && isSelected) {
				g.setColor(COLOR_SELECTED_TAB_FG);
				SwingUtilities2.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());

			} else if (tabPane.isEnabled() && !isSelected) {
				g.setColor(COLOR_UNSELECTED_TAB_FG);
				SwingUtilities2.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());

			} else { // tab disabled
				g.setColor(COLOR_DISABLE_TAB_FG);
				SwingUtilities2.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());

			}
		}
	}
}