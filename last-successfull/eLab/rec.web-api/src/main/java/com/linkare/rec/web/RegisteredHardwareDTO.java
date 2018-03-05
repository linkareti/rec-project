package com.linkare.rec.web;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Set;

public class RegisteredHardwareDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String hardwareUniqueID;
    private byte stateCode;
    private Set<String> usersConnected;

    @ConstructorProperties({ "hardwareUniqueID", "stateCode", "usersConnected" })
    public RegisteredHardwareDTO(final String hardwareUniqueID, final byte stateCode, final Set<String> usersConnected) {
	super();
	this.hardwareUniqueID = hardwareUniqueID;
	this.stateCode = stateCode;
	this.usersConnected = usersConnected;
    }

    public byte getStateCode() {
	return stateCode;
    }

    public String getHardwareUniqueID() {
	return hardwareUniqueID;
    }

    public void setHardwareUniqueID(String hardwareUniqueID) {
	this.hardwareUniqueID = hardwareUniqueID;
    }

    public Set<String> getUsersConnected() {
	return usersConnected;
    }

    public void setUsersConnected(Set<String> usersConnected) {
	this.usersConnected = usersConnected;
    }

    public void setStateCode(byte stateCode) {
	this.stateCode = stateCode;
    }


}
