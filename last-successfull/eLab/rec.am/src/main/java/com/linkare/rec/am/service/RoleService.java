package com.linkare.rec.am.service;

import java.util.List;

import com.linkare.commons.jpa.security.Role;
import com.linkare.commons.jpa.security.User;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public interface RoleService extends BusinessService<Role, Long> {

    public List<User> getNonMembers(final Role role);

    public Role setUsersMembership(final Role role, final List<User> users);
}