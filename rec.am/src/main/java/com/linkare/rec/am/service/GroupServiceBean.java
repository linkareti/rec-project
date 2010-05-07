package com.linkare.rec.am.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.linkare.commons.dao.security.GroupDAO;
import com.linkare.commons.jpa.security.Group;
import com.linkare.commons.jpa.security.User;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@Local(GroupServiceLocal.class)
@Stateless(name = "GroupService")
public class GroupServiceBean extends BusinessServiceBean<Group, Long> implements GroupService {

    private GroupDAO groupDAO;

    private GroupDAO getOrCreateDAO() {
	if (groupDAO == null) {
	    groupDAO = new GroupDAO(getEntityManager());
	}
	return groupDAO;
    }

    @Override
    public void create(final Group group) {
	getOrCreateDAO().create(group);
    }

    @Override
    public Group edit(final Group group) {
	return getOrCreateDAO().edit(group);
    }

    @Override
    public void remove(final Group group) {
	getOrCreateDAO().remove(group);
    }

    @Override
    public Group find(final Long id) {
	return getOrCreateDAO().find(id);
    }

    @Override
    public List<Group> findRange(final int[] range) {
	return getOrCreateDAO().findRange(range);
    }

    @Override
    public List<Group> findAll() {
	return getOrCreateDAO().findAll();
    }

    @Override
    public int count() {
	return getOrCreateDAO().count();
    }

    @Override
    public List<User> getNonMembers(final Group group) {
	return group == null ? Collections.<User> emptyList() : getOrCreateDAO().getNonMembers(group.getAllUsers());
    }

    @Override
    public Group setUsersMembership(final Group group, final List<User> users) {
	return getOrCreateDAO().setUsersMembership(group, mergeUsers(users));
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