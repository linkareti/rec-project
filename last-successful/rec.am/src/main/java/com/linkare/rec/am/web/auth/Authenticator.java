package com.linkare.rec.am.web.auth;

import javax.ejb.Local;

@Local
public interface Authenticator {

    boolean authenticate();

}
