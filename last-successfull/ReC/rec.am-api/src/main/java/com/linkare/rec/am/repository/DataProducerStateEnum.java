package com.linkare.rec.am.repository;

import java.util.HashMap;
import java.util.Map;

public enum DataProducerStateEnum {

    WAITING((byte) 0), STARTED_NODATA((byte) 1), STARTED((byte) 2), ENDED((byte) 3), STOPED((byte) 4), ERROR((byte) 5);

    private final Byte code;

    private DataProducerStateEnum(final byte code) {
	this.code = Byte.valueOf(code);
    }

    //by efective java 
    private static final Map<Byte, DataProducerStateEnum> byteToEnum = new HashMap<Byte, DataProducerStateEnum>();

    static {

	for (final DataProducerStateEnum state : DataProducerStateEnum.values()) {
	    byteToEnum.put(state.code, state);
	}
    }

    public static DataProducerStateEnum fromByte(final byte code) {
	return byteToEnum.get(Byte.valueOf(code));
    }

    public byte getCode() {
	return code.byteValue();
    }

}
