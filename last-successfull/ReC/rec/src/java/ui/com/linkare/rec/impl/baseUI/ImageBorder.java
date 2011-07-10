/*
 * ImageBorder.java
 *
 * Created on August 18, 2004, 11:04 AM
 */

package com.linkare.rec.impl.baseUI;

/**
 *
 * @author André Neto - LEFT - IST
 *
 * Use this class to set images in the background of any container...
 */

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.border.Border;

public class ImageBorder implements Border {
	private final Image image;

	/** Creates a new instance of ImageBorder */
	public ImageBorder(final Image image, final boolean fill) {
		this.image = image;
		this.fill = fill;
	}

	@Override
	public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width,
			final int height) {
		if (image == null) {
			return;
		}
		if (!fill) {
			final int x0 = x + (width - image.getWidth(null)) / 2;
			final int y0 = y + (height - image.getHeight(null)) / 2;
			g.drawImage(image, x0, y0, null);
		} else {
			g.drawImage(image, 0, 0, width, height, null);
		}
	}

	@Override
	public Insets getBorderInsets(final Component c) {
		return new Insets(0, 0, 0, 0);
	}

	@Override
	public boolean isBorderOpaque() {
		return true;
	}

	private boolean fill = true;

	public void setFill(final boolean fill) {
		this.fill = fill;
	}
}
