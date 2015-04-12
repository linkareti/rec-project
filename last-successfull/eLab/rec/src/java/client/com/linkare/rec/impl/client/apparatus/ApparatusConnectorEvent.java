/*
 * ApparatusConnectorEvent.java
 *
 * Created on July 29, 2004, 12:14 PM
 */

package com.linkare.rec.impl.client.apparatus;

import com.linkare.rec.acquisition.DataProducer;

/**
 * @author André Neto - LEFT - IST
 * @param <T>
 */
public class ApparatusConnectorEvent extends java.util.EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7071882058319116578L;
	private String message = null;
	private long millis_to_lock_success = 0;
	private DataProducer dataSource = null;

	private String key;
	private Object value;

	public ApparatusConnectorEvent(final Object source, final String message) {
		super(source);
		this.message = message;
	}

	public ApparatusConnectorEvent(final Object source, final long millis_to_lock_success) {
		super(source);
		this.millis_to_lock_success = millis_to_lock_success;
	}

	public ApparatusConnectorEvent(final Object source,
			final DataProducer dataSource) {
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

	public DataProducer getDataSource() {
		return dataSource;
	}

	public void setValue(final String key, final Object value) {
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
