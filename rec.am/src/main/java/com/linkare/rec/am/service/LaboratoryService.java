package com.linkare.rec.am.service;

import java.util.List;

import com.linkare.rec.am.model.Laboratory;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public interface LaboratoryService extends BusinessService<Laboratory, Long> {
    public List<Laboratory> findAllActive();
}