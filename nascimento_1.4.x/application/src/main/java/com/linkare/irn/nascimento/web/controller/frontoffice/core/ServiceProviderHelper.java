package com.linkare.irn.nascimento.web.controller.frontoffice.core;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.opensaml.saml2.core.AuthnRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.irn.nascimento.model.core.BirthRegistration;
import com.linkare.irn.nascimento.model.geographic.Country;
import com.linkare.irn.nascimento.model.geographic.Parish;
import com.linkare.irn.nascimento.model.identification.Address;
import com.linkare.irn.nascimento.model.identification.AddressType;
import com.linkare.irn.nascimento.model.identification.IdentificationCardType;
import com.linkare.irn.nascimento.model.identification.Parent;
import com.linkare.irn.nascimento.util.ApplicationMessageUtils;
import com.linkare.irn.nascimento.util.JsfUtils;
import com.linkare.irn.nascimento.web.fasp.FALoginRequest;
import com.linkare.irn.nascimento.web.fasp.FAProperties;
import com.linkare.irn.nascimento.web.fasp.FARequestedAttribute;
import com.linkare.irn.nascimento.web.fasp.common.Constant;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ServiceProviderHelper implements Serializable, IServiceProviderHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProviderHelper.class);

	private static final long serialVersionUID = -1693593160859559888L;

	private static final int FA_RELAY_STATE_MAX_CHARS = 80;

	private BirthRegistrationController controller;

	private boolean authnRequestPrepared;

	private String idpUrl;

	private String encodedAuthnRequest;

	private String returnUrl;

	private String encodedRelayState;

	private ServiceProviderResponseData responseData;

	private boolean dummyMode;

	public ServiceProviderHelper(final BirthRegistrationController controller) {
		this.controller = controller;
		final BirthRegistration birthRegistration = controller.getBirthRegistration();

		final HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(false);
		this.dummyMode = Boolean.TRUE == controller.getConfigurationManager().getConfiguration().getDummyMode();

		@SuppressWarnings("unchecked")
		final Map<String, String> requestedAttributesMap = (Map<String, String>) session
				.getAttribute(Constant.REQUESTED_ATTRIBUTES_KEY);

		authnRequestPrepared = false;
		returnUrl = String.format("%s/public/birth_registration/birth_registration.xhtml%s",
				controller.getConfigurationManager().getConfiguration().getApplicationBaseUrl(),
				(StringUtils.isNotBlank(controller.getUuid()) ? "?uuid=" + controller.getUuid() : ""));

		extractDataFromRequest(controller, birthRegistration, session, requestedAttributesMap);

		if (responseData != null) {
			buildFromResponseData(controller, birthRegistration);
		}
	}

	/**
	 * @param controller
	 * @param birthRegistration
	 * @param session
	 * @param requestedAttributesMap
	 */
	private void extractDataFromRequest(final BirthRegistrationController controller,
			final BirthRegistration birthRegistration, final HttpSession session,
			final Map<String, String> requestedAttributesMap) {
		if (requestedAttributesMap != null) {
			responseData = new ServiceProviderResponseData(requestedAttributesMap);
			// authentication goes here
			controller.getAuthenticationBean().login(responseData);

			// session.removeAttribute(Constant.REQUESTED_ATTRIBUTES_KEY);
		}
	}

	private void buildFromResponseData(final BirthRegistrationController controller,
			final BirthRegistration birthRegistration) {
		controller.setIdentificationCardNumber(responseData.getCivilIdentificationNumber());
		if (!birthRegistration.isPersistent()) {
			initFirstParentFromResponseData(birthRegistration.getFirstParent());
		}
	}

	private Map<String, String> initializeDummyRequestedAttributesMap(final BirthRegistration birthRegistration) {
		final Map<String, String> result = new HashMap<>();
		result.put(FARequestedAttribute.NOME_PROPRIO, "NOME1 NOME2");
		result.put(FARequestedAttribute.NOME_APELIDO, "APELIDO1 APELIDO2");

		result.put(FARequestedAttribute.NIC, UUID.randomUUID().toString());
		if (birthRegistration.isPersistent() && birthRegistration.getSecondParent() != null) {
			result.put(FARequestedAttribute.NIC, birthRegistration.getSecondParent().getIdentificationCardNumber());
		}

		result.put(FARequestedAttribute.DATA_NASCIMENTO, "01-01-1980");

		result.put(FARequestedAttribute.NOME_PROPRIO_PAI, "NOME PAI");
		result.put(FARequestedAttribute.NOME_APELIDO_PAI, "APELIDO2");

		result.put(FARequestedAttribute.NOME_PROPRIO_MAE, "NOME MAE");
		result.put(FARequestedAttribute.NOME_APELIDO_MAE, "APELIDO1");

		result.put(FARequestedAttribute.NACIONALIDADE, "PRT");

		result.put(FARequestedAttribute.CODIGO_POSTAL4, "1000");
		result.put(FARequestedAttribute.CODIGO_POSTAL3, "100");

		result.put(FARequestedAttribute.LOCALIDADE, "LOCALIDADE");

		result.put(FARequestedAttribute.TIPO_DE_VIA, "RUA");
		result.put(FARequestedAttribute.DESIGNACAO_DA_VIA, "ESPETACULAR");
		result.put(FARequestedAttribute.NUMERO_PORTA, "1");
		result.put(FARequestedAttribute.ANDAR, "R/C");
		result.put(FARequestedAttribute.LADO, "FRENTE");

		result.put(FARequestedAttribute.FREGUESIA, "FANHÕES");

		return result;
	}

	void initFirstParentFromResponseData(final Parent firstParent) {
		firstParent.setGivenName(responseData.getGivenName());
		firstParent.setFamilyName(responseData.getFamilyName());
		firstParent.setIdentificationCardNumber(responseData.getCivilIdentificationNumber());
		firstParent.setIdentificationCardType(IdentificationCardType.CITIZEN_CARD);
		try {
			firstParent.setDateOfBirth(new SimpleDateFormat("dd-MM-yyyy").parse(responseData.getDateOfBirth()));
		} catch (ParseException e) {
		}

		// Parents
		firstParent.setFatherName(buildString(responseData.getFatherGivenName(), responseData.getFatherFamilyName()));
		firstParent.setMotherName(buildString(responseData.getMotherGivenName(), responseData.getMotherFamilyName()));

		final Country country = controller.getCountryService().findByCode(responseData.getNationality());
		firstParent.setNationality(country == null ? controller.getApplicationFactory().getPortugal() : country);

		initFirstParentAddressFromResponseData(firstParent);
	}

	void initFirstParentAddressFromResponseData(final Parent firstParent) {
		final Address address = new Address();
		address.setAddressType(AddressType.PORTUGUESE);
		address.setZipCode(buildString(responseData.getZipCode4(), responseData.getZipCode3()));

		address.setLocation(responseData.getLocation());
		address.setCountry(controller.getApplicationFactory().getPortugal());
		address.setStreet(buildStreet());

		final Parish parish = controller.getParishService().findByName(responseData.getParish());
		address.setParish(parish);
		firstParent.setAddress(address);
	}

	/**
	 * @return
	 */
	private String buildStreet() {
		return buildString(responseData.getStreetType(), responseData.getStreetName(), responseData.getBuildingType(),
				responseData.getDoorNumber(), responseData.getFloor(), responseData.getSide());
	}

	/**
	 * @param requestedAttributesMap
	 * @return
	 */
	private String buildString(final String... attributes) {
		final StringBuilder result = new StringBuilder();
		for (final String attribute : attributes) {
			result.append(StringUtils.isBlank(attribute) ? "" : StringUtils.defaultString(attribute) + " ");
		}
		return result.toString().trim();
	}

	public String prepareAuthnRequest() {
		LOGGER.info("Preparing AuthnRequest");

		try {
			final FAProperties properties = getFAProperties();
			final FALoginRequest faServiceProvider = new FALoginRequest(properties);

			DateTime instant = new DateTime();
			AuthnRequest authnRequest = faServiceProvider.buildAuthnRequest(instant);
			Collection<FARequestedAttribute> requestedAttributes = getRequestedAttributes();

			final HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(true);
			session.setAttribute(Constant.AUTHN_REQUEST_RETURN_URL_KEY, returnUrl);

			encodedAuthnRequest = faServiceProvider.encodeAuthnRequest(authnRequest, requestedAttributes);
			encodedRelayState = faServiceProvider.base64Encode(session.getId());

			if (encodedRelayState.length() > FA_RELAY_STATE_MAX_CHARS) {
				LOGGER.error("Relay state may not be preserved because exceeds the size limit.");
			}
			idpUrl = properties.getDestination();

			authnRequestPrepared = true;
		} catch (Exception e) {
			authnRequestPrepared = false;
			LOGGER.error(e.getMessage(), e);
			JsfUtils.addGlobalErrorMessage(
					ApplicationMessageUtils.getMessage("message.error.connectionToIdentityProviderFailed"));
		}
		return null;
	}

	private Collection<FARequestedAttribute> getRequestedAttributes() {
		Collection<FARequestedAttribute> reqAttrs = new ArrayList<FARequestedAttribute>();

		FARequestedAttribute documentType = FARequestedAttribute.create(FARequestedAttribute.TIPO_DOCUMENTO, true);
		LOGGER.info("DOCUMENT TYPE FIELD: " + documentType.getFriendlyName() + " WITH VALUE OF " + documentType.getValue());
		//reqAttrs.add(documentType);
		// reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.NUMERO_DE_CONTROLO,
		// true));
		
		FARequestedAttribute noDocument = FARequestedAttribute.create(FARequestedAttribute.NO_DOCUMENTO, true);
		LOGGER.info("NO DOCUMENT FIELD: " + noDocument.getFriendlyName() + " WITH VALUE OF " + noDocument.getValue());
		//reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.NO_DOCUMENTO, true));
		
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.NIC, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.NOME_PROPRIO, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.NOME_APELIDO, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.DATA_NASCIMENTO, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.NACIONALIDADE, true));

		// Parents
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.NOME_PROPRIO_PAI, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.NOME_APELIDO_PAI, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.NOME_PROPRIO_MAE, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.NOME_APELIDO_MAE, true));

		// Address
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.TIPO_DE_VIA, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.DESIGNACAO_DA_VIA, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.TIPO_EDIFICIO, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.NUMERO_PORTA, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.ANDAR, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.LADO, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.CODIGO_POSTAL4, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.CODIGO_POSTAL3, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.LOCALIDADE, true));
		reqAttrs.add(FARequestedAttribute.create(FARequestedAttribute.FREGUESIA, true));
		return reqAttrs;
	}

	private FAProperties getFAProperties() {
		FAProperties properties = new FAProperties();
		properties.setIssuer(controller.getConfigurationManager().getConfiguration().getSamlAuthnRequestIssuer());
		properties.setProviderName(
				controller.getConfigurationManager().getConfiguration().getSamlAuthnRequestProviderName());
		properties.setDestination(
				controller.getConfigurationManager().getConfiguration().getSamlAuthnRequestDestination());
		properties.setAssertionConsumerServiceURL(controller.getConfigurationManager().getConfiguration()
				.getSamlAuthnRequestAssertionConsumerServiceURL());

		properties.setServiceProviderKeystoreType(
				controller.getConfigurationManager().getConfiguration().getSamlServiceProviderKeystoreType());
		properties.setServiceProviderKeystoreName(
				controller.getConfigurationManager().getConfiguration().getSamlServiceProviderKeystoreName());
		properties.setServiceProviderKeystorePass(
				controller.getConfigurationManager().getConfiguration().getSamlServiceProviderKeystorePass());
		properties.setServiceProviderCertificateAlias(
				controller.getConfigurationManager().getConfiguration().getSamlServiceProviderCertificateAlias());
		properties.setServiceProviderPrivateKeyPass(
				controller.getConfigurationManager().getConfiguration().getSamlServiceProviderPrivateKeyPass());
		return properties;
	}

	public String getIdpUrl() {
		return idpUrl;
	}

	public boolean isAuthnRequestPrepared() {
		return authnRequestPrepared;
	}

	public String getEncodedAuthnRequest() {
		return encodedAuthnRequest;
	}

	public String getEncodedRelayState() {
		return encodedRelayState;
	}

	@Override
	public boolean isDummyMode() {
		return dummyMode;
	}

	/**
	 * @return the responseData
	 */
	public ServiceProviderResponseData getResponseData() {
		return responseData;
	}

	public void dummyInit() {
		final BirthRegistration birthRegistration = controller.getBirthRegistration();
		final HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(false);

		final Map<String, String> requestedAttributesMap = initializeDummyRequestedAttributesMap(birthRegistration);
		session.setAttribute(Constant.REQUESTED_ATTRIBUTES_KEY, requestedAttributesMap);
		extractDataFromRequest(controller, birthRegistration, session, requestedAttributesMap);

		if (responseData != null) {
			buildFromResponseData(controller, birthRegistration);
		}
	}
}