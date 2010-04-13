package com.linkare.rec.am.web.moodle;

import com.linkare.rec.am.web.auth.Login;
import com.linkare.rec.am.web.util.JsfUtil;
import com.linkare.rec.am.wsgen.moodle.LoginReturn;

public final class SessionHelper {

    private static final SessionHelper INSTANCE = new SessionHelper();

    private SessionHelper() {
    }

    public static LoginReturn getLoginReturn() {
	final LoginReturn loginReturn = (JsfUtil.getSession().getAttribute(Login.AUTHENTICATE_RESULT_SESSION_KEY) == null ? null
		: (LoginReturn) JsfUtil.getSession().getAttribute(Login.AUTHENTICATE_RESULT_SESSION_KEY));
	return loginReturn;
    }

    public static String getUsername() {
	final String username = (JsfUtil.getSession().getAttribute(Login.USERNAME_SESSION_KEY) == null ? null
		: JsfUtil.getSession().getAttribute(Login.USERNAME_SESSION_KEY)).toString();
	return username;
    }
}