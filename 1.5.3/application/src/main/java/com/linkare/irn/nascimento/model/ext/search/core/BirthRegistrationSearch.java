package com.linkare.irn.nascimento.model.ext.search.core;

import java.util.Date;

import com.linkare.irn.nascimento.model.core.BirthRegistrationStatus;
import com.linkare.irn.nascimento.model.ext.search.BaseSearch;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class BirthRegistrationSearch extends BaseSearch {

	private static final long serialVersionUID = 1L;

	private Date start;

	private Date end;

	private String firstParentIdentificationCardNumber;

	private BirthRegistrationStatus status;

	private Long hospitalUnitId;

	private String registrationNumber;

	private String babyName;

	private String otherHospitalUnit;
	
	private Boolean isOtherHospital;

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * @return the firstParentIdentificationCardNumber
	 */
	public String getFirstParentIdentificationCardNumber() {
		return firstParentIdentificationCardNumber;
	}

	/**
	 * @param firstParentIdentificationCardNumber the
	 *                                            firstParentIdentificationCardNumber
	 *                                            to set
	 */
	public void setFirstParentIdentificationCardNumber(String firstParentIdentificationCardNumber) {
		this.firstParentIdentificationCardNumber = firstParentIdentificationCardNumber;
	}

	/**
	 * @return the status
	 */
	public BirthRegistrationStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(BirthRegistrationStatus status) {
		this.status = status;
	}

	/**
	 * @return the registrationNumber
	 */
	public final String getRegistrationNumber() {
		return registrationNumber;
	}

	/**
	 * @param registrationNumber the registrationNumber to set
	 */
	public final void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public void clear() {
		this.start = null;
		this.end = null;
		this.firstParentIdentificationCardNumber = null;

		this.status = null;
		this.hospitalUnitId = null;
		this.registrationNumber = null;
		this.otherHospitalUnit = null;
		this.babyName = null;
	}

	public String getBabyName() {
		return babyName;
	}

	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}

	public String getOtherHospitalUnit() {
		return otherHospitalUnit;
	}

	public void setOtherHospitalUnit(String otherHospitalUnit) {
		this.otherHospitalUnit = otherHospitalUnit;
	}

	public Long getHospitalUnitId() {
		return hospitalUnitId;
	}

	public void setHospitalUnitId(Long hospitalUnitId) {
		this.hospitalUnitId = hospitalUnitId;
	}

	public Boolean getIsOtherHospital() {
		return isOtherHospital;
	}

	public void setIsOtherHospital(Boolean isOtherHospital) {
		this.isOtherHospital = isOtherHospital;
	}

}