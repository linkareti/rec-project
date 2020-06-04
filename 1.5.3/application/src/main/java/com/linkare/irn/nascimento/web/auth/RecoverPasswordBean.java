package com.linkare.irn.nascimento.web.auth;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

import com.linkare.irn.nascimento.service.security.UserService;
import com.linkare.irn.nascimento.util.JsfUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@RequestScoped
@Named
public class RecoverPasswordBean implements Serializable {

    private static final long serialVersionUID = -1537039845083491619L;

    @NotNull
    private String username;

    @Inject
    private UserService userService;

    /**
     * @return the username
     */
    public String getUsername() {
	return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
	this.username = username;
    }

    public String createRequest() {
	try {
	    userService.requestPasswordRecovery(username);
	    JsfUtils.addGlobalInfoMessage(getMessage("message.info.recoverPasswordCompleted"));
	    return "reset_password?faces-redirect=true&username=" + username;
	} catch (final EntityNotFoundException e) {
	    JsfUtils.addGlobalErrorMessage(getMessage("message.error.recoverPassword.usernameNotFound"));
	} catch (final RuntimeException e) {
	    JsfUtils.addGlobalErrorMessage(getMessage("message.error.recoverPassword.emailMessageFailed"));
	}
	return null;
    }
}