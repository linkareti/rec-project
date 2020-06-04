package com.linkare.irn.nascimento.web.controller.frontoffice.core;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public enum BirthRegistrationStep {

    IDENTIFICATION("/public/birth_registration/identification.xhtml"),

    FIRST_PARENT("/public/birth_registration/first_parent.xhtml"),

    SECOND_PARENT("/public/birth_registration/second_parent.xhtml"),

    REGISTRATION("/public/birth_registration/registration.xhtml"),
    
    SURVEY("/public/birth_registration/survey.xhtml"),

    SUMMARY("/public/birth_registration/summary.xhtml"),

    ACCESS_DENIED("/public/birth_registration/access_denied.xhtml");

    private String includedPage;

    /**
     * @param includedPage
     */
    private BirthRegistrationStep(String includedPage) {
	this.includedPage = includedPage;
    }

    /**
     * @return the includedPage
     */
    public String getIncludedPage() {
	return includedPage;
    }

    /**
     * @param includedPage
     *            the includedPage to set
     */
    public void setIncludedPage(String includedPage) {
	this.includedPage = includedPage;
    }

    public String getKey() {
	return String.format("%s.%s", getClass().getSimpleName(), this.name());
    }
}