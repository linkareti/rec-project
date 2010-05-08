package com.linkare.rec.am.model.moodle;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.linkare.commons.utils.EqualityUtils;

@Embeddable
public class MoodleRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "EXTERNAL_COURSE_ID", nullable = true, insertable = true, updatable = true)
    private String externalCourseId;

    public MoodleRecord() {
	super();
    }

    public MoodleRecord(String externalCourseId) {
	this();
	this.externalCourseId = externalCourseId;
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
	result = 29 * result + (getExternalCourseId() != null ? getExternalCourseId().hashCode() : 0);
	return result;
    }

    private boolean equalsTo(final MoodleRecord other) {
	return EqualityUtils.equals(getExternalCourseId(), other.getExternalCourseId());
    }

    @Override
    public String toString() {
	return getExternalCourseId();
    }
}