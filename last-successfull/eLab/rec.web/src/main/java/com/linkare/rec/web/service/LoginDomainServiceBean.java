package com.linkare.rec.web.service;

import static com.linkare.rec.web.model.LoginDomain.COUNT_ALL_QUERYNAME;
import static com.linkare.rec.web.model.LoginDomain.FIND_ALL_QUERYNAME;
import static com.linkare.rec.web.model.LoginDomain.FIND_BY_NAME_QUERYNAME;
import static com.linkare.rec.web.model.LoginDomain.QUERY_PARAM_NAME;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.linkare.commons.utils.StringUtils;
import com.linkare.rec.web.model.LoginDomain;
import com.linkare.rec.web.util.ConstantUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@Local(LoginDomainServiceLocal.class)
@Stateless(name = "LoginDomainService")
public class LoginDomainServiceBean extends BusinessServiceBean<LoginDomain, Long> implements LoginDomainService {

    @Override
    public LoginDomain find(final Long id) {
	return getEntityManager().find(LoginDomain.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LoginDomain> findAll() {
	final Query query = getEntityManager().createNamedQuery("LoginDomain.findAll");
	return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LoginDomain> findRange(final int[] range) {
	final Query query = getEntityManager().createNamedQuery(FIND_ALL_QUERYNAME);
	query.setMaxResults(range[1] - range[0]);
	query.setFirstResult(range[0]);
	return query.getResultList();
    }

    @Override
    public int count() {
	final Query query = getEntityManager().createNamedQuery(COUNT_ALL_QUERYNAME);
	return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public LoginDomain findByName(final String name) {
	try {
	    return (LoginDomain) getEntityManager().createNamedQuery(FIND_BY_NAME_QUERYNAME)
						   .setParameter(QUERY_PARAM_NAME, StringUtils.isBlank(name) ? ConstantUtils.INTERNAL_DOMAIN_NAME : name)
						   .getSingleResult();
	} catch (NoResultException e) {
	    return null;
	}

    }
}