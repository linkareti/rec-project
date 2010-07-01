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

import javax.naming.NamingException;
import javax.xml.rpc.ServiceException;

import com.linkare.commons.utils.StringUtils;
import com.linkare.rec.am.model.LoginDomain;
import com.linkare.rec.am.model.moodle.ExternalCourse;
import com.linkare.rec.am.model.moodle.ExternalUser;
import com.linkare.rec.am.service.LoginDomainService;
import com.linkare.rec.am.web.ex.AllocationManagerException;
import com.linkare.rec.am.web.util.ConstantUtils;
import com.linkare.rec.am.web.util.JndiHelper;
import com.linkare.rec.am.wsgen.moodle.CourseRecord;
import com.linkare.rec.am.wsgen.moodle.LoginReturn;
import com.linkare.rec.am.wsgen.moodle.MoodleWS;
import com.linkare.rec.am.wsgen.moodle.MoodleWSLocator;
import com.linkare.rec.am.wsgen.moodle.MoodleWSPortType;
import com.linkare.rec.am.wsgen.moodle.UserRecord;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class MoodleClientHelper {

    private static final String SHORTNAME = "shortname";

    public static final String EXTERNAL_SYSTEM_CONFIGURATION_PROBLEMS = "external.system.configuration.problems";

    public static final String ERROR_INVALID_URL_REGISTERING_LOGIN_DOMAINS = "error.invalidURL.registering.login.domains";

    public static final String ERROR_ACCESSING_LOGIN_DOMAIN_FACADE_IN_JNDI = "error.accessing.loginDomainFacade.in.jndi";

    /**
     * This map saves all the moodle client help instances, indexed by the login domains.
     */
    private static Map<String, MoodleClientHelper> instancesMap;

    private final MoodleWSPortType moodleWsPort;

    private static Map<String, MoodleClientHelper> getInstancesMap() {
	if (instancesMap == null) {
	    try {
		final LoginDomainService loginDomainService = JndiHelper.getLoginDomainService();
		registerLoginDomains(loginDomainService.findAll());
	    } catch (NamingException e) {
		throw new AllocationManagerException(ERROR_ACCESSING_LOGIN_DOMAIN_FACADE_IN_JNDI, e);
	    } catch (MalformedURLException e) {
		throw new AllocationManagerException(ERROR_INVALID_URL_REGISTERING_LOGIN_DOMAINS, e);
	    }
	}
	return instancesMap;
    }

    private static void registerLoginDomains(final List<LoginDomain> loginDomains) throws MalformedURLException {
	instancesMap = new Hashtable<String, MoodleClientHelper>();
	for (final LoginDomain loginDomain : loginDomains) {
	    if (!ignoreDomain(loginDomain.getName())) {
		instancesMap.put(loginDomain.getName(), new MoodleClientHelper(new URL(loginDomain.getUrl())));
	    }
	}
    }

    private static boolean ignoreDomain(final String name) {
	return ConstantUtils.INTERNAL_DOMAIN_NAME.equalsIgnoreCase(name);
    }

    private MoodleClientHelper(final URL url) {
	final MoodleWS moodleWS = new MoodleWSLocator();
	try {
	    moodleWsPort = moodleWS.getMoodleWSPort(url);
	} catch (ServiceException e) {
	    throw new AllocationManagerException(EXTERNAL_SYSTEM_CONFIGURATION_PROBLEMS, e);
	}
    }

    public static LoginReturn login(final String username, final String password, final String loginDomain) {
	try {
	    return getInstance(loginDomain).moodleWsPort.login(username, password);
	} catch (RemoteException e) {
	    throw new AllocationManagerException(e);
	}
    }

    public static List<ExternalCourse> getCurrentUserCourses(final String loginDomain, final LoginReturn loginReturn) {
	try {
	    return toExternalCourses(loginDomain, loginReturn, getInstance(loginDomain).moodleWsPort.get_my_courses(getClient(loginReturn),
														    getSessionkey(loginReturn), null, null)
												    .getCourses());
	} catch (RemoteException e) {
	    return Collections.<ExternalCourse> emptyList();
	}
    }

    public static List<ExternalCourse> getUserLecturedCourses(final String username, final String loginDomain, final LoginReturn loginReturn) {
	final List<ExternalCourse> result = new ArrayList<ExternalCourse>();
	final List<ExternalCourse> externalCourses = getCurrentUserCourses(loginDomain, loginReturn);
	for (final ExternalCourse externalCourse : externalCourses) {
	    final UserRecord[] teachers = getTeachers(externalCourse.getShortname(), loginDomain, loginReturn);
	    for (final UserRecord teacher : teachers) {
		if (teacher.getUsername().equals(username)) {
		    result.add(externalCourse);
		    break;
		}
	    }
	}
	return result;
    }

    public static ExternalCourse findCourse(final String id, final String loginDomain, final LoginReturn loginReturn) {
	if (id == null) {
	    return null;
	}
	final List<ExternalCourse> courseRecords = new ArrayList<ExternalCourse>(1);
	try {
	    courseRecords.addAll(toExternalCourses(loginDomain, loginReturn,
						   getInstance(loginDomain).moodleWsPort.get_course(getClient(loginReturn), getSessionkey(loginReturn),
												    id == null ? null : id, SHORTNAME).getCourses()));
	} catch (RemoteException e) {
	    e.printStackTrace();
	}
	return courseRecords.size() == 0 ? null : courseRecords.get(0);
    }

    private static List<ExternalCourse> toExternalCourses(final String loginDomain, final LoginReturn loginReturn, final CourseRecord[] courseRecords) {
	final List<ExternalCourse> result = new ArrayList<ExternalCourse>(courseRecords == null ? 0 : courseRecords.length);
	for (final CourseRecord courseRecord : courseRecords) {
	    result.add(new ExternalCourse(loginDomain, loginReturn, courseRecord));
	}
	return result;
    }

    private static List<ExternalUser> toExternalUsers(final String loginDomain, final UserRecord[] userRecords) {
	final List<ExternalUser> result = new ArrayList<ExternalUser>(userRecords == null ? 0 : userRecords.length);
	for (final UserRecord userRecord : userRecords) {
	    if (StringUtils.isNotBlank(userRecord.getUsername())) {
		result.add(new ExternalUser(loginDomain, userRecord));
	    }
	}
	return result;
    }

    public static List<ExternalUser> getStudents(final ExternalCourse externalCourse) {
	try {
	    return toExternalUsers(externalCourse.getLoginDomain(),
				   getInstance(externalCourse.getLoginDomain()).moodleWsPort.get_students(getClient(externalCourse.getLoginReturn()),
													  getSessionkey(externalCourse.getLoginReturn()),
													  externalCourse.getShortname(), SHORTNAME).getUsers());
	} catch (RemoteException e) {
	    e.printStackTrace();
	    return Collections.<ExternalUser> emptyList();
	}
    }

    public static List<ExternalUser> getTeachers(final ExternalCourse externalCourse) {
	try {
	    return toExternalUsers(externalCourse.getLoginDomain(),
				   getInstance(externalCourse.getLoginDomain()).moodleWsPort.get_teachers(getClient(externalCourse.getLoginReturn()),
													  getSessionkey(externalCourse.getLoginReturn()),
													  externalCourse.getShortname(), SHORTNAME).getUsers());
	} catch (RemoteException e) {
	    e.printStackTrace();
	    return Collections.<ExternalUser> emptyList();
	}
    }

    public static UserRecord[] getStudents(final String courseShortName, final String loginDomain, final LoginReturn loginReturn) {
	try {
	    return getInstance(loginDomain).moodleWsPort.get_students(getClient(loginReturn), getSessionkey(loginReturn), courseShortName, SHORTNAME)
							.getUsers();
	} catch (RemoteException e) {
	    e.printStackTrace();
	    return new UserRecord[] {};
	}
    }

    public static UserRecord[] getTeachers(final String courseShortName, final String loginDomain, final LoginReturn loginReturn) {
	try {
	    return getInstance(loginDomain).moodleWsPort.get_teachers(getClient(loginReturn), getSessionkey(loginReturn), courseShortName, SHORTNAME)
							.getUsers();
	} catch (RemoteException e) {
	    e.printStackTrace();
	    return new UserRecord[] {};
	}
    }

    private static MoodleClientHelper getInstance(final String loginDomain) {
	final MoodleClientHelper instance = getInstancesMap().get(loginDomain);
	if (instance == null) {
	    throw new IllegalArgumentException("no instance was found for " + loginDomain);
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