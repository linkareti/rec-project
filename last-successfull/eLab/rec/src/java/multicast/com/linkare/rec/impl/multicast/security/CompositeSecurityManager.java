package com.linkare.rec.impl.multicast.security;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.multicast.ReCMultiCastHardware;

/**
 * @author José Pedro Pereira - Linkare TI
 */
public class CompositeSecurityManager implements ISecurityManager {

	private List<ISecurityManager> securityManagers = null;

	public static final String SYSPROP_SECURITY_MANAGERS_CLASS_LIST = "rec.multicast.compositesecuritymanager.list";

	private static final Logger LOGGER = Logger.getLogger(CompositeSecurityManager.class.getName());

	private void loadSecurityManagers() {
		securityManagers = new ArrayList<ISecurityManager>();
		final String secManagersClassName = System
				.getProperty(CompositeSecurityManager.SYSPROP_SECURITY_MANAGERS_CLASS_LIST);
		if (secManagersClassName == null || secManagersClassName.trim().length() == 0) {
			LOGGER.log(Level.INFO, "SecurityManager System Property not found... Loading DefaultSecurityManager!");
			return;
		}
		final String[] classNames = secManagersClassName.split(",");
		for (final String className : classNames) {
			try {
				securityManagers.add((ISecurityManager) Class.forName(className).newInstance());
			} catch (final Exception e) {
				LOGGER.log(Level.INFO, "Unable to load SecurityManager defined at system : " + className
						+ " - ignoring!");
				LOGGER.log(Level.SEVERE, "Error loading specified SecurityManager", e);
			} catch (final LinkageError e) {
				LOGGER.log(Level.INFO, "Unable to load SecurityManager defined at system : " + className + "!");
				LOGGER.log(Level.SEVERE, "Error loading specified SecurityManager", e);
			}
		}
	}

	/**
	 * Creates the <code>CompositeSecurityManager</code>.
	 */
	public CompositeSecurityManager() {
		loadSecurityManagers();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean authenticate(final IResource resource, final IUser user) {
		boolean retVal = true;
		if (securityManagers != null) {
			for (final ISecurityManager secManager : securityManagers) {
				retVal = retVal && secManager.authenticate(resource, user);
				if (!retVal) {
					break;
				}
			}
		}
		return retVal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean authorize(final IResource resource, final IUser user, final IOperation op) {
		boolean retVal = true;
		if (securityManagers != null) {
			for (final ISecurityManager secManager : securityManagers) {
				retVal = retVal && secManager.authorize(resource, user, op);
				if (!retVal) {
					break;
				}
			}
		}
		return retVal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerMultiCastHardware(final List<ReCMultiCastHardware> multiCastHardwares) {
		if (securityManagers != null) {
			for (final ISecurityManager secManager : securityManagers) {
				secManager.registerMultiCastHardware(multiCastHardwares);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerSecurityCommunicator(final ISecurityCommunicator communicator) {
		if (securityManagers != null) {
			for (final ISecurityManager secManager : securityManagers) {
				secManager.registerSecurityCommunicator(communicator);
			}
		}
	}

}
