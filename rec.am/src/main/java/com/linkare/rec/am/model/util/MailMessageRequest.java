/**
 * 
 */
package com.linkare.rec.am.model.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author jpereira
 * @author Bruno Catarino - Linkare TI
 * 
 */
public class MailMessageRequest implements Serializable {

    private static final long serialVersionUID = -177035431863395847L;

    private String from;
    private String to;
    private String[] recipients;
    private String content;
    private MailFormatEnum mailFormat;
    private String subject;
    private List<Attachment> attachments;
    private List<MimePart> mimeParts;
    private Locale clientLocale;

    private int recipientsPerBlockMaxSize;
    private int maxFailCount;
    private int sendMailRetryMillis;

    /**
     * 
     */
    public MailMessageRequest() {
    }

    public MailMessageRequest(MailMessageRequest request, String to, String[] recipients) {
	this(request.getFrom(), to, recipients, request.getContent(), request.getMailFormat(), request.getSubject(), request.getAttachments(),
	     request.getMimeParts());
	setClientLocale(request.getClientLocale());
	setMailProperties(request.getRecipientsPerBlockMaxSize(), request.getMaxFailCount(), request.getSendMailRetryMillis());
    }

    public MailMessageRequest(String from, String to, String[] recipients, String content, MailFormatEnum mailFormat, String subject,
	    List<Attachment> attachments, List<MimePart> mimeParts) {
	super();
	this.from = from;
	this.to = to;
	this.recipients = recipients;
	this.content = content;
	this.mailFormat = mailFormat;
	this.subject = subject;
	this.attachments = attachments;
	this.mimeParts = mimeParts;
    }

    /**
     * @return the recipients
     */
    public String[] getRecipients() {
	return recipients;
    }

    /**
     * @param recipients
     *            the recipients to set
     */
    public void setRecipients(String[] recipients) {
	this.recipients = recipients;
    }

    /**
     * @return the content
     */
    public String getContent() {
	return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
	this.content = content;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
	return subject;
    }

    /**
     * @param subject
     *            the subject to set
     */
    public void setSubject(String subject) {
	this.subject = subject;
    }

    /**
     * @return the attachments
     */
    public List<Attachment> getAttachments() {
	return attachments;
    }

    /**
     * @param attachments
     *            the attachments to set
     */
    public void setAttachments(List<Attachment> attachments) {
	this.attachments = attachments;
    }

    /**
     * @return the mimeParts
     */
    public List<MimePart> getMimeParts() {
	return mimeParts;
    }

    /**
     * @param mimeParts
     *            the mimeParts to set
     */
    public void setMimeParts(List<MimePart> mimeParts) {
	this.mimeParts = mimeParts;
    }

    /**
     * @return the mailFormat
     */
    public MailFormatEnum getMailFormat() {
	return mailFormat;
    }

    /**
     * @param mailFormat
     *            the mailFormat to set
     */
    public void setMailFormat(MailFormatEnum mailFormat) {
	this.mailFormat = mailFormat;
    }

    /**
     * @return the from
     */
    public String getFrom() {
	return from;
    }

    /**
     * @param from
     *            the from to set
     */
    public void setFrom(String from) {
	this.from = from;
    }

    /**
     * @return the to
     */
    public String getTo() {
	return to;
    }

    /**
     * @param to
     *            the to to set
     */
    public void setTo(String to) {
	this.to = to;
    }

    public Locale getClientLocale() {
	return clientLocale;
    }

    public void setClientLocale(Locale clientLocale) {
	this.clientLocale = clientLocale;
    }

    public int getRecipientsPerBlockMaxSize() {
	return recipientsPerBlockMaxSize;
    }

    public void setRecipientsPerBlockMaxSize(int recipientsPerBlockMaxSize) {
	this.recipientsPerBlockMaxSize = recipientsPerBlockMaxSize;
    }

    public int getMaxFailCount() {
	return maxFailCount;
    }

    public void setMaxFailCount(int maxFailCount) {
	this.maxFailCount = maxFailCount;
    }

    public int getSendMailRetryMillis() {
	return sendMailRetryMillis;
    }

    public void setSendMailRetryMillis(int sendMailRetryMillis) {
	this.sendMailRetryMillis = sendMailRetryMillis;
    }

    public void setMailProperties(int recipientsPerBlockMaxSize, int maxFailCount, int sendMailRetryMillis) {
	setRecipientsPerBlockMaxSize(recipientsPerBlockMaxSize);
	setMaxFailCount(maxFailCount);
	setSendMailRetryMillis(sendMailRetryMillis);
    }

    public long calculateSizeBytes() {

	ObjectOutputStream oos = null;
	ByteArrayOutputStream bos = null;
	try {
	    bos = new ByteArrayOutputStream();
	    oos = new ObjectOutputStream(bos);
	    oos.writeObject(this);
	    oos.flush();
	    bos.flush();
	    return bos.size();
	} catch (IOException ignored) {

	} finally {
	    if (oos != null)
		try {
		    oos.close();
		} catch (IOException ignored) {
		}
	}

	return -1;
    }

    public String toShortString() {
	return "[from=" + from + ", to=" + to + ", recipients=" + Arrays.toString(recipients) + ", subject=" + subject + "]";
    }

    @Override
    public String toString() {
	return "MailMessageRequest [from=" + from + ", to=" + to + ", recipients=" + Arrays.toString(recipients) + ", content=" + content + ", mailFormat="
		+ mailFormat + ", subject=" + subject + ", attachments=" + attachments + ", mimeParts=" + mimeParts + ", clientLocale=" + clientLocale + "]";
    }
}
