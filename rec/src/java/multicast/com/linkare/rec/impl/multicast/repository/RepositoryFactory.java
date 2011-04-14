/* 
 * ExperimentResultsManagerFactory.java created on Apr 14, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.multicast.repository;

import java.io.File;
import java.io.IOException;

import com.linkare.rec.impl.multicast.ReCMultiCastDataProducer;
import com.linkare.rec.impl.utils.SerializationHelper;

/**
 * 
 * 
 * @author Artur Correia - Linkare TI
 */
public final class RepositoryFactory {

	private RepositoryFactory() {
		throw new UnsupportedOperationException("instance creation not available");
	}

	private static final IRepository DEFAULT_REPOSITORY = new FSRepository();

	public static IRepository getRepository() throws RepositoryException {
		return null;
	}

	/**
	 * 
	 * @author Artur Correia - Linkare TI
	 */
	private static class FSRepository implements IRepository {

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

	private static class RemoteRepository implements IRepository {

		private final RepositoryFacadeDelegate delegate;

		private RemoteRepository() throws RepositoryException {
			delegate = new RepositoryFacadeDelegate();
		}

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
			// FIXME: user ???? where can i now the user associated with
			// experiment to persist
			delegate.persistExperiment((ReCMultiCastDataProducer) experimentResult, "user");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getExperimentResult(String oid) throws RepositoryException {
			return delegate.getExperiment(oid);
		}

	}

}
