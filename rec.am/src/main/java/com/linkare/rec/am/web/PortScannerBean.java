package com.linkare.rec.am.web;

import java.io.Serializable;
import java.net.Socket;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

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

    public String scan() {
	boolean canAccessPort = false;
	int startPortRange = 9000;
	int stopPortRange = 9001;

	for (int i = startPortRange; i <= stopPortRange; i++) {
	    if (canAccessPort) {
		break;
	    }
	    try {
		Socket ServerSok = new Socket("elab1.ist.utl.pt", i);
		System.out.println("Port in use: " + i);
		ServerSok.close();
	    } catch (Exception e) {
	    }
	    System.out.println("Port not in use: " + i);
	    canAccessPort = true;
	}
	if (!canAccessPort) {
	    JsfUtil.addGlobalErrorMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY, "error.elab.port.not.accessible");
	}
	return null;
    }
}