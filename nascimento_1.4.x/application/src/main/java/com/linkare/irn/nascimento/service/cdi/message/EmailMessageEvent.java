package com.linkare.irn.nascimento.service.cdi.message;

import com.linkare.irn.nascimento.model.message.EmailMessage;
import com.linkare.irn.nascimento.service.cdi.BaseEvent;
import com.linkare.irn.nascimento.service.cdi.EventOperation;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class EmailMessageEvent extends BaseEvent<EmailMessage> {

    private static final long serialVersionUID = -669678524505830870L;

    /**
     * @param operation
     * @param payload
     */
    public EmailMessageEvent(EventOperation operation, EmailMessage payload) {
	super(operation, payload);
    }
}