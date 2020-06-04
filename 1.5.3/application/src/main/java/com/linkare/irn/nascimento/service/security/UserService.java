package com.linkare.irn.nascimento.service.security;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.apache.commons.lang.StringUtils;

import com.linkare.irn.nascimento.dao.security.UserDAO;
import com.linkare.irn.nascimento.model.config.Configuration;
import com.linkare.irn.nascimento.model.ext.mapping.security.UserDTO;
import com.linkare.irn.nascimento.model.mapping.Mapper;
import com.linkare.irn.nascimento.model.message.EmailMessage;
import com.linkare.irn.nascimento.model.message.MessageTemplateInfo;
import com.linkare.irn.nascimento.model.organization.Organization;
import com.linkare.irn.nascimento.model.security.Ticket;
import com.linkare.irn.nascimento.model.security.TicketStatus;
import com.linkare.irn.nascimento.model.security.TicketType;
import com.linkare.irn.nascimento.model.security.User;
import com.linkare.irn.nascimento.service.BaseImportableStatelessServiceImpl;
import com.linkare.irn.nascimento.service.cdi.EventOperation;
import com.linkare.irn.nascimento.service.cdi.message.EmailMessageEvent;
import com.linkare.irn.nascimento.service.message.EmailMessageService;
import com.linkare.irn.nascimento.service.organization.OrganizationService;
import com.linkare.irn.nascimento.util.PasswordEncryptor;
import com.linkare.irn.nascimento.web.ConfigurationManager;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserService extends BaseImportableStatelessServiceImpl<User, UserDAO, UserDTO> {

    @Inject
    private OrganizationService organizationService;

    @Inject
    private TicketService ticketService;

    @Inject
    private EmailMessageService emailMessageService;

    @Inject
    private Event<EmailMessageEvent> emailMessageEvent;

    @Inject
    private ConfigurationManager configurationManager;

    @Override
    public void mapCsvColumnsToProperties(Map<String, String> columnMapping) {
	columnMapping.put("externalId", "externalId");
	columnMapping.put("username", "username");
	columnMapping.put("password", "password");
	columnMapping.put("displayName", "displayName");
	columnMapping.put("email", "email");
	columnMapping.put("roleType", "roleType");
	columnMapping.put("organizationExternalId", "organizationExternalId");
    }

    @Override
    public Class<UserDTO> getType() {
	return UserDTO.class;
    }

    @Override
    public UserDAO getDAO() {
	return new UserDAO(getEntityManager());
    }

    @Override
    public void doAdditionalMappings(UserDTO entry, User entity) {
	if (StringUtils.isNotBlank(entry.getOrganizationExternalId())) {
	    final Mapper organizationMapper = getMapperDAO().findByModuleAndExternalId(Organization.class.getSimpleName(), entry.getOrganizationExternalId());
	    if (organizationMapper != null) {
		Organization organization = organizationService.find(organizationMapper.getInternalId());
		entity.setOrganization(organization);
	    }
	}
    }

    @Override
    public void doValidation(final UserDTO entry, final User entity) {
	validate(entity);

	if (StringUtils.isNotBlank(entry.getOrganizationExternalId()) && entity.getOrganization() == null) {
	    throw new ValidationException("Could not find organization with externalId " + entry.getOrganizationExternalId());
	}
    }

    public User authenticate(final String username, final String password) {
	return getDAO().findByUsernameAndPassword(username, PasswordEncryptor.encryptPassword(password));
    }

    public User findByUsername(final String username) {
	return getDAO().findByUsername(username);
    }

    @Override
    protected void validate(User entity, Class<?>... validationGroups) {
	super.validate(entity, validationGroups);

	final User existingUser = getDAO().findByUsername(entity.getUsername());
	if (existingUser != null && !Objects.equals(existingUser.getId(), entity.getId())) {
	    throw new ValidationException("A user with the username " + entity.getUsername() + " already exists");
	}
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public User create(User entity, Class<?>... validationGroups) {
	// generate/override with a valid dummy password before validating user
	if (StringUtils.isBlank(entity.getPassword())) {
	    entity.setPassword(UUID.randomUUID().toString());
	}

	// encrypt password before saving
	entity.setPassword(PasswordEncryptor.encryptPassword(entity.getPassword()));

	return super.create(entity, validationGroups);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void requestPasswordRecovery(final String username) {
	registerPasswordRecoveryEmail(username, MessageTemplateInfo.RECOVER_PASSWORD);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void finishPasswordRecovery(final String username, final String token, final String password, final String confirmPassword) {
	final User user = findByUsername(username);
	if (user == null) {
	    throw new RuntimeException("No user found for username " + username); // handle this as a runtime/unexpected exception (someone is "playing" with the form)
	}

	final Ticket ticket = ticketService.findValidByUsernameAndToken(username, token);
	if (ticket == null) {
	    throw new EntityNotFoundException("No valid ticket for username " + username + " and token " + token);
	}

	if (!Objects.equals(password, confirmPassword)) {
	    throw new ValidationException("Password and confirmation must match");
	}

	user.setPassword(password);
	user.setPassword(PasswordEncryptor.encryptPassword(password));
	getDAO().update(user);

	ticket.setStatus(TicketStatus.FINISHED);
	ticketService.update(ticket);
    }

    private void registerPasswordRecoveryEmail(final String username, final MessageTemplateInfo template) {
	final User user = findByUsername(username);
	if (user == null) {
	    throw new EntityNotFoundException("No user found for username " + username);
	}
	final Ticket ticket = ticketService.create(username, TicketType.PASSWORD_RECOVERY);

	final Map<String, Object> subjectParams = new HashMap<>();
	final Map<String, Object> contentParams = new HashMap<>();

	final Configuration configuration = configurationManager.getConfiguration();
	contentParams.put("applicationBaseUrl", configuration.getApplicationBaseUrl());
	contentParams.put("username", username);
	contentParams.put("token", ticket.getToken());

	try {

	    final InternetAddress from = new InternetAddress(configuration.getFromEmailAddress(), configuration.getFromEmailName());
	    final List<InternetAddress> destinations = Arrays.asList(new InternetAddress(user.getEmail(), user.getDisplayName()));
	    final EmailMessage message = emailMessageService.create(from, destinations, template, subjectParams, contentParams,user.getUsername(),user.getDisplayName());

	    message.setUsername(user.getUsername());
	    message.setDisplayName(user.getDisplayName());

	    emailMessageEvent.fire(new EmailMessageEvent(EventOperation.CREATE, message));
	} catch (UnsupportedEncodingException e) {
	    throw new RuntimeException("Problem creating the addresses to send the email", e);
	}
    }
}