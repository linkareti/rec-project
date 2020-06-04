package com.linkare.irn.nascimento.model;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class DomainException extends Exception {

    public static final String GENERAL_ERROR_CODE = "Unspecified";

    private static final long serialVersionUID = -9026529940199461593L;

    private final String errorCode;

    public DomainException(final String message, final Throwable cause, final String errorCode) {
	super(message, cause);
	this.errorCode = StringUtils.isBlank(errorCode) ? GENERAL_ERROR_CODE : errorCode;
    }

    public DomainException(final String message, final String errorCode) {
	super(message);
	this.errorCode = StringUtils.isBlank(errorCode) ? GENERAL_ERROR_CODE : errorCode;
    }

    public DomainException(final Throwable cause, final String errorCode) {
	super(cause);
	this.errorCode = StringUtils.isBlank(errorCode) ? GENERAL_ERROR_CODE : errorCode;
    }

    public DomainException(final Throwable cause) {
	this(cause, GENERAL_ERROR_CODE);
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
	return errorCode;
    }
}