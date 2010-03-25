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
public class LaboratoryFacade {

    @PersistenceContext(unitName = "AllocationManagerPU")
    private EntityManager em;

    public void create(Laboratory laboratory) {
        em.persist(laboratory);
    }

    public void edit(Laboratory laboratory) {
        em.merge(laboratory);
    }

    public void remove(Laboratory laboratory) {
        em.remove(em.merge(laboratory));
    }

    public Laboratory find(Object id) {
        return em.find(Laboratory.class, id);
    }

    public List<Laboratory> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Laboratory.class));
        return em.createQuery(cq).getResultList();
    }

    public List<Laboratory> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Laboratory.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Laboratory> rt = cq.from(Laboratory.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
