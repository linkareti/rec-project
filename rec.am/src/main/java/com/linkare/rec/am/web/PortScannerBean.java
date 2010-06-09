package com.linkare.rec.am.web;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlCommandLink;
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
@RequestScoped
public class PortScannerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String DEFAULT_ELAB_HOST = "elab1.ist.utl.pt";

    private static String elabHost;

    public static final int DEFAULT_START_PORT = 9000;

    private static int startPort = -1;

    public static final int DEFAULT_END_PORT = 9001;

    private static int endPort = -1;

    public static final String ELAB_HOST_KEY = "elab.host";

    public static final String ELAB_PORTRANGE_START_KEY = "elab.portrange.start";

    public static final String ELAB_PORTRANGE_END_KEY = "elab.portrange.end";

    private HtmlCommandLink checkMulticastServerLink;

    /**
     * @return the checkMulticastServerLink
     */
    public HtmlCommandLink getCheckMulticastServerLink() {
	return checkMulticastServerLink;
    }

    /**
     * @param checkMulticastServerLink
     *            the checkMulticastServerLink to set
     */
    public void setCheckMulticastServerLink(HtmlCommandLink outputLink) {
	this.checkMulticastServerLink = outputLink;
    }

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
	    JsfUtil.addErrorMessage(getCheckMulticastServerLink().getClientId(), ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY,
				    "error.multicastServer.not.available");
	} else {
	    JsfUtil.addSuccessMessage(getCheckMulticastServerLink().getClientId(), ConstantUtils.BUNDLE, ConstantUtils.LABEL_INFO_KEY,
				      "info.multicastServer.available");
	}
    }

    private static int getPortRangeStart() {
	if (startPort == -1) {
	    try {
		startPort = PropertiesManager.getIntegerProperty(ELAB_PORTRANGE_START_KEY);
	    } catch (NumberFormatException e) {
		startPort = DEFAULT_START_PORT;
	    }
	}
	return startPort;
    }

    private int getPortRangeEnd() {
	if (endPort == -1) {
	    try {
		endPort = PropertiesManager.getIntegerProperty(ELAB_PORTRANGE_END_KEY);
	    } catch (NumberFormatException e) {
		endPort = DEFAULT_END_PORT;
	    }
	}
	return endPort;
    }

    private String getElabHost() {
	if (elabHost == null) {
	    String elabHostKey = PropertiesManager.getProperty(ELAB_HOST_KEY);
	    if (elabHostKey != null) {
		elabHost = elabHostKey;
	    } else {
		elabHost = DEFAULT_ELAB_HOST;
	    }
	}
	return elabHost;
    }
}