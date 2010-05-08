package com.linkare.rec.am.service;

import java.util.List;

import com.linkare.commons.jpa.security.Role;
import com.linkare.commons.jpa.security.User;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public interface UserService extends BusinessService<User, Long> {

    public User authenticate(final String username, final String password);

    public List<Role> getRoles(final User user);

    public User findByUsername(final String username);

    public void createUsers(final List<User> usersToCreate);
}