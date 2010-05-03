package com.linkare.rec.am.web.auth;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@SessionScoped
@ManagedBean(name = "userView")
public class UserView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String domain;

    public UserView() {
	super();
    }

    public UserView(String username, String domain) {
	this.username = username;
	this.domain = domain;
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

    public boolean isAdmin() {
	return false;
    }

    public boolean isExternal() {
	return false;
    }
}