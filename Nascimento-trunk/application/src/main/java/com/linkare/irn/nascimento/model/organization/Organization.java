package com.linkare.irn.nascimento.model.organization;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.linkare.irn.nascimento.model.DomainObject;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@Table(name = "organization")
@NamedQueries({ 
	@NamedQuery(name = Organization.QUERY_NAME_FIND_ACTIVE_BY_TYPE, query = "select o from Organization o where o.organizationType = :"
					  + Organization.QUERY_PARAM_TYPE + " and o.active = true order by o.name"),
	@NamedQuery(name = Organization.QUERY_FIND_ACTIVE_BY_TYPE_AND_EMAIL, query = "select o from Organization o where o.organizationType = :"
			  + Organization.QUERY_PARAM_TYPE + " and o.active = true and o.email = :" + Organization.QUERY_PARAM_EMAIL)

})
public class Organization extends DomainObject {

    private static final long serialVersionUID = 4398105041935864052L;

    public static final String QUERY_NAME_FIND_ACTIVE_BY_TYPE = "Organization.findActiveByType";
    
    public static final String QUERY_FIND_ACTIVE_BY_TYPE_AND_EMAIL = "Organization.findyActiveAndTypeAndEmail";

    public static final String QUERY_PARAM_TYPE = "type";
    
    public static final String QUERY_PARAM_EMAIL = "email";

    @Column(name = "name", length = 150)
    @Size(max = 150)
    @NotNull
    @NotBlank
    private String name;

    @Column(name = "organization_type", length = 75)
    @Enumerated(EnumType.STRING)
    @NotNull
    private OrganizationType organizationType;

    @Column(name = "email", length = 150)
    @Size(max = 150)
    @NotNull
    @NotBlank
    @Email
    private String email;

    @Column(name = "second_email", length = 150)
    @Size(max = 150)
    @Email
    private String secondEmail;

    @Column(name = "active")
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
     * @return the organizationType
     */
    public OrganizationType getOrganizationType() {
	return organizationType;
    }

    /**
     * @param organizationType
     *            the organizationType to set
     */
    public void setOrganizationType(OrganizationType organizationType) {
	this.organizationType = organizationType;
    }

    /**
     * @return the email
     */
    public String getEmail() {
	return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * @return the secondEmail
     */
    public String getSecondEmail() {
	return secondEmail;
    }

    /**
     * @param secondEmail
     *            the secondEmail to set
     */
    public void setSecondEmail(String secondEmail) {
	this.secondEmail = secondEmail;
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