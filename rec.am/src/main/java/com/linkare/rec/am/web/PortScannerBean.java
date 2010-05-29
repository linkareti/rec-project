package com.linkare.rec.am.web;

import java.io.Serializable;
import java.net.Socket;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.am.web.util.ConstantUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@ManagedBean(name = "portScannerBean")
@ApplicationScoped
public class PortScannerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public void scan(final ActionEvent event) {
	System.out.println("SCANNING PORTS...");
	boolean canAccessPort = false;
	int startPortRange = 9000;
	int stopPortRange = 9001;

	for (int i = startPortRange; i <= stopPortRange; i++) {
	    if (canAccessPort) {
		break;
	    }
	    try {
		Socket ServerSok = new Socket("elab1.ist.utl.pt", i);
		canAccessPort = true;
		ServerSok.close();
	    } catch (Exception e) {
	    }
	}
	if (!canAccessPort) {
	    JsfUtil.addGlobalErrorMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY, "error.elab.port.not.accessible");
	}
	return null;
    }
}