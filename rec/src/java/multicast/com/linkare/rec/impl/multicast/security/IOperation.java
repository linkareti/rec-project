/*
 * IResource.java
 *
 * Created on 2 de Janeiro de 2004, 15:22
 */

package com.linkare.rec.impl.multicast.security;

import java.util.Map;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public interface IOperation extends java.io.Serializable {

	/**
	 * MCC Operation - Operation that enumerates all the hardwares from a CORBA
	 * Naming Context. Restricting this operation would not enable an MCC to
	 * have a list of all the registered Hardwares at some naming system. This
	 * way you can use one single COS Naming System for many MCC's
	 */
	public static final short OP_ENUM_HARDWARES = 0;
	/**
	 * MCC Operation - From the client side, is it possible to enumerate or list
	 * the hardwares?
	 */
	public static final short OP_LIST_HARDWARE = 1;
	/**
	 * MCC Operation and MCH Operation - You have chat system by MCC and/or by
	 * MCH. So you theory you can send a message to all the users connected to
	 * the same MCC or only to those in a specific MCH
	 */
	public static final short OP_SEND_MESSAGE = 2;
	/**
	 * MCH Operation - May you send a configuration for the Hardware?
	 */
	public static final short OP_CONFIG = 3;
	/**
	 * MCH Operation - May you send a start to the Hardware?
	 */
	public static final short OP_START = 4;
	/**
	 * MCH Operation - May you send a start (with output return - bidir comm) to
	 * the Hardware?
	 */
	public static final short OP_START_OUTPUT = 5;
	/**
	 * MCH and DP Operation - May you stop the hardware or the DataProducer?
	 */
	public static final short OP_STOP = 6;
	/**
	 * MCH Operation - May you reset the hardware?
	 */
	public static final short OP_RESET = 7;
	/**
	 * MCC and MCH Operation - May you list the "other" users listed at some
	 * specific MCC or MCH?
	 */
	public static final short OP_ENUM_USERS = 8;
	/**
	 * MCC and MCH Operation - May you list the "user data" of some user listed
	 * at some specific MCC or MCH?
	 */
	public static final short OP_LIST_USER = 9;
	/**
	 * MCH Operation - May you lock the hardware for X time to configure or
	 * start it afterwards?
	 */
	public static final short OP_LOCK = 10;
	/**
	 * MCH Operation - May you get the reference to a Data Producer?
	 */
	public static final short OP_GET_DATAPRODUCER = 11;
	/**
	 * DP Operation - May you get some samples?
	 */
	public static final short OP_GET_SAMPLES = 12;
	/**
	 * MCH Operation - May you find the Hardware Current State?
	 */
	public static final short OP_GET_HARDWARESTATE = 13;
	/**
	 * DP Operation - May you find the DP Current State?
	 */
	public static final short OP_GET_DATAPRODUCERSTATE = 14;
	/**
	 * MCH Operation - May you find the Hardware Information? Settings, State
	 * and Metadata?
	 */
	public static final short OP_GET_HARDWAREINFO = 15;

	public static final String PROPKEY_ACQ_CONFIG = "MCHardware.AcquisitionConfig";
	public static final String PROPKEY_USERID_OTHER = "MC.UserID.Other";
	public static final String PROPKEY_HARDWARESTATE = "MCHardware.HardwareState";
	public static final String PROPKEY_REMOTE_DATAPRODUCER = "MCHardware.RemoteDataProducer";
	public static final String PROPKEY_HARDWARE_ID = "MCHardware.UniqueID";
	public static final String PROPKEY_HARDWAREINFO = "MCHardware.HardwareInfo";

	short getOperation();

	Map<String, Object> getProperties();

}
