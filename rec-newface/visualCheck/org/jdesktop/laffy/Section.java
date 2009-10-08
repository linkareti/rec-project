/*
 * Laffy - Swing Look and Feel Sampler
 * Copyright (C) Sun Microsystems
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 */
package org.jdesktop.laffy;

import org.jdesktop.swingx.JXHyperlink;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Section
 * 
 * @author Created by Jasper Potts (Aug 23, 2007)
 * @version 1.0
 */
public class Section extends JPanel {
    private Action floatAction = createFloatAction();
    private JXHyperlink hyperlink = new JXHyperlink(floatAction) {
	@Override
	protected void paintComponent(Graphics g) {
	    if (g instanceof Graphics2D) {
		Graphics2D g2 = (Graphics2D) g.create(0, 0, getWidth(), getHeight());
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		super.paintComponent(g2);
	    } else {
		super.paintComponent(g);
	    }
	}
    };
    protected ContentPanel contentPanel = new ContentPanel();
    protected Class<? extends JPanel> demoPanelClass;

    protected Section(String name) {
	super(new BorderLayout(0, 10));
	setBackground(Color.WHITE);
	setName(name);
	hyperlink.setFont(new JLabel().getFont().deriveFont(Font.BOLD, 16f));
	hyperlink.setForeground(Color.DARK_GRAY);
	add(hyperlink, BorderLayout.NORTH);
	add(contentPanel, BorderLayout.CENTER);
    }

    public Section(String name, Class<? extends JPanel> demoPanelClass) {
	this(name);
	this.demoPanelClass = demoPanelClass;
	try {
	    contentPanel.setContent(demoPanelClass.newInstance());
	} catch (Exception e) {
	    // System.err.println("Error createing instance of demo panel");
	    System.err.println(I18nResourceHandler.getMessage("Error_createing_instance_of_demo_panel"));
	    e.printStackTrace();
	}
    }

    protected Action createFloatAction() {
	return new FloatAction();
    }

    // =================================================================================================================
    // Content Panel

    protected class ContentPanel extends JPanel {
	private static final float FACTOR = 0.85f;
	private GridBagLayout childLayout;
	private Color normalBackground;
	private Color background;

	/** Creates a new <code>JPanel</code> with a double buffer and a flow layout. */
	public ContentPanel() {
	    super(new BorderLayout());
	    Laffy.getInstance().addPropertyChangeListener(new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
		    if ("forceCyanPanels".equals(evt.getPropertyName())) {
			background = Color.cyan;
		    } else {
			background = normalBackground;
		    }
		}
	    });
	}

	public void setContent(final JComponent content) {
	    content.setOpaque(false);
	    add(content, BorderLayout.WEST);
	    if (content.getLayout() instanceof GridBagLayout) {
		childLayout = (GridBagLayout) content.getLayout();
	    }
	    normalBackground = background = content.getBackground();
	    content.addPropertyChangeListener("background", new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
		    background = content.getBackground();
		}
	    });
	}

	@Override
	protected void paintComponent(Graphics g) {
	    g.setColor(Color.WHITE);
	    g.fillRect(0, 0, getWidth(), getHeight());
	    if (childLayout != null) {
		int[][] sizes = childLayout.getLayoutDimensions();
		int[] widths = sizes[0];
		int[] heights = sizes[1];
		int x = 0;
		if (getComponentOrientation().isLeftToRight()) {
		    for (int i = 0; i < widths.length; i++) {
			int width = widths[i];
			int y = 0;
			for (int j = 0; j < heights.length; j++) {
			    int height = heights[j];
			    if (i == 0 || j == 0) {
				g.setColor(new Color(Math.max((int) (background.getRed() * FACTOR), 0), Math.max(
					(int) (background.getGreen() * FACTOR), 0), Math.max((int) (background
					.getBlue() * FACTOR), 0)));
			    } else {
				g.setColor(background);
			    }
			    g.fillRect(x + 1, y + 1, width - 2, height - 2);
			    y += height;
			}
			x += width;
		    }
		} else {
		    for (int i = widths.length - 1; i >= 0; i--) {
			int width = widths[i];
			int y = 0;
			for (int j = 0; j < heights.length; j++) {
			    int height = heights[j];
			    if (i == 0 || j == 0) {
				g.setColor(new Color(Math.max((int) (background.getRed() * FACTOR), 0), Math.max(
					(int) (background.getGreen() * FACTOR), 0), Math.max((int) (background
					.getBlue() * FACTOR), 0)));
			    } else {
				g.setColor(background);
			    }
			    g.fillRect(x + 1, y + 1, width - 2, height - 2);
			    y += height;
			}
			x += width;
		    }
		}
		// filler column
		int y = 0;
		for (int j = 0; j < heights.length; j++) {
		    int height = heights[j];
		    if (j == 0) {
			g.setColor(new Color(Math.max((int) (background.getRed() * FACTOR), 0), Math.max(
				(int) (background.getGreen() * FACTOR), 0), Math.max(
				(int) (background.getBlue() * FACTOR), 0)));
		    } else {
			g.setColor(background);
		    }
		    g.fillRect(x + 1, y + 1, getWidth() - x - 2, height - 2);
		    y += height;
		}
	    }
	}
    }

    // =================================================================================================================
    // Float Action

    private class FloatAction extends AbstractAction {
	/** Creates an {@code Action}. */
	public FloatAction() {
	    super(getName());
	    Section.this.addPropertyChangeListener("name", new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
		    putValue(Action.NAME, getName());
		}
	    });
	}

	/** Invoked when an action occurs. */
	public void actionPerformed(ActionEvent event) {
	    // JFrame frame = new JFrame(getName() + ": Resize Test Window");
	    JFrame frame = new JFrame(getName() + I18nResourceHandler.getMessage("Resize_Test_Window"));
	    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	    try {
		frame.getContentPane().add(demoPanelClass.newInstance(), BorderLayout.CENTER);
	    } catch (Exception e) {
		// System.err.println("Error createing instance of demo panel");
		System.err.println(I18nResourceHandler.getMessage("Error_createing_instance_of_demo_panel"));
		e.printStackTrace();
	    }
	    frame.pack();
	    frame.setLocationRelativeTo(Laffy.getInstance().getFrame());
	    frame.setVisible(true);
	}
    }

}
