package com.linkare.rec.web.util;

/**
 * Helper class for manipulating Strings
 */
public final class Strings {

    private Strings() {

    }

    /**
     * Checks if String  is null or empty
     *
     * @param string String
     * @return true if the String is null or empty
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
