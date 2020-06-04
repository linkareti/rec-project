package com.linkare.irn.nascimento.dao.config;

import javax.persistence.EntityManager;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.config.Configuration;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ConfigurationDAO extends GenericDAO<Configuration> {

    public ConfigurationDAO(final EntityManager entityManager) {
	super(entityManager);
    }
}