/*
 * InternalResources.java created on Feb 26, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.laf.flat.resources;

import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Utility for LAF resources access.
 * 
 * @author Henrique Fernandes
 */
public class FlatLAFResources {

	private static final Logger log = Logger.getLogger(FlatLAFResources.class.getName());

	private static final String BUNDLE_CLASS_NAME = FlatLAFResources.class.getName();

	public enum FlatLAFImageResourcesEnum {

		SQUARE_FOCUS("squareFocus.png", new Insets(8, 8, 8, 8));

		private String name;
		private Insets insets;

		FlatLAFImageResourcesEnum(final String resourceName) {
			name = resourceName;
		}

		FlatLAFImageResourcesEnum(final String resourceName, final Insets insets) {
			name = resourceName;
			this.insets = insets;
		}

		public String getName() {
			return name;
		}

		public Insets getInsets() {
			return insets;
		}
	}

	private FlatLAFResources() {
		super();
	}

	public static BufferedImage getImage(final FlatLAFImageResourcesEnum key) {
		BufferedImage result = null;
		try {
			final URL resource = FlatLAFResources.class.getResource(key.getName());
			result = ImageIO.read(resource);
		} catch (final IOException e) {
			FlatLAFResources.log.log(Level.SEVERE, "Could not read the resource", e);
		}
		return result;
	}

	public static ImageIcon getImageIcon(final FlatLAFImageResourcesEnum key) {
		final URL resource = FlatLAFResources.class.getResource(key.getName());
		return new ImageIcon(resource);
	}
}
