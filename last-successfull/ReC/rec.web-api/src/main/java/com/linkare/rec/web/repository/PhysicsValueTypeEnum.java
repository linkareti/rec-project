package com.linkare.rec.web.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public enum PhysicsValueTypeEnum {

    BOOLEAN_VAL(0), BYTE_VAL(1), SHORT_VAL(2), INT_VAL(3), LONG_VAL(4), FLOAT_VAL(5), DOUBLE_VAL(6), BYTEARRAY_VAL(7);

    private final Integer code;

    private PhysicsValueTypeEnum(final int value) {
	this.code = value;
    }

    //by efective java 
    private static final Map<Integer, PhysicsValueTypeEnum> intToEnum = new HashMap<Integer, PhysicsValueTypeEnum>();

    static {

	for (final PhysicsValueTypeEnum physicsValue : PhysicsValueTypeEnum.values()) {
	    intToEnum.put(physicsValue.code, physicsValue);
	}
    }

    public static PhysicsValueTypeEnum fromInt(final int code) {
	return intToEnum.get(Integer.valueOf(code));
    }

    public int getCode() {
	return code.intValue();
    }

}
