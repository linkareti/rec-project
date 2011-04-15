/* 
 * ExperimentResultsManagerFactory.java created on Apr 14, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.multicast.repository;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.multicast.ReCMultiCastDataProducer;
import com.linkare.rec.impl.utils.SerializationHelper;

/**
 * 
 * 
 * @author Artur Correia - Linkare TI
 */
public final class RepositoryFactory {

	private static final Logger LOG = Logger.getLogger(RepositoryFactory.class.getName());

	public static final String SYSPROP_REPOSITORY_CLASS = "ReC.MultiCast.Repository";

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

	public static IRepository getRepository() throws RepositoryException {
		return REPOSITORY;
	}

	/**
	 * 
	 * @author Artur Correia - Linkare TI
	 */
	public static class FSRepository implements IRepository {

		private static final String baseDir = new StringBuilder(System.getProperty("user.dir")).append(File.separator)
				.append("DataProducers").toString();

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void persistExperimentResult(final Object experimentResult, final String oid) throws RepositoryException {

			try {
				final FSInformation fsInformation = new FSInformation(oid);
				SerializationHelper.writeObject(fsInformation.getFilename(), fsInformation.getDir(), experimentResult);
			} catch (IOException e) {
				throw new RepositoryException(e);
			}

		}

		/**
		 * {@inheritDoc}
		 * 
		 * @throws RepositoryException
		 */
		@Override
		public Object getExperimentResult(final String oid) throws RepositoryException {
			try {
				final FSInformation fsInformation = new FSInformation(oid);
				return SerializationHelper.readObject(fsInformation.getFilename(), fsInformation.getDir());
			} catch (IOException e) {
				throw new RepositoryException(e);
			} catch (ClassNotFoundException e) {
				throw new RepositoryException(e);
			}
		}

		/**
		 * 
		 * Helper class. Immutable object
		 * 
		 * 
		 * @author Artur Correia - Linkare TI
		 */
		private class FSInformation {

			private final String dir;
			private final String filename;

			private FSInformation(final String filename) {
				final int barPos = filename.lastIndexOf(File.separator);
				this.dir = new StringBuilder(baseDir).append(File.separator).append(filename.substring(0, barPos))
						.toString();
				this.filename = filename.substring(barPos + 1);
			}

			public String getDir() {
				return dir;
			}

			public String getFilename() {
				return filename;
			}

		}
	}

	public static class RemoteRepository implements IRepository {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void persistExperimentResult(Object experimentResult, String oid) throws RepositoryException {

			if (experimentResult == null || oid == null) {
				throw new NullPointerException("experimentResult or oid cannot be null");
			}

			if (!(experimentResult instanceof ReCMultiCastDataProducer)) {
				throw new IllegalArgumentException("experiment Result must be a instanceOf ReCMultiCastDataProducer");
			}
			// final ReCMultiCastDataProducer dataProducer =
			// (ReCMultiCastDataProducer) experimentResult;
			// if (dataProducer.getOID() == null ||
			// dataProducer.getOID().equals(oid)) {
			//
			// }

			final RepositoryFacadeDelegate delegate = new RepositoryFacadeDelegate();
			// FIXME: user ???? where can i now the user associated with
			// experiment to persist
			delegate.persistExperiment((ReCMultiCastDataProducer) experimentResult, "user");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getExperimentResult(String oid) throws RepositoryException {
			return new RepositoryFacadeDelegate().getExperiment(oid);
		}

	}

}
