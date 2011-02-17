/* 
 * ReCChannelResourceUtil.java created on 16 Feb 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.i18n;

import java.util.MissingResourceException;

/**
 * 
 * @author npadriano
 */
public class ReCChannelResourceUtil {

	/**
	 * Static <code>ReCChannelResourceUtil</code>.
	 */
	private ReCChannelResourceUtil() {
		throw new IllegalStateException("Static class can't be instatiated.");
	}

	private static String find(String bundleName, int channelIndex, String attribute) {
		if (bundleName == null || attribute == null || attribute.isEmpty()) {
			return null;
		}
		try {
			return ReCResourceBundle.findString(bundleName.toLowerCase() + "$rec.exp."
					+ bundleName.toLowerCase() + ".hardwareinfo.channel." + channelIndex + "." + attribute);
		} catch (MissingResourceException e) {
		}
		return null;
	}

	public static String findName(String bundleName, int channelIndex) {
		return find(bundleName, channelIndex, "name");
	}

	public static String findLabel(String bundleName, int channelIndex) {
		return find(bundleName, channelIndex, "label");
	}

	public static String findPhysicsUnitName(String bundleName, int channelIndex) {
		return find(bundleName, channelIndex, "physicsunitname");
	}

	public static String findPhysicsUnitSymbol(String bundleName, int channelIndex) {
		return find(bundleName, channelIndex, "physicsunitsymbol");
	}

	public static String findMultiplier(String bundleName, int channelIndex) {
		return find(bundleName, channelIndex, "multiplier");
	}

}
