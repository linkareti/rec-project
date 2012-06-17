/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.am.repository;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Gedsimon Pereira - Linkare TI
 */
public class BadWordDTO extends AbstractBaseDTO {

    public BadWordDTO(){
        super();
    }
    public BadWordDTO(long badWordID, String locale, String regex){
        super();
        this.badWordId = badWordID;
        this.locale = locale;
        this.regex = regex;
    }
    private long badWordId;

    private String locale;
    
    private String regex;
    
    @XmlElement(required=true)
    public long getBadWordId() {
        return badWordId;
    }
    
    public void setBadWordId(long badWordId) {
        this.badWordId = badWordId;
    }
    
    @XmlElement(required=true)
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @XmlElement(required=true)
    public String getRegex() {
        return regex;
    }

    
    public void setRegex(String regex) {
        this.regex = regex;
    }
}
