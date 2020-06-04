package com.linkare.irn.nascimento.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.DomainObject;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 * @param <ENTITY>
 *            the entity class type, extending from DomainObject to which this converter will perform its magic
 * @param <DAO>
 *            the DAO implementation class that should be used when converting this entity type
 */
public abstract class GenericConverter<ENTITY extends DomainObject, DAO extends GenericDAO<ENTITY>> implements Converter {

    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
	return getDAO().find(Long.valueOf(value));
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {

	if (value == null) {
	    return "";
	}
	final ENTITY entity = (ENTITY) value;
	return String.valueOf(entity.getId());
    }

    protected abstract DAO getDAO();
}