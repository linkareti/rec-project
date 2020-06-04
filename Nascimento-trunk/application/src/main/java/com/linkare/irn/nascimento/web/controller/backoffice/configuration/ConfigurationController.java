package com.linkare.irn.nascimento.web.controller.backoffice.configuration;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.linkare.irn.nascimento.model.config.Configuration;
import com.linkare.irn.nascimento.service.config.ConfigurationService;
import com.linkare.irn.nascimento.util.ApplicationMessageUtils;
import com.linkare.irn.nascimento.util.JsfUtils;
import com.linkare.irn.nascimento.web.auth.UnauthorizedException;
import com.linkare.irn.nascimento.web.controller.BaseProtectedController;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Named
@ViewScoped
public class ConfigurationController extends BaseProtectedController {

    private static final long serialVersionUID = -6454088446543836978L;

    @Inject
    private ConfigurationService configurationService;

    private Configuration configuration;

    @Override
    public void checkAccess() throws UnauthorizedException {
	if (!getLoginBean().getHasAdminRole()) {
	    throw new UnauthorizedException("User " + getLoginBean().getUsername() + " has no access to the configurations");
	}
    }

    @PostConstruct
    public void init() {
	getLog().info("Initiating " + this);
	this.configuration = configurationService.find();
    }

    public void save() {
	try {
	    configurationService.update(configuration);
	    JsfUtils.addGlobalInfoMessage(ApplicationMessageUtils.getMessage("message.configuration.success"));
	} catch (final Exception e) {
	    logError(e);
	    JsfUtils.addGlobalErrorMessage(ApplicationMessageUtils.getMessage("message.configuration.error"));
	}
    }

    /**
     * @return the configuration
     */
    public Configuration getConfiguration() {
	return configuration;
    }
}