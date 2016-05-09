/* 
 * ValidationErrors.java created on 27 Feb 2013
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils;

import java.util.ArrayList;

import com.linkare.rec.impl.config.ReCSystemProperty;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ValidationErrors extends ArrayList<ValidationError> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4352177827556240584L;
	
	
	private static final String LINE_SEPARATOR = ReCSystemProperty.LINE_SEPARATOR.getValue();

	public void addError(final String message, final int code) {
		this.add(new ValidationError(message,code));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		boolean addNewLine = false;
		for (ValidationError error : this) {
			if (addNewLine) {
				builder.append(LINE_SEPARATOR);
			}
			builder.append(error);
			addNewLine = true;
		}
		return builder.toString();
	}
}
