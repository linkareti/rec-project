package com.linkare.rec.am.model;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.linkare.commons.dao.security.UserDAO;
import com.linkare.commons.jpa.security.User;

/**
 * 
 * @author Joao
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserFacade extends Facade<User> {

    private UserDAO userDAO;

    private UserDAO getOrCreateDAO() {
	if (userDAO == null) {
	    userDAO = new UserDAO(em);
	}
	return userDAO;
    }

    @Override
    public void create(User user) {
	getOrCreateDAO().create(user);
    }

    @Override
    public void edit(User user) {
	getOrCreateDAO().edit(user);
    }

    @Override
    public void remove(User user) {
	getOrCreateDAO().remove(user);
    }

    @Override
    public User find(Object id) {
	return getOrCreateDAO().find(id);
    }

    public User findByUsername(final String username) {
	return getOrCreateDAO().findByUsername(username);
    }

    @Override
    public List<User> findRange(int[] range) {
	return getOrCreateDAO().findRange(range);
    }

    @Override
    public List<User> findAll() {
	return getOrCreateDAO().findAll();
    }

    @Override
    public int count() {
	return getOrCreateDAO().count();
    }

    public User authenticate(final String username, final String password) {
	return getOrCreateDAO().authenticate(username, password);
    }
}