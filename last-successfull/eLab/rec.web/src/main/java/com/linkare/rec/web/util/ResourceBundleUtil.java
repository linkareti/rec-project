/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.web.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Helper class to deal with getting the resource bundle for the application and its properties, according to the requested locale.
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class ResourceBundleUtil {

    private static final String RESOURCE_BUNDLE_NAME = "messages";

    public static final String getResourceBundleName() {
	return RESOURCE_BUNDLE_NAME;
    }

    /**
     * 
     * @return the resource bundle named {@link #RESOURCE_BUNDLE_NAME} for the default locale
     * @see ResourceBundle#getBundle(String))
     */
    public static final ResourceBundle getResourceBundle() {
	return ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);
    }

    /**
     * 
     * @param locale
     *            the locale to which we want the bundle to be returned
     * @return the resource bundle named {@link #RESOURCE_BUNDLE_NAME} for the locale passed in as argument
     * @see ResourceBundle#getBundle(String, Locale))
     */
    public static final ResourceBundle getResourceBundle(final Locale locale) {
	return ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale);
    }

    /**
     * 
     * @param property
     *            the property to look for in the resource bundle
     * @return the value that matches the property passed in, translated to the default locale
     * @see ResourceBundleUtil#getValue(Locale, String)
     */
    public static final String getValue(final String property) {
	return getResourceBundle().getString(property);
    }

    /**
     * 
     * @param locale
     *            the locale to which we want the property to be returned
     * @param property
     *            the property to look for in the resource bundle
     * @return the value that matches the property passed in, translated to the locale passed in
     * @see ResourceBundle#getString(String)
     */
    public static final String getValue(final Locale locale, final String property) {
	return getResourceBundle(locale).getString(property);
    }
}
