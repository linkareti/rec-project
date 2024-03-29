package com.linkare.rec.web.auth;

import java.util.Date;

import javax.naming.NamingException;

import com.linkare.rec.web.service.ReservationService;
import com.linkare.rec.web.ex.AuthenticationException;
import com.linkare.rec.web.moodle.SessionHelper;
import com.linkare.rec.web.util.JndiHelper;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public abstract class LoginProvider {

    public void login(final String username, final String password, final String loginDomain) throws AuthenticationException {
	UserView userView = authenticate(username, password, loginDomain);
	try {
	    final ReservationService reservationService = JndiHelper.getReservationService();
	    userView.setReservations(reservationService.findReservationsFor(new Date(), null, userView));
	} catch (NamingException e) {
	    throw new RuntimeException(e.getMessage(), e);
	}
	SessionHelper.setUserView(userView);
    }

    public abstract UserView authenticate(final String username, final String password, final String loginDomain) throws AuthenticationException;
}