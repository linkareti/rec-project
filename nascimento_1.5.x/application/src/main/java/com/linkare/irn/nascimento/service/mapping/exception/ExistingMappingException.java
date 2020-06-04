package com.linkare.irn.nascimento.service.mapping.exception;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ExistingMappingException extends RuntimeException {

    private static final long serialVersionUID = 8527569433439352415L;

    public ExistingMappingException() {
	super();
    }

    public ExistingMappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public ExistingMappingException(String message, Throwable cause) {
	super(message, cause);
    }

    public ExistingMappingException(String message) {
	super(message);
    }

    public ExistingMappingException(Throwable cause) {
	super(cause);
    }
}