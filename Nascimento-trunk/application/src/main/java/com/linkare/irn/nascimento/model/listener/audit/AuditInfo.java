package com.linkare.irn.nascimento.model.listener.audit;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class AuditInfo implements Serializable {

    private static final long serialVersionUID = -3764153352466937801L;

    public static final String UNKNOWN_USER = "GUEST";

    public static final String UNKNOWN_OPERATION = "UNKNOWN_OP";

    private static final long UNKNOWN_TIMESTAMP = 0;

    public static final AuditInfo EMPTY = new AuditInfo(UNKNOWN_USER, UNKNOWN_OPERATION, UNKNOWN_TIMESTAMP);

    private String username;

    private String operation;

    private long timestamp;

    public AuditInfo() {
	super();
    }

    public AuditInfo(final String username, final String operation, final long timestamp) {
	super();
	this.username = username;
	this.operation = operation;
	this.timestamp = timestamp;
    }

    /**
     * @return the username
     */
    public String getUsername() {
	return StringUtils.defaultString(username, UNKNOWN_USER);
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
	this.username = username;
    }

    /**
     * @return the operation
     */
    public String getOperation() {
	return StringUtils.defaultString(operation, UNKNOWN_OPERATION);
    }

    /**
     * @param operation
     *            the operation to set
     */
    public void setOperation(String operation) {
	this.operation = operation;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
	return timestamp == 0l ? System.currentTimeMillis() : timestamp;
    }

    /**
     * @param timestamp
     *            the timestamp to set
     */
    public void setTimestamp(long timestamp) {
	this.timestamp = timestamp;
    }
}