package com.linkare.irn.nascimento.service.cdi;

import java.io.Serializable;

import com.linkare.irn.nascimento.model.DomainObject;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class BaseEvent<T extends DomainObject> implements Serializable {

    private static final long serialVersionUID = 2836499050730401933L;

    private final EventOperation operation;

    private final T payload;

    public BaseEvent(final EventOperation operation, final T payload) {
	super();
	this.operation = operation;
	this.payload = payload;
    }

    /**
     * @return the operation
     */
    public EventOperation getOperation() {
	return operation;
    }

    /**
     * @return the payload
     */
    public T getPayload() {
	return payload;
    }

    @Override
    public String toString() {
	return getOperation() + ":" + (getPayload() == null ? null : getPayload());
    }
}