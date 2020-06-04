package com.linkare.irn.nascimento.web.converter.organization;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.linkare.irn.nascimento.dao.organization.OrganizationDAO;
import com.linkare.irn.nascimento.model.organization.Organization;
import com.linkare.irn.nascimento.web.converter.GenericConverter;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Named
public class OrganizationConverter extends GenericConverter<Organization, OrganizationDAO> {

    @Inject
    private EntityManager entityManager;

    @Override
    protected OrganizationDAO getDAO() {
	return new OrganizationDAO(entityManager);
    }
}