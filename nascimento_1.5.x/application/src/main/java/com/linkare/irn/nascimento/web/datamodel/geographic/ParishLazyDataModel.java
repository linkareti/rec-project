package com.linkare.irn.nascimento.web.datamodel.geographic;

import com.linkare.irn.nascimento.dao.geographic.ParishDAO;
import com.linkare.irn.nascimento.model.geographic.Parish;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ParishLazyDataModel extends GenericLazyDataModel<Parish, ParishDAO> {

    private static final long serialVersionUID = 1L;

    /**
     * @param dao
     * @param pageSize
     */
    public ParishLazyDataModel(ParishDAO dao, int pageSize) {
	super(dao, pageSize);
    }

    /**
     * @param dao
     */
    public ParishLazyDataModel(ParishDAO dao) {
	super(dao);
    }
}