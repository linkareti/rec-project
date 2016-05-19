package com.linkare.rec.web.util;

import org.apache.commons.lang.StringUtils;

public enum SystemPropertiesEnum {


	TIME_BETWEEN_MONITORING_EVENTS_SECONDS(
			"rec.web.TimeBetweenMonitoringEventsInSeconds", false) {

		@Override
		public Integer getValue() {
			return getValueAsStr() != null ? Integer.valueOf(getValueAsStr())
					: DEFAULT_WAIT_TIME;
		}
	};

	// five minutes
	private static final Integer DEFAULT_WAIT_TIME = Integer.valueOf(5 * 60);

	private final String systemPropertyName;
	private final boolean isRequired;
	private final String valueAsStr;

	private SystemPropertiesEnum(final String systemProperty,
			final boolean isRequired) {
		this.systemPropertyName = systemProperty;
		this.isRequired = isRequired;
		this.valueAsStr = StringUtils.trimToNull(System
				.getProperty(systemPropertyName));
	}

	protected final String getValueAsStr() {
		return valueAsStr;
	}

	protected final boolean isRequired() {
		return isRequired;
	}

	public abstract Object getValue();
}
