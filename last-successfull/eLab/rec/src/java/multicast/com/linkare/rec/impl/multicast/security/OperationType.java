/* 
 * OperationType.java created on 17 May 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.multicast.security;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public enum OperationType {
	/**
	 * MCC Operation - Operation that enumerates all the hardwares from a CORBA
	 * Naming Context. Restricting this operation would not enable an MCC to
	 * have a list of all the registered Hardwares at some naming system. This
	 * way you can use one single COS Naming System for many MCC's
	 */
	OP_ENUM_HARDWARES("Enumerate Hardwares"),
	/**
	 * MCC Operation - From the client side, is it possible to enumerate or list
	 * the hardwares?
	 */
	OP_LIST_HARDWARE("List Hardwares"),
	/**
	 * MCC Operation and MCH Operation - You have chat system by MCC and/or by
	 * MCH. So you theory you can send a message to all the users connected to
	 * the same MCC or only to those in a specific MCH
	 */
	OP_SEND_MESSAGE("Send Message"),
	/**
	 * MCH Operation - May you send a configuration for the Hardware?
	 */
	OP_CONFIG("Configure"),
	/**
	 * MCH Operation - May you send a start to the Hardware?
	 */
	OP_START("Start"),
	/**
	 * MCH Operation - May you send a start (with output return - bidir comm) to
	 * the Hardware?
	 */
	OP_START_OUTPUT("Start Output"),
	/**
	 * MCH and DP Operation - May you stop the hardware or the DataProducer?
	 */
	OP_STOP("Stop"),
	/**
	 * MCH Operation - May you reset the hardware?
	 */
	OP_RESET("Reset"),
	/**
	 * MCC and MCH Operation - May you list the "other" users listed at some
	 * specific MCC or MCH?
	 */
	OP_ENUM_USERS("Enumerate Users"),
	/**
	 * MCC and MCH Operation - May you list the "user data" of some user listed
	 * at some specific MCC or MCH?
	 */
	OP_LIST_USER("List User"),
	/**
	 * MCH Operation - May you lock the hardware for X time to configure or
	 * start it afterwards?
	 */
	OP_LOCK("Lock"),
	/**
	 * MCH Operation - May you get the reference to a Data Producer?
	 */
	OP_GET_DATAPRODUCER("Get DataProducer"),
	/**
	 * DP Operation - May you get some samples?
	 */
	OP_GET_SAMPLES("Get Samples"),
	/**
	 * MCH Operation - May you find the Hardware Current State?
	 */
	OP_GET_HARDWARESTATE("Get Hardware State"),
	/**
	 * DP Operation - May you find the DP Current State?
	 */
	OP_GET_DATAPRODUCERSTATE("Get DataProducer State"),
	/**
	 * MCH Operation - May you find the Hardware Information? Settings, State
	 * and Metadata?
	 */
	OP_GET_HARDWAREINFO("Get Hardware Info"),
	/**
	 * When the operation is not known
	 */
	UNKNOWN("Unknown");

	private String description = null;

	private OperationType(String description) {
		this.description = description;
	}

	public String toString() {
		return this.description;
	}

}
