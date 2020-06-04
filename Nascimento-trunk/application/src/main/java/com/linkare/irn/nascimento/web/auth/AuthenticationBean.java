package com.linkare.irn.nascimento.web.auth;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.linkare.irn.nascimento.model.security.RoleType;
import com.linkare.irn.nascimento.model.security.User;
import com.linkare.irn.nascimento.util.JsfUtils;
import com.linkare.irn.nascimento.web.ConfigurationManager;
import com.linkare.irn.nascimento.web.controller.frontoffice.core.ServiceProviderResponseData;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@RequestScoped
@Named
public class AuthenticationBean implements Serializable {

	private static final String CONFIGS_OUTCOME = "configs";

	private static final String BIRTHREGISTRATIONS_OUTCOME = "birthregistrations";

	private static final long serialVersionUID = -1537039845083491619L;

	@Inject
	private Credentials credentials;

	@Inject
	private UserAuthenticator authenticator;

	@Inject
	private LoginBean loginBean;

	@Inject
	private ConfigurationManager configurationManager;

	public String login() {
		final User user = authenticator.authenticate(credentials.getUsername(), credentials.getPassword());
		if (user != null) {

			loginBean.setUsername(user.getUsername());
			loginBean.setDisplayName(user.getDisplayName());
			loginBean.setRoleType(user.getRoleType());
			loginBean.setOrganization(user.getOrganization() == null ? null : user.getOrganization());

			return user.getRoleType() == RoleType.ADMIN ? CONFIGS_OUTCOME : BIRTHREGISTRATIONS_OUTCOME;
		}

		JsfUtils.addGlobalErrorMessage(getMessage("message.error"), getMessage("message.login.authenticationFailure"));

		return null;
	}

	public void login(final ServiceProviderResponseData responseData) {
		if (responseData != null) {
			loginBean.setUsername(responseData.getCivilIdentificationNumber());
			loginBean.setIdentificationCardNumber(responseData.getCivilIdentificationNumber());
			loginBean.setDisplayName(String.format("%s %s", responseData.getGivenName(), responseData.getFamilyName()));
			loginBean.setRoleType(RoleType.CITIZEN);
		}
	}

	public String logout() throws IOException {
		final boolean isCitizen = loginBean.getHasCitizenRole();
		final boolean isAuthenticated = loginBean.isLoggedIn();
		loginBean.setUsername(null);
		// hack: https://java.net/jira/browse/JAVASERVERFACES-3450
		// We cannot invoke this method as it will result in an exception being thrown
		// Caused by: org.jboss.weld.exceptions.IllegalArgumentException: WELD-000085:
		// Cannot destroy null instance of Session bean
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		if (isCitizen || !isAuthenticated) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(configurationManager.getConfiguration().getLogoutLandingUrlForCitizen());
			return null;
		} else {
			return "login";
		}
	}
}