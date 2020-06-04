package com.linkare.irn.nascimento.service.message;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.linkare.irn.nascimento.model.message.EmailMessage;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class MessageSenderSchedulerService {

    @Inject
    private EmailMessageService emailMessageService;

    @Inject
    private MessageSenderService messageSenderService;

    @Schedule(minute = "*/1", hour = "*")
    public void sendPendingMessages() {
	final List<EmailMessage> pendingMessages = emailMessageService.findPending();
	for (final EmailMessage message : pendingMessages) {
	    messageSenderService.sendEmail(message);
	}
    }
}