/* 
 * ManagementException.java created on Jun 11, 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils.mbean;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class ManagementException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ManagementException() {
		super();
	}

	public ManagementException(String message, Throwable cause) {
		super(message, cause);
	}

	public ManagementException(String message) {
		super(message);
	}

	public ManagementException(Throwable cause) {
		super(cause);
	}

}
