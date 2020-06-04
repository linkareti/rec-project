package com.linkare.irn.nascimento.model.identification;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public enum MaritalStatus {

    SINGLE, // Solteiro(a)

    MARRIED, // Casado(a)

    DIVORCED, // Divorciado(a)

    WIDOWER, // Viúvo(a)

    SEPARATED; // Casado(a), mas separado de pessoas e bens

    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }

    public boolean isSingle() {
	return this == MaritalStatus.SINGLE;
    }

    public boolean isMarried() {
	return this == MaritalStatus.MARRIED;
    }

    public boolean isDivorced() {
	return this == MaritalStatus.DIVORCED;
    }

    public boolean isWidower() {
	return this == MaritalStatus.WIDOWER;
    }

    public boolean isSeparated() {
	return this == MaritalStatus.SEPARATED;
    }
}