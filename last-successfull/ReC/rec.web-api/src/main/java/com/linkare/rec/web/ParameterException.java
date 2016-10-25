package com.linkare.rec.web;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class ParameterException extends Exception {

    private static final long serialVersionUID = -3353420039725953601L;

    public ParameterException() {
	super();
    }

    public ParameterException(String message, Throwable cause) {
	super(message, cause);
    }

    public ParameterException(String message) {
	super(message);
    }

    public ParameterException(Throwable cause) {
	super(cause);
    }
}