package com.linkare.rec.am.web;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import com.linkare.rec.am.model.LoginDomain;
import com.linkare.rec.am.model.LoginDomainFacade;
import com.linkare.rec.am.web.moodle.MoodleClientHelper;
import com.linkare.rec.am.web.util.JsfUtil;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@ManagedBean(name = "loginDomainBean")
@ApplicationScoped
public class LoginDomainBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private LoginDomainFacade facade;

    private List<LoginDomain> loginDomains;

    /**
     * @return the facade
     */
    public LoginDomainFacade getFacade() {
	return facade;
    }

    /**
     * @param facade
     *            the facade to set
     */
    public void setFacade(LoginDomainFacade facade) {
	this.facade = facade;
    }

    /**
     * @return the loginDomains
     */
    public SelectItem[] getLoginDomains() {
	if (loginDomains == null) {
	    loginDomains = facade.findAll();
	    try {
		MoodleClientHelper.registerLoginDomains(loginDomains);
	    } catch (MalformedURLException e) {
		throw new RuntimeException("error.invalidURL.registering.login.domains", e);
	    }
	}
	return JsfUtil.getSelectItems(loginDomains, false);
    }

    /**
     * @param loginDomains
     *            the loginDomains to set
     */
    public void setLoginDomains(List<LoginDomain> loginDomains) {
	this.loginDomains = loginDomains;
    }
}