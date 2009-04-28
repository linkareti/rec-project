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
		osName = System.getProperty("os.name").toLowerCase();
		macOSX = "mac os x".equals(osName);
		windows = osName != null && osName.indexOf("windows") != -1;
		linux = osName != null && osName.indexOf("linux") != -1;
	}

	/**
	 * @return the macOsX
	 */
	public static boolean isMacOSX() {
		return macOSX;
	}

	/**
	 * @return the windows
	 */
	public static boolean isWindows() {
		return windows;
	}

	/**
	 * @return the linux
	 */
	public static boolean isLinux() {
		return linux;
	}

    public static String getOSName() {
        return osName;
    }
	
}
