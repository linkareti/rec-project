package com.linkare.irn.nascimento.web.auth;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.linkare.irn.nascimento.model.security.RoleType;
import com.linkare.irn.nascimento.model.security.User;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@Named
@RequestScoped
public class DummyAuthenticator implements Authenticator {

    @Override
    public User authenticate(String username, String password) {
	if (username.equals("admin") && password.equals("admin")) {
	    final User user = new User();
	    user.setUsername(username);
	    user.setDisplayName("Administrador");
	    user.setEmail("pzenida@linkare.com");
	    user.setRoleType(RoleType.ADMIN);
	    return user;
	}
	return null;
    }
}