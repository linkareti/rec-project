/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;

/**
 *
 * @author Gedsimon Pereira - LinkareTI
 */
@Entity
@Table(name = "BAD_WORD")
@NamedQueries({ @NamedQuery(name = BadWord.FIND_ALL_QUERYNAME, query = BadWord.FIND_ALL_QUERY),
	@NamedQuery(name = BadWord.COUNT_ALL_QUERYNAME, query = BadWord.COUNT_ALL_QUERY),
        @NamedQuery(name = BadWord.FIND_FOR_LOCALE_QUERYNAME, query = BadWord.FIND_FOR_LOCALE_QUERY)})
public class BadWord extends DefaultDomainObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String QUERY_PARAM_LOCALE = "locale";
    
    public static final String FIND_ALL_QUERYNAME = "BadWord.findAll";
    public static final String FIND_ALL_QUERY = "select bw from BadWord bw";

    public static final String COUNT_ALL_QUERYNAME = "BadWord.countAll";
    public static final String COUNT_ALL_QUERY = "select count(bw) from BadWord bw";
    
    public static final String FIND_FOR_LOCALE_QUERYNAME = "BadWord.findForLocale";
    public static final String FIND_FOR_LOCALE_QUERY = "Select bw from BadWord bw WHERE bw.locale = :" + QUERY_PARAM_LOCALE;
    

    @Column(name = "REGEX", length = 254)
    private String regex;

    @Column(name = "LOCALE", length = 20)
    private String locale;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    
}