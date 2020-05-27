package com.linkare.rec.web.mail;

import java.rmi.RemoteException;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * 
 * @author Bruno Catarino - Linkare TI
 * 
 */
@WebService(name = "MailServiceWS", targetNamespace = "http://webservices.linkare.com/rec")
public interface MailServiceRemote {

    /**
     * Queues a new mail message in the queue to be processed asynchronously.
     * 
     * @param request The information about the mail to process. The <code>to</code> or the <code>recipients</code> properties should be correctly filled with at
     *            least one valid e-mail. The <code>subject</code> must be equal to the experience identifier (OID). This is mandatory, since this id will be
     *            used to get the experience data from the database. The <code>content</code> should contain a string representing the hardware configuration.
     *            This String will be used to create an attachment with the experiment configuration.
     * @throws NoValidRecipientsFoundForMessage If no valid e-mails are given.
     * @throws RemoteException ...
     */
    @WebMethod
    void queueMessage(MailMessageRequest request) throws NoValidRecipientsFoundForMessage, RemoteException;
}
