package com.linkare.irn.nascimento.model.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.linkare.irn.nascimento.model.DomainObject;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@XmlRootElement
@Entity
@Table(name = "configuration")
public class Configuration extends DomainObject {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -810466732613623651L;

    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    private static final String DEFAULT_DATE_MASK = "9999-99-99";

    private static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    private static final String DEFAULT_DATE_TIME_MASK = "9999-99-99 99:99";

    private static final String DEFAULT_TIME_PATTERN = "HH:mm";

    private static final String DEFAULT_TIME_MASK = "99:99";

    private static final String DEFAULT_TIMEZONE = "Europe/Lisbon";

    private static final String DEFAULT_LOCALE = "pt_PT";

    private static final String DEFAULT_FROM_EMAIL_ADDRESS = "noreply@irn.pt";

    private static final String DEFAULT_FROM_EMAIL_NAME = "IRN - Nascimento online";

    /** The Constant DEFAULT_MAX_UPLOAD_SIZE. */
    private static final int DEFAULT_MAX_UPLOAD_SIZE = 5242880; // 5 MB

    private static final String DEFAULT_ALLOWED_FILE_EXTENSIONS = ".pdf";

    private static final String DEFAULT_SAML_AUTHN_REQUEST_ISSUER = "nascimento.justica.gov.pt";

    private static final String DEFAULT_SAML_AUTHN_REQUEST_PROVIDER_NAME = "IRN - Registo de Nascimento Online";

    private static final String DEFAULT_SAML_AUTHN_REQUEST_DESTINATION = "https://preprod.autenticacao.gov.pt/fa/Default.aspx";

    private static final String DEFAULT_SAML_SERVICE_PROVIDER_KEYSTORE_NAME = "/home/nascimento/certs/nascimento_irn.p12";

    private static final String DEFAULT_SAML_SERVICE_PROVIDER_KEYSTORE_TYPE = "JKS";

    private static final String DEFAULT_SAML_AUTHN_REQUEST_ASSERTION_CONSUMER_SERVICE_URL = "http://localhost:8080/nascimento/saml/validate-login";

    private static final String DEFAULT_SAML_SERVICE_PROVIDER_KEYSTORE_PASS = "7iAueNpa";

    private static final String DEFAULT_SAML_SERVICE_PROVIDER_CERTIFICATE_ALIAS = "nascimento_irn";

    private static final String DEFAULT_SAML_SERVICE_PROVIDER_PRIVATE_KEY_PASS = "7iAueNpa";

    private static final boolean DEFAULT_SAML_IDENTITY_PROVIDER_CERTIFICATE_VALIDATION = false;

    private static final String DEFAULT_SAML_IDENTITY_PROVIDER_CERTIFICATE_ALIAS = "pre_producao_ama_ca";

    private static final String DEFAULT_SAML_IDENTITY_PROVIDER_KEYSTORE_TYPE = "JKS";

    private static final String DEFAULT_SAML_IDENTITY_PROVIDER_KEYSTORE_NAME = "/home/nascimento/certs/saml_fa_truststore.jks";

    private static final String DEFAULT_SAML_IDENTITY_PROVIDER_KEYSTORE_PASS = "7iAueNpa";

    private static final String DEFAULT_APPLICATION_BASE_URL = "http://localhost:8080/nascimento";

    private static final boolean DEFAULT_DUMMY_MODE = false;

    private static final long DEFAULT_RECOVER_PASSWORD_TIMEOUT_MILLIS = 600000l;

    private static final String DEFAUL_CAPTCHA_PRIVATE_KEY = "6LcHMCQUAAAAAHuh4ahsfu50ENFR5U7s0M1SW2CD";

    private static final String DEFAULT_CAPTCHA_PUBLIC_KEY = "6LcHMCQUAAAAADyJH1PAIecgjU-lrjtsFVpmDv-g";

    private static final boolean DEFAULT_COMBINED_RESOURCE_HANDLER_DISABLED = false;

    private static final String DEFAULT_FAQ_URL = "http://www.civilonline.mj.pt/CivilOnline/site/faqs.jsp#certidao";

    private static final int DEFAULT_BIRTH_REGISTRATION_MAX_AGE = 1;

    private static final int DEFAULT_BIRTH_REGISTRATION_MIN_AGE = 0;

    private static final int DEFAULT_FIRST_PARENT_MAX_AGE = 90;

    private static final int DEFAULT_FIRST_PARENT_MIN_AGE = 16;

    private static final int DEFAULT_SECOND_PARENT_MAX_AGE = 90;

    private static final int DEFAULT_SECOND_PARENT_MIN_AGE = 16;

    private static final int DEFAULT_NUMBER_OF_DAYS_OF_INACTIVITY_TO_EXPIRE_BIRTH_REGISTRATIONS = 5;

    private static final String DEFAULT_EMAIL_CONTACTS_BLOCK = null;

    private static final String DEFAULT_LOGOUT_LANDING_URL_FOR_CITIZEN = "http://justica.cf/Registos/Civil/Nascimento";

    // WS Send Email
    private static final String DEFAULT_WS_SEND_EMAIL_ENDPOINT = "https://localhost:2028/wsMCEnvioEmail";

    private static final String DEFAULT_WS_SEND_EMAIL_USERNAME = "username";

    private static final String DEFAULT_WS_SEND_EMAIL_PASSWORD = "password";

    private static final String DEFAULT_WS_SEND_EMAIL_APPLICATION_CODE = "NASC_ONLINE";

    private static final String DEFAULT_WS_SEND_EMAIL_SERVICE_CODE = "MC_EMAIL";

    private static final String DEFAULT_WS_SEND_EMAIL_DEPARTMENT_CODE = "DAS - NASIAR";

    /** The Constant QUERY_NAME_FIND_BY_KEY. */
    public static final String QUERY_NAME_FIND_BY_KEY = "Configuration.findByKey";

    /** The Constant QUERY_PARAM_KEY. */
    public static final String QUERY_PARAM_KEY = "key";

    /** The Constant DEFAULT_CONFIG. */
    public static final Configuration DEFAULT_CONFIG = new Configuration(DEFAULT_DATE_PATTERN, // datePattern                                  
									 DEFAULT_DATE_MASK, // dateMask                                     
									 DEFAULT_DATE_TIME_PATTERN, // dateTimePattern                              
									 DEFAULT_DATE_TIME_MASK, // dateTimeMask                                 
									 DEFAULT_TIME_PATTERN, // timePattern                                  
									 DEFAULT_TIME_MASK, // timeMask                                     
									 DEFAULT_TIMEZONE, // timeZone                                     
									 DEFAULT_LOCALE, // locale                                       
									 DEFAULT_FROM_EMAIL_ADDRESS, // fromEmailAddress                             
									 DEFAULT_FROM_EMAIL_NAME, // fromEmailName                                
									 DEFAULT_MAX_UPLOAD_SIZE, // maxUploadSizeInBytes                         
									 DEFAULT_ALLOWED_FILE_EXTENSIONS, // allowedFileExtension                         
									 DEFAULT_SAML_AUTHN_REQUEST_ISSUER, // samlAuthnRequestIssuer                       
									 DEFAULT_SAML_AUTHN_REQUEST_PROVIDER_NAME, // samlAuthnRequestProviderName                 
									 DEFAULT_SAML_AUTHN_REQUEST_DESTINATION, // samlAuthnRequestDestination                  
									 DEFAULT_SAML_AUTHN_REQUEST_ASSERTION_CONSUMER_SERVICE_URL, // samlAuthnRequestAssertionConsumerServiceURL  
									 DEFAULT_SAML_SERVICE_PROVIDER_KEYSTORE_TYPE, // samlServiceProviderKeystoreType              
									 DEFAULT_SAML_SERVICE_PROVIDER_KEYSTORE_NAME, // samlServiceProviderKeystoreName              
									 DEFAULT_SAML_SERVICE_PROVIDER_KEYSTORE_PASS, // samlServiceProviderKeystorePass              
									 DEFAULT_SAML_SERVICE_PROVIDER_CERTIFICATE_ALIAS, // samlServiceProviderCertificateAlias          
									 DEFAULT_SAML_SERVICE_PROVIDER_PRIVATE_KEY_PASS, // samlServiceProviderPrivateKeyPass            
									 DEFAULT_SAML_IDENTITY_PROVIDER_CERTIFICATE_VALIDATION, // samlIdentityProviderCertificateValidation    
									 DEFAULT_SAML_IDENTITY_PROVIDER_CERTIFICATE_ALIAS, // samlIdentityProviderCertificateAlias         
									 DEFAULT_SAML_IDENTITY_PROVIDER_KEYSTORE_TYPE, // samlIdentityProviderKeystoreType             
									 DEFAULT_SAML_IDENTITY_PROVIDER_KEYSTORE_NAME, // samlIdentityProviderKeystoreName             
									 DEFAULT_SAML_IDENTITY_PROVIDER_KEYSTORE_PASS, // samlIdentityProviderKeystorePass             
									 DEFAULT_APPLICATION_BASE_URL, // applicationBaseUrl                           
									 DEFAULT_DUMMY_MODE, // dummyMode                                    
									 DEFAULT_RECOVER_PASSWORD_TIMEOUT_MILLIS, // recoverPasswordTimeoutMillis                 
									 DEFAULT_CAPTCHA_PUBLIC_KEY, // captchaPublicKey
									 DEFAUL_CAPTCHA_PRIVATE_KEY, // captchaPrivateKey                              
									 DEFAULT_COMBINED_RESOURCE_HANDLER_DISABLED, // combinedResourceHandlerDisabled
									 DEFAULT_FAQ_URL, // faqURL
									 DEFAULT_BIRTH_REGISTRATION_MAX_AGE, // birthRegistrationMaxAge 
									 DEFAULT_BIRTH_REGISTRATION_MIN_AGE, // birthRegistrationMinAge 
									 DEFAULT_FIRST_PARENT_MAX_AGE, // firstParentMaxAge 
									 DEFAULT_FIRST_PARENT_MIN_AGE, // firstParentMinAge 
									 DEFAULT_SECOND_PARENT_MAX_AGE, // secondParentMaxAge 
									 DEFAULT_SECOND_PARENT_MIN_AGE, // secondParentMinAge
									 DEFAULT_NUMBER_OF_DAYS_OF_INACTIVITY_TO_EXPIRE_BIRTH_REGISTRATIONS, // numberOfDaysOfInactivityToExpireBirthRegistrations
									 DEFAULT_EMAIL_CONTACTS_BLOCK, // emailContactsBlock
									 DEFAULT_LOGOUT_LANDING_URL_FOR_CITIZEN, // logoutLandingUrlForCitizen
									 DEFAULT_WS_SEND_EMAIL_ENDPOINT, //wsSendEmailEndpoint
									 DEFAULT_WS_SEND_EMAIL_USERNAME, //wsSendEmailUsername
									 DEFAULT_WS_SEND_EMAIL_PASSWORD, //wsSendEmailPassword
									 DEFAULT_WS_SEND_EMAIL_APPLICATION_CODE, //wsSendEmailApplicationCode
									 DEFAULT_WS_SEND_EMAIL_SERVICE_CODE, //wsSendEmailServiceCode
									 DEFAULT_WS_SEND_EMAIL_DEPARTMENT_CODE); //wsSendEmailDepartmentCode

    /** The date pattern. */
    @Column(name = "date_pattern", length = 30)
    @NotNull
    @Size(min = 0, max = 30)
    private String datePattern;

    /** The date mask. */
    @Column(name = "date_mask", length = 30)
    @NotNull
    @Size(min = 0, max = 30)
    private String dateMask;

    /** The date time pattern. */
    @Column(name = "date_time_pattern", length = 30)
    @NotNull
    @Size(min = 0, max = 30)
    private String dateTimePattern;

    /** The date time mask. */
    @Column(name = "date_time_mask", length = 30)
    @NotNull
    @Size(min = 0, max = 30)
    private String dateTimeMask;

    /** The time pattern. */
    @Column(name = "time_pattern", length = 15)
    @NotNull
    @Size(min = 0, max = 15)
    private String timePattern;

    /** The time mask. */
    @Column(name = "time_mask", length = 15)
    @NotNull
    @Size(min = 0, max = 15)
    private String timeMask;

    /** The time zone. */
    @Column(name = "time_zone", length = 50)
    @NotNull
    @Size(min = 0, max = 50)
    private String timeZone;

    /** The locale. */
    @Column(name = "locale", length = 10)
    @NotNull
    @Size(min = 0, max = 10)
    private String locale;

    /** The from email address. */
    @Column(name = "from_email_address", length = 150)
    @NotNull
    @Size(min = 0, max = 150)
    private String fromEmailAddress;

    /** The from email name. */
    @Column(name = "from_email_name", length = 150)
    @NotNull
    @Size(min = 0, max = 150)
    private String fromEmailName;

    /** The max upload size in bytes. */
    @Column(name = "max_upload_size_in_bytes")
    @NotNull
    @Min(0)
    @Max(Configuration.DEFAULT_MAX_UPLOAD_SIZE)
    private Integer maxUploadSizeInBytes;

    /** The allowed file extension. */
    @Column(name = "allowed_file_extension", length = 150)
    @NotNull
    @Size(min = 0, max = 150)
    private String allowedFileExtension;

    /** The saml authn request issuer. */
    @Column(name = "saml_authn_request_issuer", length = 150)
    @NotNull
    @Size(min = 0, max = 150)
    private String samlAuthnRequestIssuer;

    /** The saml authn request provider name. */
    @Column(name = "saml_authn_request_provider_name", length = 50)
    @NotNull
    @Size(min = 0, max = 50)
    private String samlAuthnRequestProviderName;

    /** The saml authn request destination. */
    @Column(name = "saml_authn_request_destination", length = 150)
    @NotNull
    @Size(min = 0, max = 150)
    private String samlAuthnRequestDestination;

    /** The saml authn request assertion consumer service URL. */
    @Column(name = "saml_authn_request_assertion_consumer_service_url", length = 150)
    @NotNull
    @Size(min = 0, max = 150)
    private String samlAuthnRequestAssertionConsumerServiceURL;

    /** The saml service provider keystore type. */
    @Column(name = "saml_service_provider_keystore_type", length = 30)
    @NotNull
    @Size(min = 0, max = 30)
    private String samlServiceProviderKeystoreType;

    /** The saml service provider keystore name. */
    @Column(name = "saml_service_provider_keystore_name", length = 150)
    @NotNull
    @Size(min = 0, max = 150)
    private String samlServiceProviderKeystoreName;

    /** The saml service provider keystore pass. */
    @Column(name = "saml_service_provider_Keystore_pass", length = 30)
    @NotNull
    @Size(min = 0, max = 30)
    private String samlServiceProviderKeystorePass;

    /** The saml service provider certificate alias. */
    @Column(name = "saml_service_provider_certificate_alias", length = 150)
    @NotNull
    @Size(min = 0, max = 150)
    private String samlServiceProviderCertificateAlias;

    /** The saml service provider private key pass. */
    @Column(name = "saml_service_provider_private_key_pass", length = 30)
    @NotNull
    @Size(min = 0, max = 30)
    private String samlServiceProviderPrivateKeyPass;

    /** The saml identity provider certificate validation. */
    @Column(name = "saml_identity_provider_certificate_validation", length = 150)
    @NotNull
    private Boolean samlIdentityProviderCertificateValidation;

    /** The saml identity provider certificate alias. */
    @Column(name = "saml_identity_provider_certificate_alias", length = 150)
    @NotNull
    @Size(min = 0, max = 150)
    private String samlIdentityProviderCertificateAlias;

    /** The saml identity provider keystore type. */
    @Column(name = "saml_identity_provider_keystore_type", length = 30)
    @NotNull
    @Size(min = 0, max = 30)
    private String samlIdentityProviderKeystoreType;

    /** The saml identity provider keystore name. */
    @Column(name = "saml_identity_provider_keystore_name", length = 150)
    @NotNull
    @Size(min = 0, max = 150)
    private String samlIdentityProviderKeystoreName;

    /** The saml identity provider keystore pass. */
    @Column(name = "saml_identity_provider_keystore_pass", length = 30)
    @NotNull
    @Size(min = 0, max = 30)
    private String samlIdentityProviderKeystorePass;

    @Column(name = "application_base_url")
    @NotNull
    @Size(max = 175)
    private String applicationBaseUrl;

    @Column(name = "dummy_mode")
    @NotNull
    private Boolean dummyMode;

    @Column(name = "recover_password_timeout_millis")
    @NotNull
    private Long recoverPasswordTimeoutMillis;

    @Column(name = "captcha_public_key")
    @Size(max = 75)
    @NotNull
    private String captchaPublicKey;

    @Column(name = "captcha_private_key")
    @Size(max = 75)
    @NotNull
    private String captchaPrivateKey;

    @Column(name = "combined_resource_handler_disabled")
    @NotNull
    private Boolean combinedResourceHandlerDisabled;

    @Column(name = "faq_url")
    @Size(max = 175)
    @NotNull
    private String faqURL;
    
    @Column(name = "statistics_email")
    @Size(min = 0, max = 150)
    private String statisticsEmail;

    @Column(name = "birth_registration_max_age")
    @NotNull
    private Integer birthRegistrationMaxAge;

    @Column(name = "birth_registration_min_age")
    @NotNull
    private Integer birthRegistrationMinAge;

    @Column(name = "first_parent_max_age")
    @NotNull
    private Integer firstParentMaxAge;

    @Column(name = "first_parent_min_age")
    @NotNull
    private Integer firstParentMinAge;

    @Column(name = "second_parent_max_age")
    @NotNull
    private Integer secondParentMaxAge;

    @Column(name = "second_parent_min_age")
    @NotNull
    private Integer secondParentMinAge;

    @Column(name = "number_of_days_of_inactivity_to_expire_birth_registrations")
    @NotNull
    private Integer numberOfDaysOfInactivityToExpireBirthRegistrations;

    @Column(name = "email_contacts_block")
    @Size(max = 500)
    private String emailContactsBlock;

    @Column(name = "logout_landing_url_for_citizen")
    @Size(max = 150)
    private String logoutLandingUrlForCitizen;

    // WS Send Email
    @Column(name = "ws_send_email_endpoint")
    @Size(min = 0, max = 150)
    private String wsSendEmailEndpoint;

    @Column(name = "ws_send_email_username")
    @Size(min = 0, max = 150)
    private String wsSendEmailUsername;

    @Column(name = "ws_send_email_password")
    @Size(min = 0, max = 150)
    private String wsSendEmailPassword;

    @Column(name = "ws_send_email_application_code")
    @Size(min = 0, max = 150)
    private String wsSendEmailApplicationCode;

    @Column(name = "ws_send_email_service_code")
    @Size(min = 0, max = 150)
    private String wsSendEmailServiceCode;

    @Column(name = "ws_send_email_department_code")
    @Size(min = 0, max = 150)
    private String wsSendEmailDepartmentCode;

    /**
     * Instantiates a new configuration.
     */
    public Configuration() {
	super();
    }

    /**
     * Instantiates a new configuration.
     *
     * @param datePattern
     *            the date pattern
     * @param dateMask
     *            the date mask
     * @param dateTimePattern
     *            the date time pattern
     * @param dateTimeMask
     *            the date time mask
     * @param timePattern
     *            the time pattern
     * @param timeMask
     *            the time mask
     * @param timeZone
     *            the time zone
     * @param locale
     *            the locale
     * @param fromEmailAddress
     *            the from email address
     * @param fromEmailName
     *            the from email name
     * @param maxUploadSizeInBytes
     *            the max upload size in bytes
     * @param allowedFileExtension
     *            the allowed file extension
     * @param samlAuthnRequestIssuer
     *            the saml authn request issuer
     * @param samlAuthnRequestProviderName
     *            the saml authn request provider name
     * @param samlAuthnRequestDestination
     *            the saml authn request destination
     * @param samlAuthnRequestAssertionConsumerServiceURL
     *            the saml authn request assertion consumer service URL
     * @param samlServiceProviderKeystoreType
     *            the saml service provider keystore type
     * @param samlServiceProviderKeystoreName
     *            the saml service provider keystore name
     * @param samlServiceProviderKeystorePass
     *            the saml service provider keystore pass
     * @param samlServiceProviderCertificateAlias
     *            the saml service provider certificate alias
     * @param samlServiceProviderPrivateKeyPass
     *            the saml service provider private key pass
     * @param samlIdentityProviderCertificateValidation
     *            the saml identity provider certificate validation
     * @param samlIdentityProviderCertificateAlias
     *            the saml identity provider certificate alias
     * @param samlIdentityProviderKeystoreType
     *            the saml identity provider keystore type
     * @param samlIdentityProviderKeystoreName
     *            the saml identity provider keystore name
     * @param samlIdentityProviderKeystorePass
     *            the saml identity provider keystore pass
     * @param applicationBaseUrl
     *            the applicationBaseUrl to set
     * @param dummyMode
     *            the dummyMode to set
     * @param recoverPasswordTimeoutMillis
     *            the recoverPasswordTimeoutMillis to set
     * @param captchaPublicKey
     *            the captchaPublicKey to set
     * @param captchaPrivateKey
     *            the captchaPrivateKey to set
     * @param combinedResourceHandlerDisabled
     *            the combinedResourceHandlerDisabled to set
     * @param faqURL
     *            the faqURL to set
     * @param birthRegistrationMaxAge
     *            the birth registration max age
     * @param birthRegistrationMinAge
     *            the birth registration min age
     * @param firstParentMaxAge
     *            the first parent max age
     * @param firstParentMinAge
     *            the first parent min age
     * @param secondParentMaxAge
     *            the second parent max age
     * @param secondParentMinAge
     *            the second parent min age
     * @param numberOfDaysOfInactivityToExpireBirthRegistrations
     *            the number of days of inactivity to expire birth registrations
     * @param emailContactsBlock
     *            the emailContactsBlock
     * @param logoutLandingUrlForCitizen
     *            the logoutLandingUrlForCitizen
     * @param wsSendEmailEndpoint
     *            the WS send email endpoint
     * @param wsSendEmailUsername
     *            the WS send email header username
     * @param wsSendEmailPassword
     *            the WS send email header password
     * @param wsSendEmailApplicationCode
     *            the WS send email application code
     * @param wsSendEmailServiceCode
     *            the WS send email service code
     * @param wsSendEmailDepartmentCode
     *            the WS send email department code
     */
    public Configuration(final String datePattern, final String dateMask, final String dateTimePattern, final String dateTimeMask, final String timePattern,
	    final String timeMask, String timeZone, String locale, String fromEmailAddress, String fromEmailName, Integer maxUploadSizeInBytes,
	    String allowedFileExtension, final String samlAuthnRequestIssuer, final String samlAuthnRequestProviderName,
	    final String samlAuthnRequestDestination, final String samlAuthnRequestAssertionConsumerServiceURL, final String samlServiceProviderKeystoreType,
	    final String samlServiceProviderKeystoreName, final String samlServiceProviderKeystorePass, final String samlServiceProviderCertificateAlias,
	    final String samlServiceProviderPrivateKeyPass, final Boolean samlIdentityProviderCertificateValidation,
	    final String samlIdentityProviderCertificateAlias, final String samlIdentityProviderKeystoreType, final String samlIdentityProviderKeystoreName,
	    final String samlIdentityProviderKeystorePass, final String applicationBaseUrl, final Boolean dummyMode, final Long recoverPasswordTimeoutMillis,
	    final String captchaPublicKey, final String captchaPrivateKey, final Boolean combinedResourceHandlerDisabled, final String faqURL,
	    final Integer birthRegistrationMaxAge, final Integer birthRegistrationMinAge, final Integer firstParentMaxAge, final Integer firstParentMinAge,
	    final Integer secondParentMaxAge, final Integer secondParentMinAge, final Integer numberOfDaysOfInactivityToExpireBirthRegistrations,
	    final String emailContactsBlock, final String logoutLandingUrlForCitizen, final String wsSendEmailEndpoint, final String wsSendEmailUsername,
	    final String wsSendEmailPassword, final String wsSendEmailApplicationCode, final String wsSendEmailServiceCode,
	    final String wsSendEmailDepartmentCode) {
	super();
	this.datePattern = datePattern;
	this.dateMask = dateMask;

	this.dateTimePattern = dateTimePattern;
	this.dateTimeMask = dateTimeMask;

	this.timePattern = timePattern;
	this.timeMask = timeMask;

	this.timeZone = timeZone;
	this.locale = locale;
	this.fromEmailAddress = fromEmailAddress;
	this.fromEmailName = fromEmailName;
	this.maxUploadSizeInBytes = maxUploadSizeInBytes;
	this.allowedFileExtension = allowedFileExtension;

	this.samlAuthnRequestIssuer = samlAuthnRequestIssuer;
	this.samlAuthnRequestProviderName = samlAuthnRequestProviderName;
	this.samlAuthnRequestDestination = samlAuthnRequestDestination;
	this.samlAuthnRequestAssertionConsumerServiceURL = samlAuthnRequestAssertionConsumerServiceURL;

	this.samlServiceProviderKeystoreType = samlServiceProviderKeystoreType;
	this.samlServiceProviderKeystoreName = samlServiceProviderKeystoreName;
	this.samlServiceProviderKeystorePass = samlServiceProviderKeystorePass;

	this.samlServiceProviderCertificateAlias = samlServiceProviderCertificateAlias;

	this.samlServiceProviderPrivateKeyPass = samlServiceProviderPrivateKeyPass;

	this.samlIdentityProviderCertificateValidation = samlIdentityProviderCertificateValidation;
	this.samlIdentityProviderCertificateAlias = samlIdentityProviderCertificateAlias;

	this.samlIdentityProviderKeystoreType = samlIdentityProviderKeystoreType;
	this.samlIdentityProviderKeystoreName = samlIdentityProviderKeystoreName;
	this.samlIdentityProviderKeystorePass = samlIdentityProviderKeystorePass;

	this.applicationBaseUrl = applicationBaseUrl;
	this.dummyMode = dummyMode;

	this.recoverPasswordTimeoutMillis = recoverPasswordTimeoutMillis;
	this.combinedResourceHandlerDisabled = combinedResourceHandlerDisabled;

	this.captchaPublicKey = captchaPublicKey;
	this.captchaPrivateKey = captchaPrivateKey;

	this.faqURL = faqURL;

	this.birthRegistrationMaxAge = birthRegistrationMaxAge;
	this.birthRegistrationMinAge = birthRegistrationMinAge;

	this.firstParentMaxAge = firstParentMaxAge;
	this.firstParentMinAge = firstParentMinAge;

	this.secondParentMaxAge = secondParentMaxAge;
	this.secondParentMinAge = secondParentMinAge;

	this.numberOfDaysOfInactivityToExpireBirthRegistrations = numberOfDaysOfInactivityToExpireBirthRegistrations;
	this.emailContactsBlock = emailContactsBlock;

	this.logoutLandingUrlForCitizen = logoutLandingUrlForCitizen;

	// WS Send Email
	this.wsSendEmailEndpoint = wsSendEmailEndpoint;
	this.wsSendEmailUsername = wsSendEmailUsername;
	this.wsSendEmailPassword = wsSendEmailPassword;
	this.wsSendEmailApplicationCode = wsSendEmailApplicationCode;
	this.wsSendEmailServiceCode = wsSendEmailServiceCode;
	this.wsSendEmailDepartmentCode = wsSendEmailDepartmentCode;

    }

    /**
     * Gets the date pattern.
     *
     * @return the datePattern
     */
    public String getDatePattern() {
	return datePattern;
    }

    /**
     * Sets the date pattern.
     *
     * @param datePattern
     *            the datePattern to set
     */
    public void setDatePattern(String datePattern) {
	this.datePattern = datePattern;
    }

    /**
     * Gets the date mask.
     *
     * @return the date mask
     */
    public String getDateMask() {
	return dateMask;
    }

    /**
     * Sets the date mask.
     *
     * @param dateMask
     *            the dateMask to set
     * @return the dateMask
     */
    public void setDateMask(String dateMask) {
	this.dateMask = dateMask;
    }

    /**
     * Gets the date time pattern.
     *
     * @return the dateTimePattern
     */
    public String getDateTimePattern() {
	return dateTimePattern;
    }

    /**
     * Sets the date time pattern.
     *
     * @param dateTimePattern
     *            the dateTimePattern to set
     */
    public void setDateTimePattern(String dateTimePattern) {
	this.dateTimePattern = dateTimePattern;
    }

    /**
     * Gets the time pattern.
     *
     * @return the dateTimeMask
     */
    public String getDateTimeMask() {
	return dateTimeMask;
    }

    /**
     * @param dateTimeMask
     *            the dateTimeMask to set
     */
    public void setDateTimeMask(String dateTimeMask) {
	this.dateTimeMask = dateTimeMask;
    }

    /**
     * @return the timePattern
     */
    public String getTimePattern() {
	return timePattern;
    }

    /**
     * Sets the time pattern.
     *
     * @param timePattern
     *            the timePattern to set
     */
    public void setTimePattern(String timePattern) {
	this.timePattern = timePattern;
    }

    /**
     * Gets the time zone.
     * 
     * @return the timeMask
     */
    public String getTimeMask() {
	return timeMask;
    }

    /**
     * @param timeMask
     *            the timeMask to set
     */
    public void setTimeMask(String timeMask) {
	this.timeMask = timeMask;
    }

    /**
     * @return the timeZone
     */
    public String getTimeZone() {
	return timeZone;
    }

    /**
     * Sets the time zone.
     *
     * @param timeZone
     *            the timeZone to set
     */
    public void setTimeZone(String timeZone) {
	this.timeZone = timeZone;
    }

    /**
     * Gets the locale.
     *
     * @return the locale
     */
    public String getLocale() {
	return locale;
    }

    /**
     * Sets the locale.
     *
     * @param locale
     *            the locale to set
     */
    public void setLocale(String locale) {
	this.locale = locale;
    }

    /**
     * Gets the from email address.
     *
     * @return the fromEmailAddress
     */
    public String getFromEmailAddress() {
	return fromEmailAddress;
    }

    /**
     * Sets the from email address.
     *
     * @param fromEmailAddress
     *            the fromEmailAddress to set
     */
    public void setFromEmailAddress(String fromEmailAddress) {
	this.fromEmailAddress = fromEmailAddress;
    }

    /**
     * Gets the from email name.
     *
     * @return the fromEmailName
     */
    public String getFromEmailName() {
	return fromEmailName;
    }

    /**
     * Sets the from email name.
     *
     * @param fromEmailName
     *            the fromEmailName to set
     */
    public void setFromEmailName(String fromEmailName) {
	this.fromEmailName = fromEmailName;
    }

    /**
     * Gets the max upload size in bytes.
     *
     * @return the maxUploadSizeInBytes
     */
    public Integer getMaxUploadSizeInBytes() {
	return maxUploadSizeInBytes;
    }

    /**
     * Sets the max upload size in bytes.
     *
     * @param maxUploadSizeInBytes
     *            the maxUploadSizeInBytes to set
     */
    public void setMaxUploadSizeInBytes(Integer maxUploadSizeInBytes) {
	this.maxUploadSizeInBytes = maxUploadSizeInBytes;
    }

    /**
     * Gets the allowed file extension.
     *
     * @return the allowedFileExtension
     */
    public String getAllowedFileExtension() {
	return allowedFileExtension;
    }

    /**
     * Sets the allowed file extension.
     *
     * @param allowedFileExtension
     *            the allowedFileExtension to set
     */
    public void setAllowedFileExtension(String allowedFileExtension) {
	this.allowedFileExtension = allowedFileExtension;
    }

    /**
     * Gets the saml authn request issuer.
     *
     * @return the saml authn request issuer
     */
    public String getSamlAuthnRequestIssuer() {
	return samlAuthnRequestIssuer;
    }

    /**
     * Sets the saml authn request issuer.
     *
     * @param samlAuthnRequestIssuer
     *            the new saml authn request issuer
     */
    public void setSamlAuthnRequestIssuer(String samlAuthnRequestIssuer) {
	this.samlAuthnRequestIssuer = samlAuthnRequestIssuer;
    }

    /**
     * Gets the saml authn request provider name.
     *
     * @return the saml authn request provider name
     */
    public String getSamlAuthnRequestProviderName() {
	return samlAuthnRequestProviderName;
    }

    /**
     * Sets the saml authn request provider name.
     *
     * @param samlAuthnRequestProviderName
     *            the new saml authn request provider name
     */
    public void setSamlAuthnRequestProviderName(String samlAuthnRequestProviderName) {
	this.samlAuthnRequestProviderName = samlAuthnRequestProviderName;
    }

    /**
     * Gets the saml authn request destination.
     *
     * @return the saml authn request destination
     */
    public String getSamlAuthnRequestDestination() {
	return samlAuthnRequestDestination;
    }

    /**
     * Sets the saml authn request destination.
     *
     * @param samlAuthnRequestDestination
     *            the new saml authn request destination
     */
    public void setSamlAuthnRequestDestination(String samlAuthnRequestDestination) {
	this.samlAuthnRequestDestination = samlAuthnRequestDestination;
    }

    /**
     * Gets the saml authn request assertion consumer service URL.
     *
     * @return the saml authn request assertion consumer service URL
     */
    public String getSamlAuthnRequestAssertionConsumerServiceURL() {
	return samlAuthnRequestAssertionConsumerServiceURL;
    }

    /**
     * Sets the saml authn request assertion consumer service URL.
     *
     * @param samlAuthnRequestAssertionConsumerServiceURL
     *            the new saml authn request assertion consumer service URL
     */
    public void setSamlAuthnRequestAssertionConsumerServiceURL(String samlAuthnRequestAssertionConsumerServiceURL) {
	this.samlAuthnRequestAssertionConsumerServiceURL = samlAuthnRequestAssertionConsumerServiceURL;
    }

    /**
     * Gets the saml service provider keystore type.
     *
     * @return the saml service provider keystore type
     */
    public String getSamlServiceProviderKeystoreType() {
	return samlServiceProviderKeystoreType;
    }

    /**
     * Sets the saml service provider keystore type.
     *
     * @param samlServiceProviderKeystoreType
     *            the new saml service provider keystore type
     */
    public void setSamlServiceProviderKeystoreType(String samlServiceProviderKeystoreType) {
	this.samlServiceProviderKeystoreType = samlServiceProviderKeystoreType;
    }

    /**
     * Gets the saml service provider keystore name.
     *
     * @return the saml service provider keystore name
     */
    public String getSamlServiceProviderKeystoreName() {
	return samlServiceProviderKeystoreName;
    }

    /**
     * Sets the saml service provider keystore name.
     *
     * @param samlServiceProviderKeystoreName
     *            the new saml service provider keystore name
     */
    public void setSamlServiceProviderKeystoreName(String samlServiceProviderKeystoreName) {
	this.samlServiceProviderKeystoreName = samlServiceProviderKeystoreName;
    }

    /**
     * Gets the saml service provider keystore pass.
     *
     * @return the saml service provider keystore pass
     */
    public String getSamlServiceProviderKeystorePass() {
	return samlServiceProviderKeystorePass;
    }

    /**
     * Sets the saml service provider keystore pass.
     *
     * @param samlServiceProviderKeystorePass
     *            the new saml service provider keystore pass
     */
    public void setSamlServiceProviderKeystorePass(String samlServiceProviderKeystorePass) {
	this.samlServiceProviderKeystorePass = samlServiceProviderKeystorePass;
    }

    /**
     * Gets the saml service provider certificate alias.
     *
     * @return the saml service provider certificate alias
     */
    public String getSamlServiceProviderCertificateAlias() {
	return samlServiceProviderCertificateAlias;
    }

    /**
     * Sets the saml service provider certificate alias.
     *
     * @param samlServiceProviderCertificateAlias
     *            the new saml service provider certificate alias
     */
    public void setSamlServiceProviderCertificateAlias(String samlServiceProviderCertificateAlias) {
	this.samlServiceProviderCertificateAlias = samlServiceProviderCertificateAlias;
    }

    /**
     * Gets the saml service provider private key pass.
     *
     * @return the saml service provider private key pass
     */
    public String getSamlServiceProviderPrivateKeyPass() {
	return samlServiceProviderPrivateKeyPass;
    }

    /**
     * Sets the saml service provider private key pass.
     *
     * @param samlServiceProviderPrivateKeyPass
     *            the new saml service provider private key pass
     */
    public void setSamlServiceProviderPrivateKeyPass(String samlServiceProviderPrivateKeyPass) {
	this.samlServiceProviderPrivateKeyPass = samlServiceProviderPrivateKeyPass;
    }

    /**
     * Gets the saml identity provider certificate validation.
     *
     * @return the saml identity provider certificate validation
     */
    public Boolean getSamlIdentityProviderCertificateValidation() {
	return samlIdentityProviderCertificateValidation;
    }

    /**
     * Sets the saml identity provider certificate validation.
     *
     * @param samlIdentityProviderCertificateValidation
     *            the new saml identity provider certificate validation
     */
    public void setSamlIdentityProviderCertificateValidation(Boolean samlIdentityProviderCertificateValidation) {
	this.samlIdentityProviderCertificateValidation = samlIdentityProviderCertificateValidation;
    }

    /**
     * Gets the saml identity provider certificate alias.
     *
     * @return the saml identity provider certificate alias
     */
    public String getSamlIdentityProviderCertificateAlias() {
	return samlIdentityProviderCertificateAlias;
    }

    /**
     * Sets the saml identity provider certificate alias.
     *
     * @param samlIdentityProviderCertificateAlias
     *            the new saml identity provider certificate alias
     */
    public void setSamlIdentityProviderCertificateAlias(String samlIdentityProviderCertificateAlias) {
	this.samlIdentityProviderCertificateAlias = samlIdentityProviderCertificateAlias;
    }

    /**
     * Gets the saml identity provider keystore type.
     *
     * @return the saml identity provider keystore type
     */
    public String getSamlIdentityProviderKeystoreType() {
	return samlIdentityProviderKeystoreType;
    }

    /**
     * Sets the saml identity provider keystore type.
     *
     * @param samlIdentityProviderKeystoreType
     *            the new saml identity provider keystore type
     */
    public void setSamlIdentityProviderKeystoreType(String samlIdentityProviderKeystoreType) {
	this.samlIdentityProviderKeystoreType = samlIdentityProviderKeystoreType;
    }

    /**
     * Gets the saml identity provider keystore name.
     *
     * @return the saml identity provider keystore name
     */
    public String getSamlIdentityProviderKeystoreName() {
	return samlIdentityProviderKeystoreName;
    }

    /**
     * Sets the saml identity provider keystore name.
     *
     * @param samlIdentityProviderKeystoreName
     *            the new saml identity provider keystore name
     */
    public void setSamlIdentityProviderKeystoreName(String samlIdentityProviderKeystoreName) {
	this.samlIdentityProviderKeystoreName = samlIdentityProviderKeystoreName;
    }

    /**
     * Gets the saml identity provider keystore pass.
     *
     * @return the saml identity provider keystore pass
     */
    public String getSamlIdentityProviderKeystorePass() {
	return samlIdentityProviderKeystorePass;
    }

    /**
     * Sets the saml identity provider keystore pass.
     *
     * @param samlIdentityProviderKeystorePass
     *            the new saml identity provider keystore pass
     */
    public void setSamlIdentityProviderKeystorePass(String samlIdentityProviderKeystorePass) {
	this.samlIdentityProviderKeystorePass = samlIdentityProviderKeystorePass;
    }

    /**
     * @return the applicationBaseUrl
     */
    public String getApplicationBaseUrl() {
	return applicationBaseUrl;
    }

    /**
     * @param applicationBaseUrl
     *            the applicationBaseUrl to set
     */
    public void setApplicationBaseUrl(String applicationBaseUrl) {
	this.applicationBaseUrl = applicationBaseUrl;
    }

    /**
     * @return the dummyMode
     */
    public Boolean getDummyMode() {
	return dummyMode;
    }

    /**
     * @param dummyMode
     *            the dummyMode to set
     */
    public void setDummyMode(Boolean dummyMode) {
	this.dummyMode = dummyMode;
    }

    /**
     * @return the recoverPasswordTimeoutMillis
     */
    public Long getRecoverPasswordTimeoutMillis() {
	return recoverPasswordTimeoutMillis;
    }

    /**
     * @param recoverPasswordTimeoutMillis
     *            the recoverPasswordTimeoutMillis to set
     */
    public void setRecoverPasswordTimeoutMillis(Long recoverPasswordTimeoutMillis) {
	this.recoverPasswordTimeoutMillis = recoverPasswordTimeoutMillis;
    }

    /**
     * @return the captchaPublicKey
     */
    public String getCaptchaPublicKey() {
	return captchaPublicKey;
    }

    /**
     * @param captchaPublicKey
     *            the captchaPublicKey to set
     */
    public void setCaptchaPublicKey(String captchaPublicKey) {
	this.captchaPublicKey = captchaPublicKey;
    }

    /**
     * @return the captchaPrivateKey
     */
    public String getCaptchaPrivateKey() {
	return captchaPrivateKey;
    }

    /**
     * @param captchaPrivateKey
     *            the captchaPrivateKey to set
     */
    public void setCaptchaPrivateKey(String captchaPrivateKey) {
	this.captchaPrivateKey = captchaPrivateKey;
    }

    /**
     * @return the combinedResourceHandlerDisabled
     */
    public Boolean getCombinedResourceHandlerDisabled() {
	return combinedResourceHandlerDisabled;
    }

    /**
     * @param combinedResourceHandlerDisabled
     *            the combinedResourceHandlerDisabled to set
     */
    public void setCombinedResourceHandlerDisabled(Boolean combinedResourceHandlerDisabled) {
	this.combinedResourceHandlerDisabled = combinedResourceHandlerDisabled;
    }

    /**
     * @return the faqURL
     */
    public String getFaqURL() {
	return faqURL;
    }

    /**
     * @param faqURL
     *            the faqURL to set
     */
    public void setFaqURL(String faqURL) {
	this.faqURL = faqURL;
    }

    /**
     * Gets the birth registration max age.
     *
     * @return the birth registration max age
     */
    public Integer getBirthRegistrationMaxAge() {
	return birthRegistrationMaxAge;
    }

    /**
     * Sets the birth registration max age.
     *
     * @param birthRegistrationMaxAge
     *            the new birth registration max age
     */
    public void setBirthRegistrationMaxAge(Integer birthRegistrationMaxAge) {
	this.birthRegistrationMaxAge = birthRegistrationMaxAge;
    }

    /**
     * Gets the birth registration min age.
     *
     * @return the birth registration min age
     */
    public Integer getBirthRegistrationMinAge() {
	return birthRegistrationMinAge;
    }

    /**
     * Sets the birth registration min age.
     *
     * @param birthRegistrationMinAge
     *            the new birth registration min age
     */
    public void setBirthRegistrationMinAge(Integer birthRegistrationMinAge) {
	this.birthRegistrationMinAge = birthRegistrationMinAge;
    }

    /**
     * Gets the first parent max age.
     *
     * @return the first parent max age
     */
    public Integer getFirstParentMaxAge() {
	return firstParentMaxAge;
    }

    /**
     * Sets the first parent max age.
     *
     * @param firstParentMaxAge
     *            the new first parent max age
     */
    public void setFirstParentMaxAge(Integer firstParentMaxAge) {
	this.firstParentMaxAge = firstParentMaxAge;
    }

    /**
     * Gets the first parent min age.
     *
     * @return the first parent min age
     */
    public Integer getFirstParentMinAge() {
	return firstParentMinAge;
    }

    /**
     * Sets the first parent min age.
     *
     * @param firstParentMinAge
     *            the new first parent min age
     */
    public void setFirstParentMinAge(Integer firstParentMinAge) {
	this.firstParentMinAge = firstParentMinAge;
    }

    /**
     * Gets the second parent max age.
     *
     * @return the second parent max age
     */
    public Integer getSecondParentMaxAge() {
	return secondParentMaxAge;
    }

    /**
     * Sets the second parent max age.
     *
     * @param secondParentMaxAge
     *            the new second parent max age
     */
    public void setSecondParentMaxAge(Integer secondParentMaxAge) {
	this.secondParentMaxAge = secondParentMaxAge;
    }

    /**
     * Gets the second parent min age.
     *
     * @return the second parent min age
     */
    public Integer getSecondParentMinAge() {
	return secondParentMinAge;
    }

    /**
     * Sets the second parent min age.
     *
     * @param secondParentMinAge
     *            the new second parent min age
     */
    public void setSecondParentMinAge(Integer secondParentMinAge) {
	this.secondParentMinAge = secondParentMinAge;
    }

    /**
     * Gets the number of days of inactivity to expire birth registrations.
     *
     * @return the number of days of inactivity to expire birth registrations
     */
    public Integer getNumberOfDaysOfInactivityToExpireBirthRegistrations() {
	return numberOfDaysOfInactivityToExpireBirthRegistrations;
    }

    /**
     * Sets the number of days of inactivity to expire birth registrations.
     *
     * @param numberOfDaysOfInactivityToExpireBirthRegistrations
     *            the new number of days of inactivity to expire birth registrations
     */
    public void setNumberOfDaysOfInactivityToExpireBirthRegistrations(Integer numberOfDaysOfInactivityToExpireBirthRegistrations) {
	this.numberOfDaysOfInactivityToExpireBirthRegistrations = numberOfDaysOfInactivityToExpireBirthRegistrations;
    }

    /**
     * @return the emailContactsBlock
     */
    public String getEmailContactsBlock() {
	return emailContactsBlock;
    }

    /**
     * @param emailContactsBlock
     *            the emailContactsBlock to set
     */
    public void setEmailContactsBlock(String emailContactsBlock) {
	this.emailContactsBlock = emailContactsBlock;
    }

    /**
     * @return the logoutLandingUrlForCitizen
     */
    public String getLogoutLandingUrlForCitizen() {
	return logoutLandingUrlForCitizen;
    }

    /**
     * @param logoutLandingUrlForCitizen
     *            the logoutLandingUrlForCitizen to set
     */
    public void setLogoutLandingUrlForCitizen(String logoutLandingUrlForCitizen) {
	this.logoutLandingUrlForCitizen = logoutLandingUrlForCitizen;
    }

    /**
     * @return the wsSendEmailEndpoint
     */
    public String getWsSendEmailEndpoint() {
	return wsSendEmailEndpoint;
    }

    /**
     * @param wsSendEmailEndpoint
     *            the wsSendEmailEndpoint to set
     */
    public void setWsSendEmailEndpoint(String wsSendEmailEndpoint) {
	this.wsSendEmailEndpoint = wsSendEmailEndpoint;
    }

    /**
     * @return the wsSendEmailUsername
     */
    public String getWsSendEmailUsername() {
	return wsSendEmailUsername;
    }

    /**
     * @param wsSendEmailUsername
     *            the wsSendEmailUsername to set
     */
    public void setWsSendEmailUsername(String wsSendEmailUsername) {
	this.wsSendEmailUsername = wsSendEmailUsername;
    }

    /**
     * @return the wsSendEmailPassword
     */
    public String getWsSendEmailPassword() {
	return wsSendEmailPassword;
    }

    /**
     * @param wsSendEmailPassword
     *            the wsSendEmailPassword to set
     */
    public void setWsSendEmailPassword(String wsSendEmailPassword) {
	this.wsSendEmailPassword = wsSendEmailPassword;
    }

    /**
     * @return the wsSendEmailApplicationCode
     */
    public String getWsSendEmailApplicationCode() {
	return wsSendEmailApplicationCode;
    }

    /**
     * @param wsSendEmailApplicationCode
     *            the wsSendEmailApplicationCode to set
     */
    public void setWsSendEmailApplicationCode(String wsSendEmailApplicationCode) {
	this.wsSendEmailApplicationCode = wsSendEmailApplicationCode;
    }

    /**
     * @return the wsSendEmailServiceCode
     */
    public String getWsSendEmailServiceCode() {
	return wsSendEmailServiceCode;
    }

    /**
     * @param wsSendEmailServiceCode
     *            the wsSendEmailServiceCode to set
     */
    public void setWsSendEmailServiceCode(String wsSendEmailServiceCode) {
	this.wsSendEmailServiceCode = wsSendEmailServiceCode;
    }

    /**
     * @return the wsSendEmailDepartmentCode
     */
    public String getWsSendEmailDepartmentCode() {
	return wsSendEmailDepartmentCode;
    }

    /**
     * @param wsSendEmailDepartmentCode
     *            the wsSendEmailDepartmentCode to set
     */
    public void setWsSendEmailDepartmentCode(String wsSendEmailDepartmentCode) {
	this.wsSendEmailDepartmentCode = wsSendEmailDepartmentCode;
    }

	public String getStatisticsEmail() {
		return statisticsEmail;
	}

	public void setStatisticsEmail(String statisticsEmail) {
		this.statisticsEmail = statisticsEmail;
	}
}