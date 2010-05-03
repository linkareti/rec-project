package com.linkare.rec.am.model;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.linkare.commons.dao.security.RoleDAO;
import com.linkare.commons.jpa.security.Role;
import com.linkare.commons.jpa.security.User;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@Stateless(name = "RoleFacade")
public class RoleFacade extends Facade<Role, Long> {

    private RoleDAO roleDAO;

    private RoleDAO getOrCreateDAO() {
	if (roleDAO == null) {
	    roleDAO = new RoleDAO(getEntityManager());
	}
	return roleDAO;
    }

    @Override
    public void create(final Role role) {
	getOrCreateDAO().create(role);
    }

    @Override
    public Role edit(final Role role) {
	return getOrCreateDAO().edit(role);
    }

    @Override
    public void remove(final Role role) {
	getOrCreateDAO().remove(role);
    }

    @Override
    public Role find(final Long id) {
	return getOrCreateDAO().find(id);
    }

    @Override
    public List<Role> findRange(final int[] range) {
	return getOrCreateDAO().findRange(range);
    }

    @Override
    public List<Role> findAll() {
	return getOrCreateDAO().findAll();
    }

    public List<Role> find(final boolean all, final int firstResult, final int maxResults) {
	return getOrCreateDAO().find(all, firstResult, maxResults);
    }

    @Override
    public int count() {
	return getOrCreateDAO().count();
    }

    public List<User> getNonMembers(final List<User> members) {
	return getOrCreateDAO().getNonMembers(members);
    }

    public Role setUsersMembership(final Role role, final List<User> users) {
	return getOrCreateDAO().setUsersMembership(role, mergeUsers(users));
    }

    private List<User> mergeUsers(final List<User> users) {
	final List<User> mergedUsers = new ArrayList<User>();
	for (User user : users) {
	    final User mergedUser = getEntityManager().merge(user);
	    mergedUser.getParents();
	    mergedUsers.add(mergedUser);
	}
	return mergedUsers;
    }
}