package com.linkare.rec.am.web.auth;

import java.util.ArrayList;
import java.util.List;

import com.linkare.commons.jpa.security.Role;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class InternalUserView extends UserView {

    private static final long serialVersionUID = 1L;

    private List<RoleType> roles;

    public InternalUserView(String username, String domain) {
	super(username, domain);
    }

    public InternalUserView(String username, String domain, List<Role> roles) {
	this(username, domain);
	setRoles(roles);
    }

    private void setRoles(final List<Role> roles) {
	for (final Role role : roles) {
	    final RoleType roleType = RoleType.from(role.getName());
	    if (roleType != null) {
		addRole(roleType);
	    }
	}
    }

    private void addRole(final RoleType roleType) {
	getRoles().add(roleType);
    }

    @Override
    public boolean isExternal() {
	return false;
    }

    public boolean isAdmin() {
	return getRoles().contains(RoleType.ADMIN);
    }

    public List<RoleType> getRoles() {
	return roles = roles == null ? new ArrayList<RoleType>() : roles;
    }
}