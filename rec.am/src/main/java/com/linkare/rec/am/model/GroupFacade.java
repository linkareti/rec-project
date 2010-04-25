package com.linkare.rec.am.model;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import com.linkare.commons.dao.security.GroupDAO;
import com.linkare.commons.jpa.security.Group;
import com.linkare.commons.jpa.security.User;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@Stateless
public class GroupFacade extends Facade<Group> {

    private GroupDAO groupDAO;

    private GroupDAO getOrCreateDAO() {
	if (groupDAO == null) {
	    groupDAO = new GroupDAO(em);
	}
	return groupDAO;
    }

    @Override
    public void create(Group group) {
	getOrCreateDAO().create(group);
    }

    @Override
    public void edit(Group group) {
	getOrCreateDAO().edit(group);
    }

    @Override
    public void remove(Group group) {
	getOrCreateDAO().remove(group);
    }

    @Override
    public Group find(Object id) {
	return getOrCreateDAO().find(id);
    }

    @Override
    public List<Group> findRange(int[] range) {
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

    public List<User> getNonMembers(final List<User> members) {
	return getOrCreateDAO().getNonMembers(members);
    }

    public Group setUsersMembership(final Group group, final List<User> users) {
	return getOrCreateDAO().setUsersMembership(group, mergeUsers(users));
    }

    private List<User> mergeUsers(final List<User> users) {
	final List<User> mergedUsers = new ArrayList<User>();
	for (User user : users) {
	    final User mergedUser = em.merge(user);
	    mergedUser.getParents();
	    mergedUsers.add(mergedUser);
	}
	return mergedUsers;
    }
}