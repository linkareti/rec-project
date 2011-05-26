/* 
 * FlatUtils.java created on 2009/04/30
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

/**
 * 
 * @author João FLorindo
 */
public class FlatUtils {

	static void drawGradient(final JComponent c, final Graphics g, final int x, final int y, final int w, final int h,
			final float[] fractions, final Color[] colors) {
		LinearGradientPaint paint = null;

		c.setOpaque(true);
		final Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		final Composite old = g2.getComposite();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));

		// VERTIVAL GRADIENT
		paint = new LinearGradientPaint(x, y, w, h, fractions, colors);
		g2.setPaint(paint);
		final Shape shape = new Rectangle2D.Float(0, 0, c.getWidth(), c.getHeight());
		g2.fill(shape);
		g2.setComposite(old);
		g2.dispose();
	}

}
