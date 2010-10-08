package com.linkare.rec.am.web;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.event.ActionEvent;

import com.linkare.commons.utils.PropertiesManager;
import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.am.aop.ExceptionHandle;
import com.linkare.rec.am.aop.ExceptionHandleCase;
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

    public static final String ELAB_HOSTS_AND_PORTS_KEY = "elab.hosts.and.ports";

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

    @ExceptionHandle(@ExceptionHandleCase)
    public PortScannerBean() {
	PropertiesManager.loadProperties("/configuration.properties");
    }

    @ExceptionHandle(@ExceptionHandleCase)
    public void scan(final ActionEvent event) {
	boolean canAccessPort = false;

	List<ElabServer> elabServers = getElabServerInstances(PropertiesManager.getProperty(ELAB_HOSTS_AND_PORTS_KEY));
	for (ElabServer elabServer : elabServers) {
	    try {
		Socket ServerSok = new Socket(elabServer.getHost(), elabServer.getPort());
		canAccessPort = true;
		ServerSok.close();
	    } catch (Exception e) {
		canAccessPort = false;
		break;
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

    private List<ElabServer> getElabServerInstances(String propValue) {
	List<ElabServer> elabs = new ArrayList<ElabServer>();
	if (propValue != null) {
	    String[] split = propValue.split(",");
	    for (int i = 0; i < split.length; i += 2) {
		elabs.add(new ElabServer(split[i], Integer.parseInt(split[i + 1])));
	    }
	}
	return elabs;
    }

    private class ElabServer {

	private String host;
	private int port;

	public ElabServer(String host, int port) {
	    this.setHost(host);
	    this.setPort(port);
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
	    this.host = host;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
	    return host;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
	    this.port = port;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
	    return port;
	}

    }
}