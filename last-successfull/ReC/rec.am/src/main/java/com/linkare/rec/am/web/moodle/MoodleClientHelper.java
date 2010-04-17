package com.linkare.rec.am.web.moodle;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.rpc.ServiceException;

import com.linkare.rec.am.model.moodle.ExternalCourse;
import com.linkare.rec.am.wsgen.moodle.CourseRecord;
import com.linkare.rec.am.wsgen.moodle.LoginReturn;
import com.linkare.rec.am.wsgen.moodle.MoodleWS;
import com.linkare.rec.am.wsgen.moodle.MoodleWSLocator;
import com.linkare.rec.am.wsgen.moodle.MoodleWSPortType;
import com.linkare.rec.am.wsgen.moodle.UserRecord;

public final class MoodleClientHelper {

    private static final MoodleWSPortType MOODLEWS_PORT;

    static {
	try {
	    final MoodleWS moodleWS = new MoodleWSLocator();
	    MOODLEWS_PORT = moodleWS.getMoodleWSPort();
	} catch (ServiceException e) {
	    throw new RuntimeException("external.system.configuration.problems");
	}
    }

    private static final MoodleClientHelper INSTANCE = new MoodleClientHelper();

    private MoodleClientHelper() {
    }

    public static ExternalCourse findCourse(final Object id) {
	final List<ExternalCourse> courseRecords = new ArrayList<ExternalCourse>(1);
	try {
	    courseRecords.addAll(toExternalCourses(MOODLEWS_PORT.get_course(getClient(), getSessionkey(), id == null ? null : id.toString(), "shortname")
								.getCourses()));
	} catch (RemoteException e) {
	    e.printStackTrace();
	}
	return courseRecords.size() == 0 ? null : courseRecords.get(0);
    }

    private static List<ExternalCourse> toExternalCourses(final CourseRecord[] courseRecords) {
	final List<ExternalCourse> result = new ArrayList<ExternalCourse>(courseRecords == null ? 0 : courseRecords.length);
	for (final CourseRecord courseRecord : courseRecords) {
	    result.add(new ExternalCourse(courseRecord));
	}
	return result;
    }

    private static List<ExternalCourse> toExternalCourses(final List<CourseRecord> courseRecords) {
	final List<ExternalCourse> result = new ArrayList<ExternalCourse>(courseRecords == null ? 0 : courseRecords.size());
	for (final CourseRecord courseRecord : courseRecords) {
	    result.add(new ExternalCourse(courseRecord));
	}
	return result;
    }

    public static List<ExternalCourse> findCurrentUserCourses() {
	final String username = SessionHelper.getUsername();
	final List<CourseRecord> result = new ArrayList<CourseRecord>();
	if (username != null) {
	    try {
		final CourseRecord[] courses = MOODLEWS_PORT.get_courses(getClient(), getSessionkey(), new String[] {}, null).getCourses();
		for (final CourseRecord courseRecord : courses) {
		    final String id = courseRecord.getShortname();
		    UserRecord[] teachers = MOODLEWS_PORT.get_teachers(getClient(), getSessionkey(), id, "shortname").getUsers();
		    for (final UserRecord userRecord : teachers) {
			if (username.equalsIgnoreCase(userRecord.getUsername())) {
			    result.add(courseRecord);
			    break;
			}
		    }
		}
	    } catch (RemoteException e) {
		return Collections.<ExternalCourse> emptyList();
	    }
	}
	return toExternalCourses(result);
    }

    public static UserRecord[] getStudents(final String courseShortName) {
	try {
	    System.out.println("\n\n\n\nLOOKING FOR STUDENTS IN COURSE " + courseShortName);
	    final UserRecord[] result = MOODLEWS_PORT.get_students(getClient(), getSessionkey(), courseShortName, "shortname").getUsers();
	    for (final UserRecord userRecord : result) {
		System.out.println("----------------------> " + userRecord.getFirstname());
	    }
	    System.out.println("\n\n\n\n\n");
	    return result;
	} catch (RemoteException e) {
	    e.printStackTrace();
	    return new UserRecord[] {};
	}
    }

    public static UserRecord[] getTeachers(final String courseShortName) {
	try {
	    return MOODLEWS_PORT.get_teachers(getClient(), getSessionkey(), courseShortName, "shortname").getUsers();
	} catch (RemoteException e) {
	    e.printStackTrace();
	    return new UserRecord[] {};
	}
    }

    private static BigInteger getClient() {
	final LoginReturn loginReturn = SessionHelper.getLoginReturn();
	return loginReturn == null ? null : loginReturn.getClient();
    }

    private static String getSessionkey() {
	final LoginReturn loginReturn = SessionHelper.getLoginReturn();
	return loginReturn == null ? null : loginReturn.getSessionkey();
    }
}