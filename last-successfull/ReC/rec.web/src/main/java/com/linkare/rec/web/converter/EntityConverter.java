package com.linkare.rec.web.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.linkare.commons.jpa.DefaultDomainObject;

@FacesConverter("entityConverter")
public class EntityConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "RecPU")
    protected EntityManager em;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	if (value == null || value.length() == 0) {
	    return null;
	}
	final Long pk = new Long(value);
	return em.find(getClazz(context, component), pk);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
	if (value != null && !(value instanceof DefaultDomainObject)) {
	    throw new IllegalArgumentException("This converter only handles instances of DefaultDomainObject");
	}
	if (value == null) {
	    return "";
	}
	if (value instanceof String) {
	    return (String) value;
	}
	DefaultDomainObject entity = (DefaultDomainObject) value;
	return entity.getIdInternal() == null ? "" : entity.getIdInternal().toString();
    }

    // Gets the class corresponding to the context in jsf page
    @SuppressWarnings("unchecked")
    private Class<? extends DefaultDomainObject> getClazz(FacesContext facesContext, UIComponent component) {
	return (Class<? extends DefaultDomainObject>) component.getValueExpression("value").getType(facesContext.getELContext());
    }
}