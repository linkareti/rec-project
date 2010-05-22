package com.linkare.rec.am.service;

import com.linkare.rec.am.model.moodle.ExternalCourse;
import com.linkare.rec.am.wsgen.moodle.UserRecord;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public interface ExternalCourseService extends BusinessService<ExternalCourse, String> {

    public UserRecord[] getStudents(final String courseShortName);

    public UserRecord[] getTeachers(final String courseShortName);
}