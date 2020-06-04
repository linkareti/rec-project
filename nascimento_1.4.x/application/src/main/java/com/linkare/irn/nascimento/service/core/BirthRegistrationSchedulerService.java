package com.linkare.irn.nascimento.service.core;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.irn.nascimento.model.core.BirthRegistration;
import com.linkare.irn.nascimento.web.ConfigurationManager;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class BirthRegistrationSchedulerService {

    private static final Logger LOG = LoggerFactory.getLogger(BirthRegistrationSchedulerService.class);

    @Inject
    private BirthRegistrationService birthRegistrationService;

    @Inject
    private ConfigurationManager configurationManager;

    @Schedule(minute = "*/5", hour = "*")
    public void expireBirthRegistrationsRequiringConfirmationOlderThanPossible() {
	final LocalDate today = new LocalDate();
	final LocalDate expirationThreeshold = today.minusDays(configurationManager.getNumberOfDaysOfInactivityToExpireBirthRegistrations());
	LOG.info("Expiring birth registrations older than " + expirationThreeshold);
	final List<BirthRegistration> registrationsToExpire = birthRegistrationService.findBirthRegistrationsToExpire(expirationThreeshold.toDateTimeAtCurrentTime()
																	  .toDate());
	for (final BirthRegistration birthRegistration : registrationsToExpire) {
	    birthRegistrationService.expireBirthRegistration(birthRegistration);
	}
    }
}