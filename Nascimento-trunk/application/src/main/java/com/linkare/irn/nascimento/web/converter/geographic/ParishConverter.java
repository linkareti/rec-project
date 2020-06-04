package com.linkare.irn.nascimento.web.converter.geographic;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.linkare.irn.nascimento.dao.geographic.ParishDAO;
import com.linkare.irn.nascimento.model.geographic.Parish;
import com.linkare.irn.nascimento.web.converter.GenericConverter;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Named
public class ParishConverter extends GenericConverter<Parish, ParishDAO> {

    @Inject
    private EntityManager entityManager;

    @Override
    protected ParishDAO getDAO() {
	return new ParishDAO(entityManager);
    }

}