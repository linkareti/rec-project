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

	private ISecurityManager secManager = null;

	public static final String SYSPROP_SECURITY_MANAGER_CLASS = "rec.multicast.securitymanager";

	private static final Logger LOGGER = Logger.getLogger(SecurityManagerFactory.class.getName());

	// Be careful about changing the order of this field. Because the
	// SecurityManagerFactory constructor is being called, the <classinit> is
	// being delayed by the <init>, and therefore,
	// any method called from the <init> may not have all static references
	// initialized.
	private static SecurityManagerFactory INSTANCE = new SecurityManagerFactory();

	private void loadSecurityManager() {
		final String secManagerClassName = System.getProperty(SecurityManagerFactory.SYSPROP_SECURITY_MANAGER_CLASS);

		if (secManagerClassName == null || secManagerClassName.trim().length() == 0) {
			if (LOGGER.isLoggable(Level.WARNING)) {
				LOGGER.log(Level.WARNING, "SecurityManager System Property '" + SYSPROP_SECURITY_MANAGER_CLASS
						+ "' not defined... Loading DefaultSecurityManager!");
			}
			secManager = new DefaultSecurityManager();
		}

		if (secManagerClassName.contains(",")) {
			if (LOGGER.isLoggable(Level.FINE)) {
				LOGGER.log(Level.FINE, "Security Manager configuration has multiple values '" + secManagerClassName
						+ "', so composing them through the " + CompositeSecurityManager.class);
			}
			secManager = new CompositeSecurityManager(secManagerClassName);
		} else {
			try {
				if (LOGGER.isLoggable(Level.FINE)) {
					LOGGER.log(Level.FINE, "Trying to load the SecurityManager class [" + secManagerClassName + "]");
				}
				final Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(secManagerClassName);
				if (clazz != null) {
					secManager = (ISecurityManager) Class.forName(secManagerClassName).newInstance();
				}
			} catch (final Exception e) {
				LOGGER.log(Level.WARNING, "Unable to load SecurityManager defined at system : " + secManagerClassName
						+ " - loading DefaultSecurityManager!", e);
			} catch (final LinkageError e) {
				LOGGER.log(Level.WARNING, "Unable to load SecurityManager defined at system : " + secManagerClassName
						+ " - loading DefaultSecurityManager!", e);
			} finally {
				if (secManager == null) {
					secManager = new DefaultSecurityManager();
				}
			}
		}
	}

	/** Creates a new instance of SecurityManagerFactory */
	private SecurityManagerFactory() {
		loadSecurityManager();
	}

	public static boolean authenticate(final IResource resource, final IUser user) {
		try {
			return INSTANCE.secManager.authenticate(resource, user);
		} catch (final Exception e) {
			SecurityManagerFactory.LOGGER.log(Level.WARNING, "Exception occured authenticating user! Returning false!",
					e);
			return false;
		}
	}

	public static boolean authorize(final IResource resource, final IUser user, final IOperation op) {
		try {
			return INSTANCE.secManager.authorize(resource, user, op);
		} catch (final Exception e) {
			SecurityManagerFactory.LOGGER.log(Level.WARNING,
					"Exception occured authorizing user for operation! Returning false!", e);
			return false;
		}
	}

	/**
	 * @param multiCastHardwares
	 */
	public static void registerMultiCastHardware(List<ReCMultiCastHardware> multiCastHardwares) {
		INSTANCE.secManager.registerMultiCastHardware(multiCastHardwares);
	}

	/**
	 * @param secCommunicator
	 */
	public static void registerSecurityCommunicator(ISecurityCommunicator secCommunicator) {
		INSTANCE.secManager.registerSecurityCommunicator(secCommunicator);
	}

}
