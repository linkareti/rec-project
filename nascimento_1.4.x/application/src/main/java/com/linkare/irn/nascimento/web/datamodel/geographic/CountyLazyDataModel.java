package com.linkare.irn.nascimento.web.datamodel.geographic;

import com.linkare.irn.nascimento.dao.geographic.CountyDAO;
import com.linkare.irn.nascimento.model.geographic.County;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class CountyLazyDataModel extends GenericLazyDataModel<County, CountyDAO> {

    private static final long serialVersionUID = 1L;

    /**
     * @param dao
     * @param pageSize
     */
    public CountyLazyDataModel(CountyDAO dao, int pageSize) {
	super(dao, pageSize);
    }

    /**
     * @param dao
     */
    public CountyLazyDataModel(CountyDAO dao) {
	super(dao);
    }
}