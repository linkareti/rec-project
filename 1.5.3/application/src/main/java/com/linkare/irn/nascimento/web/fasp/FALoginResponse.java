package com.linkare.irn.nascimento.web.fasp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.opensaml.xml.Configuration;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.io.UnmarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class FALoginResponse extends SAMLBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(FALoginResponse.class);

    private static final String STATUS_SUCCESS = "urn:oasis:names:tc:SAML:2.0:status:Success";

    private String id;

    private String statusCode;

    private String statusMessage;

    private List<FARequestedAttribute> attributeList;

    public FALoginResponse(FAProperties properties) {
	super(properties);
    }

    public String getId() {
	return id;
    }

    public String getStatusCode() {
	return statusCode;
    }

    public String getStatusMessage() {
	return statusMessage;
    }

    public List<FARequestedAttribute> getAttributeList() {
	return attributeList;
    }

    public void getResult(String loginResponseXml) throws Exception {
	// Validate
	if ("true".equalsIgnoreCase(properties.getIdentityProviderCertificateValidation())) {
	    boolean validSignature = new EnvelopedXMLSignature(properties).validateMessageXMLDSIG(loginResponseXml);
	    if (!validSignature) {
		throw new Exception("Invalid FA Login Response Signature");
	    }
	}
	XMLObject loginResponse = unmarshall(loginResponseXml);
	Element dom = loginResponse.getDOM();
	getId(dom);
	getStatusCode(dom);
	getStatusMessage(dom);
	if (isStatusSuccess()) {
	    getAttributeList(dom);
	} else {
	    LOGGER.error(statusCode + ": " + statusMessage);
	}
    }

    private String getId(Element dom) {
	if (id == null) {
	    id = dom.getAttribute("ID");
	}
	return id;
    }

    private List<FARequestedAttribute> getAttributeList(Element dom) {
	if (attributeList == null) {
	    attributeList = new ArrayList<>();
	    NodeList attributeNodes = dom.getElementsByTagName("Attribute");
	    for (int i = 0; i < attributeNodes.getLength(); i++) {
		Node attr = attributeNodes.item(i);
		NamedNodeMap attrAttributes = attr.getAttributes();
		Node attrName = attrAttributes.getNamedItem("Name");
		Node attrStatus = attrAttributes.getNamedItem("d4p1:AttributeStatus");
		if (attrStatus.getNodeValue().equalsIgnoreCase("Available")) {
		    String attrValue = attr.getTextContent().trim();
		    attributeList.add(FARequestedAttribute.create(attrName.getNodeValue(), attrValue));
		}
	    }
	}
	return attributeList;
    }

    private String getStatusCode(Element dom) {
	if (statusCode == null) {
	    Node statusCodeNode = dom.getElementsByTagName("StatusCode").item(0);
	    Node statusCodeValueAttr = statusCodeNode.getAttributes().item(0);
	    statusCode = statusCodeValueAttr.getNodeValue();
	}
	return statusCode;
    }

    private String getStatusMessage(Element dom) {
	if (statusMessage == null) {
	    NodeList statusMessageList = dom.getElementsByTagName("StatusMessage");
	    if (statusMessageList != null) {
		Node statusMessageNode = statusMessageList.item(0);
		if (statusMessageNode != null) {
		    String message = statusMessageNode.getTextContent();
		    statusMessage = message == null ? "" : message;
		}
	    }
	}
	return statusMessage;
    }

    private XMLObject unmarshall(String responseMessage)
	    throws ConfigurationException, ParserConfigurationException, SAXException, IOException, UnmarshallingException {

	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	documentBuilderFactory.setNamespaceAware(true);
	DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
	Document document = docBuilder.parse(new ByteArrayInputStream(responseMessage.trim().getBytes("UTF-8"))); // response
														  // to
														  // DOM
	Element element = document.getDocumentElement(); // the DOM element
	UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
	Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(element);
	return unmarshaller.unmarshall(element); // Response object
    }

    public boolean isStatusSuccess() {
	return STATUS_SUCCESS.equals(statusCode);
    }
}
