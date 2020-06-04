package com.linkare.irn.nascimento.service.statistic;

public enum DaysOfWeek {

	MONDAY("Segunda"),

	TUESDAY("Terça"),

	WEDNESDAY("Quarta"),

	THURDAY("Quinta"),

	FRIDAY("Sexta"),

	SATURDAY("Sábado"),

	SUNDAY("Domingo");

	private String portgueseTranslation;

	private DaysOfWeek(final String label) {

		this.portgueseTranslation = label;
	}

	public String getPortgueseTranslation() {
		return portgueseTranslation;
	}

	public String getKey() {
		return String.format("%s.%s", getClass().getSimpleName(), this.name());
	}

}
