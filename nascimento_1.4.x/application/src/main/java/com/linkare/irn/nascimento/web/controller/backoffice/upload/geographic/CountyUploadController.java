package com.linkare.irn.nascimento.web.controller.backoffice.upload.geographic;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import com.linkare.irn.nascimento.dao.geographic.CountyDAO;
import com.linkare.irn.nascimento.model.DomainException;
import com.linkare.irn.nascimento.model.ext.mapping.geographic.CountyDTO;
import com.linkare.irn.nascimento.model.geographic.County;
import com.linkare.irn.nascimento.service.geographic.CountyService;
import com.linkare.irn.nascimento.service.mapping.IMapperProcessor;
import com.linkare.irn.nascimento.util.JsfUtils;
import com.linkare.irn.nascimento.web.controller.backoffice.upload.BaseFileUploadController;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;
import com.linkare.irn.nascimento.web.datamodel.geographic.CountyLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Named
@ViewScoped
public class CountyUploadController extends BaseFileUploadController<County, CountyDTO, CountyDAO> {

    private static final long serialVersionUID = -560744945272669439L;

    private GenericLazyDataModel<County, CountyDAO> dataModel;

    @Inject
    private CountyService countyService;

    @PostConstruct
    @Override
    public void init() {
	super.init();
	this.dataModel = new CountyLazyDataModel(countyService.getDAO());
    }

    @Override
    public long getNumberOfExistingRecords() {
	return countyService.count();
    }

    @Override
    public String getSpecificModuleGlobalMessagesClientId() {
	return "globalCountySpecificMessages";
    }

    @Override
    public GenericLazyDataModel<County, CountyDAO> getDataModel() {
	return dataModel;
    }

    public void onRowEdit(RowEditEvent event) throws DomainException {
	final County county = (County) event.getObject();
	countyService.update(county);
	JsfUtils.addGlobalInfoMessage(getMessage("message.info.countyUpdated", county.getName()));
    }

    @Override
    protected String getDataListComponentId() {
	return "countysTable";
    }

    @Override
    public IMapperProcessor<County, CountyDTO, CountyDAO> getMapperProcessor() {
	return countyService;
    }

    @Override
    public String getModuleName() {
	return County.class.getSimpleName();
    }
}