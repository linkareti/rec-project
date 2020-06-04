package com.linkare.irn.nascimento.model.identification;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public enum AcademicDegree {

    UNKNOWN, // Não sabe / Não response

    CANT_READ_NOR_WIRTE, // Não sabe ler nem escrever

    CAN_READ_WITHOUT_OFFICIAL_SCHOOL, // Sabe ler sem ter frequentado o sistema de ensino

    FIRST_CYCLE, // Ensino básico - 1.º ciclo

    SECOND_CYCLE, // Ensino básico - 2.º ciclo

    THIRD_CYCLE, // Ensino básico - 3.º ciclo

    SECONDARY_SCHOOL, // Ensino secundário

    HIGH_SCHOOL; // Ensino superior

    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}