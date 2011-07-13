/* 
 * FSRepository.java created on Apr 20, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.multicast.repository.impl;

import java.io.File;
import java.io.IOException;

import com.linkare.rec.impl.multicast.repository.IRepository;
import com.linkare.rec.impl.multicast.repository.RepositoryException;
import com.linkare.rec.impl.utils.SerializationHelper;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class FSRepository implements IRepository {

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
