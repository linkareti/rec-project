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
public class ExperimentFacade {

    @PersistenceContext(unitName = "AllocationManagerPU")
    private EntityManager em;

    public final void create(Experiment experiment) {
        em.persist(experiment);
    }

    public final void edit(Experiment experiment) {
        em.merge(experiment);
    }

    public final void remove(Experiment experiment) {
        em.remove(em.merge(experiment));
    }

    public final Experiment find(Object id) {
        return em.find(Experiment.class, id);
    }

    public final List<Experiment> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Experiment.class));
        return em.createQuery(cq).getResultList();
    }

    public final List<Experiment> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Experiment.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public final int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Experiment> rt = cq.from(Experiment.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
