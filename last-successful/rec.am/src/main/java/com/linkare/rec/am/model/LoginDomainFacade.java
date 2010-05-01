package com.linkare.rec.am.model;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@Stateless
public class LoginDomainFacade extends Facade<LoginDomain, Long> {

    @Override
    public void create(final LoginDomain loginDomain) {
	em.persist(loginDomain);
    }

    @Override
    public LoginDomain edit(final LoginDomain loginDomain) {
	return em.merge(loginDomain);
    }

    @Override
    public void remove(final LoginDomain loginDomain) {
	em.remove(em.merge(loginDomain));
    }

    @Override
    public LoginDomain find(final Long id) {
	LoginDomain loginDomain = em.find(LoginDomain.class, id);
	return loginDomain;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LoginDomain> findAll() {
	final Query query = em.createNamedQuery("LoginDomain.findAll");
	return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LoginDomain> findRange(final int[] range) {
	final Query query = em.createNamedQuery("LoginDomain.findAll");
	query.setMaxResults(range[1] - range[0]);
	query.setFirstResult(range[0]);
	return query.getResultList();
    }

    @Override
    public int count() {
	final Query query = em.createNamedQuery("LoginDomain.countAll");
	return ((Long) query.getSingleResult()).intValue();
    }
}