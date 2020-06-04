package com.linkare.irn.nascimento.model.survey;

/**
 * 
 * @author Sérgio Bogas - Linkare TI
 *
 */
public enum BirthLocation {
    HOME, // Domicílio do próprio

    HOSPITAL, // Hospital / Clinica

    OTHER;

    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}
