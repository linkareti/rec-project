package com.linkare.rec.impl.newface.utils;

public class TimeUtils {

	public static long getSystemCurrentTimeMs() {
		// return System.nanoTime() / 1000000;
		return System.currentTimeMillis();
	}

	public static long msToSeconds(final long milliseconds) {
		return milliseconds / 1000;
	}
}
