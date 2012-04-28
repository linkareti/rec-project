package com.linkare.rec.am.model;

import java.io.Serializable;

import com.linkare.rec.am.model.util.MailMessageRequest;

/**
 * Represents a mail message that failed to be sent.
 * 
 * @author Bruno Catarino - Linkare TI
 * 
 */
public class FailedMailMessage implements Serializable {

    private static final long serialVersionUID = -7208340101096371615L;

    private MailMessageRequest mailRequest;
    private int failCount;

    public FailedMailMessage(MailMessageRequest request, int failCount) {
	this.mailRequest = request;
	this.failCount = failCount;
    }

    public MailMessageRequest getMailRequest() {
	return mailRequest;
    }

    public int getFailCount() {
	return failCount;
    }
}