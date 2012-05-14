/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.impl.baseUI;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBException;

import com.linkare.rec.am.config.Apparatus;
import com.linkare.rec.am.config.Lab;
import com.linkare.rec.am.config.LocalizationBundle;
import com.linkare.rec.am.config.ReCFaceConfig;
import com.linkare.rec.am.mail.MailMessageRequest;
import com.linkare.rec.am.mail.MailServiceRemote;
import com.linkare.rec.am.mail.NoValidRecipientsFoundForMessage;
import com.linkare.rec.data.config.ChannelAcquisitionConfig;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.baseUI.table.DefaultExperimentDataTable;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.newface.utils.WSServiceLocator;

/**
 * 
 * @author Gedsimon Pereira - Linkare TI
 */
public class SendMailToQueue {

	static {
		try {
			InputStream stream = SendMailToQueue.class.getClassLoader().getResourceAsStream("ReCFaceConfig.xml");
			ReCFaceConfig recFaceConfig = ReCFaceConfig.unmarshall(stream);
			for (final LocalizationBundle bundle : recFaceConfig.getLocalizationBundle()) {
				ReCResourceBundle.loadResourceBundle(bundle.getName(), bundle.getLocation());
			}
			for (final Lab lab : recFaceConfig.getLab()) {
				for (final LocalizationBundle bundle : lab.getLocalizationBundle()) {
					ReCResourceBundle.loadResourceBundle(bundle.getName(), bundle.getLocation());
				}
				for (final Apparatus apparatus : lab.getApparatus()) {
					for (final LocalizationBundle bundle : apparatus.getLocalizationBundle()) {
						ReCResourceBundle.loadResourceBundle(bundle.getName(), bundle.getLocation());
					}
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError("It was not possible to load ReCFaceConfig.xml");
		}
	}

	public SendMailToQueue() {
	}

	boolean isPrivatePc = Preferences.userRoot().getBoolean("ElabPrivateComputer", true);

	public String sendMail(final String recipients, final HardwareAcquisitionConfig acquisitionConfig,
			final String dataProducerName) {

		String lookupAddress = System.getProperty("rec.am.endpoint");
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
			mailMessageRequest.setContent(translatePropertyBundles(acquisitionConfig).toString());

			String namespace = ResourceBundle.getBundle("com.linkare.rec.impl.newface.resources.wsconfig").getString(
					"mail.namespace");
			String part = java.util.ResourceBundle.getBundle("com.linkare.rec.impl.newface.resources.wsconfig")
					.getString("mail.part");
			MailServiceRemote remote = WSServiceLocator.lookup(lookupAddress, namespace, part, MailServiceRemote.class);

			remote.queueMessage(mailMessageRequest);

			if (isPrivatePc)
				Preferences.userRoot().put("ElabRecipientsMail", recipients);

		} catch (NoValidRecipientsFoundForMessage ex) {
			Logger.getLogger(DefaultExperimentDataTable.class.getName()).log(Level.SEVERE, null, ex);
			return ResourceBundle.getBundle("com.linkare.rec.impl.newface.component.resources.SendMailBox").getString(
					"email.invalid");
		} catch (RemoteException ex) {
			Logger.getLogger(DefaultExperimentDataTable.class.getName()).log(Level.SEVERE, null, ex);
			return ResourceBundle.getBundle("com.linkare.rec.impl.newface.component.resources.SendMailBox").getString(
					"erro.server");
		} catch (RuntimeException ex) {
			Logger.getLogger(DefaultExperimentDataTable.class.getName()).log(Level.SEVERE, null, ex);
			return ResourceBundle.getBundle("com.linkare.rec.impl.newface.component.resources.SendMailBox").getString(
					"erro.server");
		}
		return null;
	}

	/**
	 * @param acquisitionConfig
	 */
	private HardwareAcquisitionConfig translatePropertyBundles(HardwareAcquisitionConfig acquisitionConfig) {
		HardwareAcquisitionConfig otherConfig = new HardwareAcquisitionConfig(acquisitionConfig);
		for (ChannelAcquisitionConfig channel : otherConfig.getChannelsConfig()) {
			channel.setChannelName(getTranslatedString(channel.getChannelName()));
			channel.getSelectedScale().setScaleLabel(getTranslatedString(channel.getSelectedScale().getScaleLabel()));
			channel.getSelectedScale().setPhysicsUnitName(
					getTranslatedString(channel.getSelectedScale().getPhysicsUnitName()));
			channel.getSelectedScale().setPhysicsUnitSymbol(
					getTranslatedString(channel.getSelectedScale().getPhysicsUnitSymbol()));
		}
		return otherConfig;
	}

	private String getTranslatedString(String key) {
		try {
			String translatedName = ReCResourceBundle.findString(key);
			if (translatedName != null && !translatedName.trim().equals("")) {
				return translatedName;
			}
			return key;
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return key;
		}
	}

	public static boolean checkMaxNumberRecipients(String[] recipients) {
		return recipients.length > 5 ? true : false;
	}

}
