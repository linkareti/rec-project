package com.linkare.rec.am.web;

import javax.ejb.Local;

@Local
public interface Authenticator {

	boolean authenticate();

}
