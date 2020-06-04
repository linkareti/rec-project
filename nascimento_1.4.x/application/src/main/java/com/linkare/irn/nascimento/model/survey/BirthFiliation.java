package com.linkare.irn.nascimento.model.survey;

public enum BirthFiliation {
    
    WITHIN_MARRIAGE, // Dentro do casamento
    OUTSIDE_MARRIAGE_WITH_COHABITATION, // Fora do casamento - com coabitação dos pais
    OUTSIDE_MARRIAGE_WITHOUT_COHABITATION; // Fora do casamento - sem coabitação dos pais
//    IGNORED; // Ignorado
    
    public String getKeyUser() {
	return String.format("%s.%s.%s", getClass().getSimpleName(), this.name(),"user");
    }
    
    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}
