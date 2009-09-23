package com.linkare.rec.am.action;

import javax.ejb.Local;

@Local
public interface Authenticator {

	boolean authenticate();

}
