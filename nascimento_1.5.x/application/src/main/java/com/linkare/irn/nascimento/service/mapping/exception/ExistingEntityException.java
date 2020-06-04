package com.linkare.irn.nascimento.service.mapping.exception;

/**
 * It represents the situation where an existing entity already exists
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ExistingEntityException extends RuntimeException {

    private static final long serialVersionUID = 8527569433439352415L;

    private Long existingId;

    public ExistingEntityException(final long existingId) {
	super();
	setExistingId(existingId);
    }

    public ExistingEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, final long existingId) {
	super(message, cause, enableSuppression, writableStackTrace);
	setExistingId(existingId);
    }

    public ExistingEntityException(String message, Throwable cause, final long existingId) {
	super(message, cause);
	setExistingId(existingId);
    }

    public ExistingEntityException(String message, final long existingId) {
	super(message);
	setExistingId(existingId);
    }

    public ExistingEntityException(Throwable cause, final long existingId) {
	super(cause);
	setExistingId(existingId);
    }

    /**
     * @return the existingId
     */
    public long getExistingId() {
	return existingId;
    }

    private void setExistingId(final Long existingId) {
	if (existingId == null) {
	    throw new IllegalArgumentException("Existing id cannot be null");
	}
	this.existingId = existingId;
    }
}