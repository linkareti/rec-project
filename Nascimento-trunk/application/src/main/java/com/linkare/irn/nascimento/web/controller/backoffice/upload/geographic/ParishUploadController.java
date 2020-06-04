package com.linkare.irn.nascimento.web.controller.backoffice.upload.geographic;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import com.linkare.irn.nascimento.dao.geographic.ParishDAO;
import com.linkare.irn.nascimento.model.DomainException;
import com.linkare.irn.nascimento.model.ext.mapping.geographic.ParishDTO;
import com.linkare.irn.nascimento.model.geographic.Parish;
import com.linkare.irn.nascimento.service.geographic.ParishService;
import com.linkare.irn.nascimento.service.mapping.IMapperProcessor;
import com.linkare.irn.nascimento.util.JsfUtils;
import com.linkare.irn.nascimento.web.controller.backoffice.upload.BaseFileUploadController;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;
import com.linkare.irn.nascimento.web.datamodel.geographic.ParishLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Named
@ViewScoped
public class ParishUploadController extends BaseFileUploadController<Parish, ParishDTO, ParishDAO> {

    private static final long serialVersionUID = -560744945272669439L;

    private GenericLazyDataModel<Parish, ParishDAO> dataModel;

    @Inject
    private ParishService parishService;

    @PostConstruct
    @Override
    public void init() {
	super.init();
	this.dataModel = new ParishLazyDataModel(parishService.getDAO());
    }

    @Override
    public long getNumberOfExistingRecords() {
	return parishService.count();
    }

    @Override
    public String getSpecificModuleGlobalMessagesClientId() {
	return "globalParishSpecificMessages";
    }

    @Override
    public GenericLazyDataModel<Parish, ParishDAO> getDataModel() {
	return dataModel;
    }

    public void onRowEdit(RowEditEvent event) throws DomainException {
	final Parish parish = (Parish) event.getObject();
	parishService.update(parish);
	JsfUtils.addGlobalInfoMessage(getMessage("message.info.parishUpdated", parish.getName()));
    }

    @Override
    protected String getDataListComponentId() {
	return "parishesTable";
    }

    @Override
    public IMapperProcessor<Parish, ParishDTO, ParishDAO> getMapperProcessor() {
	return parishService;
    }

    @Override
    public String getModuleName() {
	return Parish.class.getSimpleName();
    }
}