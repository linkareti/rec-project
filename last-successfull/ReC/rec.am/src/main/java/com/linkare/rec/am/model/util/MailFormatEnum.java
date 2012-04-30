package com.linkare.rec.am.model.util;

public enum MailFormatEnum {

    //TODO - I18n
    PLAIN_TEXT("Texto Simples"), HTML("Html");

    private String description;

    private MailFormatEnum(String description) {
	this.description = description;
    }

    @Override
    public String toString() {
	return description;
    }
}
