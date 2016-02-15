/* 
 * WSServiceLocator.java created on 10 de Mai de 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.utils;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 * Class to lookup for a specific web service.
 * 
 * @author Bruno Catarino - Linkare TI
 */
public class WSServiceLocator {

	/**
	 * Looks up for a remote web service interface.
	 * 
	 * @param address The url for the ws endpoint, without the ?wsdl part (ex:
	 *            http://www.linkare.com/rec/MyServiceWS).
	 * @param namespace 
	 * @param localpart
	 * @param type
	 * @return
	 */
	public static <T> T lookup(String address, String namespace, String localpart, Class<T> type) {

		if (address == null || namespace == null || localpart == null || type == null) {
			throw new IllegalAccessError("All arguments are mandatory");
		}

		String wsdlAddress = address + "?wsdl";

		URL wsdlURL;
		try {
			wsdlURL = new URL(wsdlAddress);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("The address " + wsdlAddress + " is an invalid url");
		}

		Service service = Service.create(wsdlURL, new QName(namespace, localpart));
		return service.getPort(type);
	}
}