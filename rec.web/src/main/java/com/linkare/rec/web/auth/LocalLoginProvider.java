package com.linkare.rec.web.auth;

import java.util.Collections;
import java.util.List;

import javax.naming.NamingException;

import com.linkare.commons.jpa.security.Role;
import com.linkare.commons.jpa.security.User;
import com.linkare.rec.web.service.UserService;
import com.linkare.rec.web.ex.AuthenticationException;
import com.linkare.rec.web.util.JndiHelper;

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
	User user = service.findByUsername(username);
	if (user == null) {
	    List<Role> emptyList = Collections.emptyList();
	    return new InternalUserView(username, loginDomain, emptyList);
	}
	user = service.authenticate(user, password);
	if (user == null) {
	    throw new AuthenticationException();
	}
	return new InternalUserView(username, loginDomain, service.getRoles(user));
    }
}