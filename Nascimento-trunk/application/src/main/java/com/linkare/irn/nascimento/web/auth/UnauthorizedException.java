package com.linkare.irn.nascimento.web.auth;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = -1764774757854979276L;

    /**
     * 
     */
    public UnauthorizedException() {
	super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public UnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public UnauthorizedException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * @param message
     */
    public UnauthorizedException(String message) {
	super(message);
    }

    /**
     * @param cause
     */
    public UnauthorizedException(Throwable cause) {
	super(cause);
    }
}