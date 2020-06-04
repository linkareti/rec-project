package com.linkare.irn.nascimento.service.cdi.geographic;

import com.linkare.irn.nascimento.model.geographic.District;
import com.linkare.irn.nascimento.service.cdi.BaseEvent;
import com.linkare.irn.nascimento.service.cdi.EventOperation;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class DistrictEvent extends BaseEvent<District> {

    private static final long serialVersionUID = -6504193958467041430L;

    public DistrictEvent(EventOperation operation, District payload) {
	super(operation, payload);
    }
}