package com.linkare.rec.am.model;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * 
 * @author Joao
 */
@Stateless(name = "ExperimentFacade")
public class ExperimentFacade extends Facade<Experiment, Long> {

    @Override
    public void create(final Experiment experiment) {
	getEntityManager().persist(experiment);
    }

    @Override
    public Experiment edit(final Experiment experiment) {
	return getEntityManager().merge(experiment);
    }

    @Override
    public Experiment find(final Long id) {
	return getEntityManager().find(Experiment.class, id);
    }

    @Override
    public List<Experiment> findRange(final int[] range) {
	return find(false, range[0], range[1]);
    }

    @Override
    public List<Experiment> findAll() {
	return find(true, -1, -1);
    }

    @SuppressWarnings("unchecked")
    public List<Experiment> find(final boolean all, final int firstResult, final int maxResults) {
	Query q = getEntityManager().createNamedQuery("Experiment.findAll");
	if (!all) {
	    q.setMaxResults(maxResults);
	    q.setFirstResult(firstResult);
	}
	return q.getResultList();
    }

    @Override
    public int count() {
	final Query query = getEntityManager().createNamedQuery("Experiment.countAll");
	return ((Long) query.getSingleResult()).intValue();
    }
}