package com.linkare.rec.am.web.auth;

import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.linkare.rec.am.service.ReservationService;
import com.linkare.rec.am.web.ex.AuthenticationException;
import com.linkare.rec.am.web.util.ConstantUtils;
import com.linkare.rec.am.web.util.JndiHelper;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public abstract class LoginProvider {

    public void login(final HttpServletRequest request, final String username, final String password, final String loginDomain) throws AuthenticationException {
	final HttpSession session = request.getSession();
	UserView userView = authenticate(username, password, loginDomain);
	try {
	    final ReservationService reservationService = JndiHelper.getReservationService();
	    userView.setReservations(reservationService.findReservationsFor(new Date(), null, userView));
	} catch (NamingException e) {
	    throw new RuntimeException(e.getMessage(), e);
	}
	session.setAttribute(ConstantUtils.USER_VIEW_SESSION_KEY, userView);
    }

    public abstract UserView authenticate(final String username, final String password, final String loginDomain) throws AuthenticationException;
}