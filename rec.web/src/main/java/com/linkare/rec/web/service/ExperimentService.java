package com.linkare.rec.web.service;

import java.util.List;

import com.linkare.rec.web.config.Lab;
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

    /**
     * Update and creates new Experiments based on the apparatus of the Lab
     *
     * @param laboratory {@link Laboratory} of the experiments
     * @param lab {@link Lab} with new configurations
     */
    void createOrUpdateFromLab(Laboratory laboratory, Lab lab);

    /**
     * Inactivate all the experiments under a laboratory
     *
     * @param laboratory {@link Laboratory}
     */
    void inactivateAll(Laboratory laboratory);
}