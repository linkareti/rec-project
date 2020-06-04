package com.linkare.irn.nascimento.web.controller;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.linkare.irn.nascimento.web.ApplicationFactory;
import com.linkare.irn.nascimento.web.ConfigurationManager;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public abstract class BaseController implements Serializable {

    private static final long serialVersionUID = -4706053494500749660L;

    @Inject
    private Instance<ConfigurationManager> configurationManager;

    @Inject
    private Instance<ApplicationFactory> applicationFactory;

    @Inject
    private FacesContext facesContext;

    private Logger log = Logger.getLogger(this.getClass().getName());

    protected void logError(final Exception e) {
	if (e != null) {
	    log.log(Level.SEVERE, "An exception was thrown", e);
	}
    }

    @PostConstruct
    public void init() {
	log.info("Initiating " + this);
    }

    @PreDestroy
    public void destroy() {
	log.info("Destroying " + this);
    }

    /**
     * @return the configurationManager
     */
    public ConfigurationManager getConfigurationManager() {
	return configurationManager.get();
    }

    /**
     * @return the applicationFactory
     */
    public ApplicationFactory getApplicationFactory() {
	return applicationFactory.get();
    }

    /**
     * @return the facesContext
     */
    public FacesContext getFacesContext() {
	return facesContext;
    }

    /**
     * @return the log
     */
    public Logger getLog() {
	return log;
    }
}