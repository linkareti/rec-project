/* 
 * NoControlFileException.java created on 28 Jun 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils;

/**
 * 
 * @author jpereira
 */
public class NoControlFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3100337935729946446L;

	public NoControlFileException() {
		super();
	}

	public NoControlFileException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public NoControlFileException(final String message) {
		super(message);
	}

	public NoControlFileException(final Throwable cause) {
		super(cause);
	}

}
