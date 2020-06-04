package com.linkare.irn.nascimento.service.cdi.geographic;

import com.linkare.irn.nascimento.model.geographic.County;
import com.linkare.irn.nascimento.service.cdi.BaseEvent;
import com.linkare.irn.nascimento.service.cdi.EventOperation;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class CountyEvent extends BaseEvent<County> {

    private static final long serialVersionUID = -6504193958467041430L;

    public CountyEvent(EventOperation operation, County payload) {
	super(operation, payload);
    }
}