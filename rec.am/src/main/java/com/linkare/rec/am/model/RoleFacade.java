package com.linkare.rec.am.model;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.linkare.commons.jpa.security.Role;
import com.linkare.commons.jpa.security.User;

/**
 * 
 * @author Joao
 */
@Stateless
public class RoleFacade extends Facade<Role> {

    @EJB
    private UserFacade userFacade;

    @Override
    public void create(Role role) {
	em.persist(role);
    }

    @Override
    public void edit(Role role) {
	em.merge(role);
    }

    @Override
    public void remove(Role role) {
	em.remove(em.merge(role));
    }

    @Override
    public Role find(Object id) {
	Role role = em.find(Role.class, id);
	role.getAllChildren();
	return role;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> findAll() {
	final Query query = em.createNamedQuery("Role.findAll");
	return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> findRange(int[] range) {
	final Query query = em.createNamedQuery("Role.findAll");
	query.setMaxResults(range[1] - range[0]);
	query.setFirstResult(range[0]);
	final List<Role> result = query.getResultList();
	return result;
    }

    @Override
    public int count() {
	final Query query = em.createNamedQuery("Role.countAll");
	return ((Long) query.getSingleResult()).intValue();
    }

    public List<User> getNonMembers(final List<User> members) {
	final List<User> result = userFacade.findAll();
	result.removeAll(members);
	return result;
    }

    public Role setUsersMembership(final Role role, final List<User> users) {
	final Role mergedRole = em.merge(role);
	mergedRole.setChildPrincipalsMembership(users);
	return mergedRole;
    }
}