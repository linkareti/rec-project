/*
 * DefaultOperation.java
 *
 * Created on 2 de Janeiro de 2004, 16:03
 */
package com.linkare.rec.impl.multicast.security;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class DefaultOperation implements IOperation {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1873349350151682256L;
	private short operation;
	private HashMap<String, Object> properties;

	/** Creates a new instance of DefaultOperation */
	public DefaultOperation() {
		setOperation((short) -1);
		setProperties(new HashMap<String, Object>());
	}

	/** Creates a new instance of DefaultOperation 
	 * @param operation */
	public DefaultOperation(final short operation) {
		setOperation(operation);
		setProperties(new HashMap<String, Object>());
	}

	/** Creates a new instance of DefaultOperation 
	 * @param operation 
	 * @param properties */
	public DefaultOperation(final short operation, final Map<String, Object> properties) {
		setOperation(operation);
		setProperties(properties);
	}

	/**
	 * Getter for property operation.
	 * 
	 * @return Value of property operation.
	 * 
	 */
	@Override
	public short getOperation() {
		return operation;
	}

	/**
	 * Setter for property operation.
	 * 
	 * @param operation New value of property operation.
	 * 
	 */
	public void setOperation(final short operation) {
		this.operation = operation;
	}

	/**
	 * Getter for property properties.
	 * 
	 * @return Value of property properties.
	 * 
	 */
	@Override
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * Setter for property properties.
	 * 
	 * @param properties New value of property properties.
	 * 
	 */
	public void setProperties(final Map<String, Object> properties) {
		this.properties = new HashMap<String, Object>(properties);
	}

	@Override
	public String toString() {
		switch (operation) {
		case OP_CONFIG:
			return "CONFIG Config=" + getProperties().get(IOperation.PROPKEY_ACQ_CONFIG);
		case OP_ENUM_HARDWARES:
			return "ENUM_HARDWARES";
		case OP_ENUM_USERS:
			return "ENUM_USERS";
		case OP_GET_DATAPRODUCER:
			return "GET_DATAPRODUCER";
		case OP_GET_DATAPRODUCERSTATE:
			return "GET_DATAPRODUCERSTATE";
		case OP_GET_HARDWARESTATE:
			return "GET_HARDWARESTATE";
		case OP_GET_SAMPLES:
			return "GET_SAMPLES";
		case OP_LIST_HARDWARE:
			return "LIST_HARDWARE";
		case OP_LIST_USER:
			return "LIST_USER";
		case OP_LOCK:
			return "LOCK";
		case OP_RESET:
			return "RESET";
		case OP_SEND_MESSAGE:
			return "SEND_MESSAGE";
		case OP_START:
			return "START";
		case OP_START_OUTPUT:
			return "START_OUTPUT";
		case OP_STOP:
			return "STOP";
		case OP_GET_HARDWAREINFO:
			return "GET_HARDWAREINFO HardwareInfo=" + getProperties().get(IOperation.PROPKEY_HARDWAREINFO);
		default:
			return "UNKNOW OPERATION = NUMBER(" + operation + ")";
		}
	}

    @Override
    public int hashCode() {
        return getOperation() * getProperties().hashCode() * 13;
    }
    
	@Override
	public boolean equals(final Object other) {
		if (other == null || !(other instanceof IOperation)) {
			return false;
		}

		final IOperation otherOperation = (IOperation) other;

		return otherOperation.getOperation() == getOperation()
				&& getProperties().equals(otherOperation.getProperties());

	}

}
