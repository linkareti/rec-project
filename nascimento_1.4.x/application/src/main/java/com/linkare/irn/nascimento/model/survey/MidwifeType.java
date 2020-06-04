package com.linkare.irn.nascimento.model.survey;

/**
 * 
 * @author Sérgio Bogas - Linkare TI
 *
 */
public enum MidwifeType {
    
    DOCTOR, // Médico

    NURSE_MIDWIFE, // Enfermeira parteira

    NURSE_NON_MIDWIFE, // Enfermeira não parteira

    OTHER, // Outra

    WITHOUT_CARE, // Sem Assistência

    IGNORED; // Ignorada

    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}