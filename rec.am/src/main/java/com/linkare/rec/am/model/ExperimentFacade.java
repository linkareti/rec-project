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

    @SuppressWarnings("unchecked")
    @Override
    public List<Experiment> findAll() {
	final Query query = em.createNamedQuery("Experiment.findAll");
	return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Experiment> findRange(int[] range) {
	final Query query = em.createNamedQuery("Experiment.findAll");
	query.setMaxResults(range[1] - range[0]);
	query.setFirstResult(range[0]);
	return query.getResultList();
    }

    @Override
    public int count() {
	final Query query = em.createNamedQuery("Experiment.countAll");
	return ((Long) query.getSingleResult()).intValue();
    }
}