/*
 * Defaults.java
 *
 * Created on 13 May 2003, 09:15
 */

package com.linkare.rec.impl.utils;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class Defaults {

	private Defaults() {
	}

	public static int defaultIfEmpty(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static long defaultIfEmpty(String value, long defaultValue) {
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static double defaultIfEmpty(String value, double defaultValue) {
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static String defaultIfEmpty(String value, String defaultValue) {
		if (value != null && !value.trim().equals(""))
			return value;

		return defaultValue;
	}

	public static javax.swing.ImageIcon defaultIfEmpty(Object value,
			javax.swing.ImageIcon defaultValue) {
		if (value != null && value instanceof javax.swing.ImageIcon)
			return (javax.swing.ImageIcon) value;

		return defaultValue;
	}

}
