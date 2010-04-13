package com.linkare.rec.am.model.moodle;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MoodleRecord {

    @Column(name = "EXTERNAL_USER", nullable = true, insertable = true, updatable = false)
    private String externalUser;

    @Column(name = "EXTERNAL_COURSE_ID", nullable = true, insertable = true, updatable = false)
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
}