package com.linkare.rec.am.web.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.linkare.rec.am.web.ex.AuthenticationException;

public abstract class LoginProvider<T> {

    public T login(final HttpServletRequest request, final String username, final String password) throws AuthenticationException {
	T authenticateResult = authenticate(request, username, password);
	final HttpSession session = request.getSession();
	if (authenticateResult != null) {
	    session.setAttribute(Login.AUTHENTICATE_RESULT_SESSION_KEY, authenticateResult);
	}
	session.setAttribute(Login.USERNAME_SESSION_KEY, username);
	return authenticateResult;
    }

    public abstract T authenticate(final HttpServletRequest request, final String username, final String password) throws AuthenticationException;
}