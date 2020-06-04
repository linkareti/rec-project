package com.linkare.irn.nascimento.service.core;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.linkare.irn.nascimento.dao.core.CounterDAO;
import com.linkare.irn.nascimento.model.core.Counter;
import com.linkare.irn.nascimento.service.BaseStatelessServiceImpl;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CounterService extends BaseStatelessServiceImpl<Counter, CounterDAO> {

    @Override
    public CounterDAO getDAO() {
	return new CounterDAO(getEntityManager());
    }

    public Counter findByCode(final String code) {
	return getDAO().findByCode(code);
    }
}