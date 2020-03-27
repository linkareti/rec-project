package com.linkare.rec.impl.multicast.security;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.multicast.ReCMultiCastHardware;

/**
 * @author José Pedro Pereira - Linkare TI
 */
public final class CompositeSecurityManager implements ISecurityManager {

	private List<ISecurityManager> securityManagers = new ArrayList<ISecurityManager>();

	private static final Logger LOGGER = Logger.getLogger(CompositeSecurityManager.class.getName());

	private final void loadSecurityManagers(final String secManagersClassName) {
		if (secManagersClassName == null || secManagersClassName.trim().length() == 0) {
			LOGGER.log(Level.WARNING, "SecurityManager System Property empty!");
		} else {
			final String[] classNames = secManagersClassName.split(",");
			for (final String className : classNames) {
				try {
					securityManagers.add((ISecurityManager) Thread.currentThread().getContextClassLoader()
							.loadClass(className).getDeclaredConstructor().newInstance());
				} catch (final Exception e) {
					LOGGER.log(Level.WARNING, "Unable to load SecurityManager defined at system : " + className
							+ " - ignoring!", e);
				} catch (final LinkageError e) {
					LOGGER.log(Level.WARNING, "Unable to load SecurityManager defined at system : " + className + "!",
							e);
				}
			}
		}
		if (securityManagers.size() == 0) {
			securityManagers.add(new DefaultSecurityManager());
		}
	}

	/**
	 * Creates the <code>CompositeSecurityManager</code>.
	 * 
	 * @param secManagersClassName
	 */
	public CompositeSecurityManager(final String secManagersClassName) {
		loadSecurityManagers(secManagersClassName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean authenticate(final IResource resource, final IUser user) {
		boolean retVal = true;
		for (final ISecurityManager secManager : securityManagers) {
			retVal = retVal && secManager.authenticate(resource, user);
			if (!retVal) {
				break;
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
		for (final ISecurityManager secManager : securityManagers) {
			retVal = retVal && secManager.authorize(resource, user, op);
			if (!retVal) {
				break;
			}
		}
		return retVal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerMultiCastHardware(final List<ReCMultiCastHardware> multiCastHardwares) {
		for (final ISecurityManager secManager : securityManagers) {
			secManager.registerMultiCastHardware(multiCastHardwares);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerSecurityCommunicator(final ISecurityCommunicator communicator) {
		for (final ISecurityManager secManager : securityManagers) {
			secManager.registerSecurityCommunicator(communicator);
		}
	}

}
