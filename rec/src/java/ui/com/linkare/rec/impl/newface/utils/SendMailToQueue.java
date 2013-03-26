package com.linkare.rec.impl.newface.utils;

import java.rmi.RemoteException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import com.linkare.rec.web.mail.MailMessageRequest;
import com.linkare.rec.web.mail.MailServiceRemote;
import com.linkare.rec.web.mail.NoValidRecipientsFoundForMessage;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.ui.table.DefaultExperimentDataTable;

/**
 * 
 * @author Gedsimon Pereira - Linkare TI
 */
public class SendMailToQueue {

	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(SendMailToQueue.class.getName());

	public SendMailToQueue() {
	}

	boolean isPrivatePc = Preferences.userRoot().getBoolean("ElabPrivateComputer", true);

	public String sendMail(final String recipients, final HardwareAcquisitionConfig acquisitionConfig,
			final String dataProducerName) {

		String lookupAddress = ReCSystemProperty.REC_WEB_ENDPOINT.getValue() + "/MailServiceWS";
		try {
			if (checkMaxNumberRecipients(recipients.split(";")))
				return ResourceBundle.getBundle("com.linkare.rec.impl.newface.component.resources.SendMailBox")
						.getString("error.number.max.addressee");

			MailMessageRequest mailMessageRequest = new MailMessageRequest();
			mailMessageRequest.setSubject(dataProducerName);
			mailMessageRequest.setClientLocale(Locale.getDefault());

			String[] recipientsArray = recipients.split(";");
			for (int i = 0; i < recipientsArray.length; i++) {
				recipientsArray[i] = recipientsArray[i].trim();
			}

			mailMessageRequest.setRecipients(recipientsArray);
			mailMessageRequest.setContent(HardwareAcquisitionConfig.translatePropertyBundles(acquisitionConfig)
					.toString());

			String namespace = ResourceBundle.getBundle("com.linkare.rec.impl.newface.resources.wsconfig").getString(
					"mail.namespace");
			String part = java.util.ResourceBundle.getBundle("com.linkare.rec.impl.newface.resources.wsconfig")
					.getString("mail.part");
			MailServiceRemote remote = WSServiceLocator.lookup(lookupAddress, namespace, part, MailServiceRemote.class);

			remote.queueMessage(mailMessageRequest);

			if (isPrivatePc) {
				Preferences.userRoot().put("ElabRecipientsMail", recipients);
			}
		} catch (NoValidRecipientsFoundForMessage ex) {
			LOGGER.log(Level.SEVERE, "Error sending email because:" +ex.getMessage(), ex);
			return ResourceBundle.getBundle("com.linkare.rec.impl.newface.component.resources.SendMailBox").getString(
					"email.invalid");
		} catch (RemoteException ex) {
			LOGGER.log(Level.SEVERE, "Error sending email because:" +ex.getMessage(), ex);
			return ResourceBundle.getBundle("com.linkare.rec.impl.newface.component.resources.SendMailBox").getString(
					"erro.server");
		} catch (RuntimeException ex) {
			LOGGER.log(Level.SEVERE, "Error sending email because:" +ex.getMessage(), ex);
			return ResourceBundle.getBundle("com.linkare.rec.impl.newface.component.resources.SendMailBox").getString(
					"erro.server");
		} catch(Throwable t) {
			LOGGER.log(Level.SEVERE, "Error sending email because:" +t.getMessage(), t);
			return ResourceBundle.getBundle("com.linkare.rec.impl.newface.component.resources.SendMailBox").getString(
					"erro.server");
		}
		return null;
	}

	public static boolean checkMaxNumberRecipients(String[] recipients) {
		int maxNumberRecipientes = Integer.parseInt(ResourceBundle.getBundle(
				"com.linkare.rec.impl.newface.component.resources.SendMailBox").getString("number.max.recipientes"));
		return recipients.length > maxNumberRecipientes ? true : false;
	}
}