package com.linkare.rec.am.service;

import static com.linkare.rec.am.model.Reservation.FIND_IN_INTERVAL_AND_LAB_QUERYNAME;
import static com.linkare.rec.am.model.Reservation.QUERY_PARAM_END_DATE;
import static com.linkare.rec.am.model.Reservation.QUERY_PARAM_LAB_NAME;
import static com.linkare.rec.am.model.Reservation.QUERY_PARAM_START_DATE;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

import com.linkare.commons.jpa.security.User;
import com.linkare.commons.utils.StringUtils;
import com.linkare.rec.am.AllocationDTO;
import com.linkare.rec.am.AllocationManager;
import com.linkare.rec.am.ParameterException;
import com.linkare.rec.am.UnknownDomainException;
import com.linkare.rec.am.model.LoginDomain;
import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.web.auth.LoginProviderFactory;
import com.linkare.rec.am.web.ex.AuthenticationException;
import com.linkare.rec.am.web.util.ConstantUtils;

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
	final List<Reservation> reservations = entityManager.createNamedQuery(FIND_IN_INTERVAL_AND_LAB_QUERYNAME).setParameter(QUERY_PARAM_START_DATE, begin,
															       TemporalType.TIMESTAMP)
							    .setParameter(QUERY_PARAM_END_DATE, end, TemporalType.TIMESTAMP).setParameter(QUERY_PARAM_LAB_NAME,
																	  laboratoryID)
							    .getResultList();
	for (final Reservation reservation : reservations) {
	    final Set<String> users = getUsers(reservation);
	    final Set<String> owners = getOwners(reservation);
	    result.add(new AllocationDTO(reservation.getStartDate(), reservation.getEndDate(), reservation.getExperiment().toString(), users, owners));
	}
	return result;
    }

    private Set<String> getOwners(Reservation reservation) {
	return Collections.singleton(reservation.getReservedBy());
    }

    private Set<String> getUsers(Reservation reservation) {
	final Set<String> usernames = new HashSet<String>();
	final List<User> users = reservation.getGroup().getAllUsers();
	for (final User user : users) {
	    usernames.add(user.getUsername());
	}
	return usernames;
    }

    @Override
    public boolean authenticate(String username, String password) throws RemoteException, UnknownDomainException {
	final String simpleUsername = username.contains("@") ? username.substring(0, username.indexOf("@")) : username;
	final String domain = username.contains("@") ? username.substring(username.indexOf("@") + 1) : ConstantUtils.INTERNAL_DOMAIN_NAME;
	LoginDomain loginDomain = loginDomainService.findByName(domain);
	if (!StringUtils.isBlank(domain) && loginDomain == null) {
	    throw new UnknownDomainException("domain not found " + domain);
	}
	try {
	    LoginProviderFactory.getLoginProvider(domain).authenticate(simpleUsername, password, domain);
	} catch (AuthenticationException e) {
	    e.printStackTrace();
	    return false;
	}
	return true;
    }
}