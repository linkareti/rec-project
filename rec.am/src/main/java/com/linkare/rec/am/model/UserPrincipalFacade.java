package com.linkare.rec.am.model;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * 
 * @author Joao
 */
@Stateless
public class UserPrincipalFacade extends Facade<UserPrincipal> {

    @Override
    public void create(UserPrincipal userPrincipal) {
	em.persist(userPrincipal);
    }

    @Override
    public void edit(UserPrincipal userPrincipal) {
	em.merge(userPrincipal);
    }

    @Override
    public void remove(UserPrincipal userPrincipal) {
	em.remove(em.merge(userPrincipal));
    }

    @Override
    public UserPrincipal find(Object id) {
	UserPrincipal userPrincipal = em.find(UserPrincipal.class, id);
	userPrincipal.getGroups();
	return userPrincipal;
    }

    public UserPrincipal findByUsername(final String username) {
	UserPrincipal userPrincipal = (UserPrincipal) em.createNamedQuery("UserPrincipal.findByUsername").setParameter("username", username).getSingleResult();
	userPrincipal.getGroups();
	return userPrincipal;
    }

    @Override
    public List<UserPrincipal> findAll() {
	final Query query = em.createNamedQuery("UserPrincipal.findAll");
	return query.getResultList();
    }

    @Override
    public List<UserPrincipal> findRange(int[] range) {
	final Query query = em.createNamedQuery("UserPrincipal.findAll");
	query.setMaxResults(range[1] - range[0]);
	query.setFirstResult(range[0]);
	return query.getResultList();
    }

    @Override
    public int count() {
	final Query query = em.createNamedQuery("UserPrincipal.countAll");
	return ((Long) query.getSingleResult()).intValue();
    }
}