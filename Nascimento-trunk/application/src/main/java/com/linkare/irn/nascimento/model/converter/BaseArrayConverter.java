package com.linkare.irn.nascimento.model.converter;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public abstract class BaseArrayConverter extends BaseJsonConverter {

    public static <T> String convertToDatabaseColumn(T[] attribute) {
	final ObjectMapper mapper = getMapper();
	if (attribute == null || attribute.length == 0) {
	    return "";
	}
	try {
	    return mapper.writeValueAsString(attribute);
	} catch (JsonProcessingException e) {
	    return null;
	}
    }

    public static <T> T[] convertToEntityAttribute(String dbData, final Class<T[]> clazz) {
	final ObjectMapper mapper = getMapper();
	try {
	    if (StringUtils.isBlank(dbData)) {
		return null;
	    }
	    return mapper.readValue(dbData, clazz);
	} catch (IOException e) {
	    return null;
	}
    }
}