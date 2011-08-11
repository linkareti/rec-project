package com.linkare.rec.am.model.util.converter;

import javax.ejb.EJBException;

public class RecMappingException extends EJBException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RecMappingException() {
	super();
    }

    public RecMappingException(Exception ex) {
	super(ex);
    }

    public RecMappingException(String message, Exception ex) {
	super(message, ex);
    }

    public RecMappingException(String message) {
	super(message);
    }

}
