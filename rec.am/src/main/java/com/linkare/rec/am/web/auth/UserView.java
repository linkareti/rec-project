package com.linkare.rec.am.web.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.ScheduleEvent;

import com.linkare.commons.utils.StringUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@ManagedBean(name = "userView")
@SessionScoped
public class UserView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String domain;

    private List<ScheduleEvent> reservations = new ArrayList<ScheduleEvent>();

    public UserView() {
	super();
    }

    public UserView(String username, String domain) {
	this.username = username;
	this.domain = domain;
    }

    /**
     * @return the username
     */
    public String getUsername() {
	return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
	this.username = username;
    }

    /**
     * @return the domain
     */
    public String getDomain() {
	return domain;
    }

    /**
     * @param domain
     *            the domain to set
     */
    public void setDomain(String domain) {
	this.domain = domain;
    }

    public String getFullUsername() {
	return getUsername() + (StringUtils.isBlank(getDomain()) ? "" : "@" + getDomain());
    }

    /**
     * This method should be overridden by the subclasses
     * 
     * @return returns dummy false
     */
    public boolean isAdmin() {
	return false;
    }

    /**
     * This method should be overridden by the subclasses
     * 
     * @return returns dummy false
     */
    public boolean isTeacher() {
	return false;
    }

    /**
     * This method should be overridden by the subclasses
     * 
     * @return returns dummy false
     */
    public boolean isExternal() {
	return false;
    }

    public boolean getHasReservations() {
	return getReservations().size() != 0;
    }

    /**
     * @return the reservations
     */
    public List<ScheduleEvent> getReservations() {
	return reservations;
    }

    /**
     * @param reservations
     *            the reservations to set
     */
    public void setReservations(List<ScheduleEvent> reservations) {
	this.reservations = reservations;
    }
}