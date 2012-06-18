package com.linkare.rec.am.service;

import java.util.List;

import com.linkare.rec.am.model.Experiment;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public interface ExperimentService extends BusinessService<Experiment, Long> {

    public List<Experiment> findAllActiveExperiments();
}