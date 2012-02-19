package com.linkare.rec.am.web.listener;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * When the user session timedout, ({@link #sessionDestroyed(HttpSessionEvent)}) method will be invoked. This method will make necessary cleanups (logging out
 * user, updating db and audit logs, etc...) As a result; after this method, we will be in a clear and stable state. So nothing left to think about because
 * session expired, user can do nothing after this point.
 * 
 * @author Joao
 * 
 */
public class SessionListener implements HttpSessionListener {

    private static Logger logger = Logger.getLogger("SessionListener");

    public SessionListener() {
    }

    @Override
    public final void sessionCreated(HttpSessionEvent event) {
	logger.info("session created : " + event.getSession().getId());
    }

    @Override
    public final void sessionDestroyed(HttpSessionEvent event) {
	// get the destroying session...
	HttpSession session = event.getSession();
	logger.info("session destroyed :" + session.getId() + " Logging out user...");
    }
}