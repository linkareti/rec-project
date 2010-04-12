package com.linkare.rec.am.web.auth;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.linkare.rec.am.web.ex.AuthenticationException;

public class RequestLoginProvider extends LoginProvider<Object> {

    @Override
    public Object authenticate(final HttpServletRequest request, String username, String password) throws AuthenticationException {
	try {
	    request.login(username, password);
	} catch (ServletException e) {
	    throw new AuthenticationException(e);
	}
	// must return something
	return null;
    }
}