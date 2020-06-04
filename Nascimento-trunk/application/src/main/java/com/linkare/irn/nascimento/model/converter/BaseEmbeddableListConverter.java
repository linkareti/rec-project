package com.linkare.irn.nascimento.model.converter;

import java.io.IOException;
import java.util.List;

import javax.persistence.AttributeConverter;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public abstract class BaseEmbeddableListConverter<T> extends BaseJsonConverter implements AttributeConverter<List<T>, String> {

    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
	final ObjectMapper mapper = getMapper();
	if (attribute == null || attribute.isEmpty()) {
	    return "";
	}
	try {
	    return mapper.writeValueAsString(attribute);
	} catch (JsonProcessingException e) {
	    return null;
	}
    }

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
	final ObjectMapper mapper = getMapper();
	try {
	    if (StringUtils.isBlank(dbData)) {
		return null;
	    }
	    return mapper.readValue(dbData, new TypeReference<List<T>>() {
	    });
	} catch (IOException e) {
	    return null;
	}
    }
}