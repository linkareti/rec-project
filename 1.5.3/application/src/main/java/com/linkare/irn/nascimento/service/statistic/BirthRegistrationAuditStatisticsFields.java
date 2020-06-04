package com.linkare.irn.nascimento.service.statistic;

public enum BirthRegistrationAuditStatisticsFields {

	SUBMITTED("Novos pedidos"),

	PENDING_FOREFATHER("Aguardam 2º progenitor"),

	ELABORATING_WITHOUT_FOREFATHER("Não necessitam 2º Progenitor"),

	ELABORATING_BY_SECOND_FOREFATHER("Aprovados 2º Progenitor"),

	REJECTED_BY_FOREFATHER("Rejeitados 2º Progenitor"),

	EXPIRED("Expirados"),

	ACCEPTED("Pendentes"),

	CONCLUDED("Efetuados"), 
	
	REJECTED("Recusados"),

	DELETED("Eliminados");

	private String portgueseTranslation;

	private BirthRegistrationAuditStatisticsFields(final String label) {
		this.portgueseTranslation = label;
	}

	public String getPortgueseTranslation() {
		return portgueseTranslation;
	}

	public String getKey() {
		return String.format("%s.%s", getClass().getSimpleName(), this.name());
	}

}
