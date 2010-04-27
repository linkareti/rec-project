package com.linkare.rec.am.web;

import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.ToggleEvent;

import com.linkare.rec.am.web.util.ConstantUtils;
import com.linkare.rec.am.web.util.JsfUtil;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@ManagedBean(name = "panelBean")
@RequestScoped
public class PanelBean {

    public void handleClose(CloseEvent event) {
	JsfUtil.addSuccessMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString("info.panelClose"));
    }

    public void handleToggle(ToggleEvent event) {
	JsfUtil.addSuccessMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString("info.panelToggled")
		+ ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString("label." + event.getVisibility().name()));
    }
}