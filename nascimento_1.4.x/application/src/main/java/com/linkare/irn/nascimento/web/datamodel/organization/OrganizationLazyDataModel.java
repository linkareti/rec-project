package com.linkare.irn.nascimento.web.datamodel.organization;

import com.linkare.irn.nascimento.dao.organization.OrganizationDAO;
import com.linkare.irn.nascimento.model.organization.Organization;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class OrganizationLazyDataModel extends GenericLazyDataModel<Organization, OrganizationDAO> {

    private static final long serialVersionUID = 1L;

    /**
     * @param dao
     * @param pageSize
     */
    public OrganizationLazyDataModel(OrganizationDAO dao, int pageSize) {
	super(dao, pageSize);
    }

    /**
     * @param dao
     */
    public OrganizationLazyDataModel(OrganizationDAO dao) {
	super(dao);
    }
}