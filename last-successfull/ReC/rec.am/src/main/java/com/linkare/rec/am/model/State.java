package com.linkare.rec.am.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Joao
 */
@Embeddable
public class State implements Serializable {

    @Column(name = "LABEL")
    private String label;

    @Column(name = "HELP_MESSAGE")
    private String helpMessage;

    @Column(name = "URL")
    private String url;

    @Column(name = "IS_ACTIVE")
    private boolean active;

    /**
     * Get the value of label
     * 
     * @return the value of label
     */
    public final String getLabel() {
	return label;
    }

    /**
     * Set the value of label
     * 
     * @param label
     *            new value of label
     */
    public final void setLabel(String label) {
	this.label = label;
    }

    /**
     * Get the value of helpMessage
     * 
     * @return the value of helpMessage
     */
    public final String getHelpMessage() {
	return helpMessage;
    }

    /**
     * Set the value of helpMessage
     * 
     * @param helpMessage
     *            new value of helpMessage
     */
    public final void setHelpMessage(String helpMessage) {
	this.helpMessage = helpMessage;
    }

    /**
     * Get the value of url
     * 
     * @return the value of url
     */
    public final String getUrl() {
	return url;
    }

    /**
     * Set the value of url
     * 
     * @param url
     *            new value of url
     */
    public final void setUrl(String url) {
	this.url = url;
    }

    /**
     * Get the value of active
     * 
     * @return the value of active
     */
    public final boolean isActive() {
	return active;
    }

    /**
     * Set the value of active
     * 
     * @param active
     *            new value of active
     */
    public final void setActive(boolean active) {
	this.active = active;
    }
}
