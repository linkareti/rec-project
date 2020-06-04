package com.linkare.irn.nascimento.web.fasp;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.security.KeyStore;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class EnvelopedXMLSignature {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvelopedXMLSignature.class);
    
    private FAProperties properties;

    public EnvelopedXMLSignature(FAProperties properties) {
	super();
	this.properties = properties;
    }

    public String generate(String input, String referenceToSign) throws Exception {

	// Create a DOM XMLSignatureFactory that will be used to generate the
	// enveloped signature
	String providerName = System.getProperty("jsr105Provider", "org.jcp.xml.dsig.internal.dom.XMLDSigRI");
	XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM", (Provider) Class.forName(providerName).newInstance());

        List<Transform> transformList = new ArrayList<Transform>(); 
        TransformParameterSpec transformSpec = null;
        Transform envTransform = fac.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", transformSpec);
        Transform exc14nTransform = fac.newTransform("http://www.w3.org/2001/10/xml-exc-c14n#", transformSpec);
        transformList.add(envTransform);
        transformList.add(exc14nTransform);

        // Create a Reference to the enveloped document (in this case we are
        // signing the whole document, so a URI of "" signifies that) and
        // also specify the SHA1 digest algorithm and the ENVELOPED Transform.
        Reference ref = fac.newReference("#" + referenceToSign, fac.newDigestMethod(DigestMethod.SHA1, null), transformList, null, null);
        // Create the SignedInfo
        SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS,
                (C14NMethodParameterSpec) null),
                fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));

        X509Certificate cert = getServiceProviderCertificate();

        // Create the KeyInfo containing the X509Data.
        KeyInfoFactory kif = fac.getKeyInfoFactory();
        List<Object> x509Content = new ArrayList<Object>();
        x509Content.add(cert.getSubjectX500Principal().getName());
        x509Content.add(cert);
        X509Data xd = kif.newX509Data(x509Content);
        KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

        // Instantiate the document to be signed
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(Thread.currentThread().getContextClassLoader().getResource("saml-schema/saml-schema-protocol-2.0.xsd"));
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setSchema(schema);
        dbf.setIgnoringElementContentWhitespace(true);
        InputSource isxml = new InputSource(new StringReader(input));
        Document doc = dbf.newDocumentBuilder().parse(isxml);
        // Sign AuthnRequest node
        org.w3c.dom.Node xmlSigInsertionPoint = getXmlSignatureInsertLocation(doc.getDocumentElement());
        DOMSignContext dsc = new DOMSignContext(getServiceProviderKeyEntry().getPrivateKey(), doc.getDocumentElement());
        
        // Include xd namespace on signature
        dsc.putNamespacePrefix("http://www.w3.org/2000/09/xmldsig#", "xd");
        dsc.setNextSibling(xmlSigInsertionPoint);
        dsc.setBaseURI(""); // avoids  javax.xml.crypto.URIReferenceException on startUp deploy glassfish v3
        
        // Create the XMLSignature (but don't sign it yet)
        XMLSignature signature = fac.newXMLSignature(si, ki);
        // Marshal, generate (and sign) the enveloped signature
        signature.sign(dsc);
        
        // output the resulting document
        ByteArrayOutputStream os;
        os = new ByteArrayOutputStream();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();
        trans.transform(new DOMSource(doc), new StreamResult(os));
        String outxml = os.toString();
        return outxml;
    }

    /*
     * Determines location to insert the XML <Signature> element into the SAML response.
     */
    private Node getXmlSignatureInsertLocation(org.w3c.dom.Element elem) {
	Node insertLocation = null;
	NodeList nodeList = elem.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:protocol", "Extensions");
	if (nodeList.getLength() != 0) {
	    insertLocation = nodeList.item(nodeList.getLength() - 1);
	}
	return insertLocation;
    }
    
    private X509Certificate getServiceProviderCertificate() throws Exception{
        X509Certificate cert = (X509Certificate) getServiceProviderKeyEntry().getCertificate();
        return cert;
    }
    
    private X509Certificate getIdentityProviderCertificate() throws Exception{
        X509Certificate cert = (X509Certificate) getIdentityProviderKeyEntry().getTrustedCertificate();
        return cert;
    }
    
    public KeyStore.TrustedCertificateEntry getIdentityProviderKeyEntry() throws Exception{
		
        KeyStore ks = KeyStore.getInstance(properties.getIdentityProviderKeystoreType());
        InputStream is = new FileInputStream(properties.getIdentityProviderKeystoreName());
        ks.load(is, properties.getIdentityProviderKeystorePass().toCharArray());
        //aliases
        KeyStore.TrustedCertificateEntry keyEntry = (KeyStore.TrustedCertificateEntry) ks.getEntry(properties.getIdentityProviderCertificateAlias(), null);
        is.close();
        return keyEntry;
    }
    
    private KeyStore.PrivateKeyEntry getServiceProviderKeyEntry() throws Exception{
        KeyStore ks = KeyStore.getInstance(properties.getServiceProviderKeystoreType());

        InputStream is = new FileInputStream(properties.getServiceProviderKeystoreName());
        ks.load(is, properties.getServiceProviderKeystorePass().toCharArray());
        //aliases
        KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(properties.getServiceProviderCertificateAlias(), new KeyStore.PasswordProtection(properties.getServiceProviderPrivateKeyPass().toCharArray()));
        is.close();
        return keyEntry;
    }
    
    public boolean validateMessageXMLDSIG(String xml) throws Exception {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants. W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(Thread.currentThread().getContextClassLoader().getResource("saml-schema/saml-schema-protocol-2.0.xsd"));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setSchema(schema);
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new java.io.ByteArrayInputStream(xml.getBytes("UTF-8"))));
        NodeList nl = document.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        // Find Signature element
        if (nl.getLength() == 0) {
            throw new Exception("GenEnvelopedXMLSignature Cannot find Signature element");
        }
        // Create a DOM XMLSignatureFactory that will be used to unmarshal the
        // document containing the XMLSignature
        String providerName = System.getProperty("jsr105Provider", "org.jcp.xml.dsig.internal.dom.XMLDSigRI");
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM", (Provider) Class.forName(providerName).newInstance());
        // Create a DOMValidateContext and specify a KeyValue KeySelector and document context
        // DOMValidateContext valContext = new DOMValidateContext(new X509KeySelector(), nl.item(0));
        //valida que signature e da AMA e esta estruturalmente valida
        X509Certificate cert = getIdentityProviderCertificate();
        DOMValidateContext valContext = new DOMValidateContext(cert.getPublicKey(), nl.item(0));
        XMLSignature signature = fac.unmarshalXMLSignature(valContext);
        // valContext.setURIDereferencer( new URIResolverImpl());
        // Validate the XMLSignature (generated above)
        boolean coreValidity = signature.validate(valContext);
        boolean refValidation = true;
        // Check core validation status
        if (coreValidity == true) {
            boolean sv = signature.getSignatureValue().validate(valContext);
            if (!sv) {
                LOGGER.warn("EnvelopedXMLSignature Signature nao passou validacao de valor.");
                return sv;
            };
            // check the validation status of each Reference
            @SuppressWarnings("rawtypes")
	    Iterator i = signature.getSignedInfo().getReferences().iterator();
            for (int j = 0; i.hasNext(); j++) {
                refValidation = ((Reference) i.next()).validate(valContext);
                if (!refValidation) {
                    LOGGER.warn("ref[" + j + "] validity status: " + refValidation);
                    return refValidation;
                }
            }
            return refValidation;
        } else {
            LOGGER.warn("EnvelopedXMLSignature Signature nao passou validacao core.");
            return coreValidity;
        }
    }

}
