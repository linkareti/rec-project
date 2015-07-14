package com.linkare.rec.web.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.linkare.commons.dao.security.RoleDAO;
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
public class UserServiceBean extends BusinessServiceBean<User, Long> implements
		UserService {

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
		// First remove the user from all direct roles, otherwise a Foreign Key
		// problem occurs in the database
		User userFound = find(user.getIdInternal());
		List<Role> allParentRoles = userFound.getAllParentRoles();
		
		RoleDAO roleDAO = new RoleDAO(getEntityManager());
		for (Role role : allParentRoles) {
			roleDAO.removeUser(role, userFound.id());
		}

		getOrCreateDAO().remove(find(user.getIdInternal()));
	}

	@Override
	public User find(final Long id) {
		final User user = getOrCreateDAO().find(id);
		loadRelations(user);
		return user;
	}

	private void loadRelations(final User user) {
		if (user != null) {
			user.getSubject();
			if (user.hasLogin()) {
				user.getLogin();
			}
		}
	}

	@Override
	public User findByUsername(final String username) {
		final User user = getOrCreateDAO().findByUsername(username);
		loadRelations(user);
		return user;
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
	public User authenticate(final User user, final String password) {
		if (user == null) {
			return null;
		}
		return getOrCreateDAO().authenticate(user, password);
	}

	@Override
	public List<Role> getRoles(final User user) {
		final User mergedUser = getEntityManager().merge(user);
		return mergedUser == null ? Collections.<Role> emptyList() : mergedUser
				.getAllParentRoles();
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