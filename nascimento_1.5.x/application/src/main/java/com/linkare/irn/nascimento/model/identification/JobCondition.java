package com.linkare.irn.nascimento.model.identification;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public enum JobCondition {

    UNKNOWN, // Não sabe / Não response

    EMPLOYED, // Empregado

    UNEMPLOYED_LOOKING_FOR_FIRST_JOB, // Desempregador à procura de primeiro emprego

    UNEMPLOYED_LOOKING_FOR_ANOTHER_JOB, // Desempregador à procura de um novo emprego

    INACTIVE; // Não ativo

    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}