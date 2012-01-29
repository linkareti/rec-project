/*
 * ReCResourceBundle.java
 *
 * Created on 21 de Janeiro de 2004, 3:16
 */

package com.linkare.rec.impl.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.protocols.ReCProtocols;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class ReCResourceBundle extends ResourceBundle {

	public static String REC_RESBUNDLE_LOGGER = "ReCResourceBundle.Logger";

	static {
		final Logger l = LogManager.getLogManager().getLogger(ReCResourceBundle.REC_RESBUNDLE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(ReCResourceBundle.REC_RESBUNDLE_LOGGER));
		}
	}

	// private static void log(Level debugLevel, String message) {
	// Logger.getLogger(REC_RESBUNDLE_LOGGER).log(debugLevel,
	// "ReCResourceBundle - " + message);
	// }

	private static void logThrowable(final String message, final Throwable t) {
		LoggerUtil.logThrowable("ReCResourceBundle - " + message, t,
				Logger.getLogger(ReCResourceBundle.REC_RESBUNDLE_LOGGER));
	}

	private static Map<String, ReCResourceBundle> bundles = new HashMap<String, ReCResourceBundle>();

	public static String findString(final String bundleName, final String key) throws MissingResourceException {

		final List<String> bundleNames = ReCResourceBundle.calculateLanguageVariants(bundleName,
				Locale.getDefault());

		ResourceBundle bundle = null;

		for (int i = 0; i < bundleNames.size() && bundle == null; i++) {
			bundle = ReCResourceBundle.bundles.get(bundleNames.get(i));
		}

		if (bundle == null) {
			throw new MissingResourceException("No bundle here by the name " + bundleName,
					ReCResourceBundle.class.getName(), key);
		}

		return bundle.getString(key);
	}

	public static ImageIcon findImageIcon(final String bundleName, final String key) throws MissingResourceException,
			MalformedURLException {
		final String location = ReCResourceBundle.findString(bundleName, key);
		final URL url = ReCProtocols.getURL(location);
		final ImageIcon iconImage = new ImageIcon(url);
		return iconImage;
	}

	public static Object findObject(final String bundleName, final String key) throws MissingResourceException,
			IOException, ClassNotFoundException {
		final String location = ReCResourceBundle.findString(bundleName, key);
		final Object o = java.beans.Beans.instantiate(null, location);
		return o;
	}

	public static String findStringOrDefault(final String bundleName, final String key, final String defaultValue)
			throws MissingResourceException {
		try {
			return ReCResourceBundle.findString(bundleName, key);
		} catch (final Exception e) {
			ReCResourceBundle.logThrowable("Couldn't find key '" + key + "' on bundle " + bundleName, e);
			return defaultValue;
		}
	}

	public static ImageIcon findImageIconOrDefault(final String bundleName, final String key,
			final ImageIcon defaultValue) throws MissingResourceException {
		try {
			return ReCResourceBundle.findImageIcon(bundleName, key);
		} catch (final Exception e) {
			ReCResourceBundle.logThrowable("Couldn't find key '" + key + "' on bundle " + bundleName, e);
			return defaultValue;
		}
	}

	public static Object findObjectOrDefault(final String bundleName, final String key, final Object defaultValue)
			throws MissingResourceException {
		try {
			return ReCResourceBundle.findObject(bundleName, key);
		} catch (final Exception e) {
			ReCResourceBundle.logThrowable("Couldn't find key '" + key + "' on bundle " + bundleName, e);
			return defaultValue;
		}
	}

	public static ImageIcon findImageIcon(final String key) throws MissingResourceException, MalformedURLException {
		final String location = ReCResourceBundle.findString(key);
		final URL url = ReCProtocols.getURL(location);
		final ImageIcon iconImage = new ImageIcon(url);
		return iconImage;
	}

	public static Object findObject(final String key) throws MissingResourceException, IOException,
			ClassNotFoundException {
		final String location = ReCResourceBundle.findString(key);
		final Object o = java.beans.Beans.instantiate(null, location);
		return o;
	}

	public static String findString(final String key) throws MissingResourceException {

		if (key != null && key.indexOf('$') != -1) {
			final int loc = key.indexOf('$');
			final String bundleName = key.substring(0, loc);
			final String bundleKey = key.substring(loc + 1);
			return ReCResourceBundle.findString(bundleName, bundleKey);
		}

		for (final ReCResourceBundle bundle : ReCResourceBundle.bundles.values()) {
			try {
				if (bundle.containsKey(key)) {
					return bundle.getString(key);
				}
			} catch (final Exception e) {
			}
		}

		throw new MissingResourceException("Key '" + key + "' not found in any bundle here",
				ReCResourceBundle.class.getName(), key);
	}

	public static String findStringOrDefault(final String key, final String defaultValue)
			throws MissingResourceException {
		try {
			return ReCResourceBundle.findString(key);
		} catch (final Exception e) {
			ReCResourceBundle.logThrowable("Couldn't find key '" + key + "' on any bundle!", e);
			return defaultValue;
		}
	}

	public static ImageIcon findImageIconOrDefault(final String key, final ImageIcon defaultValue)
			throws MissingResourceException {
		try {
			return ReCResourceBundle.findImageIcon(key);
		} catch (final Exception e) {
			ReCResourceBundle.logThrowable("Couldn't find key '" + key + "' on any bundle!", e);
			return defaultValue;
		}
	}

	public static Object findObjectOrDefault(final String key, final Object defaultValue)
			throws MissingResourceException {
		try {
			return ReCResourceBundle.findObject(key);
		} catch (final Exception e) {
			ReCResourceBundle.logThrowable("Couldn't find key '" + key + "' on any bundle!", e);
			return defaultValue;
		}
	}

	public static ReCResourceBundle loadResourceBundle(final String bundleName, final String bundleLocation) {
		return ReCResourceBundle.loadResourceBundle(bundleName, bundleLocation, Locale.getDefault());
	}

	public static ReCResourceBundle loadResourceBundle(final String bundleName, final String bundleLocation,
			final Locale locale) {
		// first try to locate the bundle in the cache
		final ArrayList<String> bundleKeys = ReCResourceBundle.calculateLanguageVariants(bundleName, locale);
		final String bundleNameKey = bundleKeys.get(0);
		if (ReCResourceBundle.bundles.containsKey(bundleNameKey)) {
			return ReCResourceBundle.bundles.get(bundleNameKey);
		}

		// next calculate the languageVariants
		final ArrayList<String> bundleLocations = ReCResourceBundle.calculateLanguageVariants(bundleLocation, locale);

		for (int i = 0; i < bundleLocations.size(); i++) {
			final String bundleLocationCurrent = bundleLocations.get(i);
			ReCResourceBundle bundle = ReCResourceBundle.loadFromClassName(bundleLocationCurrent);
			if (bundle != null) {
				ReCResourceBundle.bundles.put(bundleKeys.get(i), bundle);
				ReCResourceBundle.propagateBundle(bundle, bundleLocationCurrent, bundleKeys.get(i));
				return bundle;
			} else {
				bundle = ReCResourceBundle.loadFromURL(bundleLocationCurrent);
				if (bundle != null) {
					ReCResourceBundle.bundles.put(bundleKeys.get(i), bundle);
					ReCResourceBundle.propagateBundle(bundle, bundleLocationCurrent, bundleKeys.get(i));
					return bundle;
				}
			}
		}
		return null;
	}

	private static void propagateBundle(final ReCResourceBundle bundle, final String bundleLocation,
			final String bundleName) {
		ReCResourceBundle childBundle = bundle;
		String temp = bundleLocation;
		final int fileExtLoc = temp.lastIndexOf(".properties");
		if (fileExtLoc != -1) {
			temp = temp.substring(0, fileExtLoc);
		}

		int loc = temp.lastIndexOf("_");

		String bundleNameTemp = bundleName;

		while (loc != -1) {
			temp = temp.substring(0, loc);
			bundleNameTemp = bundleNameTemp.substring(0, bundleNameTemp.lastIndexOf("_"));
			ReCResourceBundle bundleParent = ReCResourceBundle.loadFromClassName(temp);
			if (bundleParent != null) {
				ReCResourceBundle.bundles.put(bundleNameTemp, bundleParent);
				childBundle.parent = bundleParent;
				childBundle = bundleParent;
			} else {
				bundleParent = ReCResourceBundle.loadFromURL(temp);
				if (bundleParent != null) {
					ReCResourceBundle.bundles.put(bundleNameTemp, bundleParent);
					childBundle.parent = bundleParent;
					childBundle = bundleParent;
				}
			}

			loc = temp.lastIndexOf("_");
		}
	}

	private static ReCResourceBundle loadFromClassName(final String className) {
		try {
			final Object oBundle = ClassLoader.getSystemClassLoader().loadClass(className).newInstance();
			if (oBundle instanceof ReCResourceBundle) {
				return (ReCResourceBundle) oBundle;
			}
		} catch (final Exception e) {

		}

		return null;
	}

	private static ReCResourceBundle loadFromURL(String strUrl) {
		try {
			if (strUrl.indexOf(".properties") == -1) {
				strUrl += ".properties";
			}

			final URL url = ReCProtocols.getURL(strUrl);
			if (url != null) {
				final URLConnection con = url.openConnection();
				con.setDoInput(true);
				con.setDoOutput(false);
				con.connect();

				final InputStream is = con.getInputStream();
				final ReCPropertyResourceBundle bundle = new ReCPropertyResourceBundle(is);
				return bundle;
			}

		} catch (final Exception e) {
		}

		return null;
	}

	private static ArrayList<String> calculateLanguageVariants(final String base, final Locale locale) {
		final String language1 = locale.getLanguage();
		final String country1 = locale.getCountry();
		final String variant1 = locale.getVariant();

		final Locale defLocale = Locale.getDefault();
		final String language2 = defLocale.getLanguage();
		final String country2 = defLocale.getCountry();
		final String variant2 = defLocale.getVariant();

		final ArrayList<String> retVal = new ArrayList<String>(7);

		String temp = "";
		if (language1 != null && !language1.equals("")) {
			temp += "_" + language1;
			retVal.add(base + temp);
			if (country1 != null && !country1.equals("")) {
				temp += "_" + country1;
				retVal.add(0, base + temp);

				if (variant1 != null && !variant1.equals("")) {
					temp += "_" + variant1;
					retVal.add(0, base + temp);
				}
			}
		}

		if (!(language1 != null && language2 != null && language1.equals(language2) && country1 != null
				&& country2 != null && country1.equals(country2) && variant1 != null && variant2 != null && variant1
				.equals(variant2))) {

			temp = "";
			final int locationInsert = retVal.size();
			if (language2 != null && !language2.equals("")) {
				temp += "_" + language2;

				retVal.add(base + temp);
				if (country2 != null && !country2.equals("")) {
					temp += "_" + country2;
					retVal.add(locationInsert, base + temp);

					if (variant2 != null && !variant2.equals("")) {
						temp += "_" + variant2;
						retVal.add(locationInsert, base + temp);
					}
				}
			}
		}
		retVal.add(base);

		return retVal;
	}

}
