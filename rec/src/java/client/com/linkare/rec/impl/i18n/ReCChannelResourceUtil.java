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
	
	private static String find(String experimentFamiliarName, int channelIndex, String attribute) {
		try {
			return ReCResourceBundle.findString(experimentFamiliarName + "$rec.exp." + experimentFamiliarName
					+ ".hardwareinfo.channel." + channelIndex + "." + attribute);
		} catch (MissingResourceException e) {
		}
		return null;
	}

	public static String findName(String experimentFamiliarName, int channelIndex) {
		return find(experimentFamiliarName, channelIndex, "name");
	}

	public static String findLabel(String experimentFamiliarName, int channelIndex) {
		return find(experimentFamiliarName, channelIndex, "label");
	}

	public static String findPhysicsUnitName(String experimentFamiliarName, int channelIndex) {
		return find(experimentFamiliarName, channelIndex, "physicsunitname");
	}

	public static String findPhysicsUnitSymbol(String experimentFamiliarName, int channelIndex) {
		return find(experimentFamiliarName, channelIndex, "physicsunitsymbol");
	}

	public static String findMultiplier(String experimentFamiliarName, int channelIndex) {
		return find(experimentFamiliarName, channelIndex, "multiplier");
	}

}
