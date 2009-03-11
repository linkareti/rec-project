/* 
 * InternalResources.java created on Feb 26, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.laf.flat.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Utility for LAF resources access.
 * 
 * @author Henrique Fernandes
 */
public class FlatLAFResources {
    
    public enum FlatLAFResourcesEnum {
	FLATMENUBAR_BACKGROUD_IMG("headerBkg.jpg");
	
	private String name;

	FlatLAFResourcesEnum(String resourceName) {
	    this.name = resourceName;
	}

	public String getName() {
	    return name;
	}
    }

    private static final String BUNDLE_CLASS_NAME = FlatLAFResources.class.getName();

    private FlatLAFResources() {
    }

    public static BufferedImage getImage(String key) {
	try {
	    URL resource = FlatLAFResources.class.getResource(key);
	    if (resource == null) {
		throw new MissingResourceException("Image resource not found: " + key, BUNDLE_CLASS_NAME, key);
	    }
	    return ImageIO.read(resource);
	} catch (IOException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public static ImageIcon getImageIcon(String key) {
	URL resource = FlatLAFResources.class.getResource(key);
	if (resource == null) {
	    throw new MissingResourceException("Image resource not found: " + key, BUNDLE_CLASS_NAME, key);
	}
	return new ImageIcon(resource);
    }

}
