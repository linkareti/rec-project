package com.linkare.rec.am.web.moodle;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import com.linkare.rec.am.model.LoginDomain;
import com.linkare.rec.am.model.moodle.ExternalCourse;
import com.linkare.rec.am.wsgen.moodle.CourseRecord;
import com.linkare.rec.am.wsgen.moodle.LoginReturn;
import com.linkare.rec.am.wsgen.moodle.MoodleWS;
import com.linkare.rec.am.wsgen.moodle.MoodleWSLocator;
import com.linkare.rec.am.wsgen.moodle.MoodleWSPortType;
import com.linkare.rec.am.wsgen.moodle.UserRecord;

public final class MoodleClientHelper {

    /**
     * This map saves all the moodle client help instances, indexed by the login domains.
     */
    private static Map<String, MoodleClientHelper> instancesMap = new Hashtable<String, MoodleClientHelper>();

    private final MoodleWSPortType MOODLEWS_PORT;

    public static void registerLoginDomains(final List<LoginDomain> loginDomains) throws MalformedURLException {
	for (final LoginDomain loginDomain : loginDomains) {
	    if (!ignoreDomain(loginDomain.getName())) {
		instancesMap.put(loginDomain.getName(), new MoodleClientHelper(new URL(loginDomain.getUrl())));
	    }
	}
    }

    private static boolean ignoreDomain(final String name) {
	return "internal".equalsIgnoreCase(name);
    }

    private MoodleClientHelper(final URL url) {
	final MoodleWS moodleWS = new MoodleWSLocator();
	try {
	    MOODLEWS_PORT = moodleWS.getMoodleWSPort(url);
	} catch (ServiceException e) {
	    throw new RuntimeException("external.system.configuration.problems");
	}
    }

    public static LoginReturn login(final String username, final String password, final String loginDomain) {
	try {
	    return getInstance(loginDomain).MOODLEWS_PORT.login(username, password);
	} catch (RemoteException e) {
	    throw new RuntimeException(e);
	}
    }

    public static List<ExternalCourse> getCurrentUserCourses(final String loginDomain, final LoginReturn loginReturn) {
	try {
	    return toExternalCourses(getInstance(loginDomain).MOODLEWS_PORT.get_my_courses(getClient(loginReturn), getSessionkey(loginReturn), null, null)
									   .getCourses());
	} catch (RemoteException e) {
	    return Collections.<ExternalCourse> emptyList();
	}
    }

    public static ExternalCourse findCourse(final String id, final String loginDomain, final LoginReturn loginReturn) {
	final List<ExternalCourse> courseRecords = new ArrayList<ExternalCourse>(1);
	try {
	    courseRecords.addAll(toExternalCourses(getInstance(loginDomain).MOODLEWS_PORT.get_course(getClient(loginReturn), getSessionkey(loginReturn),
												     id == null ? null : id, "shortname").getCourses()));
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

    public static UserRecord[] getStudents(final String courseShortName, final String loginDomain, final LoginReturn loginReturn) {
	try {
	    return getInstance(loginDomain).MOODLEWS_PORT.get_students(getClient(loginReturn), getSessionkey(loginReturn), courseShortName, "shortname")
							 .getUsers();
	} catch (RemoteException e) {
	    e.printStackTrace();
	    return new UserRecord[] {};
	}
    }

    public static UserRecord[] getTeachers(final String courseShortName, final String loginDomain, final LoginReturn loginReturn) {
	try {
	    return getInstance(loginDomain).MOODLEWS_PORT.get_teachers(getClient(loginReturn), getSessionkey(loginReturn), courseShortName, "shortname")
							 .getUsers();
	} catch (RemoteException e) {
	    e.printStackTrace();
	    return new UserRecord[] {};
	}
    }

    private static MoodleClientHelper getInstance(final String loginDomain) {
	final MoodleClientHelper instance = instancesMap.get(loginDomain);
	if (instance == null) {
	    throw new NullPointerException("no instance was found for " + loginDomain);
	}
	return instance;
    }

    private static BigInteger getClient(final LoginReturn loginReturn) {
	return loginReturn == null ? null : loginReturn.getClient();
    }

    private static String getSessionkey(final LoginReturn loginReturn) {
	return loginReturn == null ? null : loginReturn.getSessionkey();
    }
}