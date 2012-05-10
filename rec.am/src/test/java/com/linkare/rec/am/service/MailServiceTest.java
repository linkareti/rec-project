package com.linkare.rec.am.service;

import java.rmi.RemoteException;
import java.util.Locale;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.linkare.rec.am.model.util.BusinessException;
import com.linkare.rec.am.model.util.MailMessageRequest;
import com.linkare.rec.am.model.util.NoValidRecipientsFoundForMessage;

public class MailServiceTest {

    private MailServiceRemote remote;

    @Before
    public void setup() {

	Properties props = new Properties();
	props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.impl.SerialInitContextFactory");
	props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
	props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
	props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
	props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

	try {
	    InitialContext ic = new InitialContext(props);
	    remote = (MailServiceRemote) ic.lookup("java:global/rec.am/mailService!com.linkare.rec.am.service.MailServiceRemote");

	} catch (NamingException e) {
	    e.printStackTrace();
	} catch (Throwable e) {
	    e.printStackTrace();
	}
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