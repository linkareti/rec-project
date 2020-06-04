package com.linkare.irn.nascimento.model.converter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public abstract class BaseJsonConverter {

    private static final ObjectMapper MAPPER;

    static {
	MAPPER = new ObjectMapper();
	MAPPER.setSerializationInclusion(Include.NON_NULL);
    }

    public static ObjectMapper getMapper() {
	return MAPPER;
    }
}