package com.linkare.rec.am.model;

/**
 * Represents the possible states of the Physical Hardware.
 * 
 * @author Bruno Catarino - Linkare TI
 * 
 */
public enum HardwareState {
    
    UNKNOWN((byte) 0), CONFIGURING((byte) 1), CONFIGURED((byte) 2), STARTING((byte) 3), STARTED((byte) 4), STOPING((byte) 5), STOPED((byte) 6), RESETING(
	    (byte) 7), RESETED((byte) 8);

    private byte code;

    private HardwareState(byte code) {
	this.code = code;
    }

    /**
     * Returns the enum value of the state for a given state code.
     * 
     * @param code
     * @return
     */
    public static HardwareState valueOfCode(byte code) {
	for (HardwareState state : HardwareState.values()) {
	    if (state.code == code) {
		return state;
	    }
	}
	return null;
    }
}
