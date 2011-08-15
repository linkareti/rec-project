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

	private static String find(final String bundleName, final int channelIndex, final String attribute) {
		if (bundleName == null || attribute == null || attribute.isEmpty()) {
			return null;
		}
		try {
			return ReCResourceBundle.findString(bundleName.toLowerCase() + "$rec.exp." + bundleName.toLowerCase()
					+ ".hardwareinfo.channel." + channelIndex + "." + attribute);
		} catch (final MissingResourceException e) {
		}
		return null;
	}

	public static String findName(final String bundleName, final int channelIndex) {
		return ReCChannelResourceUtil.find(bundleName, channelIndex, "name");
	}

	public static String findLabel(final String bundleName, final int channelIndex) {
		return ReCChannelResourceUtil.find(bundleName, channelIndex, "label");
	}

	public static String findPhysicsUnitName(final String bundleName, final int channelIndex) {
		return ReCChannelResourceUtil.find(bundleName, channelIndex, "physicsunitname");
	}

	public static String findPhysicsUnitSymbol(final String bundleName, final int channelIndex) {
		return ReCChannelResourceUtil.find(bundleName, channelIndex, "physicsunitsymbol");
	}

	public static String findMultiplier(final String bundleName, final int channelIndex) {
		return ReCChannelResourceUtil.find(bundleName, channelIndex, "multiplier");
	}

}
