package com.linkare.rec.am;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class AllocationDTO implements Serializable {

    private static final long serialVersionUID = -4730231207036294734L;

    private Calendar begin;

    private Calendar end;

    private String experimentId;

    private List<String> users;

    private List<String> owners;

    public AllocationDTO() {
	super();
    }

    public AllocationDTO(Calendar begin, Calendar end, String experimentId, List<String> users, List<String> owners) {
	super();
	this.begin = begin;
	this.end = end;
	this.experimentId = experimentId;
	this.users = users;
	this.owners = owners;
    }

    /**
     * @return the begin
     */
    public Calendar getBegin() {
	return begin;
    }

    /**
     * @param begin
     *            the begin to set
     */
    public void setBegin(Calendar begin) {
	this.begin = begin;
    }

    /**
     * @return the end
     */
    public Calendar getEnd() {
	return end;
    }

    /**
     * @param end
     *            the end to set
     */
    public void setEnd(Calendar end) {
	this.end = end;
    }

    /**
     * @return the experimentId
     */
    public String getExperimentId() {
	return experimentId;
    }

    /**
     * @param experimentId
     *            the experimentId to set
     */
    public void setExperimentId(String experimentId) {
	this.experimentId = experimentId;
    }

    /**
     * @return the users
     */
    public List<String> getUsers() {
	return users;
    }

    /**
     * @param users
     *            the users to set
     */
    public void setUsers(List<String> users) {
	this.users = users;
    }

    /**
     * @return the owners
     */
    public List<String> getOwners() {
	return owners;
    }

    /**
     * @param owners
     *            the owners to set
     */
    public void setOwners(List<String> owners) {
	this.owners = owners;
    }
}