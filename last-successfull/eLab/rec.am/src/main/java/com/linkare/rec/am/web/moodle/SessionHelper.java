package com.linkare.rec.am.web.moodle;

import com.linkare.rec.am.web.auth.UserView;
import com.linkare.rec.am.web.util.JsfUtil;
import com.linkare.rec.am.wsgen.moodle.LoginReturn;

public final class SessionHelper {

    private static final SessionHelper INSTANCE = new SessionHelper();

    private SessionHelper() {
    }

    public static LoginReturn getLoginReturn() {
	final LoginReturn loginReturn = JsfUtil.getSession().getAttribute(UserView.USER_VIEW_SESSION_KEY) == null ? null
		: ((UserView) JsfUtil.getSession().getAttribute(UserView.USER_VIEW_SESSION_KEY)).getLoginReturn();
	return loginReturn;
    }

    public static String getUsername() {
	final String username = JsfUtil.getSession().getAttribute(UserView.USER_VIEW_SESSION_KEY) == null ? null
		: ((UserView) JsfUtil.getSession().getAttribute(UserView.USER_VIEW_SESSION_KEY)).getUsername();
	return username;
    }

    public static String getExternalURL() {
	final String domain = JsfUtil.getSession().getAttribute(UserView.USER_VIEW_SESSION_KEY) == null ? null
		: ((UserView) JsfUtil.getSession().getAttribute(UserView.USER_VIEW_SESSION_KEY)).getDomain();
	return domain;
    }

    public static UserView getUserView() {
	return (UserView) JsfUtil.getSession().getAttribute(UserView.USER_VIEW_SESSION_KEY);
    }
}