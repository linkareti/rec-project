package com.linkare.irn.nascimento.model.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.linkare.irn.nascimento.model.DomainObject;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = Mapper.QUERY_NAME_FIND_BY_MODULE_AND_INTERNAL_ID, query = "select m from Mapper m where m.module = :" + Mapper.QUERY_PARAM_MODULE
		+ " and m.internalId = :" + Mapper.QUERY_PARAM_INTERNAL_ID),
	@NamedQuery(name = Mapper.QUERY_NAME_FIND_BY_MODULE_AND_EXTERNAL_ID, query = "select m from Mapper m where m.module = :" + Mapper.QUERY_PARAM_MODULE
		+ " and m.externalId = :" + Mapper.QUERY_PARAM_EXTERNAL_ID) })
@Table(name = "mapper", uniqueConstraints = @UniqueConstraint(columnNames = { "external_id", "module" }))
public class Mapper extends DomainObject {

    private static final long serialVersionUID = -8461111261197040674L;

    public static final String QUERY_NAME_FIND_BY_MODULE_AND_INTERNAL_ID = "Mapper.findByModuleAndInternalId";

    public static final String QUERY_NAME_FIND_BY_MODULE_AND_EXTERNAL_ID = "Mapper.findByModuleAndExternalId";

    public static final String QUERY_PARAM_MODULE = "module";

    public static final String QUERY_PARAM_INTERNAL_ID = "internalId";

    public static final String QUERY_PARAM_EXTERNAL_ID = "externalId";

    @Column(name = "internal_id")
    private Long internalId;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "module")
    private String module;

    /**
     * @return the internalId
     */
    public Long getInternalId() {
	return internalId;
    }

    /**
     * @param internalId
     *            the internalId to set
     */
    public void setInternalId(Long internalId) {
	this.internalId = internalId;
    }

    /**
     * @return the externalId
     */
    public String getExternalId() {
	return externalId;
    }

    /**
     * @param externalId
     *            the externalId to set
     */
    public void setExternalId(String externalId) {
	this.externalId = externalId;
    }

    /**
     * @return the module
     */
    public String getModule() {
	return module;
    }

    /**
     * @param module
     *            the module to set
     */
    public void setModule(String module) {
	this.module = module;
    }
}