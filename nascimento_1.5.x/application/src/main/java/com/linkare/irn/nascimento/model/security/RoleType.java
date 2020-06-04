package com.linkare.irn.nascimento.model.security;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public enum RoleType {

	CITIZEN,

	HOSPITAL_UNIT,

	CIVIL_REGISTRATION_OFFICE,

	MANAGER,

	ADMIN;

	public String getKey() {
		return String.format("%s.%s", getClass().getSimpleName(), this.name());
	}
	
	public boolean isManager() {
		return this == MANAGER;
	}
	
	public boolean isAdmin() {
		return this == ADMIN;
	}
	
	public boolean isCivilRegistrationOffice() {
		return this == CIVIL_REGISTRATION_OFFICE;
	}
	
	public boolean isHospitalUnit() {
		return this == HOSPITAL_UNIT;
	}
	
	public boolean isCitizen() {
		return this == CITIZEN;
	}
}