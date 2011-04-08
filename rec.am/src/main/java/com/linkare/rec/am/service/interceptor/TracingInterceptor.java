package com.linkare.rec.am.service.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 
 * 
 * @author Artur Correia - Linkare TI
 * 
 */
public class TracingInterceptor {

    private static final Log LOG = LogFactory.getLog(TracingInterceptor.class);

    @AroundInvoke
    public Object log(InvocationContext ctx) throws Exception {
	//enable tracing only if debug is enabled
	if (LOG.isInfoEnabled()) {
	    LOG.info("*** TracingInterceptor intercepting " + ctx.getMethod().getName());
	    final long start = System.currentTimeMillis();
	    try {
		return ctx.proceed();
	    } finally {
		final long time = System.currentTimeMillis() - start;
		final String method = new StringBuilder(ctx.getTarget().getClass().getName()).append(".").append(ctx.getMethod().getName()).append("()")
											     .toString();
		LOG.info("*** TracingInterceptor invocation of " + method + " took " + time + "ms");
	    }
	} else {
	    return ctx.proceed();
	}

    }

}
