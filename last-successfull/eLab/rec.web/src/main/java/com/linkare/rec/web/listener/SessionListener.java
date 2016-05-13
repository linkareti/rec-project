package com.linkare.rec.web.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * When the user session timedout, ({@link #sessionDestroyed(HttpSessionEvent)})
 * method will be invoked. This method will make necessary cleanups (logging out
 * user, updating db and audit logs, etc...) As a result; after this method, we
 * will be in a clear and stable state. So nothing left to think about because
 * session expired, user can do nothing after this point.
 * 
 * @author Joao
 * 
 */
public class SessionListener implements HttpSessionListener {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(SessionListener.class);

	public SessionListener() {
	}

	@Override
	public final void sessionCreated(HttpSessionEvent event) {
		LOGGER.info("session created : " + event.getSession().getId());
	}

	@Override
	public final void sessionDestroyed(HttpSessionEvent event) {
		// get the destroying session...
		HttpSession session = event.getSession();
		LOGGER.info("session destroyed :" + session.getId()
				+ " Logging out user...");
	}
}