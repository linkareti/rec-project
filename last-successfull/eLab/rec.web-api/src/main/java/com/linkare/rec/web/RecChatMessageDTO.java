/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.web;

import java.beans.ConstructorProperties;
import java.io.Serializable;

/**
 *
 * @author Gedsimon Pereira - Linkare TI
 */
public final class RecChatMessageDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 926839542310583383L;
	private final String message;
    private final long dateOfSending;
    private final String hardwareUniqueId;

    @ConstructorProperties({"message", "dateOfSending", "HardwareUniqueId"})
    public RecChatMessageDTO(String message, long dateOfSending, String hardwareUniqueId) {
        this.message = message;
        this.dateOfSending = dateOfSending;
        this.hardwareUniqueId = hardwareUniqueId;
    }

    public long getDateOfSending() {
        return dateOfSending;
    }

    public String getMessage() {
        return message;
    }

    public String getHardwareUniqueId() {
        return hardwareUniqueId;
    }
}
