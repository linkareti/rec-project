package com.linkare.rec.web;

import java.beans.ConstructorProperties;
import java.io.Serializable;

/**
 * 
 * @author Artur Correia - Linkare TI
 * 
 */
public class HardwareStateChangeDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String hardwareUniqueID;
    private byte newStateCode;
    private byte oldSateCode;

    @ConstructorProperties({ "hardwareUniqueID", "newStateCode", "oldSateCode" })
    public HardwareStateChangeDTO(String hardwareUniqueID, byte newState, byte oldSate) {
	super();
	this.hardwareUniqueID = hardwareUniqueID;
	this.newStateCode = newState;
	this.oldSateCode = oldSate;
    }

    public String getHardwareUniqueID() {
	return hardwareUniqueID;
    }

    public byte getNewStateCode() {
	return newStateCode;
    }

    public byte getOldSateCode() {
	return oldSateCode;
    }

}
