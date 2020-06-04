package com.linkare.irn.nascimento.web.converter.geographic;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.linkare.irn.nascimento.dao.geographic.CountryDAO;
import com.linkare.irn.nascimento.model.geographic.Country;
import com.linkare.irn.nascimento.web.converter.GenericConverter;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Named
public class CountryConverter extends GenericConverter<Country, CountryDAO> {

    @Inject
    private EntityManager entityManager;

    @Override
    protected CountryDAO getDAO() {
	return new CountryDAO(entityManager);
    }

}