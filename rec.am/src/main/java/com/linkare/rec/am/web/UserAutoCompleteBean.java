package com.linkare.rec.am.web;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import com.linkare.commons.jpa.security.User;
import com.linkare.rec.am.service.UserService;
import com.linkare.rec.am.service.UserServiceLocal;

@ManagedBean(name = "userAutoComplete")
@RequestScoped
public class UserAutoCompleteBean {

    private String text;

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

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public List<String> complete(String query) {
	List<String> results = new ArrayList<String>();

	for (int i = 0; i < 10; i++) {
	    results.add(query + i);
	}

	return results;
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

    public void handleSelect(SelectEvent event) {
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected:" + event.getObject().toString(), null);

	FacesContext.getCurrentInstance().addMessage(null, message);
    }
}