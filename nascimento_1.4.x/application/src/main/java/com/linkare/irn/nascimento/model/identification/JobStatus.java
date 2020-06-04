package com.linkare.irn.nascimento.model.identification;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public enum JobStatus {

    UNKNOWN, // Não sabe / Não response

    EMPLOYER, // Empregador

    SELF_EMPLOYED, // Trabalhador por conta própria

    POSTED_WORKER, // Trabalhador por conta de outrém

    OTHER_SITUATION; // Outra situação

    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}