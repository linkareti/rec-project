/* 
 * ValidationError.java created on 27 Feb 2013
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public final class ValidationError {

	private String message;
	private int code;

	/**
	 * Creates the <code>ValidationError</code>.
	 * 
	 * @param message
	 * @param code 
	 */
	public ValidationError(final String message, final int code) {
		super();
		this.message = message;
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return message;
	}
}
