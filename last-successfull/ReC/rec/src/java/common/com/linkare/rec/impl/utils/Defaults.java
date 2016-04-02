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

	public static int defaultIfEmpty(final String value, final int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (final Exception e) {
			return defaultValue;
		}
	}

	public static long defaultIfEmpty(final String value, final long defaultValue) {
		try {
			return Long.parseLong(value);
		} catch (final Exception e) {
			return defaultValue;
		}
	}

	public static double defaultIfEmpty(final String value, final double defaultValue) {
		try {
			return Double.parseDouble(value);
		} catch (final Exception e) {
			return defaultValue;
		}
	}

	public static String defaultIfEmpty(final String value, final String defaultValue) {
		if (value != null && !value.trim().isEmpty()){
			return value;
		}

		return defaultValue;
	}

	public static javax.swing.ImageIcon defaultIfEmpty(final Object value, final javax.swing.ImageIcon defaultValue) {
		if (value instanceof javax.swing.ImageIcon) {
			return (javax.swing.ImageIcon) value;
		}

		return defaultValue;
	}

}
