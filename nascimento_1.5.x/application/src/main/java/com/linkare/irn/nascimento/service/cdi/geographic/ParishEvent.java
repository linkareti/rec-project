package com.linkare.irn.nascimento.service.cdi.geographic;

import com.linkare.irn.nascimento.model.geographic.Parish;
import com.linkare.irn.nascimento.service.cdi.BaseEvent;
import com.linkare.irn.nascimento.service.cdi.EventOperation;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ParishEvent extends BaseEvent<Parish> {

    private static final long serialVersionUID = -6504193958467041430L;

    public ParishEvent(EventOperation operation, Parish payload) {
	super(operation, payload);
    }
}