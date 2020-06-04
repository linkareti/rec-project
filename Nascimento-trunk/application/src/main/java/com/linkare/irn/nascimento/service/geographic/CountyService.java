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

import com.linkare.irn.nascimento.dao.geographic.CountyDAO;
import com.linkare.irn.nascimento.dao.geographic.DistrictDAO;
import com.linkare.irn.nascimento.model.ext.mapping.geographic.CountyDTO;
import com.linkare.irn.nascimento.model.geographic.County;
import com.linkare.irn.nascimento.model.geographic.District;
import com.linkare.irn.nascimento.model.mapping.Mapper;
import com.linkare.irn.nascimento.service.BaseImportableStatelessServiceImpl;
import com.linkare.irn.nascimento.service.cdi.EventOperation;
import com.linkare.irn.nascimento.service.cdi.geographic.CountyEvent;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CountyService extends BaseImportableStatelessServiceImpl<County, CountyDAO, CountyDTO> {

    @Inject
    @Any
    private Event<CountyEvent> countyEvent;

    private DistrictDAO districtDAO;

    @PostConstruct
    @Override
    public void init() {
	super.init();
	this.districtDAO = new DistrictDAO(getEntityManager());
    }

    @Override
    public CountyDAO getDAO() {
	return new CountyDAO(getEntityManager());
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
	columnMapping.put("districtExternalId", "districtExternalId");
    }

    @Override
    public void doAdditionalMappings(CountyDTO entry, County entity) {
	if (StringUtils.isNotBlank(entry.getDistrictExternalId())) {
	    final Mapper districtMapper = getMapperDAO().findByModuleAndExternalId(District.class.getSimpleName(), entry.getDistrictExternalId());
	    if (districtMapper != null) {
		final District district = districtDAO.find(districtMapper.getInternalId());
		entity.setDistrict(district);
	    }
	}

	super.doAdditionalMappings(entry, entity);
    }

    @Override
    public void doValidation(CountyDTO entry, County entity) {
	if (StringUtils.isNotBlank(entry.getDistrictExternalId()) && entity.getDistrict() == null) {
	    throw new ValidationException("District with externalId " + entry.getDistrictExternalId() + " was not found");
	}

	super.doValidation(entry, entity);
    }

    @Override
    public Class<CountyDTO> getType() {
	return CountyDTO.class;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public County create(final County entity, Class<?>... validationGroups) {
	final County result = super.create(entity);
	countyEvent.fire(new CountyEvent(EventOperation.CREATE, result));
	return result;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public County update(County entity, Class<?>... validationGroups) {
	final County result = super.update(entity);
	countyEvent.fire(new CountyEvent(EventOperation.UPDATE, result));
	return result;
    }
}