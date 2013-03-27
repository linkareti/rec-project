/* 
 * BusinessServiceLocator.java created on Apr 13, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils.locator;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.linkare.rec.impl.config.ReCSystemProperty;

/**
 * 
 * Singleton to locate business services
 * 
 * @author Artur Correia - Linkare TI
 */
public final class BusinessServiceLocator {

	private static final Logger LOG = Logger.getLogger(BusinessServiceLocator.class.getName());

	public static final String NAMING_FACTORY = ReCSystemProperty.REC_WEB_INITIAL_CONTEXT_FACTORY.getValue();
	public static final String NAMING_URL_PKGS = ReCSystemProperty.REC_WEB_NAMING_CTX_PKGS.getValue();
	public static final String NAMING_STATE = ReCSystemProperty.REC_WEB_NAMING_FACTORY_STATE.getValue();
	public static final String ORB_ENV_HOST = ReCSystemProperty.REC_WEB_NAMING_HOST.getValue();
	public static final String ORB_ENV_PORT = ReCSystemProperty.REC_WEB_NAMING_PORT.getValue();

	private static final String ORG_OMG_CORBA_ORB_INITIAL_PORT = "org.omg.CORBA.ORBInitialPort";
	private static final String ORG_OMG_CORBA_ORB_INITIAL_HOST = "org.omg.CORBA.ORBInitialHost";

	private static final BusinessServiceLocator INSTANCE = new BusinessServiceLocator();

	private final ConcurrentMap<BusinessServiceEnum, Object> cacheMap;
	private final InitialContext ic;

	private BusinessServiceLocator() {

		cacheMap = new ConcurrentHashMap<BusinessServiceEnum, Object>(10);

		try {

			final Properties jndiProps = new Properties();
			jndiProps.put(InitialContext.INITIAL_CONTEXT_FACTORY, NAMING_FACTORY);
			jndiProps.put(InitialContext.URL_PKG_PREFIXES, NAMING_URL_PKGS);
			jndiProps.put(InitialContext.STATE_FACTORIES, NAMING_STATE);
			jndiProps.setProperty(ORG_OMG_CORBA_ORB_INITIAL_HOST, ORB_ENV_HOST);
			jndiProps.setProperty(ORG_OMG_CORBA_ORB_INITIAL_PORT, ORB_ENV_PORT);
			ic = new InitialContext(jndiProps);
		} catch (NamingException e) {
			LOG.log(Level.SEVERE, "Error while initializing BusinessServiceLocator.", e);
			// oooppppssss lets rock
			throw new RuntimeException(e);
		}

	}

	public static BusinessServiceLocator getInstance() {
		return INSTANCE;
	}

	@SuppressWarnings("unchecked")
	public <T> T getBusinessInterface(final BusinessServiceEnum businessService) throws BusinessServiceLocatorException {
		if (!cacheMap.containsKey(businessService)) {
			// atomic operation... if we have two threads in concurrency only
			// one
			// will have sucess executing this statement
			try {
				cacheMap.putIfAbsent(businessService, ic.lookup(businessService.getJNDIName()));
			} catch (NamingException e) {
				throw new BusinessServiceLocatorException(e);
			}
		}
		return (T) cacheMap.get(businessService);
	}

}
