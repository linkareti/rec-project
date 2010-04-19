package com.linkare.rec.am.web.auth;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.linkare.rec.am.wsgen.moodle.LoginReturn;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@SessionScoped
@ManagedBean(name = "userView")
public class UserView implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String USER_VIEW_SESSION_KEY = "userView";

    private String username;

    private String domain;

    private LoginReturn loginReturn;

    public UserView() {
	super();
    }

    public UserView(String username, String domain) {
	this(username, domain, null);
    }

    public UserView(String username, String domain, LoginReturn loginReturn) {
	this();
	this.username = username;
	this.domain = domain;
	this.loginReturn = loginReturn;
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

    /**
     * @return the loginReturn
     */
    public LoginReturn getLoginReturn() {
	return loginReturn;
    }

    /**
     * @param loginReturn
     *            the loginReturn to set
     */
    public void setLoginReturn(LoginReturn loginReturn) {
	this.loginReturn = loginReturn;
    }

    public boolean isExternal() {
	return getLoginReturn() != null;
    }
}