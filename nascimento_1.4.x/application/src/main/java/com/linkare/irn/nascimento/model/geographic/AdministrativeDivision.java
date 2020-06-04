package com.linkare.irn.nascimento.model.geographic;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Table(name = "administrative_division")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "instance_type")
public abstract class AdministrativeDivision extends DomainObject {

    private static final long serialVersionUID = 5721793789686110344L;

    @Column(name = "code", unique = false, nullable = false)
    @Size(max = 10)
    @NotNull
    @NotBlank
    private String code;

    @Column(name = "name")
    @Size(max = 150)
    @NotNull
    @NotBlank
    private String name;

    @Column(name = "active", nullable = false)
    @NotNull
    private Boolean active;

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