/* 
 * ReCException.java created on Feb 6, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.exceptions;

/**
 * The ReC checked Exceptions base.
 * 
 * @author Henrique Fernandes
 */
public class ReCException extends Exception implements ReCExceptionDescriptionCode {

	private static final long serialVersionUID = -4083319135076449860L;

	private ExceptionCode code;

	/**
	 * Creates a new <code>ReCException</code>.
	 * 
	 * @param code
	 * @param message
	 * @param cause
	 */
	public ReCException(ExceptionCode code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	/**
	 * Creates a new <code>ReCException</code>.
	 * 
	 * @param code
	 * @param message
	 */
	public ReCException(ExceptionCode code, String message) {
		super(message);
		this.code = code;
	}

	/*
	 * @see com.linkare.rec.impl.exceptions.ReCExceptionMessageCode#getCode()
	 */
	@Override
	public ExceptionCode getCode() {
		return code;
	}

}
