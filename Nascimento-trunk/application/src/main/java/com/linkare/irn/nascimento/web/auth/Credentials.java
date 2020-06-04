package com.linkare.irn.nascimento.web.auth;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@Named("credentials")
@RequestScoped
public class Credentials implements Serializable {

    private static final long serialVersionUID = -1766187069571099165L;

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @Size(min = 4, max = 20)
    private String password;

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }
}