package com.linkare.irn.nascimento.service.config;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import com.linkare.irn.nascimento.dao.config.ConfigurationDAO;
import com.linkare.irn.nascimento.model.config.Configuration;
import com.linkare.irn.nascimento.service.BaseStatelessServiceImpl;
import com.linkare.irn.nascimento.service.cdi.ConfigurationEvent;
import com.linkare.irn.nascimento.service.cdi.EventOperation;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ConfigurationService extends BaseStatelessServiceImpl<Configuration, ConfigurationDAO> {

    @Inject
    @Any
    private Event<ConfigurationEvent> configurationEvent;

    @Override
    public ConfigurationDAO getDAO() {
	return new ConfigurationDAO(getEntityManager());
    }

    public Configuration find() {
	return findAll().isEmpty() ? null : findAll().get(0);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Configuration create(final Configuration entity, Class<?>... validationGroups) {
	if (findAll().isEmpty()) {
	    return super.create(entity);
	} else {
	    return findAll().get(0);
	}
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Configuration update(Configuration entity, Class<?>... validationGroups) {
	final Configuration result = super.update(entity);
	configurationEvent.fire(new ConfigurationEvent(EventOperation.UPDATE, result));
	return result;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void remove(final Configuration t) {
	throw new UnsupportedOperationException("Remove is not supported for configurations");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void remove(final long id) {
	throw new UnsupportedOperationException("Remove is not supported for configurations");
    }
}