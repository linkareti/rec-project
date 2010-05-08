package com.linkare.rec.am.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

import com.linkare.commons.jpa.security.User;
import com.linkare.rec.am.AllocationDTO;
import com.linkare.rec.am.AllocationManager;
import com.linkare.rec.am.ParameterException;
import com.linkare.rec.am.UnknownDomainException;
import com.linkare.rec.am.model.LoginDomain;
import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.web.auth.LoginProviderFactory;
import com.linkare.rec.am.web.ex.AuthenticationException;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@Remote(AllocationManager.class)
@Stateless(name = "AllocationManager")
public class AllocationManagerBean implements AllocationManager {

    @PersistenceContext(unitName = "AllocationManagerPU")
    private EntityManager entityManager;

    @EJB(beanInterface = LoginDomainServiceLocal.class)
    private LoginDomainService loginDomainService;

    @SuppressWarnings("unchecked")
    @Override
    public List<AllocationDTO> getBy(Date begin, Date end, String laboratoryID) throws RemoteException, ParameterException {
	final List<AllocationDTO> result = new ArrayList<AllocationDTO>();
	final List<Reservation> reservations = entityManager.createNamedQuery("Reservation.findAllocationsInIntervalAndLab")
							    .setParameter("startDate", begin, TemporalType.TIMESTAMP).setParameter("endDate", end,
																   TemporalType.TIMESTAMP)
							    .setParameter("laboratoryName", laboratoryID).getResultList();
	for (final Reservation reservation : reservations) {
	    final List<String> users = getUsers(reservation);
	    final List<String> owners = getOwners(reservation);
	    result.add(new AllocationDTO(reservation.getStartDate(), reservation.getEndDate(), reservation.getExperiment().toString(), users, owners));
	}
	return result;
    }

    private List<String> getOwners(Reservation reservation) {
	return Collections.singletonList(reservation.getReservedBy());
    }

    private List<String> getUsers(Reservation reservation) {
	final List<String> usernames = new ArrayList<String>();
	final List<User> users = reservation.getGroup().getAllUsers();
	for (final User user : users) {
	    usernames.add(user.getUsername());
	}
	return usernames;
    }

    @Override
    public boolean authenticate(String username, String password) throws RemoteException, UnknownDomainException {
	final String domain = username.contains("@") ? username.substring(username.indexOf("@") + 1) : null;
	LoginDomain loginDomain = loginDomainService.findByName(domain);
	if (domain != null && loginDomain == null) {
	    throw new UnknownDomainException("domain not found " + domain);
	}
	try {
	    LoginProviderFactory.getLoginProvider(domain).authenticate(username.substring(0, username.indexOf("@")), password, domain);
	} catch (AuthenticationException e) {
	    e.printStackTrace();
	    return false;
	}
	return true;
    }
}