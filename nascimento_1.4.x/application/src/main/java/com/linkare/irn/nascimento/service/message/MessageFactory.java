package com.linkare.irn.nascimento.service.message;

import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import com.linkare.irn.nascimento.model.config.Configuration;
import com.linkare.irn.nascimento.model.message.EmailMessage;
import com.linkare.irn.nascimento.model.message.IMessageTemplate;
import com.linkare.irn.nascimento.model.message.MessageTemplateType;
import com.linkare.irn.nascimento.util.VelocityUtil;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class MessageFactory {

    private MessageFactory() {
    }

    public static EmailMessage toEmailMessage(final Configuration configuration, final InternetAddress origin, final List<InternetAddress> destinations,
	    final IMessageTemplate template, final Map<String, Object> subjectParams, final Map<String, Object> contentParams) {
	if (template.getType() != MessageTemplateType.EMAIL) {
	    throw new IllegalArgumentException("Template type must be EMAIL and it was " + template.getType());
	}
	if (destinations == null || destinations.isEmpty()) {
	    throw new IllegalArgumentException("Destinations cannot be null nor empty");
	}
	final EmailMessage message = new EmailMessage(origin);
	for (final InternetAddress destination : destinations) {
	    message.addDestination(destination);
	}
	message.setSubject(VelocityUtil.getFromString(getSubjectCode(template.getCode()), template.getSubject(), configuration, subjectParams));
	message.setContent(VelocityUtil.getFromString(getContentCode(template.getCode()), template.getContent(), configuration, contentParams));
	return message;
    }

    private static String getSubjectCode(final String templateCode) {
	return String.format("%s_SUBJECT", templateCode);
    }

    private static String getContentCode(final String templateCode) {
	return String.format("%s_CONTENT", templateCode);
    }
}