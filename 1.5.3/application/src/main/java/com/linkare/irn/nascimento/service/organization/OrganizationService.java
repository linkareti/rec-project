package com.linkare.irn.nascimento.service.organization;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.owasp.esapi.util.CollectionsUtil;

import com.linkare.irn.nascimento.dao.organization.OrganizationDAO;
import com.linkare.irn.nascimento.exception.organization.OrganizationNotFoundException;
import com.linkare.irn.nascimento.model.ext.mapping.organization.OrganizationDTO;
import com.linkare.irn.nascimento.model.organization.Organization;
import com.linkare.irn.nascimento.model.organization.OrganizationType;
import com.linkare.irn.nascimento.service.BaseImportableStatelessServiceImpl;
import com.linkare.irn.nascimento.service.cdi.EventOperation;
import com.linkare.irn.nascimento.service.cdi.organization.OrganizationEvent;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class OrganizationService
		extends BaseImportableStatelessServiceImpl<Organization, OrganizationDAO, OrganizationDTO> {

	@Inject
	@Any
	private Event<OrganizationEvent> organizationEvent;

	@Override
	public void mapCsvColumnsToProperties(Map<String, String> columnMapping) {
		columnMapping.put("externalId", "externalId");
		columnMapping.put("name", "name");
		columnMapping.put("email", "email");
		columnMapping.put("secondEmail", "secondEmail");
		columnMapping.put("organizationType", "organizationType");
		columnMapping.put("active", "active");
	}

	@Override
	public void doValidation(OrganizationDTO entry, Organization entity) {
		super.doValidation(entry, entity);
		if (StringUtils.isNotBlank(entry.getOrganizationType()) && entity.getOrganizationType() == null) {
			throw new ValidationException("Unknown value for organization type " + entry.getOrganizationType());
		}
	}

	@Override
	public Class<OrganizationDTO> getType() {
		return OrganizationDTO.class;
	}

	@Override
	public OrganizationDAO getDAO() {
		return new OrganizationDAO(getEntityManager());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public Organization create(final Organization entity, Class<?>... validationGroups) {
		final Organization result = super.create(entity);
		organizationEvent.fire(new OrganizationEvent(EventOperation.CREATE, result));
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public Organization update(Organization entity, Class<?>... validationGroups) {
		final Organization result = super.update(entity);
		organizationEvent.fire(new OrganizationEvent(EventOperation.UPDATE, result));
		return result;
	}

	public List<Organization> findActiveByType(final OrganizationType organizationType) {
		return getDAO().findActiveByType(organizationType);
	}

	public Organization findActiveByTypeAndEmail(final OrganizationType organizationType, String email) {

		Organization organization = getDAO().findyActiveAndTypeAndEmail(organizationType, email);

		if (organization == null) {
			throw new OrganizationNotFoundException();
		}

		return organization;
	}
	
	
	public List<Organization> findActivesByTypeAndEmail(final OrganizationType organizationType, String email) {

		List<Organization> organizations = getDAO().findyActivesAndTypeAndEmail(organizationType, email);

		if (CollectionUtils.isEmpty(organizations)) {
			throw new OrganizationNotFoundException();
			
		}

		return organizations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.linkare.irn.nascimento.service.BaseStatelessServiceImpl#validate(com.
	 * linkare.irn.nascimento.model.DomainObject, java.lang.Class[])
	 */
	@Override
	protected void validate(Organization entity, Class<?>... validationGroups) {
		super.validate(entity, validationGroups);

		if (entity.getOrganizationType() == OrganizationType.CIVIL_REGISTRATION_OFFICE
				&& StringUtils.isNotBlank(entity.getSecondEmail())) {
			throw new ValidationException("The civil registration organization cannot have a second email");
		}
	}
}