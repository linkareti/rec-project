/* 
 * GlassLayer.java created on Mar 12, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

/**
 * 
 * @author Henrique Fernandes
 */
public class GlassLayer extends JComponent {

    public enum CatchEvents {
	ALL, NONE
    }

    private static final long serialVersionUID = -4011172885863014229L;

    private static final Color DEFAULT_BACKGROUND = new Color(0xE4EEED);

    protected float opacity = 0.8f;

    /**
     * Creates a new GlassPane.
     */
    public GlassLayer() {
	setBackground(DEFAULT_BACKGROUND);
    }

    /**
     * Creates a new GlassPane.
     */
    public GlassLayer(CatchEvents eventsToCatch) {
	setBackground(DEFAULT_BACKGROUND);

	if (CatchEvents.ALL == eventsToCatch) {

	    // Catch events
	    addMouseListener(new MouseAdapter() {
	    });
	    addMouseMotionListener(new MouseMotionAdapter() {
	    });
	    addKeyListener(new KeyAdapter() {
	    });

	    // Request focus
	    addComponentListener(new ComponentAdapter() {
		public void componentShown(ComponentEvent evt) {
		    requestFocusInWindow();
		}
	    });

	    // Prevent focus with Tab
	    setFocusTraversalKeysEnabled(false);
	}
    }

    @Override
    protected void paintComponent(Graphics g) {
	Graphics2D g2 = (Graphics2D) g;

	// gets the current clipping area
	Rectangle clip = g.getClipBounds();

	// sets a translucent composite with current opacity
	AlphaComposite alpha = AlphaComposite.SrcOver.derive(opacity);
	Composite composite = g2.getComposite();
	g2.setComposite(alpha);

	// background fill
	g2.setColor(getBackground());
	g2.fillRect(clip.x, clip.y, clip.width, clip.height);

	// get back to normal state
	g2.setComposite(composite);
    }

}
