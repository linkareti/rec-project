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
public class ExperimentFacade extends Facade<Experiment> {

    @Override
    public void create(Experiment experiment) {
        em.persist(experiment);
    }

    @Override
    public void edit(Experiment experiment) {
        em.merge(experiment);
    }

    @Override
    public void remove(Experiment experiment) {
        em.remove(em.merge(experiment));
    }

    @Override
    public Experiment find(Object id) {
        return em.find(Experiment.class, id);
    }

    @Override
    public List<Experiment> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Experiment.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Experiment> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Experiment.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Experiment> rt = cq.from(Experiment.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}