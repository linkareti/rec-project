package com.linkare.rec.am.web.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.linkare.rec.am.service.ExperimentService;
import com.linkare.rec.am.service.ExperimentServiceLocal;
import com.linkare.rec.am.service.LaboratoryService;
import com.linkare.rec.am.service.LaboratoryServiceLocal;
import com.linkare.rec.am.service.LoginDomainService;
import com.linkare.rec.am.service.ReservationService;
import com.linkare.rec.am.service.UserService;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class JndiHelper {

    private static final String USER_SERVICE_JNDI_BINDING = "java:global/rec.am/UserService";

    private static final String LOGIN_DOMAIN_SERVICE_JNDI_BINDING = "java:global/rec.am/LoginDomainService";

    private static final String RESERVATION_SERVICE_JNDI_BINDING = "java:global/rec.am/ReservationService";

    private static final String EXPERIMENT_SERVICE_JNDI_BINDING = "java:global/rec.am/ExperimentService";

    private static final String LABORATORY_SERVICE_JNDI_BINDING = "java:global/rec.am/LaboratoryService";

    private static InitialContext ctx;

    static {
	try {
	    ctx = new InitialContext();
	} catch (NamingException e) {
	    e.printStackTrace();
	}
    }

    private JndiHelper() {
    }

    public static UserService getUserService() throws NamingException {
	return (UserService) ctx.lookup(USER_SERVICE_JNDI_BINDING);
    }

    public static LoginDomainService getLoginDomainService() throws NamingException {
	return (LoginDomainService) ctx.lookup(LOGIN_DOMAIN_SERVICE_JNDI_BINDING);
    }

    public static ReservationService getReservationService() throws NamingException {
	return (ReservationService) ctx.lookup(RESERVATION_SERVICE_JNDI_BINDING);
    }

    public static ExperimentService getExperimentService() throws NamingException {
	return (ExperimentServiceLocal) ctx.lookup(EXPERIMENT_SERVICE_JNDI_BINDING);
    }

    public static LaboratoryService getLaboratoryService() throws NamingException {
	return (LaboratoryServiceLocal) ctx.lookup(LABORATORY_SERVICE_JNDI_BINDING);
    }
}