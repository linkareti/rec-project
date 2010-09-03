package com.linkare.rec.am;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class AllocationDTO implements Serializable {

    private static final long serialVersionUID = -4730231207036294734L;

    private Date begin;

    private Date end;

    private String experimentId;

    private Set<String> users;

    private Set<String> owners;

    public AllocationDTO() {
	super();
    }

    public AllocationDTO(final Date begin, final Date end, final String experimentId, final Set<String> users, final Set<String> owners) {
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
    public Date getBegin() {
	return begin;
    }

    /**
     * @param begin
     *            the begin to set
     */
    public void setBegin(final Date begin) {
	this.begin = begin;
    }

    /**
     * @return the end
     */
    public Date getEnd() {
	return end;
    }

    /**
     * @param end
     *            the end to set
     */
    public void setEnd(final Date end) {
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
    public void setExperimentId(final String experimentId) {
	this.experimentId = experimentId;
    }

    /**
     * @return the users
     */
    public Set<String> getUsers() {
	return users;
    }

    /**
     * @param users
     *            the users to set
     */
    public void setUsers(final Set<String> users) {
	this.users = users;
    }

    /**
     * @return the owners
     */
    public Set<String> getOwners() {
	return owners;
    }

    /**
     * @param owners
     *            the owners to set
     */
    public void setOwners(final Set<String> owners) {
	this.owners = owners;
    }
}