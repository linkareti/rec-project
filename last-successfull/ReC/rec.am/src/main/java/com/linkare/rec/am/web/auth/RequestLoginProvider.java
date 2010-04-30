package com.linkare.rec.am.web.auth;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.linkare.rec.am.web.ex.AuthenticationException;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class RequestLoginProvider extends LoginProvider {

    @Override
    public UserView authenticate(final HttpServletRequest request, final String username, final String password, final String loginDomain)
	    throws AuthenticationException {
	try {
	    request.login(username, password);
	} catch (ServletException e) {
	    throw new AuthenticationException(e);
	}
	return new InternalUserView(username, loginDomain);
    }
}