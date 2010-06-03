package com.linkare.rec.am.web;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import com.linkare.commons.utils.PropertiesManager;
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

    public static final String DEFAULT_ELAB_HOST = "elab1.ist.utl.pt";

    public static final int DEFAULT_START_PORT = 9000;

    public static final int DEFAULT_END_PORT = 9001;

    public static final String ELAB_HOST_KEY = "elab.host";

    public static final String ELAB_PORTRANGE_START_KEY = "elab.portrange.start";

    public static final String ELAB_PORTRANGE_END_KEY = "elab.portrange.end";

    public PortScannerBean() {
	try {
	    PropertiesManager.loadProperties("/configuration.properties");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void scan(final ActionEvent event) {
	boolean canAccessPort = false;
	int startPortRange = getPortRangeStart();
	int stopPortRange = getPortRangeEnd();

	for (int i = startPortRange; i <= stopPortRange; i++) {
	    if (canAccessPort) {
		break;
	    }
	    try {
		Socket ServerSok = new Socket(getElabHost(), i);
		canAccessPort = true;
		ServerSok.close();
	    } catch (Exception e) {
	    }
	}
	if (!canAccessPort) {
	    JsfUtil.addGlobalErrorMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY, "error.elab.port.not.accessible");
	} else {
	    JsfUtil.addGlobalSuccessMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_INFO_KEY, "info.elab.port.accessible");
	}
    }

    private int getPortRangeStart() {
	try {
	    return PropertiesManager.getIntegerProperty(ELAB_PORTRANGE_START_KEY);
	} catch (NumberFormatException e) {
	    return DEFAULT_START_PORT;
	}
    }

    private int getPortRangeEnd() {
	try {
	    return PropertiesManager.getIntegerProperty(ELAB_PORTRANGE_END_KEY);
	} catch (NumberFormatException e) {
	    return DEFAULT_END_PORT;
	}
    }

    private String getElabHost() {
	String elabHostKey = PropertiesManager.getProperty(ELAB_HOST_KEY);
	if (elabHostKey != null) {
	    return elabHostKey;
	} else {
	    return DEFAULT_ELAB_HOST;
	}
    }
}