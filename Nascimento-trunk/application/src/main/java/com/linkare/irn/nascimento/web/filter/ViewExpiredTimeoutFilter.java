package com.linkare.irn.nascimento.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

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

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@WebFilter(filterName = "ViewExpiredTimeoutFilter", urlPatterns = "*.xhtml", dispatcherTypes = { DispatcherType.FORWARD, DispatcherType.REQUEST })
public class ViewExpiredTimeoutFilter implements Filter {

    private String timeoutPage = "view_expired_error.xhtml";

    private String indexPage = "index.xhtml";

    private String publicPage = "public/";

    private String protectedPage = "protected/";

    private String resources = "javax.faces.resource";

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

		// a get should be able to proceed as it is a new (fresh) request!
		if (!httpServletRequest.getMethod().equals("GET")) {

		    final String redirectPage = httpServletRequest.getContextPath() + "/"
			    + (httpServletRequest.getParameter("logout") != null ? getIndexPage() : getTimeoutPage());

		    if (isAJAXRequest(httpServletRequest)) {
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><partial-response><redirect url=\"").append(redirectPage)
			  .append("\"></redirect></partial-response>");
			httpServletResponse.setHeader("Cache-Control", "no-cache");
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.println(sb.toString());
			pw.flush();
			return;
		    }

		    httpServletResponse.sendRedirect(redirectPage);
		    return;
		}
	    }
	}
	filterChain.doFilter(request, response);
    }

    private boolean isAJAXRequest(HttpServletRequest request) {
	boolean check = false;
	String facesRequest = request.getHeader("Faces-Request");
	if (facesRequest != null && facesRequest.equals("partial/ajax")) {
	    check = true;
	}
	return check;
    }

    /**
     * 
     * Session shouldn't be checked for some pages. For example: for timeout page. Since we're redirecting to timeout page from this filter, if we don't disable
     * session control for it, filter will again redirect to it and this will be result with an infinite loop. Only protected and public pages and not the JSF
     * resources should result in a true result from this method.
     * 
     * @param httpServletRequest
     *            the servlet request to check
     * @return true if the session control is required for the request passed in as parameter. It returns false, otherwise.
     */
    private boolean isSessionControlRequiredForThisResource(HttpServletRequest httpServletRequest) {
	String requestPath = httpServletRequest.getRequestURI();
	return (requestPath.contains(getProtectedPage()) || requestPath.contains(getPublicPage())) && !requestPath.contains(resources);
    }

    /**
     * 
     * @param httpServletRequest
     *            the request to check
     * @return true if the session is invalid. It returns false, otherwise.
     */
    private boolean isSessionInvalid(HttpServletRequest httpServletRequest) {
	return (httpServletRequest.getRequestedSessionId() != null) && !httpServletRequest.isRequestedSessionIdValid();
    }

    /**
     * 
     * @return the timeoutPage
     */
    public String getTimeoutPage() {
	return timeoutPage;
    }

    /**
     * 
     * @return the indexPage
     */
    public String getIndexPage() {
	return indexPage;
    }

    /**
     * @return the protectedPage
     */
    public String getProtectedPage() {
	return protectedPage;
    }

    /**
     * @return the publicPage
     */
    public String getPublicPage() {
	return publicPage;
    }

    @Override
    public void destroy() {
    }
}