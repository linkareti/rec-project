package com.linkare.irn.nascimento.service.mapping.exception;

import javax.ejb.ApplicationException;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@ApplicationException(rollback = true)
public class CSVFileParsingException extends RuntimeException {

    private static final long serialVersionUID = -7633472428347237610L;

    /**
     * 
     */
    public CSVFileParsingException() {
	super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CSVFileParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public CSVFileParsingException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * @param message
     */
    public CSVFileParsingException(String message) {
	super(message);
    }

    /**
     * @param cause
     */
    public CSVFileParsingException(Throwable cause) {
	super(cause);
    }
}