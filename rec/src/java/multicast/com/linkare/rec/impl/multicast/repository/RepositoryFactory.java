package com.linkare.rec.impl.multicast.repository;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.multicast.repository.impl.FSRepository;

/**
 * 
 * 
 * @author Artur Correia - Linkare TI
 */
public final class RepositoryFactory {

	private static final Logger LOGGER = Logger.getLogger(RepositoryFactory.class.getName());

	private static final String SYSPROP_REPOSITORY_CLASS = "rec.multicast.repository";

	private static IRepository REPOSITORY = null;

	static {

		final String repositoryClassName = System.getProperty(SYSPROP_REPOSITORY_CLASS);

		if (repositoryClassName == null || repositoryClassName.trim().length() == 0) {

			LOGGER.log(Level.INFO, "Repository System Property not found... Loading DefaultRepository!");
			REPOSITORY = new FSRepository();

		} else {
			try {
				LOGGER.log(Level.FINE, "Trying to load the Repository class [" + repositoryClassName + "]");

				REPOSITORY = (IRepository) Class.forName(repositoryClassName).newInstance();

			} catch (Exception e) {
				LOGGER.log(
						Level.INFO,
						new StringBuilder("Unable to load Repository defined by className : ")
								.append(repositoryClassName).append(" - loading DefaultSecurityManager!").toString());
				LOGGER.log(Level.SEVERE, "Error loading specified SecurityManager", e);
			} catch (LinkageError e) {
				LOGGER.log(
						Level.INFO,
						new StringBuilder("Unable to load Repository defined by className : ")
								.append(repositoryClassName).append(" - loading DefaultRepository!").toString());
				LOGGER.log(Level.SEVERE, "Error loading specified SecurityManager", e);
			} finally {
				if (REPOSITORY == null) {
					LOGGER.log(Level.INFO, "Respository not instantiated... Loading FSRepository!");
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
