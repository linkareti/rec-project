package com.linkare.rec.web.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@FacesConverter("longTypePkConverter")
public class LongTypePkConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	if (value == null || value.length() == 0) {
	    return null;
	}
	return Long.valueOf(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
	if (value != null && !(value instanceof Long)) {
	    throw new IllegalArgumentException("This converter only handles instances of Long");
	}
	if (value == null) {
	    return "";
	}
	if (value instanceof String) {
	    return (String) value;
	}
	return value.toString();
    }
}