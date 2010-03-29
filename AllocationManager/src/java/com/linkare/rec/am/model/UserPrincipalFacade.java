package com.linkare.rec.am.model;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Joao
 */
@Stateless
public class UserPrincipalFacade extends Facade<UserPrincipal> {

    @Override
    public void create(UserPrincipal userPrincipal) {
        em.persist(userPrincipal);
    }

    @Override
    public void edit(UserPrincipal userPrincipal) {
        em.merge(userPrincipal);
    }

    @Override
    public void remove(UserPrincipal userPrincipal) {
        em.remove(em.merge(userPrincipal));
    }

    @Override
    public UserPrincipal find(Object id) {
        return em.find(UserPrincipal.class, id);
    }

    @Override
    public List<UserPrincipal> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(UserPrincipal.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<UserPrincipal> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(UserPrincipal.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<UserPrincipal> rt = cq.from(UserPrincipal.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}