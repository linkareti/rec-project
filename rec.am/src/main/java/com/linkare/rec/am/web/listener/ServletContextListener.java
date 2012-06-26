package com.linkare.rec.am.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.rec.am.util.LaboratoriesMonitor;

@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {

    private final static Logger LOG = LoggerFactory.getLogger(ServletContextListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
	LOG.info("Context detroyed");
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

	try {
	    LaboratoriesMonitor.getInstance();
	} catch (Exception e) {
	    LOG.error(e.getMessage(), e);
	}
    }

}
