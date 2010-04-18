package com.linkare.rec.am.model;

import java.util.List;

import com.linkare.rec.am.model.moodle.ExternalCourse;
import com.linkare.rec.am.web.moodle.MoodleClientHelper;
import com.linkare.rec.am.wsgen.moodle.UserRecord;

public class ExternalCourseFacade extends Facade<ExternalCourse> {

    private List<ExternalCourse> courseRecords;

    @Override
    public void create(ExternalCourse courseRecord) {
	throw new UnsupportedOperationException("external.courses.create.not.available");
    }

    @Override
    public void edit(ExternalCourse courseRecord) {
	throw new UnsupportedOperationException("external.courses.edit.not.available");
    }

    @Override
    public void remove(ExternalCourse courseRecord) {
	throw new UnsupportedOperationException("external.courses.remove.not.available");
    }

    @Override
    public ExternalCourse find(Object id) {
	return MoodleClientHelper.findCourse(id);
    }

    @Override
    public List<ExternalCourse> findAll() {
	if (courseRecords == null) {
	    courseRecords = MoodleClientHelper.findCurrentUserCourses();
	}
	return courseRecords;
    }

    @Override
    public List<ExternalCourse> findRange(int[] range) {
	return findAll();
    }

    @Override
    public int count() {
	return findAll().size();
    }

    public UserRecord[] getStudents(final String courseShortName) {
	return MoodleClientHelper.getStudents(courseShortName);
    }

    public UserRecord[] getTeachers(final String courseShortName) {
	return MoodleClientHelper.getTeachers(courseShortName);
    }
}