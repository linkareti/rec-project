package com.linkare.rec.am.web.auth;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.am.service.UserService;
import com.linkare.rec.am.service.UserServiceLocal;
import com.linkare.rec.am.web.ex.AuthenticationException;
import com.linkare.rec.am.web.moodle.SessionHelper;
import com.linkare.rec.am.web.util.ConstantUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@ManagedBean(name = "authenticationBean")
@RequestScoped
public class AuthenticationBean {

    private String username;

    private String password;

    private String domain;

    @EJB(beanInterface = UserServiceLocal.class)
    private UserService userService;

    public String login() {
	try {
	    authenticate();
	    registerUserIfNecessary();
	} catch (AuthenticationException e) {
	    JsfUtil.addGlobalErrorMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY, ConstantUtils.ERROR_LOGIN_FAILED_KEY);
	    return null;
	}
	return "index";
    }

    private void authenticate() throws AuthenticationException {
	final LoginProvider loginProvider = LoginProviderFactory.getLoginProvider(getDomain());
	loginProvider.login(getUsername(), getPassword(), getDomain());
    }

    private void registerUserIfNecessary() {
	if (!isInternalDomain(getDomain())) {
	    userService.registerExternalUser(SessionHelper.getUserView().getFullUsername());
	}
    }

    private boolean isInternalDomain(final String domain) {
	return ConstantUtils.INTERNAL_DOMAIN_NAME.equalsIgnoreCase(domain);
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
     * @return the domain
     */
    public String getDomain() {
	return domain;
    }

    /**
     * @param domain
     *            the domain to set
     */
    public void setDomain(String domain) {
	this.domain = domain;
    }
}