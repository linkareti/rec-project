package com.linkare.irn.nascimento.model.ext.mapping.statistic;

import java.io.Serializable;
import java.util.Date;

import com.linkare.irn.nascimento.model.core.BirthRegistrationStatus;

public class BirthRegistrationAuditStatisticDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int count;

	private BirthRegistrationStatus newStatus;

	private Date modifiedDate;

	private BirthRegistrationStatus oldStatus;

	public BirthRegistrationAuditStatisticDTO(final Object[] params) {

		this.count = Integer.parseInt(params[0].toString());
		this.newStatus = BirthRegistrationStatus.valueOf(params[1].toString());
		this.modifiedDate = (Date) params[2];
		this.oldStatus = (params[3] != null && !params[3].equals("NEW") ) ? BirthRegistrationStatus.valueOf(params[3].toString()) : null;

	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public BirthRegistrationStatus getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(BirthRegistrationStatus newStatus) {
		this.newStatus = newStatus;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public BirthRegistrationStatus getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(BirthRegistrationStatus oldStatus) {
		this.oldStatus = oldStatus;
	}

}
