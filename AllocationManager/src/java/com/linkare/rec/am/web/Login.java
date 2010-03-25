package com.linkare.rec.am.web;

import com.linkare.rec.am.model.UserPrincipal;
import com.linkare.rec.am.web.util.JsfUtil;
import java.io.Serializable;
import java.util.logging.Level;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Joao
 *
 * A simple Weld Bean that performs a login operation with user's
 * credentials.
 */
@Named(value="login")
@SessionScoped
@Default
public class Login implements Authenticator, Serializable {

    private static Logger logger = Logger.getLogger("Login");

    @Inject
    private Credentials credentials;

    private UserPrincipal user = null;

    private boolean loggedIn = false;

    private HttpServletRequest request;

    private HttpSession session;

    public final void login(HttpServletRequest request) {

        if ((getCredentials().getUsername() != null
                && getCredentials().getUsername().trim().length() > 0)
                && (getCredentials().getPassword() != null
                && getCredentials().getPassword().trim().length() > 0)) {

            try {
                request.login(getCredentials().getUsername(), getCredentials().getPassword());
                setSession(request.getSession());
                getLogger().info("User '" + getCredentials().getUsername() + "' logged in successfully");
                setLoggedIn(true);
            } catch (ServletException ex) {
                getLogger().info("User '" + getCredentials().getUsername() + "' failed to login");
                setLoggedIn(false);
            }
        }
    }

    public final void logout(HttpServletRequest request) {
        try {
            request.logout();
            setLoggedIn(false);
        } catch (ServletException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final String logout() {
        JsfUtil.getSession().invalidate();
//        return "/LoginServlet?action=logout";
        return "logout";
    }

    public final boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public final boolean authenticate() {
        try {
            request.login(getCredentials().getUsername(), getCredentials().getPassword());
            setLoggedIn(true);
        } catch (ServletException ex) {
            setLoggedIn(false);
        } finally {
            return isLoggedIn();
        }
    }

    /**
     * @return the logger
     */
    public final static Logger getLogger() {
        return logger;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public final void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * @return the user
     */
    public final UserPrincipal getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public final void setUser(UserPrincipal user) {
        this.user = user;
    }

    /**
     * @return the session
     */
    public final HttpSession getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public final void setSession(HttpSession session) {
        this.session = session;
    }

    /**
     * @return the credentials
     */
    public final Credentials getCredentials() {
        return credentials;
    }
}
