package com.linkare.irn.nascimento.service.message;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
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
import org.joda.time.DateTimeZone;

import com.linkare.irn.nascimento.dao.message.EmailMessageDAO;
import com.linkare.irn.nascimento.model.config.Configuration;
import com.linkare.irn.nascimento.model.core.BirthRegistration;
import com.linkare.irn.nascimento.model.ext.mapping.statistic.StatisticDTO;
import com.linkare.irn.nascimento.model.identification.Parent;
import com.linkare.irn.nascimento.model.message.EmailMessage;
import com.linkare.irn.nascimento.model.message.IMessageTemplate;
import com.linkare.irn.nascimento.model.message.MessageTemplateInfo;
import com.linkare.irn.nascimento.model.organization.Organization;
import com.linkare.irn.nascimento.model.organization.OrganizationType;
import com.linkare.irn.nascimento.service.BaseStatelessServiceImpl;
import com.linkare.irn.nascimento.service.cdi.EventOperation;
import com.linkare.irn.nascimento.service.cdi.message.EmailMessageEvent;
import com.linkare.irn.nascimento.service.organization.OrganizationService;
import com.linkare.irn.nascimento.web.ConfigurationManager;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class EmailMessageService extends BaseStatelessServiceImpl<EmailMessage, EmailMessageDAO> {

	@Inject
	private MessageTemplateService messageTemplateService;

	@Inject
	private Event<EmailMessageEvent> emailMessageEvent;

	@Inject
	private ConfigurationManager configurationManager;
	
	@Inject
	private OrganizationService organizationService;

	@Override
	public EmailMessageDAO getDAO() {
		return new EmailMessageDAO(getEntityManager());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public EmailMessage create(final InternetAddress from, final List<InternetAddress> destinations,
			final MessageTemplateInfo messageTemplateInfo, final Map<String, Object> subjectParams,
			final Map<String, Object> contentParams, String userName, String displayName) {
		String templateCode = messageTemplateInfo.getCode();
		final IMessageTemplate template = messageTemplateService.findByCode(templateCode);
		if (template == null) {
			throw new EntityNotFoundException("No template found for code " + templateCode);
		}

		final EmailMessage message = MessageFactory.toEmailMessage(configurationManager.getConfiguration(), from,
				destinations, template, subjectParams, contentParams);
		message.setDisplayName(displayName);
		message.setUsername(userName);
		return super.create(message);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public EmailMessage update(EmailMessage t, Class<?>... validationGroups) {
		return super.update(t, validationGroups);
	}

	public List<EmailMessage> findPending() {
		return getDAO().findPending();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void registerNotificationForFirstParent(final BirthRegistration birthRegistration) {
		final Configuration configuration = configurationManager.getConfiguration();

		final Map<String, Object> params = new HashMap<>();
		params.put("birthRegistration", birthRegistration);

		try {
			final Parent firstParent = birthRegistration.getFirstParent();

			final InternetAddress from = new InternetAddress(configuration.getFromEmailAddress(),
					configuration.getFromEmailName());
			final List<InternetAddress> destinations = Arrays
					.asList(new InternetAddress(firstParent.getEmail(), firstParent.getName()));
			final EmailMessage message = create(from, destinations,
					MessageTemplateInfo.NOTIFY_FIRST_PARENT_ABOUT_SUBMISSION_WHEN_CONFIRMATION_NOT_REQUIRED, params,
					params, configuration.getFromEmailAddress(), configuration.getFromEmailName());

			emailMessageEvent.fire(new EmailMessageEvent(EventOperation.CREATE, message));

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Problem creating the addresses to send the email", e);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void registerNewBirthRegistrationNotificationForCivilRegistrationOffice(
			final BirthRegistration birthRegistration) {
		
		final Organization hospitalUnit = birthRegistration.getHospitalUnit();
		
		if (StringUtils.isNotBlank(hospitalUnit.getSecondEmail())) {
			
			final Organization conservatory = organizationService.findActiveByTypeAndEmail(OrganizationType.CIVIL_REGISTRATION_OFFICE, hospitalUnit.getSecondEmail());
			
			final Configuration configuration = configurationManager.getConfiguration();

			final Map<String, Object> params = new HashMap<>();
			params.put("birthRegistration", birthRegistration);

			try {
				final InternetAddress from = new InternetAddress(configuration.getFromEmailAddress(),
						configuration.getFromEmailName());
				final List<InternetAddress> destinations = Arrays
						.asList(new InternetAddress(conservatory.getEmail(), conservatory.getName()));
				final EmailMessage message = create(from, destinations,
						MessageTemplateInfo.NOTIFY_CIVIL_REGISTRATION_OFFICE_OF_NEW_BIRTH_REGISTRATION, params, params,
						configuration.getFromEmailAddress(), configuration.getFromEmailName());

				emailMessageEvent.fire(new EmailMessageEvent(EventOperation.CREATE, message));

			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("Problem creating the addresses to send the email", e);
			}
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void sendStatistics(final StatisticDTO statisticDTO) {

		if (statisticDTO != null) {
			final Configuration configuration = configurationManager.getConfiguration();

			final Map<String, Object> params = new HashMap<>();
			params.put("statistic", statisticDTO);

			try {
				final InternetAddress from = new InternetAddress(configuration.getFromEmailAddress(),
						configuration.getFromEmailName());
				final List<InternetAddress> destinations = Arrays
						.asList(new InternetAddress(configuration.getStatisticsEmail(), "IRN"));

				final EmailMessage message = create(from, destinations, MessageTemplateInfo.NOTIFY_IRN_OF_STATISTICS,
						params, params, configuration.getFromEmailAddress(), configuration.getFromEmailName());

				emailMessageEvent.fire(new EmailMessageEvent(EventOperation.CREATE, message));

			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("Problem creating the addresses to send the email", e);
			}
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void registerNotificationsWhenSecondParentConfirmationIsRequired(BirthRegistration birthRegistration) {
		final Configuration configuration = configurationManager.getConfiguration();

		final DateTime now = new DateTime(DateTimeZone.forID(configuration.getTimeZone()));
		final DateTime expirationDate = now
				.plusDays(configuration.getNumberOfDaysOfInactivityToExpireBirthRegistrations());
		final Map<String, Object> params = new HashMap<>();
		params.put("birthRegistration", birthRegistration);
		params.put("expirationDate", expirationDate.toDate());

		try {
			notifyFirstParentWhenSecondParentConfirmationIsRequired(birthRegistration, configuration, params);
			notifySecondParentWhenSecondParentConfirmationIsRequired(birthRegistration, configuration, params);

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Problem creating the addresses to send the email", e);
		}
	}

	private void notifyFirstParentWhenSecondParentConfirmationIsRequired(final BirthRegistration birthRegistration,
			final Configuration configuration, final Map<String, Object> params) throws UnsupportedEncodingException {
		final InternetAddress from = new InternetAddress(configuration.getFromEmailAddress(),
				configuration.getFromEmailName());
		final Parent firstParent = birthRegistration.getFirstParent();

		final List<InternetAddress> firstParentDestinations = Arrays
				.asList(new InternetAddress(firstParent.getEmail(), firstParent.getName()));
		final EmailMessage firstParentMessage = create(from, firstParentDestinations,
				MessageTemplateInfo.NOTIFY_FIRST_PARENT_ABOUT_SUBMISSION_WHEN_CONFIRMATION_REQUIRED, params, params,
				configuration.getFromEmailAddress(), configuration.getFromEmailName());

		emailMessageEvent.fire(new EmailMessageEvent(EventOperation.CREATE, firstParentMessage));
	}

	private void notifySecondParentWhenSecondParentConfirmationIsRequired(final BirthRegistration birthRegistration,
			final Configuration configuration, final Map<String, Object> params) throws UnsupportedEncodingException {
		final InternetAddress from = new InternetAddress(configuration.getFromEmailAddress(),
				configuration.getFromEmailName());
		final Parent secondParent = birthRegistration.getSecondParent();

		final List<InternetAddress> secondParentDestinations = Arrays
				.asList(new InternetAddress(secondParent.getEmail(), secondParent.getName()));
		final EmailMessage secondParentMessage = create(from, secondParentDestinations,
				MessageTemplateInfo.NOTIFY_SECOND_PARENT_WITH_CONFIRMATION_URL, params, params,
				configuration.getFromEmailAddress(), configuration.getFromEmailName());

		emailMessageEvent.fire(new EmailMessageEvent(EventOperation.CREATE, secondParentMessage));
	}
}