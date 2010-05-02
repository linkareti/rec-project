package com.linkare.rec.am.web.auth;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.Default;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.linkare.commons.jpa.security.User;
import com.linkare.rec.am.web.ex.AuthenticationException;
import com.linkare.rec.am.web.util.JsfUtil;

/**
 * 
 * @author João Lourenço - Linkare TI
 * 
 *         A simple Weld Bean that performs a login operation with user's credentials.
 */
@Named(value = "login")
@SessionScoped
@Default
public class Login implements Authenticator, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger("Login");

    public static final String INTERNAL_LOGIN_DOMAIN = "internal";

    @Inject
    private Credentials credentials;

    private String loginDomain;

    private User user = null;

    private boolean loggedIn = false;

    private HttpServletRequest request;

    private HttpSession session;

    private LoginProvider loginProvider;

    public void login(HttpServletRequest request) {

	if ((getCredentials().getUsername() != null && getCredentials().getUsername().trim().length() > 0)
		&& (getCredentials().getPassword() != null && getCredentials().getPassword().trim().length() > 0)) {

	    try {
		getLoginProvider().login(request, getCredentials().getUsername(), getCredentials().getPassword(), getLoginDomain());
		getLogger().info("User '" + getCredentials().getUsername() + "' logged in successfully");
		setLoggedIn(true);
	    } catch (AuthenticationException ex) {
		getLogger().info("User '" + getCredentials().getUsername() + "' failed to login");
		setLoggedIn(false);
	    }
	}
    }

    private LoginProvider getLoginProvider() {
	if (loginProvider == null) {
	    loginProvider = INTERNAL_LOGIN_DOMAIN.equals(loginDomain) ? new RequestLoginProvider() : new MoodleLoginProvider();
	}
	return loginProvider;
    }

    public void logout(HttpServletRequest request) {
	try {
	    request.logout();
	    setLoggedIn(false);
	} catch (ServletException ex) {
	    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public String logout() {
	JsfUtil.getSession().invalidate();
	//        return "/LoginServlet?action=logout";
	return "logout";
    }

    public boolean isLoggedIn() {
	return loggedIn;
    }

    @Override
    public boolean authenticate() {
	try {
	    request.login(getCredentials().getUsername(), getCredentials().getPassword());
	    setLoggedIn(true);
	} catch (ServletException ex) {
	    setLoggedIn(false);
	}
	return isLoggedIn();
    }

    /**
     * @return the logger
     */
    public static Logger getLogger() {
	return logger;
    }

    /**
     * @param loggedIn
     *            the loggedIn to set
     */
    public void setLoggedIn(boolean loggedIn) {
	this.loggedIn = loggedIn;
    }

    /**
     * @return the user
     */
    public User getUser() {
	return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
	this.user = user;
    }

    /**
     * @return the session
     */
    public HttpSession getSession() {
	return session;
    }

    /**
     * @param session
     *            the session to set
     */
    public void setSession(HttpSession session) {
	this.session = session;
    }

    /**
     * @return the credentials
     */
    public Credentials getCredentials() {
	return credentials;
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
}