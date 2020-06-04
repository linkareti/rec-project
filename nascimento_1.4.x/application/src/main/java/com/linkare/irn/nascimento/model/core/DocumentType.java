package com.linkare.irn.nascimento.model.core;

public enum DocumentType {

	BIRTH_PROOF,

	BABY_PHOTO;

	public String getKey() {
		return String.format("%s.%s", getClass().getSimpleName(), this.name());
	}

	public String getCssClass() {
		return this.name().toLowerCase();
	}

	public boolean isBirthProof() {
		return this == BIRTH_PROOF;
	}
	
	public boolean isBabyPhoto() {
		return this == BABY_PHOTO;
	}
}
