package com.linkare.irn.nascimento.service.core;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import com.linkare.irn.nascimento.dao.core.BirthRegistrationDAO;
import com.linkare.irn.nascimento.model.config.Configuration;
import com.linkare.irn.nascimento.model.core.BirthRegistration;
import com.linkare.irn.nascimento.model.core.BirthRegistrationStatus;
import com.linkare.irn.nascimento.model.core.Counter;
import com.linkare.irn.nascimento.model.core.DocumentType;
import com.linkare.irn.nascimento.model.ext.mapping.statistic.StatisticDTO;
import com.linkare.irn.nascimento.model.identification.Parent;
import com.linkare.irn.nascimento.model.message.EmailMessage;
import com.linkare.irn.nascimento.model.message.MessageTemplateInfo;
import com.linkare.irn.nascimento.model.organization.Organization;
import com.linkare.irn.nascimento.service.BaseStatelessServiceImpl;
import com.linkare.irn.nascimento.service.cdi.EventOperation;
import com.linkare.irn.nascimento.service.cdi.message.EmailMessageEvent;
import com.linkare.irn.nascimento.service.message.EmailMessageService;
import com.linkare.irn.nascimento.util.SelectionRange;
import com.linkare.irn.nascimento.web.ConfigurationManager;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class BirthRegistrationService extends BaseStatelessServiceImpl<BirthRegistration, BirthRegistrationDAO> {

	private static final String BIRTH_REGISTRATION_COUNTER_PREFIX = "NASCIMENTO.";

	private static final char BIRTH_REGISTRATION_PAD_CHAR = '0';

	private static final int BIRTH_REGISTRATION_SUFFIX_LENGTH = 5;

	@Inject
	private CounterService counterService;

	@Inject
	private EmailMessageService emailMessageService;

	@Inject
	private ConfigurationManager configurationManager;

	@Inject
	private Event<EmailMessageEvent> emailMessageEvent;

	@Override
	public BirthRegistrationDAO getDAO() {
		return new BirthRegistrationDAO(getEntityManager());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public byte[] getFileContent(final DocumentType documentType, final BirthRegistration birthRegistration) {
		final BirthRegistration found = getDAO().find(birthRegistration.getId());

		return found == null ? null
				: (documentType.isBirthProof()) ? found.getFileContent() : found.getBabyPhoto().getFileContent();
	}

	public List<BirthRegistration> findBirthRegistrationsToExpire(final Date startDate) {
		return getDAO().findBirthRegistrationsToExpire(startDate);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void expireBirthRegistration(final BirthRegistration birthRegistration) {
		final BirthRegistration existingBirthRegistration = find(birthRegistration.getId());
		if (existingBirthRegistration == null) {
			throw new EntityNotFoundException("No birth registration found for id " + birthRegistration.getId());
		}
		if (existingBirthRegistration
				.getBirthRegistrationStatus() == BirthRegistrationStatus.REQUIRES_2ND_FOREFATHER_CONFIRMATION) {
			existingBirthRegistration.setBirthRegistrationStatus(BirthRegistrationStatus.EXPIRED);
			existingBirthRegistration.setLastBirthRegistrarionDate(Calendar.getInstance().getTime());
			final BirthRegistration updated = super.update(existingBirthRegistration);
			registerBirthRegistrationExpiration(updated);
		}
	}

	private void registerBirthRegistrationExpiration(final BirthRegistration birthRegistration) {
		final Configuration configuration = configurationManager.getConfiguration();

		final Map<String, Object> params = new HashMap<>();
		params.put("birthRegistration", birthRegistration);

		try {
			final Parent firstParent = birthRegistration.getFirstParent();
			final Parent secondParent = birthRegistration.getSecondParent();

			final InternetAddress from = new InternetAddress(configuration.getFromEmailAddress(),
					configuration.getFromEmailName());
			final List<InternetAddress> destinations = new ArrayList<InternetAddress>();
			destinations.add(new InternetAddress(firstParent.getEmail(), firstParent.getName()));
			if (secondParent != null && secondParent.getDeceased() == Boolean.FALSE) {
				destinations.add(new InternetAddress(secondParent.getEmail(), secondParent.getName()));
			}
			final EmailMessage message = emailMessageService.create(from, destinations,
					MessageTemplateInfo.NOTIFY_PARENTS_OF_BIRTH_REGISTRATION_EXPIRY, params, params,
					configuration.getFromEmailAddress(), configuration.getFromEmailName());

			emailMessageEvent.fire(new EmailMessageEvent(EventOperation.CREATE, message));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Problem creating the addresses to send the email", e);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void hospitalUnitAcceptBirthRegistration(final BirthRegistration birthRegistration) {
		final BirthRegistration existingBirthRegistration = find(birthRegistration.getId());
		if (existingBirthRegistration == null) {
			throw new EntityNotFoundException("No birth registration found for id " + birthRegistration.getId());
		}
		if (existingBirthRegistration.getBirthRegistrationStatus() == BirthRegistrationStatus.SUBMITTED) {
			existingBirthRegistration.setBirthRegistrationStatus(BirthRegistrationStatus.ELABORATING);
			existingBirthRegistration.setLastBirthRegistrarionDate(Calendar.getInstance().getTime());
			existingBirthRegistration.setObservation(birthRegistration.getObservation());

			registerNewBirthRegistrationNotificationForCivilRegistrationOffice(birthRegistration);

			super.update(existingBirthRegistration);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void hospitalUnitRejectBirthRegistration(final BirthRegistration birthRegistration) {
		final BirthRegistration existingBirthRegistration = find(birthRegistration.getId());
		if (existingBirthRegistration == null) {
			throw new EntityNotFoundException("No birth registration found for id " + birthRegistration.getId());
		}
		if (existingBirthRegistration.getBirthRegistrationStatus() == BirthRegistrationStatus.SUBMITTED) {
			existingBirthRegistration.setBirthRegistrationStatus(BirthRegistrationStatus.REJECTED);
			existingBirthRegistration.setLastBirthRegistrarionDate(Calendar.getInstance().getTime());
			existingBirthRegistration.setRejectionReason(birthRegistration.getRejectionReason());
			final BirthRegistration updated = super.update(existingBirthRegistration);
			registerBirthRegistrationRejection(updated,
					MessageTemplateInfo.NOTIFY_PARENT_OF_BIRTH_REGISTRATION_REJECTION);
		}
	}

	private void registerBirthRegistrationRejection(final BirthRegistration birthRegistration,
			final MessageTemplateInfo template) {
		final Configuration configuration = configurationManager.getConfiguration();

		final Map<String, Object> params = new HashMap<>();
		params.put("birthRegistration", birthRegistration);

		try {
			final Parent firstParent = birthRegistration.getFirstParent();

			final InternetAddress from = new InternetAddress(configuration.getFromEmailAddress(),
					configuration.getFromEmailName());
			final List<InternetAddress> destinations = Arrays
					.asList(new InternetAddress(firstParent.getEmail(), firstParent.getName()));
			final EmailMessage message = emailMessageService.create(from, destinations, template, params, params,
					configuration.getFromEmailAddress(), configuration.getFromEmailName());

			emailMessageEvent.fire(new EmailMessageEvent(EventOperation.CREATE, message));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Problem creating the addresses to send the email", e);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void civilRegistrationOfficeEffectuateBirthRegistration(final BirthRegistration birthRegistration) {
		final BirthRegistration existingBirthRegistration = find(birthRegistration.getId());
		if (existingBirthRegistration == null) {
			throw new EntityNotFoundException("No birth registration found for id " + birthRegistration.getId());
		}
		if (existingBirthRegistration.getBirthRegistrationStatus() == BirthRegistrationStatus.ACCEPTED) {
			existingBirthRegistration.setBirthRegistrationStatus(BirthRegistrationStatus.CONCLUDED);
			existingBirthRegistration.setLastBirthRegistrarionDate(Calendar.getInstance().getTime());

			super.update(existingBirthRegistration);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void civilRegistrationOfficeRejectBirthRegistration(final BirthRegistration birthRegistration) {
		final BirthRegistration existingBirthRegistration = find(birthRegistration.getId());
		if (existingBirthRegistration == null) {
			throw new EntityNotFoundException("No birth registration found for id " + birthRegistration.getId());
		}
		if (existingBirthRegistration.getBirthRegistrationStatus() == BirthRegistrationStatus.ACCEPTED) {
			existingBirthRegistration.setBirthRegistrationStatus(BirthRegistrationStatus.REJECTED);
			existingBirthRegistration.setLastBirthRegistrarionDate(Calendar.getInstance().getTime());
			existingBirthRegistration.setRejectionReason(birthRegistration.getRejectionReason());
			super.update(existingBirthRegistration);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void assignToDeletedBirthRegistration(final BirthRegistration birthRegistration) {

		birthRegistration.setBirthRegistrationStatus(BirthRegistrationStatus.DELETED);
		super.update(birthRegistration);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void recoverBirthRegistration(final BirthRegistration birthRegistration) {

		birthRegistration.setBirthRegistrationStatus(BirthRegistrationStatus.ELABORATING);
		super.update(birthRegistration);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void civilRegistrationOfficeAcceptBirthRegistration(final BirthRegistration birthRegistration) {

		birthRegistration.setBirthRegistrationStatus(BirthRegistrationStatus.ACCEPTED);
		super.update(birthRegistration);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public BirthRegistration create(BirthRegistration birthRegistration, Class<?>... validationGroups) {
		final int year = new DateTime().getYear();
		final Counter birthRegistrationCounter = getOrCreateNextCounterFor(year);

		final String registrationNumber = year + "."
				+ StringUtils.leftPad(String.valueOf(birthRegistrationCounter.getCount()),
						BIRTH_REGISTRATION_SUFFIX_LENGTH, BIRTH_REGISTRATION_PAD_CHAR);
		birthRegistration.setRegistrationNumber(registrationNumber);

		return super.create(birthRegistration, validationGroups);
	}

	public void acceptSubmission(final BirthRegistration birthRegistration) {
		birthRegistration.setBirthRegistrationStatus(BirthRegistrationStatus.ELABORATING);
		birthRegistration.setLastBirthRegistrarionDate(Calendar.getInstance().getTime());
		registerNotificationForBothParentsAboutConfirmationAcceptance(birthRegistration);
		registerNewBirthRegistrationNotificationForCivilRegistrationOffice(birthRegistration);
	}

	public void rejectSubmission(final BirthRegistration birthRegistration) {
		birthRegistration.setBirthRegistrationStatus(BirthRegistrationStatus.CANCELLED_BY_SECOND_FOREFATHER);
		birthRegistration.setLastBirthRegistrarionDate(Calendar.getInstance().getTime());
		registerNotificationForParentsAboutConfirmationRejection(birthRegistration);
	}

	private void registerNotificationForBothParentsAboutConfirmationAcceptance(
			final BirthRegistration birthRegistration) {
		final Configuration configuration = configurationManager.getConfiguration();

		final Map<String, Object> params = new HashMap<>();
		params.put("birthRegistration", birthRegistration);

		try {
			final Parent firstParent = birthRegistration.getFirstParent();

			final InternetAddress from = new InternetAddress(configuration.getFromEmailAddress(),
					configuration.getFromEmailName());
			final List<InternetAddress> destinationsFirstParent = new ArrayList<>();
			destinationsFirstParent.add(new InternetAddress(firstParent.getEmail(), firstParent.getName()));

			final Parent secondParent = birthRegistration.getSecondParent();
			if (secondParent != null && secondParent.getDeceased() != Boolean.TRUE) {
				final List<InternetAddress> destinationsSecondParent = new ArrayList<>();
				destinationsSecondParent.add(new InternetAddress(secondParent.getEmail(), secondParent.getName()));
				final EmailMessage messageSecondParent = emailMessageService.create(from, destinationsSecondParent,
						MessageTemplateInfo.NOTIFY_PARENTS_ON_SECOND_PARENT_CONFIRMATION, params, params,
						configuration.getFromEmailAddress(), configuration.getFromEmailName());

				emailMessageEvent.fire(new EmailMessageEvent(EventOperation.CREATE, messageSecondParent));

			}

			final EmailMessage message = emailMessageService.create(from, destinationsFirstParent,
					MessageTemplateInfo.NOTIFY_PARENTS_ON_SECOND_PARENT_CONFIRMATION, params, params,
					configuration.getFromEmailAddress(), configuration.getFromEmailName());

			emailMessageEvent.fire(new EmailMessageEvent(EventOperation.CREATE, message));

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Problem creating the addresses to send the email", e);
		}
	}

	private void registerNotificationForParentsAboutConfirmationRejection(final BirthRegistration birthRegistration) {
		final Configuration configuration = configurationManager.getConfiguration();
		final Map<String, Object> params = new HashMap<>();
		params.put("birthRegistration", birthRegistration);

		try {
			final InternetAddress from = new InternetAddress(configuration.getFromEmailAddress(),
					configuration.getFromEmailName());
			final Parent firstParent = birthRegistration.getFirstParent();

			final List<InternetAddress> destinations = new ArrayList<>();
			destinations.add(new InternetAddress(firstParent.getEmail(), firstParent.getName()));

			final Parent secondParent = birthRegistration.getSecondParent();

			final EmailMessage message = emailMessageService.create(from, destinations,
					MessageTemplateInfo.NOTIFY_PARENTS_ON_SECOND_PARENT_REJECTION, params, params,
					configuration.getFromEmailAddress(), configuration.getFromEmailName());

			emailMessageEvent.fire(new EmailMessageEvent(EventOperation.CREATE, message));

			if (secondParent != null && secondParent.getDeceased() != Boolean.TRUE) {
				final List<InternetAddress> destinationsSecondParent = new ArrayList<>();
				destinationsSecondParent.add(new InternetAddress(secondParent.getEmail(), secondParent.getName()));
				final EmailMessage messageSecondParent = emailMessageService.create(from, destinationsSecondParent,
						MessageTemplateInfo.NOTIFY_PARENTS_ON_SECOND_PARENT_REJECTION, params, params,
						configuration.getFromEmailAddress(), configuration.getFromEmailName());
				emailMessageEvent.fire(new EmailMessageEvent(EventOperation.CREATE, messageSecondParent));

			}

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Problem creating the addresses to send the email", e);
		}
	}

	private void registerNewBirthRegistrationNotificationForHospitalUnit(final BirthRegistration birthRegistration) {
		final Organization hospitalUnit = birthRegistration.getHospitalUnit();
		if (StringUtils.isNotBlank(hospitalUnit.getEmail())) {
			final Configuration configuration = configurationManager.getConfiguration();

			final Map<String, Object> params = new HashMap<>();
			params.put("birthRegistration", birthRegistration);

			try {
				final InternetAddress from = new InternetAddress(configuration.getFromEmailAddress(),
						configuration.getFromEmailName());
				final List<InternetAddress> destinations = Arrays
						.asList(new InternetAddress(hospitalUnit.getEmail(), hospitalUnit.getName()));
				final EmailMessage message = emailMessageService.create(from, destinations,
						MessageTemplateInfo.NOTIFY_HOSPITAL_UNIT_OF_NEW_BIRTH_REGISTRATION, params, params,
						configuration.getFromEmailAddress(), configuration.getFromEmailName());

				emailMessageEvent.fire(new EmailMessageEvent(EventOperation.CREATE, message));

			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("Problem creating the addresses to send the email", e);
			}
		}
	}

	private void registerNewBirthRegistrationNotificationForCivilRegistrationOffice(
			final BirthRegistration birthRegistration) {
		emailMessageService.registerNewBirthRegistrationNotificationForCivilRegistrationOffice(birthRegistration);
	}

	private Counter getOrCreateNextCounterFor(final int year) {
		final String code = BIRTH_REGISTRATION_COUNTER_PREFIX + year;
		Counter counter = counterService.findByCode(code);
		if (counter == null) {
			counter = new Counter(code);
			counterService.create(counter);
		} else {
			counter.increment();
			counter = counterService.update(counter);
		}
		return counter;
	}

	public List<String> findOtherHospitalUnits(final String name, final SelectionRange range) {
		return getDAO().findOtherHospitalUnits(name, range);
	}
	
	public StatisticDTO findYesterdayStatistics(){
		return getDAO().findYesterdayStatistics();
	}
	
}