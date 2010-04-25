package com.linkare.rec.am.model;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.linkare.commons.jpa.exceptions.DomainException;
import com.linkare.commons.jpa.security.Login;
import com.linkare.commons.jpa.security.User;
import com.linkare.commons.utils.PasswordEncryptor;

/**
 * 
 * @author Joao
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserFacade extends Facade<User> {

    @Override
    public void create(User user) {
	final User otherUser = findByUsername(user.getUsername());
	if (otherUser != null) {
	    throw new DomainException("error.username.already.existing");
	}
	em.persist(user);
    }

    @Override
    public void edit(User user) {
	em.merge(user);
    }

    @Override
    public void remove(User user) {
	em.remove(em.merge(user));
    }

    @Override
    public User find(Object id) {
	try {
	    User user = em.find(User.class, id);
	    user.getAllParentGroups();
	    return user;
	} catch (NoResultException e) {
	    return null;
	}
    }

    public User findByUsername(final String username) {
	try {
	    User user = (User) em.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
	    user.getAllParentGroups();
	    return user;
	} catch (NoResultException e) {
	    return null;
	}
    }

    @Override
    public List<User> findRange(int[] range) {
	return find(false, range[0], range[1]);
    }

    @Override
    public List<User> findAll() {
	return find(true, -1, -1);
    }

    @SuppressWarnings("unchecked")
    public List<User> find(boolean all, int firstResult, int maxResults) {
	Query q = em.createNamedQuery("User.findAll");
	if (!all) {
	    q.setMaxResults(maxResults);
	    q.setFirstResult(firstResult);
	}
	return q.getResultList();
    }

    @Override
    public int count() {
	final Query query = em.createNamedQuery("User.countAll");
	return ((Long) query.getSingleResult()).intValue();
    }

    public User authenticate(final String username, final String password) {
	User user = null;
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