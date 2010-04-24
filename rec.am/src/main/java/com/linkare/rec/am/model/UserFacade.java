package com.linkare.rec.am.model;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
	User user = em.find(User.class, id);
	if (user != null) {
	    user.getAllParentGroups();
	    return user;
	}
	return null;
    }

    public User findByUsername(final String username) {
	User user = (User) em.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
	if (user != null) {
	    user.getAllParentGroups();
	    return user;
	}
	return null;
    }

    @Override
    public List<User> findAll() {
	final Query query = em.createNamedQuery("User.findAll");
	return query.getResultList();
    }

    @Override
    public List<User> findRange(int[] range) {
	final Query query = em.createNamedQuery("User.findAll");
	query.setMaxResults(range[1] - range[0]);
	query.setFirstResult(range[0]);
	return query.getResultList();
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