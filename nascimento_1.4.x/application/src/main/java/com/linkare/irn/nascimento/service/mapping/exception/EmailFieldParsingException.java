package com.linkare.irn.nascimento.service.mapping.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class EmailFieldParsingException extends RuntimeException {

	private static final long serialVersionUID = -7633472428347237610L;

	/**
	 * 
	 */
	public EmailFieldParsingException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public EmailFieldParsingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EmailFieldParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public EmailFieldParsingException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public EmailFieldParsingException(Throwable cause) {
		super(cause);
	}
}