package com.linkare.irn.nascimento.web.controller.backoffice.upload.organization;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import com.linkare.irn.nascimento.dao.organization.OrganizationDAO;
import com.linkare.irn.nascimento.model.DomainException;
import com.linkare.irn.nascimento.model.ext.mapping.organization.OrganizationDTO;
import com.linkare.irn.nascimento.model.organization.Organization;
import com.linkare.irn.nascimento.service.mapping.IMapperProcessor;
import com.linkare.irn.nascimento.service.organization.OrganizationService;
import com.linkare.irn.nascimento.util.JsfUtils;
import com.linkare.irn.nascimento.web.controller.backoffice.upload.BaseFileUploadController;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;
import com.linkare.irn.nascimento.web.datamodel.organization.OrganizationLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Named
@ViewScoped
public class OrganizationUploadController
		extends BaseFileUploadController<Organization, OrganizationDTO, OrganizationDAO> {

	private static final long serialVersionUID = -560744945272669439L;

	private GenericLazyDataModel<Organization, OrganizationDAO> dataModel;

	@Inject
	private OrganizationService organizationService;

	@PostConstruct
	@Override
	public void init() {
		super.init();
		this.dataModel = new OrganizationLazyDataModel(organizationService.getDAO());
	}

	@Override
	public long getNumberOfExistingRecords() {
		return organizationService.count();
	}

	@Override
	public String getSpecificModuleGlobalMessagesClientId() {
		return "globalOrganizationSpecificMessages";
	}

	@Override
	public GenericLazyDataModel<Organization, OrganizationDAO> getDataModel() {
		return dataModel;
	}

	public void onRowEdit(RowEditEvent event) throws DomainException {
		final Organization organization = (Organization) event.getObject();
		organizationService.update(organization);
		JsfUtils.addGlobalInfoMessage(getMessage("message.info.organizationUpdated", organization.getName()));
	}

	@Override
	protected String getDataListComponentId() {
		return "organizationsTable";
	}

	@Override
	public IMapperProcessor<Organization, OrganizationDTO, OrganizationDAO> getMapperProcessor() {
		return organizationService;
	}

	@Override
	public String getModuleName() {
		return Organization.class.getSimpleName();
	}
}