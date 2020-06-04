package com.linkare.irn.nascimento.web.controller.shared.core;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public enum BirthRegistrationOperation {

    FIRST_PARENT_SUBMISSION,

    SECOND_PARENT_CONFIRMATION_ACCEPTANCE,

    SECOND_PARENT_CONFIRMATION_REJECTION,

    HOSPITAL_UNIT_ACCEPTANCE,

    HOSPITAL_UNIT_REJECTION,

    CIVIL_REGISTRATION_OFFICE_EFFECTUATE,
    
    CIVIL_REGISTRATION_OFFICE_ACCEPTANCE,

    CIVIL_REGISTRATION_OFFICE_REJECTION,
    
    ASSIGN_TO_DELETED,
    
    RECOVER;

    private final String messageKey;

    private final String errorKey;

    private BirthRegistrationOperation() {
	this.messageKey = "message.info." + this.name();
	this.errorKey = "message.error." + this.name();
    }

    /**
     * @return the messageKey
     */
    public String getMessageKey() {
	return messageKey;
    }

    /**
     * @return the errorKey
     */
    public String getErrorKey() {
	return errorKey;
    }

}