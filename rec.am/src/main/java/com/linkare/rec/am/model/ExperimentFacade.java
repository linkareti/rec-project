package com.linkare.rec.am.model;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

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
    public List<Experiment> findRange(int[] range) {
	return find(false, range[0], range[1]);
    }

    @Override
    public List<Experiment> findAll() {
	return find(true, -1, -1);
    }

    @SuppressWarnings("unchecked")
    public List<Experiment> find(boolean all, int firstResult, int maxResults) {
	Query q = em.createNamedQuery("Experiment.findAll");
	if (!all) {
	    q.setMaxResults(maxResults);
	    q.setFirstResult(firstResult);
	}
	return q.getResultList();
    }

    @Override
    public int count() {
	final Query query = em.createNamedQuery("Experiment.countAll");
	return ((Long) query.getSingleResult()).intValue();
    }
}