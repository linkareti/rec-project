package com.linkare.rec.am.model;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@Stateless(name = "LoginDomainFacade")
public class LoginDomainFacade extends Facade<LoginDomain, Long> {

    @Override
    public LoginDomain find(final Long id) {
	LoginDomain loginDomain = getEntityManager().find(LoginDomain.class, id);
	return loginDomain;
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
}