package com.linkare.rec.am.web.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.linkare.rec.am.web.ex.AuthenticationException;

public abstract class LoginProvider {

    public void login(final HttpServletRequest request, final String username, final String password) throws AuthenticationException {
	UserView userView = authenticate(request, username, password);
	final HttpSession session = request.getSession();
	session.setAttribute(UserView.USER_VIEW_SESSION_KEY, userView);
    }

    public abstract UserView authenticate(final HttpServletRequest request, final String username, final String password) throws AuthenticationException;
}