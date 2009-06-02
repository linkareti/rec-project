/* 
 * FlatButtonUI.java created on 2009/04/29
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.ViewportLayout;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.plaf.metal.MetalButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import sun.swing.SwingUtilities2;

import com.linkare.rec.impl.newface.component.FlatButton;
import com.linkare.rec.impl.newface.laf.flat.elabtheme.ScrollPaneStyle;
import com.sun.xml.internal.bind.v2.model.impl.ModelBuilder;

/**
 * 
 * @author João Florindo
 */
public class FlatTableUI extends BasicTableUI {

	private static final Color SELECTION_ROW_COLOR = new Color(0xF4F3A3);
	private static final Color EVEN_ROW_COLOR = new Color(0xF6F9F8);
	private PropertyChangeListener fAncestorPropertyChangeListener = createAncestorPropertyChangeListener();

	public static ComponentUI createUI(JComponent c) {
		return new FlatTableUI();
	}

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);

		// TODO save defaults.
		table.setFocusable(false);
		table.setSelectionBackground(SELECTION_ROW_COLOR);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setShowGrid(false);
		table.setIntercellSpacing(new Dimension(0, 0));
		
		table.addPropertyChangeListener("ancestor",	fAncestorPropertyChangeListener);
		
	}

	private PropertyChangeListener createAncestorPropertyChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				// indicate that the parent of the JTable has changed.
				parentDidChange();
			}
		};
	}

	private void parentDidChange() {
		// if the parent of the table is an instance of JViewport, and that
		// JViewport's parent is
		// a JScrollPane, then install the custom BugFixedViewportLayout.
		if (table.getParent() instanceof JViewport	&& table.getParent().getParent() instanceof JScrollPane) {
			JScrollPane scrollPane = (JScrollPane) table.getParent().getParent();
			scrollPane.getViewport().setLayout(new BugFixedViewportLayout());
		}
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		// get the row index at the top of the clip bounds (the first row to
		// paint).
		int rowAtPoint = table.rowAtPoint(g.getClipBounds().getLocation());
		// get the y coordinate of the first row to paint. if there are no rows
		// in the table, start
		// painting at the top of the supplied clipping bounds.
		int topY = rowAtPoint < 0 ? g.getClipBounds().y : table.getCellRect(
				rowAtPoint, 0, true).y;

		// create a counter variable to hold the current row. if there are no
		// rows in the table,
		// start the counter at 0.
		int currentRow = rowAtPoint < 0 ? 0 : rowAtPoint;
		while (topY < g.getClipBounds().y + g.getClipBounds().height) {
			int bottomY = topY + table.getRowHeight();
			g.setColor(getRowColor(currentRow));
			g.fillRect(g.getClipBounds().x, topY, g.getClipBounds().width,	bottomY);
			topY = bottomY;
			currentRow++;
		}

		super.paint(g, c);
	}

	private Color getRowColor(int row) {
		return row % 2 == 0 ? EVEN_ROW_COLOR : table.getBackground();
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
		public void layoutContainer(Container parent) {
			// note that the original code (at the link supplied in the comment
			// above) contained the
			// following call to super.layoutContainer(parent). this call caused
			// the table to
			// continuously paint itself when the table did not fill the view.
			// thus, i've commented
			// it out for now, as doing so seems to have no ill effects.
			// super.layoutContainer(parent);

			JViewport vp = (JViewport) parent;
			Component view = vp.getView();

			if (view == null) {
				return;
			}

			Point viewPosition = vp.getViewPosition();
			Dimension viewPrefSize = view.getPreferredSize();
			Dimension vpSize = vp.getSize();
			Dimension viewSize = new Dimension(viewPrefSize);

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
