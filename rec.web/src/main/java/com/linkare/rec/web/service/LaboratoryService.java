package com.linkare.rec.web.service;

import java.util.List;

import com.linkare.rec.web.config.Lab;
import com.linkare.rec.web.model.Laboratory;

/**
 * @author Paulo Zenida - Linkare TI
 */
public interface LaboratoryService extends BusinessService<Laboratory, Long> {

    public List<Laboratory> findAllActive();

    /**
     * Find a laboratory by it's name
     *
     * @param name Name of the Laboratory
     * @return Laboratory
     */
    Laboratory findByName(String name);

    /**
     * Find a list of Laboratories with recFaceConfigUrl configured
     *
     * @return List of Laboratories
     */
    List<Laboratory> findAllLaboratoriesWithRecConfigUrl();

    /**
     * Update a given laboratory from the RecFaceConfig
     *
     * @param laboratory {@link Laboratory}
     * @param lab {@link Lab}
     * @return updated laboratory
     */
    Laboratory updateFromRecFaceConfig(Laboratory laboratory, Lab lab);

}