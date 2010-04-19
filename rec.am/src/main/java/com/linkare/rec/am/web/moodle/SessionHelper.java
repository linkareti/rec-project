package com.linkare.rec.am.web.moodle;

import com.linkare.rec.am.web.auth.ExternalUserView;
import com.linkare.rec.am.web.auth.UserView;
import com.linkare.rec.am.web.util.JsfUtil;
import com.linkare.rec.am.wsgen.moodle.LoginReturn;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class SessionHelper {

    public static final String USER_VIEW_SESSION_KEY = "userView";

    private static final SessionHelper INSTANCE = new SessionHelper();

    private SessionHelper() {
    }

    public static LoginReturn getLoginReturn() {
	final UserView userView = getUserView();
	if (userView.isExternal()) {
	    return ((ExternalUserView) userView).getLoginReturn();
	}
	return null;
    }

    public static String getUsername() {
	final UserView userView = getUserView();
	return userView == null ? null : userView.getUsername();
    }

    public static String getLoginDomain() {
	final UserView userView = getUserView();
	return userView == null ? null : userView.getDomain();
    }

    public static UserView getUserView() {
	return (UserView) JsfUtil.getSession().getAttribute(SessionHelper.USER_VIEW_SESSION_KEY);
    }
}