package com.linkare.rec.am.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * When the session destroyed, SessionListener will do necessary logout operations. Later, at the first request of client, this filter will be fired and
 * redirect the user to the appropriate timeout page if the session is not valid.
 * 
 * @author Joao
 * 
 */
public class SessionTimeoutFilter implements Filter {

    private static Logger logger = Logger.getLogger("SessionTimeoutFilter");

    private String timeoutPage = "timeout.faces";

    private String loginPage = "Login.faces";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

	if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
	    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	    HttpServletResponse httpServletResponse = (HttpServletResponse) response;

	    // is session expire control required for this request AND is session invalid?
	    if (isSessionControlRequiredForThisResource(httpServletRequest) && isSessionInvalid(httpServletRequest)) {
		String timeoutUrl = httpServletRequest.getContextPath() + "/" + getTimeoutPage();
		logger.info("session is invalid! redirecting to timeoutpage : " + timeoutUrl);

		httpServletResponse.sendRedirect(timeoutUrl);
		return;
	    }
	}
	filterChain.doFilter(request, response);
    }

    /**
     * 
     * session shouldn't be checked for some pages. For example: for timeout page.. Since we're redirecting to timeout page from this filter, if we don't
     * disable session control for it, filter will again redirect to it and this will be result with an infinite loop...
     */
    private boolean isSessionControlRequiredForThisResource(HttpServletRequest httpServletRequest) {
	String requestPath = httpServletRequest.getRequestURI();
	logger.info("isSessionControlRequired for:" + requestPath);
	boolean controlRequired = !requestPath.contains(getTimeoutPage()) && !requestPath.contains(getLoginPage());
	logger.info("isSessionControlRequired for:" + requestPath + "? " + controlRequired);

	return controlRequired;
    }

    private boolean isSessionInvalid(HttpServletRequest httpServletRequest) {
	boolean sessionInValid = (httpServletRequest.getRequestedSessionId() != null) && !httpServletRequest.isRequestedSessionIdValid();
	logger.info("isSessionInvalid? " + sessionInValid);

	return sessionInValid;
    }

    public String getTimeoutPage() {
	return timeoutPage;
    }

    public String getLoginPage() {
	return loginPage;
    }

    public void setTimeoutPage(String timeoutPage) {
	this.timeoutPage = timeoutPage;
    }

    @Override
    public void destroy() {
	//	throw new UnsupportedOperationException("Not supported yet.");
    }
}
