package com.linkare.rec.web.auth;

import java.util.List;

import com.linkare.rec.web.model.moodle.ExternalCourse;
import com.linkare.rec.web.ex.AuthenticationException;
import com.linkare.rec.web.moodle.MoodleClientHelper;
import com.linkare.rec.web.wsgen.moodle.LoginReturn;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class MoodleLoginProvider extends LoginProvider {

    @Override
    public UserView authenticate(final String username, final String password, final String loginDomain) throws AuthenticationException {
	try {
	    final LoginReturn loginReturn = MoodleClientHelper.login(username, password, loginDomain);
	    final List<ExternalCourse> courses = MoodleClientHelper.getUserLecturedCourses(username, loginDomain, loginReturn);
	    return new ExternalUserView(username, loginDomain, loginReturn, courses);
	} catch (Exception e) {
	    throw new AuthenticationException(e);
	}
    }
}