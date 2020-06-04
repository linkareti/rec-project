package com.linkare.irn.nascimento.service.cdi;

import com.linkare.irn.nascimento.model.config.Configuration;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ConfigurationEvent extends BaseEvent<Configuration> {

    private static final long serialVersionUID = -4204757302074091749L;

    public ConfigurationEvent(EventOperation operation, Configuration payload) {
	super(operation, payload);
    }
}