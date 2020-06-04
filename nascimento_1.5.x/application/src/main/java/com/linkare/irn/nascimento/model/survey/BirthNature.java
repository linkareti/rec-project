package com.linkare.irn.nascimento.model.survey;

/**
 * 
 * @author Sérgio Bogas - Linkare TI
 *
 */
public enum BirthNature {
    SIMPLE, // Simples

    TWIN; // Gemelar

    public String getKeyUser() {
	return String.format("%s.%s.%s", getClass().getSimpleName(), this.name(),"user");
    }

    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}