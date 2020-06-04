package com.linkare.irn.nascimento.model.identification;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public enum Gender {

    MALE, // Masculino

    FEMALE, // Feminino
	
	UNDETERMINED;

    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}