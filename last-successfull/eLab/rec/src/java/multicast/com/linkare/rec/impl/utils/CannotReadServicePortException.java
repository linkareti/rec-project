/* 
 * CannotReadServicePortException.java created on 28 Jun 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils;

/**
 * 
 * @author jpereira
 */
public class CannotReadServicePortException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5177461978317439092L;

	public CannotReadServicePortException() {
		super();
	}

	public CannotReadServicePortException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public CannotReadServicePortException(final String message) {
		super(message);
	}

	public CannotReadServicePortException(final Throwable cause) {
		super(cause);
	}

}
