/* 
 * BusinessServiceEnum.java created on Apr 14, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils.locator;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public enum BusinessServiceEnum {
	REPOSITORY_FACADE("java:global/rec.am/RepositoryFacadeBean!com.linkare.rec.web.RepositoryFacade"), ALLOCATION_MANAGER(
			"java:global/rec.am/AllocationManager!com.linkare.rec.web.AllocationManager"), REC_SERVICE(
			"java:global/rec.am/RecService!com.linkare.rec.web.RecServiceRemote");

	private final String jndiName;

	private BusinessServiceEnum(final String jndiName) {
		this.jndiName = jndiName;
	}

	public String getJNDIName() {
		return this.jndiName;
	}
}
