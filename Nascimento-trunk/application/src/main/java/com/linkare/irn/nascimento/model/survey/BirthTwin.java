package com.linkare.irn.nascimento.model.survey;

public enum BirthTwin {

    FIRST_TWIN, // 1º gémeo
    SECOND_TWIN, // 2º gémeo
    THIRD_TWIN, // 3º gémeo
    OTHER; // outro múltiplo

    public String getKeyUser() {
	return String.format("%s.%s.%s", getClass().getSimpleName(), this.name(),"user");
    }

    
    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}
