package com.linkare.rec.web.service;

import java.util.List;

import com.linkare.rec.web.model.Experiment;

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
}