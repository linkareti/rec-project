package com.linkare.rec.am.service;

import com.linkare.rec.am.model.LoginDomain;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public interface LoginDomainService extends BusinessService<LoginDomain, Long> {

    public LoginDomain findByName(final String name);
}