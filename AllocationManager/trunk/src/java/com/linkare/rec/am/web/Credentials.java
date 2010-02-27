/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.am.web;

import java.io.Serializable;
import java.security.Principal;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Joao
 *
 * This is just a simple container class Weld bean for the username
 * and password entry values.
 */
@Named
@SessionScoped
@Default
public class Credentials implements Serializable {

    private String username = null;
    private String password = null;

    /**
     * @return the User Principal
     */
    public Principal getUser() {
            return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
    }



    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
