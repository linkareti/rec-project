/* 
 * BusinessServiceEnum.java created on Apr 14, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils.locator;

/**
 * 
 * @author artur
 */
public enum BusinessServiceEnum {
	REPOSITORY_FACADE("java:global/rec.am/.RepositoryFacade!com.linkare.rec.am.RepositoryFacade"), ALLOCATION_MANAGER(
			"java:global/rec.am/AllocationManager!com.linkare.rec.am.AllocationManager");

	private final String jndiName;

	private BusinessServiceEnum(final String jndiName) {
		this.jndiName = jndiName;
	}

	public String getJNDIName() {
		return this.jndiName;
	}
}
