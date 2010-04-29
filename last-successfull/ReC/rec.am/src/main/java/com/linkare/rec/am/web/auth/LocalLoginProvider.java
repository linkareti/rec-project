package com.linkare.rec.am.web.auth;

import javax.servlet.http.HttpServletRequest;

import com.linkare.commons.jpa.security.User;
import com.linkare.rec.am.model.UserFacade;
import com.linkare.rec.am.web.ex.AuthenticationException;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class LocalLoginProvider extends LoginProvider {

    private UserFacade facade;

    public LocalLoginProvider(final UserFacade facade) {
	this.facade = facade;
    }

    @Override
    public UserView authenticate(HttpServletRequest request, String username, String password, String loginDomain) throws AuthenticationException {
	final User user = facade.authenticate(username, password);
	if (user == null) {
	    throw new AuthenticationException();
	}
	return new InternalUserView(username, loginDomain, user.getAllParentRoles());
    }
}