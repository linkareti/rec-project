package com.linkare.rec.am.web.moodle;

import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.am.web.auth.ExternalUserView;
import com.linkare.rec.am.web.auth.UserView;
import com.linkare.rec.am.web.util.ConstantUtils;
import com.linkare.rec.am.wsgen.moodle.LoginReturn;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class SessionHelper {

    private SessionHelper() {
    }

    public static void setUserView(final UserView userView) {
	JsfUtil.getSession().setAttribute(ConstantUtils.USER_VIEW_SESSION_KEY, userView);
    }

    public static LoginReturn getLoginReturn() {
	final UserView userView = getUserView();
	if (userView.isExternal()) {
	    return ((ExternalUserView) userView).getLoginReturn();
	}
	return null;
    }

    public static String getLoginDomain() {
	final UserView userView = getUserView();
	return userView == null ? null : userView.getDomain();
    }

    public static UserView getUserView() {
	return (UserView) JsfUtil.getSession().getAttribute(ConstantUtils.USER_VIEW_SESSION_KEY);
    }
}