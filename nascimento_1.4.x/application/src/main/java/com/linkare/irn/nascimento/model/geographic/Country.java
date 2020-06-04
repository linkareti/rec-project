package com.linkare.irn.nascimento.model.geographic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.linkare.irn.nascimento.model.DomainObject;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@Table(name = "country")
@NamedQueries({ @NamedQuery(name = Country.QUERY_NAME_FIND_BY_CODE, query = "select c from Country c where c.code = :" + Country.QUERY_PARAM_CODE) })
public class Country extends DomainObject {

    private static final long serialVersionUID = 4420800633718933196L;

    public static final String QUERY_NAME_FIND_BY_CODE = "Country.findByCode";

    public static final String QUERY_PARAM_CODE = "code";

    @Column(name = "name", nullable = false, length = 150)
    @Size(max = 150)
    @NotNull
    @NotBlank
    private String name;

    @Column(name = "code", unique = true, nullable = false, length = 3)
    @Size(max = 3)
    @NotNull
    @NotBlank
    private String code;

    @Column(name = "active", nullable = false)
    @NotNull
    private Boolean active;

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the code
     */
    public String getCode() {
	return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
	this.code = code;
    }

    /**
     * @return the active
     */
    public Boolean getActive() {
	return active;
    }

    /**
     * @param active
     *            the active to set
     */
    public void setActive(Boolean active) {
	this.active = active;
    }
}