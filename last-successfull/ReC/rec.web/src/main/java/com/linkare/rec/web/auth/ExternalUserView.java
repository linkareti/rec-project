package com.linkare.rec.web.auth;

import java.util.ArrayList;
import java.util.List;

import com.linkare.rec.web.model.moodle.ExternalCourse;
import com.linkare.rec.web.wsgen.moodle.LoginReturn;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class ExternalUserView extends UserView {

    private static final long serialVersionUID = 1L;

    private LoginReturn loginReturn;

    private List<String> associatedCourses;

    public ExternalUserView(final String username, final String domain, final LoginReturn loginReturn, final List<ExternalCourse> associatedCourses) {
	super(username, domain);
	this.loginReturn = loginReturn;
	setAssociatedCourses(associatedCourses);
    }

    @Override
    public boolean isExternal() {
	return true;
    }

    /**
     * @return the associatedCourses
     */
    public List<String> getAssociatedCourses() {
	return associatedCourses = associatedCourses == null ? new ArrayList<String>() : associatedCourses;
    }

    private void addAssociatedCourse(final ExternalCourse externalCourse) {
	getAssociatedCourses().add(externalCourse.getShortname());
    }

    private void setAssociatedCourses(final List<ExternalCourse> associatedCourses) {
	for (final ExternalCourse externalCourse : associatedCourses) {
	    addAssociatedCourse(externalCourse);
	}
    }

    /**
     * @return the loginReturn
     */
    public LoginReturn getLoginReturn() {
	return loginReturn;
    }

    @Override
    public boolean isAdmin() {
	return false;
    }

    /**
     * One external user is a teacher if it is associated with any course
     */
    @Override
    public boolean isTeacher() {
	return !getAssociatedCourses().isEmpty();
    }
}