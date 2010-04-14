package com.linkare.rec.am.model;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.linkare.commons.jpa.security.User;

/**
 * 
 * @author Joao
 */
@Stateless
public class UserGroupFacade extends Facade<UserGroup> {

    @EJB
    private UserPrincipalFacade userFacade;

    @Override
    public void create(UserGroup userGroup) {
	em.persist(userGroup);
    }

    @Override
    public void edit(UserGroup userGroup) {
	em.merge(userGroup);
    }

    @Override
    public void remove(UserGroup userGroup) {
	em.remove(em.merge(userGroup));
    }

    @Override
    public UserGroup find(Object id) {
	UserGroup userGroup = em.find(UserGroup.class, id);
	// FIXME: Remove this dummy invocation....
	userGroup.getMembers();
	return userGroup;
    }

    @Override
    public List<UserGroup> findAll() {
	final Query query = em.createNamedQuery("UserGroup.findAll");
	return query.getResultList();
    }

    @Override
    public List<UserGroup> findRange(int[] range) {
	final Query query = em.createNamedQuery("UserGroup.findAll");
	query.setMaxResults(range[1] - range[0]);
	query.setFirstResult(range[0]);
	final List<UserGroup> result = query.getResultList();
	return result;
    }

    @Override
    public int count() {
	final Query query = em.createNamedQuery("UserGroup.countAll");
	return ((Long) query.getSingleResult()).intValue();
    }

    public List<UserPrincipal> getNonMembers(final List<User> members) {
	final List<UserPrincipal> result = userFacade.findAll();
	result.removeAll(members);
	return result;
    }

    public void addUser(final UserGroup group, final Long userId) {
	em.merge(group);
	UserPrincipal user = userFacade.find(userId);
	group.addDirectChild(user);
	em.merge(group);
    }

    public void removeUser(final UserGroup group, final Long userId) {
	UserPrincipal user = userFacade.find(userId);
	group.removeDirectChild(user);
	em.merge(group);
    }
}