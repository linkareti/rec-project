/* 
 *RepositoryFacadeDelegate.java created on Apr 13, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.multicast.repository;

import java.rmi.RemoteException;

import com.linkare.rec.web.RepositoryFacade;
import com.linkare.rec.impl.multicast.ReCMultiCastDataProducer;
import com.linkare.rec.impl.utils.locator.BusinessServiceEnum;
import com.linkare.rec.impl.utils.locator.BusinessServiceLocator;
import com.linkare.rec.impl.utils.locator.BusinessServiceLocatorException;
import com.linkare.rec.impl.utils.mapping.DTOMapperUtils;
import com.linkare.rec.impl.utils.mapping.DTOMappingException;

/**
 * 
 * BusinessDelegate to RepositoryFacade
 * 
 * 
 * @author Artur Correia - Linkare TI
 */
public final class RepositoryFacadeDelegate {

	private final RepositoryFacade repositoryFacade;

	public RepositoryFacadeDelegate() throws RepositoryException {
		try {
			repositoryFacade = BusinessServiceLocator.getInstance().getBusinessInterface(
					BusinessServiceEnum.REPOSITORY_FACADE);
		} catch (BusinessServiceLocatorException e) {
			throw new RepositoryException(e);
		}
	}

	public void persistExperiment(final ReCMultiCastDataProducer dataProducer) throws RepositoryException {
		try {
			repositoryFacade.persistExperimentResults(DTOMapperUtils.mapToDataProducerDTO(dataProducer));
		} catch (DTOMappingException e) {
			throw new RepositoryException(e);
		} catch (RemoteException e) {
			throw new RepositoryException(e);
		}
	}

	public ReCMultiCastDataProducer getExperiment(final String oid) throws RepositoryException {
		try {

			if (oid == null) {
				throw new RepositoryException("oid cannot be null");
			}

			return DTOMapperUtils.mapToRecMultiCastDataProducer(repositoryFacade.getExperimentResultByOID(oid));
		} catch (RemoteException e) {
			throw new RepositoryException(e);
		} catch (DTOMappingException e) {
			throw new RepositoryException(e);
		}
	}
}
