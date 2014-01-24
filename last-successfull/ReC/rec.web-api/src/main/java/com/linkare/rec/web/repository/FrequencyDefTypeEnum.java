/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.web.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public enum FrequencyDefTypeEnum {
    FREQUENCY_TYPE(0), SAMPLING_INTERVAL_TYPE(1);

    private final Integer code;

    private FrequencyDefTypeEnum(final int code) {
	this.code = code;
    }

    //by efective java 
    private static final Map<Integer, FrequencyDefTypeEnum> intToEnum = new HashMap<Integer, FrequencyDefTypeEnum>();

    static {

	for (final FrequencyDefTypeEnum frequencyDefType : FrequencyDefTypeEnum.values()) {
	    intToEnum.put(frequencyDefType.code, frequencyDefType);
	}
    }

    public static FrequencyDefTypeEnum fromInt(final int code) {
	return intToEnum.get(Integer.valueOf(code));
    }

    public int getCode() {
	return code.intValue();
    }

}