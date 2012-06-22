package com.linkare.rec.am;

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
    private String newState;
    private String oldSate;

    @ConstructorProperties({ "hardwareUniqueID", "newState", "oldState" })
    public HardwareStateChangeDTO(String hardwareUniqueID, String newState, String oldSate) {
	super();
	this.hardwareUniqueID = hardwareUniqueID;
	this.newState = newState;
	this.oldSate = oldSate;
    }

    public String getHardwareUniqueID() {
	return hardwareUniqueID;
    }

    public void setHardwareUniqueID(String hardwareUniqueID) {
	this.hardwareUniqueID = hardwareUniqueID;
    }

    public String getNewState() {
	return newState;
    }

    public void setNewState(String newState) {
	this.newState = newState;
    }

    public String getOldSate() {
	return oldSate;
    }

    public void setOldSate(String oldSate) {
	this.oldSate = oldSate;
    }

}
