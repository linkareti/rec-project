package com.linkare.rec.am.service;

import java.rmi.RemoteException;
import java.util.Properties;

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
    public void testQueueMail() {
	MailMessageRequest request = new MailMessageRequest();
	request.setFrom("noreply@linkare.com");
	request.setTo("bcatarino@linkare.com");
	request.setRecipients(new String[] { "bcatarino@linkare.com", "bcatarino@gmail.com", "jflorindo@linkare.com", "gpereira@linkare.com" });
	request.setSubject("Assync Mail Test");
	request.setContent("Peço desculpa a quem receba o spam, mas isto é um teste! :D Just delete it and move on!!");
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
    public void testQueueInvalidMail() {
	MailMessageRequest request = new MailMessageRequest();
	request.setFrom("noreply@linkare.com");
	request.setTo("");
	request.setRecipients(new String[] { "bcatarino@linkare.com", "aaaa", "teste", "bcatarino@gmail.com" });
	request.setSubject("Another assync mail");
	request.setContent("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz gfdgfdgfd fdfdfds");
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
	request.setSubject("No Recipients");
	request.setContent("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz gfdgfdgfd fdfdfds");
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