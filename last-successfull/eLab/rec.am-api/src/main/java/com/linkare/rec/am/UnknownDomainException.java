package com.linkare.rec.am;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class UnknownDomainException extends Exception {

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