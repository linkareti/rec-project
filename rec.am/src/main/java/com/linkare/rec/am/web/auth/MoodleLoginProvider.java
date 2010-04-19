package com.linkare.rec.am.web.auth;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.linkare.rec.am.model.moodle.ExternalCourse;
import com.linkare.rec.am.web.ex.AuthenticationException;
import com.linkare.rec.am.web.moodle.MoodleClientHelper;
import com.linkare.rec.am.wsgen.moodle.LoginReturn;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class MoodleLoginProvider extends LoginProvider {

    @Override
    public UserView authenticate(final HttpServletRequest request, final String username, final String password, final String loginDomain)
	    throws AuthenticationException {
	try {
	    final LoginReturn loginReturn = MoodleClientHelper.login(username, password, loginDomain);
	    final List<ExternalCourse> courses = MoodleClientHelper.getCurrentUserCourses(loginDomain, loginReturn);
	    return new ExternalUserView(username, loginDomain, loginReturn, courses);
	} catch (Exception e) {
	    throw new AuthenticationException(e);
	}
    }
}