package com.linkare.irn.nascimento.web;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;

import com.linkare.irn.nascimento.model.config.Configuration;
import com.linkare.irn.nascimento.service.cdi.ConfigurationEvent;
import com.linkare.irn.nascimento.service.cdi.EventOperation;
import com.linkare.irn.nascimento.service.config.ConfigurationService;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@ApplicationScoped
@Singleton
@Startup
@Named
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Lock(LockType.READ)
public class ConfigurationManager {

    @Inject
    private ConfigurationService configurationService;

    private Configuration configuration;

    private String allowedFileExtensions;

    private String primefacesAllowedFileExtensions;

    @PostConstruct
    public void configure() {
	this.configuration = configurationService.find();
	if (configuration == null) {
	    this.configuration = Configuration.DEFAULT_CONFIG;
	    configuration = configurationService.create(configuration);
	}
	initInternalState();
    }

    @Lock(LockType.WRITE)
    public void onConfigurationEvent(@Observes(during = TransactionPhase.AFTER_COMPLETION) final ConfigurationEvent configurationEvent) {
	final EventOperation operation = configurationEvent.getOperation();
	final Configuration configuration = configurationEvent.getPayload();

	if (configuration == null) {
	    throw new IllegalArgumentException("Configuration not included in configuration event " + configurationEvent);
	}
	switch (operation) {
	case UPDATE:
	    this.configuration = configuration;
	    initInternalState();
	    break;
	default:
	    throw new IllegalArgumentException("Unexpected operation type " + operation);
	}
    }

    public Configuration getConfiguration() {
	return configuration;
    }

    /**
     * @return the allowedFileExtensions
     */
    public String getAllowedFileExtensions() {
	return allowedFileExtensions;
    }

    /**
     * @return the primefacesAllowedFileExtensions
     */
    public String getPrimefacesAllowedFileExtensions() {
	return primefacesAllowedFileExtensions;
    }

    private void initInternalState() {
	initAllowedFileExtensions();
	initPrimefacesAllowedFileExtensions();
    }

    private void initAllowedFileExtensions() {
	this.allowedFileExtensions = StringUtils.defaultIfBlank(configuration.getAllowedFileExtension(), "*");
    }

    private void initPrimefacesAllowedFileExtensions() {
	this.primefacesAllowedFileExtensions = getPrimefacesAllowedFileExtensions(configuration.getAllowedFileExtension());
    }

    private String getPrimefacesAllowedFileExtensions(final String allowedFileExtensionsConfig) {
	final StringBuilder result = new StringBuilder("/(\\.|\\/)");
	final String allowedFileExtensions = StringUtils.defaultIfEmpty(allowedFileExtensionsConfig, "*");
	if (allowedFileExtensions.equals("*")) {
	    result.append(".*");
	} else {
	    result.append("(");
	    if (allowedFileExtensions.contains(",")) {
		final String[] types = allowedFileExtensions.split(",");
		for (int i = 0; i < types.length; i++) {
		    result.append(types[i].replaceAll("\\.", "").trim());
		    if (i != types.length - 1) {
			result.append("|");
		    }
		}
	    } else {
		result.append(allowedFileExtensions.replaceAll("\\.", "").trim());
	    }
	    result.append(")");
	}
	result.append("$/");
	return result.toString();
    }

    public BigDecimal getAllowedMaxSizeInMB() {
	final BigDecimal result = new BigDecimal(configuration.getMaxUploadSizeInBytes() / 1024. / 1024.);
	return result.setScale(1, RoundingMode.HALF_EVEN);
    }

    public Long getRecoverPasswordTimeoutMillis() {
	return configuration.getRecoverPasswordTimeoutMillis();
    }

    // Go here for setup of the CAPTCHA: https://www.google.com/recaptcha/admin#site/337915911?setup
    public String getCaptchaPublicKey() {
	return configuration.getCaptchaPublicKey();
    }

    public String getCaptchaPrivateKey() {
	return configuration.getCaptchaPrivateKey();
    }

    public Boolean getCombinedResourceHandlerDisabled() {
	return configuration.getCombinedResourceHandlerDisabled();
    }

    public Integer getNumberOfDaysOfInactivityToExpireBirthRegistrations() {
	return configuration.getNumberOfDaysOfInactivityToExpireBirthRegistrations();
    }

    public Integer getBirthRegistrationMaxAge() {
	return configuration.getBirthRegistrationMaxAge();
    }

    public Integer getBirthRegistrationMinAge() {
	return configuration.getBirthRegistrationMinAge();
    }

    public Integer getFirstParentMaxAge() {
	return configuration.getFirstParentMaxAge();
    }

    public Integer getFirstParentMinAge() {
	return configuration.getFirstParentMinAge();
    }

    public Integer getSecondParentMaxAge() {
	return configuration.getSecondParentMaxAge();
    }

    public Integer getSecondParentMinAge() {
	return configuration.getSecondParentMinAge();
    }

    public String getDateTimePlaceHolder() {
	String dateTimePlaceHolder = getConfiguration().getDateTimePattern();
	dateTimePlaceHolder = dateTimePlaceHolder.replace("y", "A");
	dateTimePlaceHolder = dateTimePlaceHolder.replace("d", "D");
	dateTimePlaceHolder = dateTimePlaceHolder.replace("H", "h");

	return dateTimePlaceHolder;

    }

    public String getDatePlaceHolder() {
	String dateTimePlaceHolder = getConfiguration().getDatePattern();
	dateTimePlaceHolder = dateTimePlaceHolder.replace("y", "A");
	dateTimePlaceHolder = dateTimePlaceHolder.replace("d", "D");

	return dateTimePlaceHolder;
    }

    public Date getMinDate() {
	Calendar minCalendar = Calendar.getInstance();
	minCalendar.set(1850, 0, 1, 0, 0, 0);
	return minCalendar.getTime();
    }

    public Date getMaxDate() {
	Calendar minCalendar = Calendar.getInstance();
	minCalendar.set(2100, 11, 31, 0, 0, 0);
	return minCalendar.getTime();
    }
}