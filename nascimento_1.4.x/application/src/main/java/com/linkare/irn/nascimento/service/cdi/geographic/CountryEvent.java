package com.linkare.irn.nascimento.service.cdi.geographic;

import com.linkare.irn.nascimento.model.geographic.Country;
import com.linkare.irn.nascimento.service.cdi.BaseEvent;
import com.linkare.irn.nascimento.service.cdi.EventOperation;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class CountryEvent extends BaseEvent<Country> {

    private static final long serialVersionUID = -6504193958467041430L;

    public CountryEvent(EventOperation operation, Country payload) {
	super(operation, payload);
    }
}