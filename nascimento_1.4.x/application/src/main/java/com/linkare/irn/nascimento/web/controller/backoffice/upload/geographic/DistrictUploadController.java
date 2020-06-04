package com.linkare.irn.nascimento.web.controller.backoffice.upload.geographic;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import com.linkare.irn.nascimento.dao.geographic.DistrictDAO;
import com.linkare.irn.nascimento.model.DomainException;
import com.linkare.irn.nascimento.model.ext.mapping.geographic.DistrictDTO;
import com.linkare.irn.nascimento.model.geographic.District;
import com.linkare.irn.nascimento.service.geographic.DistrictService;
import com.linkare.irn.nascimento.service.mapping.IMapperProcessor;
import com.linkare.irn.nascimento.util.JsfUtils;
import com.linkare.irn.nascimento.web.controller.backoffice.upload.BaseFileUploadController;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;
import com.linkare.irn.nascimento.web.datamodel.geographic.DistrictLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Named
@ViewScoped
public class DistrictUploadController extends BaseFileUploadController<District, DistrictDTO, DistrictDAO> {

    private static final long serialVersionUID = -560744945272669439L;

    private GenericLazyDataModel<District, DistrictDAO> dataModel;

    @Inject
    private DistrictService districtService;

    @PostConstruct
    @Override
    public void init() {
	super.init();
	this.dataModel = new DistrictLazyDataModel(districtService.getDAO());
    }

    @Override
    public long getNumberOfExistingRecords() {
	return districtService.count();
    }

    @Override
    public String getSpecificModuleGlobalMessagesClientId() {
	return "globalDistrictSpecificMessages";
    }

    @Override
    public GenericLazyDataModel<District, DistrictDAO> getDataModel() {
	return dataModel;
    }

    public void onRowEdit(RowEditEvent event) throws DomainException {
	final District district = (District) event.getObject();
	districtService.update(district);
	JsfUtils.addGlobalInfoMessage(getMessage("message.info.districtUpdated", district.getName()));
    }

    @Override
    protected String getDataListComponentId() {
	return "districtsTable";
    }

    @Override
    public IMapperProcessor<District, DistrictDTO, DistrictDAO> getMapperProcessor() {
	return districtService;
    }

    @Override
    public String getModuleName() {
	return District.class.getSimpleName();
    }
}