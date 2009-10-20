/* 
 * ExceptionCode.java created on Feb 6, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.exceptions;

/**
 * Holds the ReC Application exception codes and description. <br>
 * These codes can be used on checked and unchecked ReC Exceptions.
 * 
 * @author Henrique Fernandes
 */
public enum ExceptionCode {

	THE_FAMOUS_UNKNOWN_ERROR("Unknown Error", 1),

	MISSING_SYSTEM_PROPERTIES("Missing System Properties", 10);

	int id;
	String description;

	ExceptionCode(String description, int id) {
		this.description = description;
		this.id = id;
	}

	/**
	 * @return the <code>ExceptionCode</code> description.
	 */
	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

}