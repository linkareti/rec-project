package com.linkare.rec.am.web.auth;

import javax.naming.NamingException;

import com.linkare.commons.jpa.security.User;
import com.linkare.rec.am.service.UserService;
import com.linkare.rec.am.web.ex.AuthenticationException;
import com.linkare.rec.am.web.util.JndiHelper;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class LocalLoginProvider extends LoginProvider {

    private UserService service;

    public LocalLoginProvider() {
	try {
	    this.service = JndiHelper.getUserService();
	} catch (NamingException e) {
	    throw new RuntimeException("error.canot.get.userService", e);
	}
    }

    @Override
    public UserView authenticate(final String username, final String password, final String loginDomain) throws AuthenticationException {
	final User user = service.authenticate(username, password);
	if (user == null) {
	    throw new AuthenticationException();
	}
	return new InternalUserView(username, loginDomain, service.getRoles(user));
    }
}