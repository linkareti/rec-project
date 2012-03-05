package com.linkare.rec.am.web.ex;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class AllocationManagerException extends RuntimeException {

    private static final long serialVersionUID = 4412921221816209363L;

    public AllocationManagerException() {
	super();
    }

    public AllocationManagerException(String message, Throwable cause) {
	super(message, cause);
    }

    public AllocationManagerException(String message) {
	super(message);
    }

    public AllocationManagerException(Throwable cause) {
	super(cause);
    }
}