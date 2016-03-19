/* 
 * IExperimentResultsManager.java created on Apr 14, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.multicast.repository;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public interface IRepository {

	/**
	 * Persistence of the experimentResults
	 * 
	 * @param experimentResult
	 * @param oid
	 * @throws RepositoryException
	 */
	public void persistExperimentResult(final Object experimentResult, final String oid) throws RepositoryException;

	/**
	 * 
	 * @param oid
	 * @return
	 * @throws RepositoryException
	 */
	public Object getExperimentResult(final String oid) throws RepositoryException;

}
