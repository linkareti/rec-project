package com.linkare.irn.nascimento.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.model.organization.Organization;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@NamedQueries({
    /* @formatter:off */
    @NamedQuery(name = User.QUERY_NAME_FIND_BY_USERNAME, query = "select u from User u "
            + "where u.username = :" + User.QUERY_PARAM_USERNAME),
    @NamedQuery(name = User.QUERY_NAME_FIND_BY_USERNAME_AND_PASSWORD, query = "select u from User u where u.username = :" + 
            User.QUERY_PARAM_USERNAME + " and u.password = :" + User.QUERY_PARAM_PASSWORD),
    /* @formatter:on */
})
@Table(name = "user")
public class User extends DomainObject {

    private static final long serialVersionUID = -6470631168810746010L;

    public static final String QUERY_NAME_FIND_BY_USERNAME_AND_PASSWORD = "User.authenticate";
    public static final String QUERY_NAME_FIND_BY_USERNAME = "User.findByUsername";

    public static final String QUERY_PARAM_USERNAME = "username";
    public static final String QUERY_PARAM_PASSWORD = "password";

    @Column(name = "username", length = 75, unique = true, nullable = false)
    @NotNull
    @NotBlank
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    @NotNull
    @NotBlank
    private String password;

    @Column(name = "display_name", length = 75, nullable = false)
    @NotNull
    @NotBlank
    private String displayName;

    @Column(name = "email", length = 150, nullable = false)
    @NotNull
    @NotBlank
    private String email;

    @Column(name = "role_type", length = 50)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    @ManyToOne
    @JoinColumn(name = "key_organization")
    private Organization organization;

    /**
     * @return the username
     */
    public String getUsername() {
	return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
	this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
	return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
	return displayName;
    }

    /**
     * @param displayName
     *            the displayName to set
     */
    public void setDisplayName(String displayName) {
	this.displayName = displayName;
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
     * @return the roleType
     */
    public RoleType getRoleType() {
	return roleType;
    }

    /**
     * @param roleType
     *            the roleType to set
     */
    public void setRoleType(RoleType roleType) {
	this.roleType = roleType;
    }

    /**
     * @return the organization
     */
    public Organization getOrganization() {
	return organization;
    }

    /**
     * @param organization
     *            the organization to set
     */
    public void setOrganization(Organization organization) {
	this.organization = organization;
    }
}