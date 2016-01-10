/* 
 * ReCSystemPropertyLocation.java created on 18 Feb 2013
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.config;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public enum ReCSystemPropertyLocation {
	/**
	 * The property in question should be defined at the client side
	 */
	CLIENT,
	/**
	 * The property in question should be defined at the Multicast side
	 */
	MULTICAST,
	/**
	 * The property in question should be defined at the Hardware(Driver) side
	 */
	HARDWARE;
}
