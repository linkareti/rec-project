package com.linkare.rec.web.service;

import com.linkare.rec.web.model.LoginDomain;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public interface LoginDomainService extends BusinessService<LoginDomain, Long> {

    public LoginDomain findByName(final String name);
}