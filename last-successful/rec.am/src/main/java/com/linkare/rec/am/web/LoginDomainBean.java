package com.linkare.rec.am.web;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.am.model.LoginDomain;
import com.linkare.rec.am.service.LoginDomainService;
import com.linkare.rec.am.service.LoginDomainServiceLocal;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@ManagedBean(name = "loginDomainBean")
@SessionScoped
public class LoginDomainBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB(beanInterface = LoginDomainServiceLocal.class)
    private LoginDomainService service;

    private List<LoginDomain> loginDomains;

    /**
     * @return the service
     */
    public LoginDomainService getService() {
	return service;
    }

    /**
     * @param service
     *            the service to set
     */
    public void setFacade(LoginDomainService service) {
	this.service = service;
    }

    /**
     * @return the loginDomains
     */
    public SelectItem[] getLoginDomains() {
	if (loginDomains == null) {
	    loginDomains = service.findAll();
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