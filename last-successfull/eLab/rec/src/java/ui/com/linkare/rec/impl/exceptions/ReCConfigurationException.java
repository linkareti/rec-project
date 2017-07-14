/* 
 * ReCConfigurationException.java created on Feb 6, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.exceptions;

/**
 * Represents the configuration exceptions group on rec.
 * 
 * @author Henrique Fernandes
 */
public class ReCConfigurationException extends ReCException {

	private static final long serialVersionUID = 5606883801602301869L;

	/**
	 * Creates a new <code>ReCRuntimeException</code>.
	 * 
	 * @param code
	 * @param message
	 * @param cause
	 */
	public ReCConfigurationException(final ExceptionCode code, final String message, final Throwable cause) {
		super(code, message, cause);
	}

	/**
	 * Creates a new <code>ReCRuntimeException</code>.
	 * 
	 * @param code
	 * @param message
	 */
	public ReCConfigurationException(final ExceptionCode code, final String message) {
		super(code, message);
	}

}
