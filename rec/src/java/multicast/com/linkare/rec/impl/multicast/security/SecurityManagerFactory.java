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
		final Logger l = LogManager.getLogManager().getLogger(
				SecurityManagerFactory.MCCONTROLLER_SECURITYMANAGER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(
					Logger.getLogger(SecurityManagerFactory.MCCONTROLLER_SECURITYMANAGER_LOGGER));
		}
	}

	private void loadSecurityManager() {
		final String secManagerClassName = System.getProperty(SecurityManagerFactory.SYSPROP_SECURITY_MANAGER_CLASS);
		if (secManagerClassName == null || secManagerClassName.trim().length() == 0) {
			Logger.getLogger(SecurityManagerFactory.MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
					"SecurityManager System Property not found... Loading DefaultSecurityManager!");
			secManager = new DefaultSecurityManager();
		}
		try {
			Logger.getLogger(SecurityManagerFactory.MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.FINE,
					"Trying to load the SecurityManager class [" + secManagerClassName + "]");
			final Class<?> clazz = Class.forName(secManagerClassName);
			if (clazz != null) {
				secManager = (ISecurityManager) Class.forName(secManagerClassName).newInstance();
			}
		} catch (final Exception e) {
			Logger.getLogger(SecurityManagerFactory.MCCONTROLLER_SECURITYMANAGER_LOGGER).log(
					Level.INFO,
					"Unable to load SecurityManager defined at system : " + secManagerClassName
							+ " - loading DefaultSecurityManager!");
			LoggerUtil.logThrowable("Error loading specified SecurityManager", e,
					Logger.getLogger(SecurityManagerFactory.MCCONTROLLER_SECURITYMANAGER_LOGGER));
			e.printStackTrace();
		} catch (final LinkageError e) {
			Logger.getLogger(SecurityManagerFactory.MCCONTROLLER_SECURITYMANAGER_LOGGER).log(
					Level.INFO,
					"Unable to load SecurityManager defined at system : " + secManagerClassName
							+ " - loading DefaultSecurityManager!");
			LoggerUtil.logThrowable("Error loading specified SecurityManager", e,
					Logger.getLogger(SecurityManagerFactory.MCCONTROLLER_SECURITYMANAGER_LOGGER));
			e.printStackTrace();
		} finally {
			if (secManager == null) {
				Logger.getLogger(SecurityManagerFactory.MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
						"SecurityManager not instatiated... Loading DefaultSecurityManager!");
				secManager = new DefaultSecurityManager();
			}
		}
	}

	/** Creates a new instance of SecurityManagerFactory */
	private SecurityManagerFactory() {
		loadSecurityManager();
	}

	private static SecurityManagerFactory getInstance() {
		return SecurityManagerFactory.instance;
	}

	private ISecurityManager getSecManager() {
		return secManager;
	}

	private void setSecManager(final ISecurityManager secManager) {
		if (secManager == null) {
			Logger.getLogger(SecurityManagerFactory.MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
					"Setting SecurityManager to null... Loading DefaultSecurityManager!");
			this.secManager = new DefaultSecurityManager();
		} else {
			Logger.getLogger(SecurityManagerFactory.MCCONTROLLER_SECURITYMANAGER_LOGGER).log(
					Level.INFO,
					"Setting SecurityManager to instance of class " + secManager.getClass().getPackage().getName()
							+ "." + secManager.getClass().getName());
			this.secManager = secManager;
		}
	}

	public static void setSecurityManager(final ISecurityManager secManager) {
		SecurityManagerFactory.getInstance().setSecManager(secManager);
	}

	public static ISecurityManager getSecurityManager() {
		return SecurityManagerFactory.getInstance().getSecManager();
	}

	public boolean authenticateNow(final IResource resource, final IUser user) {
		try {
			return SecurityManagerFactory.getSecurityManager().authenticate(resource, user);
		} catch (final Exception e) {
			LoggerUtil.logThrowable("Exception occured authenticating user! Returning false!", e,
					Logger.getLogger(SecurityManagerFactory.MCCONTROLLER_SECURITYMANAGER_LOGGER));
			e.printStackTrace();
			return false;
		}
	}

	public boolean authorizeNow(final IResource resource, final IUser user, final IOperation op) {
		try {
			return SecurityManagerFactory.getSecurityManager().authorize(resource, user, op);
		} catch (final Exception e) {
			LoggerUtil.logThrowable("Exception occured authorizing user for operation! Returning false!", e,
					Logger.getLogger(SecurityManagerFactory.MCCONTROLLER_SECURITYMANAGER_LOGGER));
			e.printStackTrace();
			return false;
		}
	}

	public static boolean authenticate(final IResource resource, final IUser user) {
		return SecurityManagerFactory.getInstance().authenticateNow(resource, user);
	}

	public static boolean authorize(final IResource resource, final IUser user, final IOperation op) {
		return SecurityManagerFactory.getInstance().authorizeNow(resource, user, op);
	}

	private class DefaultSecurityManager implements ISecurityManager {

		@Override
		public boolean authenticate(final IResource resource, final IUser user) {
			// Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,"Authenticating access for Resource: "
			// + resource + " to User: " + user +" OK!");
			return true;
		}

		@Override
		public boolean authorize(final IResource resource, final IUser user, final IOperation op) {
			// Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,"Authorizing Operation "
			// + op + " for Resource: " + resource + " to User: " + user
			// +" OK!");
			return true;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void registerMultiCastHardware(final List<ReCMultiCastHardware> multiCastHardwares) {
			// this security implementation doesn't require the multicast
			// hardwares
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void registerSecurityCommunicator(final ISecurityCommunicator communicator) {
			// this security implementation doesn't require the multicast
			// hardwares
		}
	}

}
