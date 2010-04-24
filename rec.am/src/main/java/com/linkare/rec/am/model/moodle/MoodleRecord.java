package com.linkare.rec.am.model.moodle;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.linkare.commons.utils.EqualityUtils;

@Embeddable
public class MoodleRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "EXTERNAL_USER", nullable = true, insertable = true, updatable = false)
    private String externalUser;

    @Column(name = "EXTERNAL_COURSE_ID", nullable = true, insertable = true, updatable = true)
    private String externalCourseId;

    @Column(name = "DOMAIN", nullable = true, insertable = true, updatable = false)
    private String domain;

    public MoodleRecord() {
	super();
    }

    public MoodleRecord(String externalUser, String externalCourseId, String domain) {
	this();
	this.externalUser = externalUser;
	this.externalCourseId = externalCourseId;
	this.domain = domain;
    }

    /**
     * @return the externalUser
     */
    public String getExternalUser() {
	return externalUser;
    }

    /**
     * @param externalUser
     *            the externalUser to set
     */
    public void setExternalUser(String externalUser) {
	this.externalUser = externalUser;
    }

    /**
     * @return the externalCourseId
     */
    public String getExternalCourseId() {
	return externalCourseId;
    }

    /**
     * @param externalCourseId
     *            the externalCourseId to set
     */
    public void setExternalCourseId(String externalCourseId) {
	this.externalCourseId = externalCourseId;
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

    @Override
    public boolean equals(final Object other) {
	if (!(other instanceof MoodleRecord)) {
	    return false;
	}
	return equalsTo((MoodleRecord) other);
    }

    @Override
    public int hashCode() {
	int result = 14;
	result = 29 * result + (getExternalUser() != null ? getExternalUser().hashCode() : 0);
	result = 29 * result + (getExternalCourseId() != null ? getExternalCourseId().hashCode() : 0);
	result = 29 * result + (getDomain() != null ? getDomain().hashCode() : 0);
	return result;
    }

    private boolean equalsTo(final MoodleRecord other) {
	return EqualityUtils.equals(getExternalUser(), other.getExternalUser()) && EqualityUtils.equals(getExternalCourseId(), other.getExternalCourseId())
		&& EqualityUtils.equals(getDomain(), other.getDomain());
    }

    @Override
    public String toString() {
	return getExternalUser() + "-" + getExternalCourseId() + "-" + getDomain();
    }
}