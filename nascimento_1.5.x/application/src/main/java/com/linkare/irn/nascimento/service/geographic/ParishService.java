package com.linkare.irn.nascimento.service.geographic;

import java.util.List;
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
import com.linkare.irn.nascimento.dao.geographic.ParishDAO;
import com.linkare.irn.nascimento.model.ext.mapping.geographic.ParishDTO;
import com.linkare.irn.nascimento.model.geographic.County;
import com.linkare.irn.nascimento.model.geographic.Parish;
import com.linkare.irn.nascimento.model.mapping.Mapper;
import com.linkare.irn.nascimento.service.BaseImportableStatelessServiceImpl;
import com.linkare.irn.nascimento.service.cdi.EventOperation;
import com.linkare.irn.nascimento.service.cdi.geographic.ParishEvent;
import com.linkare.irn.nascimento.service.mapping.exception.ExistingEntityException;
import com.linkare.irn.nascimento.util.SelectionRange;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ParishService extends BaseImportableStatelessServiceImpl<Parish, ParishDAO, ParishDTO> {

    @Inject
    @Any
    private Event<ParishEvent> parishEvent;

    private CountyDAO countyDAO;

    @PostConstruct
    @Override
    public void init() {
	super.init();
	this.countyDAO = new CountyDAO(getEntityManager());
    }

    @Override
    public ParishDAO getDAO() {
	return new ParishDAO(getEntityManager());
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
	columnMapping.put("countyExternalId", "countyExternalId");
    }

    @Override
    public void doAdditionalMappings(ParishDTO entry, Parish entity) {
	if (StringUtils.isNotBlank(entry.getCountyExternalId())) {
	    final Mapper countyMapper = getMapperDAO().findByModuleAndExternalId(County.class.getSimpleName(), entry.getCountyExternalId());
	    if (countyMapper != null) {
		final County county = countyDAO.find(countyMapper.getInternalId());
		entity.setCounty(county);
	    }
	}

	super.doAdditionalMappings(entry, entity);
    }

    @Override
    public void doValidation(ParishDTO entry, Parish entity) {
	if (StringUtils.isNotBlank(entry.getCountyExternalId()) && entity.getCounty() == null) {
	    throw new ValidationException("County with externalId " + entry.getCountyExternalId() + " was not found");
	}

	super.doValidation(entry, entity);
    }

    @Override
    public Class<ParishDTO> getType() {
	return ParishDTO.class;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Parish create(final Parish entity, Class<?>... validationGroups) {
	final Parish result = super.create(entity);
	parishEvent.fire(new ParishEvent(EventOperation.CREATE, result));
	return result;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Parish update(Parish entity, Class<?>... validationGroups) {
	final Parish result = super.update(entity);
	parishEvent.fire(new ParishEvent(EventOperation.UPDATE, result));
	return result;
    }

    public Parish findByName(final String name) {
	return getDAO().findByName(name);
    }

    public List<Parish> findAllWithNameLikeInFullPath(final String name, final SelectionRange range) {
	return getDAO().findAllWithNameLikeInFullPath(name, range);
    }

    public List<Parish> findActiveWithNameLikeInFullPath(final String name, final SelectionRange range) {
	return getDAO().findActiveWithNameLikeInFullPath(name, range);
    }
}