package com.linkare.rec.am.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.ResizeEvent;
import org.primefaces.event.ToggleEvent;

import com.linkare.rec.am.web.util.JsfUtil;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@ManagedBean(name = "layoutBean")
@RequestScoped
public class LayoutBean {

    public void handleClose(CloseEvent event) {
	JsfUtil.addSuccessMessage("Closed unit id:'" + event.getComponent().getId() + "'");
    }

    public void handleToggle(ToggleEvent event) {
	JsfUtil.addSuccessMessage(event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());
    }

    public void handleResize(ResizeEvent event) {
	JsfUtil.addSuccessMessage(event.getComponent().getId() + " resized", "Width:" + event.getWidth() + ", Height:" + event.getHeight());
    }
}