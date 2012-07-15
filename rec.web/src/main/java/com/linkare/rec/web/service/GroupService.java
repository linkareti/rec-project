package com.linkare.rec.web.service;

import java.util.List;

import com.linkare.commons.jpa.security.Group;
import com.linkare.commons.jpa.security.User;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public interface GroupService extends BusinessService<Group, Long> {

    public List<User> getNonMembers(final Group group);

    public Group setUsersMembership(final Group group, final List<User> users);
}