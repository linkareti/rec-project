package com.linkare.irn.nascimento.exception.organization;

public class OrganizationNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -1376202456397251093L;
	
	public OrganizationNotFoundException() {
		super("The specidied organization was not found");
	}

}
