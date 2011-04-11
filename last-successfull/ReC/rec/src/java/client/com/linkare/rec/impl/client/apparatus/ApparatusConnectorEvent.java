/*
 * ApparatusConnectorEvent.java
 *
 * Created on July 29, 2004, 12:14 PM
 */

package com.linkare.rec.impl.client.apparatus;

/**
 * @author André Neto - LEFT - IST
 * @param <T>
 */
public class ApparatusConnectorEvent extends java.util.EventObject {

	private String message = null;
	private long millis_to_lock_success = 0;
	private com.linkare.rec.impl.wrappers.DataProducerWrapper dataSource = null;

	private String key;
	private Object value;

	public ApparatusConnectorEvent(Object source, String message) {
		super(source);
		this.message = message;
	}

	public ApparatusConnectorEvent(Object source, long millis_to_lock_success) {
		super(source);
		this.millis_to_lock_success = millis_to_lock_success;
	}

	public ApparatusConnectorEvent(Object source, com.linkare.rec.impl.wrappers.DataProducerWrapper dataSource) {
		super(source);
		// this.millis_to_lock_success = millis_to_lock_success;
		this.dataSource = dataSource;
	}

	public String getMessage() {
		return message;
	}

	public long getMillisToLockSuccess() {
		return millis_to_lock_success;
	}

	public com.linkare.rec.impl.wrappers.DataProducerWrapper getDataSource() {
		return dataSource;
	}

	public void setValue(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

}
