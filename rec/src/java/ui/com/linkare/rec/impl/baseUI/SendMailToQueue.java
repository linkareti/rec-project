/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.impl.baseUI;

import com.linkare.rec.am.mail.MailMessageRequest;
import com.linkare.rec.am.mail.MailServiceRemote;
import com.linkare.rec.am.mail.NoValidRecipientsFoundForMessage;
import com.linkare.rec.impl.baseUI.table.DefaultExperimentDataTable;
import com.linkare.rec.impl.newface.utils.WSServiceLocator;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 * @author Gedsimon Pereira - Linkare TI
 */
public class SendMailToQueue {

    public SendMailToQueue() {
    }
    
    boolean isPrivatePc = Preferences.userRoot().getBoolean("ElabPrivateComputer", true);
    
    public String sendMail(String recipients, String acquisitionConfig, String dataProducerName) {
       
        
        String lookupAddress = System.getProperty("rec.am.endpoint");
        try {
            if(checkMaxNumberRecipients(recipients.split(";")))
                return ResourceBundle.getBundle("com.linkare.rec.impl.newface.component.resources.SendMailBox").getString("error.number.max.addressee");
            
            MailMessageRequest mailMessageRequest = new MailMessageRequest();
            mailMessageRequest.setSubject(dataProducerName);
            mailMessageRequest.setClientLocale(Locale.getDefault());
            
            mailMessageRequest.setRecipients(recipients.split(";"));
            mailMessageRequest.setContent(acquisitionConfig);

            String namespace = ResourceBundle.getBundle("com.linkare.rec.impl.newface.resources.wsconfig").getString("mail.namespace");
            String part = java.util.ResourceBundle.getBundle("com.linkare.rec.impl.newface.resources.wsconfig").getString("mail.part");
            MailServiceRemote remote = WSServiceLocator.lookup(lookupAddress, namespace, part, MailServiceRemote.class);

            remote.queueMessage(mailMessageRequest);
           
            if (isPrivatePc)
                            Preferences.userRoot().put("ElabRecipientsMail", recipients);
            
        } catch (NoValidRecipientsFoundForMessage ex) {
            Logger.getLogger(DefaultExperimentDataTable.class.getName()).log(Level.SEVERE, null, ex);
            return ResourceBundle.getBundle("com.linkare.rec.impl.newface.component.resources.SendMailBox").getString("email.invalid");
        } catch (RemoteException ex) {
            Logger.getLogger(DefaultExperimentDataTable.class.getName()).log(Level.SEVERE, null, ex);
            return ResourceBundle.getBundle("com.linkare.rec.impl.newface.component.resources.SendMailBox").getString("erro.server");
        } catch (RuntimeException ex){
            Logger.getLogger(DefaultExperimentDataTable.class.getName()).log(Level.SEVERE, null, ex);
            return ResourceBundle.getBundle("com.linkare.rec.impl.newface.component.resources.SendMailBox").getString("erro.server");
        }
        return null;
    }
    
    public static boolean checkMaxNumberRecipients(String[] recipients){
            return recipients.length > 5 ? true : false;
    }
    
}
