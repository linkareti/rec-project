package com.linkare.irn.nascimento.web.fasp;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.UUID;

import javax.xml.XMLConstants;
import javax.xml.bind.DatatypeConverter;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.common.Extensions;
import org.opensaml.saml2.common.impl.ExtensionsBuilder;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.impl.AuthnRequestBuilder;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.util.XMLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class FALoginRequest extends SAMLBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(FALoginRequest.class);

    public FALoginRequest(FAProperties properties) {
	super(properties);
    }

    public String encodeAuthnRequest(AuthnRequest authnRequest, Collection<FARequestedAttribute> requestedAttributes) throws Exception {
	String encodedXmlAuthnRequest = null;
	String xmlAuthnRequest = marshalAuthnRequest(authnRequest);
	String modifiedXmlAuthnRequest = includeFARequestedAttributes(xmlAuthnRequest, requestedAttributes);
	String encryptedXmlAuthnRequest = new EnvelopedXMLSignature(properties).generate(modifiedXmlAuthnRequest, authnRequest.getID());
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug(modifiedXmlAuthnRequest);
	    LOGGER.debug(encryptedXmlAuthnRequest);
	}
	encodedXmlAuthnRequest = base64Encode(encryptedXmlAuthnRequest);
	return encodedXmlAuthnRequest;
    }

    public String base64Encode(String data) throws UnsupportedEncodingException {
	return DatatypeConverter.printBase64Binary(data.getBytes("UTF-8"));
    }

    private String includeFARequestedAttributes(String xmlAuthnRequest, Collection<FARequestedAttribute> requestedAttributes) {
	// include Extensions (AMA FA format)
	StringBuilder extensions = new StringBuilder();

	openFAExtensions(extensions);
	for (FARequestedAttribute attribute : requestedAttributes) {
	    addRequestedAttribute(extensions, attribute.getName(), attribute.isRequired());
	}
	closeFAExtensions(extensions);

	return xmlAuthnRequest.replace("<saml2p:Extensions/>", extensions.toString());
    }

    private void openFAExtensions(StringBuilder element) {
	element.append("<saml2p:Extensions><fa:RequestedAttributes xmlns:fa=\"http://autenticacao.cartaodecidadao.pt/atributos\">");
    }

    private void closeFAExtensions(StringBuilder element) {
	element.append("</fa:RequestedAttributes><fa:FAAALevel xmlns:fa=\"http://autenticacao.cartaodecidadao.pt/atributos\">3</fa:FAAALevel></saml2p:Extensions>");
    }

    private void addRequestedAttribute(StringBuilder element, String name, boolean required) {
	element.append("<fa:RequestedAttribute Name=\"").append(name).append("\" NameFormat=\"urn:oasis:names:tc:SAML:2.0:attrname-format:uri\"")
	       .append(" isRequired=\"").append(Boolean.toString(required)).append("\" />");
    }

    private String marshalAuthnRequest(AuthnRequest authnRequest) throws MarshallingException {
	Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(authnRequest);
	Element authnRequestDOM = marshaller.marshall(authnRequest);
	authnRequestDOM.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:xsd", XMLConstants.W3C_XML_SCHEMA_NS_URI);
	authnRequestDOM.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
	StringWriter requestWriter = new StringWriter();
	XMLHelper.writeNode(authnRequestDOM, requestWriter);
	return requestWriter.toString();
    }

    public AuthnRequest buildAuthnRequest(DateTime issueInstant) {
	AuthnRequest authnRequest = new AuthnRequestBuilder().buildObject();
	authnRequest.setVersion(SAMLVersion.VERSION_20);
	authnRequest.setProtocolBinding(SAMLConstants.SAML2_POST_BINDING_URI);
	authnRequest.setID(getId());
	authnRequest.setIssueInstant(issueInstant);
	authnRequest.setDestination(properties.getDestination());
	authnRequest.setAssertionConsumerServiceURL(properties.getAssertionConsumerServiceURL());
	authnRequest.setProviderName(properties.getProviderName());
	authnRequest.setForceAuthn(Boolean.TRUE);
	authnRequest.setIsPassive(Boolean.FALSE);

	buildIssuer(authnRequest);
	buildExtensions(authnRequest);

	return authnRequest;
    }

    private void buildIssuer(AuthnRequest request) {
	Issuer issuer = new IssuerBuilder().buildObject();
	issuer.setValue(properties.getIssuer());
	request.setIssuer(issuer);
    }

    private void buildExtensions(AuthnRequest request) {
	Extensions extensions = new ExtensionsBuilder().buildObject(SAMLConstants.SAML20P_NS, Extensions.LOCAL_NAME, SAMLConstants.SAML20P_PREFIX);
	request.setExtensions(extensions);
    }

    private String getId() {
	return "_" + UUID.randomUUID().toString();
    }

}