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
public class ResetPasswordBean implements Serializable {

    private static final long serialVersionUID = -1537039845083491619L;

    @NotNull
    private String username;

    @NotNull
    private String token;

    @NotNull
    private String newPassword;

    @NotNull
    private String confirmNewPassword;

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

    /**
     * @return the token
     */
    public String getToken() {
	return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(String token) {
	this.token = token;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
	return newPassword;
    }

    /**
     * @param newPassword
     *            the newPassword to set
     */
    public void setNewPassword(String newPassword) {
	this.newPassword = newPassword;
    }

    /**
     * @return the confirmNewPassword
     */
    public String getConfirmNewPassword() {
	return confirmNewPassword;
    }

    /**
     * @param confirmNewPassword
     *            the confirmNewPassword to set
     */
    public void setConfirmNewPassword(String confirmNewPassword) {
	this.confirmNewPassword = confirmNewPassword;
    }

    public String resetPassword() {
	try {
	    userService.finishPasswordRecovery(username, token, newPassword, confirmNewPassword);
	    JsfUtils.addGlobalInfoMessage(getMessage("message.info.resetPasswordCompleted"));
	    return "login?faces-redirect=true";
	} catch (final EntityNotFoundException e) {
	    JsfUtils.addGlobalErrorMessage(getMessage("message.error.resetPassword.ticketNotFound"));
	} catch (final RuntimeException e) {
	    JsfUtils.addGlobalErrorMessage(getMessage("message.error.resetPassword.userNotFound"));
	}
	return null;
    }
}