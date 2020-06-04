package com.linkare.irn.nascimento.model.ext.mapping.statistic;

import java.io.Serializable;

public class StatisticDTO implements Serializable {

	private static final long serialVersionUID = -7212976777083611383L;

	private Integer submitted;

	private Integer pendingForefather;

	private Integer elaboratingWithoutForefather;

	private Integer elaboratingByForefather;

	private Integer rejectedByForefather;

	private Integer expired;

	private Integer deleted;

	private Integer rejected;

	private Integer accepted;

	private Integer concluded;

	public StatisticDTO() {
		this.submitted = 0;
		this.pendingForefather = 0;
		this.elaboratingWithoutForefather = 0;
		this.elaboratingByForefather = 0;
		this.rejectedByForefather = 0;
		this.expired = 0;
		this.deleted = 0;
		this.rejected = 0;
		this.accepted = 0;
		this.concluded = 0;
	}

	public void processAudit(final BirthRegistrationAuditStatisticDTO audit) {

		if (audit != null) {
			switch (audit.getNewStatus()) {
			case REQUIRES_2ND_FOREFATHER_CONFIRMATION:
				this.submitted += audit.getCount();
				this.pendingForefather = audit.getCount();
				break;
			case EXPIRED:
				this.expired = audit.getCount();
				break;
			case DELETED:
				this.deleted = audit.getCount();
				break;
			case REJECTED:

				if (audit.getOldStatus() != null && audit.getOldStatus().isRequires2NDForefatherConfirmation()) {
					this.rejectedByForefather += audit.getCount();
				} else {
					this.rejected = audit.getCount();
				}

				break;
			case ACCEPTED:
				this.accepted = audit.getCount();
				break;
			case CONCLUDED:
				this.concluded = audit.getCount();
				break;
			case ELABORATING:

				if (audit.getOldStatus() == null) {
					this.submitted += audit.getCount();
					this.elaboratingWithoutForefather = audit.getCount();
				}

				if (audit.getOldStatus() != null && audit.getOldStatus().isRequires2NDForefatherConfirmation()) {
					this.elaboratingByForefather = audit.getCount();
				}
				break;
			case CANCELLED_BY_SECOND_FOREFATHER:
				this.rejectedByForefather += audit.getCount();
				break;
			default:
				break;
			}
		}

	}

	public Integer getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Integer submitted) {
		this.submitted = submitted;
	}

	public Integer getPendingForefather() {
		return pendingForefather;
	}

	public void setPendingForefather(Integer pendingForefather) {
		this.pendingForefather = pendingForefather;
	}

	public Integer getElaboratingWithoutForefather() {
		return elaboratingWithoutForefather;
	}

	public void setElaboratingWithoutForefather(Integer elaboratingWithoutForefather) {
		this.elaboratingWithoutForefather = elaboratingWithoutForefather;
	}

	public Integer getElaboratingByForefather() {
		return elaboratingByForefather;
	}

	public void setElaboratingByForefather(Integer elaboratingByForefather) {
		this.elaboratingByForefather = elaboratingByForefather;
	}

	public Integer getRejectedByForefather() {
		return rejectedByForefather;
	}

	public void setRejectedByForefather(Integer rejectedByForefather) {
		this.rejectedByForefather = rejectedByForefather;
	}

	public Integer getExpired() {
		return expired;
	}

	public void setExpired(Integer expired) {
		this.expired = expired;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getRejected() {
		return rejected;
	}

	public void setRejected(Integer rejected) {
		this.rejected = rejected;
	}

	public Integer getAccepted() {
		return accepted;
	}

	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}

	public Integer getConcluded() {
		return concluded;
	}

	public void setConcluded(Integer concluded) {
		this.concluded = concluded;
	}

}
