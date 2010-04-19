package com.linkare.rec.am.web.auth;

import javax.servlet.http.HttpServletRequest;

import com.linkare.rec.am.model.UserPrincipal;
import com.linkare.rec.am.model.UserPrincipalFacade;
import com.linkare.rec.am.web.ex.AuthenticationException;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class LocalLoginProvider extends LoginProvider {

    private UserPrincipalFacade facade;

    public LocalLoginProvider(final UserPrincipalFacade facade) {
	this.facade = facade;
    }

    @Override
    public UserView authenticate(HttpServletRequest request, String username, String password, String loginDomain) throws AuthenticationException {
	final UserPrincipal userPrincipal = facade.authenticate(username, password);
	if (userPrincipal == null) {
	    throw new AuthenticationException();
	}
	return new InternalUserView(username, loginDomain, userPrincipal.getAllParentRoles());
    }
}