package com.linkare.irn.nascimento.model.ext.mapping;

import java.lang.reflect.ParameterizedType;

import com.linkare.irn.nascimento.model.DomainObject;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public abstract class ImportableDTO<T extends DomainObject> implements Importable<T> {

    private Class<T> entityTypeClass;

    private Long internalId;

    private String externalId;

    /**
     * @return the internalId
     */
    @Override
    public Long getInternalId() {
	return internalId;
    }

    /**
     * @param internalId
     *            the internalId to set
     */
    @Override
    public void setInternalId(Long internalId) {
	this.internalId = internalId;
    }

    /**
     * @return the externalId
     */
    @Override
    public String getExternalId() {
	return externalId;
    }

    /**
     * @param externalId
     *            the externalId to set
     */
    @Override
    public void setExternalId(String externalId) {
	this.externalId = externalId;
    }

    @Override
    public Class<T> getModuleClass() {
	initGenericType();
	return entityTypeClass;
    }

    /**
     * Initialises the internal <code>entityTypeClass</code> generic type.
     */
    @SuppressWarnings("unchecked")
    protected void initGenericType() {
	if (entityTypeClass == null) {
	    ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
	    entityTypeClass = ((Class<T>) parameterizedType.getActualTypeArguments()[0]);
	}
    }
}