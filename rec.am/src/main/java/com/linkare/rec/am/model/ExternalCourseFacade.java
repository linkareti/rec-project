package com.linkare.rec.am.model;

import java.util.List;

import com.linkare.rec.am.web.moodle.MoodleClientHelper;
import com.linkare.rec.am.wsgen.moodle.CourseRecord;
import com.linkare.rec.am.wsgen.moodle.UserRecord;

public class ExternalCourseFacade extends Facade<CourseRecord> {

    private List<CourseRecord> courseRecords;

    @Override
    public void create(CourseRecord courseRecord) {
	throw new UnsupportedOperationException("external.courses.create.not.available");
    }

    @Override
    public void edit(CourseRecord courseRecord) {
	throw new UnsupportedOperationException("external.courses.edit.not.available");
    }

    @Override
    public void remove(CourseRecord courseRecord) {
	throw new UnsupportedOperationException("external.courses.remove.not.available");
    }

    @Override
    public CourseRecord find(Object id) {
	return MoodleClientHelper.findCourse(id);
    }

    @Override
    public List<CourseRecord> findAll() {
	if (courseRecords == null) {
	    courseRecords = MoodleClientHelper.findCurrentUserCourses();
	}
	return courseRecords;
    }

    @Override
    public List<CourseRecord> findRange(int[] range) {
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