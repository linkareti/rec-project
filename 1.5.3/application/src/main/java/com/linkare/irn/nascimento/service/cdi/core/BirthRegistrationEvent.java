package com.linkare.irn.nascimento.service.cdi.core;

import com.linkare.irn.nascimento.model.core.BirthRegistration;
import com.linkare.irn.nascimento.service.cdi.BaseEvent;
import com.linkare.irn.nascimento.service.cdi.EventOperation;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class BirthRegistrationEvent extends BaseEvent<BirthRegistration> {

    private static final long serialVersionUID = -6504193958467041430L;

    public BirthRegistrationEvent(EventOperation operation, BirthRegistration payload) {
	super(operation, payload);
    }
}