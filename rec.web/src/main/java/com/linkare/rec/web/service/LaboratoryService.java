package com.linkare.rec.web.service;

import java.util.List;

import com.linkare.rec.web.model.Laboratory;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
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
}