package com.linkare.irn.nascimento.web.auth;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.linkare.irn.nascimento.model.organization.Organization;
import com.linkare.irn.nascimento.model.security.RoleType;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@SessionScoped
@Named("loginBean")
public class LoginBean implements Serializable {

	private static final long serialVersionUID = -3905390352456438558L;

	private String username;

	private String displayName;

	private RoleType roleType;

	private String identificationCardNumber;

	private Organization organization;

	public LoginBean() {
		super();
	}

	/**
	 * @param username
	 * @param displayName
	 * @param roleType
	 * @param organization
	 */
	public LoginBean(String username, String displayName, RoleType roleType, final Organization organization) {
		super();
		this.username = username;
		this.displayName = displayName;
		this.roleType = roleType;
		this.organization = organization;
	}

	/**
	 * @param username
	 * @param displayName
	 * @param roleType
	 * @param identificationCardNumber
	 * @param organization
	 */
	public LoginBean(String username, String displayName, RoleType roleType, String identificationCardNumber,
			Organization organization) {
		super();
		this.username = username;
		this.displayName = displayName;
		this.roleType = roleType;
		this.identificationCardNumber = identificationCardNumber;
		this.organization = organization;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the roleType
	 */
	public RoleType getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the identificationCardNumber
	 */
	public String getIdentificationCardNumber() {
		return identificationCardNumber;
	}

	/**
	 * @param identificationCardNumber the identificationCardNumber to set
	 */
	public void setIdentificationCardNumber(String identificationCardNumber) {
		this.identificationCardNumber = identificationCardNumber;
	}

	public boolean isLoggedIn() {
		return username != null;
	}

	public boolean getHasCitizenRole() {
		return roleType.isCitizen();
	}

	public boolean getHasAdminRole() {
		return roleType.isAdmin();
	}

	public boolean getHasHospitalUnitRole() {
		return roleType.isHospitalUnit();
	}

	public boolean getHasCivilRegistrationOfficeRole() {
		return roleType.isCivilRegistrationOffice() || roleType.isManager();
	}
	
	public boolean getHasManagerRole() {
		return roleType.isManager();
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
}