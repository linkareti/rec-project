package com.linkare.rec.web.auth;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public enum RoleType {

    ADMIN("admin"),

    TEACHER("teacher"),

    STUDENT("student");

    private String name;

    private RoleType(final String name) {
	this.name = name;
    }

    public static RoleType from(final String roleName) {
	for (final RoleType roleType : values()) {
	    if (roleType.name.equalsIgnoreCase(roleName)) {
		return roleType;
	    }
	}
	return null;
    }
}