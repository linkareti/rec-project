package com.linkare.rec.am.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linkare.rec.am.web.util.ConstantUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class AuthenticatedFilter implements Filter {

    private String protectedPage = "protected/";

    private String timeoutPage = "timeout.faces";

    private String loginPage = "LabsStatus.faces";

    private String indexPage = "index.faces";

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
	if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
	    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	    HttpServletResponse httpServletResponse = (HttpServletResponse) response;

	    if (isPageProtected(httpServletRequest)
		    && (isSessionControlRequiredForThisResource(httpServletRequest) && !isUserViewInSession(httpServletRequest))) {
		String loginPage = httpServletRequest.getContextPath() + "/" + getLoginPage();
		httpServletResponse.sendRedirect(loginPage);
		return;
	    } else if (isLoginPage(httpServletRequest) && isUserViewInSession(httpServletRequest)) {
		String indexPage = httpServletRequest.getContextPath() + "/" + getIndexPage();
		httpServletResponse.sendRedirect(indexPage);
		return;
	    }
	}
	filterChain.doFilter(request, response);
    }

    private boolean isPageProtected(HttpServletRequest httpServletRequest) {
	String requestPath = httpServletRequest.getRequestURI();
	return requestPath.contains(getProtectedPage());
    }

    private boolean isLoginPage(HttpServletRequest httpServletRequest) {
	String requestPath = httpServletRequest.getRequestURI();
	return requestPath.contains(getLoginPage());
    }

    private boolean isSessionControlRequiredForThisResource(HttpServletRequest httpServletRequest) {
	String requestPath = httpServletRequest.getRequestURI();
	return !requestPath.contains(getTimeoutPage()) && !requestPath.contains(getLoginPage());
    }

    private boolean isUserViewInSession(HttpServletRequest httpServletRequest) {
	return httpServletRequest.getSession().getAttribute(ConstantUtils.USER_VIEW_SESSION_KEY) != null;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    public String getTimeoutPage() {
	return timeoutPage;
    }

    public String getLoginPage() {
	return loginPage;
    }

    public String getProtectedPage() {
	return protectedPage;
    }

    public String getIndexPage() {
	return indexPage;
    }
}