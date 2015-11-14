package com.linkare.rec.impl.exceptions;

import com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException;

public class NotAnAvailableSamplesPacketExceptionConstants {

	private static final java.util.ResourceBundle notAnAvailableSamplesPacketExceptionRB = java.util.ResourceBundle
			.getBundle("com/linkare/rec/impl/exceptions/resources/NotAnAvailableSamplesPacketException");

	public static final int PACKET_NOT_FOUND_IN_CACHE = 0;
	public static final String PACKET_NOT_FOUND_IN_CACHE_MSG = NotAnAvailableSamplesPacketExceptionConstants.notAnAvailableSamplesPacketExceptionRB
			.getString("not_an_available_samples_packet_in_cache");

	public static String getTranslatedMessage(final NotAnAvailableSamplesPacketException e) {
		switch (e.errorCode) {
		case PACKET_NOT_FOUND_IN_CACHE:
			return NotAnAvailableSamplesPacketExceptionConstants.PACKET_NOT_FOUND_IN_CACHE_MSG;

		}

		return e.getMessage();
	}

}
