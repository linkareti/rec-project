/* 
 * DefaultSecurityManager.java created on 14 Feb 2013
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.multicast.security;

import java.util.List;

import com.linkare.rec.impl.multicast.ReCMultiCastHardware;

class DefaultSecurityManager implements ISecurityManager {

	@Override
	public boolean authenticate(final IResource resource, final IUser user) {
		return true;
	}

	@Override
	public boolean authorize(final IResource resource, final IUser user, final IOperation op) {
		return true;
	}

	@Override
	public void registerMultiCastHardware(final List<ReCMultiCastHardware> multiCastHardwares) {
		// this security implementation doesn't require the multicast
		// hardwares
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerSecurityCommunicator(final ISecurityCommunicator communicator) {
		// this security implementation doesn't require the multicast
		// hardwares
	}
}