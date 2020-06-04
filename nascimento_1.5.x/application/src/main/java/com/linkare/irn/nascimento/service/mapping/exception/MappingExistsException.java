package com.linkare.irn.nascimento.service.mapping.exception;

/**
 * It represents the situation where an existing mapping already exists, during the import/initialization of data into the system
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class MappingExistsException extends RuntimeException {

    private static final long serialVersionUID = 8527569433439352415L;

    public MappingExistsException() {
	super();
    }

    public MappingExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public MappingExistsException(String message, Throwable cause) {
	super(message, cause);
    }

    public MappingExistsException(String message) {
	super(message);
    }

    public MappingExistsException(Throwable cause) {
	super(cause);
    }
}