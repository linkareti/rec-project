/* 
 * BusinessServiceLocatorException.java created on Apr 13, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils.locator;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class BusinessServiceLocatorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessServiceLocatorException() {
		super();
	}

	public BusinessServiceLocatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessServiceLocatorException(String message) {
		super(message);
	}

	public BusinessServiceLocatorException(Throwable cause) {
		super(cause);
	}

}
