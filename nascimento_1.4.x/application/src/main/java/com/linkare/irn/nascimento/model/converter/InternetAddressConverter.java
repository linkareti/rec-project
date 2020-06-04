package com.linkare.irn.nascimento.model.converter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.internet.InternetAddress;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Converter(autoApply = true)
public class InternetAddressConverter extends BaseJsonConverter implements AttributeConverter<InternetAddress, String> {

    @Override
    public String convertToDatabaseColumn(InternetAddress attribute) {
	final ObjectMapper mapper = getMapper();
	mapper.addMixIn(InternetAddress.class, InternetAddressJsonMixin.class);
	if (attribute == null) {
	    return "";
	}
	try {
	    return mapper.writeValueAsString(attribute);
	} catch (JsonProcessingException e) {
	    return null;
	}
    }

    @Override
    public InternetAddress convertToEntityAttribute(String dbData) {
	final ObjectMapper mapper = getMapper();
	mapper.addMixIn(InternetAddress.class, InternetAddressJsonMixin.class);
	try {
	    if (StringUtils.isBlank(dbData)) {
		return null;
	    }
	    return mapper.readValue(dbData, InternetAddress.class);
	} catch (IOException e) {
	    return null;
	}
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
	System.out.println(new InternetAddressConverter().convertToDatabaseColumn(new InternetAddress("pzenida@linkare.com", "Paulo Zenida")));
    }
}