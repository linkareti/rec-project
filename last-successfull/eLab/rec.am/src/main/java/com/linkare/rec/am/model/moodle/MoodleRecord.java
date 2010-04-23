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

    @Column(name = "MOODLE_URL", nullable = true, insertable = true, updatable = false)
    private String moodleUrl;

    public MoodleRecord() {
	super();
    }

    public MoodleRecord(String externalUser, String externalCourseId, String moodleUrl) {
	this();
	this.externalUser = externalUser;
	this.externalCourseId = externalCourseId;
	this.moodleUrl = moodleUrl;
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
     * @return the moodleUrl
     */
    public String getMoodleUrl() {
	return moodleUrl;
    }

    /**
     * @param moodleUrl
     *            the moodleUrl to set
     */
    public void setMoodleUrl(String moodleUrl) {
	this.moodleUrl = moodleUrl;
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
	result = 29 * result + (getMoodleUrl() != null ? getMoodleUrl().hashCode() : 0);
	return result;
    }

    private boolean equalsTo(final MoodleRecord other) {
	return EqualityUtils.equals(getExternalUser(), other.getExternalUser()) && EqualityUtils.equals(getExternalCourseId(), other.getExternalCourseId())
		&& EqualityUtils.equals(getMoodleUrl(), other.getMoodleUrl());
    }

    @Override
    public String toString() {
	return getExternalUser() + "-" + getExternalCourseId() + "-" + getMoodleUrl();
    }
}