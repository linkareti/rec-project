package com.linkare.rec.web.service;

import static com.linkare.rec.web.model.Experiment.COUNT_ALL_QUERYNAME;
import static com.linkare.rec.web.model.Experiment.FIND_ALL_ACTIVE_QUERYNAME;
import static com.linkare.rec.web.model.Experiment.FIND_ALL_QUERYNAME;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.linkare.rec.web.model.Experiment;

/**
 * 
 * @author Joao
 * @author Bruno Catarino - Linkare TI
 */
@Local(ExperimentServiceLocal.class)
@Stateless(name = "ExperimentService")
public class ExperimentServiceBean extends BusinessServiceBean<Experiment, Long> implements ExperimentService {

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
	Query q = getEntityManager().createNamedQuery(FIND_ALL_QUERYNAME);
	if (!all) {
	    q.setMaxResults(maxResults);
	    q.setFirstResult(firstResult);
	}
	return q.getResultList();
    }

    @Override
    public int count() {
	final Query query = getEntityManager().createNamedQuery(COUNT_ALL_QUERYNAME);
	return ((Long) query.getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Experiment> findAllActiveExperiments() {
	return getEntityManager().createNamedQuery(FIND_ALL_ACTIVE_QUERYNAME).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Experiment> findExperimentsActiveByLaboratory(String labName) {
	return getEntityManager().createNamedQuery(Experiment.FIND_BY_ACTIVE_LAB).setParameter(Experiment.LABORATORY, labName).getResultList();
    }

    @Override
    public Experiment findByExternalID(String externalID) {
	return (Experiment) getEntityManager().createNamedQuery(Experiment.FIND_BY_EXTERNAL_ID).setParameter(Experiment.EXTERNAL_ID_QRY_PARAM, externalID)
					      .getSingleResult();
    }
}