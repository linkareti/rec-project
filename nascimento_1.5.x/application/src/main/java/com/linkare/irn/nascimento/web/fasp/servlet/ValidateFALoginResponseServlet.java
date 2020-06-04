package com.linkare.irn.nascimento.web.fasp.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.irn.nascimento.web.ConfigurationManager;
import com.linkare.irn.nascimento.web.fasp.FALoginResponse;
import com.linkare.irn.nascimento.web.fasp.FAProperties;
import com.linkare.irn.nascimento.web.fasp.FARequestedAttribute;
import com.linkare.irn.nascimento.web.fasp.common.Constant;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ValidateFALoginResponseServlet extends HttpServlet {

    private static final long serialVersionUID = -555424983819574245L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateFALoginResponseServlet.class);

    @Inject
    private ConfigurationManager configurationManager;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	LOGGER.info("Validating FA Login Response");

	String relayStateEncoded = request.getParameter("RelayState");
	String samlResponseEncoded = request.getParameter("SAMLResponse");

	String relayState = new String(Base64.decodeBase64(relayStateEncoded));
	String samlResponseXml = new String(Base64.decodeBase64(samlResponseEncoded));
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("SAML Response: " + samlResponseXml);
	}

	HttpSession session = request.getSession();
	String returnUrl = (String) session.getAttribute(Constant.AUTHN_REQUEST_RETURN_URL_KEY);
	session.removeAttribute(Constant.AUTHN_REQUEST_RETURN_URL_KEY);
	if (relayState.equals(session.getId())) {
	    try {
		FALoginResponse loginResponse = new FALoginResponse(getFAProperties());
		loginResponse.getResult(samlResponseXml);
		LOGGER.info("Status code: " + loginResponse.getStatusCode());
		if (loginResponse.isStatusSuccess()) {
		    List<FARequestedAttribute> attributeList = loginResponse.getAttributeList();
		    session.setAttribute(Constant.REQUESTED_ATTRIBUTES_KEY, getRequestedAttributesMap(attributeList));
		}
	    } catch (Exception e) {
		LOGGER.error("Could not do post", e);
	    }
	    response.sendRedirect(returnUrl);
	} else {
	    LOGGER.error("RelayState does not match session id. Returning to home url.");
	    try {
		response.sendRedirect(configurationManager.getConfiguration().getLogoutLandingUrlForCitizen());
	    } catch (Exception e) {
		LOGGER.error("Could not send redirect", e);
	    }
	}
    }

    private Map<String, String> getRequestedAttributesMap(List<FARequestedAttribute> attributeList) {
	Map<String, String> result = new HashMap<>();
	for (FARequestedAttribute attribute : attributeList) {
	    result.put(attribute.getFriendlyName(), attribute.getValue());
	}
	return result;
    }

    private FAProperties getFAProperties() {
	final FAProperties properties = new FAProperties();
	properties.setIdentityProviderCertificateValidation(String.valueOf(configurationManager.getConfiguration()
											       .getSamlIdentityProviderCertificateValidation()));
	properties.setIdentityProviderKeystoreType(configurationManager.getConfiguration().getSamlIdentityProviderKeystoreType());
	properties.setIdentityProviderKeystoreName(configurationManager.getConfiguration().getSamlIdentityProviderKeystoreName());
	properties.setIdentityProviderKeystorePass(configurationManager.getConfiguration().getSamlIdentityProviderKeystorePass());
	properties.setIdentityProviderCertificateAlias(configurationManager.getConfiguration().getSamlIdentityProviderCertificateAlias());
	return properties;
    }
}