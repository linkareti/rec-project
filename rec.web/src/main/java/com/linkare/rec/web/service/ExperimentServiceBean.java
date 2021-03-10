package com.linkare.rec.web.service;

import static com.linkare.rec.web.model.Experiment.COUNT_ALL_QUERYNAME;
import static com.linkare.rec.web.model.Experiment.FIND_ALL_ACTIVE_QUERYNAME;
import static com.linkare.rec.web.model.Experiment.FIND_ALL_QUERYNAME;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.web.config.Apparatus;
import com.linkare.rec.web.model.Experiment;
import com.linkare.rec.web.model.Laboratory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Joao
 * @author Bruno Catarino - Linkare TI
 */
@Local(ExperimentServiceLocal.class)
@Stateless(name = "ExperimentService")
public class ExperimentServiceBean extends BusinessServiceBean<Experiment, Long> implements ExperimentService {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ExperimentServiceBean.class);
    
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
        try {
            return (Experiment) getEntityManager().createNamedQuery(Experiment.FIND_BY_EXTERNAL_ID).setParameter(Experiment.EXTERNAL_ID_QRY_PARAM, externalID)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

    }

    @Override
    public void createExperimentFromApparatus(Apparatus apparatus, Laboratory laboratory) {
        Experiment experiment = new Experiment();

        String toolTipBundleKey = apparatus.getToolTipBundleKey();
        experiment.getState().setHelpMessage(toolTipBundleKey);

        String displayStringBundleKey = apparatus.getDisplayStringBundleKey();
        experiment.getState().setLabel(displayStringBundleKey);
        experiment.getState().setUrl(apparatus.getLocation());

        experiment.getState().setActive(true);
        experiment.setDescription(
                ReCResourceBundle.findStringOrDefault(displayStringBundleKey, displayStringBundleKey));
        experiment.setName(
                ReCResourceBundle.findStringOrDefault(displayStringBundleKey, displayStringBundleKey));
        experiment.setExternalId(apparatus.getLocation());
        experiment.setLaboratory(laboratory);
        create(experiment);

        LOGGER.info("Experiment {} created for Laboratory {}", experiment, laboratory);
    }

    @Override
    public Experiment updateExperimentFromApparatus(Experiment experiment, Apparatus apparatus, Laboratory laboratory) {
        if (experiment.getState().getHelpMessage() == null) {
            String toolTipBundleKey = apparatus.getToolTipBundleKey();
            experiment.getState().setHelpMessage(toolTipBundleKey);
            LOGGER.info("toolTipBundleKey={}", toolTipBundleKey);
        }
        if (experiment.getState().getLabel() == null) {
            experiment.getState().setLabel(apparatus.getDisplayStringBundleKey());
            LOGGER.info("Experiment Label={}", experiment.getState().getLabel());
        }
        if (experiment.getState().getUrl() == null) {
            experiment.getState().setUrl(apparatus.getLocation());
            LOGGER.info("Experiment Label={}", experiment.getState().getUrl());
        }
        experiment.setLaboratory(laboratory);
        experiment = edit(experiment);
        LOGGER.info("Experiment {} updated from apparatus", experiment);
        return experiment;
    }
}