package com.linkare.irn.nascimento.model.ext.mapping;

import com.linkare.irn.nascimento.model.DomainObject;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public interface Importable<T extends DomainObject> {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public Class<T> getModuleClass();

    public default String getModule() {
	return getModuleClass().getSimpleName();
    }

    public Long getInternalId();

    public void setInternalId(final Long internalId);

    public String getExternalId();

    public void setExternalId(final String externalId);

    public T toEntity();
}