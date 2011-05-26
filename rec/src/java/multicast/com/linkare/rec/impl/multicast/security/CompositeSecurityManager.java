/* 
 * CompositeSecurityManager.java created on 7 May 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.multicast.security;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.multicast.ReCMultiCastHardware;

/**
 * 
 * @author jpereira
 */
public class CompositeSecurityManager implements ISecurityManager {

	private List<ISecurityManager> securityManagers = null;

	public static final String SYSPROP_SECURITY_MANAGERS_CLASS_LIST = "ReC.MultiCast.CompositeSecurityManager.list";

	public static final String MCCONTROLLER_SECURITYMANAGER_LOGGER = "ReC.MultiCast.SecurityManager.Logger";

	static {
		final Logger l = LogManager.getLogManager().getLogger(
				CompositeSecurityManager.MCCONTROLLER_SECURITYMANAGER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(
					Logger.getLogger(CompositeSecurityManager.MCCONTROLLER_SECURITYMANAGER_LOGGER));
		}
	}

	private void loadSecurityManagers() {
		securityManagers = new ArrayList<ISecurityManager>();
		final String secManagersClassName = System
				.getProperty(CompositeSecurityManager.SYSPROP_SECURITY_MANAGERS_CLASS_LIST);
		if (secManagersClassName == null || secManagersClassName.trim().length() == 0) {
			Logger.getLogger(CompositeSecurityManager.MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
					"SecurityManager System Property not found... Loading DefaultSecurityManager!");
			return;
		}
		final String[] classNames = secManagersClassName.split(",");
		for (final String className : classNames) {
			try {
				securityManagers.add((ISecurityManager) Class.forName(className).newInstance());
			} catch (final Exception e) {
				Logger.getLogger(CompositeSecurityManager.MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
						"Unable to load SecurityManager defined at system : " + className + " - ignoring!");
				LoggerUtil.logThrowable("Error loading specified SecurityManager", e,
						Logger.getLogger(CompositeSecurityManager.MCCONTROLLER_SECURITYMANAGER_LOGGER));
			} catch (final LinkageError e) {
				Logger.getLogger(CompositeSecurityManager.MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
						"Unable to load SecurityManager defined at system : " + className + "!");
				LoggerUtil.logThrowable("Error loading specified SecurityManager", e,
						Logger.getLogger(CompositeSecurityManager.MCCONTROLLER_SECURITYMANAGER_LOGGER));
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
