package com.linkare.irn.nascimento.model.organization;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public enum OrganizationType {

	HOSPITAL_UNIT, // Unidade hospitalar

	OTHER_HOSPITAL_UNIT, // Unidade hospitalar não presente como organização

	CIVIL_REGISTRATION_OFFICE; // Conservatória do registo cívil

	public String getKey() {
		return String.format("%s.%s", getClass().getSimpleName(), this.name());
	}

	
	public boolean isOtherHospitalUnit() {
		return this == OTHER_HOSPITAL_UNIT;
	}
}