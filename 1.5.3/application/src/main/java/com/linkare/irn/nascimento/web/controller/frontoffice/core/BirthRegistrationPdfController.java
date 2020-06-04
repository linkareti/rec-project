package com.linkare.irn.nascimento.web.controller.frontoffice.core;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.linkare.irn.nascimento.model.core.BirthRegistration;
import com.linkare.irn.nascimento.service.core.BirthRegistrationService;
import com.linkare.irn.nascimento.web.ConfigurationManager;
import com.linkare.irn.nascimento.web.auth.UnauthorizedException;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@Named("birthRegistrationPdfController")
@RequestScoped
public class BirthRegistrationPdfController implements Serializable {

    private static final long serialVersionUID = -7567799696638899759L;

    private String uuid;

    private BirthRegistration birthRegistration;

    @Inject
    private BirthRegistrationService birthRegistrationService;

    @Inject
    private ConfigurationManager configurationManager;

    public void load() {
	loadBirthRegistration();
    }

    private void loadBirthRegistration() {
	if (StringUtils.isNotBlank(uuid)) {
	    this.birthRegistration = birthRegistrationService.findByUUID(uuid);
	    if (birthRegistration == null) {
		throw new EntityNotFoundException("Birth registration with UUID " + uuid + " not found");
	    } else {
		final DateTime now = new DateTime(DateTimeZone.forID(configurationManager.getConfiguration().getTimeZone()));
		final DateTime expirationDate = now.minusSeconds(30);
		if (birthRegistration.getCreationDate() == null || birthRegistration.getCreationDate().before(expirationDate.toDate())) {
		    throw new UnauthorizedException("Birth registration with UUID " + uuid + " no longer available");
		}
	    }
	}
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
	return uuid;
    }

    /**
     * @param uuid
     *            the uuid to set
     */
    public void setUuid(String uuid) {
	this.uuid = uuid;
    }

    /**
     * @return the birthRegistration
     */
    public BirthRegistration getBirthRegistration() {
	return birthRegistration;
    }
}