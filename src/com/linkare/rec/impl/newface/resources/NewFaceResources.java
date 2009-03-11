/* 
 * InternalResources.java created on Feb 26, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Utility for ReC New Face resources access.
 * 
 * @author Henrique Fernandes
 */
public class NewFaceResources {

    public enum NewFaceResourcesEnum {
	LOGIN_IMG("on.gif");
	
	private String name;

	NewFaceResourcesEnum(String resourceName) {
	    this.name = resourceName;
	}

	public String getName() {
	    return name;
	}
    }

    private static final String BUNDLE_CLASS_NAME = NewFaceResources.class.getName();

    private NewFaceResources() {
    }

    public static BufferedImage getImage(String key) {
	try {
	    URL resource = NewFaceResources.class.getResource(key);
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
	URL resource = NewFaceResources.class.getResource(key);
	if (resource == null) {
	    throw new MissingResourceException("Image resource not found: " + key, BUNDLE_CLASS_NAME, key);
	}
	return new ImageIcon(resource);
    }

}
