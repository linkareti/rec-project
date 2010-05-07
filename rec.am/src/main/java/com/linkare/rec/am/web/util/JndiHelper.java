package com.linkare.rec.am.web.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.linkare.rec.am.service.LoginDomainService;
import com.linkare.rec.am.service.UserService;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class JndiHelper {

    private static final String USER_FACADE_JNDI_BINDING = "java:global/rec.am/UserService";

    private static final String LOGIN_DOMAIN_FACADE_JNDI_BINDING = "java:global/rec.am/LoginDomainService";

    private static InitialContext ctx;

    static {
	try {
	    ctx = new InitialContext();
	} catch (NamingException e) {
	    e.printStackTrace();
	}
    }

    public static UserService getUserService() throws NamingException {
	return (UserService) ctx.lookup(USER_FACADE_JNDI_BINDING);
    }

    public static LoginDomainService getLoginDomainService() throws NamingException {
	return (LoginDomainService) ctx.lookup(LOGIN_DOMAIN_FACADE_JNDI_BINDING);
    }
}