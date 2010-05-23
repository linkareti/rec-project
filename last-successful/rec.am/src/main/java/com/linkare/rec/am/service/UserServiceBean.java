package com.linkare.rec.am.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.linkare.commons.dao.security.UserDAO;
import com.linkare.commons.jpa.security.Role;
import com.linkare.commons.jpa.security.User;
import com.linkare.zas.annotation.AccessControlled;

/**
 * 
 * @author Joao
 */
@Local(UserServiceLocal.class)
@Stateless(name = "UserService")
public class UserServiceBean extends BusinessServiceBean<User, Long> implements UserService {

    private UserDAO userDAO;

    private UserDAO getOrCreateDAO() {
	if (userDAO == null) {
	    userDAO = new UserDAO(getEntityManager());
	}
	return userDAO;
    }

    @Override
    public void create(final User user) {
	getOrCreateDAO().create(user);
    }

    @Override
    public User edit(final User user) {
	return getOrCreateDAO().edit(user);
    }

    @AccessControlled("$currentSubject.isAdmin()")
    @Override
    public void remove(final User user) {
	getOrCreateDAO().remove(user);
    }

    @Override
    public User find(final Long id) {
	return getOrCreateDAO().find(id);
    }

    @Override
    public User findByUsername(final String username) {
	return getOrCreateDAO().findByUsername(username);
    }

    @Override
    public List<User> findRange(final int[] range) {
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

    @Override
    public User authenticate(final String username, final String password) {
	return getOrCreateDAO().authenticate(username, password);
    }

    @Override
    public List<Role> getRoles(final User user) {
	final User mergedUser = getEntityManager().merge(user);
	return mergedUser == null ? Collections.<Role> emptyList() : mergedUser.getAllParentRoles();
    }

    @Override
    public List<User> getOrCreateUsers(List<String> usernames) {
	final List<User> result = new ArrayList<User>();
	for (final String username : usernames) {
	    User user = findByUsername(username);
	    if (user == null) {
		user = new User(username);
		create(user);
	    }
	    result.add(user);
	}
	return result;
    }

    @Override
    public void registerExternalUser(final String username) {
	final User existingUser = findByUsername(username);
	if (existingUser == null) {
	    User user = new User(username);
	    create(user);
	}
    }
}