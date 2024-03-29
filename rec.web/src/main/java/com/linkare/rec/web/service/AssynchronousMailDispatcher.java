package com.linkare.rec.web.service;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.linkare.rec.web.mail.MailMessageRequest;
import com.linkare.rec.web.model.util.BusinessException;

/**
 * Processes mail messages on the mail JMS queue.
 * 
 * @author Bruno Catarino - Linkare TI
 * 
 */
@MessageDriven(mappedName = "jms/MailMessages", activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class AssynchronousMailDispatcher implements MessageListener {

    @EJB
    private MailServiceLocal mailService;

    @Override
    public void onMessage(Message msg) {

	if (!(msg instanceof ObjectMessage)) {
	    return;
	}

	ObjectMessage objectMessage = (ObjectMessage) msg;

	try {
	    mailService.sendMessages((MailMessageRequest) objectMessage.getObject(), 0);
	} catch (BusinessException e) {
	    e.printStackTrace();
	} catch (JMSException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}