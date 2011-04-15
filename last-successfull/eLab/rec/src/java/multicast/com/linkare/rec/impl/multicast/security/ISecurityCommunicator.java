/* 
 * ISecurityCommunicator.java created on 13 Sep 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.multicast.security;

/**
 * 
 * @author npadriano
 */
public interface ISecurityCommunicator {

	/**
	 * Send a message from multicast to clients that are going to be shutdown by the SecurityManager.
	 * 
	 * @param clientTo
	 * @param message
	 */
	void sendMulticastMessage(String clientTo, String message);

}
