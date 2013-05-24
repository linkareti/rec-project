/* 
 * RegisteredHardwareInfo.java created on Jun 21, 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils;

import java.util.List;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class RegisteredHardwareInfo {

	private String hardwareUniqueID;
	private byte state;
	private List<String> usersConnected;

	public RegisteredHardwareInfo(final String hardwareUniqueID, final byte state, final List<String> usersConnected) {
		super();
		this.hardwareUniqueID = hardwareUniqueID;
		this.state = state;
		this.usersConnected = usersConnected;
	}

	public RegisteredHardwareInfo() {
	}

	public String getHardwareUniqueID() {
		return hardwareUniqueID;
	}

	public void setHardwareUniqueID(final String hardwareUniqueID) {
		this.hardwareUniqueID = hardwareUniqueID;
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public List<String> getUsersConnected() {
		return usersConnected;
	}

	public void setUsersConnected(List<String> usersConnected) {
		this.usersConnected = usersConnected;
	}

}
