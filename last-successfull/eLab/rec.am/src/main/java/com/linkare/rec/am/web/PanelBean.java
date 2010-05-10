package com.linkare.rec.am.web;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.ToggleEvent;

import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.am.web.util.ConstantUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@ManagedBean(name = "panelBean")
@RequestScoped
public class PanelBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public void handleClose(CloseEvent event) {
	JsfUtil.addSuccessMessage(ConstantUtils.LABEL_INFO, ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString("info.panelClose"));
    }

    public void handleToggle(ToggleEvent event) {
	JsfUtil.addSuccessMessage(ConstantUtils.LABEL_INFO, ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString("info.panelToggled")
		+ ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString("label." + event.getVisibility().name()));
    }
}