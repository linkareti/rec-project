/* 
 * ExperimentResultsManagerFactory.java created on Apr 14, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.multicast.repository;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.multicast.repository.impl.FSRepository;

/**
 * 
 * 
 * @author Artur Correia - Linkare TI
 */
public final class RepositoryFactory {

	private static final Logger LOG = Logger.getLogger(RepositoryFactory.class.getName());

	private static final String SYSPROP_REPOSITORY_CLASS = "ReC.MultiCast.Repository";

	private static IRepository REPOSITORY = null;

	static {

		final String repositoryClassName = System.getProperty(SYSPROP_REPOSITORY_CLASS);

		if (repositoryClassName == null || repositoryClassName.trim().length() == 0) {

			LOG.log(Level.INFO, "Repository System Property not found... Loading DefaultRepository!");
			REPOSITORY = new FSRepository();

		} else {
			try {
				LOG.log(Level.FINE, "Trying to load the Repository class [" + repositoryClassName + "]");

				REPOSITORY = (IRepository) Class.forName(repositoryClassName).newInstance();

			} catch (Exception e) {
				LOG.log(Level.INFO,
						new StringBuilder("Unable to load Repository defined by className : ")
								.append(repositoryClassName).append(" - loading DefaultSecurityManager!").toString());
				LoggerUtil.logThrowable("Error loading specified SecurityManager", e, LOG);
			} catch (LinkageError e) {
				LOG.log(Level.INFO,
						new StringBuilder("Unable to load Repository defined by className : ")
								.append(repositoryClassName).append(" - loading DefaultRepository!").toString());
				LoggerUtil.logThrowable("Error loading specified SecurityManager", e, LOG);
			} finally {
				if (REPOSITORY == null) {
					LOG.log(Level.INFO, "Respository not instantiated... Loading FSRepository!");
					REPOSITORY = new FSRepository();
				}
			}
		}

	}

	private RepositoryFactory() {
		throw new UnsupportedOperationException("instance creation not available");
	}

	public static IRepository getRepository() {
		return REPOSITORY;
	}

}
