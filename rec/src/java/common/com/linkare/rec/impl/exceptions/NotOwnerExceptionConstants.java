package com.linkare.rec.impl.exceptions;

import com.linkare.rec.acquisition.NotOwnerException;

/**
 * Exception definition : NotOwnerException
 * 
 * @author OpenORB Compiler
 */
public class NotOwnerExceptionConstants {
	private static final java.util.ResourceBundle notOwnerExceptionRB = java.util.ResourceBundle
			.getBundle("com/linkare/rec/impl/exceptions/resources/NotOwnerException");

	public static final int HARDWARE_NOT_LOCKABLE_TO_ANYONE = 0;
	public static final String HARDWARE_NOT_LOCKABLE_TO_ANYONE_MSG = notOwnerExceptionRB
			.getString("multicasthardware_lock_unavailable_all_dataclients");
	public static final int HARDWARE_IN_LOCK_PROCESS_TO_ANOTHER = 1;
	public static final String HARDWARE_IN_LOCK_PROCESS_TO_ANOTHER_MSG = notOwnerExceptionRB
			.getString("multicasthardware_locking_another_dataclient");
	public static final int HARDWARE_LOCKED_TO_ANOTHER = 2;
	public static final String HARDWARE_LOCKED_TO_ANOTHER_MSG = notOwnerExceptionRB
			.getString("multicasthardware_locked_another_dataclient");
	public static final int HARDWARE_LOCK_AVAILABLE_TO_ANOTHER = 3;
	public static final String HARDWARE_LOCK_AVAILABLE_TO_ANOTHER_MSG = notOwnerExceptionRB
			.getString("multicasthardware_lock_available_another_dataclient");

	public static String getTranslatedMessage(NotOwnerException e) {
		switch (e.errorCode) {
		case HARDWARE_IN_LOCK_PROCESS_TO_ANOTHER:
			return HARDWARE_IN_LOCK_PROCESS_TO_ANOTHER_MSG;
		case HARDWARE_LOCKED_TO_ANOTHER:
			return HARDWARE_LOCKED_TO_ANOTHER_MSG;
		case HARDWARE_LOCK_AVAILABLE_TO_ANOTHER:
			return HARDWARE_LOCK_AVAILABLE_TO_ANOTHER_MSG;
		case HARDWARE_NOT_LOCKABLE_TO_ANYONE:
			return HARDWARE_NOT_LOCKABLE_TO_ANYONE_MSG;
		}

		return e.getMessage();
	}

}
