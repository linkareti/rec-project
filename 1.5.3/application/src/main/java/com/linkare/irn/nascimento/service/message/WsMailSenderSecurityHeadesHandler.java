package com.linkare.irn.nascimento.service.message;

import java.io.ByteArrayOutputStream;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * @author Sérgio Bogas - Linkare TI
 *
 */
public class WsMailSenderSecurityHeadesHandler implements SOAPHandler<SOAPMessageContext> {

    /**
     * <wsse:Security soapenv:mustUnderstand="1" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
     * <wsse:UsernameToken wsu:Id="UsernameToken-6" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
     * <wsse:Username>username</wsse:Username>
     * <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">password</wsse:Password>
     * <wsse:Nonce EncodingType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary">randomnaumber==</wsse:Nonce>
     * <wsu:Created>dateCreated</wsu:Created> </wsse:UsernameToken> </wsse:Security>
     */

    @Override
    public boolean handleMessage(SOAPMessageContext smc) {

	Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

	logMessage(smc);

	return outboundProperty;

    }

    @Override
    public boolean handleFault(SOAPMessageContext smc) {
	logMessage(smc);
	return true;
    }

    @Override
    public void close(MessageContext mc) {
	// TODO Auto-generated method stub

    }

    @Override
    public Set<QName> getHeaders() {
	// TODO Auto-generated method stub
	return null;
    }

    private void logMessage(SOAPMessageContext smc) {
	final Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
	if (outboundProperty.booleanValue()) {
	    System.out.println("Outbound message:");
	} else {
	    System.out.println("Inbound message:");
	}

	final SOAPMessage message = smc.getMessage();
	try (final ByteArrayOutputStream bout = new ByteArrayOutputStream();) {
	    message.writeTo(bout);
	    String msg = bout.toString("UTF-8");
	    System.out.println(msg);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}