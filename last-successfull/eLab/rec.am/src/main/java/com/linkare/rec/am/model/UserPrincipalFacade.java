package com.linkare.rec.am.model;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import com.linkare.commons.jpa.security.Login;
import com.linkare.commons.utils.PasswordEncryptor;

/**
 * 
 * @author Joao
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
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

    public UserPrincipal authenticate(final String username, final String password) {
	UserPrincipal user = null;
	try {
	    user = findByUsername(username);
	    final Login login = user.getLogin();
	    if (login == null || !PasswordEncryptor.areEquals(login.getPassword(), password)) {
		return null;
	    }
	    user.getAllParentRoles();
	} catch (RuntimeException e) {
	    e.printStackTrace();
	}
	return user;
    }
}