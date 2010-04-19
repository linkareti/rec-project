package com.linkare.rec.am.web.auth;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import com.linkare.rec.am.model.UserPrincipalFacade;
import com.linkare.rec.am.web.ex.AuthenticationException;
import com.linkare.rec.am.web.util.JsfUtil;

@ManagedBean(name = "authenticationBean")
@RequestScoped
public class AuthenticationBean {

    private String username;

    private String password;

    private String loginDomain;

    private LoginProvider loginProvider;

    @EJB
    private UserPrincipalFacade facade;

    public static final String INTERNAL_LOGIN_DOMAIN = "internal";

    public String login() {
	try {
	    getLoginProvider().login((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest(), getUsername(), getPassword(),
				     getLoginDomain());
	} catch (AuthenticationException e) {
	    e.printStackTrace();
	    JsfUtil.addErrorMessage("error.login.failed");
	    return null;
	}
	return "index";
    }

    private LoginProvider getLoginProvider() {
	if (loginProvider == null) {
	    loginProvider = INTERNAL_LOGIN_DOMAIN.equals(loginDomain) ? new LocalLoginProvider(facade) : new MoodleLoginProvider();
	}
	return loginProvider;
    }

    public String logout() {
	JsfUtil.getSession().invalidate();
	return "logout";
    }

    /**
     * @return the username
     */
    public String getUsername() {
	return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
	this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
	return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * @return the loginDomain
     */
    public String getLoginDomain() {
	return loginDomain;
    }

    /**
     * @param loginDomain
     *            the loginDomain to set
     */
    public void setLoginDomain(String loginDomain) {
	this.loginDomain = loginDomain;
    }

    public SelectItem[] getLoginDomains() {
	// TODO Implement.......................
	final List<String> domains = new ArrayList<String>(3);
	domains.add("Localhost Moodle");
	domains.add("IP Moodle");
	domains.add("Invalid moodle");
	domains.add("internal");
	return JsfUtil.getSelectItems(domains, false);
    }
}