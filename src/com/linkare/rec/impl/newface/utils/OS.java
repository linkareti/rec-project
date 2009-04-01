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

	private static boolean macOSX;
	private static boolean windows;
	private static boolean linux;

	static {
		String os = System.getProperty("os.name").toLowerCase();
		macOSX = "mac os x".equals(os);
		windows = os != null && os.indexOf("windows") != -1;
		linux = os != null && os.indexOf("linux") != -1;
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
	
}
