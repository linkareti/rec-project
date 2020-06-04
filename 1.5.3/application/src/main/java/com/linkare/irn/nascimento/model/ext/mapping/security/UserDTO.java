package com.linkare.irn.nascimento.model.ext.mapping.security;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.linkare.irn.nascimento.model.ext.mapping.ImportableDTO;
import com.linkare.irn.nascimento.model.security.RoleType;
import com.linkare.irn.nascimento.model.security.User;
import com.linkare.irn.nascimento.service.mapping.ImportUtils;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class UserDTO extends ImportableDTO<User> {

    private String username;

    private String password;

    private String displayName;

    private String email;

    private String roleType;

    private String organizationExternalId;

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
    public String getRoleType() {
	return roleType;
    }

    /**
     * @param roleType
     *            the roleType to set
     */
    public void setRoleType(String roleType) {
	this.roleType = roleType;
    }

    /**
     * @return the organizationExternalId
     */
    public String getOrganizationExternalId() {
	return organizationExternalId;
    }

    /**
     * @param organizationExternalId
     *            the organizationExternalId to set
     */
    public void setOrganizationExternalId(String organizationExternalId) {
	this.organizationExternalId = organizationExternalId;
    }

    @Override
    public User toEntity() {
	final User user = new User();

	user.setUsername(username);
	user.setPassword(StringUtils.isBlank(password) ? UUID.randomUUID().toString() : password);

	user.setDisplayName(displayName);
	user.setEmail(email);

	user.setRoleType(ImportUtils.enumOf(roleType, RoleType.class));

	return user;
    }

}