package com.linkare.irn.nascimento.model.identification;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public enum AddressType {

    PORTUGUESE, // Portugal

    FOREIGN; // Estrangeiro

    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}