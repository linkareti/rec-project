package com.linkare.rec.am.aop;

import static com.linkare.rec.am.web.moodle.MoodleClientHelper.ERROR_ACCESSING_LOGIN_DOMAIN_FACADE_IN_JNDI;
import static com.linkare.rec.am.web.moodle.MoodleClientHelper.ERROR_INVALID_URL_REGISTERING_LOGIN_DOMAINS;
import static com.linkare.rec.am.web.moodle.MoodleClientHelper.EXTERNAL_SYSTEM_CONFIGURATION_PROBLEMS;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.naming.NamingException;
import javax.xml.rpc.ServiceException;

import com.linkare.rec.am.web.ex.AllocationManagerException;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class MoodleClientHelperExceptionHandler extends LoggerExceptionHandler {

    @Override
    public Object execute(Object target, Object methodResult, Throwable throwable) {
	super.execute(target, methodResult, throwable);
	if (throwable instanceof RemoteException) {
	    throw new AllocationManagerException(throwable);
	} else if (throwable instanceof ServiceException) {
	    throw new AllocationManagerException(EXTERNAL_SYSTEM_CONFIGURATION_PROBLEMS, throwable);
	} else if (throwable instanceof NamingException) {
	    throw new AllocationManagerException(ERROR_ACCESSING_LOGIN_DOMAIN_FACADE_IN_JNDI, throwable);
	} else if (throwable instanceof MalformedURLException) {
	    throw new AllocationManagerException(ERROR_INVALID_URL_REGISTERING_LOGIN_DOMAINS, throwable);
	}
	return methodResult;
    }
}