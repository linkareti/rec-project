package com.linkare.irn.nascimento.web.datamodel.geographic;

import com.linkare.irn.nascimento.dao.geographic.CountryDAO;
import com.linkare.irn.nascimento.model.geographic.Country;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class CountryLazyDataModel extends GenericLazyDataModel<Country, CountryDAO> {

    private static final long serialVersionUID = 1L;

    /**
     * @param dao
     * @param pageSize
     */
    public CountryLazyDataModel(CountryDAO dao, int pageSize) {
	super(dao, pageSize);
    }

    /**
     * @param dao
     */
    public CountryLazyDataModel(CountryDAO dao) {
	super(dao);
    }
}