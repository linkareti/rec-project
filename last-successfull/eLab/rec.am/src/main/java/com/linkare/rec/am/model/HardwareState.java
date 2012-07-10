package com.linkare.rec.am.model;

import java.util.HashMap;
import java.util.Map;

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

    private static final Map<Byte, HardwareState> codeMap;
    static {
	codeMap = new HashMap<Byte, HardwareState>();

	for (final HardwareState state : HardwareState.values()) {
	    codeMap.put(Byte.valueOf(state.code), state);
	}
    }

    /**
     * Returns the enum value of the state for a given state code.
     * 
     * @param code
     * @return
     */
    public static HardwareState valueOfCode(byte code) {
	return codeMap.get(code);
    }
}
