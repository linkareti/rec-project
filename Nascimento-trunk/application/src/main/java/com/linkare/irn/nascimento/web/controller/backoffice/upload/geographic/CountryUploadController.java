package com.linkare.irn.nascimento.web.controller.backoffice.upload.geographic;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import com.linkare.irn.nascimento.dao.geographic.CountryDAO;
import com.linkare.irn.nascimento.model.DomainException;
import com.linkare.irn.nascimento.model.ext.mapping.geographic.CountryDTO;
import com.linkare.irn.nascimento.model.geographic.Country;
import com.linkare.irn.nascimento.service.geographic.CountryService;
import com.linkare.irn.nascimento.service.mapping.IMapperProcessor;
import com.linkare.irn.nascimento.util.JsfUtils;
import com.linkare.irn.nascimento.web.controller.backoffice.upload.BaseFileUploadController;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;
import com.linkare.irn.nascimento.web.datamodel.geographic.CountryLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Named
@ViewScoped
public class CountryUploadController extends BaseFileUploadController<Country, CountryDTO, CountryDAO> {

    private static final long serialVersionUID = -560744945272669439L;

    private GenericLazyDataModel<Country, CountryDAO> dataModel;

    @Inject
    private CountryService countryService;

    @PostConstruct
    @Override
    public void init() {
	super.init();
	this.dataModel = new CountryLazyDataModel(countryService.getDAO());
    }

    @Override
    public long getNumberOfExistingRecords() {
	return countryService.count();
    }

    @Override
    public String getSpecificModuleGlobalMessagesClientId() {
	return "globalCountrySpecificMessages";
    }

    @Override
    public GenericLazyDataModel<Country, CountryDAO> getDataModel() {
	return dataModel;
    }

    public void onRowEdit(RowEditEvent event) throws DomainException {
	final Country country = (Country) event.getObject();
	countryService.update(country);
	JsfUtils.addGlobalInfoMessage(getMessage("message.info.countryUpdated", country.getName()));
    }

    @Override
    protected String getDataListComponentId() {
	return "countriesTable";
    }

    @Override
    public IMapperProcessor<Country, CountryDTO, CountryDAO> getMapperProcessor() {
	return countryService;
    }

    @Override
    public String getModuleName() {
	return Country.class.getSimpleName();
    }
}