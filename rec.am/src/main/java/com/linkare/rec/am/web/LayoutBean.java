package com.linkare.rec.am.web;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.ResizeEvent;
import org.primefaces.event.ToggleEvent;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@ManagedBean(name = "layoutBean")
@RequestScoped
public class LayoutBean {

    public void handleClose(CloseEvent event) {
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unit Closed", "Closed unit id:'" + event.getComponent().getId() + "'");

	addMessage(message);
    }

    public void handleToggle(ToggleEvent event) {
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());

	addMessage(message);
    }

    public void handleResize(ResizeEvent event) {
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " resized", "Width:" + event.getWidth()
		+ ", Height:" + event.getHeight());

	addMessage(message);
    }

    private void addMessage(FacesMessage message) {
	FacesContext.getCurrentInstance().addMessage(null, message);
    }
}