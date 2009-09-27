/*
 * BooleanXMLParser.java
 *
 * Created on 27 de Janeiro de 2004, 2:24
 */

package com.linkare.rec.impl.baseUI.config;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class BooleanXMLParser {

	public static boolean parseBoolean(String value) {
		if (value == null)
			return false;

		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1") || value.equalsIgnoreCase("on")
				|| value.equalsIgnoreCase("yes"))
			return true;
		else
			return false;
	}

	public static String toString(boolean value) {
		return value ? "true" : "false";
	}
}
