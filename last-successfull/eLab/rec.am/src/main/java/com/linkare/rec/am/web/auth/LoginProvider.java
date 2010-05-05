package com.linkare.rec.am.web.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.linkare.rec.am.web.ex.AuthenticationException;
import com.linkare.rec.am.web.util.ConstantUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public abstract class LoginProvider {

    public void login(final HttpServletRequest request, final String username, final String password, final String loginDomain) throws AuthenticationException {
	final HttpSession session = request.getSession();
	UserView userView = authenticate(request, username, password, loginDomain);
	session.setAttribute(ConstantUtils.USER_VIEW_SESSION_KEY, userView);
    }

    public abstract UserView authenticate(final HttpServletRequest request, final String username, final String password, final String loginDomain)
	    throws AuthenticationException;
}