package com.linkare.rec.am;

import java.io.Serializable;

/**
 * 
 * @author Artur Correia - Linkare TI
 * 
 */
public class ClientInfoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String userName = null;

    public ClientInfoDTO(final String userName) {
	this.userName = userName;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

}
