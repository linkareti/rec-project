package com.linkare.irn.nascimento.web.fasp.servlet;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.irn.nascimento.web.ConfigurationManager;
import com.linkare.irn.nascimento.web.auth.LoginBean;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ValidateFALogoutResponseServlet extends HttpServlet {

    private static final long serialVersionUID = -555424983819574245L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateFALogoutResponseServlet.class);

    @Inject
    private LoginBean loginBean;

    @Inject
    private ConfigurationManager configurationManager;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	LOGGER.info("Validate FA Logout Response");
	try {
	    final boolean isCitizen = loginBean.getHasCitizenRole();
	    final boolean isAuthenticated = loginBean.isLoggedIn();

	    loginBean.setUsername(null);
	    req.getSession().invalidate();
	    if (isCitizen || !isAuthenticated) {
		resp.sendRedirect(configurationManager.getConfiguration().getLogoutLandingUrlForCitizen());
	    } else {
		resp.sendRedirect("login.xhtml");
	    }
	} catch (Exception e) {
	    LOGGER.error("Could not send redirect", e);
	}
    }

}
