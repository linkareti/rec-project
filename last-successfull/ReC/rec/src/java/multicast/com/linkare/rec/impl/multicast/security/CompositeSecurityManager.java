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

/**
 * 
 * @author jpereira
 */
public class CompositeSecurityManager implements ISecurityManager {

	private List<ISecurityManager> securityManagers = null;

	public static final String SYSPROP_SECURITY_MANAGERS_CLASS_LIST = "ReC.MultiCast.CompositeSecurityManager.list";

	public static final String MCCONTROLLER_SECURITYMANAGER_LOGGER = "ReC.MultiCast.SecurityManager.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER));
		}
	}

	private void loadSecurityManagers() {
		securityManagers = new ArrayList<ISecurityManager>();
		String secManagersClassName = System.getProperty(SYSPROP_SECURITY_MANAGERS_CLASS_LIST);
		if (secManagersClassName == null || secManagersClassName.trim().length() == 0) {
			Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
					"SecurityManager System Property not found... Loading DefaultSecurityManager!");
			return;
		}
		String[] classNames = secManagersClassName.split(",");
		for (String className : classNames) {
			try {
				securityManagers.add((ISecurityManager) Class.forName(className).newInstance());
			} catch (Exception e) {
				Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
						"Unable to load SecurityManager defined at system : " + className + " - ignoring!");
				LoggerUtil.logThrowable("Error loading specified SecurityManager", e, Logger
						.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER));
			} catch (LinkageError e) {
				Logger.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER).log(Level.INFO,
						"Unable to load SecurityManager defined at system : " + className + "!");
				LoggerUtil.logThrowable("Error loading specified SecurityManager", e, Logger
						.getLogger(MCCONTROLLER_SECURITYMANAGER_LOGGER));
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
	public boolean authenticate(IResource resource, IUser user) {
		boolean retVal = true;
		if (securityManagers != null) {
			for (ISecurityManager secManager : securityManagers) {
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
	public boolean authorize(IResource resource, IUser user, IOperation op) {
		boolean retVal = true;
		if (securityManagers != null) {
			for (ISecurityManager secManager : securityManagers) {
				retVal = retVal && secManager.authorize(resource, user, op);
				if (!retVal) {
					break;
				}
			}
		}
		return retVal;
	}

}
