package com.linkare.rec.impl.threading.util;

public enum EnumPriority {
	MAXIMUM, MINIMUM, MEDIUM;

	public static EnumPriority valueOfFromInt(final int priorityAsInt) {

		if (priorityAsInt < 5) {
			return MINIMUM;
		} else if (priorityAsInt > 5) {
			return MAXIMUM;
		} else {
			return MEDIUM;
		}
	}
}
