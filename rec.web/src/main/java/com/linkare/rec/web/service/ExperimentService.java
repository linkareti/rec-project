package com.linkare.rec.web.service;

import java.util.List;

import com.linkare.rec.web.config.Apparatus;
import com.linkare.rec.web.model.Experiment;
import com.linkare.rec.web.model.Laboratory;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * @author Bruno Catarino - Linkare TI
 * 
 */
public interface ExperimentService extends BusinessService<Experiment, Long> {

    public List<Experiment> findAllActiveExperiments();
    
    public List<Experiment> findExperimentsActiveByLaboratory(String labName);

    public Experiment findByExternalID(final String externalID);

    void createExperimentFromApparatus(Apparatus apparatus, Laboratory laboratory);

    Experiment updateExperimentFromApparatus(Experiment experiment, Apparatus apparatus, Laboratory laboratory);
}