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
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import javax.swing.text.View;

/**
 * 
 * @author João Florindo
 */
public class FlatTabbedPaneUI extends MetalTabbedPaneUI {

	// FIXME: Corrigir esta forma de obter as cores. Tem de ser por intermédio
	// de propriedades
	// ENABLED COLORS
	private static final Color COLOR_SELECTED_TAB_BG = new Color(0xE4EEED);
	private static final Color COLOR_SELECTED_TAB_FG = new Color(0x22363A);

	private static final Color COLOR_UNSELECTED_TAB_FG = new Color(0xE4EEED);
	private static final Color COLOR_UNSELECTED_TAB_BG = new Color(0x424A4E);
	// OTHER COLORS
	public static final Color COLOR_DISABLE_TAB_FG = new Color(0x677478);

	public static ComponentUI createUI(final JComponent c) {
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
	protected void paintTabBackground(final Graphics g, final int tabPlacement, final int tabIndex, final int x,
			final int y, final int w, final int h, final boolean isSelected) {

		if (isSelected) {
			g.setColor(FlatTabbedPaneUI.COLOR_SELECTED_TAB_BG);
		} else {
			g.setColor(FlatTabbedPaneUI.COLOR_UNSELECTED_TAB_BG);
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
	public void paint(final Graphics g, final JComponent c) {
		final int selectedIndex = tabPane.getSelectedIndex();
		final int tabPlacement = tabPane.getTabPlacement();

		ensureCurrentLayout();
		paintTabArea(g, tabPlacement, selectedIndex);
		g.setColor(FlatTabbedPaneUI.COLOR_SELECTED_TAB_BG);
		g.fillRect(0, 28, tabPane.getWidth(), tabPane.getHeight() - 29);
	}

	private void ensureCurrentLayout() {
		if (!tabPane.isValid()) {
			tabPane.validate();
		}
		/*
		 * If tabPane doesn't have a peer yet, the validate() call will silently
		 * fail. We handle that by forcing a layout if tabPane is still invalid.
		 * See bug 4237677.
		 */
		if (!tabPane.isValid()) {
			final TabbedPaneLayout layout = (TabbedPaneLayout) tabPane.getLayout();
			layout.calculateLayoutInfo();
		}
	}

	@Override
	protected void paintFocusIndicator(final Graphics g, final int tabPlacement, final Rectangle[] rects,
			final int tabIndex, final Rectangle iconRect, final Rectangle textRect, final boolean isSelected) {
		// Painting spec
	}

	@Override
	protected void paintTabBorder(final Graphics g, final int tabPlacement, final int tabIndex, final int x,
			final int y, final int w, final int h, final boolean isSelected) {
		// Painting spec
	}

	@Override
	protected void paintContentBorderLeftEdge(final Graphics g, final int tabPlacement, final int selectedIndex,
			final int x, final int y, final int w, final int h) {
		// Painting spec
	}

	@Override
	protected void paintText(final Graphics g, final int tabPlacement, final Font font, final FontMetrics metrics,
			final int tabIndex, final String title, final Rectangle textRect, final boolean isSelected) {

		g.setFont(font);

		final View v = getTextViewForTab(tabIndex);
		if (v != null) {
			// html
			v.paint(g, textRect);
		} else {

			// plain text
			final int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);
			if (tabPane.isEnabled() && isSelected) {
				g.setColor(FlatTabbedPaneUI.COLOR_SELECTED_TAB_FG);
				BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x,
						textRect.y + metrics.getAscent());

			} else if (tabPane.isEnabled() && !isSelected) {
				g.setColor(FlatTabbedPaneUI.COLOR_UNSELECTED_TAB_FG);
				BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x,
						textRect.y + metrics.getAscent());

			} else { // tab disabled
				g.setColor(FlatTabbedPaneUI.COLOR_DISABLE_TAB_FG);
				BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x,
						textRect.y + metrics.getAscent());

			}
		}
	}
}