package com.linkare.irn.nascimento.web.converter.geographic;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.linkare.irn.nascimento.dao.geographic.DistrictDAO;
import com.linkare.irn.nascimento.model.geographic.District;
import com.linkare.irn.nascimento.web.converter.GenericConverter;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Named
public class DistrictConverter extends GenericConverter<District, DistrictDAO> {

    @Inject
    private EntityManager entityManager;

    @Override
    protected DistrictDAO getDAO() {
	return new DistrictDAO(entityManager);
    }

}