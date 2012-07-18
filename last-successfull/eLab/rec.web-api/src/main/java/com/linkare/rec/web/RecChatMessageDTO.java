/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.web;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Gedsimon Pereira - Linkare TI
 */
public class RecChatMessageDTO implements Serializable{
    
    private String message;
    private Date dateOfSending;
    
    @ConstructorProperties({ "message", "dateOfSending" })
    public RecChatMessageDTO(String message, Date dateOfSending){
        this.message = message;
        this.dateOfSending = dateOfSending;
    }

    public Date getDateOfSending() {
        return dateOfSending;
    }

    public void setDateOfSending(Date dateOfSending) {
        this.dateOfSending = dateOfSending;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
