package com.linkare.rec.impl.multicast.repository;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.multicast.repository.impl.FSRepository;
import com.linkare.rec.utils.ClassUtils;

/**
 * 
 * 
 * @author Artur Correia - Linkare TI
 */
public final class RepositoryFactory {

	private static final Logger LOGGER = Logger.getLogger(RepositoryFactory.class.getName());

	private static IRepository REPOSITORY = null;

	static {

		final String repositoryClassName = ReCSystemProperty.MULTICAST_REPOSITORY_CLASSNAME.getValue();

		try {
			LOGGER.log(Level.FINE, "Trying to load the Repository class [" + repositoryClassName + "]");
                                               
			REPOSITORY = (IRepository) ClassUtils.findClass(repositoryClassName, RepositoryFactory.class.getClassLoader()).getDeclaredConstructor().newInstance();

		} catch (Exception e) {
			LOGGER.log(Level.INFO,
					new StringBuilder("Unable to load Repository defined by className : ").append(repositoryClassName)
							.append(" - loading DefaultSecurityManager!").toString());
			LOGGER.log(Level.SEVERE, "Error loading specified SecurityManager", e);
		} catch (LinkageError e) {
			LOGGER.log(Level.INFO,
					new StringBuilder("Unable to load Repository defined by className : ").append(repositoryClassName)
							.append(" - loading DefaultRepository!").toString());
			LOGGER.log(Level.SEVERE, "Error loading specified SecurityManager", e);
		} finally {
			if (REPOSITORY == null) {
				LOGGER.log(Level.INFO, "Respository not instantiated... Loading FSRepository!");
				REPOSITORY = new FSRepository();
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
