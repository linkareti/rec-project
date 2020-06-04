package com.linkare.irn.nascimento.model.core;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public enum BirthRegistrationStatus {

	// Initial state when the first submission requires confirmation from the second
	// forefather
	REQUIRES_2ND_FOREFATHER_CONFIRMATION,

	// Initial state after a submission of a first forefather where there is no need
	// for confirmation from the second forefather. It is the state one should go
	// to, if the second
	// forefather confirms the registration
	SUBMITTED,

	// Final (automatic) state we get when a registration at state
	// REQUIRES_2ND_FOREFATHER_CONFIRMATION has no response in a specific period of
	// time after its creation
	EXPIRED,

	// Final state we get when the second forefather does not confirm the birth
	// registration
	CANCELLED_BY_SECOND_FOREFATHER,

	// Intermediate state we get when the hospital unit accepts the birth
	// registration when it is at the SUBMITTED state
	ELABORATING,
	
	// Stage after the conservatory accepts the elaborating process
	ACCEPTED,

	// Final state we get when the civil registration office accepts the birth
	// registration when it is at the ELABORATING state
	CONCLUDED,

	// Final state we get when, during SUBMITTED or ELABORATING states, the hospital
	// unit , reject the birth registration request
	REJECTED,

	DELETED;
	
	public boolean isRequires2NDForefatherConfirmation() {
		return this == REQUIRES_2ND_FOREFATHER_CONFIRMATION;
	}

	public boolean isDeleted() {
		return this == DELETED;
	}

	public boolean isAccepted() {
		return this == ACCEPTED;
	}

	public boolean isRejected() {
		return this == REJECTED;
	}

	public boolean isConcluded() {
		return this == CONCLUDED;
	}

	public boolean isElaborating() {
		return this == ELABORATING;
	}

	public String getKey() {
		return String.format("%s.%s", getClass().getSimpleName(), this.name());
	}

	public String getCssClass() {
		return this.name().toLowerCase();
	}

}