package com.linkare.irn.nascimento.web.datamodel.geographic;

import com.linkare.irn.nascimento.dao.geographic.DistrictDAO;
import com.linkare.irn.nascimento.model.geographic.District;
import com.linkare.irn.nascimento.web.datamodel.GenericLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class DistrictLazyDataModel extends GenericLazyDataModel<District, DistrictDAO> {

    private static final long serialVersionUID = 1L;

    /**
     * @param dao
     * @param pageSize
     */
    public DistrictLazyDataModel(DistrictDAO dao, int pageSize) {
	super(dao, pageSize);
    }

    /**
     * @param dao
     */
    public DistrictLazyDataModel(DistrictDAO dao) {
	super(dao);
    }
}