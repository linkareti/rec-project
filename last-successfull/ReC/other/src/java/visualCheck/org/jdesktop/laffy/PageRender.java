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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.CellRendererPane;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.graphics.GraphicsUtilities;

/**
 * PageRender
 * 
 * @author Created by Jasper Potts (Apr 16, 2008)
 */
public class PageRender {
	private CellRendererPane rendererPane;

	/**
	 * Render preview images for the pages, this should be called by a thread that is not the EDT
	 * 
	 * @param pages
	 *            The list of pages to create previews for
	 * @param splashWindow
	 *            The splash window to use as a parent for the CellRendererPane and to update with progress
	 */
	public void loadPages(List<Page> pages, final SplashWindow splashWindow) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					rendererPane = new CellRendererPane();
					splashWindow.getContentPane().add(rendererPane, BorderLayout.EAST); //just stick it somewhere
				}
			});

			BufferedImage under = Laffy.loadImage("page-under.png");
			BufferedImage over = Laffy.loadImage("page-over.png");
			for (int i = 0; i < pages.size(); i++) {
				final Page page = pages.get(i);
				//the width of the live portion of the final icon
				final int finalIconWidth = 130;
				//the height of the live portion of the final icon
				final int finalIconHeight = 155;

				BufferedImage img = renderPage(page, finalIconWidth * 4, finalIconHeight * 4);
				img = scaleDown(img);
				img = scaleDown(img);

				final BufferedImage fimg = GraphicsUtilities.createCompatibleTranslucentImage(200, 245);
				Graphics2D g = fimg.createGraphics();
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g.drawImage(under, 0, 0, 200, 245, null);
				g.translate(29, 34);
				g.rotate(Math.toRadians(-5));
				g.drawImage(img, 0, 0, 130, 155, null);
				g.rotate(-Math.toRadians(-5));
				g.translate(-29, -34);
				g.drawImage(over, 0, 0, 200, 245, null);
				g.dispose();

				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						page.setPreviewIcon(new ImageIcon(fimg));
					}
				});
				// update splash progress
				splashWindow.setPercentageComplete(0.5f + ((0.5f / pages.size()) * i));
			}
			// remove renderer
			splashWindow.getContentPane().remove(rendererPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private BufferedImage renderPage(Page p, int width, int height) throws Exception {
		BufferedImage img = GraphicsUtilities.createCompatibleTranslucentImage(width, height);
		final Graphics2D g = img.createGraphics();
		final Dimension pref = p.getPreferredSize();
		final Page page = p;
		float scalex = (float) width / pref.width;
		float scaley = (float) height / pref.height;
		float scale = Math.min(scalex, scaley);
		int w, h;
		if (scalex > scaley) {
			w = (int) (width / scale);
			h = pref.height;
		} else {
			w = pref.width;
			h = (int) (width / scale);
		}
		AffineTransform tx = AffineTransform.getScaleInstance(scale, scale);
		g.setTransform(tx);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		final int width2 = w;
		final int height2 = h;

		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				rendererPane.paintComponent(g, page, null, 0, 0, width2, height2, true);
			}
		});
		g.dispose();
		return img;
	}

	private BufferedImage scaleDown(BufferedImage src) {
		//scale down by power of 2
		BufferedImage img = GraphicsUtilities.createCompatibleTranslucentImage(src.getWidth() >> 1, src.getHeight() >> 1);
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(src, 0, 0, img.getWidth(), img.getHeight(), null);
		g.dispose();
		return img;
	}

}
