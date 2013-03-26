package com.linkare.rec.web;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class UnknownDomainException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2892502285131619123L;

	public UnknownDomainException() {
	super();
    }

    public UnknownDomainException(String message, Throwable cause) {
	super(message, cause);
    }

    public UnknownDomainException(String message) {
	super(message);
    }

    public UnknownDomainException(Throwable cause) {
	super(cause);
    }
}