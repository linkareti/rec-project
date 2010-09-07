/*
 * SecurityManagerFactory.java
 *
 * Created on 2 de Janeiro de 2004, 15:34
 */

package com.linkare.rec.impl.multicast.security;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.multicast.ClientQueue;
import com.linkare.rec.impl.multicast.ReCMultiCastHardware;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public final class SecurityManagerFactory {
	private static SecurityManagerFactory instance = new SecurityManagerFactory();

	private ISecurityManager secManager = null;

	public static final String SYSPROP_SECURITY_MANAGER_CLASS = "ReC.MultiCast.SecurityManager";

	// public static final String
	// MCCONTROLLER_SECURITYMANAGER_LOGGER=ReCMultiCastController.MCCONTROLLER_LOGGER;
	public static final String MCCONTROLLER_SECURITYMANAGER_LOGGER = "ReC.MultiCast.SecurityManager.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER));
		}
	}

	private void loadSecurityManager() {
		String secManagerClassName = System.getProperty(SYSPROP_SECURITY_MANAGER_CLASS);
		if (secManagerClassName == null || secManagerClassName.trim().length() == 0) {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
					"SecurityManager System Property not found... Loading DefaultSecurityManager!");
			secManager = new DefaultSecurityManager();
		}
		try {
			secManager = (ISecurityManager) Class.forName(secManagerClassName).newInstance();
		} catch (Exception e) {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(
					Level.INFO,
					"Unable to load SecurityManager defined at system : " + secManagerClassName
							+ " - loading DefaultSecurityManager!");
			LoggerUtil.logThrowable("Error loading specified SecurityManager", e, Logger
					.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER));
		} catch (LinkageError e) {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(
					Level.INFO,
					"Unable to load SecurityManager defined at system : " + secManagerClassName
							+ " - loading DefaultSecurityManager!");
			LoggerUtil.logThrowable("Error loading specified SecurityManager", e, Logger
					.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER));
		} finally {
			if (secManager == null) {
				secManager = new DefaultSecurityManager();
			}
		}

	}

	/** Creates a new instance of SecurityManagerFactory */
	private SecurityManagerFactory() {
		loadSecurityManager();
	}

	private static SecurityManagerFactory getInstance() {
		return instance;
	}

	private ISecurityManager getSecManager() {
		return secManager;
	}

	private void setSecManager(ISecurityManager secManager) {
		if (secManager == null) {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
					"Setting SecurityManager to null... Loading DefaultSecurityManager!");
			this.secManager = new DefaultSecurityManager();
		} else {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(
					Level.INFO,
					"Setting SecurityManager to instance of class " + secManager.getClass().getPackage().getName()
							+ "." + secManager.getClass().getName());
			this.secManager = secManager;
		}
	}

	public static void setSecurityManager(ISecurityManager secManager) {
		getInstance().setSecManager(secManager);
	}

	public static ISecurityManager getSecurityManager() {
		return getInstance().getSecManager();
	}

	public boolean authenticateNow(IResource resource, IUser user) {
		try {
			return getSecurityManager().authenticate(resource, user);
		} catch (Exception e) {
			LoggerUtil.logThrowable("Exception occured authenticating user! Returning false!", e, Logger
					.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER));
			return false;
		}
	}

	public boolean authorizeNow(IResource resource, IUser user, IOperation op) {
		try {
			return getSecurityManager().authorize(resource, user, op);
		} catch (Exception e) {
			LoggerUtil.logThrowable("Exception occured authorizing user for operation! Returning false!", e, Logger
					.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER));
			return false;
		}
	}

	public static boolean authenticate(IResource resource, IUser user) {
		return getInstance().authenticateNow(resource, user);
	}

	public static boolean authorize(IResource resource, IUser user, IOperation op) {
		return getInstance().authorizeNow(resource, user, op);
	}

	private class DefaultSecurityManager implements ISecurityManager {

		public boolean authenticate(IResource resource, IUser user) {
			// Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,"Authenticating access for Resource: "
			// + resource + " to User: " + user +" OK!");
			return true;
		}

		public boolean authorize(IResource resource, IUser user, IOperation op) {
			// Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,"Authorizing Operation "
			// + op + " for Resource: " + resource + " to User: " + user
			// +" OK!");
			return true;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void registerMultiCastHardware(List<ReCMultiCastHardware> multiCastHardwares) {
			// this security implementation doesn't require the multicast hardwares
		}
	}

}
