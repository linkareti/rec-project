/* 
 * RemoteRepository.java created on Apr 20, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.multicast.repository.impl;

import com.linkare.rec.impl.multicast.ReCMultiCastDataProducer;
import com.linkare.rec.impl.multicast.repository.IRepository;
import com.linkare.rec.impl.multicast.repository.RepositoryException;
import com.linkare.rec.impl.multicast.repository.RepositoryFacadeDelegate;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class RemoteRepository implements IRepository {
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
