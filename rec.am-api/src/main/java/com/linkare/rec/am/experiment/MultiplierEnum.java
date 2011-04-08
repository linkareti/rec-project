package com.linkare.rec.am.experiment;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public enum MultiplierEnum {
    FENTO((byte) 0), PICO((byte) 1), NANO((byte) 2), MICRO((byte) 3), MILI((byte) 4), NONE((byte) 5), KILO((byte) 6), MEGA((byte) 7), GIGA((byte) 8), TERA(
	    (byte) 9);

    private final Byte code;

    private MultiplierEnum(final byte code) {
	this.code = Byte.valueOf(code);
    }

    //by efective java 
    private static final Map<Byte, MultiplierEnum> byteToEnum = new HashMap<Byte, MultiplierEnum>();

    static {

	for (final MultiplierEnum multiplier : MultiplierEnum.values()) {
	    byteToEnum.put(multiplier.code, multiplier);
	}
    }

    public static MultiplierEnum fromByte(final byte code) {
	return byteToEnum.get(Byte.valueOf(code));
    }

    public byte getCode() {
	return code.byteValue();
    }

}