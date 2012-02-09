/* 
 * OS.java created on Apr 1, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.utils;

/**
 * @author hfernandes
 */
public class OS {

	private static String osName;
	private static boolean macOSX;
	private static boolean windows;
	private static boolean linux;

	static {
		OS.osName = System.getProperty("os.name").toLowerCase();
		OS.macOSX = "mac os x".equals(OS.osName);
		OS.windows = OS.osName != null && OS.osName.indexOf("windows") != -1;
		OS.linux = OS.osName != null && OS.osName.indexOf("linux") != -1;
	}

	/**
	 * @return the macOsX
	 */
	public static boolean isMacOSX() {
		return OS.macOSX;
	}

	/**
	 * @return the windows
	 */
	public static boolean isWindows() {
		return OS.windows;
	}

	/**
	 * @return the linux
	 */
	public static boolean isLinux() {
		return OS.linux;
	}

	public static String getOSName() {
		return OS.osName;
	}

}
