package com.linkare.rec.am.web;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.ResizeEvent;
import org.primefaces.event.ToggleEvent;

import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.am.web.util.ConstantUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@ManagedBean(name = "layoutBean")
@RequestScoped
public class LayoutBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public void handleClose(CloseEvent event) {
	JsfUtil.addSuccessMessage(ConstantUtils.LABEL_INFO, "Closed unit id:'" + event.getComponent().getId() + "'");
    }

    public void handleToggle(ToggleEvent event) {
	JsfUtil.addSuccessMessage(event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());
    }

    public void handleResize(ResizeEvent event) {
	JsfUtil.addSuccessMessage(event.getComponent().getId() + " resized", "Width:" + event.getWidth() + ", Height:" + event.getHeight());
    }
}