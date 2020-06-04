package com.linkare.irn.nascimento.model.converter;

import java.io.IOException;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.persistence.Converter;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Converter(autoApply = true)
public class InternetAddressListConverter extends BaseEmbeddableListConverter<InternetAddress> {

    @Override
    public String convertToDatabaseColumn(List<InternetAddress> attribute) {
	final ObjectMapper mapper = getMapper();
	mapper.addMixIn(InternetAddress.class, InternetAddressJsonMixin.class);
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
    public List<InternetAddress> convertToEntityAttribute(String dbData) {
	final ObjectMapper mapper = getMapper();
	mapper.addMixIn(InternetAddress.class, InternetAddressJsonMixin.class);
	try {
	    if (StringUtils.isBlank(dbData)) {
		return null;
	    }
	    return mapper.readValue(dbData, new TypeReference<List<InternetAddress>>() {
	    });
	} catch (IOException e) {
	    return null;
	}
    }
}