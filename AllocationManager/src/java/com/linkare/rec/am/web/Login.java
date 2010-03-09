package com.linkare.rec.am.web;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joao
 *
 * A simple Weld Bean that performs a login operation with user's
 * credentials.
 */
@Named
@SessionScoped
@Default
public class Login implements Authenticator, Serializable {

    static Logger logger = Logger.getLogger("Login");
    @Inject
    Credentials credentials;
    private boolean loggedIn = false;
    private HttpServletRequest request;

    public void login(HttpServletRequest request) {

        if ((credentials.getUsername() != null
                && credentials.getUsername().trim().length() > 0)
                && (credentials.getPassword() != null
                && credentials.getPassword().trim().length() > 0)) {

            try {
                request.login(credentials.getUsername(), credentials.getPassword());
                logger.info("User '"+ credentials.getUsername() + "' logged in successfully");
                loggedIn = true;
            } catch (ServletException ex) {
                logger.info("User '"+ credentials.getUsername() + "' failed to login");
                loggedIn = false;
            }
        }
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public boolean authenticate() {
        try {
            request.login(credentials.getUsername(), credentials.getPassword());
            loggedIn = true;
        } catch (ServletException ex) {
            loggedIn = false;
        } finally {
            return isLoggedIn();
        }
    }
}
