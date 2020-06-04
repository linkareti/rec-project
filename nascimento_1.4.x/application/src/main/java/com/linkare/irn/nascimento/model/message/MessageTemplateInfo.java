package com.linkare.irn.nascimento.model.message;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public final class MessageTemplateInfo {

    public static final MessageTemplateInfo NOTIFY_SECOND_PARENT_WITH_CONFIRMATION_URL = new MessageTemplateInfo("NOTIFY_SECOND_PARENT_WITH_CONFIRMATION_URL");

    public static final MessageTemplateInfo NOTIFY_FIRST_PARENT_ABOUT_SUBMISSION_WHEN_CONFIRMATION_REQUIRED = new MessageTemplateInfo("NOTIFY_FIRST_PARENT_ABOUT_SUBMISSION_WHEN_CONFIRMATION_REQUIRED");

    public static final MessageTemplateInfo NOTIFY_FIRST_PARENT_ABOUT_SUBMISSION_WHEN_CONFIRMATION_NOT_REQUIRED = new MessageTemplateInfo("NOTIFY_FIRST_PARENT_ABOUT_SUBMISSION_WHEN_CONFIRMATION_NOT_REQUIRED");

    public static final MessageTemplateInfo NOTIFY_PARENTS_ON_SECOND_PARENT_CONFIRMATION = new MessageTemplateInfo("NOTIFY_PARENTS_ON_SECOND_PARENT_CONFIRMATION");

    public static final MessageTemplateInfo NOTIFY_PARENTS_ON_SECOND_PARENT_REJECTION = new MessageTemplateInfo("NOTIFY_PARENTS_ON_SECOND_PARENT_REJECTION");

    public static final MessageTemplateInfo NOTIFY_PARENTS_OF_BIRTH_REGISTRATION_EXPIRY = new MessageTemplateInfo("NOTIFY_PARENTS_OF_BIRTH_REGISTRATION_EXPIRY");

    public static final MessageTemplateInfo NOTIFY_PARENT_OF_BIRTH_REGISTRATION_REJECTION = new MessageTemplateInfo("NOTIFY_PARENT_OF_BIRTH_REGISTRATION_REJECTION");

    public static final MessageTemplateInfo NOTIFY_HOSPITAL_UNIT_OF_NEW_BIRTH_REGISTRATION = new MessageTemplateInfo("NOTIFY_HOSPITAL_UNIT_OF_NEW_BIRTH_REGISTRATION");

    public static final MessageTemplateInfo NOTIFY_CIVIL_REGISTRATION_OFFICE_OF_NEW_BIRTH_REGISTRATION = new MessageTemplateInfo("NOTIFY_CIVIL_REGISTRATION_OFFICE_OF_NEW_BIRTH_REGISTRATION");
    
    public static final MessageTemplateInfo NOTIFY_IRN_OF_STATISTICS = new MessageTemplateInfo("NOTIFY_IRN_OF_STATISTICS");

    public static final MessageTemplateInfo RECOVER_PASSWORD = new MessageTemplateInfo("RECOVER_PASSWORD");

    private final String code;

    private final MessageTemplateLocation location;

    private MessageTemplateInfo(String code) {
	this(code, MessageTemplateLocation.DATABASE);
    }

    private MessageTemplateInfo(String code, MessageTemplateLocation location) {
	super();
	this.code = code;
	this.location = location;
    }

    /**
     * @return the code
     */
    public String getCode() {
	return code;
    }

    /**
     * @return the location
     */
    public MessageTemplateLocation getLocation() {
	return location;
    }
}