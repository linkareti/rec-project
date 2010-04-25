package com.linkare.rec.am.model;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.linkare.commons.jpa.security.Group;
import com.linkare.commons.jpa.security.User;

/**
 * 
 * @author Joao
 */
@Stateless
public class GroupFacade extends Facade<Group> {

    @EJB
    private UserFacade userFacade;

    @Override
    public void create(Group group) {
	em.persist(group);
    }

    @Override
    public void edit(Group group) {
	em.merge(group);
    }

    @Override
    public void remove(Group group) {
	em.remove(em.merge(group));
    }

    @Override
    public Group find(Object id) {
	Group group = em.find(Group.class, id);
	// FIXME: Remove this dummy invocation....
	group.getAllUsers();
	return group;
    }

    @Override
    public List<Group> findAll() {
	final Query query = em.createNamedQuery("Group.findAll");
	return query.getResultList();
    }

    @Override
    public List<Group> findRange(int[] range) {
	final Query query = em.createNamedQuery("Group.findAll");
	query.setMaxResults(range[1] - range[0]);
	query.setFirstResult(range[0]);
	final List<Group> result = query.getResultList();
	return result;
    }

    @Override
    public int count() {
	final Query query = em.createNamedQuery("Group.countAll");
	return ((Long) query.getSingleResult()).intValue();
    }

    public List<User> getNonMembers(final List<User> members) {
	final List<User> result = userFacade.findAll();
	result.removeAll(members);
	return result;
    }

    public Group setUsersMembership(final Group group, final List<User> users) {
	final Group mergedGroup = em.merge(group);
	mergedGroup.setChildPrincipalsMembership(users);
	return mergedGroup;
    }
}