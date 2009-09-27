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
	private Image image;

	/** Creates a new instance of ImageBorder */
	public ImageBorder(Image image, boolean fill) {
		this.image = image;
		this.fill = fill;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		if (image == null) {
			Color color = c.getBackground();
			g.setColor(color);
			g.fillRect(0, 0, width, height);
		}
		if (!fill) {
			int x0 = x + (width - image.getWidth(null)) / 2;
			int y0 = y + (height - image.getHeight(null)) / 2;
			g.drawImage(image, x0, y0, null);
		} else {
			g.drawImage(image, 0, 0, width, height, null);
		}
	}

	public Insets getBorderInsets(Component c) {
		return new Insets(0, 0, 0, 0);
	}

	public boolean isBorderOpaque() {
		return true;
	}

	private boolean fill = true;

	public void setFill(boolean fill) {
		this.fill = fill;
	}
}