package com.linkare.rec.am.web.auth;

import javax.servlet.http.HttpServletRequest;

import com.linkare.rec.am.web.ex.AuthenticationException;
import com.linkare.rec.am.wsgen.moodle.LoginReturn;
import com.linkare.rec.am.wsgen.moodle.MoodleWS;
import com.linkare.rec.am.wsgen.moodle.MoodleWSLocator;
import com.linkare.rec.am.wsgen.moodle.MoodleWSPortType;

public class MoodleLoginProvider extends LoginProvider {

    @Override
    public UserView authenticate(final HttpServletRequest request, String username, String password) throws AuthenticationException {
	MoodleWS service = new MoodleWSLocator();
	try {
	    MoodleWSPortType port = service.getMoodleWSPort();
	    final LoginReturn loginReturn = port.login(username, password);
	    return new UserView(username, "TO-BE-FETCHED", loginReturn);
	} catch (Exception e) {
	    throw new AuthenticationException(e);
	}
    }
}