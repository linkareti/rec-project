package com.linkare.rec.impl.newface.utils;

import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Wrapper class for defining and reading preferences using the Preferences API.
 * 
 * @author bcatarino
 */
public class PreferencesUtils {

	private static final Logger log = Logger.getLogger(PreferencesUtils.class.getName());

	/**
	 * Returns a user preference for a given key.
	 * 
	 * @param key
	 * @return A string containing the value of the property for the given key. If there is not a user preference for
	 *         the given key, null is returned instead.
	 */
	public static String readUserPreference(String key) {
		return Preferences.userRoot().get(key, null);
	}

	/**
	 * Saves a new value to a given key as a preference.
	 * 
	 * @param key
	 *            The key name of the preference to save.
	 * @param value
	 *            The new value to set the given key.
	 */
	public static void writeUserPreference(String key, String value) {
		
		Preferences.userRoot().put(key, value);

		try {
			Preferences.userRoot().sync();
		} catch (BackingStoreException e) {
			log.severe("Error writing preference " + key 
					+ "with value " + value + ": " + e.getMessage());
		}
	}
	
	/**
	 * Removes a preference from the preference tree root.
	 * @param key The key name of the preference to remove.
	 */
	public static void removeUserPreference(String key) {
		
		Preferences.userRoot().remove(key);
		
		try {
			Preferences.userRoot().sync();
		} catch (BackingStoreException e) {
			log.severe("Error removing preference " + key 
					+ ": " + e.getMessage());
		}
	}
}
