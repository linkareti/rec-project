/* 
 * ReCRuntimeException.java created on Feb 6, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.exceptions;

/**
 * The ReC unchecked Exceptions base.
 * 
 * @author Henrique Fernandes
 */
public class ReCRuntimeException extends RuntimeException implements ReCExceptionDescriptionCode {

	private static final long serialVersionUID = 1101481717221162525L;

	private ExceptionCode code;

	/**
	 * Creates a new <code>ReCRuntimeException</code>.
	 * 
	 * @param code
	 * @param message
	 * @param cause
	 */
	public ReCRuntimeException(ExceptionCode code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	/**
	 * Creates a new <code>ReCRuntimeException</code>.
	 * 
	 * @param code
	 * @param message
	 */
	public ReCRuntimeException(ExceptionCode code, String message) {
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
