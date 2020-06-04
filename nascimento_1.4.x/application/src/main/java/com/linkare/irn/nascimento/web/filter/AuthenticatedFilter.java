package com.linkare.irn.nascimento.web.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linkare.irn.nascimento.web.auth.LoginBean;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@WebFilter(filterName = "AuthenticatedFilter", urlPatterns = "*.xhtml", dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD })
public class AuthenticatedFilter implements Filter {

    private static final String CONFIGURATIONS_PAGE = "protected/configurations/configurations.xhtml";

    private static final String BIRTH_REGISTRATIONS_PAGE = "protected/birth_registrations/birth_registrations.xhtml";

    private static final String PUBLIC_BIRTH_REGISTRATION_PAGE = "public/birth_registration/birth_registration.xhtml";

    private String protectedPage = "protected/";

    private String timeoutPage = "view_expired_error.xhtml";

    private String loginPage = "login.xhtml";

    @Inject
    private LoginBean loginBean;

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
		String indexPage = httpServletRequest.getContextPath() + "/" + getHomePage();
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
	return loginBean.isLoggedIn();
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

    public String getHomePage() {
	if (loginBean.getHasAdminRole()) {
	    return CONFIGURATIONS_PAGE;
	} else if (loginBean.getHasCitizenRole()) {
	    return PUBLIC_BIRTH_REGISTRATION_PAGE;
	} else {
	    return BIRTH_REGISTRATIONS_PAGE;
	}
    }
}