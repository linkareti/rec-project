package com.linkare.rec.am.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.linkare.rec.am.model.LoginDomain;

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
	final Query query = getEntityManager().createNamedQuery("LoginDomain.findAll");
	query.setMaxResults(range[1] - range[0]);
	query.setFirstResult(range[0]);
	return query.getResultList();
    }

    @Override
    public int count() {
	final Query query = getEntityManager().createNamedQuery("LoginDomain.countAll");
	return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    public LoginDomain findByName(final String name) {
	if (name == null) {
	    return null;
	}
	try {
	    return (LoginDomain) getEntityManager().createNamedQuery("LoginDomain.findByName").setParameter("name", name).getSingleResult();
	} catch (NoResultException e) {
	    return null;
	}

    }
}