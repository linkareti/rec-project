/*
 * ImageBorder.java
 *
 * Created on September 2, 2005, 6:41 PM
 */

package pt.utl.ist.elab.client.thomson.displays;

/**
 *
 * @author André Neto - LEFT - IST
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.border.Border;

public class ImageBorder implements Border {
	private final Image image;

	/** Creates a new instance of ImageBorder 
	 * @param image 
	 * @param fill */
	public ImageBorder(final Image image, final boolean fill) {
		this.image = image;
		this.fill = fill;
	}

	@Override
	public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width,
			final int height) {
		if (image == null) {
			final Color color = c.getBackground();
			g.setColor(color);
			g.fillRect(0, 0, width, height);
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