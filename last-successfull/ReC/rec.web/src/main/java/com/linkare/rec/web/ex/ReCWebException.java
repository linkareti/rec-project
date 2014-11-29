package com.linkare.rec.web.ex;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class ReCWebException extends RuntimeException {

    private static final long serialVersionUID = 4412921221816209363L;

    public ReCWebException() {
	super();
    }

    public ReCWebException(String message, Throwable cause) {
	super(message, cause);
    }

    public ReCWebException(String message) {
	super(message);
    }

    public ReCWebException(Throwable cause) {
	super(cause);
    }
}