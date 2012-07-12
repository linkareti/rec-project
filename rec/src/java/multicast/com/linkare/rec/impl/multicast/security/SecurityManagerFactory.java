package com.linkare.rec.impl.multicast.security;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.multicast.ReCMultiCastHardware;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public final class SecurityManagerFactory {
	private static SecurityManagerFactory instance = new SecurityManagerFactory();

	private ISecurityManager secManager = null;

	public static final String SYSPROP_SECURITY_MANAGER_CLASS = "rec.multicast.securitymanager";

	private static final Logger LOGGER = Logger.getLogger(SecurityManagerFactory.class.getName());

	private void loadSecurityManager() {
		final String secManagerClassName = System.getProperty(SecurityManagerFactory.SYSPROP_SECURITY_MANAGER_CLASS);
		if (secManagerClassName == null || secManagerClassName.trim().length() == 0) {
			LOGGER.log(Level.INFO, "SecurityManager System Property not found... Loading DefaultSecurityManager!");
			secManager = new DefaultSecurityManager();
		}
		try {
			LOGGER.log(Level.FINE, "Trying to load the SecurityManager class [" + secManagerClassName + "]");
			final Class<?> clazz = Class.forName(secManagerClassName);
			if (clazz != null) {
				secManager = (ISecurityManager) Class.forName(secManagerClassName).newInstance();
			}
		} catch (final Exception e) {
			LOGGER.log(Level.INFO, "Unable to load SecurityManager defined at system : " + secManagerClassName
					+ " - loading DefaultSecurityManager!");
			LOGGER.log(Level.SEVERE, "Error loading specified SecurityManager", e);
		} catch (final LinkageError e) {
			LOGGER.log(Level.INFO, "Unable to load SecurityManager defined at system : " + secManagerClassName
					+ " - loading DefaultSecurityManager!");
			LOGGER.log(Level.SEVERE, "Error loading specified SecurityManager", e);
		} finally {
			if (secManager == null) {
				LOGGER.log(Level.INFO, "SecurityManager not instatiated... Loading DefaultSecurityManager!");
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
			LOGGER.log(Level.INFO, "Setting SecurityManager to null... Loading DefaultSecurityManager!");
			this.secManager = new DefaultSecurityManager();
		} else {
			LOGGER.log(Level.INFO, "Setting SecurityManager to instance of class "
					+ secManager.getClass().getPackage().getName() + "." + secManager.getClass().getName());
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
			LOGGER.log(Level.SEVERE, "Exception occured authenticating user! Returning false!", e);
			return false;
		}
	}

	public boolean authorizeNow(final IResource resource, final IUser user, final IOperation op) {
		try {
			return SecurityManagerFactory.getSecurityManager().authorize(resource, user, op);
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Exception occured authorizing user for operation! Returning false!", e);
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
