package com.linkare.irn.nascimento.service.geographic;

import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.validation.ValidationException;

import com.linkare.irn.nascimento.dao.geographic.CountryDAO;
import com.linkare.irn.nascimento.model.ext.mapping.geographic.CountryDTO;
import com.linkare.irn.nascimento.model.geographic.Country;
import com.linkare.irn.nascimento.service.BaseImportableStatelessServiceImpl;
import com.linkare.irn.nascimento.service.cdi.EventOperation;
import com.linkare.irn.nascimento.service.cdi.geographic.CountryEvent;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CountryService extends BaseImportableStatelessServiceImpl<Country, CountryDAO, CountryDTO> {

    @Inject
    @Any
    private Event<CountryEvent> countryEvent;

    @Override
    public CountryDAO getDAO() {
	return new CountryDAO(getEntityManager());
    }

    /**
     * @param columnMapping
     */
    @Override
    public void mapCsvColumnsToProperties(final Map<String, String> columnMapping) {
	columnMapping.put("externalId", "externalId");
	columnMapping.put("name", "name");
	columnMapping.put("code", "code");
	columnMapping.put("active", "active");
    }

    @Override
    public Class<CountryDTO> getType() {
	return CountryDTO.class;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Country create(final Country entity, final Class<?>... validationGroups) {
	final Country result = super.create(entity);
	countryEvent.fire(new CountryEvent(EventOperation.CREATE, result));
	return result;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Country update(Country entity, final Class<?>... validationGroups) {
	final Country result = super.update(entity);
	countryEvent.fire(new CountryEvent(EventOperation.UPDATE, result));
	return result;
    }

    public Country findByCode(final String code) {
	return getDAO().findByCode(code);
    }

    @Override
    protected void validate(Country entity, Class<?>... validationGroups) {
	super.validate(entity, validationGroups);

	final Country existingCountry = getDAO().findByCode(entity.getCode());
	if (existingCountry != null) {
	    throw new ValidationException("A country with the code '" + entity.getCode() + "' already exists");
	}
    }
}