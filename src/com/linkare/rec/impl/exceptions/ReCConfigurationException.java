/* 
 * ReCConfigurationException.java created on Feb 6, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.exceptions;

/**
 * Represents the configuration exceptions group on ReC.
 * 
 * @author Henrique Fernandes
 */
public class ReCConfigurationException extends ReCRuntimeException {

    private static final long serialVersionUID = 5606883801602301869L;

    /**
     * Creates a new <code>ReCRuntimeException</code>.
     * 
     * @param code
     * @param message
     * @param cause
     */
    public ReCConfigurationException(ExceptionCode code, String message, Throwable cause) {
	super(code, message, cause);
    }

    /**
     * Creates a new <code>ReCRuntimeException</code>.
     * 
     * @param code
     * @param message
     */
    public ReCConfigurationException(ExceptionCode code, String message) {
	super(code, message);
    }

}
