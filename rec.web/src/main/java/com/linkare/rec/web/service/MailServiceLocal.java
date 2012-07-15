package com.linkare.rec.web.service;

import javax.ejb.Local;

import com.linkare.rec.web.mail.MailMessageRequest;
import com.linkare.rec.web.model.util.BusinessException;

/**
 * 
 * @author Bruno Catarino - Linkare TI
 *
 */
@Local
public interface MailServiceLocal {

    /**
     * Sends the mail represented by the <code>mailMessageRequest</code> to its destination, while the number of failCount is inferior to the max number set on
     * the server. All the e-mails present in the to fields and the recipients field are sent always as BCC.
     * 
     * @param mailMessageRequest
     *            The information about the mail to be sent. At least one e-mail is mandatory in the <code>to</code> or in the <code>recipients</code>
     *            properties.
     * @param failCount
     *            Number of attempts to send that e-mail already made. If more than X tries are made to send the e-mail, the mail will be discarded and will not
     *            be delivered.
     * @throws BusinessException
     *             If one of the e-mails are invalid or if any other unexpected error occurs.
     */
    public void sendMessages(MailMessageRequest mailMessageRequest, int failCount);
}