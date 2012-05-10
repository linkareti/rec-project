package com.linkare.rec.impl.threading.util;

public enum EnumPriority {
	MAXIMUM, MINIMUM, MEDIUM;

	private static EnumPriority[] orderedPriorities = new EnumPriority[] { MAXIMUM, MEDIUM, MINIMUM };

	/**
	 * @return
	 */
	public static EnumPriority[] valuesOrderedByPriority() {
		return orderedPriorities;
	}
}
