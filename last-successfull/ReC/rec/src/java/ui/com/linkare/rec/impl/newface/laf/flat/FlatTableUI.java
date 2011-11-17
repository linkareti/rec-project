/* 
 * FlatButtonUI.java created on 2009/04/29
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.ViewportLayout;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * 
 * @author João Florindo
 */
public class FlatTableUI extends BasicTableUI {

	public static final Color SELECTION_ROW_COLOR = new Color(0xF4F3A3);
	public static final Color EVEN_ROW_COLOR = new Color(0xF6F9F8);
	public static final Dimension INTERCELL_SPACING = new Dimension(0, 4);
	private static final int INTERCELL_SPACING_HALF_HEIGHT = (int) (FlatTableUI.INTERCELL_SPACING.getHeight() / 2);

	private final PropertyChangeListener fAncestorPropertyChangeListener = createAncestorPropertyChangeListener();

	public static ComponentUI createUI(final JComponent c) {
		return new FlatTableUI();
	}

	@Override
	public void installUI(final JComponent c) {
		super.installUI(c);

		// TODO save defaults.
		table.setFocusable(false);
		table.setSelectionBackground(FlatTableUI.SELECTION_ROW_COLOR);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setShowGrid(false);
		table.setIntercellSpacing(FlatTableUI.INTERCELL_SPACING);
		table.setOpaque(false);

		table.addPropertyChangeListener("ancestor", fAncestorPropertyChangeListener);
	}

	private PropertyChangeListener createAncestorPropertyChangeListener() {
		return new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				// indicate that the parent of the JTable has changed.
				parentDidChange();
			}
		};
	}

	private void parentDidChange() {
		// if the parent of the table is an instance of JViewport, and that
		// JViewport's parent is
		// a JScrollPane, then install the custom BugFixedViewportLayout.
		if (table.getParent() instanceof JViewport && table.getParent().getParent() instanceof JScrollPane) {
			final JViewport viewPort = (JViewport) table.getParent();
			viewPort.setOpaque(false);
			final JScrollPane scrollPane = (JScrollPane) viewPort.getParent();
			scrollPane.getViewport().setLayout(new BugFixedViewportLayout());
			LookAndFeel.installProperty(scrollPane, "opaque", Boolean.FALSE);
		}

	}

	@Override
	public void paint(final Graphics g, final JComponent c) {
		// get the row index at the top of the clip bounds (the first row to
		// paint).
		final int rowAtPoint = table.rowAtPoint(g.getClipBounds().getLocation());
		// get the y coordinate of the first row to paint. if there are no rows
		// in the table, start
		// painting at the top of the supplied clipping bounds.
		int topY = rowAtPoint < 0 ? g.getClipBounds().y : table.getCellRect(rowAtPoint, 0, true).y;

		// create a counter variable to hold the current row. if there are no
		// rows in the table,
		// start the counter at 0.
		int currentRow = rowAtPoint < 0 ? 0 : rowAtPoint;
		final int rowHeight = table.getRowHeight();
		while (topY < g.getClipBounds().y + g.getClipBounds().height) {

			g.setColor(getRowColor(currentRow));
			g.fillRect(g.getClipBounds().x, topY + FlatTableUI.INTERCELL_SPACING_HALF_HEIGHT, g.getClipBounds().width,
					rowHeight - FlatTableUI.INTERCELL_SPACING.height);

			topY += rowHeight;
			currentRow++;
		}

		final TableColumnModel colmodel = table.getColumnModel();

		if (colmodel.getColumnCount() == 3) {

			// Set column widths
			colmodel.getColumn(0).setPreferredWidth(125);
			colmodel.getColumn(1).setPreferredWidth(20);
			colmodel.getColumn(2).setPreferredWidth(20);

			final TableColumn namecol1 = colmodel.getColumn(0);
			final TableColumn namecol2 = colmodel.getColumn(1);
			final TableColumn namecol3 = colmodel.getColumn(2);

			final FlatTableCellRenderer renderer = new FlatTableCellRenderer();
			renderer.setHorizontalAlignment(SwingConstants.CENTER);
			namecol1.setCellRenderer(renderer);
			namecol2.setCellRenderer(renderer);
			namecol3.setCellRenderer(renderer);
		}

		super.paint(g, c);
	}

	private Color getRowColor(final int row) {
		return row % 2 == 0 ? FlatTableUI.EVEN_ROW_COLOR : table.getBackground();
	}

	// BugFixedViewportLayout implementation.
	// /////////////////////////////////////////////////////

	/**
	 * A modified ViewportLayout to fix the JFC bug where components that
	 * implement Scrollable do not resize correctly, if their size is less than
	 * the viewport size. This is a JDK1.2.2 bug (id 4310721). This used to work
	 * in Swing 1.0.3 and the fix is putting the old logic back. Copied from:
	 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4310721
	 */
	private class BugFixedViewportLayout extends ViewportLayout {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1324811680463903647L;

		@Override
		public void layoutContainer(final Container parent) {
			// note that the original code (at the link supplied in the comment
			// above) contained the
			// following call to super.layoutContainer(parent). this call caused
			// the table to
			// continuously paint itself when the table did not fill the view.
			// thus, i've commented
			// it out for now, as doing so seems to have no ill effects.
			// super.layoutContainer(parent);

			final JViewport vp = (JViewport) parent;
			final Component view = vp.getView();

			if (view == null) {
				return;
			}

			final Point viewPosition = vp.getViewPosition();
			final Dimension viewPrefSize = view.getPreferredSize();
			final Dimension vpSize = vp.getSize();
			final Dimension viewSize = new Dimension(viewPrefSize);

			if ((viewPosition.x == 0) && (vpSize.width > viewPrefSize.width)) {
				viewSize.width = vpSize.width;
			}

			if ((viewPosition.y == 0) && (vpSize.height > viewPrefSize.height)) {
				viewSize.height = vpSize.height;
			}

			if (!viewSize.equals(viewPrefSize)) {
				vp.setViewSize(viewSize);
			}
		}
	}

}
