package com.linkare.rec.impl.newface.utils;

public class TimeUtils {

	public static long getSystemCurrentTimeMs() {
		return System.nanoTime() / 1000000;
	}

	public static long msToSeconds(long milliseconds) {
		return milliseconds / 1000;
	}
}
