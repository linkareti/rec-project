package com.linkare.irn.nascimento.model.identification;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public enum IdentificationCardType {

    IDENTITY_CARD,

    CITIZEN_CARD,

    PASSPORT,

    DRIVER_LICENSE,

    FOREIGN_ID_CARD;

    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}