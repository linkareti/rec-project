package com.linkare.irn.nascimento.web.auth;

import com.linkare.irn.nascimento.model.security.User;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public interface Authenticator {

    public User authenticate(final String username, final String password);
}