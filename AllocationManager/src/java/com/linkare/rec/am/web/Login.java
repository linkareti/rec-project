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
    Credentials credentials;

    private UserPrincipal user = null;

    private boolean loggedIn = false;

    private HttpServletRequest request;

    private HttpSession session;

    public void login(HttpServletRequest request) {

        if ((credentials.getUsername() != null
                && credentials.getUsername().trim().length() > 0)
                && (credentials.getPassword() != null
                && credentials.getPassword().trim().length() > 0)) {

            try {
                request.login(credentials.getUsername(), credentials.getPassword());
                setSession(request.getSession());
                getLogger().info("User '" + credentials.getUsername() + "' logged in successfully");
                setLoggedIn(true);
            } catch (ServletException ex) {
                getLogger().info("User '" + credentials.getUsername() + "' failed to login");
                setLoggedIn(false);
            }
        }
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
            request.login(credentials.getUsername(), credentials.getPassword());
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
    public static Logger getLogger() {
        return logger;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * @return the user
     */
    public UserPrincipal getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserPrincipal user) {
        this.user = user;
    }

    /**
     * @return the session
     */
    public HttpSession getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(HttpSession session) {
        this.session = session;
    }
}
