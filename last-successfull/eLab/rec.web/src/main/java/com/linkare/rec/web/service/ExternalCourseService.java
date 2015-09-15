package com.linkare.rec.web.service;

import com.linkare.rec.web.model.moodle.ExternalCourse;
import com.linkare.rec.web.wsgen.moodle.UserRecord;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public interface ExternalCourseService extends BusinessService<ExternalCourse, String> {

    public UserRecord[] getStudents(final String courseShortName);

    public UserRecord[] getTeachers(final String courseShortName);
}