package com.linkare.irn.nascimento.service.geographic;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.validation.ValidationException;

import org.apache.commons.lang.StringUtils;

import com.linkare.irn.nascimento.dao.geographic.CountryDAO;
import com.linkare.irn.nascimento.dao.geographic.DistrictDAO;
import com.linkare.irn.nascimento.model.ext.mapping.geographic.DistrictDTO;
import com.linkare.irn.nascimento.model.geographic.Country;
import com.linkare.irn.nascimento.model.geographic.District;
import com.linkare.irn.nascimento.model.mapping.Mapper;
import com.linkare.irn.nascimento.service.BaseImportableStatelessServiceImpl;
import com.linkare.irn.nascimento.service.cdi.EventOperation;
import com.linkare.irn.nascimento.service.cdi.geographic.DistrictEvent;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DistrictService extends BaseImportableStatelessServiceImpl<District, DistrictDAO, DistrictDTO> {

    @Inject
    @Any
    private Event<DistrictEvent> districtEvent;

    private CountryDAO countryDAO;

    @PostConstruct
    @Override
    public void init() {
	super.init();
	this.countryDAO = new CountryDAO(getEntityManager());
    }

    @Override
    public DistrictDAO getDAO() {
	return new DistrictDAO(getEntityManager());
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
	columnMapping.put("countryExternalId", "countryExternalId");
    }

    @Override
    public void doAdditionalMappings(DistrictDTO entry, District entity) {
	if (StringUtils.isNotBlank(entry.getCountryExternalId())) {
	    final Mapper countryMapper = getMapperDAO().findByModuleAndExternalId(Country.class.getSimpleName(), entry.getCountryExternalId());
	    if (countryMapper != null) {
		final Country country = countryDAO.find(countryMapper.getInternalId());
		entity.setCountry(country);
	    }
	}

	super.doAdditionalMappings(entry, entity);
    }

    @Override
    public void doValidation(DistrictDTO entry, District entity) {
	if (StringUtils.isNotBlank(entry.getCountryExternalId()) && entity.getCountry() == null) {
	    throw new ValidationException("Country with externalId " + entry.getCountryExternalId() + " was not found");
	}

	super.doValidation(entry, entity);
    }

    @Override
    public Class<DistrictDTO> getType() {
	return DistrictDTO.class;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public District create(final District entity, Class<?>... validationGroups) {
	final District result = super.create(entity);
	districtEvent.fire(new DistrictEvent(EventOperation.CREATE, result));
	return result;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public District update(District entity, Class<?>... validationGroups) {
	final District result = super.update(entity);
	districtEvent.fire(new DistrictEvent(EventOperation.UPDATE, result));
	return result;
    }
}