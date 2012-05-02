/**
 * 
 */
package com.linkare.rec.am.model.util;

/**
 * @author jpereira
 * 
 */
public class NoValidRecipientsFoundForMessage extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -3898069447228145632L;

    /**
     * 
     */
    public NoValidRecipientsFoundForMessage() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public NoValidRecipientsFoundForMessage(String message) {
	super(message);
    }

    /**
     * @param cause
     */
    public NoValidRecipientsFoundForMessage(Throwable cause) {
	super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public NoValidRecipientsFoundForMessage(String message, Throwable cause) {
	super(message, cause);
    }

}
