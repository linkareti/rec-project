/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.am.model;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Joao
 */
@Stateless
public class UserGroupFacade {
    @PersistenceContext(unitName = "AllocationManagerPU")
    private EntityManager em;

    public void create(UserGroup userGroup) {
        em.persist(userGroup);
    }

    public void edit(UserGroup userGroup) {
        em.merge(userGroup);
    }

    public void remove(UserGroup userGroup) {
        em.remove(em.merge(userGroup));
    }

    public UserGroup find(Object id) {
        return em.find(UserGroup.class, id);
    }

    public List<UserGroup> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(UserGroup.class));
        return em.createQuery(cq).getResultList();
    }

    public List<UserGroup> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(UserGroup.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<UserGroup> rt = cq.from(UserGroup.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
