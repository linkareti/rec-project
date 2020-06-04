package com.linkare.irn.nascimento.service.cdi.organization;

import com.linkare.irn.nascimento.model.organization.Organization;
import com.linkare.irn.nascimento.service.cdi.BaseEvent;
import com.linkare.irn.nascimento.service.cdi.EventOperation;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class OrganizationEvent extends BaseEvent<Organization> {

    private static final long serialVersionUID = -6504193958467041430L;

    public OrganizationEvent(EventOperation operation, Organization payload) {
	super(operation, payload);
    }
}