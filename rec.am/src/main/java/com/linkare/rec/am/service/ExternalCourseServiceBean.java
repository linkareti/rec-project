package com.linkare.rec.am.service;

import java.util.List;

import com.linkare.rec.am.model.moodle.ExternalCourse;
import com.linkare.rec.am.web.moodle.MoodleClientHelper;
import com.linkare.rec.am.web.moodle.SessionHelper;
import com.linkare.rec.am.wsgen.moodle.UserRecord;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class ExternalCourseServiceBean extends BusinessServiceBean<ExternalCourse, String> implements ExternalCourseService {

    private List<ExternalCourse> courseRecords;

    @Override
    public void create(final ExternalCourse courseRecord) {
	throw new UnsupportedOperationException("external.courses.create.not.available");
    }

    @Override
    public ExternalCourse edit(final ExternalCourse courseRecord) {
	throw new UnsupportedOperationException("external.courses.edit.not.available");
    }

    @Override
    public void remove(final ExternalCourse courseRecord) {
	throw new UnsupportedOperationException("external.courses.remove.not.available");
    }

    @Override
    public ExternalCourse find(final String id) {
	return MoodleClientHelper.findCourse(id, SessionHelper.getLoginDomain(), SessionHelper.getLoginReturn());
    }

    @Override
    public List<ExternalCourse> findAll() {
	if (courseRecords == null) {
	    courseRecords = MoodleClientHelper.getCurrentUserCourses(SessionHelper.getLoginDomain(), SessionHelper.getLoginReturn());
	}
	return courseRecords;
    }

    @Override
    public List<ExternalCourse> findRange(final int[] range) {
	return findAll();
    }

    @Override
    public int count() {
	return findAll().size();
    }

    @Override
    public UserRecord[] getStudents(final String courseShortName) {
	return MoodleClientHelper.getStudents(courseShortName, SessionHelper.getLoginDomain(), SessionHelper.getLoginReturn());
    }

    @Override
    public UserRecord[] getTeachers(final String courseShortName) {
	return MoodleClientHelper.getTeachers(courseShortName, SessionHelper.getLoginDomain(), SessionHelper.getLoginReturn());
    }
}