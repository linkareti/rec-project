package com.linkare.rec.am.model;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Joao
 */
@Embeddable
public class State implements Serializable {

    private String label;

    private String helpMessage;

    private String url;

    private boolean active;

    /**
     * Get the value of label
     *
     * @return the value of label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the value of label
     *
     * @param label new value of label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Get the value of helpMessage
     *
     * @return the value of helpMessage
     */
    public String getHelpMessage() {
        return helpMessage;
    }

    /**
     * Set the value of helpMessage
     *
     * @param helpMessage new value of helpMessage
     */
    public void setHelpMessage(String helpMessage) {
        this.helpMessage = helpMessage;
    }

    /**
     * Get the value of url
     *
     * @return the value of url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the value of url
     *
     * @param url new value of url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the value of active
     *
     * @return the value of active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set the value of active
     *
     * @param active new value of active
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
