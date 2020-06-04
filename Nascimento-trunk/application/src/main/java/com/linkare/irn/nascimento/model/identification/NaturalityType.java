package com.linkare.irn.nascimento.model.identification;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public enum NaturalityType {

    PORTUGUESE,

    FOREIGN,

    ON_BOARD;

    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}