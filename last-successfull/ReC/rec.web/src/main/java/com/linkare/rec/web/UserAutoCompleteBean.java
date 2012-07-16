package com.linkare.rec.web;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.linkare.commons.jpa.security.User;
import com.linkare.rec.web.service.UserService;
import com.linkare.rec.web.service.UserServiceLocal;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@ManagedBean(name = "userAutoComplete")
@RequestScoped
public class UserAutoCompleteBean {

    private User selectedUser;

    private List<User> users;

    @EJB(beanInterface = UserServiceLocal.class)
    private UserService userService;

    public User getSelectedUser() {
	return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
	this.selectedUser = selectedUser;
    }

    private List<User> getUsers() {
	if (users == null) {
	    users = userService.findAll();
	}
	return users;
    }

    public List<User> completeUser(String query) {
	List<User> suggestions = new ArrayList<User>();

	for (User u : getUsers()) {
	    if (u.getUsername().startsWith(query))
		suggestions.add(u);
	}

	return suggestions;
    }
}