package com.linkare.irn.nascimento.model.ext.mapping.organization;

import java.util.regex.Pattern;

import com.linkare.irn.nascimento.model.ext.mapping.ImportableDTO;
import com.linkare.irn.nascimento.model.organization.Organization;
import com.linkare.irn.nascimento.model.organization.OrganizationType;
import com.linkare.irn.nascimento.service.mapping.ImportUtils;
import com.linkare.irn.nascimento.web.validator.ForbiddenEmailFieldValidator;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class OrganizationDTO extends ImportableDTO<Organization> {

	private String name;

	private String email;

	private String secondEmail;

	private String organizationType;

	private String active;

	private Pattern namePattern;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
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
	 * @param secondEmail the secondEmail to set
	 */
	public void setSecondEmail(String secondEmail) {
		this.secondEmail = secondEmail;
	}

	/**
	 * @return the organizationType
	 */
	public String getOrganizationType() {
		return organizationType;
	}

	/**
	 * @param organizationType the organizationType to set
	 */
	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public Organization toEntity() {
		final Organization organization = new Organization();
		organization.setName(name);
		organization.setEmail(email);
		organization.setSecondEmail(secondEmail);
		organization.setOrganizationType(ImportUtils.enumOf(organizationType, OrganizationType.class));
		organization.setActive(ImportUtils.booleanOf(active));
		return organization;
	}

	public Pattern getNamePattern() {

		if (namePattern == null) {
			namePattern = Pattern.compile(ForbiddenEmailFieldValidator.EMAIL_INVALID_MAIL_FIELD_REGEX);
		}

		return namePattern;
	}

	public Boolean hasValidName() {
		return !getNamePattern().matcher(name).find();
	}
}