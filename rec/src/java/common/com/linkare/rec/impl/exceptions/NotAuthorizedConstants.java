/*
 * NotAuthorizedConstants.java
 *
 * Created on 24 de Janeiro de 2003, 11:59
 */

package com.linkare.rec.impl.exceptions;

import com.linkare.rec.acquisition.WrongConfigurationException;

public class NotAuthorizedConstants {
	private static final java.util.ResourceBundle notAuthorizedRB = java.util.ResourceBundle
			.getBundle("com/linkare/rec/impl/exceptions/resources/NotAuthorized");

	public static final int NOT_AUTHORIZED_USERNAME_NULL = 0;
	public static final String NOT_AUTHORIZED_USERNAME_NULL_MSG = NotAuthorizedConstants.notAuthorizedRB
			.getString("not_authorized_username_null");

	public static final int NOT_AUTHORIZED_USERNAME_EMPTY = 1;
	public static final String NOT_AUTHORIZED_USERNAME_EMPTY_MSG = NotAuthorizedConstants.notAuthorizedRB
			.getString("not_authorized_username_empty");

	public static final int NOT_AUTHORIZED_USERNAME_NOT_AVAILABLE = 2;
	public static final String NOT_AUTHORIZED_USERNAME_NOT_AVAILABLE_MSG = NotAuthorizedConstants.notAuthorizedRB
			.getString("not_authorized_username_unreadable_network_error");

	public static final int NOT_AUTHORIZED_USERNAME_WRONG = 3;
	public static final String NOT_AUTHORIZED_USERNAME_WRONG_MSG = NotAuthorizedConstants.notAuthorizedRB
			.getString("not_authorized_username_not_available");

	public static final int NOT_AUTHORIZED_USERNAME_REPEATED = 4;
	public static final String NOT_AUTHORIZED_USERNAME_REPEATED_MSG = NotAuthorizedConstants.notAuthorizedRB
			.getString("not_authorized_username_allready_taken");

	public static final int NOT_AUTHORIZED_USERNAME_PASSWORD_NOT_MATCH = 5;
	public static final String NOT_AUTHORIZED_USERNAME_PASSWORD_NOT_MATCH_MSG = NotAuthorizedConstants.notAuthorizedRB
			.getString("not_authorized_username_or_password_invalid");

	public static final int NOT_AUTHORIZED_OPERATION = 6;
	public static final String NOT_AUTHORIZED_OPERATION_MSG = NotAuthorizedConstants.notAuthorizedRB
			.getString("not_authorized_operation");

	public static final int NOT_AUTHORIZED_SECURITY_MANAGER = 7;
	public static final String NOT_AUTHORIZED_SECURITY_MANAGER_MSG = NotAuthorizedConstants.notAuthorizedRB
			.getString("not_authorized_securitymanager");

	public static final int NOT_AUTHORIZED_CONNECTION_FAILED = 8;
	public static final String NOT_AUTHORIZED_CONNECTION_FAILED_MSG = NotAuthorizedConstants.notAuthorizedRB
			.getString("not_authorized_connectionfailed");

	public static final int NOT_AUTHORIZED_REGISTER_AT_PARENT_RESOURCE_FIRST = 9;
	public static final String NOT_AUTHORIZED_REGISTER_AT_PARENT_RESOURCE_FIRST_MSG = NotAuthorizedConstants.notAuthorizedRB
			.getString("not_authorized_register_at_parent_resource_first");

	public static String getTranslatedMessage(final WrongConfigurationException e) {
		switch (e.errorCode) {
		case NOT_AUTHORIZED_USERNAME_EMPTY:
			return NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_EMPTY_MSG;
		case NOT_AUTHORIZED_USERNAME_NOT_AVAILABLE:
			return NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_NOT_AVAILABLE_MSG;
		case NOT_AUTHORIZED_USERNAME_NULL:
			return NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_NULL_MSG;
		case NOT_AUTHORIZED_USERNAME_PASSWORD_NOT_MATCH:
			return NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_PASSWORD_NOT_MATCH_MSG;
		case NOT_AUTHORIZED_USERNAME_REPEATED:
			return NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_REPEATED_MSG;
		case NOT_AUTHORIZED_USERNAME_WRONG:
			return NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_WRONG_MSG;
		case NOT_AUTHORIZED_OPERATION:
			return NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION_MSG;
		case NOT_AUTHORIZED_SECURITY_MANAGER:
			return NotAuthorizedConstants.NOT_AUTHORIZED_SECURITY_MANAGER_MSG;
		case NOT_AUTHORIZED_CONNECTION_FAILED:
			return NotAuthorizedConstants.NOT_AUTHORIZED_CONNECTION_FAILED_MSG;
		case NOT_AUTHORIZED_REGISTER_AT_PARENT_RESOURCE_FIRST:
			return NotAuthorizedConstants.NOT_AUTHORIZED_REGISTER_AT_PARENT_RESOURCE_FIRST_MSG;
		}

		return e.getMessage();
	}

}