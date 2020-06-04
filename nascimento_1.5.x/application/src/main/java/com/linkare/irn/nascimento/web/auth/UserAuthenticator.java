package com.linkare.irn.nascimento.web.auth;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.linkare.irn.nascimento.model.security.User;
import com.linkare.irn.nascimento.service.security.UserService;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Named
@RequestScoped
public class UserAuthenticator implements Authenticator {

    @Inject
    private UserService userService;

    @Override
    public User authenticate(String username, String password) {
	return userService.authenticate(username, password);
    }

}