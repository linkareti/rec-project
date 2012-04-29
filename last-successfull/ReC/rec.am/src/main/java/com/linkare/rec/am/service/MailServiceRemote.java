package com.linkare.rec.am.service;

import java.rmi.RemoteException;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.linkare.rec.am.model.util.BusinessException;
import com.linkare.rec.am.model.util.MailMessageRequest;
import com.linkare.rec.am.model.util.NoValidRecipientsFoundForMessage;

/**
 * 
 * @author Bruno Catarino - Linkare TI
 *
 */
@WebService
@Remote
public interface MailServiceRemote {

    /**
     * Queues a new mail message in the queue to be processed asynchronously.
     * 
     * @param request
     *            The information about the mail to process. The <code>to</code> or the <code>recipients</code> properties should be correctly filled with at
     *            least one valid e-mail.
     * @throws BusinessException
     *             If an unexpected error occurs.
     * @throws NoValidRecipientsFoundForMessage
     *             If no valid e-mails are given.
     * @throws RemoteException
     */
    @WebMethod
    void queueMessage(MailMessageRequest request) throws NoValidRecipientsFoundForMessage, RemoteException;
}