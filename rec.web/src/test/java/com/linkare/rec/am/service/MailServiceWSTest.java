package com.linkare.rec.am.service;

import com.linkare.rec.web.mail.MailMessageRequest;
import com.linkare.rec.web.mail.MailServiceRemote;
import com.linkare.rec.web.mail.NoValidRecipientsFoundForMessage;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Locale;

import javax.ejb.EJBException;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.linkare.rec.web.model.util.BusinessException;

public class MailServiceWSTest {

    private MailServiceRemote remote;
    private String address;
    private URL wsdlURL;

    @Before
    public void setup() {

	address = "http://localhost:8080/rec-services/MailServiceWS";
	try {
	    wsdlURL = new URL(address + "?wsdl");
	} catch (MalformedURLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	Service service = Service.create(wsdlURL, new QName("http://webservices.linkare.com/rec", "rec-services"));
	remote = service.getPort(MailServiceRemote.class);
    }

    @Test
    public void testQueueMailDefaultLocale() {

	MailMessageRequest request = new MailMessageRequest();
	request.setFrom("noreply@linkare.com");
	request.setTo("bcatarino@linkare.com");
	request.setRecipients(new String[] { "bcatarino@linkare.com", "bcatarino@gmail.com", "jflorindo@linkare.com", "gpereira@linkare.com",
		"jpereira@linkare.com" });
	request.setSubject("PENDULO_DUPLO_MOTORIZADO_V1.0/Wed_May_09_10_56_45_WEST_2012");
	request.setContent("This is an example of what should appear here!");
	request.setClientLocale(Locale.getDefault());

	try {
	    remote.queueMessage(request);
	} catch (BusinessException e) {
	    e.printStackTrace();
	    Assert.fail();
	} catch (NoValidRecipientsFoundForMessage e) {
	    e.printStackTrace();
	    Assert.fail();
	} catch (RemoteException e) {
	    e.printStackTrace();
	    Assert.fail();
	}
    }

    @Test
    public void testQueueMailLocaleEN() {

	MailMessageRequest request = new MailMessageRequest();
	request.setFrom("noreply@linkare.com");
	request.setTo("bcatarino@linkare.com");
	request.setRecipients(new String[] { "bcatarino@linkare.com", "bcatarino@gmail.com", "jflorindo@linkare.com", "gpereira@linkare.com",
		"jpereira@linkare.com" });
	request.setSubject("PENDULO_DUPLO_MOTORIZADO_V1.0/Wed_May_09_10_56_45_WEST_2012");
	request.setContent("This is an example of what should appear here!");
	request.setClientLocale(Locale.UK);

	try {
	    remote.queueMessage(request);
	} catch (BusinessException e) {
	    e.printStackTrace();
	    Assert.fail();
	} catch (NoValidRecipientsFoundForMessage e) {
	    e.printStackTrace();
	    Assert.fail();
	} catch (RemoteException e) {
	    e.printStackTrace();
	    Assert.fail();
	}
    }

    @Test
    public void testQueueMailLocaleFR() {

	MailMessageRequest request = new MailMessageRequest();
	request.setFrom("noreply@linkare.com");
	request.setTo("bcatarino@linkare.com");
	request.setRecipients(new String[] { "bcatarino@linkare.com", "bcatarino@gmail.com", "jflorindo@linkare.com", "gpereira@linkare.com",
		"jpereira@linkare.com" });
	request.setSubject("PENDULO_DUPLO_MOTORIZADO_V1.0/Wed_May_09_10_56_45_WEST_2012");
	request.setContent("This is an example of what should appear here!");
	request.setClientLocale(Locale.FRANCE);

	try {
	    remote.queueMessage(request);
	} catch (BusinessException e) {
	    e.printStackTrace();
	    Assert.fail();
	} catch (NoValidRecipientsFoundForMessage e) {
	    e.printStackTrace();
	    Assert.fail();
	} catch (RemoteException e) {
	    e.printStackTrace();
	    Assert.fail();
	}
    }

    @Test
    public void testQueueMailNoLocale() {

	MailMessageRequest request = new MailMessageRequest();
	request.setFrom("noreply@linkare.com");
	request.setTo("bcatarino@linkare.com");
	request.setRecipients(new String[] { "bcatarino@linkare.com", "bcatarino@gmail.com", "jflorindo@linkare.com", "gpereira@linkare.com",
		"jpereira@linkare.com" });
	request.setSubject("PENDULO_DUPLO_MOTORIZADO_V1.0/Wed_May_09_10_56_45_WEST_2012");
	request.setContent("This is an example of what should appear here!");

	try {
	    remote.queueMessage(request);
	} catch (BusinessException e) {
	    e.printStackTrace();
	    Assert.fail();
	} catch (NoValidRecipientsFoundForMessage e) {
	    e.printStackTrace();
	    Assert.fail();
	} catch (RemoteException e) {
	    e.printStackTrace();
	    Assert.fail();
	} catch (EJBException e) {
	    e.printStackTrace();
	    if (!(e.getCause() instanceof IllegalArgumentException)) {
		Assert.fail();
	    }
	}
    }

    @Test
    public void testQueueInvalidMail() {
	MailMessageRequest request = new MailMessageRequest();
	request.setFrom("noreply@linkare.com");
	request.setTo("");
	request.setRecipients(new String[] { "bcatarino@linkare.com", "aaaa", "teste", "bcatarino@gmail.com" });
	request.setSubject("PENDULO_DUPLO_MOTORIZADO_V1.0/Wed_May_02_14_48_37_WEST_2012");
	request.setContent("Another example");
	request.setClientLocale(Locale.getDefault());
	try {
	    remote.queueMessage(request);
	} catch (BusinessException e) {
	    e.printStackTrace();
	    Assert.fail();
	} catch (NoValidRecipientsFoundForMessage e) {
	    e.printStackTrace();
	    Assert.fail();
	} catch (RemoteException e) {
	    e.printStackTrace();
	    Assert.fail();
	}
    }

    @Test
    public void testQueueNoValidRecipients() {
	MailMessageRequest request = new MailMessageRequest();
	request.setFrom("noreply@linkare.com");
	request.setTo("");
	request.setRecipients(new String[] { "aaaa", "teste" });
	request.setSubject("PENDULO_DUPLO_MOTORIZADO_V1.0/Wed_May_02_14_32_51_WEST_2012");
	request.setContent("One more");
	request.setClientLocale(Locale.getDefault());
	try {
	    remote.queueMessage(request);
	} catch (BusinessException e) {
	    e.printStackTrace();
	    Assert.fail();
	} catch (NoValidRecipientsFoundForMessage e) {
	    e.printStackTrace();
	    Assert.assertTrue(true);
	} catch (RemoteException e) {
	    e.printStackTrace();
	    Assert.fail();
	}
    }

    @Test
    public void testQueueInexistentExperienceId() {
	MailMessageRequest request = new MailMessageRequest();
	request.setFrom("noreply@linkare.com");
	request.setTo("");
	request.setRecipients(new String[] { "bcatarino@linkare.com", "aaaa", "teste", "bcatarino@gmail.com" });
	request.setSubject("PENDULO_DUPLO_MOTORIZADO_V1.0/Wed_May_02_14_32_51_WEST_2011");
	request.setContent("One more");
	request.setClientLocale(Locale.getDefault());
	try {
	    remote.queueMessage(request);
	} catch (BusinessException e) {
	    e.printStackTrace();
	    Assert.fail();
	} catch (NoValidRecipientsFoundForMessage e) {
	    e.printStackTrace();
	    Assert.fail();
	} catch (RemoteException e) {
	    e.printStackTrace();
	    Assert.fail();
	}
    }
}