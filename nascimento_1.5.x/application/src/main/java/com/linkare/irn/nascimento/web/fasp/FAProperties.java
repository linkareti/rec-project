package com.linkare.irn.nascimento.web.fasp;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class FAProperties {

    private String issuer;

    private String assertionConsumerServiceURL;

    private String destination;

    private String providerName;

    private String serviceProviderKeystoreType;

    private String serviceProviderKeystoreName;

    private String serviceProviderKeystorePass;

    private String serviceProviderCertificateAlias;

    private String serviceProviderPrivateKeyPass;

    private String identityProviderKeystoreType;

    private String identityProviderKeystoreName;

    private String identityProviderKeystorePass;

    private String identityProviderCertificateAlias;

    private String identityProviderCertificateValidation;

    public FAProperties() {
	super();
    }

    public String getIssuer() {
	return issuer;
    }

    public void setIssuer(String issuer) {
	this.issuer = issuer;
    }

    public String getAssertionConsumerServiceURL() {
	return assertionConsumerServiceURL;
    }

    public void setAssertionConsumerServiceURL(String assertionConsumerServiceURL) {
	this.assertionConsumerServiceURL = assertionConsumerServiceURL;
    }

    public String getDestination() {
	return destination;
    }

    public void setDestination(String destination) {
	this.destination = destination;
    }

    public String getProviderName() {
	return providerName;
    }

    public void setProviderName(String providerName) {
	this.providerName = providerName;
    }

    public String getServiceProviderKeystoreName() {
	return serviceProviderKeystoreName;
    }

    public void setServiceProviderKeystoreName(String serviceProviderKeystoreName) {
	this.serviceProviderKeystoreName = serviceProviderKeystoreName;
    }

    public String getServiceProviderKeystorePass() {
	return serviceProviderKeystorePass;
    }

    public void setServiceProviderKeystorePass(String serviceProviderKeystorePass) {
	this.serviceProviderKeystorePass = serviceProviderKeystorePass;
    }

    public String getServiceProviderCertificateAlias() {
	return serviceProviderCertificateAlias;
    }

    public void setServiceProviderCertificateAlias(String serviceProviderCertificateAlias) {
	this.serviceProviderCertificateAlias = serviceProviderCertificateAlias;
    }

    public String getServiceProviderPrivateKeyPass() {
	return serviceProviderPrivateKeyPass;
    }

    public void setServiceProviderPrivateKeyPass(String serviceProviderPrivateKeyPass) {
	this.serviceProviderPrivateKeyPass = serviceProviderPrivateKeyPass;
    }

    public String getServiceProviderKeystoreType() {
	return serviceProviderKeystoreType;
    }

    public void setServiceProviderKeystoreType(String serviceProviderKeystoreType) {
	this.serviceProviderKeystoreType = serviceProviderKeystoreType;
    }

    public String getIdentityProviderKeystoreType() {
	return identityProviderKeystoreType;
    }

    public void setIdentityProviderKeystoreType(String identityProviderKeystoreType) {
	this.identityProviderKeystoreType = identityProviderKeystoreType;
    }

    public String getIdentityProviderKeystoreName() {
	return identityProviderKeystoreName;
    }

    public void setIdentityProviderKeystoreName(String identityProviderKeystoreName) {
	this.identityProviderKeystoreName = identityProviderKeystoreName;
    }

    public String getIdentityProviderKeystorePass() {
	return identityProviderKeystorePass;
    }

    public void setIdentityProviderKeystorePass(String identityProviderKeystorePass) {
	this.identityProviderKeystorePass = identityProviderKeystorePass;
    }

    public String getIdentityProviderCertificateAlias() {
	return identityProviderCertificateAlias;
    }

    public void setIdentityProviderCertificateAlias(String identityProviderCertificateAlias) {
	this.identityProviderCertificateAlias = identityProviderCertificateAlias;
    }

    public String getIdentityProviderCertificateValidation() {
	return identityProviderCertificateValidation;
    }

    public void setIdentityProviderCertificateValidation(String identityProviderCertificateValidation) {
	this.identityProviderCertificateValidation = identityProviderCertificateValidation;
    }
}