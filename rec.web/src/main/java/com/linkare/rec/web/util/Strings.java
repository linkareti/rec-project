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
     * @return
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
